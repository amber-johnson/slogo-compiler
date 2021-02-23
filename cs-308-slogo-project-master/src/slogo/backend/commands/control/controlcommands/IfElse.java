package slogo.backend.commands.control.controlcommands;

import slogo.backend.commands.CommandBlockManager;
import slogo.backend.commands.control.ControlInterface;
import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.TurtleHistory;

import java.util.List;
import java.util.Map;

/**
 * Purpose: Executes IfElse command by executing the commands given in the second item of the parameter argument if the value condition given in the
 * first parameter is 1 (true) and the commands given in the third item of the parameter argument if the condition given
 * the first item of the parameter evaluates to 0 (false)
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 3 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies: the class depends on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager class.
 * An example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */

public class IfElse extends If implements ControlInterface {

    /**
     * Purpose: Executes IfElse command by executing the commands given in the second item of the parameter argument if the value condition given in the
     * first parameter is 1 (true) and the commands given in the third item of the parameter argument if the condition given
     * the first item of the parameter evaluates to 0 (false)
     * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
     * a parameters list with less than 3 values will cause the class to fail; however, if error-checking are done appropriately
     * in the outer classes, this should not be the case.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command will be executed on
     * @param parameters arguments for the control; the 0th element is the String that specifies the if condition
     *                   the commands and the 1st element is the String that executes if the if condition evaluates to true
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the final command
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */
    public double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException {
        String ifElseConditionArgument = parameters.get(0).toString();
        String ifBlockCommandArgument = parameters.get(1).toString();
        String elseBlockCommandArgument = parameters.get(2).toString();

        double conditionValue = calculateConditionValue(ifElseConditionArgument, turtleHistory, accessibleVariables);

        if (conditionValue != 0) {
            CommandBlockManager trueBlockManager = new CommandBlockManager(ifBlockCommandArgument, turtleHistory, accessibleVariables, definedFunctions);
            return trueBlockManager.executeInstructionBlock();
        } else {
            CommandBlockManager falseBlockManager = new CommandBlockManager(elseBlockCommandArgument, turtleHistory, accessibleVariables, definedFunctions);
            return falseBlockManager.executeInstructionBlock();
        }
    }
}
