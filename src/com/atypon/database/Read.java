package com.atypon.database;

import com.atypon.files.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Read implements DatabaseRead {
  File databaseFile;
  TransactionDataInterface transactionData;


  public Read(File databaseFile, TransactionDataInterface transactionData){
    this.transactionData = transactionData;
    this.databaseFile = databaseFile;
  }

  @Override
  public Object read(Integer id) {
    Person person = new NullPerson();
    try (var reader = new ObjectInputStream(
            new FileInputStream(databaseFile))) {
      do {
        person = (Person) reader.readObject();
      } while (!person.getId().equals(id));
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Read.class.getName())
              .warning(exception);
    }
    transactionData.registerRead(id);
    return person;
  }

  @Override
  public List<Object> readAll() {
    List<Object> output = new ArrayList<>();
    try (var reader = new ObjectInputStream(
            new FileInputStream(databaseFile))) {
      synchronized (this){
        do {
          Object object = reader.readObject();
          PersonInterface person = (PersonInterface) object;
          Integer id = person.getId();
          output.add(object);
          transactionData.registerRead(id);
        } while (true);
      }
    } catch (EOFException eofException){
      // as expected
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Read.class.getName()).warning(exception);
    }
    return output;
  }
}
