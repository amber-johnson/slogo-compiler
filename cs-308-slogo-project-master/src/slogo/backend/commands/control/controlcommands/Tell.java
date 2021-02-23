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
 * Purpose: Executes Tell command by telling the turtles given in the first parameter to follow the commands fron here on out
 * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
 * a parameters list with less than 1 values will cause the class to fail; however, if error-checking are done appropriately
 * in the outer classes, this should not be the case.
 * Dependencies; on a specific TurtleHistory that is passed to it from higher classes and on the CommandBlockManager
 * The example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */

public class Tell implements ControlInterface {
    /**
     * Purpose: Executes Tell command by telling the turtles given in the first parameter to follow the commands fron here on out
     * Assumptions: Assumes that these parameters are strings and that they exist (this is checked in the ControlExecutor); so
     * a parameters list with less than 1 values will cause the class to fail; however, if error-checking are done appropriately
     * in the outer classes, this should not be the case.
     * @param turtleHistory the turtle history class that manages all turtles, of which the command will be executed on
     * @param parameters arguments for the control; the 0th element is the String of ids of the turtles that will follow
     *                   the commands from here on out
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the commands run by the last turtle
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */

    @Override
    public double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException {
        String turtlesToActivateArgument = parameters.get(0).toString();

        List<Integer> turtlesToActivate = setActivatedTurtles(turtleHistory, accessibleVariables, turtlesToActivateArgument);
        turtleHistory.setActiveTurtles(turtlesToActivate);
        return turtleHistory.getActiveTurtles().get(turtleHistory.getActiveTurtles().size()-1);
    }

    protected List<Integer> setActivatedTurtles(TurtleHistory turtleHistory, List<Map<String,Double>> accessibleVariables, String turtlesToActivateArgument) throws BackendException {
        List<Integer> turtlesToActivate = new ArrayList<>();
        PeekableScanner turtlesScanner = new PeekableScanner(turtlesToActivateArgument);
        while (turtlesScanner.hasNext()) {
            CommandTree commandTree = new CommandTree(turtleHistory);
            while (!commandTree.onlyNumberLeft()) {
                String turtleArgument = turtlesScanner.next();
                turtleArgument = CommandBlockManager.checkAndInputUserVariable(turtleArgument, accessibleVariables);
                commandTree.addToCommandTree(turtleArgument);
            }
            turtlesToActivate.add((int) commandTree.getLastDouble());
        }
        return turtlesToActivate;
    }
}
