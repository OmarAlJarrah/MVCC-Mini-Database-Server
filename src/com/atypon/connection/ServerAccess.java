package com.atypon.connection;

import com.atypon.api.LoginRequest;
import com.atypon.authorization.*;
import com.atypon.files.Log;



public class ServerAccess {

  private ServerAccess(){}

  public static AccessType checkAccess (LoginRequest loginRequest){
    AccessType output;
    try{
      Access.getAccess(loginRequest.getUsername());
      output = new GrantedAccess();
    } catch (DeniedAccessException deniedAccessException) {
      output = new DeniedAccess();
      new Log(ServerAccess.class.getName()).
              warning(deniedAccessException);
    }
    return output;
  }
}
