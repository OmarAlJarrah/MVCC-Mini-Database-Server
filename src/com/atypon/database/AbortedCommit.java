package com.atypon.database;

import java.io.Serializable;

public class AbortedCommit implements CommitStatus, Serializable {

  @Override
  public Boolean getStatus() {
    return false;
  }
}
