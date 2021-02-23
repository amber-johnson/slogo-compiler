package slogo.backend.commands.control.controlcommands;

import slogo.backend.commands.CommandBlockManager;
import slogo.backend.commands.PeekableScanner;
import slogo.backend.commands.control.ControlInterface;
import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.CommandTree;
import slogo.backend.utils.TurtleHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Purpose: Executes For command by executing the commands given in the second parameter for the start, end, increment, values specified in the
 * range given in the first parameter.
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 2 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies: the class depends on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager class.
 * An example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */

public class For implements ControlInterface {
    public static final int START_END_INCREMENT_ARGUMENT_NUMBERS = 3;

    /**
     * Purpose: Executes For command by executing the commands given in the second parameter for the start, end, increment values specified in the
     * range given in the first parameter with the commands specified in the second parameter
     * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
     * a parameters list with less than 2 values will cause the class to fail; however, if error-checking are done appropriately
     * in the outer classes, this should not be the case.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command will be executed on
     * @param parameters arguments for the control; the 0th element is the String that specify the start, end, and increment
     *                   and the 1st element is the String of the commands themselves.
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the final command
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */

    @Override
    public double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException {
        String startEndIncrementArgument = parameters.get(0).toString();
        String forCommandArgument = parameters.get(1).toString();

        PeekableScanner scanner = new PeekableScanner(startEndIncrementArgument);
        Map<String, Double> localVariables = accessibleVariables.get(accessibleVariables.size()-1);

        String variable = scanner.next();

        List<Double> parameterValues = calculateParameterValues(scanner, turtleHistory, accessibleVariables);

        double start = parameterValues.get(0);
        double end = parameterValues.get(1);
        double increment = parameterValues.get(2);

        double returnValue = 0;
        for (int i = (int) start; i <= end; i+=increment) {
            localVariables.put(variable, (double) i);
            CommandBlockManager commandBlockManager = new CommandBlockManager(forCommandArgument, turtleHistory, accessibleVariables, definedFunctions);
            returnValue = commandBlockManager.executeInstructionBlock();
        }
        localVariables.remove(variable);

        return returnValue;
    }

    private List<Double> calculateParameterValues(PeekableScanner scanner, TurtleHistory turtleHistory, List<Map<String, Double>> accessibleVariables) throws BackendException {
        List<Double> parameterValues = new ArrayList<>();

        for (int i=0; i<START_END_INCREMENT_ARGUMENT_NUMBERS; i++) {
            CommandTree commandTree = new CommandTree(turtleHistory);
            while (!commandTree.onlyNumberLeft()) {
                String nextVal = scanner.next();
                nextVal = CommandBlockManager.checkAndInputUserVariable(nextVal, accessibleVariables);
                commandTree.addToCommandTree(nextVal);
            }
            parameterValues.add(commandTree.getLastDouble());
        }

        return parameterValues;
    }
}
