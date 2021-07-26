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
    PersonInterface person;
    var list = new ArrayList<>();
    try (var reader = new ObjectInputStream(
            new FileInputStream(databaseFile))) {
        do {
          person = (Person) reader.readObject();
          if (!person.getId().equals(id)) {
            list.add(person);
          }
        } while (true);
    } catch (EOFException eofException) {
      // As expected
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Delete.class.getName()).
              warning(exception);
    }
      ObjectWriter writer = new ObjectWriter();
      writer.writeNewList(databaseFile, list);
      transactionData.registerWrite(id);
  }
}
