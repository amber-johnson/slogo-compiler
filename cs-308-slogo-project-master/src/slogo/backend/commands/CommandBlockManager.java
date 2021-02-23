package slogo.backend.commands;

import slogo.backend.commands.control.ControlExecutor;
import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.CommandTree;
import slogo.backend.utils.TurtleHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Purpose: The CommandBlockManager executes a String of commands and manages the control flow and all the variables, functions,
 * and active turtles defined for this String block of commands.
 * Assumptions: This class assumes the existence of everything in its constructor parameters. As such, when an accessibleVariables list or
 * definedFunction map has not yet been created, the user should pass in a new instance of these classes which the
 * CommandBlockManager can build off of. This class also depends on the TurtleHistory class, as its commands
 * directly execute commands that make changes to this class.
 * Example of usage: CommandBlockManager commandBlockManager = new CommandBlockManager(arguments, turtleHistory, accessibleVariables, definedFunctions);
 *                   double returnValue = commandBlockManager.executeInstructionBlock();
 * Additional details: none
 * @author Ha Nguyen, Eric Han
 */

public class CommandBlockManager {
    private static final String BLOCK_CONTROLS_RESOURCE_PATH = "resources/DefinedControls";
    private static final String MOVEMENT_COMMANDS_RESOURCE_PATH = "resources/DefinedMovementCommands";
    private static final String DEFINE_USER_VARIABLE_COMMAND = "MakeVariable";
    private static final String DEFINE_USER_INSTRUCTION_COMMAND = "MakeUserInstruction";
    private static final String EXECUTE_USER_DEFINED_COMMAND = "UserDefined";
    private static final ResourceBundle CONTROLS_RESOURCE_BUNDLE = ResourceBundle.getBundle(BLOCK_CONTROLS_RESOURCE_PATH);
    private static final ResourceBundle MOVEMENT_COMMANDS_RESOURCE_BUNDLE = ResourceBundle.getBundle(MOVEMENT_COMMANDS_RESOURCE_PATH);
    private static final String NON_BLOCK_ARGUMENT_END_SIGNAL = "[";
    private static final String BLOCK_ARGUMENT_END_SIGNAL = "]";
    private static final String BLOCK_ARGUMENT_BEGIN_SIGNAL = "[";
    private static final char USER_DEFINED_SIGNAL = ':';

    private String myCommandBlockString;
    private ControlExecutor myControlExecutor;
    private CommandTree myCommandTree;
    private TurtleHistory myTurtleHistory;
    private PeekableScanner myScanner;
    private Map<String, Double> myLocalUserDefinedVariables;
    private List<Map<String, Double>> myAccessibleVariables;
    private Map<String,List<Object>> myAccessibleUserDefinedFunctions;
    private List<Integer> myActiveTurtles;

    /**
     * Purpose: Initializes a CommandBlockManager object given a String, the turtle history, and all the higher-scope variables and functions
     * Assumptions: assumes the existence of everything in its constructor parameters. As such, when an accessibleVariables list or
     * definedFunction map has not yet been created, the user should pass in a new instance of these classes which the
     * CommandBlockManager can build off of. This class also depends on the TurtleHistory class for which its commands can make changes to.
     * @param commandBlock the String of commands to be executed
     * @param turtleHistory the TurtleHistory of which commands can act upon
     * @param higherScopeVariables the higher-scope variables that this specific CommandBlockManager has access to
     * @param definedFunctions the higher-scope functions that this specific CommandBlockManager has access to
     */
    public CommandBlockManager(String commandBlock, TurtleHistory turtleHistory, List<Map<String,Double>> higherScopeVariables, Map<String, List<Object>> definedFunctions) {
        myCommandBlockString = commandBlock;
        myTurtleHistory = turtleHistory;
        myCommandTree = new CommandTree(myTurtleHistory);
        myControlExecutor = new ControlExecutor();
        myScanner = new PeekableScanner(myCommandBlockString);
        myLocalUserDefinedVariables = new HashMap<>();
        myAccessibleVariables = new ArrayList<>();
        myAccessibleVariables.addAll(higherScopeVariables);
        myAccessibleVariables.add(myLocalUserDefinedVariables);
        myAccessibleUserDefinedFunctions = new HashMap<>();
        myAccessibleUserDefinedFunctions.putAll(definedFunctions);
        myActiveTurtles = myTurtleHistory.getActiveTurtles();
        System.out.println("Full command string of this block: " + myCommandBlockString);
    }

    /**
     * Purpose: executes the instruction block that is in this CommandBlock.
     * Assumptions: none
     * @return the double value that results from the execution process
     * @throws BackendException that occurs as a result exceptions happening in the execution of lower-level classes and methods
     * (e.g., lower-level blocks, commands, ControlExecutor, unmatched brackets, etc.)
     */
    public double executeInstructionBlock() throws BackendException {
        double returnValue = 0;
        while (myScanner.hasNext()) {
            String command = myScanner.next();
            command = checkAndInputUserVariable(command, myAccessibleVariables);
            System.out.println(command + " will now be put somewhere");
            if (CONTROLS_RESOURCE_BUNDLE.containsKey(command)) {
                returnValue = buildAndExecuteControlCommand(command);
            } else if (myAccessibleUserDefinedFunctions.containsKey(command)) {
                returnValue = buildAndExecuteUserDefinedCommand(command);
            } else {
                returnValue = buildAndExecuteBasicCommand(command);
            }
        }
        if (myCommandTree.onlyNumberLeft()) {
            returnValue = myCommandTree.getLastDouble();
        }
        return returnValue;
    }

    /**
     * Purpose: Gets a copy of the map of the user-defined functions for saving in the BackendManager.
     * Assumptions: that the client calling this method is looking to read but not modify the values of the object itself.
     * @return the copy of the map of accessible user defined function.
     */
    public Map<String, List<Object>> getUserDefinedFunctions() {
        return Map.copyOf(myAccessibleUserDefinedFunctions);
    }

    /**
     * Purpose: Gets the list of string representation of user-defined functions for display on the front-end
     * Assumptions: none
     * @return the list of String representations of the method in the form of "methodName :variable1 :variable2".
     */
    public List<String> getUserDefinedFunctionsAsStrings() {
        List<String> userDefinedFunctionsAsString = new ArrayList<>();
        for (Map.Entry<String, List<Object>> entry : myAccessibleUserDefinedFunctions.entrySet()) {
            StringBuilder functionAsString = new StringBuilder();
            functionAsString.append(entry.getKey() + " ");
            Map<String, Double> methodParametersMap = (Map<String, Double>) entry.getValue().get(0);
            for (String key : methodParametersMap.keySet()) {
                functionAsString.append(key + " ");
            }
            userDefinedFunctionsAsString.add(functionAsString.toString());
        }
        return userDefinedFunctionsAsString;
    }

    /**
     * Purpose: Given a user-defined variable, replace the user-defined variable with its value when it is called
     * Assumptions: none
     * @param command the command to check and replace with a variable
     * @param accessibleVariables the map of variables to of which to check the map against
     * @return the replacement String for the command that is its value in the variables map
     */
    public static String checkAndInputUserVariable(String command, List<Map<String, Double>> accessibleVariables) {
        if (command.charAt(0) == USER_DEFINED_SIGNAL) {
            for (Map<String, Double> variableMap : accessibleVariables) {
                if (variableMap.containsKey(command)) {
                    return variableMap.get(command).toString();
                }
            }
            Map<String, Double> mostLocalMap = accessibleVariables.get(accessibleVariables.size()-1);
            mostLocalMap.put(command, 0.0);
            return mostLocalMap.get(command).toString();
        }
        return command;
    }

    /**
     * Purpose: Returns a copy of the map of the variables for this specific scope for saving and displaying on the front-end
     * Assumptions: the client calling this method is only looking to read the variables, not modify it
     * @return the copy of the current map of variables for the current scope
     */
    public Map<String, Double> getVariables() {
        Map<String, Double> variables = new LinkedHashMap<>();
        variables.putAll(myLocalUserDefinedVariables);
        return variables;
    }

    private double buildAndExecuteControlCommand(String command) throws BackendException {
        double returnValue = 0;
        List<Object> commandArguments;
        if (command.equals(DEFINE_USER_VARIABLE_COMMAND)) {
            commandArguments = new ArrayList<>() {{
                add(myScanner);
            }};
        } else if (command.equals(DEFINE_USER_INSTRUCTION_COMMAND)) {
            commandArguments = new ArrayList<>();
            String methodName = myScanner.next();
            commandArguments.add(methodName);
            commandArguments.add(prepareBlockCommand());
            commandArguments.add(myAccessibleUserDefinedFunctions);
        } else {
            commandArguments = prepareBlockCommand();
        }
        returnValue = myControlExecutor.execute(command, commandArguments, myTurtleHistory, myAccessibleVariables, myAccessibleUserDefinedFunctions);
        myCommandTree.addToCommandTree(returnValue+"");
        return returnValue;
    }

    private double buildAndExecuteUserDefinedCommand(String command) throws BackendException {
        double returnValue = 0;
        List<Object> commandArguments = prepareUserDefinedFunction(command);
        returnValue = myControlExecutor.execute(EXECUTE_USER_DEFINED_COMMAND, commandArguments, myTurtleHistory, myAccessibleVariables, myAccessibleUserDefinedFunctions);
        myCommandTree.addToCommandTree(returnValue+"");
        return returnValue;
    }

    private double buildAndExecuteBasicCommand(String command) throws BackendException {
        double returnValue = 0;
        System.out.println(command + " is inside the tree now");
        if (MOVEMENT_COMMANDS_RESOURCE_BUNDLE.containsKey(command)) {
            command = rerunMovementCommands(command) + "";
        }
        myCommandTree.addToCommandTree(command);
        return returnValue;
    }

    private double rerunMovementCommands(String command) throws BackendException {
        int index = myScanner.getIndex() - 1;
        double returnVal = 0;
        myActiveTurtles.clear();
        myActiveTurtles.addAll(myTurtleHistory.getActiveTurtles());
        for(int i=0; i<myActiveTurtles.size(); i++) {
            System.out.println("This is what the turtle executes: " + "for turtle # " + myActiveTurtles.get(i) + " executing ");
            myScanner.goToIndex(index);

            CommandTree repeatCommandTree = new CommandTree(myTurtleHistory);
            repeatCommandTree.setTurtleID(myActiveTurtles.get(i));

            while (!repeatCommandTree.onlyNumberLeft() && myScanner.hasNext()) {
                command = myScanner.next();
                command = checkAndInputUserVariable(command, myAccessibleVariables);
                repeatCommandTree.addToCommandTree(command);
            }
            returnVal = repeatCommandTree.getLastDouble();
        }
        myTurtleHistory.toNextTurn(mergeAllAccessibleVariables());
        return returnVal;
    }

    private List<Object> prepareUserDefinedFunction(String command) throws BackendException {
        List<Object> commandArguments = new ArrayList<>();
        List<Double> numericalArgumentForMethod = new ArrayList<>();
        Map<String, Double> parameters = (Map<String, Double>) myAccessibleUserDefinedFunctions.get(command).get(0);
        if (parameters.size() == 1 && parameters.keySet().contains("")) {
            parameters.remove("");
        }
        int parametersNeeded = parameters.size();

        for (int i=0; i<parametersNeeded; i++) {
            String argument = myScanner.next();
            argument = checkAndInputUserVariable(argument, myAccessibleVariables);
            myCommandTree.addToCommandTree(argument);
            while (!myCommandTree.onlyNumberLeft()) {
                argument = myScanner.next();
                argument = checkAndInputUserVariable(argument, myAccessibleVariables);
                myCommandTree.addToCommandTree(argument);
            }
            if (myCommandTree.onlyNumberLeft()) {
                numericalArgumentForMethod.add(myCommandTree.getLastDouble());
            }
        }
        commandArguments.add(numericalArgumentForMethod);
        commandArguments.addAll(myAccessibleUserDefinedFunctions.get(command));
        return commandArguments;
    }

    private List<Object> prepareBlockCommand() throws BackendException {
        List<Object> controlCommandArguments = new ArrayList<>();
        if (! myScanner.peek().equals(BLOCK_ARGUMENT_BEGIN_SIGNAL)) {
            buildIndividualControlArgument(NON_BLOCK_ARGUMENT_END_SIGNAL, controlCommandArguments);
        } else {
            myScanner.next();
        }
        buildIndividualControlArgument(BLOCK_ARGUMENT_END_SIGNAL, controlCommandArguments);
        return controlCommandArguments;
    }

    private void buildIndividualControlArgument(String endSignaler, List<Object> arguments) throws BackendException {
        addFirstArgument(endSignaler, arguments);
        checkAndAddAdditionalArguments(endSignaler, arguments);
    }

    private void addFirstArgument(String endSignaler, List<Object> arguments) throws BackendException {
        StringBuilder builder = new StringBuilder();
        String nextWord = myScanner.next();
        int endSignalersNeeded = 1;
        if (! nextWord.equals(BLOCK_ARGUMENT_END_SIGNAL)) {
            while (endSignalersNeeded != 0) {
                builder.append(nextWord + " ");
                if(!myScanner.hasNext()) {
                    throw new BackendException("Unmatched number of brackets");
                }
                nextWord = myScanner.next();
                if (endSignaler.equals(BLOCK_ARGUMENT_END_SIGNAL) && nextWord.equals(BLOCK_ARGUMENT_BEGIN_SIGNAL)) {
                    endSignalersNeeded++;
                } else if (endSignaler.equals(BLOCK_ARGUMENT_END_SIGNAL) && nextWord.equals(BLOCK_ARGUMENT_END_SIGNAL) ||
                        endSignaler.equals(NON_BLOCK_ARGUMENT_END_SIGNAL) && nextWord.equals(NON_BLOCK_ARGUMENT_END_SIGNAL)) {
                    endSignalersNeeded--;
                }
            }
        }
        String argument = builder.toString();
        arguments.add(argument);
    }

    private Map<String, Double> mergeAllAccessibleVariables() {
        Map<String, Double> mergedMap = new LinkedHashMap<>();
        for (Map<String, Double> variableMap : myAccessibleVariables) {
            mergedMap.putAll(variableMap);
        }
        return mergedMap;
    }

    private void checkAndAddAdditionalArguments(String endSignaler, List<Object> arguments) throws BackendException {
        if (myScanner.hasNext() && endSignaler.equals(BLOCK_ARGUMENT_END_SIGNAL) && myScanner.peek().equals(BLOCK_ARGUMENT_BEGIN_SIGNAL)) {
                myScanner.next();
                buildIndividualControlArgument(endSignaler, arguments);
        }
    }
}
