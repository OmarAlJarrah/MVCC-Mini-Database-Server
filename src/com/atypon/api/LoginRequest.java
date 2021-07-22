package com.atypon.api;

import java.io.Serializable;

public class LoginRequest implements Serializable, LoginRequestInterface {
  String username;

  public LoginRequest(String username){
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
