package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;
import java.io.IOException;
import java.net.Socket;

public class Client implements ClientInterface {
  private final String USER_NAME;
  private final String IP;
  ClientConnection clientConnection;

  public Client(final String username, final String IP) {
    this.USER_NAME = username;
    this.IP = IP;
  }

  public void initConnection() throws DeniedAccessException, IOException {
    if (this.clientConnection != null){
      this.clientConnection.getSocket().close();
    }
    this.clientConnection = new ClientConnection(this);
  }

  @Override
  public Socket getSocket() throws DeniedAccessException, IOException {
    initConnection();
    return clientConnection.getSocket();
  }

  @Override
  public String getUsername() {
    return USER_NAME;
  }

  @Override
  public String getIP() {
    return IP;
  }
}
