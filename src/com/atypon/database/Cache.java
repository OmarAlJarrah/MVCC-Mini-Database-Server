package com.atypon.database;

import com.atypon.files.ObjectReader;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Cache implements Serializable {
  private final Map<Integer, PersonInterface> readOnlyCache;

  public Cache(File dbFile) {
    readOnlyCache = new HashMap<>();
    var list = new ObjectReader().readAll(dbFile);
    var size = generateRandomCacheSize(1, list.size()/10);
    for (var i = list.size()-1;size >= 0 && i >= 0; --i, --size){
      var person = (PersonInterface) list.get(i);
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
