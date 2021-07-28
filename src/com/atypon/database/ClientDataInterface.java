package com.atypon.database;

import java.io.Serializable;
import java.util.Date;

public interface ClientDataInterface extends Serializable {
  DatabaseInterface getDatabase();

  String getUser();

  Date getConnectionTime();
}
