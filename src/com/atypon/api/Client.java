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

  public void refresh() throws DeniedAccessException, IOException {
    this.clientConnection = new ClientConnection(this);
  }

  public Socket getSocket() throws DeniedAccessException, IOException {
    refresh();
    return clientConnection.getSOCKET();
  }

  public String getUsername() {
    return USER_NAME;
  }

  public String getIP() {
    return IP;
  }
}
