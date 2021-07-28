package com.atypon.database;

import java.io.Serializable;

public interface CacheInterface extends Serializable  {
  Object read(Integer id) throws NullPointerException;
}
