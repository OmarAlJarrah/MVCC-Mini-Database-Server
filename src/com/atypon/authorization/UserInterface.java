package com.atypon.authorization;

public interface UserInterface {
  String getUSER_ID();

  String getACCESS_TYPE();

  boolean login(String username);

  public String getUsername();

  public void setUsername(String username);
}
