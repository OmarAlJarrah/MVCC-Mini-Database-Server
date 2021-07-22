package com.atypon.authorization;

import java.io.*;
import java.util.Objects;

public class User implements UserInterface, Serializable {
  private final String userId;
  private final String accessType;

  private String username;

  private static final Long serialVersionUID = 2L;
  public User(String id, String access) {
    userId = id;
    accessType = access;
  }

  @Override
  public String getUserId() {
    return userId;
  }

  @Override
  public String getAccessType() {
    return accessType;
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
    return Objects.equals(userId, user.userId) && Objects.equals(accessType, user.accessType);
  }

  @Override
  public boolean login(String username) {
    return username.equals(this.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, accessType);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
