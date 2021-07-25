package com.atypon.authorization;

import java.io.Serializable;

public class DeniedAccess implements AccessType, Serializable {

  @Override
  public final boolean getAccess() {
    return false;
  }
}
