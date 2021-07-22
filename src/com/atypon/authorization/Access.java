package com.atypon.authorization;
import com.atypon.files.FilesManager;
import com.atypon.files.ObjectReader;
import java.io.File;

public class Access {
  private static final File usersData = FilesManager.ACCESS_FILE;

  private Access(){}

  public static synchronized void getAccess(String username) throws DeniedAccessException {
    var reader = new ObjectReader();
    for (Object obj : reader.readAll(usersData)) {
      if (((UserInterface)obj).login(username)) {
        return;
      }
    }
    throw new DeniedAccessException();
  }

  public static File getUsersData(){
    return usersData;
  }
}
