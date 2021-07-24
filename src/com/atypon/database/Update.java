package com.atypon.database;

import com.atypon.files.Log;
import com.atypon.files.ObjectWriter;
import java.io.*;
import java.util.ArrayList;

public class Update implements DatabaseUpdate {
  private final File databaseFile;
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
          System.out.println("DOING");
          person = (PersonInterface) reader.readObject();
          System.out.println(id + " compared to " + person.getId());
          if (person.getId().equals(id)) {
            if (property.equalsIgnoreCase("name")) {
              System.out.println("Old name = " + person.getName());
              person.setName((String) newValue);
              System.out.println("New name = " + person.getName());
            } else {
              System.out.println("Old age = " + person.getAge());
              person.setAge((Integer) newValue);
              System.out.println("New age = " + person.getAge());
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

    synchronized (databaseFile) {
      transactionData.registerWrite(id);
      new ObjectWriter().writeNewList(databaseFile, list);
    }

  }

}
