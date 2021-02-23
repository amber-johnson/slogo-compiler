package slogo.frontend.turtlescreen;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slogo.backend.utils.TurtleHistory;

/**
 * This class is the actual "display screen" where the turtle moves.
 *
 * @author Eric Han, Michael Castro
 * */
public class DisplayScreen extends Pane {

    private static final double SCENE_WIDTH = 600;
    private static final double SCENE_HEIGHT = 400;

    private SLogoViewManager myViewManager;

    public DisplayScreen(){
        setPrefHeight(SCENE_HEIGHT);
        setPrefWidth(SCENE_WIDTH);
        setHeight(SCENE_HEIGHT);
        setWidth(SCENE_WIDTH);
        myViewManager = new SLogoViewManager(this);
    }

    /**
     * This method helps change the background color of the display screen.
     * */

    public void setBackground(Color color) {
        BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(new Background(fill));
    }

    /**
     * This  method helps update the display screen through the view manager.
     * */

    public void update() {
        myViewManager.update();
    }

    /**
     * This method helps set the turtle history through the view manager.
     * */

    public void setHistory(TurtleHistory turtleHistory) {
        myViewManager.setHistory(turtleHistory);
    }

    /**
     * This method helps change/update the pen color.
     * */

    public void setLineColor(Color color) {
        myViewManager.setLineColor(color);
    }

    /**
     * This method helps change/update the turtle image.
     * */

    public void setImage(int imageNum) {
        myViewManager.setImage(imageNum);
    }

    /**
     * This method helps change/update the size of the pen.
     * */

    public void setPenSize(int penSize) {
        myViewManager.setPenSize(penSize);
    }

    /**
     * This method helps change/update the animation.
     * */

    public void setAnimation(double ratio) {
        myViewManager.setSpeed(ratio);
    }

    /**
     * This method helps "break" the animation step by step.
     * */

    public void step() {
        myViewManager.step();
    }


    public int getCurrentIndex() {return myViewManager.getCurrentInstructionIndex();}
}
