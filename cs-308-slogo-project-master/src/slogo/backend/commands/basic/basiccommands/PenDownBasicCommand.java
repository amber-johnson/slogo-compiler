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
 * Implementation of BasicCommandInterface that implements PenDown command
 */
public class PenDownBasicCommand implements BasicCommandInterface {

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        TurtleModel turtle = turtleHistory.getTurtleModel(turtleID);
        Point2D curPos = new Point2D(turtle.getXPos(), turtle.getYPos());
        Movement movement = new Movement(curPos, curPos, turtle.getOrientation());

        PenStatus initialPenStatus = turtle.getPenStatus();
        PenStatus newPenStatus = new PenStatus(true, initialPenStatus.getPenSize(), initialPenStatus.getPenColor());

        turtleHistory.updateTurtle(turtleID, movement, turtle.getDrawStatus(), newPenStatus);

        return 1;
    }
}
