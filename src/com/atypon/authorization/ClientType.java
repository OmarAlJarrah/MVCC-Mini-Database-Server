package com.atypon.authorization;

public class ClientType implements UserType {
  @Override
  public String getType() {
    return "Client";
  }
}
