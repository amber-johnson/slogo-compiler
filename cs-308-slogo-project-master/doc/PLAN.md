# SLogo Team 05 Design Documentation

## Specification

### Introduction
For this project, our group will create a modular system to interpret Simple Logo (SLogo) commands and display a turtle in a graphic interface that follows a path based on the SLogo commands. The project's primary goal is to allow for flexible extension of different commands, whether they be relating to movement, query, math, or control. The primary architecture of the design is that the front-end and the back-end are two big black boxes that hide all internal implementations from each other; the only communication they have is via the connector, and most of the communication relates to updating movements; thus, these are the closed aspects of our code. In terms of what is open, the architecture implements interfaces relating to commands in order to create flexible openness surrounding the commands to be added. 

### Overview

Essentially, we have the external back-end API, the internal back-end API, the external front-end API, and the internal front-end API.

The external back-end API is implemented in the `BackEndManager` class. This back-end API connects with the fron-end API, which is implemented in the `FrontEndManager` class, via the `Connector` class. 

The internal back-end API consists of the `Interpretor` class, the `CommandStructurer` class, the `CommandTreeNode` class, the `Movement` class, the `TurtleStatus` class, and the `ExistingCommandsInterface`. Beyond that, we will have enums of each of the command in a separate `ExistingCommandsEnum` class, which will return each of the different implementations of the `ExistingCommandsInterface` for separate commands. The `CommandStructurer` class's primary responsibility is to structure commands into a tree of `CommandTreeNodes`; this is useful because with `if/else` or similar control statements, each block of code inside or outside of the `if/else` statement will exist in a different node of the tree. The `Interpretor` class is responsible for processing each block of commands in the `CommandTreeNode`; in doing so, it uses the `Movement` class in order to send over to the front-end any movement made as well ast the `TurtleStatus` class to hold the current status of the tutle for any necessary querying. 

The internal front-end internal API consists of the `Visualization` class, the `Reader` class, and the `TurtleImage` class. The `Visualization` class's responsibility is setting up everything that the user sees in the UI; this includes buttons, language settings, etc. The `Visualization` class will have `Reader` objects, which are primarily responsible for taking in the commands the user inputted, checking any simple syntax errors, and composing a list of commands to be passed onto the `FrontEndManager` to be passed onto the backend. The `Visualization` class will also have the `TurtleImage` class, which is primarily repsonsible for visualization the update of movement for the turtle. 

The image below shows a high level overview of our front-end and connector.
![](https://i.imgur.com/WmFjxGh.jpg)

The image below shows a high level oveview of our back-end.

![](https://i.imgur.com/ea2Nf0d.jpg)



### User Interface
Below is a standard drawing of what our user interface will look like.

![](https://i.imgur.com/osimEZ1.png)

The idea is that the user will input their commands in the command box. The user will also have the ability to access two drop down menus (language and turtle image) and two buttons (start and clear). This can be seen above the command box. 

The language drop down will let the user choose their language preference and the turtle image drop down will let the user choose their prefered image for the "turtle" object. Similarly, the start button will let the user send the commands to the turtle and the clear button will remove any commands typed in the box. Furthermore, to the right of the buttons, command box, and display screen, the user will have the opportunity to look at the current variables and click the history and help pop ups. The idea is that when the user clicks one of these links, a pop up will be displayed that shows the respective information. Lastly, there will also be a color palette. The color palette will let the user change the color of the background or the pen by clicking the respective box as seen below the palette. 

Aside from the actual setup, the UI will also show some error pop ups. The pop ups will consist of syntax or langauge errors that the front-end reader method reads or any other errors found by the interpreter in the back-end. If some errors are found, that information will be sent to the front-end which then will be used to show an error pop up. The idea is that the front-end will deal with syntax and langauge errors while the back-end will handle more detailed errors like an unfinished commands.



### Design Details
This section describes each API introduced in the Overview in detail (as well as any other sub-components that may be needed but are not significant to include in a high-level description of the program). Describe how each API supports specific features given in the assignment specification, what resources it might use, how it is intended to be used, and how it could be extended to include additional requirements (from the assignment specification or discussed by your team). Finally, justify the decision to create each class introduced with respect to the design's key goals, principles, and abstractions. This section should go into as much detail as necessary to cover all your team wants to say.

In the front-end, there are four main classes: `Visualization`, `TurtleImage`, `Reader` and `FrontedManager`.

The `Visualization` class will hold everything in the user interface. It will consist of a command field, the actual display, the buttons (start and clear), the drop downs (language and turtle image selector, color palette (for both background and pen color), popups (history and help), and the `TurtleImage` and `Reader` classes. Overall, this class checks all the requirements needed for the basic implementation of the project. The goal is to let the user have an easy interface to use. 

The `Reader` class takes in a string parameter that takes in a speicifc language selected from the langauge dropdown menu. Consdering this, the `Reader` needs all the languages in the resources folder. This approach allows the language checker aspect of this class to be easily implemented. This class really helps with the error checking aspect of specification. Consdering this, when the this class detects the erros, it will trigger a n error popup. Furthermore, this class also takes the user input and makes it a list of string – which includes all the commands.

The `TurtleImage` class implements the dispaly API and includes a method that assits with updating the image. This is important because it helps with the speficiation of changing the image if prompted to.

The `FrontedManger` class implemnts the front-end external API. This helps create one, unique class for the front-end to interact with the Connector, and the back-end. This is an imporant design decision for us because it ensurs no mess when connecting the front-end and the back-end. This is good to have because if there needs to be any extensions or any additioanl information to be communicated between both ends, we only have to deal with the connection, not the entire code.


The `BackEndManager`, which implements the back-end external API, provides a singular source for the back-end to interact with the Connector and thereby with the Front-end. We made the design decision to include the back-end manager so that there are not messy connections between the `Connector`/front-end and random classes in the back-end. This way, if our project were to be extended and additional communications between the front-end and back-end is needed, or if current communications need to be modified, then there is an easy place in which they are all gethered in order to be added to, removed from, or modified. All communication goes through the `BackendManager`, and essentially, all implementations of the Backend is hidden. 

The backend internal API supports the project's specifications by providing the service of parsing the user's commands. This is done through the subcomponents of the `CommandStructure`, `CommandTreeNode`, `ExistingCommandsInterface`, `TurtleStatus`, `Movement`, and `ControlEnums` as well as `NonControlEnums`. 


In terms of extension, the `CommandStructure` component implements a tree whose nodes are chunks of commands. The pointers between the nodes of the tree will thus allow us to support different controls such as conditionals or repeating a step; for example, with a conditional, we can have the left child be the node of commands evaluated if the command is false, and the right child be the node of commands evaluated if the command is true, and that node can have a pointer to the block of code that will be evluated next outside of the conditionals. Thus, this justifies the necessity of the `CommandStructure` as well as `CommandTreeNode` class, which implements the individual nodes for the `CommandStructure`. 

The `Interpreter` class is created to handle the actual parsing of chunks of commands in each of the node. To do so, it uses resources such as the `CommandStructure` class, 2 stacks of numbers and commands, a map of user variables, a queue of movement (to be passed onto the back-end) manager whenever the front-end needs a movement in order to draw it, and the `TurtleStatus` class to handle queries. The `Interpreter` class ccesses the `CommandStructure` class node by node, and processes movement by each of the node. 

The `Movement` class is created in order to hold all the information that the front end will need for each movement to move the turtles and draw the line if needed. It is going to store the starting coordinate, ending coordinate, orientation of the turtle, and boolean values for making the turtle visible or the line visible.

The `TurtleStatus` class holds the current status of the turtle, and is needed as some commands requires the current status of the turtle. 

The `ExistingCommandsInterface` serves as the contract between each of the commands and their return value and movement to be processed by the `Interpretor`. The `ExistingCommandsInterface` is implemented by each of the enums for each of the command; for example, the `FD` command will have a different way of implementing `getValue` (the return value) and `getMovement` from the `LEFT` command; however, the `Interpretor` class can expect that both of these implementations will provide it with what it needs. 

The `NonControlEnums` class is an enums class that holds all the commands except the conditional/repeat commands as enums. Each object in this enium class will override the getCommandInterface() method which will return a implementation of the ExistingCommandsInterface in such a way that is appropriate for that object. 

The `ControlEnums` holds all the commands related to conditionals/for loops and will be used to make the appropriate tree structures according to the command.

### API as Code
Your APIs should be written as Java interfaces, types that cannot contain instance variables or private methods, in appropriate packages. These should be Java code files that compile and contain extensive comments to explain the purpose of each interface and each method within the interface (note this code can be generated directly from a UML diagram). Include any Exceptions you plan to throw because of errors that might occur within your methods. Note, this does not require that all of these types will remain as interfaces in the final implementation, just that the goal is for you to focus on each type's behavior and purpose. Also include the steps needed to complete the Use Cases below to help make your ideas more concrete.


#### External Front-end
FrontEndManager class will implement the FrontEndExternalInterface. This will assist with back-end and front-end communication.

```java
public interface FrontEndExternalInterface {
    //Stores a movement object, which will be used to update the display screen.
    public void setMovement(Movement movement);
     //True if Connector needs to get another movement object to update the screen.
    public boolean needNextMovement(Movement movement);
    // Error message from backend will be delivered to the Front End with this method.
    public boolean showError(String errorMessage);
}
```

#### Internal Front-end
```java
public interface Visualization {
    public Visualization();
    Command
    public List<String> getCommands();
    Command
    public boolean isCommandEmpty();
}
```
```java
public interface TurtleImage extends ImageView{
    //centerOfDisplay is needed as that will be (0,0) for the turtle
    public TurtleImage(Image image, Point centerOfDisplay);
    //the turtle will move along the path, drawing the line if it needs to do so.
    public void update(Movement movement);
    //false if the turtle is moving along the path. true if reached the end.
    public boolan needNextPath();
}
```
```java
public interface Reader {
    //This string will be used to get the right file for that language.
    public Reader(String language);
    //The user-input string will be checked. If it has syntax error, error will be thrown. If not, it will turned to
    //a list of string, which will be the command for the backend.
    public List<String> getCommands(String command) throws SyntaxErrorException;
}
```

#### External Back-end
Implementation of the BackEndExternalInterface is going to be used for the communication between Front-end and Back-end.
The Conncector class, which is the connection between the two subgroups, will have an implementation of BackEndExternalInterface as a field. The API of BackEndExternalInterface is...

```java
public interface BackEndExternalInterface {
    Command
    public void setCommands(List<String> commands);
    //Returns the next movement by getting it from Interpreter
    public Movement getNextMovement();
    //Returns boolean value by getting it from Interpreter
    public boolean hasNextMovement();
    //Returns user defined variables by getting it from back-end
    public Map<String, Double> getVariables();
}
```

#### Internal Back-end

```java
public interface Interpreter { // we expect this to be a class later
    //returns the first element in the queue that stores all the movement objects
    public Movement getMovement();
    //returns true if the queue is not empty
    public boolean hasNextMovement();
    //returns all the user-defined variables
    public Map<String, Double> getUserVariables();
}
```

```java
public interface CommandStructurer { // we expect this to be a class later
    //makes a tree structure from the list of string
    public CommandStructurer(List<String> commands)
    //returns the head node of the tree
    public CommandTreeNode getHeadNode();
}
```

```java
public interface CommandTreeNode { // we expect this to be a class later
    public CommandTreeNode getLeft();
    public CommandTreeNode getRight();
    public List<String> getCommands();
}
```

```java
//This interface will be implemented to implement the behaviours of different commands
public interface ExistingCommandsInterface {
    public double getValue(double paramter1, double parameter2);
    public Movement getMove();
}
```
``` java
//This class will be used to store the values of the parameters needed to move the turtle and draw the line
public interface Movement { // we expect this to be a class later
    public Point getStartPosition();
    public Point getEndPosition();
    public double getOrientation();
    public boolean turtleVisible();
    public boolean penUp();
}
```

``` java
//THis class stores the status of the turtle, which the interpreter will need for some commands.
public interface TurtleStatus { // we expect this to be a class later
    public Point getCurrentPosition();
    public double getCurrentOrientation();
    public boolean getCurrentVisibility();
    public boolean getCurrentPenStatus();
    public void changePenStatus();
    public void changeVisibility();
    public void setCurrentPosition(Point Pos);
    public void setCurrentOrientation(double angle);
}
```

``` java
public enum NonControlEnum {
    public abstract ExistingCommandsInterface getCommandsInterface();
}

``` java
public interface Exception {
    public String getExceptionMessage();
}
//Implementations of Exception will be 'UndefinedCommandException(User tries to use defined command that is not defined)', 'WrongAmountOfNumbersException', 'UnfinishedBracketExcpetion', etc.
```

### Design Considerations


* We decided to have our DisplayAnimation() class implementing our internal front-end API. We decided to this because it makes it easier for us to create a path that is not straight, like an oscillation.

* We decided to send the user commands as an ArrayList of Strings and not just one really long String. We decided to this because we felt it would be easier to deal with an ArrayList with multiple strings/commands than one long string with various commands. 

* We decided to have a FrontedManager() and BackendManager() classes that implement their respective external API. We went with this approach because we felt it would be easier to implement the external interfaces with this approach.

* We decided to create a Connector() class that allows the front-end and the back-end to communicate. We decided to go with this approach because it allows us to completely divide the front-end and back-end. Thus, when getting the two to communicate, we only have to worry about code that deals with this. This means that we reduce the chances of destroying our front-end and back-end code.

* We decided to include our pen information in our Movement() class, instead of creating a separate pen class. We did this because we figured it was best to place anything realted to the turtle querues to be in our turtleSettings() class. This include getting position, the pen, and whether the turtle (or other animal) is visible or not. 

* We decided to create an interface for different categories of command (movement, math calculations, and queries). We decided to this because all three commands have different behaviors. 

* We decided our Reader() to be included in our Visualization(). We did this because our reader only communicated with our visualization, so there is no need to have it in our front-end manager. 

* We decided to use interface to implement each pre-defined commands in order to increase the flexibility for adding new commands; whenever a new command needs to be added, that command merely needs to define its implementation of `ExistingCommandsInterface` in order to make sure that it fits in with the rest of the commands. 

* We decided to use tree structure in order to implement if/while conditionals and repeated statements. Simply using stacks will make it impossible to implement conditionals in a flexible way. With a tree structure, we can define different ways in which the nodes are connected to one another (for example, a root might have a left child that holds commands for when the expression evluates to false and a right child that holds commands for when the expression evaluates to true); this is not something that will be possible to implement with a stack.


### Team Responsibilities
* Michael Castro (front-end)
    * Primary responsibility: The visualization – mainly the user interface (like buttons, code input, etc).
    * Secondary responsibility: The reader class to help with syntax error.
* Amber Johnson (front-end)
    * Primary responsibility: The reader class and error checking for user-input
    * Secondary responsibility:  The visualization, including accepting user-input through buttons and command line, and displaying the turtle to the user
* Eric Han (back-end)
    * Primary responsibility: interpret turtle commands and turtle operators
    * Secondary responsibility: Designing the data structure for the commands
* Ha Nguyen (back-end)
    * Primary responsibility: interpret math and boolean operators
    * Secondary responsibility: Throwing appropriate errors apart from syntax error

## Use Cases

#### Michael Castro, Front-end use cases:
* The user types in their commands in the command box, and then click start. After the user click starts, two things will happen: commands are sent to history and reader. Furthemore, the user will see the turtle move/the creation of the path.
* The user types in their commands in the command box, but after they finish, they see an error in their input. The user will then click the clear button which will remove any text in the command box. This will allow the user to start again and type something new.

#### Amber Johnson, Front-end use cases:
```logo
# Use Case 1
# A decorative pagebreak

repeat 6
[
  repeat 2
  [
    repeat 60
    [
      fd 1
      lt 2
    ]
    lt 120
  ]
  lt 60
]
```

My portion of the project is centered around accepting, parsing, and error-checking user input in the form of commands.  Within this section, code like the sample code shown above will first be passed to the class reader.java.  Similar to the ProgramParser.java class from spike_slogo, the code will be checked using public methods addPaterns() and getSymbol() to confirm that each command matches a command contained within the listed basic syntax for the selected language. This user-selected language will come from a ComboBox created within the class visualization.java. If no errors are thrown, the commands will be passed through the pubic method parser() also within the class reader and parsed into an ArrayList<> of Strings to be used in other parts of the front-end as well as the back-end.  In the front-end, a history of commands and the current command will be displayed in the user-interface via the class visualization.java.  

```logo
# Use Case 2
# Make a query to see if the turtle is hidden (0) or showing (1)

SHOWING?
```

Exactly like Use Case 1, this command would first be passed to the class reader.java to be checked for correct language, spelling, and syntax by the public methods addPaterns() and  getSymbol().  Assuming no errors are found, the command is passed to the public method parser() also within the class reader.java and output as an ArrayList<>.  Because there is only one command, the ArrayList<> will only need to contain one element.  Additionally, because this command represents a query, the response will need to be displayed to the user in the command line box within the UI.  However, this step can only occur once the command has been interpreted in the back-end.  After the ArrayList<> containing the command is passed to the back-end, the response of the query is determined in the class movement.java, and this response is passed back to the front-end, the class visualization.java will be able to display to the user whether the turle is hiding or showing.

#### Ha Nguyen, Back-end use cases:
```
SUM FD 10
:DOSTUFF 20
```
A part of my responsibilities is implementing error checking; some use cases for this is when the user provides an incorrect number of parameters for a given command, such as the first line shows, as well as when the user tries to use commands or variables that have not yet been defined, such as the second line shows. When the user inputs in invalid inputs such as those above, the `Interpretor` will throw an Exceptionto the `Exception` class, which will have methods to handle each distinct exception deifned; the `Exception` class then provides the message that has to be shown to the backend manager, which will then send that to the front-end in order to actually display the error. 

#### Erie Seong Ho Han, Back-end use cases:
* If for loop appears: In the commandStructurer class, everything in the bracket will be combined together, and that part will be attached to the node until that part is repeated as much as it needs to repeat.

* FD SUM 20 20 is the list of string to be analyzed : As there is no conditional, there will be one node in commandStructure that has neither left nor right nodes. In the interpretor class, the FD and SUM will be put into a stack, and 20 and 20 will be put into another stack. As SUM needs two numbers, it will take the two numbers out from the stack. The interpretor class will make an instance of the ExistingCommandsInterface(this may be defined in the enums class), and then use the getValue() of the instance to calculate SUM 20 20. Now, FD 40 will be left. Another instance of the ExistingCommandsInterface will be gotten, and the getMovement() method will be used to get the movement object needed, which will be stored in the queue inside the interpretor class, which will be passed to the BackEndManager, and then to Connector.



