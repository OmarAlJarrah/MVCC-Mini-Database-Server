package com.atypon.database;

import java.util.Set;

public interface TransactionDataInterface {
  void registerRead(Integer id);

  void registerWrite(Integer id);

  Set<Integer> getReadSet();

  Set<Integer> getWriteSet();

  Boolean checkConflict(TransactionDataInterface other);
}
