package com.atypon.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface ReadOperation {
  Object read(File file);

  Object readEntity(File file, Object goal);

  ArrayList<Object> readAll(File file);

}
