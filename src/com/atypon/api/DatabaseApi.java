package com.atypon.api;


import com.atypon.authorization.DeniedAccessException;
import com.atypon.database.PersonInterface;
import java.io.IOException;
import java.util.List;

public class DatabaseApi {
  private final ClientInterface client;

  public DatabaseApi(String username, String userIp) throws IOException, DeniedAccessException {
    client = new Client(username, userIp);
  }

  public Object read(Integer id) throws IOException, DeniedAccessException {
    return new Query(client).read(id);
  }

  public List<PersonInterface> readAll() throws IOException, DeniedAccessException {
    return new Query(client).readAll();
  }

  public void updateName(Integer id, String newValue) throws IOException, DeniedAccessException {
    new Query(client).updateName(id, newValue);
  }

  public void delete(Integer id) throws IOException, DeniedAccessException {
    new Query(client).delete(id);
  }

  public void updateAge(Integer id, Integer newValue) throws IOException, DeniedAccessException {
    new Query(client).updateAge(id, newValue);
  }

  public synchronized void create(String name, Integer age) throws IOException, DeniedAccessException {
    new Query(client).create(name, age);
  }

  public Object commit() throws IOException, DeniedAccessException {
    return new Query(client).commit();
  }
}
