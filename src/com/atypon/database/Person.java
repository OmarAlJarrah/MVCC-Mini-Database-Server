package com.atypon.database;

import java.io.*;
import java.util.Objects;

public class Person implements Serializable, PersonInterface {
  private static final Long serialVersionUID = 1L;
  private String firstName = "";
  private int id = 0;
  private int age = 0;

  public Person() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    var person = (Person) o;
    return age == person.age && Objects.equals(firstName, person.firstName) && Objects.equals(id, person.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, id, age);
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public int getAge() {
    return age;
  }

  @Override
  public void setAge(int age) {
    this.age = age;
  }
}
