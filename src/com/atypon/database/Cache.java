package com.atypon.database;

import com.atypon.files.ObjectReader;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Cache implements Serializable {
  private final AtomicReference<Map<Integer, PersonInterface>> readOnlyCache = new AtomicReference<>();

  public Cache(File dbFile) {
    List<Object> list = new ObjectReader().readAll(dbFile);
    int size = generateRandomCacheSize(1, list.size()/10).intValue();
    for (int i = list.size()-1;size >= 0 && i >= 0; --i, --size){
      var person = (PersonInterface) list.get(i);
      readOnlyCache.get().put(person.getId(), person);
    }
  }

  public Object read(Integer id) throws NullPointerException {
    if (readOnlyCache.get().get(id) == null){
      throw new NullPointerException();
    } else {
      return readOnlyCache.get().get(id);
    }
  }

  private Double generateRandomCacheSize(Integer min, Integer max) {
    var pivot = (max-min+1)+min;
    return Math.floor(Math.random() * pivot);
  }
}
