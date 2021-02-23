package slogo.frontend.turtlescreen;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import slogo.util.DrawStatus;
import slogo.util.Movement;
import slogo.util.PenStatus;
import slogo.backend.utils.TurtleMovement;

import java.util.ArrayList;
import java.util.List;

/**
 * TurtleView extends ImageView, and according to the turtleMovement objects stored in
 * myMovements, it moves its position and changes its penStatus, drawStatus values
 *
 * @author Eric Han
 */
public class TurtleView extends ImageView {

    private static final double CONSTANT_SCREEN_WIDTH = 3000;
    private static final double ABSOLUTE_SIZE_X = 30;
    private static final double INITIAL_ORIENTATION = 90;
    private static final double FULL_ANGLE = 360;

    private List<TurtleMovement> myMovements = new ArrayList<>();
    private int myID;
    private int index = 0;
    private double screenWidth;
    private double screenHeight;
    private double speed;
    private PenStatus penStatus;
    private DrawStatus drawStatus;
    private boolean active;

    public TurtleView(Image image, int turtleID, double screenWidth, double screenHeight) {
        super(image);
        myID = turtleID;
        double ABSOLUTE_SIZE_Y = ABSOLUTE_SIZE_X * getBoundsInLocal().getHeight() / getBoundsInLocal().getWidth();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        setFitWidth(CONSTANT_SCREEN_WIDTH * ABSOLUTE_SIZE_X / screenWidth);
        setFitHeight(getFitWidth() * ABSOLUTE_SIZE_Y / ABSOLUTE_SIZE_X);
    }

    /**
     * Calling this method will make the turtleView automatically update its position, and return a Line
     * object, which is equivalent to the path it moved through
     * @return Path of the turtleView's movement
     */
    public Line updateAndGetLine() {
        Point2D initialPos = new Point2D(getCentralX(), getCentralY());
        Movement movement = myMovements.get(index).getMovement();
        updateDrawStatus(myMovements.get(index).getDrawStatus());
        updatePenStatus(myMovements.get(index).getPenStatus());
        double angle = getAngle(movement.getStartPosition(), movement.getEndPosition());
        Point2D endPos = new Point2D(movement.getEndPosition().getX() + getScreenCenter().getX(),
                getScreenCenter().getY() - movement.getEndPosition().getY());
        if(initialPos.distance(endPos) < speed) {
            moveView(endPos, movement.getOrientation());
            index++;
            return penStatus.isPenDown()? new Line(initialPos.getX(), initialPos.getY(), endPos.getX(), endPos.getY()) : null;
        } else {
            double targetXPos = getCentralX() +  speed * Math.cos(Math.toRadians(angle));
            double targetYPos = getCentralY() -  speed * Math.sin(Math.toRadians(angle));
            moveView(new Point2D(targetXPos, targetYPos), movement.getOrientation());
            return penStatus.isPenDown()? new Line(initialPos.getX(), initialPos.getY(), targetXPos, targetYPos) : null;
        }
    }

    /**
     * Adds TurtleMovemet object to myMovements
     */
    public void addMovement(TurtleMovement turtleMovement) {
        myMovements.add(turtleMovement);
    }

    /**
     * returns true if the turtleView is still moving
     */
    public boolean isMoving() {
        if(index >= myMovements.size()) {
            myMovements.clear();
            index = 0;
        }
        return index < myMovements.size();
    }

    /**
     * Sets the speed of the turtleView's movement
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Returns the id of the turtleView
     */
    public int getMyID() {
        return myID;
    }

    /**
     * Sets the penStatus of the TurtleView
     */
    public void setPenStatus(PenStatus penStatus) {
        this.penStatus = penStatus;
    }

    /**
     * Sets the DrawStatus of the TurtleView
     */
    public void setDrawStatus(DrawStatus drawStatus) {this.drawStatus = drawStatus;}

    /**
     * Returns the penStatus of the TurtleView
     */
    public PenStatus getPenStatus() {return penStatus;}

    /**
     * Returns the DrawStatus of the TurtleView
     */
    public DrawStatus getDrawStatus() {return drawStatus;}

    public void setActive(boolean active) {this.active = active;}

    public boolean isActive() {return active;}

    private void moveView(Point2D targetPos, double orientation) {
        setX(targetPos.getX() - getFitWidth()/2);
        setY(targetPos.getY() - getFitHeight()/2);
        setRotate(INITIAL_ORIENTATION - orientation);
    }

    private double getCentralX() {
        return getX() + getBoundsInLocal().getWidth() / 2;
    }

    private double getCentralY() {
        return getY() + getBoundsInLocal().getHeight() / 2;
    }

    private Point2D getScreenCenter() {
        return new Point2D(screenWidth/2, screenHeight/2);
    }

    private void updateDrawStatus(DrawStatus drawStatus) {
        DrawStatus newDrawStatus = new DrawStatus(drawStatus);
        newDrawStatus.setTurtleVisible(drawStatus.isVisibleChanged() ? drawStatus.isTurtleVisible() : this.drawStatus.isTurtleVisible());
        newDrawStatus.setImageNum(drawStatus.isImageChanged() ? drawStatus.getImageNum() : this.drawStatus.getImageNum());
        newDrawStatus.setBackGround(drawStatus.isBackGroundChanged() ? drawStatus.getBackGround() : this.drawStatus.getBackGround());

        this.drawStatus = newDrawStatus;
    }

    private void updatePenStatus(PenStatus penStatus) {
        PenStatus newPenStatus = new PenStatus(penStatus);
        newPenStatus.setPenDown(penStatus.isPenDownChanged() ? penStatus.isPenDown() : this.penStatus.isPenDown());
        newPenStatus.setPenSize(penStatus.isPenSizeChanged() ? penStatus.getPenSize() : this.penStatus.getPenSize());
        newPenStatus.setPenColor(penStatus.isPenColorChanged() ? penStatus.getPenColor() : this.penStatus.getPenColor());

        this.penStatus = newPenStatus;
    }

    private double getAngle(Point2D startPos, Point2D targetPos) {
        double angle = new Point2D(1, 0).angle(targetPos.getX() - startPos.getX(), targetPos.getY() - startPos.getY());
        return targetPos.getY() - startPos.getY() < 0 ? FULL_ANGLE - angle : angle;
    }
}
