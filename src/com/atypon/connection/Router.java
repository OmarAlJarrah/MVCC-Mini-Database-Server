package com.atypon.connection;
import com.atypon.api.LoginRequest;
import com.atypon.authorization.AccessType;
import com.atypon.authorization.User;
import com.atypon.database.ClientData;
import com.atypon.database.CoreDatabase;
import com.atypon.database.Database;
import com.atypon.files.FilesManager;
import com.atypon.files.Log;
import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Router {

  private static ServerSocket           routerServerSocket;
  private static Socket                 routerSocket;
  private static final int              PORT = 2000;
  private static final File             CLIENTS_FILE = FilesManager.CLIENTS_DATA_FILE;
  private static final File             USER_COMMIT_HISTORY_FILE = FilesManager.COMMITS_FILE;

  static {
    try {
      routerServerSocket = new ServerSocket(PORT);
    } catch (IOException ioException) {
      new Log(Router.class.getName()).
              warning(ioException);
    }
  }

  private Router() {
  }

  public static void main(String[] args) {
    while (true) {
      try {
        routerSocket = routerServerSocket.accept();
        AccessType access = checkAccess();
        new ObjectOutputStream(routerSocket.getOutputStream())
                .writeObject(access);
      } catch (IOException ioException) {
        new Log(Router.class.getName()).
                warning(ioException);
      }
    }
  }

  private static synchronized AccessType checkAccess(){
    var login = readLoginRequest();
    var access = ServerAccess.checkAccess(login);
    if (access.getAccess()){
      registerClient(access, login);
    }
    return access;
  }

  public static synchronized LoginRequest readLoginRequest(){
    var loginRequest = new LoginRequest("");
    try {
      var in = new ObjectInputStream(routerSocket.getInputStream());
      loginRequest = (LoginRequest) in.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      new Log(ServerAccess.class.getName()).warning(exception);
    }
    return loginRequest;
  }


  private static synchronized void registerClient(AccessType access, LoginRequest loginRequest){
    if (access.getAccess()) {
      var user = loginRequest.getUsername();
      if (!checkRegistrationStatus(user)){
        var writer = new ObjectWriter();
        var db = new Database(CoreDatabase.getNewVersion());
        var clientData = new ClientData(user, db);
        writer.write(CLIENTS_FILE, clientData);
      }
    }
  }

  private static synchronized boolean checkRegistrationStatus(String user){
    var reader = new ObjectReader();
    for (Object object: reader.readAll(CLIENTS_FILE)) {
      ClientData data = (ClientData) object;
      if (data.getUser().equals(user)) {
        return true;
      }
    } return false;
  }

  public static File getUserCommitHistoryFile() {
    return USER_COMMIT_HISTORY_FILE;
  }

  public static File getClientsFile() {
    return CLIENTS_FILE;
  }

  public static ClientData getClientData(String username) {
    var reader = new ObjectReader();
    var list = reader.readAll(CLIENTS_FILE);
    for (Object object : list) {
      var clientData = (ClientData) object;
      if (clientData.getUser().equals(username)){
        return clientData;
      }
    }
    return null;
  }
}
