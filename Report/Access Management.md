# Access Management

### Users
  + There are 2 types of users:
    - Normal User: their role is to make a connection to the server & do the CRUD operations on the database if and only if they are 
        valid registered users.
    - Admin User: their role is to give access for normal users so they can do their role, also they can turn their access down by
        deleting them from the list of registered users.
        
### Grant Access or Denie it
  + Once a client tryes to connect to the server, he goes through an access layer first, this layer checks if the user trying to 
      connect has the right to do so, then it sends a respose to the user connection request wether they got access or not, in case 
      the user does not have the authority to connect, it will send him a ```DeniedAccess``` object, then a denied access exception 
      should be thrown at client side.
      ```
      if (!access.getAccess()){
        throw new DeniedAccessException();
      }
      ```
      
      ![imagw](https://www.ictshore.com/wp-content/uploads/2017/06/1054-06-dot1x.png)
      
### Access Database
  + Users with access privilege are stored as serialized objects on a file ```access.txt```, each user has 3 properties, ```username```, ```access type```
      & a special ```ID```, in order to get access, a user should send a connection request to the routing layer, a connection request includes each of 
      the ```username``` & ```IP address``` of the client.
      ```
      function connect(USERNAME, IP) throws DeniedAccessException {
        request = new ConnectionRequest(USERNAME, IP);
      }
      ```

  + Once a user get's access he will be registered at the server side.
      ```
      login = readLoginRequest();
      access = ServerAccess.checkAccess(login);
      if (access.getAccess()){
        registerClient(access, login);
      }
      ```
