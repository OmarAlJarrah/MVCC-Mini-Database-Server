package com.atypon.api;


import com.atypon.authorization.DeniedAccessException;
import com.atypon.database.PersonInterface;
import java.util.List;

public class DatabaseApi {
  private final ClientInterface CLIENT;

  public DatabaseApi(String username, String userIp) {
    CLIENT = new Client(username, userIp);
  }

  public Object read(Integer id) throws DeniedAccessException {
    return new Query(CLIENT).read(id);
  }

  public List<PersonInterface> readAll() throws  DeniedAccessException {
    return new Query(CLIENT).readAll();
  }

  public void updateName(Integer id, String newValue) throws DeniedAccessException {
    new Query(CLIENT).updateName(id, newValue);
  }

  public void delete(Integer id) throws DeniedAccessException {
    new Query(CLIENT).delete(id);
  }

  public void updateAge(Integer id, Integer newValue) throws DeniedAccessException {
    new Query(CLIENT).updateAge(id, newValue);
  }

  public void create(String name, Integer age) throws DeniedAccessException {
    new Query(CLIENT).create(name, age);
  }

  public Object commit() throws DeniedAccessException {
    return new Query(CLIENT).commit();
  }
}
