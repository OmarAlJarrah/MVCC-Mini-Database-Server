package com.atypon.files;

import java.io.File;
import java.util.ArrayList;

public interface WriteOperation {
  void write(File file, Object object);

  void writeList(File file, ArrayList<Object> arrayList);

  void writeNewList(File file, ArrayList<Object> arrayList);

  void discard(File databaseFile);
}
