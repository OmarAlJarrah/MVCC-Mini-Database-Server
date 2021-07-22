package com.atypon.database;

import java.util.Date;

public interface ClientDataInterface {
  Database getDatabase();

  String getUser();

  Date getConnectionTime();
}
