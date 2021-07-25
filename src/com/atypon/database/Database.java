package com.atypon.database;

import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable, DatabaseInterface {
  private final File DATABASE_FILE;
  private final TransactionDataInterface TRANSACTION_DATA;
  private static final Long serialVersionUID = 5L;
  private Cache cache;

  public Database(File dbFile) {
    DATABASE_FILE = dbFile;
    TRANSACTION_DATA = new TransactionData();
    synchronized (DATABASE_FILE){
      cache = new Cache(DATABASE_FILE);
    }
  }

  public File getDatabaseFile() {
    return DATABASE_FILE;
  }

  @Override
  public TransactionDataInterface getTransactionData() {
    return TRANSACTION_DATA;
  }

  @Override
  public void create(String name, Integer age) {
    synchronized (this.DATABASE_FILE){
      DatabaseCreate createHandler = new Create(DATABASE_FILE, TRANSACTION_DATA);
      createHandler.create(name, age);
    }
  }

  @Override
  public void delete(Integer id) {
    synchronized (this) {
      new Delete(DATABASE_FILE, TRANSACTION_DATA).delete(id);
    }
  }

  @Override
  public PersonInterface read(Integer id) {
    synchronized (this) {
      try {
        Object cacheRead = cache.read(id);
        return (PersonInterface) cacheRead;
      } catch (NullPointerException nullPointerException) {
        DatabaseRead readHandler = new Read(DATABASE_FILE, TRANSACTION_DATA);
        return (PersonInterface) readHandler.read(id);
      }
    }
  }

  @Override
  public List<Person> readAll() {
    synchronized (this) {
      DatabaseRead readHandler = new Read(DATABASE_FILE, TRANSACTION_DATA);
      List<Person> list = new ArrayList<>();
      for (Object obj : readHandler.readAll()) {
        list.add((Person) obj);
      }
      return list;
    }
  }

  @Override
  public void updateName(Integer id, String newValue) {
    synchronized (this) {
      DatabaseUpdate updateHandler = new Update(DATABASE_FILE, TRANSACTION_DATA);
      updateHandler.updateName(id, newValue);
    }
  }

  @Override
  public void updateAge(Integer id, Integer newValue) {
    synchronized (this) {
      DatabaseUpdate updateHandler = new Update(DATABASE_FILE, TRANSACTION_DATA);
      updateHandler.updateAge(id, newValue);
    }
  }
}
