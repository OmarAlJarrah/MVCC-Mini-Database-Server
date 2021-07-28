package com.atypon.database;

import java.util.Date;

public class ClientData implements ClientDataInterface {
  private final String user;
  private final DatabaseInterface database;
  private final Date connectionDate;

  @Override
  public DatabaseInterface getDatabase() {
    return database;
  }

  @Override
  public String getUser() {
    return user;
  }

  public ClientData(String user, DatabaseInterface database){
    this.database = database;
    this.user = user;
    this.connectionDate = new Date();
  }

  @Override
  public Date getConnectionTime() {
    return connectionDate;
  }

}
