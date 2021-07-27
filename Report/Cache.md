# Cache

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
