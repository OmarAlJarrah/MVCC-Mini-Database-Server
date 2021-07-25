package com.atypon.database;

public class PersonFactory {
  public static PersonInterface makePerson(String firstName, int age) {
    PersonInterface person = new Person();
    person.setFirstName(firstName);
    person.setAge(age);
    person.setId(IdFactory.generateId());
    return person;
  }

  public static PersonInterface makeNullPerson() {
    return new NullPerson();
  }
}
