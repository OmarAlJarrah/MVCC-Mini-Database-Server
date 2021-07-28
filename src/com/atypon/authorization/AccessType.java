package com.atypon.authorization;

import java.io.Serializable;

public interface AccessType extends Serializable {
  boolean getAccess();
}
