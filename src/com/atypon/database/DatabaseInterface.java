package com.atypon.database;


import java.io.File;
import java.util.List;

public interface DatabaseInterface {
  TransactionDataInterface getTransactionData();


  void create(String name, Integer age);

  void delete(Integer id);

  PersonInterface read(Integer id);

  List<Person> readAll();


  void updateName(Integer id, String newValue);

  void updateAge(Integer id, Integer newValue);

  File getDatabaseFile();
}
