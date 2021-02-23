package slogo.backend.utils;

import slogo.util.DrawStatus;
import slogo.util.Movement;
import slogo.util.PenStatus;

/**
 * Manages the current status of the Turtle, so that query commands can return the right value
 *
 * Ex:  TurtleHistory TH = new TurtleModel(1, Penstatus object, drawStatus object);
 * TH.update(movement, penstatus, drawstatus);
 *
 * Additional Usage: none
 * @author Eric Han
 */
public class TurtleModel {
    private static final double INITIAL_ORIENTATION = 90d;
    private static final double INITIAL_DISPLACEMENT = 0d;

    private double xPos;
    private double yPos;
    private double orientation;
    private int myID;
    private DrawStatus drawStatus;
    private PenStatus penStatus;


    public TurtleModel(int myID, PenStatus penStatus, DrawStatus drawStatus) {
        this.myID = myID;
        xPos = INITIAL_DISPLACEMENT;
        yPos = INITIAL_DISPLACEMENT;
        orientation = INITIAL_ORIENTATION;
        this.penStatus = penStatus;
        this.drawStatus = drawStatus;
    }

    /**
     * Returns the id of the turtleModel
     */
    public int getMyID() {
        return myID;
    }

    /**
     * Updates the turtleMode's status according tot the parameters
     *
     * @param movement : The path that the turtle moved
     * @param drawStatus : The new drawStatus for the turtle
     * @param penStatus : the new penStatus for the turtle
     */
    protected void update(Movement movement, DrawStatus drawStatus, PenStatus penStatus) {
        /**TODO: Update the turtle according to the movement object
         */
        xPos = movement.getEndPosition().getX();
        yPos = movement.getEndPosition().getY();
        orientation = movement.getOrientation();
        DrawStatus newDrawStatus = new DrawStatus(drawStatus);
        newDrawStatus.compareAndSetChanged(this.drawStatus);
        this.drawStatus = newDrawStatus;

        PenStatus newPenStatus = new PenStatus(penStatus);
        newPenStatus.compareAndSetChanged(this.penStatus);
        this.penStatus = newPenStatus;
    }

    /**
     * Returns the current X pos
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Returns the current Y pos
     */
    public double getYPos() {
        return yPos;
    }

    /**
     * Returns the current orientation
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * Returns the current drawStatus
     */
    public DrawStatus getDrawStatus() {
        return drawStatus;
    }

    /**
     * Returns the current penStatus
     */
    public PenStatus getPenStatus() {
        return penStatus;
    }
}
