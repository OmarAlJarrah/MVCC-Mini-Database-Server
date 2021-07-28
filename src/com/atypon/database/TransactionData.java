package com.atypon.database;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

public class TransactionData implements Serializable, TransactionDataInterface {
  private final HashSet<Integer> READ_SET = new HashSet<>();
  private final HashSet<Integer> WRITE_SET = new HashSet<>();


  @Override
  public void registerRead(Integer id) {
      READ_SET.add(id);
  }

  @Override
  public void registerWrite(Integer id) {
      WRITE_SET.add(id);
  }

  @Override
  public Set<Integer> getReadSet() {
      return READ_SET;
  }

  @Override
  public Set<Integer> getWriteSet() {
      return WRITE_SET;
  }

  @Override
  public Boolean checkConflict(TransactionDataInterface other) {
    READ_SET.retainAll(other.getWriteSet());
    WRITE_SET.retainAll(other.getWriteSet());
    return !READ_SET.isEmpty() && !WRITE_SET.isEmpty();
  }
}
