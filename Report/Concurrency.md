# Concurrency

### What is concurrency?
  + concurrency is the ability of different parts or units of a program, algorithm, or problem to be executed out-of-order or at the same time
      simultaneously partial order, without affecting the final outcome. This allows for parallel execution of the concurrent units, which can
      significantly improve overall speed of the execution in multi-processor and multi-core systems. In more technical terms, concurrency 
      refers to the decomposability of a program, algorithm, or problem into order-independent or partially-ordered components or units 
      of computation.
    
  ![image](https://uploads.toptal.io/blog/image/126087/toptal-blog-image-1526311066247-4ce28d0e2a6878d80c5374d2c53e8aff.png)

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
      
  + As each client have their own version of the database, any modifications or reads will be processed using their version, which implies a 
      sequential access to version per client.
      
  ![db_diagram png](https://user-images.githubusercontent.com/50204418/127238519-2e4660e8-8edd-4ebb-a8c2-00114426bb76.png)

  + For each request (CRUD operation) by the client, a new thread is created to process that reqest, and in order to assure consistency
      in data modifications, and to make sure that requests by the same client are proccessed in order, a lock on the data is created
      with each request, once the request is proccessed, the lock will be released & the data will become available again, this lock won't
      affect any other user as it only locks data that are present in one version (the version mapped to the client doing modifications),
      which means that other clients can access their data whenever they want, new clients can have thier new versions created at any time,
      though this availability comes at price (Increased disk usage), but for the commit operation, well that's another story.
      
      ```
      synchronized (database.getDatabaseFile()) {
        --- Sorry but you can't access the data unless I say so :7 ---
        
        ~ A lot of code here ~
        ~ Proccess some request somehow ~
        
      }
      --- Done, now you can access the data again ^^ ---
      ```
      
      ![image](https://www.artima.com/javaseminars/modules/Threads/images/BWMonitor.gif)
      
  + As we said, commits are different story when it comes to synchronization, so what about them?
      
      
### How the commit works?
  + A user can do commit once per session, in a sense it is the same as any CRUD operation, but it locks both original & client's versions, 
      which means that while a commit operation is on, no other client can access the the original data, in other words, new client's requesting
      a version of the database have to wait until the ongoing commit is done, also any other client requesting to commit have to wait too, this 
      method assures consistency in data, but still, a problem comes up, which is the conflict that comes out of different versions, this problem
      can be solved by few methods, the one we picked theoretically works fine, but it has few issues too, which is aborting commits that can not
      be applied.

  + Assuming past commits, each successful ```commit``` operation has 2 data sets, ```readSet``` that contains the ```IDs``` for all the records
      that are read by the client, & a  ```writeSet``` that contains the ```IDs``` of any modification on the database in that commited version.
      
  + Once a client send a ```commit``` request, it will check if the commit operation is valid or not, and that is done by comparing the current
      commit request with the past commits, the checker will only compare the current commit to those commits that have been done at the time gap
      in between the current client first connection moment (that moment when their session started) & the commit moment (current moment). 
      Comparison is done by comparing the current commit ```readSet``` & ```writeSet``` with other commits ```writeSet```, if there is a conflict
      the commit will abort and throw an ```InvalidCommitException```, otherwise it will be accepted and the data will get written to the original
      database, and a response of the commit status will be sent as a response to the client.
      
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
      it can be more convenient to use the ```MVCC``` system, but that does not imply that this solution has no issues at all.
      
