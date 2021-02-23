package slogo.backend.commands.control.controlcommands;

import slogo.backend.commands.control.ControlInterface;
import slogo.backend.utils.TurtleHistory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Purpose: Makes a UserDefined command given the variables and commands associated with this user defined command
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 3 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies: the class depends on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager class.
 * An example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */
public class MakeUserInstruction implements ControlInterface {
    /**
     * Purpose: Makes a UserDefined command given the variables and commands associated with this user defined command
     * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
     * a parameters list with less than 3 values will cause the class to fail; however, if error-checking are done appropriately
     * in the outer classes, this should not be the case.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command can be executed on
     * @param parameters arguments for the control; the 0th element is the method name and the 1st element is the user
     *                   function map needed to put the user command into and the 2nd element is the variables and string
     *                   commands definition of that command.
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return 1 if the instruction successfully executes
     */
    @Override
    public double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) {
        String methodName = parameters.get(0).toString();
        List<Object> blockArguments = (List<Object>) parameters.get(1);
        Map<String,List<Object>> userFunctionsMap = (Map<String, List<Object>>) parameters.get(2);
        String variablesAsString = blockArguments.get(0).toString();
        String commandsAsString = blockArguments.get(1).toString();

        List<Object> commandArgumentsToStore = new ArrayList<>();

        List<String> variables = Arrays.asList(variablesAsString.split(" "));
        Map<String, Double> variablesMap = new LinkedHashMap<>();
        for (String variable : variables) {
            variablesMap.put(variable, null);
        }

        commandArgumentsToStore.add(variablesMap);
        commandArgumentsToStore.add(commandsAsString);

        userFunctionsMap.put(methodName, commandArgumentsToStore);

        return 1;
    }
}
