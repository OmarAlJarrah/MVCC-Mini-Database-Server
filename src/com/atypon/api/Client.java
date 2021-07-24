package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;
import java.io.IOException;
import java.net.Socket;

public class Client implements ClientInterface {
  private final String userName;
  private final String ip;
  ClientConnection clientConnection;

  public Client(final String username, final String ip) {
    this.userName = username;
    this.ip = ip;
  }

  public void refresh() throws DeniedAccessException, IOException {
    this.clientConnection = new ClientConnection(this);
  }

  public Socket getSocket() throws DeniedAccessException, IOException {
    refresh();
    return clientConnection.getSocket();
  }

  public String getUsername() {
    return userName;
  }

  public String getIp() {
    return ip;
  }
}
