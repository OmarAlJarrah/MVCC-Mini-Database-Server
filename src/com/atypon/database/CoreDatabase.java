package com.atypon.database;

import com.atypon.files.FilesManager;
import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class CoreDatabase {

  private static final File DATABASE_FILE = FilesManager.DATABASE_FILE;

  private static final  File COMMITS_FILE = FilesManager.COMMITS_FILE;

  private CoreDatabase(){}
  public static synchronized File getNewVersion() {
    File file = new File(("db-"+new Date())+".txt");
    ObjectReader reader = new ObjectReader();
    ObjectWriter writer = new ObjectWriter();
    ArrayList<Object> dataList = reader.readAll(DATABASE_FILE);
    writer.writeNewList(file, dataList);
    return file;
  }

  public static File getDatabaseFile() {
    return DATABASE_FILE;
  }

  public static File getCommitsFile() {
    return COMMITS_FILE;
  }

  public static void commit(File file){
    var list = new ObjectReader().readAll(file);
    new ObjectWriter().writeNewList(DATABASE_FILE, list);
  }
}
