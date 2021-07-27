# Data Handeling

### 1) Serialization
  + Serialization is the process of translating a data structure or object state into a format that can be stored (for example, in a file or memory 
      data buffer) or transmitted (for example, over a computer network) and reconstructed later (possibly in a different computer environment).
      When the resulting series of bits is reread according to the serialization format, it can be used to create a semantically identical clone 
      of the original object. 
      
  + For many complex objects, such as those that make extensive use of references, this process is not straightforward.
      Serialization of object-oriented objects does not include any of their associated methods with which they were previously linked.
    
  + This process of serializing an object is also called marshalling an object in some situations. The opposite operation, extracting a data structure
      from a series of bytes, is deserialization, (also called unserialization or unmarshalling).

  + All data that are to be stored on disk are written as serialized objects on text files, this mechanism can be achieved by implementing the
      ```java.io.Serializable``` & the ```java.io.Externalizable``` interfaces in these objects classes, this implies that retrieving data 
      requires deserialization.

  ![image](https://media.geeksforgeeks.org/wp-content/cdn-uploads/gq/2016/01/serialize-deserialize-java.png)

  
  + Writing/Reading data is done using each of ```java.io.ObjectInputStream``` & ```java.io.ObjectOutputStream``` classes, given that each object
      that is to be read/written implements the serializable interface & has a special ```serialVersionUID```.
      
  + Requests/Responeses are sent through sockets as serialized objects, which makes compressing/decompressing data much easier, in addition to
      giving a general communication protocol that does not change through time if other classes are changed, though data has to be rewritten 
      in some cases.
      
  ![image](https://i0.wp.com/techvidvan.com/tutorials/wp-content/uploads/sites/2/2020/05/Serialization-in-Java.jpg?ssl=1)
            
### 2) Files management
  + All data are written as serialized objects on text files, files are stored as ```static objects``` in the ```FilesManager``` class, 
      giving a reference to all the files this way provides more readability, and more independency to the classes, which implies that
      any future modifications on the files can be done easier without the need to modify code.
      
      ![FilesManager](https://user-images.githubusercontent.com/50204418/127238672-b15a0088-667b-4e45-9623-31c4becd2da8.png)

      
    
### 3) Reading/Writing & Memory
  + All data that are to be written/read by the client do not use buffering, which insures the management of RAM usage, on the other hand, 
      a cache is created for each client to increase performance of read operations.
