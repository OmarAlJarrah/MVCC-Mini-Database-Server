package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;
import com.atypon.database.Person;
import com.atypon.database.PersonInterface;
import com.atypon.files.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Query implements QueryInterface {
  CommunicationChannelInterface channel;
  private String sender;

  public Query(ClientInterface client) throws DeniedAccessException {
    try {
      channel = new CommunicationChannel(client);
      this.sender = client.getUsername();
    } catch (IOException ioException) {
      new Log(Query.class.getName())
              .warning(ioException);
    }
  }

  @Override
  public PersonInterface read(Integer id) {
    Person person;
    String command = "READ " + id;
    DatabaseRequestInterface request = new DatabaseRequest(sender, command, true);
    RequestResponseHandler.handleRequest(request, channel);
    person = (Person) RequestResponseHandler.handleResponse(channel);
    return person;
  }

  @Override
  public List<PersonInterface> readAll() {
    List<PersonInterface> list = new ArrayList<>();
    String command = "READ *";
    DatabaseRequestInterface request = new DatabaseRequest(sender, command, true);
    RequestResponseHandler.handleRequest(request, channel);
    Object response =  RequestResponseHandler.handleResponse(channel);
    List<Object> arr = (List<Object>) response;
    for (Object obj : arr) {
      list.add((Person) obj);
    }
    return list;
  }

  @Override
  public void create(String name, Integer age) {
    String command = "CREATE "
            + name
            + " "
            + age;
    var request = new DatabaseRequest(sender, command);
    RequestResponseHandler.handleRequest(request, channel);
  }

  @Override
  public void delete(Integer id) {
    String command = "DELETE " + id;
    DatabaseRequestInterface request  = new DatabaseRequest(sender, command);
    RequestResponseHandler.handleRequest(request, channel);
  }

  @Override
  public void updateName(Integer id, String newValue) {
    String command = "UPDATE "
            + id
            + " NAME "
            + newValue;

    DatabaseRequestInterface request = new DatabaseRequest(sender, command);
    RequestResponseHandler.handleRequest(request, channel);
  }

  @Override
  public void updateAge(Integer id, Integer newValue) {
    String command = "UPDATE "
            + id
            + " AGE "
            + newValue;

    DatabaseRequestInterface request = new DatabaseRequest(sender, command);
    RequestResponseHandler.handleRequest(request, channel);
  }

  @Override
  public Object commit() {
    String command = "COMMIT";
    DatabaseRequestInterface request = new DatabaseRequest(sender, command, true);
    RequestResponseHandler.handleRequest(request, channel);
    return RequestResponseHandler.handleResponse(channel);
  }

}
