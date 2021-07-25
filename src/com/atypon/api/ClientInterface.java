package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;

import java.io.IOException;
import java.net.Socket;

public interface ClientInterface {

  Socket getSocket() throws DeniedAccessException, IOException;

  String getUsername();

  String getIP();
}
