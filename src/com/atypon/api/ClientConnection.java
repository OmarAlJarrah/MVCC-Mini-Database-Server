package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
  private Integer PORT;
  private String USER_NAME;
  private String IP;
  private Socket SOCKET;

  public ClientConnection(ClientInterface client) throws DeniedAccessException, IOException {
    this.IP = client.getIP();
    this.USER_NAME = client.getUsername();
    ConnectionRequestInterface request = new ConnectionRequest(USER_NAME, IP);
    this.PORT = request.getDatabasePort(IP);
    this.SOCKET = new Socket(IP, PORT);
  }

  public Socket getSOCKET() {
    return this.SOCKET;
  }

}
