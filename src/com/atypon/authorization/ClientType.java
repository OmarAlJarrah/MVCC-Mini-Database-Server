package com.atypon.authorization;

public class ClientType implements UserType {
  @Override
  public final String getType() {
    return "Client";
  }
}
