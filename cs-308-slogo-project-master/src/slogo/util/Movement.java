package slogo.util;

import javafx.geometry.Point2D;

/**
 * Class that holds information about the path that the turtle moved through and the turtle's orientation
 *
 * @author Eric Han
 */
public class Movement {
    private Point2D startPosition;
    private Point2D endPosition;
    private double orientation;

    public Movement(Point2D startPosition, Point2D endPosition, double orientation) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.orientation = orientation;
    }

    public Movement(Point2D startPosition, double orientation, double length) {
        this.startPosition = startPosition;
        this.orientation = orientation;
        this.endPosition = new Point2D(startPosition.getX() + length * Math.cos(Math.toRadians(orientation)),
                startPosition.getY() + length * Math.sin(Math.toRadians(orientation)));
    }

    /**
     * returns the starting position
     */
    public Point2D getStartPosition() {
        return startPosition;
    }

    /**
     * returns the ending position
     */
    public Point2D getEndPosition() {
        return endPosition;
    }

    /**
     * returns the orientation
     */
    public double getOrientation() {
        return orientation;
    }
}
