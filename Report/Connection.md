# Connection

### Connection
  + Connection describes a process in which two or more computers or devices transfer data, instructions, and information. Some communications
      involve cables and wires; others are sent wirelessly through the air.
      
  + In our project, we have applied a ```Client - Server``` model, that is a distributed application structure that partitions tasks or workloads
      between the providers of a resource or service, called servers, and service requesters, called clients.
      
  + Often clients and servers communicate over a computer network on separate hardware, but both client and server may reside in the same system.
      A server host runs one or more server programs, which share their resources with clients. A client usually does not share any of its resources,
      but it requests content or service from a server. Clients, therefore, initiate communication sessions with servers, which await incoming requests.
      
  + The "client-server" characteristic describes the relationship of cooperating programs in an application. The server component provides a function
      or service to one or many clients, which initiate requests for such services. Servers are classified by the services they provide. For example,
      a web server serves web pages and a file server serves computer files. A shared resource may be any of the server computer's software and 
      electronic components, from programs and data to processors and storage devices. The sharing of resources of a server constitutes a service.
      
  ![image](https://user.oc-static.com/upload/2020/04/28/15880577088892_15874712483517_export.png)
      
  + Clients and servers exchange messages in a request–response messaging pattern. The client sends a request, and the server returns a response.
      This exchange of messages is an example of inter-process communication. To communicate, the computers must have a common language, and they
      must follow rules so that both the client and the server know what to expect. The language and rules of communication are defined in
      a communications protocol. All protocols operate in the application layer. The application layer protocol defines the basic patterns of the
      dialogue. To formalize the data exchange even further, the server may implement an application programming interface (API).
    
    ![image](https://bytenbit.com/wp-content/uploads/2019/09/Resful-API-cycle-768x206.png)
    
    
    
### What did we use?
  + We use Sockets to create a ```Client - Server``` enviroment, where both client & server use their sockets to communicate with each other, 
      a server has a port in which it listens to clients requests on it, a client sends a request to that port, then they are connected, this
      mechanizm is provided by both the ```ServerSocket``` for the server side, ```Socket``` for the client side, in which both tools are provided
      by the ```java.net``` API.
      
  + A ```ServerSocket``` job is to listen to requests, while in order to send & recieve data with other sockets, it creates a ```Socket```
      object automatically once a request is accepted, that is configures to communicate with the socket requesting connection. The actual work 
      of the server socket is performed by an instance of the SocketImpl class. An application can change the socket factory that creates the
      socket implementation to configure itself to create sockets appropriate to the local firewall.
  
    ![image](https://codethat.files.wordpress.com/2010/01/scheme.png)


### Client Side

  + In order for the client to establish connection with the server, they will send a connection request of type ```ConnectionRequest``` that should be
      which in place will configure ask for authorization from the access layer by sending a ```LoginRequest```.

  + Once they get access, they will open a new socket for connectin with the server, then the client is registered on the server as an ongoing connection.

  ![image](https://i1.wp.com/css-tricks.com/wp-content/uploads/2020/03/oauth-code-grant-flow.png?resize=1024%2C944&ssl=1)

  + Once a user is connected to the server, in order to do any of the CRUD operations he has to use the provided interface ```DatabaseApi```, that for each
      CRUD operation, there will be a new request of type ```DatabaseRequest``` that includes the data of the requested operation sent to the server,
      each request opens a new socket at the client side while the old socket (if any exist) is closed.

    ```
    function initConnection() {
      if (clientConnection != null) {
        clientConnection.getSocket().close();
      }
      clientConnection = new ClientConnection(this);
    }
    ```


  + Each request sent or response recieved at the client side is handled using the ```RequestResponseHandler```, that is an interface (as a concept) 
      for using a special channel of type ```CommunicationChannel```, the communication channel handles creating sockets, sending new requests &
      reading server responses. 
      
      ![CommunicationChannel](https://user-images.githubusercontent.com/50204418/127261116-b2d86d30-ecdb-46ad-92a9-0c9b36253b54.png)


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
      
  + The ```ClientHandler``` will ask for the client version of the database, that is stored on the disk & mapped to that specific client, 
      as each client gets a version once they start a connection, the client handler will proccess the reqests on that version in a 
      separate thread.

  + In case a response by the server is expected, the ```ClientHandler``` class will handle sending the response through the server socket.
      ```
      if (responseExpected) {
            outputStream = OutputStream(socket.getOutputStream());
            ---Processed the request---
            outputStream.writeObject(response);
       }
      ```
      
      ![image](https://media.geeksforgeeks.org/wp-content/uploads/20201031110311/Multiserver.png)
      
  + Unlike clients where there might be more than one client, a server is a server, in other words there is only one server to serve all clients,
    this implies that using a design pattern like singelton a sensible decision to make.
