package com.atypon.authorization;

import java.io.*;
import java.util.Objects;

public class User implements UserInterface, Serializable {
  private final String USER_ID;
  private final String ACCESS_TYPE;

  private String username;

  private static final Long serialVersionUID = 2L;
  public User(String id, String access) {
    USER_ID = id;
    ACCESS_TYPE = access;
  }

  @Override
  public String getUSER_ID() {
    return USER_ID;
  }

  @Override
  public String getACCESS_TYPE() {
    return ACCESS_TYPE;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    var user = (User) o;
    return Objects.equals(USER_ID, user.USER_ID) && Objects.equals(ACCESS_TYPE, user.ACCESS_TYPE);
  }

  @Override
  public boolean login(String username) {
    return username.equals(this.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(USER_ID, ACCESS_TYPE);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
