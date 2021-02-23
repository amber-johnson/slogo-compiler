package slogo.backend.commands.basic.basiccommands;

import javafx.geometry.Point2D;
import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.util.Movement;
import slogo.backend.utils.TurtleHistory;
import slogo.backend.utils.TurtleModel;

import java.util.List;

/**
 * @author Erie Seong Ho Han
 * Implementation of BasicCommandInterface that implements SetTowards command
 */
public class SetTowardsBasicCommand implements BasicCommandInterface {

    private static final double FULL_CYCLE = 360d;
    private static final double HALF_CYCLE = 180d;

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        TurtleModel turtle = turtleHistory.getTurtleModel(turtleID);
        Point2D curPos = new Point2D(turtle.getXPos(), turtle.getYPos());
        double newAngle = getSlope(curPos, new Point2D(parameters.get(0), parameters.get(1)));
        double degreeTurned = Math.abs(turtle.getOrientation() - newAngle);

        Movement movement = new Movement(curPos, curPos, newAngle);

        turtleHistory.updateTurtle(turtleID, movement, turtle.getDrawStatus(), turtle.getPenStatus());

        return degreeTurned <= HALF_CYCLE ? degreeTurned : FULL_CYCLE - degreeTurned;
    }


    private double getSlope(Point2D curPos, Point2D targetPos) {
        return new Point2D(1, 0).angle(targetPos.getX() - curPos.getX(), targetPos.getY() - curPos.getY());
    }
}
