package com.atypon.database;

import com.atypon.files.Log;
import com.atypon.files.ObjectWriter;

import java.io.*;
import java.util.ArrayList;

public class Delete implements DatabaseDelete {
  File databaseFile;
  TransactionDataInterface transactionData;

  public Delete(File databaseFile, TransactionDataInterface transactionData){
    this.databaseFile = databaseFile;
    this.transactionData = transactionData;
  }

  @Override
  public void delete(Integer id) {
    Person person = new NullPerson();
    var list = new ArrayList<>();
    System.out.println("DELETING");
    try (var reader = new ObjectInputStream(
            new FileInputStream(databaseFile))) {
      synchronized (this){
        do {
          System.out.println("Iteration");
          person = (Person) reader.readObject();
          if (!person.getId().equals(id)) {
            list.add(person);
          }
        } while (true);
      }
    } catch (EOFException eofException) {
      // As expected
      new Log(Delete.class.getName()).
              info(eofException);
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Delete.class.getName()).
              warning(exception);
    }
    synchronized (this){
      var writer = new ObjectWriter();
      writer.writeNewList(databaseFile, list);
      transactionData.registerWrite(id);
    }
  }
}
