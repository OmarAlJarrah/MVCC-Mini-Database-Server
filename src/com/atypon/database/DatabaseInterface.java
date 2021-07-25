package com.atypon.database;


import java.util.List;

public interface DatabaseInterface {
  public TransactionDataInterface getTransactionData();


  public void create(String name, Integer age);

  public void delete(Integer id);

  public PersonInterface read(Integer id);

  public List<Person> readAll();


  public void updateName(Integer id, String newValue);

  public void updateAge(Integer id, Integer newValue);
}
