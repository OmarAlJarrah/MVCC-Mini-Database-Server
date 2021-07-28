# SOLID Principles
I did what I could, may Allah help me   :')

### 1) Single Responsibility Principle (SRP)
   
   ![image](https://user-images.githubusercontent.com/50204418/127268323-7f6e7cf6-baf9-4d25-9208-60ab4fc5a10d.png)

  + What is SRP? The Single Responsibility Principle is a computer programming principle that states that every module or class should have
      responsibility over a single part of the functionality that is provided by the software. That responsibility should be fully encapsulated 
      by that class. A good quote relating to this principle is ```A module should be responsible to one, and only one, actor. --uncle_bob```.
      
  + Why is it important? The reason it is important to keep a class focused on a single concern is that it makes the class more robust. If the code
      needed to be changed constantly, there is a chance of the code becoming unstable and eventually leading to a break further down the development line.
      
  + So... what did I do about it? well... I focused on giving each module a one & only one goal that it works to achieve, a ```Log``` class for logs, 
      ```Writing/Reading``` tools as separated modules, splitting ```CRUD``` operations into a module per operation & a lot other than that are an 
      example for of SRP in the code base.
      
      
### 2) Open Closed Principle (OCPP)
  + What is OCP? The Open-Closed Principle (OCP) was coined in 1988 by Bertrand Meyer. It says:A software artifact should be open for extension but
      closed for modification.In other words, the behavior of a software artifact ought to be extendible, without having to modify that artifact.
      This, of course, is the most fundamental reason that we study software architecture. Clearly, if simple extensions to the requirements force
      massive changes to the software, then the architects of that software system have engaged in a spectacular failure.
      
  + When a module is open? A module will be said to be open if it is still available for extension without the need for massive changes in other modules.
      For example, it should be possible to add fields to the data structures it contains, or new elements to the set of functions it performs.
      
  + When a module is closed? A module will be said to be closed of changing/adding any kind of extension implies doing massive changes on other
      modules, which can lead to instability & inconsistency in both the code & development cycle.
      
  + 
