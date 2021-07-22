package com.atypon.database;

import com.atypon.files.FilesManager;
import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Commit implements Serializable, CommitInterface {
  public TransactionDataInterface getTransactionData() {
    return transactionData;
  }

  private final TransactionDataInterface transactionData;

  private final ClientData clientData;

  private Date commitTime;

  private static final File COMMITS_FILE = CoreDatabase.getCommitsFile();

  public Commit(Database database, ClientData clientData) {
    this.transactionData = database.getTransactionData();
    this.clientData = clientData;
    System.out.println("NEW COMMIT");
  }

  @Override
  public Object commit()  {
    synchronized (CoreDatabase.getDatabaseFile()) {
      synchronized (COMMITS_FILE) {
        this.commitTime = new Date();
        try {
          checkCommitStatus();
          registerCommit();
          var database = clientData.getDatabase();
          var file = database.getDatabaseFile();
          CoreDatabase.commit(file);
          cleanSession();
          System.out.println("ACCEPTED");
          return new AcceptedCommit();
        } catch (InvalidCommitException invalidCommitException) {
          cleanSession();
          return new AbortedCommit();
        }
      }
    }
  }

  private void checkCommitStatus() throws InvalidCommitException {
    var reader = new ObjectReader();
    var commitsList = reader.readAll(COMMITS_FILE);

    for (Object object : commitsList) {
        var commit = (Commit) object;
        var clientCommitDate = commit.getCommitTime();
        var data = commit.getTransactionData();
        var timeConflictExist = clientCommitDate.after(clientData.getConnectionTime());
        var dataConflictExist = transactionData.checkConflict(data);

        if (dataConflictExist && timeConflictExist) {
          throw new InvalidCommitException();
        }
      }
  }

  public Date getCommitTime() {
    return commitTime;
  }

  public ClientData getClientData() {
    return clientData;
  }

  private void registerCommit() {
    var commitsFile = CoreDatabase.getCommitsFile();
    var writer = new ObjectWriter();
    writer.write(commitsFile, this);
  }

  private synchronized void cleanSession(){
    var reader = new ObjectReader();
    var clientsDateFile = FilesManager.CLIENTS_DATA_FILE;
    var list = reader.readAll(clientsDateFile);
    var newList = new ArrayList<Object>();
    var writer = new ObjectWriter();

    for (Object object : list) {
      var data = (ClientData) object;
      if (!data.getUser().equals(clientData.getUser())) {
        newList.add(data);
      }
    }

    writer.writeNewList(clientsDateFile, newList);
    writer.discard(clientData.getDatabase().getDatabaseFile());
  }
}
