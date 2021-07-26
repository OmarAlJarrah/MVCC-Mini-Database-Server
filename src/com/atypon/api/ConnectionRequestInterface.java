package com.atypon.api;

import com.atypon.authorization.AccessType;
import com.atypon.authorization.DeniedAccessException;

import java.io.IOException;

public interface ConnectionRequestInterface {
  Integer getDatabasePort(String ip) throws DeniedAccessException;

  AccessType readLoginResponse() throws IOException, ClassNotFoundException;

  void sendLoginRequest(LoginRequestInterface loginRequest) throws IOException;

  AccessType login();
}
