package slogo.backend.commands.basic;

import slogo.backend.utils.TurtleHistory;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Purpose: The CommandFactory executes the getRetunValue() method of the correct implementation of BasicCommandsInterface
 * by using Reflection pattern.
 *  Ex )
 *  CommandFactory commandFactory = new CommandFactory(myTurtleHistory);
 *  (Use getNumParameter to set the List<Double> parameter for execute command)
 *  double num = commandFactory.execute("Forward", 1, List.of(50))
 * Additional details: none
 * @author Eric Han
 */
public class CommandFactory {
    private static final String RESOURCE_PATH = "resources.DefinedCommands";
    private static final String CLASS_PATH = "slogo.backend.commands.basic.basiccommands.";
    private static final String ERROR_MSSG = " not recognized";

    private ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
    private TurtleHistory myTurtleHistory;

    public CommandFactory(TurtleHistory turtleHistory) {
        myTurtleHistory = turtleHistory;
    }

    /**
     * Purpose: Return the number of parameters needed to execute that command, so that the CommandTree can accurately
     * manage its tree structure
     * Assumption: command is one word
     * @param command : The command which is currently being tested in CommandTree
     */
    public int getNumParameter(String command) throws ClassNotFoundException {
        for (String key : Collections.list(resourceBundle.getKeys())) {
            if (key.equals(command)) {
                return Integer.parseInt(resourceBundle.getString(key).split(",")[1]);
            }
        }
        throw new ClassNotFoundException(command + ERROR_MSSG);
    }

    /**
     * Purpose: Calls the appropriate implementation of CommandInterface, and execute the command, returning the value produced
     * Assumption: List<Double> parameters is the required size.
     * @param command : The command which is currently being tested in CommandTree
     * @param turtleID : Current ID of the turtle
     * @param parameters : parameters that will affect the result
     */
    public double execute(String command, int turtleID, List<Double> parameters) throws ClassNotFoundException {
        return getDoubleFromInstance(getClass(command), turtleID, parameters, command);
    }

    private Class<?> getClass(String command) throws ClassNotFoundException {
        for (String key : Collections.list(resourceBundle.getKeys())) {
            if (key.equals(command)) {
                String commandName = resourceBundle.getString(key).split(",")[0];
                System.out.println(commandName);
                return Class.forName(CLASS_PATH + commandName);
            }
        }
        throw new ClassNotFoundException();
    }

    private double getDoubleFromInstance(Class<?> clazz, int turtleID, List<Double> parameters, String command) throws ClassNotFoundException {
        try {
            Constructor<?> constructor = clazz.getConstructor();
            return ((BasicCommandInterface) constructor.newInstance()).getReturnValue(myTurtleHistory, parameters, turtleID);
        } catch (Exception e) {
            System.out.println(command + " class not found.");
            throw new ClassNotFoundException(command + ERROR_MSSG, e);
        }
    }
}