package com.atypon.database;

import com.atypon.files.Log;
import com.atypon.files.ObjectWriter;
import java.io.*;
import java.util.ArrayList;

public class Update implements DatabaseUpdate {
  File databaseFile;
  TransactionDataInterface transactionData;
  
  public Update(File databaseFile, TransactionDataInterface transactionData) {
    this.transactionData = transactionData;
    this.databaseFile = databaseFile;
  }

  @Override
  public void updateName(Integer id, String newValue) {
    update(id, "name", newValue);
  }

  @Override
  public void updateAge(Integer id, Integer newValue) {
    update(id, "age", newValue);
  }
  
  public void update(Integer id, String property, Object newValue) {
    PersonInterface person = new NullPerson();
    var list = new ArrayList<Object>();
    try (var reader = new ObjectInputStream(
            new FileInputStream(databaseFile))) {
      synchronized (this) {
        do {
          person = (PersonInterface) reader.readObject();
          if (person.getId().equals(id)) {
            if (property.equals("name")) {
              person.setName((String) newValue);
            } else {
              person.setAge((Integer) newValue);
            }
          }
          list.add(person);
        } while (true);
      }
    } catch (EOFException eofException) {
      // As expected
      new Log(Update.class.getName()).info(eofException);
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Update.class.getName()).warning(exception);
    }

    synchronized (this) {
      transactionData.registerWrite(id);
      new ObjectWriter().writeNewList(databaseFile, list);
    }

  }

}
