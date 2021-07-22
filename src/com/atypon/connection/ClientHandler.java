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
        var username = request.getSender();
        var database = DatabaseServer.getClientDatabase(username);
      System.out.println("Client " + request.getSender());
        if (Boolean.TRUE.equals(request.getResponseExpected())) {
        System.out.println("TRUE");
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          var translator = new QueryTranslator(database);
          var object = translator.translate(request);
        System.out.println("BEFORE");
          outputStream.writeObject(object);
        System.out.println("AFTER");
        } else {
          new QueryTranslator(database).translate(request);
        }

    } catch (IOException exception) {
      new Log(ClientHandler.class.getName()).
              warning(exception);
    }
  }

}
