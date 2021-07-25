package com.atypon.authorization;
import com.atypon.files.FilesManager;
import com.atypon.files.ObjectReader;
import java.io.File;

public class Access {
  private static final File USER_DATA = FilesManager.ACCESS_FILE;

  private Access(){}

  public static synchronized void getAccess(String username) throws DeniedAccessException {
    var reader = new ObjectReader();
    for (Object obj : reader.readAll(USER_DATA)) {
      if (((UserInterface)obj).login(username)) {
        return;
      }
    }
    throw new DeniedAccessException();
  }

  public static File getUserData(){
    return USER_DATA;
  }
}
