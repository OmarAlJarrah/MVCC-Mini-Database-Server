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
      
      
### 2) Open Closed Principle (OCP)
  ![image](https://res.cloudinary.com/practicaldev/image/fetch/s--XI1FFTvi--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/http://d33wubrfki0l68.cloudfront.net/d1820d6c153e116bb211cc3e4499de8a8a40cf8e/b43f4/assets/images/open_closed_1.png)

  + What is OCP? The Open-Closed Principle (OCP) was coined in 1988 by Bertrand Meyer. It says:A software artifact should be open for extension but
      closed for modification.In other words, the behavior of a software artifact ought to be extendible, without having to modify that artifact.
      This, of course, is the most fundamental reason that we study software architecture. Clearly, if simple extensions to the requirements force
      massive changes to the software, then the architects of that software system have engaged in a spectacular failure.
      
  + When a module is open? A module will be said to be open if it is still available for extension without the need for massive changes in other modules.
      For example, it should be possible to add fields to the data structures it contains, or new elements to the set of functions it performs.
      
  + When a module is closed? A module will be said to be closed of changing/adding any kind of extension implies doing massive changes on other
      modules, which can lead to instability & inconsistency in both the code & development cycle.
      
  + What did I do here? I focused on using interfaces where the implementations can be changed and multiple implementations could be created
      and polymorphically substituted for each other. Interface specifications can be reused through inheritance but implementation need not be.
      The existing interface is closed to modifications and new implementations must, at a minimum, implement that interface which provides 
      a protocol that derived classes implement.
      
  + Why interfaces assures that OCP is safe? changes to derived classes which implement the interface will not break any client code, and may not 
      even require recompilation of some clients. What we can’t do is change the interface definition. Any change here may force changes on most 
      or all of its clients. Abstract interfaces directly support the Open/Closed Principle. They must be extended, but are closed to modification.
      Since they have no implementation they have no latent errors to fix and no performance issues.

  + But is it really possible to stick ```100%``` to the OCP principle? Few would suggest that 100 percent of every design should satisfy the
      open/closed principle. But an effective design may use many components which do satisfy the principle but also include ```program glue```.
      The ```glue``` is used to bind the components into a working program, without much regard to the open/closedness of the ```glue``` part.


### 3) Liskov Substitution Principle (LSP)
   + What is LSP? Substitutability is a principle in object-oriented programming stating that, in a computer program, if S is a subtype of T, then
       objects of type T may be replaced with objects of type S (i.e., an object of type T may be substituted with any object of a subtype S) without
       altering any of the desirable properties of the program (correctness, task performed, etc.). More formally, the LSP is a particular definition 
       of a subtyping relation, called (strong) behavioral subtyping. It is a semantic rather than merely syntactic relation, because it intends to
       guarantee semantic interoperability of types in a hierarchy, object types in particular. To ensure a design supports the LSP:
       
       - Derived objects must not expect users to obey pre-conditions stronger than expected for the base class.
       - Derived objects must satisfy all of the post-conditions satisfied by the base class.
       
      LSP can also be described as a counter-example of Duck Test: “If it looks like a duck, quacks like a duck, but needs batteries – you probably
       have the wrong abstraction”
   ![image](https://maksimivanov.com/static/74dad4b97aa9732e5644eb34f5eb18b7/a987b/liskov_1.png)
   + Why LSP is important? The Liskov Substitution Principle is the third of Robert C. Martin’s SOLID design principles. It extends the Open/Closed
        principle and enables you to replace objects of a parent class with objects of a subclass without breaking the application. This requires all
        subclasses to behave in the same way as the parent class.
        
   + How did I implement it? As in OCP, interfaces assures the substitution of child classes, all classes that are childs of a higher abstract class
        (interface), the compiler itself won't let you forget the rules defined by the parent class, which implies that childs of the same interface
        are always substitutabile.
        
     
### 4) Interface Segregation Principle (ISP)
   ![image](https://www.coengoedegebure.com/content/images/2018/09/InterfaceSegregationPrinciple.jpeg)
   + What is ISP? (ISP) states that no client should be forced to depend on methods it does not use.[1] ISP splits interfaces that are very large
        into smaller and more specific ones so that clients will only have to know about the methods that are of interest to them. Such shrunken
        interfaces are also called role interfaces.[2] ISP is intended to keep a system decoupled and thus easier to refactor, change, and redeploy.

   + means that sometimes we tend to make interfaces with a lot of methods, which can be good to an extent, however this can easily abused, and we can
        end up with classes that implement empty or useless methods which of course adds extra code and burden to our apps. Imagine you are declaring a
        lot of methods in single interface, if you like visual aids a class that is implementing an interface but that is really needing a couple of
        methods of it would look like this:

   ![image](https://i.stack.imgur.com/V6p8i.png)

   In the other hand, if you properly apply the interface segregation and split your interface in smaller subsets you can me sure to implement those
      that are only needed:

   ![image](https://i.stack.imgur.com/M0eWu.png)

   See! Is way better! Enforcing this principle will allow you to have low coupling which aids to a better maintainability and high resistance to changes.
   So you can really leverage the usage of interfaces and implementing the methods when you really should.
   
   + How did I implement the ISP? Simply implemented the SRP on interfaces, an example of that is the reading & writing tools, as each tool of those 
         two were splitted into different modules, their interface were splitted too, if you want to read only, why compile the write module?
         another example is the requests, as there are ```DatabaseRequest, ConnectionRequest & LoginRequest```, instead of making them a child to
         one huge parent (interface) and violating the ISP, each of them has its own interface.
         
         
### 5)  Dependency Inversion Principle (DIP)

   ![image](https://miro.medium.com/max/1400/1*rQcIU3LFGGXtGD95FfN5uw.png)
   
   + What is DIP? DIP is a specific form of loosely coupling software modules. When following this principle, the conventional dependency relationships
       established from high-level, policy-setting modules to low-level, dependency modules are reversed, thus rendering high-level modules independent
       of the low-level module implementation details. The principle states:
       - High-level modules should not depend on low-level modules. Both should depend on abstractions.
       - Abstractions should not depend on details. Details (concrete implementations) should depend on abstractions.
      The idea behind the previous points of this principle is that when designing the interaction between a high-level module and a low-level one, the
      interaction should be thought of as an abstract interaction between them. This not only has implications on the design of the high-level module, but
      also on the low-level one: the low-level one should be designed with the interaction in mind and it may be necessary to change its usage interface.
   
      ![image](https://user-images.githubusercontent.com/50204418/127282390-55a04c30-09f1-4ac9-a73a-d7e8af88587f.png)


