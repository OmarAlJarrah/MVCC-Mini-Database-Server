package com.atypon.authorization;

import java.io.IOException;

public interface AdminInterface {
  void addUser(String username, UserType type);

  void deleteUser(String username) throws IOException, ClassNotFoundException;
}
