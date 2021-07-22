package com.atypon.database;

import java.io.Serializable;
import java.util.Date;

public class ClientData implements Serializable, ClientDataInterface {
  private final String user;
  private final Database database;
  private final Date connectionDate;

  public Database getDatabase() {
    return database;
  }


  public String getUser() {
    return user;
  }

  public ClientData(String user, Database database){
    this.database = database;
    this.user = user;
    this.connectionDate = new Date();
  }

  public Date getConnectionTime() {
    return connectionDate;
  }

}
