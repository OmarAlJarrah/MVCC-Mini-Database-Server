package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;
import com.atypon.files.Log;
import com.atypon.files.NullObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationChannel implements CommunicationChannelInterface {
  private Socket socket;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  public CommunicationChannel(ClientInterface client) throws IOException, DeniedAccessException {
    this.socket = client.getSocket();
  }

  @Override
  public void sendRequest(DatabaseRequestInterface request) {
    try {
      resetOutputStream();
      output.writeObject(request);
    } catch (IOException ioException) {
      new Log(Query.class.getName())
              .warning(ioException);
    }
  }

  @Override
  public Object readResponse() {
    Object object = new NullObject();
    try {
      resetInputStream();
      object = input.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      new Log(Query.class.getName())
              .warning(exception);
    }
    return object;
  }

  @Override
  public Socket getSocket() {
    return socket;
  }

  private void resetInputStream() {
    try {
      input = new ObjectInputStream(socket.getInputStream());
    } catch (IOException ioException) {
      new Log(Query.class.getName())
              .warning(ioException);
    }
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
