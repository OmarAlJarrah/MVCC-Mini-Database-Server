package com.atypon.connection;

import com.atypon.api.DatabaseRequestInterface;
import com.atypon.database.QueryTranslator;
import com.atypon.files.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
  DatabaseRequestInterface request;
  ObjectOutputStream outputStream;
  Socket  socket;


  public ClientHandler(DatabaseRequestInterface request, Socket socket) {
    this.request = request;
    this.socket = socket;
  }

  public void run() {
    try {
        String userName = request.getSender();
        var database = DatabaseServer.getClientDatabase(userName);
        if (Boolean.TRUE.equals(request.getResponseExpected())) {
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          var translator = new QueryTranslator(database);
          var object = translator.translate(request);
          outputStream.writeObject(object);
        } else {
          new QueryTranslator(database).translate(request);
        }
    } catch (IOException exception) {
      new Log(ClientHandler.class.getName()).
              warning(exception);
    }
  }

}
