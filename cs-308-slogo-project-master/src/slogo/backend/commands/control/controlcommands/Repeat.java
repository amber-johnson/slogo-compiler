package slogo.backend.commands.control.controlcommands;

import slogo.backend.commands.CommandBlockManager;
import slogo.backend.commands.control.ControlInterface;
import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.TurtleHistory;

import java.util.List;
import java.util.Map;

/**
 * Purpose: Executes Repeat command by executing the commands given in the second parameter the number of times specified in the first parameter
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 2 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies: the class depends on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager class.
 * An example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */
public class Repeat implements ControlInterface {

    /**
     * Purpose: Executes Repeat command by executing the commands given in the second parameter the number of times specified in the first parameter
     * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
     * a parameters list with less than 2 values will cause the class to fail; however, if error-checking are done appropriately
     * in the outer classes, this should not be the case.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command will be executed on
     * @param parameters arguments for the control; the 0th element is String that specifies how many times this command is to be repeated
     *                   the commands and the 1st element is the String of the commands themselves.
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the last command run
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */

    public double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException {

        String repeatCondition = parameters.get(0).toString();
        String repeatBlockCommandArgument = parameters.get(1).toString();
        double returnValue = 0;

        CommandBlockManager conditionCommandBlockManager = new CommandBlockManager(repeatCondition, turtleHistory, accessibleVariables, definedFunctions);
        double numTimesRepeated = conditionCommandBlockManager.executeInstructionBlock();

        for (int i=0; i<(int) numTimesRepeated; i++) {
            CommandBlockManager commandBlockManager = new CommandBlockManager(repeatBlockCommandArgument, turtleHistory, accessibleVariables, definedFunctions);
            returnValue = commandBlockManager.executeInstructionBlock();
        }
        return returnValue;
    }
}

