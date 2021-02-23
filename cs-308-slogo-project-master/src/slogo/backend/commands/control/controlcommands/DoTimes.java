package slogo.backend.commands.control.controlcommands;

import slogo.backend.commands.CommandBlockManager;
import slogo.backend.commands.PeekableScanner;
import slogo.backend.commands.control.ControlInterface;
import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.CommandTree;
import slogo.backend.utils.TurtleHistory;

import java.util.List;
import java.util.Map;

/**
 * Purpose: Executes DoTimes command by executing the commands given in the second parameter for the value specified in the
 * range given in the first parameter.
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 2 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies: the class depends on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager class.
 * An example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */

public class DoTimes implements ControlInterface {
    /**
     * Purpose: Executes DoTimes command by executing the commands given in the second parameter for the value specified in the
     * range given in the first parameters.
     * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
     * a parameters list with less than 2 values will cause the class to fail; however, if error-checking are done appropriately
     * in the outer classes, this should not be the case.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command will be executed on
     * @param parameters arguments for the control; the 0th element is range/variable limit at which it should stop executing
     *                   and the 1st element is the String of the commands themselves.
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the final command
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */

    @Override
    public double execute(TurtleHistory turtleHistory,
                          List<Object> parameters,
                          List<Map<String, Double>> accessibleVariables,
                          Map<String, List<Object>> definedFunctions) throws BackendException {
        Map<String, Double> localVariables = accessibleVariables.get(accessibleVariables.size()-1);
        String variableLimitArgument = parameters.get(0).toString();
        String doTimesCommandArgument = parameters.get(1).toString();

        PeekableScanner variableScanner = new PeekableScanner(variableLimitArgument);
        String variable = variableScanner.next();

        double returnValue = 0;

        double variableLimit = calculateVariableLimit(variableScanner, turtleHistory, accessibleVariables);

        for (int i=0; i<variableLimit; i++) {
            localVariables.put(variable, (double) i);
            CommandBlockManager commandBlockManager = new CommandBlockManager(doTimesCommandArgument, turtleHistory, accessibleVariables, definedFunctions);
            returnValue = commandBlockManager.executeInstructionBlock();
        }
        localVariables.remove(variable);

        return returnValue;
    }

    private double calculateVariableLimit(PeekableScanner variableScanner, TurtleHistory turtleHistory, List<Map<String, Double>> accessibleVariables) throws BackendException {
        CommandTree commandtree = new CommandTree(turtleHistory);
        while (variableScanner.hasNext()) {
            String valueToCalculate = variableScanner.next();
            valueToCalculate = CommandBlockManager.checkAndInputUserVariable(valueToCalculate, accessibleVariables);
            commandtree.addToCommandTree(valueToCalculate);
        }
        double variableLimit = 0.0;
        if (commandtree.onlyNumberLeft()) {
            variableLimit = commandtree.getLastDouble();
        }
        return variableLimit;
    }
}
