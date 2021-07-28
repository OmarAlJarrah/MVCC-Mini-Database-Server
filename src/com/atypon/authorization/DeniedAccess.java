package com.atypon.authorization;


public class DeniedAccess implements AccessType {

  @Override
  public final boolean getAccess() {
    return false;
  }
}
