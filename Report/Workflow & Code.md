# Workflow Sample

  + When a client imports the ```DatabaseApi```, they create a new instance of the class passing their ```username``` & ```ipAddress``` as a parameter, 
      creating a new instance will configure the connection with the server.
      ```
      public DatabaseApi(String username, String userIp) throws IOException, DeniedAccessException {
        client = new Client(username, userIp);
        client.connect();
      }
      ```
  
  + The ```connect()``` method invoked by the client instance will create a new ```ConnectionRequest``` instance, then the ```getDatabasePort(ip)```
      will handle requesting access back the scene.
    ```
    public void connect() throws DeniedAccessException, IOException {
      ConnectionRequestInterface request = new ConnectionRequest(USERNAME, ip);
      this.socket = new Socket(ip, request.getDatabasePort(ip));
    }
    ```
    
  + Whenever the client invoke any of the ```DatabaseApi``` methods, it will create a new ```Query``` instance, this query instance
      will create a new ```CommunicationChannel``` that is responsible for sending reqests & reading responses.
      
  + When the client passes both the routing layer & access identifier, his version of the database is created and a new thread will be created for each
      request, all their queries will be sent to the ```QueryTranslator``` by the ```ClientHandler```, that is responsible for translating the request.
      ```
      translator = new QueryTranslator(database);
      translator.translate(request);
      ```
      
  + Once the client is done with his queries, they will invoker the ```commit``` method, that will work as mentioned before.
      
      
