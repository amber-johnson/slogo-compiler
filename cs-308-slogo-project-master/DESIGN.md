# DESIGN.MD

## Design Goals 
In terms of the front-end design goals, we wanted to:

1. Make the front-end data driven. This means taking advantage of resource bundles to create new objects (like the buttons or drop downs) by adding to the file, not hard coding.
2. Fully seperate the objects and its actions. This explains why there are "Creator" and "Controller" packagaes.
3. Get information from the back-end and display it. In order to this, the design goal was to seperate some classes and methods (like having a CommandLine class) to handle the information there. With this approach, the Visualization class doesn't handling everything, just displays.
4. Create an environment that is easy to use.  This was accomplished by creating an intuitively laid out GUI and including a help button which opens tables for all the command references.

In terms of the back-end design goals, we wanted to:
1. Make it as easy to add in new commands as possible and smoothly integrate them into the control flow (this applies for both control/complex commands and basic commands). This is implemented by taking advantage of the factory pattern that hides the implementation of the commands themselves. 
2. Make sure that control flows are appropriately handled, so that instructions are executed in the right order and scopes are maintained for variables in nested functions.
3. Make sure that all errors on the user-side is appropriately handled and detailed enough to let the user know what is going on, and make it easy to add in new error handling with new commands and syntax (e.g., the parentheses, which we did not have).
4. Make sure that we build an infrastructure for parsing commands in different languages, and build it in such a way that the addition of new languages are easy to do. 
5. Make sure that the back-end cleanly communicates with the front-end without using back-channels. This is done by leveraging the MVC model, in which the backend package acts as a capsule of the model, the front-end package acts as a capsule of the view, and the `Connector` class acts as the controller. 

## High-level Design

describe the high-level design of your project, focusing on the purpose and interaction of the core classes

### Frontend
The frontend starts with the Visualization class. The Visualization class constructs the necessary components (commandline, displayscreen(where the turtle moves), and subclasses of ChangeableNode). The subclasses of the ChangeableNode receive a subclass of the NodeController as a parameter. The ChangeableNode object will call the appropriate action using a Reflection pattern from the resource bundle file. All the changeableNode objects will be held in a UIManager object, which will make the UI change according to the user input by reading information from the ChangeableNdoe objects (ex. make UI change language). The displayscreen will get a turtleHistory object as an input, and implement the movement of the turtles and draw lines according to the information in the Collection of turtleMovements that can be extracted form the TurtleHistory class. The displayscreen also has some APIs that different parts of the UI can call to change what appears on the displayScreen.


### Backend
When the BackenedManager class gets an input by setCommand(String input, String language), it creates a new instance of Parser.java, which checks the String for correct syntax using the appropriate properties file based on user-selected language and Syntax.properties.  Parser.java will throw a new ParserException from the Parser.getResourceKey(String single_cmd) if any of the commands are not recognized in either the LANGUAGE.properties or Syntax.properties file.  If no exception is thrown, the error-checked user input is ready to be used by the other backend classes. 

With this, the `BackendManager` can instantiate a new `CommandBlockManager` that executes a String of commands. The `CommandBlock` manager essentially uses the Factory Pattern and acts as a client that make requests to the `CommandTree` and `ControlExecutor`, both of which internally implement factories to execute strings of instructions and return values of double of which the client, the `CommandBlockManager`, uses as arguments for the next comands. The commandtree has a CommandFactory object within it, which uses Factory pattern to call the correct implementation of the BasicCommandInterface.


### Connection between Frontend & Backend
The main class has a Connector object, which has a map of Stage object and Visualization Object, and a map of
Stage object and BackendManager Object. The connector object will start an animation, and when the step() method
is called at each frame of the animation, the connector object will iterate through all Stage objects using the two collections and connect the corresponding Visualization object and BackendManager object. At each step, the connector will get an input of strings from the frontend, and if it is not empty, it will send it to the backend. Then, it will send the TurtleHistory object from the backend to the frontend.

## Assumptions or Simplifications
what assumptions or decisions were made to simplify your project's design, especially those that affected adding required features
 
1. We assumed that the user is not looking to handle errors, but rather just want to know what caused the errors. As such, many of our errors are generalized into one error type with different messages to notify the user. 
2. In commands like fd set :x * :x 3, :x will be set as many times as the number of turtles that are active in the backend.

## Adding New Features
describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline

1. To add a new button to the GUI, it is very straightfoward. Only two or three properties files need to be edited and one Java class.  In ButtonResources.properites, add the name of the new button, along with the name of the method that will define the action for that button.  In ButtonNames.properties, add the translations for the button, following the order of the languages as listed in LanguageIndex.properties.  Finally, add a new method in ButtonController.java to implement the functionality of the button which will be instantiated in the ButtonController.java class.
2. One feature that was not implemented during the Complete portion of the project was the ability to upload a file and to save SLogo code from the GUI's command line.  In order to upload a file, we discussed creating a button in the GUI that would instantiate the built-in JavaFX File Chooser.  First, the button would be created using the steps mentioned above, and then the file selected would need to checked that is an acceptable file type, likely either .txt or .slogo, and the entire contents of the file would be read in as a String.  This String would then be used as the String parameter input in the Parser.java constructor, as instantiated in BackendManager.java.  The modularity of our project's design would allow for a straightforward implementation of the File Chooser or any other user-input accepting functionality.  
3. To add new commands, the programmer first needs to identify whether it is a basic command or a control flow-related command and implement the appropriate interface associated with that command (Basic or control Interface). The programmer needs to be able to specify the command in the appropriate properties file depending on what command it is alongside the number of arguments needed, then implement the functionalities of that command by creating a new class. By the time the command gets to the class that the programmer has created, all the arguments would have been properly passed in as the list of parameters. The programmer simply needs to read from in order to execute the command appropriately. For example, if the command is a basic command that compares two numbers, `parameters.get(0)` will have provided the first number to be compared and `parameters.get(1)` will have provided the second number to be compared. Then, the programmer simply needs to implement the rules of the comparison. Or, if the command is a control command, such as the `AskWith` command that we did not have enough time to implement, by the time the execution gets to the the `AskWith` class, `parameters.get(0)` would hold the string that represents the conditions and `parameters.get(1)` would hold the string that represent the commands. The programmer has access to the `TurtleHistory`, all the variablesdefined, and all the functions defined--essentially everything that allows the programmer to make changes to actually implement the the control command specified. 