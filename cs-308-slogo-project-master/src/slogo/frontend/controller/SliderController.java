package slogo.frontend.controller;

import slogo.frontend.turtlescreen.DisplayScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * purpose: Controller used in SliderCreater
 * assumptions: This is only assigned to SliderCreater
 *
 * This will only be used within the SliderCreater class. No other places should this be used.
 *
 * @author Eric Han, Michael Castro
 */
public class SliderController implements NodeController {

    private DisplayScreen displayScreen;

    public SliderController(DisplayScreen displayScreen) {
        this.displayScreen = displayScreen;
    }

    /**
     * action for speed slider
     * @param ratio = relative speed of turtle's movement
     */
    public void speedSliderAction(double ratio) {
        displayScreen.setAnimation(ratio);
    }

    @Override
    public Map<String, String> getChangedValues() {
        return new HashMap<>();
    }

    @Override
    public void setLanguage(String language) {
        return;
    }
}


