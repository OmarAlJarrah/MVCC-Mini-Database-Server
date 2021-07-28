package com.atypon.files;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ObjectReader implements ReadOperation {

  @Override
  public Object read(File file) {
    try (var objectRead = new ObjectInputStream(
            new FileInputStream(file))) {
      return objectRead.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      new Log(ObjectReader.class.getName()).warning(exception);
    }
    return new NullObject();
  }

  @Override
  public Object readEntity(File file, Object goal) {
    Object output = new NullObject();
    try (var objectRead = new ObjectInputStream(
            new FileInputStream(file))) {
      do {
        output = objectRead.read();
      } while (!output.equals(goal));
    } catch (EOFException eofException) {
      // As expected
    } catch (IOException exception) {
      new Log(ObjectReader.class.getName()).warning(exception);
    }
    if (output.equals(goal)) {
      return output;
    } else {
      return new NullObject();
    }
  }

  @Override
  public ArrayList<Object> readAll(File file) {
    ArrayList<Object> output = new ArrayList<>();
    try (var objectRead = new ObjectInputStream(
            new FileInputStream(file))) {
      do {
        output.add(objectRead.readObject());
      } while (true);
    } catch (EOFException eofException) {
      // As expected
    } catch (IOException | ClassNotFoundException exception) {
      new Log(ObjectReader.class.getName()).warning(exception);
    }
    return output;
  }
}
