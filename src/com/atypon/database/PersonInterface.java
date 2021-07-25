package com.atypon.database;

public interface PersonInterface {
  Integer getId();

  String getFirstName();

  void setFirstName(String firstName);

  int getAge();

  void setAge(int age);

  void setId(Integer id);
}
