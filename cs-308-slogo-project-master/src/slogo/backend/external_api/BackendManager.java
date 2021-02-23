package slogo.backend.external_api;

import slogo.backend.commands.CommandBlockManager;
import slogo.backend.exceptions.BackendException;
import slogo.backend.parser.Parser;
import slogo.backend.utils.TurtleHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instantiates backend classes needed to translate and execute the turtle commands,
 * including Parser.java and CommandBlockManager.java.
 * Used in Connector.java
 *
 * Ex:  BackendManager myBackEndManager = new BackendManager(myVisualization.getInput(),
 *      myVisualization.getLanguage(), new TurtleHistory());
 *
 * @author Eric Han, Ha Nguyen, and Amber Johnson
 */

public class BackendManager {

    private CommandBlockManager commandBlockManager;
    private Parser myCommandParser;
    private TurtleHistory turtleHistory;
    private Map<String, List<Object>> userDefinedFunctions = new HashMap<>();
    private List<String> userDefinedFunctionsAsString = new ArrayList<>();

    public BackendManager(String input, String language, TurtleHistory turtleHistory) {
        setCommands(input, language);
        this.turtleHistory = turtleHistory;
    }

    /**
     * Creates a new Parser.java object.
     * Accepts the String input which comes from the user input; and,
     * Accepts the language String from the dropdown option on the GUI.
     */
    public void setCommands(String input, String language) {
        myCommandParser = new Parser(input, language);
    }

    /**
     * Translates the user input from any of the languages available to standardized commands in English;
     * Instantiates a new CommandBlockManager object to begin control;
     * Re-initializes variables userDefinedFunctionsAsString and userDefinedFunctions; and
     * Executes the translated commands by calling the executeInstructionBlock() method.
     * Throws a BackendException
     */
    public void executeCommands() throws BackendException {
        String translatedCommand = myCommandParser.translateCommands();
        turtleHistory.clearHistory();
        commandBlockManager = new CommandBlockManager(translatedCommand, turtleHistory, new ArrayList<>(), userDefinedFunctions);
        userDefinedFunctionsAsString.clear();
        userDefinedFunctions.clear();
        commandBlockManager.executeInstructionBlock();
        userDefinedFunctions.putAll(commandBlockManager.getUserDefinedFunctions());
        userDefinedFunctionsAsString.addAll(commandBlockManager.getUserDefinedFunctionsAsStrings());
    }

    /**
     * Adds user-defined functions to a list; and,
     * Gets the new list.
     *
     * @return a list of Strings that represent user-defined functions
     */
    public List<String> getUserFunctions() {
        List<String> list = new ArrayList<>();
        list.addAll(userDefinedFunctionsAsString);
        return list;
    }

    /**
     * Gets a TurtleHistory object.
     *
     * @return a TurtleHistory object
     */
    public TurtleHistory getHistory() {
        return turtleHistory;
    }
}
