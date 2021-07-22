package com.atypon.database;

public class IdFactory {
  private IdFactory(){}

  public static Integer generateId(){
    return new Id().generateId();
  }
}
