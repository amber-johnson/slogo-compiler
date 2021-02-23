package slogo.backend.commands.control;

import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.TurtleHistory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Purpose: This class serves as the executor/factory for control commands that takes an execution request from a client
 * and returns a double value that results after executing that request.
 * Assumptions: Assumes that the resources defined by the resource path exists.
 * Usage Example: ControlExecutor executor = new ControlExecutor();
 *                executor.execute(commandName, arguments, turtleHistory, accessibleVariables, definedFunctions);
 * Other details: none
 * @author Ha Nguyen
 */
public class ControlExecutor {

    public static final String DEFINED_CONTROLS_RESOURCE_PATH = "resources/DefinedControls";
    public static final String CONTROLS_PACKAGE_PATH = "slogo.backend.commands.control.controlcommands.";

    /**
     * Executes a given control command (includes Ask, DoTimes, For, If, IfElse, MakeUserInstruction, MakeVariable, Repeat, Tell, and UserDefined).
     * @param commandName the command name of the command to be executed
     * @param arguments the arguments (other than turtleHistory, accessibleVariables, and definedFunctions) that the command needs to run
     * @param turtleHistory the TurtleHistory class of which the command can modify in order to execute its job
     * @param accessibleVariables the list of higher scope variables and their values that this command has access to
     * @param definedFunctions the list of higher scope functions and their arguments, which this command has access to
     * @return the double value that results after executing all the commands given
     * @throws BackendException caused by unmatched number of arguments for the method the user desires to execute and
     * the number of arguments that the user gives or by ClassNotFoundException, NoSuchMethodException, InstantiationException,
     * IllegalAccessException, or InvocationTargetException that occur as a result of instantiating the class for the given command name.
     */

    public double execute(String commandName, List<Object> arguments, TurtleHistory turtleHistory, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException {
        try {
            ResourceBundle controlCommandsBundle = ResourceBundle.getBundle(DEFINED_CONTROLS_RESOURCE_PATH);
            if (controlCommandsBundle.containsKey(commandName) && arguments.size() != Integer.parseInt(controlCommandsBundle.getString(commandName))) {
                throw new BackendException("Unmatched number of arguments for this method.");
            }
            Class<?> clazz = Class.forName(CONTROLS_PACKAGE_PATH + commandName);
            Constructor classConstructor = clazz.getConstructor();
            return ((ControlInterface) classConstructor.newInstance()).execute(turtleHistory, arguments, accessibleVariables, definedFunctions);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BackendException(e, "Unable to execute method due to ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, or InvocationTargetException.");
        }
    }
}
