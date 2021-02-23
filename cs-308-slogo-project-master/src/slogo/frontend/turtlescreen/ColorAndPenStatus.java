package slogo.frontend.turtlescreen;

import javafx.scene.paint.Color;
import slogo.backend.utils.ColorManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores the color values for the pen for each turtleView by using
 * the Colormanager object. Also used by SLogoManager to get the color
 * value according to the index
 *
 * @author Eric Han
 */
public class ColorAndPenStatus {
    private Map<TurtleView, Color> myLineColors = new HashMap<>();
    private ColorManager colorManager;
    private Color currentLineColor = Color.BLACK;
    private Color currentBackgroundColor = Color.WHITE;
    private Map<TurtleView, Double> myPenSizes = new HashMap<>();
    private double currentPenSize = 1;

    protected ColorAndPenStatus(ColorManager colorManager) {
        this.colorManager = colorManager;
    }

    /**
     * Set the default color that will be used when new turtleView is added
     * @param color
     */
    protected void setDefaultLineColor(Color color) {
        for(TurtleView turtleView : myLineColors.keySet()) {
            myLineColors.put(turtleView, color);
        }
        currentLineColor = color;
    }

    /**
     * Sets the line color index of a turtle
     * @param turtle
     * @param index
     */
    public void setLineColor(TurtleView turtle, int index) {
        myLineColors.put(turtle, colorManager.getColor(index, false));
    }

    /**
     * Gets the line color of a turtleview object
     */
    protected Color getLineColor(TurtleView turtleView) {
        return myLineColors.get(turtleView);
    }

    protected void addLineColor(TurtleView turtleView) {
        myLineColors.put(turtleView, currentLineColor);
    }

    protected void setColorManager(ColorManager colorManager) {this.colorManager = colorManager;}

    /**
     * Sets background color with the index
     * @param index
     */
    protected void setBackgroundColor(int index) {
        currentBackgroundColor = colorManager.getColor(index, true);
    }

    /**
     * Sets background color
     */
    protected void setBackgroundColor(Color color) {
        currentBackgroundColor = color;
    }

    /**
     * get the current backrounground color
     */
    protected Color getBackgroundColor() {
        return currentBackgroundColor;
    }

    protected void setPenSize(TurtleView turtleView, double penSize, boolean changeDefaultSize) {
        myPenSizes.put(turtleView, penSize);
        if(changeDefaultSize) {
            currentPenSize = penSize;
        }
    }

    protected void addPenSize(TurtleView turtleView) {myPenSizes.put(turtleView, currentPenSize);}

    protected double getPenSize(TurtleView turtleView) {return myPenSizes.get(turtleView);}
}
