package com.atypon.database;

import java.io.*;
import java.util.Objects;

public class Person implements Serializable, PersonInterface {
  private static final Long serialVersionUID = 1L;
  private String name = null;
  private Integer id = null;
  private int age = 0;

  public Person(){}

  public Person(String name, int age) {
    id = IdFactory.generateId();
    this.age = age;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    var person = (Person) o;
    return age == person.age && Objects.equals(name, person.name) && Objects.equals(id, person.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, age);
  }

  @Override
  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
