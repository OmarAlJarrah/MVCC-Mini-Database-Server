package com.atypon.database;

import java.util.List;

public interface DatabaseRead {
  Object read(Integer id);

  List readAll();
}
