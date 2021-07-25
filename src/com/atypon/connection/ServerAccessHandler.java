package com.atypon.connection;

import com.atypon.api.LoginRequest;
import com.atypon.authorization.*;
import com.atypon.files.Log;



public class ServerAccessHandler {

  private ServerAccessHandler(){}

  public static AccessType checkAccess (LoginRequest loginRequest){
    AccessType output;
    try{
      Access.getAccess(loginRequest.getUserName());
      output = new GrantedAccess();
    } catch (DeniedAccessException deniedAccessException) {
      output = new DeniedAccess();
      new Log(ServerAccessHandler.class.getName()).
              warning(deniedAccessException);
    }
    return output;
  }
}
