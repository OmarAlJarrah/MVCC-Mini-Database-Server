package com.atypon.database;


public class AbortedCommit implements CommitStatus {

  @Override
  public Boolean getStatus() {
    return false;
  }
}
