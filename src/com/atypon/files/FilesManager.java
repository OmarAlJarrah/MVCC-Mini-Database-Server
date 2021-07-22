package com.atypon.files;

import java.io.File;

public class FilesManager {
  public static final File ACCESS_FILE = new File("access.txt");
  public static final File CLIENTS_DATA_FILE = new File("ClientsDataFile.txt");
  public static final File DATABASE_FILE = new File("database.txt");
  public static final  File COMMITS_FILE = new File("USER_COMMITS_FILE.txt");
  public static final File ID_FILE = new File("IDsFile.txt");

  private FilesManager(){}
}
