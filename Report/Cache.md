# Cache

### What is cache?
  
  + Cache is a hardware or software component that stores data so that future requests for that data can be served faster; the data stored in a cache 
      might be the result of an earlier computation or a copy of data stored elsewhere. A cache hit occurs when the requested data can be found 
      in a cache, while a cache miss occurs when it cannot. Cache hits are served by reading data from the cache, which is faster than recomputing
      a result or reading from a slower data store; thus, the more requests that can be served from the cache, the faster the system performs.
      
  ![image](https://media.geeksforgeeks.org/wp-content/uploads/cache.png)
  
### Why use cache?

  + The data in a cache is generally stored in fast access hardware such as RAM (Random-access memory) and may also be used in correlation with
      a software component. A cache's primary purpose is to increase data retrieval performance by reducing the need to access the underlying
      slower storage layer. Trading off capacity for speed, a cache typically stores a subset of data transiently, in contrast to databases 
      whose data is usually complete and durable. A database cache supplements your primary database by removing unnecessary pressure on it, 
      typically in the form of frequently accessed read data. The cache itself can live in a number of areas including your database, application
      or as a standalone layer.


### What is cached?
  + Whenever a new client connects to the server and have their own version of the database, a new read-only cache is generated to improve performance,
      the generated cache will contain a random number of data records that does not exceed 10% of the total records count in the database.
      ```
      function generateRandomCacheSize() {
        min = 1;
        max = totalRecordsCount/10;
        pivot = (max-min+1)+min;
        return floor(Math.random() * pivot);
      }
      ```
      
### How does cache work?
  + Whenever a client attempts to read some entity, the server side application will try to get the record out of the cache, if the record does not
      exist in the cache, it will go & read data from the disk.
      ```
      try {
        cacheRead = cache.read(recordId);
        return (PersonInterface) cacheRead;
      } catch (NullPointerException nullPointerException) {
          return (Person) diskRead(recordID);
      }
      ```
      
      ![Cache](https://user-images.githubusercontent.com/50204418/127264278-35cbc4ad-6e6a-49f6-ac2a-d187057de9bf.png)

  + It is worth to say that any modifications on the data are not performed on the cache, this can be an issue for Some, but it's acceptable as
      long ass the cache is used for read only purposes.
