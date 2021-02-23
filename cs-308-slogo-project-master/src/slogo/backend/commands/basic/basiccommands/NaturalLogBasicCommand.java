package slogo.backend.commands.basic.basiccommands;

import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.backend.utils.TurtleHistory;

import java.util.List;

import static java.lang.Math.log;

/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements NaturalLog command
 */

public class NaturalLogBasicCommand implements BasicCommandInterface {
    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        return log(parameters.get(0));
    }
}
