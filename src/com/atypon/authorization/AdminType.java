package com.atypon.authorization;

public class AdminType implements UserType {
  @Override
  public final String getType() {
    return "Admin";
  }
}
