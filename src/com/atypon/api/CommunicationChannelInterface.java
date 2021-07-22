package com.atypon.api;


import java.net.Socket;

public interface CommunicationChannelInterface {
  void sendRequest(DatabaseRequestInterface request) ;

  Object readResponse();

  Socket getSocket();
}
