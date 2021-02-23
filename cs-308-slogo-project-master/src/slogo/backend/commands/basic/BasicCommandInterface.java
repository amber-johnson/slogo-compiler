package slogo.backend.commands.basic;

import slogo.backend.utils.TurtleHistory;

import java.util.List;



/**
 * Purpose: Classes that implement this interface
 * Additional details: none
 * @author Eric Han
 */
public interface BasicCommandInterface {

    /**
     * Purpose: Save movement information in turtleHistory and return the value from that command
     * Assumptions: The method assumes that List<Double> parameters has the right size, and that the CommandTree
     * passed in the correctly sized List by using getNumParameter() method in the CommandFactory
     * @param turtleHistory : The TurtleHistory now being used to store all the movement data
     * @param turtleID : Current ID of the turtle
     *  @param parameters : parameters that will affect the result
     */
    double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID);
}
