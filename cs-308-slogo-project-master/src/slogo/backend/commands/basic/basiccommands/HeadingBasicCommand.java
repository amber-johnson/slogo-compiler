package slogo.backend.commands.basic.basiccommands;

import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.backend.utils.TurtleHistory;
import slogo.backend.utils.TurtleModel;

import java.util.List;

/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements Heading command
 */

public class HeadingBasicCommand implements BasicCommandInterface {

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        TurtleModel turtle = turtleHistory.getTurtleModel(turtleID);
        return turtle.getOrientation();
    }
}
