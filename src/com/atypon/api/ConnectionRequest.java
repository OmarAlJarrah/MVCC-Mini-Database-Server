package com.atypon.api;

import com.atypon.authorization.AccessType;
import com.atypon.authorization.DeniedAccess;
import com.atypon.authorization.DeniedAccessException;
import com.atypon.files.FilesManager;
import com.atypon.files.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionRequest implements ConnectionRequestInterface {
  private final String clientUsername;
  private final Integer port;
  private final Socket  socket;

  public ConnectionRequest(final String USERNAME, final String IP) throws IOException {
    this.clientUsername = USERNAME;
    this.port = 2000;
    this.socket = new Socket(IP, port);
  }

  public AccessType login() {
    var loginRequest = new LoginRequest(clientUsername);
    AccessType access = new DeniedAccess();

    try {
      synchronized (FilesManager.ACCESS_FILE) {
        sendLoginRequest(loginRequest);
        access = readLoginResponse();
      }

    } catch (IOException ioException) {
      new Log(ConnectionRequest.class.getName()).
              warning(ioException);
    }
    return access;
  }

  public void sendLoginRequest(LoginRequest loginRequest) throws IOException {
    var outputStream = new ObjectOutputStream(
            socket.getOutputStream());
    outputStream.writeObject(loginRequest);
    outputStream.flush();
  }

  public AccessType readLoginResponse()  {
    ObjectInputStream inputStream = null;
    try {
      inputStream = new ObjectInputStream(
              socket.getInputStream());
      return (AccessType) inputStream.readObject();
    } catch (IOException | ClassNotFoundException ioException) {
      ioException.printStackTrace();
    }
    return null;
  }

  public Integer getDatabasePort(String ip) throws DeniedAccessException {
    AccessType access = login();
    if (!access.getAccess()){
      throw new DeniedAccessException();
    }
    return 2001;
  }
}
