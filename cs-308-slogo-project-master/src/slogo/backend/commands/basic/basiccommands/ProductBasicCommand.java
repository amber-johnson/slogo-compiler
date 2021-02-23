package slogo.backend.commands.basic.basiccommands;

import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.backend.utils.TurtleHistory;

import java.util.List;


/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements Product command
 */
public class ProductBasicCommand implements BasicCommandInterface {

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        return parameters.get(0) * parameters.get(1);
    }
}
