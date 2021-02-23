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
 * Purpose: Makes a user defined variable given the scanner object by the CommandBlockManager
 * Assumptions: Assumes scanner just scanned MakeVariable command; thus, the next value will be the variable's name
 * Dependencies: Depends on the scanner of the CommandBlockManager
 * An example for how to use this class is not relevant in this case because of the Factory Pattern, so the client that
 * requests this class's service simply does so through the factory, and does not need to know how to actually use this class.
 * @author: Ha Nguyen
 */

public class MakeVariable implements ControlInterface {

    /**
     * Purpose: Makes a user defined variable given the scanner object by the CommandBlockManager
     * Assumptions: Assumes scanner just scanned MakeVariable command; thus, the next value will be the variable's name
     * Dependencies: Depends on the scanner of the CommandBlockManager
     * @param turtleHistory the turtle history class that manages all turtles, of which the command can be executed on
     * @param parameters the scanner that will continue scanning until a double value is calculated to put into the variable
     * @param accessibleVariables all the variables that this command has access to
     * @param definedFunctions all the functions that this command has access to
     * @return the value associated with the commands run by the last turtle
     * @throws BackendException for problems in initializing the inner classes when executing CommandBlockManager
     */

    @Override
    public double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException {
        PeekableScanner scanner = (PeekableScanner) parameters.get(0);
        CommandTree commandTree = new CommandTree(turtleHistory);
        double returnValue;

        String variable = scanner.next();
        commandTree.addToCommandTree(scanner.next());
        while (!commandTree.onlyNumberLeft()) {
            String command = scanner.next();
            command = CommandBlockManager.checkAndInputUserVariable(command, accessibleVariables);
            commandTree.addToCommandTree(command);
        }
        returnValue = commandTree.getLastDouble();

        for (Map<String, Double> variableMap : accessibleVariables) {
            if (variableMap.containsKey(variable)) {
                variableMap.put(variable, returnValue);
                return returnValue;
            }
        }

        Map<String, Double> localVariables = accessibleVariables.get(accessibleVariables.size() - 1);
        localVariables.put(variable, returnValue);
        return returnValue;
    }
}


