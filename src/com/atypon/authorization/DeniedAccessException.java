package com.atypon.authorization;

public class DeniedAccessException extends Exception {
  public DeniedAccessException(){
    super("Denied Access!");
  }
}
