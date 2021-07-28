package com.atypon.authorization;

import java.io.Serializable;

public interface UserInterface extends Serializable {
  String getUSER_ID();

  String getACCESS_TYPE();

  boolean login(String username);

  public String getUsername();

  public void setUsername(String username);
}
