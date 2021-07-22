package com.atypon.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface ReadOperations {
  Object read(File file) throws IOException, ClassNotFoundException;

  Object readEntity(File file, Object goal);

  ArrayList<Object> readAll(File file) throws ClassNotFoundException, IOException;

}
