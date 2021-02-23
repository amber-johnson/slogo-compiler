1. List as many design issues as you can in the method associated with the issue (using line numbers to identify the exact code) from large things like (potential) duplicated code or if statements that should be generalized through polymorphism, data structures, or resource files down to medium sized things like poor error handling or long lambdas methods to small things like consistent coding conventions or ignored assignment design requirements (like using Resources instead of magic values).

* Front-end
    *  Visualization.java:
        *  Not fully data-driven/magic numbers (ex. line 97)
        *  Current public host services (line 53). This will be removed; we will place all the basic and advanced 
            commands in a resource file. Eliminate the hard coding and focusing more on resources 
        * Transition some of the Visualization methods classes (for example, the buttons method from line 124 to 133) 
     
    *  FrontEndManager.java: 
        *  Get user-input from GUI and pass to Parser.java
        *  Implement option for user to upload a .slogo or .txt file
        *  Add code to instantiate 

*  Back-end
    * Parser.java: 
        * Implement parsing a .slogo or .txt file
            * Create second constructor to be used when SLogo input is from a file (opposed to command box)
            * Add files from https://www2.cs.duke.edu/courses/fall19/compsci308/assign/03_parser/examples/loops/ to resources.examples/
        * Fix parsing of multi-line files

    
    * ParserException.java: 
        * Determine what types of exceptions are needed
        * Chain exceptions
        

    * CommandBlockManager.java: prepareVariable method will be a class that handles the make variable command.
    * TurtleModel.java: Had inter-file duplication with Turtle.java. This happend because Turtle.java, which had been broken down to TurtleModel.java and TurtleView.java, was not deleted. We deleted this class.
        * CommandFactory.java: 
    * NeedOfValueException will be removed and the checking will be done in the CommandBlockManager



2.  Organize this list of issues based on things that could be fixed together based on a different design choice or using similar refactorings and prioritize these groups based on which would provide the most improvement to the overall code (not just this method).

    * Reorganize location of classes and remove classes no longer in use
    * Build FrontEndManager.java to connect the front-end to the back-end (getting user-input from GUI to an error-checked String ready for control)
    * Fix error-checking throughout back-end to throw new instances of exception classes, remove unnecessary exceptions that were created, and chain exceptions when needed
    * Refactor prepareVariables to a new class so the logic is not handled by CommandBlockManager
    * 


3. Describe specific overall design changes or refactorings to fix the five most important issues you identified. Note how each change will improve the overall code design, what external changes others will have to make, and what, if any, alternatives you considered. Use SOLID Design Principles to justify the goals of your refactoring efforts.


    * Adding a second constructor that calls the to the Parser.java class to be instantiated in FrontEndManager.java eliminates possible duplicate code
    * Fixed error checking to remove uncessary exceptions that were created

