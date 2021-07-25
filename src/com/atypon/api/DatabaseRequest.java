package com.atypon.api;

import java.io.Serializable;

public class DatabaseRequest implements Serializable, DatabaseRequestInterface {
  private String sender = "";
  private String request = "";
  private Boolean responseExpected = false;


  public DatabaseRequest(String sender, String request) {
    this.sender = sender;
    this.request = request;
  }

  public DatabaseRequest(String sender, String request, Boolean responseExpected) {
    this.sender = sender;
    this.request = request;
    this.responseExpected = responseExpected;
  }

  public void setResponseExpected(Boolean responseExpected) {
    this.responseExpected = responseExpected;
  }

  public String getSender() {
    return sender;
  }

  public String getRequest() {
    return request;
  }

  public Boolean getResponseExpected() {
    return responseExpected;
  }
}
