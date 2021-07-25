package com.atypon.database;

import com.atypon.files.Log;
import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Create implements DatabaseCreate {
  File file;
  TransactionDataInterface transactionData;

  public Create(File file, TransactionDataInterface transactionData){
    this.transactionData = transactionData;
    this.file = file;
  }

  @Override
  public Object create(String name, Integer age) {
    ArrayList<Object> list = new ObjectReader().readAll(file);
    PersonInterface newPerson = PersonFactory.makePerson(name, age);
    list.add(newPerson);
    synchronized (this) {
      ObjectWriter writer = new ObjectWriter();
      writer.writeNewList(file, list);
    }
    synchronized (this) {
      transactionData.registerWrite(newPerson.getId());
    }

    return newPerson;
  }
}
