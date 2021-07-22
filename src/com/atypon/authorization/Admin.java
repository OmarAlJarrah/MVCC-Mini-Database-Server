package com.atypon.authorization;

import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;
import com.atypon.files.ReadOperations;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Admin implements AdminInterface {
  private static final File usersData = Access.getUsersData();

  @Override
  public synchronized void  addUser(String username, UserType type) {
    UserInterface user = UserFactory.makeUser(type);
    user.setUsername(username);
    new ObjectWriter().write(usersData, user);
  }

  @Override
  public synchronized void deleteUser(String username) throws IOException, ClassNotFoundException {
    ReadOperations reader = new ObjectReader();
    ArrayList<Object> list = new ArrayList<>();
    for (Object obj : reader.readAll(usersData)) {
      if (((UserInterface)obj).login(username)) {
        continue;
      }
      list.add(obj);
    }
    var writer = new ObjectWriter();
    writer.writeNewList(usersData, list);
  }
}
