package slogo.backend.commands.basic.basiccommands;

import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.backend.utils.TurtleHistory;

import java.util.List;


/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements Remainder command
 */
public class RemainderBasicCommand implements BasicCommandInterface {

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        double parameter1 = parameters.get(0);
        double parameter2 = parameters.get(1);
        return parameter1 - Math.floor(parameter1 / parameter2) * parameter2 ;
    }
}
