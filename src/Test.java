import com.atypon.api.*;
import com.atypon.authorization.*;
import com.atypon.connection.*;
import com.atypon.database.*;
import com.atypon.database.IdFactory;
import com.atypon.files.ObjectReader;
import com.atypon.files.ObjectWriter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
  public static void main(String[] args) throws DeniedAccessException, IOException, InterruptedException {
    //new ObjectWriter().write(new File("database.txt"), new Person("OOO", 3));
//     Admin admin = new Admin();
//     admin.addUser("Omar", new ClientType());
//    admin.addUser("Zaid", new ClientType());
//    admin.addUser("Ahmad", new ClientType());
    DatabaseApi omarApi = new DatabaseApi("Omar", "127.0.0.1");
//    omarApi.create("Name-5", 5);
//    omarApi.create("Name-6", 6);
//    omarApi.create("Name-7", 7);
//    omarApi.create("Name-8", 8);
//   omarApi.delete(17);
//    omarApi.delete(18);
//    omarApi.delete(19);
//    omarApi.delete(20);
//    omarApi.delete(21);
//    omarApi.delete(16);
//   omarApi.updateAge(17, 1234);
//   omarApi.updateName(18, "NEW-NAME");
//    DatabaseApi zaidApi = new DatabaseApi("Zaid", "128.2.2.2");
//    DatabaseApi ahmadApi = new DatabaseApi("Ahmad", "129.7.4.23");
    omarApi.updateAge(23, -1);
    print(omarApi.readAll());
    omarApi.commit();
  }

  public static void print(List<PersonInterface> list){
    for (PersonInterface person : list) {
      System.out.println("Name : "+person.getName()
              + ", Age : " + person.getAge()
              + " ID : " + person.getId());
    }
  }
}

