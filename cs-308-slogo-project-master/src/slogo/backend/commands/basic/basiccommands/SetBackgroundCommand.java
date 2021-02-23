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
 * Implementation of BasicCommandInterface that implements SetBackground command
 */
public class SetBackgroundCommand implements BasicCommandInterface {
    private static final double ACCURACY = 0.001;

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        TurtleModel turtle = turtleHistory.getTurtleModel(turtleID);
        Point2D curPos = new Point2D(turtle.getXPos(), turtle.getYPos());
        Movement movement = new Movement(curPos, curPos,turtle.getOrientation());

        DrawStatus initialDrawStatus = turtle.getDrawStatus();
        int index = (int) (parameters.get(0) + ACCURACY);
        DrawStatus newDrawStatus = new DrawStatus(initialDrawStatus.isTurtleVisible(), index, initialDrawStatus.getImageNum(), false);
        turtleHistory.updateTurtle(turtleID, movement, newDrawStatus, turtle.getPenStatus());

        return index;
    }
}
