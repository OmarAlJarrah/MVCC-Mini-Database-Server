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
      synchronized (this){
        do {
          person = (Person) reader.readObject();
        } while (!person.getId().equals(id));
      }
    } catch (IOException | ClassNotFoundException exception) {
      exception.printStackTrace();
    }
    synchronized (this){
      transactionData.registerRead(id);
    }
    return person;
  }

  @Override
  public List<Object> readAll() {
    List<Object> output = new ArrayList<>();
    try (var reader = new ObjectInputStream(
            new FileInputStream(databaseFile))) {
      synchronized (this){
        do {
          var object = reader.readObject();
          var person = (PersonInterface) object;
          var id = person.getId();
          output.add(object);
          transactionData.registerRead(id);
        } while (true);
      }
    } catch (EOFException eofException){
      // as expected
      new Log(Read.class.getName()).info(eofException);
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Read.class.getName()).warning(exception);
    }
    return output;
  }
}
