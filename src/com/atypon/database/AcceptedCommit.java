package com.atypon.database;

import java.io.Serializable;

public class AcceptedCommit implements CommitStatus, Serializable {

  @Override
  public Boolean getStatus() {
    return true;
  }
}
