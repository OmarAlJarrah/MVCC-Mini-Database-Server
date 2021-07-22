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
  private final String  CLIENT_USERNAME;
  private final Integer PORT;
  private final Socket  socket;

  public ConnectionRequest(final String USERNAME, final String IP) throws IOException {
    this.CLIENT_USERNAME = USERNAME;
    this.PORT = 2000;
    this.socket = new Socket(IP, PORT);
  }

  public AccessType login() {
    var loginRequest = new LoginRequest(CLIENT_USERNAME);
    AccessType access = new DeniedAccess();

    try {
      synchronized (FilesManager.ACCESS_FILE) {
        sendLoginRequest(loginRequest);
        access = readLoginResponse();
      }

    } catch (IOException | ClassNotFoundException ioException) {
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

  public AccessType readLoginResponse() throws IOException, ClassNotFoundException {
    var inputStream = new ObjectInputStream(
            socket.getInputStream());
    return (AccessType) inputStream.readObject();
  }

  public Integer getDatabasePort(String ip) throws DeniedAccessException {
    AccessType access = login();
    if (!access.getAccess()){
      throw new DeniedAccessException();
    }
    return 2001;
  }
}
