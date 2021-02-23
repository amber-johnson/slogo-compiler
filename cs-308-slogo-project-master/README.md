# README.md

### Team Members & Roles
* Erie Seong Ho Han (eh174)
    * Focus on API exchange and applying factory/reflection pattern
* Ha Nguyen (hn47)
    * Focus mainly on control flow on the back-end
* Michael Castro (mc546)
    * Focus mainly in front-end and UI
* Amber Johnson (ajj18)
    * Focus on parsing user-input and error-checking

### Project Timeline
* Start Date: 10/02/2019
* End Date: 10/30/2019
* Hours Worked: 180

### Resources
* https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ScrollPane.html
* https://o7planning.org/en/10857/javafx-scrollpane-tutorial
* https://docs.oracle.com/javafx/2/ui_controls/color-picker.htm
* https://stackoverflow.com/questions/27171885/display-custom-color-dialog-directly-javafx-colorpicker
* https://openjfx.io/javadoc/11/javafx.controls/javafx/scene/control/ColorPicker.html
* https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html



### Project Files
* Main.java: contains main() and used to start the program
* resources/image: contains all possible "turtle" images
* resources/languages: contains all the language resource bundles ans their english translation

### Exception Handling Cases
* ParserException.java
    * ParserException(String command): handles cases in Parser.java where the parameter command does not match a key from resources.languagues/ or a value from resources.languages/Syntax.properties
* BackendException.java
    * BackendException(String message): handles cases where no built-in Java Exception class is caught previously, but is expected to generate errors later on (e.g., when the number of parameters given do not match the number required for the command or when parentheses do not match)
    * BackendException(Throwable ex, String message): handles cases where a built-in Java Exception class is caught

### Required Data 

* resources/ -- defines all the commands supported and group them into groups according to characteristics (e.g., basic commands versus controls or user-defined commands)
    * DefinedCommands.properties
    * DefinedControls.properties
    * DefinedMovementCommands.properties
    * UserDefinedVariables.properties
* resources.frontend/ -- helps create the respetive object needed (e.g. the button resource contains key and value pairs where the key creates the button and the value sets the action). Overall, these resource files help make the program more data-driven
    * ButtonResource.properties
    * CheckBoxResource.properties
    * ColorPalette.properties
    * DropDownResource.properties
    * SliderResource.properties
    * TabsResource.properties
    * UIControllerResource.properties
* resources.frontend.changingfeature/ -- names for UI features such as buttons, check-boxes, color palettes, etc.
    * ButtonNames.properties
    * CheckBoxNames.properties
    * ColorPaletteNames.properties
    * DropDownNames.properties
    * LanguageIndex.properties
    * ScrollPaneNames.properties
    * SliderNames.properties
* resources.frontend.commandreference.boolean/ -- files under commandreference provide the neccessary information to display command instructions when the help button is clicked 
    * resources.frontend.commandreference.boolean.language/
        * English.properties
    * CommandDescription.properties
* resources.frontend.commandreference.display/
    * resources.frontend.commandreference.boolean.language/
        * English.properties
    * CommandDescription.properties
* resources.frontend.commandreference.math/
    * resources.frontend.commandreference.boolean.language/
        * English.properties
    * CommandDescription.properties
* resources.frontend.commandreference.movement/
    * resources.frontend.commandreference.boolean.language/
        * English.properties
    * CommandDescription.properties
* resources.frontend.commandreference.query/
    * resources.frontend.commandreference.boolean.language/
        * English.properties
    * CommandDescription.properties
* resources.frontend.dropdown/ -- these files contain all the information/choices needed for the drop downs
    * Image.properties
    * Language.properties
    * Pensize.properties
* resources.images/ -- images used for the turtle
    * turtle.png
    * panda.png
    * yoda.png
    * beaver.png
    * spongebob.png

* resources.languages/ -- translations of commands supported to these different languages
    * Chinese.properties
    * English.properties
    * French.properties
    * German.properties
    * Italian.properties
    * Portuguese.properties
    * Russian.properties
    * Spanish.properties
    * Syntax.properties
    * Urdu.properties
 

### Decisions, assumptions, or simplifications made to handle vague, ambiguous, or conflicting requirements
* We assumed the error messages displayed to the user could be shown in English



### Known Bugs
* Commands such as ```:x sum :x 1``` that require assigning a value to a variable while accessing it to do the assignment do not work when the variable has not been previously set
* Movement commands cannot recognize commands that are not integers, variables, or basic commands (.e.g, ```fd set :x 10 ``` does not correctly move turtle forward and will not recognize `set` as a valid command, but it will recognize ```fd sum 10 10```)
* Quotient command do not work completely (returns value that, when executed with example files, do not generate the expected image)
* Return value for `Tell` is not accurate (returns id of last turtle instead of instructions associated with this turtle)

### Extra Features
* UI changes languages according to the language selected. 

### Assignment Impressions

Overall, creating an effective design for the initial project specifications (Basic) was the most challenging part of the assignment.  We spent a significantly longer period of time working on our Plan.md document than in previous projects, as we initally had a difficult time understanding how to design interfaces.  Even with spending much time planning, we ended up implementing a design more extensive and robust in the Basic, and ultimately, Complete stages of the project.  Our final design decisions were driven by the primary goal of encapulsation.  We approached the separation of our backend and frontend components by imagining being able to replace the frontend with a different interface, such as a website. Moreover, by using encapsulation, we were able create flexibility in internal design decisions as long as the external API contract is upheld. 

