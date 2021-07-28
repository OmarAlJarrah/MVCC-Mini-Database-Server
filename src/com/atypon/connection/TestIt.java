package com.atypon.connection;

import com.atypon.api.DatabaseApi;
import com.atypon.authorization.Admin;
import com.atypon.authorization.DeniedAccessException;
import com.atypon.database.Person;
import com.atypon.database.PersonInterface;

public class TestIt {
  public static void main(String[] args) throws DeniedAccessException {
    Admin admin = new Admin();
//    admin.addUser();
    DatabaseApi api = new DatabaseApi("Omar", "127.0.0.1");
    for (PersonInterface person: api.readAll()) {
      System.out.println(person.getFirstName());
    }
    api.commit();
  }

}
