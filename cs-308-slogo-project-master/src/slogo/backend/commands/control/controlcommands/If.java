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
 * Purpose: Executes If command by executing the commands given in the second parameter if the value condition given in the
 * first parameter is 1 (true)
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 2 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies: the class depends on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager class.
 * An example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */

public class If implements ControlInterface {
    /**
     * Executes Ask command by telling the turtles given in the first element of parameters to follow the the commands
     * given in the second element of parameters.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command will be executed on
     * @param parameters arguments for the control; the 0th element is the String that specifies the if condition
     *                   the commands and the 1st element is the String that executes if the if condition evaluates to true
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the final command
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */
    public double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException {
        String ifConditionArgument = parameters.get(0).toString();
        String trueBlockCommandArgument = parameters.get(1).toString();

        double conditionValue = calculateConditionValue(ifConditionArgument, turtleHistory, accessibleVariables);

        if (conditionValue != 0) {
            CommandBlockManager trueBlockManager = new CommandBlockManager(trueBlockCommandArgument, turtleHistory, accessibleVariables, definedFunctions);
            return trueBlockManager.executeInstructionBlock();
        } else {
            return 0;
        }
    }

    protected double calculateConditionValue(String ifConditionArgument, TurtleHistory turtleHistory, List<Map<String, Double>> accessibleVariables) throws BackendException {
        CommandTree commandTree = new CommandTree(turtleHistory);
        double conditionValue = 0;
        PeekableScanner conditionScanner = new PeekableScanner(ifConditionArgument);
        while (conditionScanner.hasNext()) {
            String command = conditionScanner.next();
            System.out.println("If condition, current passing to command tree: " + command);
            command = CommandBlockManager.checkAndInputUserVariable(command, accessibleVariables);
            commandTree.addToCommandTree(command);
        }
        if(commandTree.onlyNumberLeft()) {
            conditionValue = commandTree.getLastDouble();
        }
        return conditionValue;
    }
}
