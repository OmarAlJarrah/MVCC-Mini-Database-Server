package com.atypon.database;

import com.atypon.api.DatabaseRequestInterface;
import com.atypon.connection.Router;
import com.atypon.files.Log;
import com.atypon.files.NullObject;

public class QueryTranslator implements TranslatorInterface {
  Database database;

  public QueryTranslator(Database database){
    this.database = database;
  }

  public synchronized Object translate(DatabaseRequestInterface request) {

    synchronized (database.getDatabaseFile()) {
      var query = request.getRequest();
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
            var sender = request.getSender();
            var clientData = Router.getClientData(sender);

            return new Commit(database, clientData).commit();
          } catch (Exception e) {
            new Log(Database.class.getName()).warning(e);
          }
          break;
      }
      return new NullObject();
    }
  }

  private synchronized Object translateRead(String[] query) {
    Object output;
    if ("*".equals(query[1])) {
      output = database.readAll();
    } else {
      output = database.read(Integer.parseInt(query[1]));
    }
    return output;
  }

  private synchronized void translateDelete(String[] query) {
    database.delete(Integer.parseInt(query[1]));
  }

  private synchronized void translateUpdate(String[] query){
    var id = Integer.parseInt(query[1]);
    var property = query[2];
    var newValue = query[3];

    if ("NAME".equalsIgnoreCase(property)) {
      database.updateName(id, newValue);
    } else if ("AGE".equalsIgnoreCase(property)) {
      database.updateAge(id, Integer.parseInt(newValue));
    } else {
      new Log(QueryTranslator.class.getName()).
              warning(new Exception("Not a property"));
    }
  }

  private synchronized void translateCreate(String[] query){
    var name = query[1];
    var age = Integer.parseInt(query[2]);

    database.create(name, age);
  }
}
