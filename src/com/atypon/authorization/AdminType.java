package com.atypon.authorization;

public class AdminType implements UserType {
  @Override
  public String getType() {
    return "Admin";
  }
}
