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
     Admin admin = new Admin();
     admin.addUser("Omar", new ClientType());
   // admin.addUser("Zaid", new ClientType());
   // admin.addUser("Ahmad", new ClientType());

    DatabaseApi omar = new DatabaseApi("Omar", "127.0.0.1");
    /*omar.create("AFTERFIX", 1000);
    omar.commit();*/
    /*DatabaseApi zaid = new DatabaseApi("Zaid", "128.0.0.21");
    DatabaseApi ahmad = new DatabaseApi("Ahmad", "129.0.0.3");*/
    /*omar.delete(7);
    omar.commit();*/
    var list = omar.readAll();
    for (PersonInterface person: list){
      System.out.println(person.getName() + " " + person.getId());
    }
    //omar.create("Name-Omar-2", 2);
    /*zaid.create("Name-Zaid-1", 1);
    ahmad.create("Name-Ahmad-1", 1);
    zaid.create("Name-Zaid-2", 2);*/
    //omar.commit();
    /*zaid.commit();
    ahmad.commit();*/
  }

  public static void print(List<PersonInterface> list){
    for (PersonInterface person : list) {
      System.out.println("Name : "+person.getName()
              + ", Age : " + person.getAge()
              + "ID : " + person.getId());
    }
  }
}

