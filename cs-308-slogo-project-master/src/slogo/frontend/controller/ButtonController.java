package slogo.frontend.controller;

import slogo.frontend.turtlescreen.DisplayScreen;
import slogo.frontend.reference.ReferenceWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that contains all the action and events of all buttons.
 * Example - > When user clicks the start button, the startButtonAction is called
 * from the resource bundle. When that method is called, the respective steps needed are taken.
 * @author Michael Castro and Eric Han
 *  */

public class ButtonController implements NodeController {

    private static final String INITIAL_LANGUAGE = "English";
    private String language;
    private Map<String, String> changedValues = new HashMap<>();
    private DisplayScreen displayScreen;

    /**
     * Constructor that takes in the DisPlayScreen parameter. This allows for proper call
     * of this class in the GUI.
     *  */

    public ButtonController(DisplayScreen displayScreen) {
        language = INITIAL_LANGUAGE;
        this.displayScreen = displayScreen;
    }

    /**
     * Method that give the start button its functionality.
     *  */

    public void startButtonAction(String key) {
        changedValues.put(key, key);
    }

    /**
     * Method that give the clear button its functionality.
     *  */

    public void clearButtonAction(String key) {
        changedValues.put(key, key);
    }

    /**
     * Method that give the help button its functionality.
     *  */

    public void helpButtonAction(String key) {
        ReferenceWindow referenceWindow = new ReferenceWindow(language);
        referenceWindow.createNewWindow();
    }
    /**
     * Method that give the paly button its functionality.
     *  */


    public void playButtonAction(String key) {
        //displayScreen.setAnimation();
    }
    /**
     * Method that give the stop button its functionality.
     *  */

    public void stopButtonAction(String key) {
        displayScreen.setAnimation(0);
    }

    /**
     * Method that give the step button its functionality.
     *  */

    public void stepButtonAction(String key) {
        displayScreen.step();
    }
    /**
     * Method that give the new button its functionality.
     *  */

    public void newButtonAction(String key) {
        changedValues.put(key, key);
    }

    /**
     * Returns a map with with changed values, letting us track the changes.
     *  */

    @Override
    public Map<String, String> getChangedValues() {
        Map<String, String> map = new HashMap<>();
        map.putAll(changedValues);
        changedValues.clear();
        return map;
    }

    /**
     * Method that gives us the ability to set the language to what the user chose. 
     *  */

    @Override
    public void setLanguage(String language) {
        this.language = language;
    }
}
