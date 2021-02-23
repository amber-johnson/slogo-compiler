package slogo.backend.commands.control;

import slogo.backend.exceptions.BackendException;
import slogo.backend.utils.TurtleHistory;

import java.util.List;
import java.util.Map;

/**
 * Purpose: The control interface is used for specifying the contract that all control commands must implement, which is the
 * execute method. It also specifies all the parameters that each of the control command can expect to get from the client
 * class; as such, it is an interface that allows the client to request service from each of the individual command.
 * Assumptions: This interface assumes the existence of everything in its parameters. As such, when an accessibleVariables list or
 * definedFunction map has not yet been created, the client should pass in a new instance of these classes which the
 * interface's implementation can build off of. This interface also depends on the TurtleHistory class, as its commands
 * directly execute commands that make changes to this class.
 * Example of usage: Class<?> clazz = Class.forName(commandName);
 *                   Constructor classConstructor = clazz.getConstructor();
 *                   ControlInterface controlInterface = (ControlInterface) classConstructor.newInstance()).execute(turtleHistory, arguments, accessibleVariables, definedFunctions;
 * Additional details: parameters for the arguments should be passed in the `parameters` argument; though often-times,
 * parameters come in the form of String arguments, the list is designed to support any kind of additional parameters needed
 * outside of the four provided (turtleHistory, accessibleVariables, definedFunctions, and the parameters list itself).
 * @author Ha Nguyen
 */
public interface ControlInterface {
    /**
     * Purpose: This method executes the commands given to the implementation of this interface.
     * Assumptions: in the absence of each list/map of parameters, variables, or functions, new objects are passed in when
     * there are no variables/functions in existence.
     * @param turtleHistory the turtle history object of which commands in the classes that implement this interface has access to for modification
     * @param parameters the parameters required to run a specific command that implements this interface
     * @param accessibleVariables all the variables of higher scope that the implementation of this interface has access to.
     * @param definedFunctions all the higher scope functions that the implementation of this interface has access to.
     * @return the double that results from executing the commands
     * @throws BackendException when the CommandBlockManager created and required for executing the control commands throws
     * Exception, this class throws the Exception to the highest-scope of the CommandBlock, BackendManager, and Connector
     * to give to the front-end to handle.
     */
    double execute(TurtleHistory turtleHistory, List<Object> parameters, List<Map<String, Double>> accessibleVariables, Map<String, List<Object>> definedFunctions) throws BackendException;
}
