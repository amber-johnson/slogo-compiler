package slogo.backend.commands.control.controlcommands;

import slogo.backend.commands.CommandBlockManager;
import slogo.backend.commands.control.ControlInterface;
import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.TurtleHistory;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Purpose: Executes a UserDefined function given the numerical arguments, the method parameters, and the commands within
 * user defined function
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 3 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies: the class depends on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager class.
 * An example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */
public class UserDefined implements ControlInterface {
    /**
     * Purpose: Executes a UserDefined function given the numerical arguments, the method parameters, and the commands within
     * user defined function
     * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
     * a parameters list with less than 3 values will cause the class to fail; however, if error-checking are done appropriately
     * in the outer classes, this should not be the case.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command will be executed on
     * @param parameters arguments for the control; the 0th element is the numerical arguments to be plugged into the user's parameters;
     *                   the 1st element is the method parameters; the 2nd element is the commands to be run
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the last command run
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */
    @Override
    public double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException {
        List<Double> numericalArguments = (List<Double>) parameters.get(0);
        Map<String, Double> methodParameters = (LinkedHashMap<String, Double>) parameters.get(1);
        String userDefinedCommandBlockArgument = parameters.get(2).toString();
        List<Map<String, Double>> currentAccessibleVariables = accessibleVariables;
        currentAccessibleVariables.add(methodParameters);

        int numericalArgumentsIndex = 0;
        for (String key : methodParameters.keySet()) {
            methodParameters.put(key, numericalArguments.get(numericalArgumentsIndex));
            numericalArgumentsIndex++;
        }

        CommandBlockManager commandBlockManager = new CommandBlockManager(userDefinedCommandBlockArgument, turtleHistory, currentAccessibleVariables, definedFunctions);
        commandBlockManager.executeInstructionBlock();
        return 1;
    }
}
