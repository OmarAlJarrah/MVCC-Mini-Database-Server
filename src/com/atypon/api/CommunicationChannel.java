package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;
import com.atypon.files.Log;
import com.atypon.files.NullObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationChannel implements CommunicationChannelInterface {
  Socket socket;

  ObjectInputStream input;
  ObjectOutputStream output;
  public CommunicationChannel(ClientInterface client) throws IOException, DeniedAccessException {
    this.socket = client.getSocket();
  }

  public synchronized void sendRequest(DatabaseRequestInterface request) {
    try {
      //resetOutputStream();
      output=new ObjectOutputStream(socket.getOutputStream());
      output.writeObject(request);
      System.out.println("Sent");
    } catch (IOException ioException) {
      new Log(Query.class.getName())
              .warning(ioException);
    }
  }

  public synchronized Object readResponse() {
    Object object = new NullObject();
    try {
      resetInputStreams();
      object = input.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Query.class.getName())
              .warning(exception);
    }
    return object;
  }

  private void resetInputStreams() {
    try {
      input = new ObjectInputStream(socket.getInputStream());
    } catch (IOException ioException) {
      new Log(Query.class.getName())
              .warning(ioException);
    }
  }

  public Socket getSocket() {
    return socket;
  }

  private void resetOutputStream() {
    try {
      output = new ObjectOutputStream(socket.getOutputStream());
    } catch (IOException ioException) {
      new Log(Query.class.getName())
              .warning(ioException);
    }
  }
}