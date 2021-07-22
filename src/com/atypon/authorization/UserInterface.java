package com.atypon.authorization;

public interface UserInterface {
  String getUserId();

  String getAccessType();

  boolean login(String username);

  public String getUsername();

  public void setUsername(String username);
}
