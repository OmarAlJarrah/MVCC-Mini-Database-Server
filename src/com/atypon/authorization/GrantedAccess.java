package com.atypon.authorization;


public class GrantedAccess implements AccessType {
  @Override
  public boolean getAccess() {
    return true;
  }
}
