# SLogo Questions

Contributors:
* Amber Johnson, ajj18
* Eric Han, eh174
* Ha Nguyen, hn47
* Michael Castro, mc546

**When does parsing need to take place and what does it need to start properly?**

Parsing needs to take place in the back-end. We are planning on having our front-end grab the user input and then pass 
the information to our back-end. In our first part of the back-end, we will determine the language. In order to start 
this properly, it gets the string of commands from the front-end, then parses them using the Properties resource bundle,
then orders them in order to send in to our second part of the back-end.

**What is the result of parsing and who receives it?**

When the parsing of user input is complete, this information and the parameters to which they are set is next used by the 
second part of the back-end. This secondary portion of the back-end will interpret the received information and translate 
it to turtle movements. These turtle movements equate to methods, likely in a movement class.

**When are errors detected and how are they reported?**

The errors detected could range from a wrong user input to a wrong method name. The most simple errors will be detected 
in the front-end(typos, different number of brackets, and unfinished commands) while the complex ones will be handled in
the back-end. The errors will be reported by displaying an “error found” popup.

**What do commands know, when do they know it, and how do they get it?**
The commands parsed from the user input will have to know different information depending on the type of command. For 
example, commands to turn right or left will require knowing the angle that the turtle will turn and which direction to 
turn. Additionally, a command to move the turtle will need to indicate how far forward. These are very basic examples, 
but they demonstrate that the command will know this information when the user input has been parsed in the first part 
of the back-end, the language has been set, and the parsed command is matched with the relevant method for that command. 
Effectively, this command will get the information when the turtle is told to move.

**How is the GUI updated after a command has completed execution?**

After the command has completed execution, the backend will pass blocks of commands over to the front-end in order to be 
drawn and updated with each run of the game loop. The GUI will then draw the image according to the command; if the 
command does not run indefinitely, then the GUI merely stops when the entire image has been drawn, and the user can 
have the option to run different commands. 