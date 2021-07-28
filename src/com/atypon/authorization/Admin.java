package com.atypon.authorization;

import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;
import com.atypon.files.ReadOperation;
import com.atypon.files.WriteOperation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Admin implements AdminInterface {
  private static final File USERS_DATA = Access.getUserData();

  @Override
  public synchronized void  addUser(String username, UserType type) {
    UserInterface user = UserFactory.makeUser(type);
    user.setUsername(username);
    new ObjectWriter().write(USERS_DATA, user);
  }

  @Override
  public synchronized void deleteUser(String username) throws IOException, ClassNotFoundException {
    ReadOperation reader = new ObjectReader();
    ArrayList<Object> list = new ArrayList<>();
    for (Object obj : reader.readAll(USERS_DATA)) {
      if (((UserInterface)obj).login(username)) {
        continue;
      }
      list.add(obj);
    }
    WriteOperation writer = new ObjectWriter();
    writer.writeNewList(USERS_DATA, list);
  }
}
