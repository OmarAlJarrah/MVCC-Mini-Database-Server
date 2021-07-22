package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
  private Integer port;

  private String username;
  private String ip;
  private Socket socket;
  public ClientConnection(ClientInterface client) throws DeniedAccessException, IOException {
    this.ip = client.getIp();
    this.username = client.getUsername();
    ConnectionRequestInterface request = new ConnectionRequest(username, ip);
    this.port = request.getDatabasePort(ip);
    this.socket = new Socket(ip, port);
  }

  public Socket getSocket(){
    return this.socket;
  }

  public Integer getPort() {
    return port;
  }
}
