package com.atypon.database;

import com.atypon.files.Log;
import com.atypon.files.ObjectReader;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class Create implements DatabaseCreate {
  File file;
  TransactionDataInterface transactionData;

  public Create(File file, TransactionDataInterface transactionData){
    this.transactionData = transactionData;
    this.file = file;
  }

  @Override
  public Object create(String name, Integer age) {
    var list = new ObjectReader().readAll(file);
    var newPerson = new Person(name, age);
    list.add(newPerson);
    System.out.println(list.size());

    try (var writer = new ObjectOutputStream(
            new FileOutputStream(file))) {
      synchronized (this) {
        for (Object object : list) {
          writer.writeObject(object);
        }
      }
    } catch (IOException ioException) {
      new Log(Create.class.getName()).
              warning(ioException);
    }
    synchronized (this) {
      transactionData.registerWrite(newPerson.getId());
    }

    return newPerson;
  }
}
