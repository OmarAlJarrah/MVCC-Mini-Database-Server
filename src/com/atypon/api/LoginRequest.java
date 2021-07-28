package com.atypon.api;

public class LoginRequest implements  LoginRequestInterface {
  private final String USER_NAME;

  public LoginRequest(String USER_NAME) {
    this.USER_NAME = USER_NAME;
  }

  @Override
  public String getUserName() {
    return USER_NAME;
  }
}
