package com.atypon.api;

public class RequestResponseHandler {
  private RequestResponseHandler() {}

  public static Object handleResponse(CommunicationChannelInterface channel) {
    return channel.readResponse();
  }

  public static void handleRequest(DatabaseRequestInterface request, CommunicationChannelInterface channel) {
    channel.sendRequest(request);
  }
}
