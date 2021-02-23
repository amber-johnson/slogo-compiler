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
 * Implementation of BasicCommandInterface that implements ShowTurtle command
 */
public class ShowTurtleBasicCommand implements BasicCommandInterface {

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        TurtleModel turtle = turtleHistory.getTurtleModel(turtleID);
        Point2D curPos = new Point2D(turtle.getXPos(), turtle.getYPos());
        Movement movement = new Movement(curPos, curPos,turtle.getOrientation());

        DrawStatus initialDrawStatus = turtle.getDrawStatus();
        DrawStatus newDrawStatus = new DrawStatus(true, initialDrawStatus.getBackGround(), initialDrawStatus.getImageNum(), false);
        turtleHistory.updateTurtle(turtleID, movement, newDrawStatus, turtle.getPenStatus());

        return 1;
    }
}
