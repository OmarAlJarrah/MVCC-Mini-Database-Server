package com.atypon.api;

import java.io.Serializable;

public class DatabaseRequest implements Serializable, DatabaseRequestInterface {
  private final String SENDER;
  private final String REQUEST;
  private Boolean responseExpected = false;


  public DatabaseRequest(String sender, String request) {
    this.SENDER = sender;
    this.REQUEST = request;
  }

  public DatabaseRequest(String sender, String request, Boolean responseExpected) {
    this.SENDER = sender;
    this.REQUEST = request;
    this.responseExpected = responseExpected;
  }

  public void setResponseExpected(Boolean responseExpected) {
    this.responseExpected = responseExpected;
  }

  @Override
  public String getSender() {
    return SENDER;
  }

  @Override
  public String getRequest() {
    return REQUEST;
  }

  @Override
  public Boolean getResponseExpected() {
    return responseExpected;
  }
}
