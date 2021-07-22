package com.atypon.authorization;

import java.io.Serializable;

public class DeniedAccess implements AccessType, Serializable {

  @Override
  public boolean getAccess() {
    return false;
  }
}
