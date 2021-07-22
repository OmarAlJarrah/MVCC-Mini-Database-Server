package com.atypon.authorization;

import java.io.Serializable;

public class GrantedAccess implements AccessType, Serializable {
  @Override
  public boolean getAccess() {
    return true;
  }
}
