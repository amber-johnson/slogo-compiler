package slogo.backend.commands.control.controlcommands;

import slogo.backend.commands.CommandBlockManager;
import slogo.backend.commands.control.ControlInterface;
import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.TurtleHistory;

import java.util.List;
import java.util.Map;

/**
 * Purpose: Executes Ask command by telling the turtles given in the first parameter to follow the commands given in the second
 * parameter.
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 2 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies; on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager;
 * it also depends on the super class Tell, which provides the method for setting activated turtles.
 * The example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */
public class Ask extends Tell implements ControlInterface {

    /**
     * Executes Ask command by telling the turtles given in the first element of parameters to follow the the commands
     * given in the second element of parameters.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command will be executed on
     * @param parameters arguments for the control; the 0th element is the String of ids of the turtles that will follow
     *                   the commands and the 1st element is the String of the commands themselves.
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the commands run by the last turtle
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */

    @Override
    public double execute(TurtleHistory turtleHistory,
                          List<Object> parameters,
                          List<Map<String, Double>> accessibleVariables,
                          Map<String, List<Object>> definedFunctions) throws BackendException {

        List<Integer> previousActiveTurtles = turtleHistory.getActiveTurtles();
        String turtlesToActivateArgument = parameters.get(0).toString();
        String askCommandsArgument = parameters.get(1).toString();

        List<Integer> turtlesToActivate = setActivatedTurtles(turtleHistory, accessibleVariables, turtlesToActivateArgument);

        turtleHistory.setActiveTurtles(turtlesToActivate);

        CommandBlockManager commandBlockManager = new CommandBlockManager(askCommandsArgument, turtleHistory, accessibleVariables, definedFunctions);
        double returnValue = commandBlockManager.executeInstructionBlock();

        turtleHistory.setActiveTurtles(previousActiveTurtles);

        return returnValue;
    }
}
