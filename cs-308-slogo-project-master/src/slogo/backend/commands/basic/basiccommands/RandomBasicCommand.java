package slogo.backend.commands.basic.basiccommands;

import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.backend.utils.TurtleHistory;

import java.util.List;


/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements Random command
 */
public class RandomBasicCommand implements BasicCommandInterface {

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        return (int) (Math.random() * parameters.get(0));
    }
}
