# Connection

### Client Side

+ In order for the client to establish connection with the server, they will send a connection request of type ```ConnectionRequest``` that should be
    processed at the routing layer, once they get access, they will open a new socket for connectin with the server, then the client is registered
    on the server as an ongoing connection.
    
+ Once a user is connected to the server, in order to do any of the CRUD operations he has to use the provided API ```DatabaseApi```, that for each
    CRUD operation, there will be a new request of type ```DatabaseRequest``` that includes the data of the requested operation sent to the server,
    each request opens a new socket at the client side while the old socket is destroyed.
    
+ Each request sent or response recieved at the client side is handled through a special channel of type ```CommunicationChannel```, the communication
    channel handles creating sockets, sending new requests & reading server responses. 
    
+ Any CRUD operation made through the API, will make a new object of type ```Query``` that will generate the right action request, then send it through
    the communication channel to the server, the ```Query``` class works as backend for the ```DatabaseApi```.

    
### Server Side

+ Once the client pass the routing layer & the access check, each query he sends is captured by the server in the main loop, and the request gets
    read inorder to be processed.
    ```
    Main Loop {
      socket = serverSocket.accept();
      reader = inputStream(socket.getInputStream());
      request = inputStream.readObject();
    }
    ```
    
+ After the request is read & is ready to be processed, the server will start a new thread to process the request as each request is processed
    independently, then the server lets the ```ClientHandler``` class process the request.
    ```
    ---request got read---
    new Thread(
              new ClientHandler(request, socket)
      ).start();
    ```
    
+ In case a response by the server is expected, the ```ClientHandler``` class will handle sending the response through the server socket.
    ```
    if (responseExpected) {
          outputStream = OutputStream(socket.getOutputStream());
          ---Processed the request---
          outputStream.writeObject(response);
     }
    ```
