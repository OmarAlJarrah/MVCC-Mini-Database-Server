package com.atypon.database;

import com.atypon.files.FilesManager;
import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;
import com.atypon.files.WriteOperations;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Commit implements Serializable, CommitInterface {
  private final TransactionDataInterface TRANSACTION_DATA;
  private final ClientDataInterface CLIENT_DATA;
  private Date commitTime;
  private static final File COMMITS_FILE = CoreDatabase.getCommitsFile();

  public Commit(DatabaseInterface database, ClientDataInterface clientData) {
    this.TRANSACTION_DATA = database.getTransactionData();
    this.CLIENT_DATA = clientData;
  }

  @Override
  public Object commit()  {
    synchronized (CoreDatabase.getDatabaseFile()) {
      synchronized (COMMITS_FILE) {
        synchronized (CLIENT_DATA.getDatabase()){
          this.commitTime = new Date();
          try {
            checkCommitStatus();
            registerCommit();
            Database database = CLIENT_DATA.getDatabase();
            File file = database.getDatabaseFile();
            CoreDatabase.commit(file);
            cleanSession();
            return new AcceptedCommit();
          } catch (InvalidCommitException invalidCommitException) {
            cleanSession();
            return new AbortedCommit();
          }
        }
      }
    }
  }

  private void checkCommitStatus() throws InvalidCommitException {
    ObjectReader reader = new ObjectReader();
    ArrayList<Object> commitsList = reader.readAll(COMMITS_FILE);

    for (Object object : commitsList) {
        Commit commit = (Commit) object;
        Date clientCommitDate = commit.getCommitTime();
        TransactionDataInterface data = commit.getTransactionData();
        Boolean timeConflictExist = clientCommitDate.after(CLIENT_DATA.getConnectionTime());
        Boolean dataConflictExist = TRANSACTION_DATA.checkConflict(data);

        if (dataConflictExist && timeConflictExist) {
          throw new InvalidCommitException();
        }
      }
  }

  public TransactionDataInterface getTransactionData() {
    return TRANSACTION_DATA;
  }

  public Date getCommitTime() {
    return commitTime;
  }

  public ClientDataInterface getClientData() {
    return CLIENT_DATA;
  }

  private void registerCommit() {
    var commitsFile = CoreDatabase.getCommitsFile();
    var writer = new ObjectWriter();
    writer.write(commitsFile, this);
  }

  private synchronized void cleanSession(){
    ObjectReader reader = new ObjectReader();
    File clientsDateFile = FilesManager.CLIENTS_DATA_FILE;
    ArrayList<Object> list = reader.readAll(clientsDateFile);
    ArrayList<Object> newList = new ArrayList<>();
    WriteOperations writer = new ObjectWriter();

    for (Object object : list) {
      var data = (ClientData) object;
      if (!data.getUser().equals(CLIENT_DATA.getUser())) {
        newList.add(data);
      }
    }

    writer.writeNewList(clientsDateFile, newList);
    writer.discard(CLIENT_DATA.getDatabase().getDatabaseFile());
  }
}
