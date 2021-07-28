package com.atypon.database;

import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;
import com.atypon.files.WriteOperation;
import java.io.File;
import java.util.ArrayList;

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
    WriteOperation writer = new ObjectWriter();
    writer.writeNewList(file, list);
    transactionData.registerWrite(newPerson.getId());
    return newPerson;
  }
}
