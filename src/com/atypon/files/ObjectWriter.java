package com.atypon.files;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class ObjectWriter implements WriteOperations {
  ArrayList<Object> objectArrayList;

  @Override
  public void write(File file, Object object) {
      try {
        ReadOperations read = new ObjectReader();
        objectArrayList = new ArrayList<>();
        objectArrayList.add(object);
        objectArrayList.addAll(read.readAll(file));
        writeNewList(file, objectArrayList);
      } catch (IOException | ClassNotFoundException exception) {
        new Log(ObjectWriter.class.getName()).warning(exception);
      }

  }

  @Override
  public void writeList(File file, ArrayList<Object> arrayList) {
    try (ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream(file))) {
        ReadOperations read = new ObjectReader();
        objectArrayList = read.readAll(file);
        objectArrayList.addAll(arrayList);
        for (Object obj : objectArrayList) {
          out.writeObject(obj);
        }
      } catch (IOException | ClassNotFoundException exception) {
        new Log(ObjectWriter.class.getName()).warning(exception);
      }
  }

  @Override
  public void writeNewList(File file, ArrayList<Object> arrayList) {
      try (ObjectOutputStream out = new ObjectOutputStream(
                      new FileOutputStream(file))) {
        for (Object obj : arrayList) {
          out.writeObject(obj);
        }
      } catch (IOException ioException) {
        new Log(ObjectWriter.class.getName()).warning(ioException);
      }

  }

  public void copy(File fromFile, File toFile){
    var data = new ObjectReader().readAll(fromFile);
    writeNewList(toFile, data);
  }

  public void discard(File file){
    try {
      Files.delete(file.toPath());
    } catch (IOException ioException) {
      new Log(ObjectWriter.class.getName())
              .warning(ioException);
    }
  }

}
