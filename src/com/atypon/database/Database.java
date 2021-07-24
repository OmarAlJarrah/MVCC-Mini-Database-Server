package com.atypon.database;

import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
  public File getDatabaseFile() {
    return databaseFile;
  }

  private final File databaseFile;
  private final TransactionDataInterface transactionData;
  private static final Long serialVersionUID = 5L;
  private final Cache cache;


  public TransactionDataInterface getTransactionData() {
    return transactionData;
  }

  public Database(File dbFile){
    databaseFile = dbFile;
    transactionData = new TransactionData();
    synchronized (databaseFile){
      cache = new Cache(databaseFile);
    }
  }


  public void create(String name, Integer age) {
    synchronized (this.databaseFile){
      var createHandler = new Create(databaseFile, transactionData);
      createHandler.create(name, age);
    }
  }

  public void delete(Integer id) {
    synchronized (this) {
      new Delete(databaseFile, transactionData).delete(id);
    }
  }

  public PersonInterface read(Integer id) {
    synchronized (this) {
      try {
        var cacheRead = cache.read(id);
        return (PersonInterface) cacheRead;
      } catch (NullPointerException nullPointerException) {
        var readHandler = new Read(databaseFile, transactionData);
        return (Person) readHandler.read(id);
      }
    }
  }

  public List<Person> readAll() {
    synchronized (this) {
      var readHandler = new Read(databaseFile, transactionData);
      List<Person> list = new ArrayList<>();
      for (Object obj : readHandler.readAll()) {
        list.add((Person) obj);
      }
      return list;
    }
  }


  public void updateName(Integer id, String newValue) {
    synchronized (this) {
      var updateHandler = new Update(databaseFile, transactionData);
      updateHandler.updateName(id, newValue);
    }
  }

  public void updateAge(Integer id, Integer newValue) {
    synchronized (this) {
      var updateHandler = new Update(databaseFile, transactionData);
      updateHandler.updateAge(id, newValue);
    }
  }
}
