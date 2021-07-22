package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;
import com.atypon.database.CommitStatus;
import com.atypon.database.Person;
import com.atypon.database.PersonInterface;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Query implements QueryInterface {
  CommunicationChannelInterface channel;
  String sender;
  Socket socket;

  public Query(ClientInterface client) throws IOException, DeniedAccessException {
    channel = new CommunicationChannel(client);
    this.sender = client.getUsername();
  }

  public PersonInterface read(Integer id) {
    Person person;
    String command = "READ " + id;
    var request = new DatabaseRequest(sender, command, true);
    handleRequest(request);
    person = (Person) handleResponse();
    return person;
  }

  public synchronized List<PersonInterface> readAll() {
    List<PersonInterface> list = new ArrayList<>();
    String command = "READ *";
    var request = new DatabaseRequest(sender, command, true);
    handleRequest(request);
    var response =  handleResponse();
    var arr = (List<Object>)response;
    for (Object obj : arr) {
      list.add((Person) obj);
    }
    return list;
  }

  public synchronized void create(String name, Integer age) {
    String command = "CREATE "
            + name
            + " "
            + age;
    var request = new DatabaseRequest(sender, command);
    handleRequest(request);
  }

  public void delete(Integer id) {
    String command = "DELETE " + id;
    var request  = new DatabaseRequest(sender, command);
    handleRequest(request);
  }

  public void updateName(Integer id, String newValue) {
    String command = "UPDATE "
            + id
            + "NAME "
            + newValue;

    var request = new DatabaseRequest(sender, command);
    handleRequest(request);
  }

  public void updateAge(Integer id, Integer newValue) {
    String command = "UPDATE "
            + id
            + "AGE "
            + newValue;

    var request = new DatabaseRequest(sender, command);
    handleRequest(request);
  }

  public synchronized Object commit() {
    String command = "COMMIT";
    var request = new DatabaseRequest(sender, command, true);
    handleRequest(request);
    return handleResponse();
  }

  public Object handleResponse() {
    return channel.readResponse();
  }

  public void handleRequest(DatabaseRequestInterface request) {
    channel.sendRequest(request);

  }

}
