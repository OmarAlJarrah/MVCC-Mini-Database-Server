package com.atypon.database;

public interface DatabaseUpdate {
  void updateName(Integer id, String newValue);

  void updateAge(Integer id, Integer newValue);
}
