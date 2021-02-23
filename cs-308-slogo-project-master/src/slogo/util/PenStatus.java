package slogo.util;

/**
 * Class to hold all the information that frontend will need when drawing the lines.
 *
 * @author Eric Han
 */
public class PenStatus {
    private boolean penDown;
    private double penSize;
    private int penColor;

    private boolean penDownChanged;
    private boolean penSizeChanged;
    private boolean penColorChanged;

    public PenStatus(boolean penDown, double penSize, int penColor) {
        this.penDown = penDown;
        this.penSize = penSize;
        this.penColor = penColor;
        penDownChanged = false;
        penSizeChanged = false;
        penColorChanged = false;
    }

    public PenStatus(PenStatus penStatus) {
        this.penDown = penStatus.isPenDown();
        this.penSize = penStatus.getPenSize();
        this.penColor = penStatus.getPenColor();
        this.penDownChanged = penStatus.isPenDownChanged();
        this.penSizeChanged = penStatus.isPenSizeChanged();
        this.penColorChanged = penStatus.isPenColorChanged();
    }

    /**
     * returns the penSize
     */
    public double getPenSize() {
        return penSize;
    }

    /**
     * Returns the index for pen color
     */
    public int getPenColor() {
        return penColor;
    }

    /**
     * Returns the index for pen color
     */
    public boolean isPenDown() {
        return penDown;
    }

    /**
     * true if penDown was changed in the last command
     */
    public boolean isPenDownChanged() {
        return penDownChanged;
    }

    /**
     * true if pensize was changed in the last command
     */
    public boolean isPenSizeChanged() {
        return penSizeChanged;
    }

    /**
     * true if pen color was changed in the last command
     */
    public boolean isPenColorChanged() {
        return penColorChanged;
    }

    /**
     * sets the visibility of the line
     */
    public void setPenDown(boolean penDown) {this.penDown = penDown;}

    /**
     * sets the color of the line
     */
    public void setPenColor(int penColor) {this.penColor = penColor;}

    /**
     * sets the thickness of the line
     */
    public void setPenSize(double penSize) {this.penSize = penSize;}

    /**
     * Sets the values for the field variables according to another PenStatus object
     */
    public void compareAndSetChanged(PenStatus other) {
        penDownChanged = !(other.isPenDown() == penDown);
        penSizeChanged = !(other.getPenSize() == penSize);
        penColorChanged = !(other.getPenColor() == penColor);
    }
}
