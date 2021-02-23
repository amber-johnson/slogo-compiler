package slogo.backend.commands.basic.basiccommands;

import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.backend.utils.TurtleHistory;

import java.util.List;

/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements atan command
 */

public class ArcTangentBasicCommand implements BasicCommandInterface {

    private static final double FULL_CYCLE = 360;

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        double degree =  Math.toDegrees(Math.atan(parameters.get(0)));
        return degree >= 0? degree : FULL_CYCLE + degree;
    }
}
