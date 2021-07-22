package com.atypon.files;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
  private final Logger LOGGER;

  public Log(String className) {
    LOGGER = Logger.getLogger(className);
  }

  public void warning(Exception exception){
    LOGGER.setLevel(Level.WARNING);
    var exceptionString = exception.toString();
    LOGGER.log(LOGGER.getLevel(), exceptionString);
  }

  public void info(Exception exception){
    LOGGER.setLevel(Level.INFO);
    var exceptionString = exception.toString();
    LOGGER.log(LOGGER.getLevel(), exceptionString);
  }
}
