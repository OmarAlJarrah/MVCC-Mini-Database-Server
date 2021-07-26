package com.atypon.database;

import com.atypon.files.ObjectReader;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Cache implements Serializable {
  private static final Long serialVersionUID = 5L;
  private final Map<Integer, PersonInterface> readOnlyCache = new HashMap<>();

  public Cache(File dbFile) {
    List<Object> list = new ObjectReader().readAll(dbFile);
    int size = generateRandomCacheSize(1, list.size()/10).intValue();
    for (int i = list.size()-1;size >= 0 && i >= 0; --i, --size){
      PersonInterface person = (PersonInterface) list.get(i);
      readOnlyCache.put(person.getId(), person);
    }
  }

  public Object read(Integer id) throws NullPointerException {
    if (readOnlyCache.get(id) == null){
      throw new NullPointerException();
    } else {
      return readOnlyCache.get(id);
    }
  }

  private Double generateRandomCacheSize(Integer min, Integer max) {
    var pivot = (max-min+1)+min;
    return Math.floor(Math.random() * pivot);
  }
}
