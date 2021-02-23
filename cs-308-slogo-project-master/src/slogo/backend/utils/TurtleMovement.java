package slogo.backend.utils;

import slogo.util.DrawStatus;
import slogo.util.Movement;
import slogo.util.PenStatus;

/**
 * Each object of this class will save the path that the turtle took, and the new drawStatus and penStatus
 * Will be called in SLogoViewManager and TurtleView
 *
 * Additional Usage: none
 * @author Eric Han
 */
public class TurtleMovement {

    private int turtleID;
    private Movement movement;
    private DrawStatus drawStatus;
    private PenStatus penStatus;

    public TurtleMovement(int turtleID, Movement movement, DrawStatus drawStatus, PenStatus penStatus) {
        this.turtleID = turtleID;
        this.movement = movement;
        this.drawStatus = drawStatus;
        this.penStatus = penStatus;
        System.out.println(penStatus.isPenDown() + ", " + drawStatus.isVisibleChanged() + "movement");
    }

    /**
     * Returns the ID
     */
    public int getTurtleID() {
        return turtleID;
    }

    /**
     * Returns the movement object
     */
    public Movement getMovement() {
        return movement;
    }

    /**
     * Returns the drawStatus object
     */
    public DrawStatus getDrawStatus() {
        return drawStatus;
    }

    /**
     * Returns the penStatus object
     */
    public PenStatus getPenStatus() {
        return penStatus;
    }
}
