package slogo.backend.commands.basic.basiccommands;

import javafx.geometry.Point2D;
import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.util.Movement;
import slogo.util.PenStatus;
import slogo.backend.utils.TurtleHistory;
import slogo.backend.utils.TurtleModel;

import java.util.List;

/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements SetPenColor command
 */
public class SetPenColorCommand implements BasicCommandInterface {
    private static final double ACCURACY = 0.001;

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        TurtleModel turtle = turtleHistory.getTurtleModel(turtleID);
        Point2D curPos = new Point2D(turtle.getXPos(), turtle.getYPos());
        Movement movement = new Movement(curPos, curPos,turtle.getOrientation());

        PenStatus initialPenStatus = turtle.getPenStatus();
        int index = (int) (parameters.get(0) + ACCURACY);
        PenStatus newPenStatus = new PenStatus(initialPenStatus.isPenDown(), initialPenStatus.getPenSize(), index);
        turtleHistory.updateTurtle(turtleID, movement, turtle.getDrawStatus(), newPenStatus);

        return index;
    }
}
