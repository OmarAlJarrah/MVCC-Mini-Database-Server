package com.atypon.database;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

public class TransactionData implements Serializable, TransactionDataInterface {
  private final HashSet<Integer> readSet = new HashSet<>();
  private final HashSet<Integer> writeSet = new HashSet<>();


  public void registerRead(Integer id) {
    synchronized (this){
      readSet.add(id);
    }
  }

  public void registerWrite(Integer id) {
    synchronized (this) {
      writeSet.add(id);
    }
  }

  public void setWrite(Integer id) {
    synchronized (this){
      writeSet.add(id);
    }
  }

  public Set<Integer> getReadSet() {
    synchronized (this){
      return readSet;
    }
  }

  public Set<Integer> getWriteSet() {
    synchronized (this){
      return writeSet;
    }
  }

  public Boolean checkConflict(TransactionDataInterface other) {
    readSet.retainAll(other.getWriteSet());
    writeSet.retainAll(other.getWriteSet());
    return !readSet.isEmpty() && !writeSet.isEmpty();
  }
}
