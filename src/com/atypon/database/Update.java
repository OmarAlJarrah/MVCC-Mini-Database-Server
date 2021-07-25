package com.atypon.database;

import com.atypon.files.Log;
import com.atypon.files.ObjectWriter;
import java.io.*;
import java.util.ArrayList;

public class Update implements DatabaseUpdate {
  private final File DATABASE_FILE;
  TransactionDataInterface transactionData;
  
  public Update(File databaseFile, TransactionDataInterface transactionData) {
    this.transactionData = transactionData;
    this.DATABASE_FILE = databaseFile;
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
    PersonInterface person = PersonFactory.makeNullPerson();
    var list = new ArrayList<Object>();
    try (var reader = new ObjectInputStream(
            new FileInputStream(DATABASE_FILE))) {
        do {
          person = (PersonInterface) reader.readObject();
          if (person.getId().equals(id)) {
            if (property.equalsIgnoreCase("name")) {
              person.setFirstName((String) newValue);
            } else {
              person.setAge((Integer) newValue);
            }
          }
          list.add(person);
        } while (true);
    } catch (EOFException eofException) {
      // As expected
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Update.class.getName()).warning(exception);
    }

      transactionData.registerWrite(id);
      new ObjectWriter().writeNewList(DATABASE_FILE, list);
  }

}
