# Concurrency

### Why concurrency is required?
  +  Multithreaded applications can take full advantage of multiple processors to gain better performance through simultaneous execution of tasks.
       A well-implemented multithreaded application efficiently uses all the processors available for its own tasks where a single-threaded application 
       must wait for each task to complete before continuing with the rest of the application. At no time can a single-threaded application execute on 
       more than one processor in the system. In our case we need to process several requests as fast as possible in order to serve all clients, which
       makes a single-threaded algorithm an inefficient method to run anything similar to a database server.
       
   
### How did we implement concurrency?
  + We decided to go with a multi version concurrency control (```MVCC```) system, where each client that is to be served get's his own version of
      the database stored on the disk as a safe or private area, each version serves one client, then they can do whatever they want in that safe
      area of theirs, while we let the CPU handles each request as a separate thread, once the client is done they have to commit their modifications
      to the original database by sending a commit request.
      ```
      databaseVersion = new Database(CoreDatabase.getNewVersion());
      clientData = new ClientData(user, databaseVersion);
      write(CLIENTS_FILE, clientData);
      ```

      
### How the commit works?
  + Each successful ```commit``` operation has 2 data sets, ```readSet``` that contains the ```IDs``` for all the records that are read by the client,
      & a  ```writeSet``` that contains any modification on the database.
      
  + Once a client send a ```commit``` request, it will check if the commit operation is valid or not, and that is done by comparing the current commit 
      request with the past commits, the checker will only compare the current commit to those commits that have been done at the time gap in between the
      current client first connection moment & the commit moment (current moment). Comparison is done by comparing the current commit ```readSet``` & 
      ```writeSet``` with other commits ```writeSet```, if there is a conflict the commit will abort and throw an ```InvalidCommitException```, otherwise
      it will be accepted and the data will get written to the original database, and a response of the commit status will be sent as a response
      to the client.
      ```
      function checkCommitStatus() {
        for (commit : commitsList) {
          clientCommitDate = commit.getCommitTime();
          data = commit.getTransactionData();

          timeConflictExist = clientCommitDate.after(clientData.getConnectionTime());
          dataConflictExist = transactionData.checkConflict(data);

          if (dataConflictExist && timeConflictExist) {
            throw new InvalidCommitException();
          }
        }
      }
      
      -----------------------SOME CODE-----------------------
      
      try {
          checkCommitStatus();
          registerCommit();
          CoreDatabase.commit();
          return new AcceptedCommit();
        } catch (InvalidCommitException invalidCommitException) {
          return new AbortedCommit();
        }
      ```
      
  + If the current ```commit``` is accepted, it will be registered as a past commit using the function ```registerCommit```.
      ```
      writer.write(commitsFile, this);
      ```
      
  + Once the client requested the commit operation, either their commit got accepted or aborted, their session will be cleaned, and their version
      of the database will be discarded.
      
  + It's worth to mention that the commit operation will lock both the original & client version of the database in a synchronized enviroment, that is the 
      only moment when the original database is being locked.
      
  + This method has the advantage of almost not using locks on the original database except at the commit stage which implies that the performance should
      be maximized, but the cost for that extra performance is the huge extra usage of the disk memory, but as disk memory isn't that big issue now a days,
      it can be more convenient to use the ```MVCC``` system.
      
