package com.atypon.database;

import com.atypon.api.DatabaseRequestInterface;
import com.atypon.connection.AccessLayer;
import com.atypon.files.Log;
import com.atypon.files.NullObject;

import java.io.File;

public class QueryTranslator implements TranslatorInterface {
  DatabaseInterface database;

  public QueryTranslator(DatabaseInterface database){
    this.database = database;
  }

  @Override
  public synchronized Object translate(DatabaseRequestInterface request) {

    synchronized (database.getDatabaseFile()) {
      String query = request.getRequest();
      String[] splitQuery = query.split(" ");
      switch (splitQuery[0]) {
        case "READ":
          return translateRead(splitQuery);

        case "DELETE":
          translateDelete(splitQuery);
          break;

        case "UPDATE":
          translateUpdate(splitQuery);
          break;

        case "CREATE":
          translateCreate(splitQuery);
          break;

        case "COMMIT":
          try {
            String sender = request.getSender();
            ClientDataInterface clientData = AccessLayer.getClientData(sender);
            return new Commit(database, clientData).commit();
          } catch (Exception e) {
            new Log(Database.class.getName()).warning(e);
          }
          break;
      }
      return new NullObject();
    }
  }

  private Object translateRead(String[] query) {
    Object output;
    if ("*".equals(query[1])) {
      output = database.readAll();
    } else {
      output = database.read(Integer.parseInt(query[1]));
    }
    return output;
  }

  private void translateDelete(String[] query) {
    database.delete(Integer.parseInt(query[1]));
  }

  private void translateUpdate(String[] query){
    Integer id = Integer.parseInt(query[1]);
    String property = query[2];
    String newValue = query[3];

    if ("NAME".equalsIgnoreCase(property)) {
      database.updateName(id, newValue);
    } else if ("AGE".equalsIgnoreCase(property)) {
      database.updateAge(id, Integer.parseInt(newValue));
    } else {
      new Log(QueryTranslator.class.getName()).
              warning(new Exception("Not a property"));
    }
  }

  private void translateCreate(String[] query){
    String name = query[1];
    Integer age = Integer.parseInt(query[2]);

    database.create(name, age);
  }
}
