package slogo.backend.commands.basic.basiccommands;

import javafx.geometry.Point2D;
import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.util.DrawStatus;
import slogo.util.Movement;
import slogo.backend.utils.TurtleHistory;
import slogo.backend.utils.TurtleModel;

import java.util.List;

/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements clearscreen command
 */

public class ClearScreenBasicCommand implements BasicCommandInterface {

    private static final double INITIAL_ORIENTATION = 90;

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        TurtleModel turtle = turtleHistory.getTurtleModel(turtleID);
        Point2D curPos = new Point2D(turtle.getXPos(), turtle.getYPos());
        double returnValue = curPos.distance(0, 0);

        Movement movement = new Movement(curPos, new Point2D(0, 0), INITIAL_ORIENTATION);

        DrawStatus initialDrawStatus = turtle.getDrawStatus();
        DrawStatus newDrawStatus = new DrawStatus(initialDrawStatus.isTurtleVisible(), initialDrawStatus.getBackGround(), initialDrawStatus.getImageNum(), true);
        turtleHistory.updateTurtle(turtleID, movement, newDrawStatus, turtle.getPenStatus());

        return returnValue;
    }
}
