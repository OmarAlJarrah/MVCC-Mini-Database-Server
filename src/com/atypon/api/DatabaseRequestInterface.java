package com.atypon.api;

import java.io.Serializable;

public interface DatabaseRequestInterface extends Serializable {

  Boolean getResponseExpected();

  String getRequest();

  String getSender();

}
