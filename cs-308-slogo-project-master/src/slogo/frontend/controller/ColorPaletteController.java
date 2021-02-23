package slogo.frontend.controller;

import javafx.scene.paint.Color;
import slogo.frontend.turtlescreen.DisplayScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * purpose: Controller used in ColorPalette
 * assumptions: This is only assigned to ColorPalette
 *
 * This will only be used within the ColorPalette class. No other places should this be used.
 *
 * @author Eric Han, Michael Castro
 */
public class ColorPaletteController implements NodeController {
    private DisplayScreen displayScreen;

    public ColorPaletteController(DisplayScreen displayScreen) {
        this.displayScreen = displayScreen;
    }

    /**
     * action when pen checkbox is selected
     * @param color = the color chosen
     */
    public void penColorAction(Color color) {
        displayScreen.setLineColor(color);
    }

    /**
     * action when background checkbox is selected
     * @param color = the color chosen
     */
    public void backgroundAction(Color color) {
        displayScreen.setBackground(color);
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
