package slogo.backend.commands.basic.basiccommands;

import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.backend.utils.TurtleHistory;

import java.util.List;

/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements NotEqual command
 */
public class NotEqualBasicCommand implements BasicCommandInterface {
    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        return parameters.get(0) != parameters.get(1) ? 1d : 0d;
    }
}
