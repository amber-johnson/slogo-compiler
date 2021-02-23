package slogo.frontend.controller;


import slogo.frontend.turtlescreen.DisplayScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * purpose: Controller used in DropDownCreater
 * assumptions: This is only assigned to DropDownCreater
 *
 * This will only be used within the DropDownCreater class. No other places should this be used.
 *
 * @author Eric Han, Michael Castro
 */
public class DropDownController implements NodeController {

    private static final int INITIAL_IMAGE_NUM = 1;

    private int imageNum;
    private DisplayScreen displayScreen;
    private Map<String, String> changedValues = new HashMap<>();

    public DropDownController(DisplayScreen displayScreen) {
        this.displayScreen = displayScreen;
        imageNum = INITIAL_IMAGE_NUM;
    }

    /**
     * action for image dropdown
     * @param key : "Image"
     * @param imageNum: selected String in the dropdown
     */
    public void chooseImage(String key, String imageNum) {
        this.imageNum = Integer.parseInt(imageNum);
        displayScreen.setImage(this.imageNum);
    }

    /**
     * action for penSize dropdown
     * @param key : "PenSize"
     * @param penSize: selected String in the dropdown
     */
    public void choosePenSize(String key, String penSize) {
        displayScreen.setPenSize(Integer.parseInt(penSize));
    }

    /**
     * action for language dropdown
     * @param key : "Language"
     * @param language: selected String in the dropdown
     */
    public void chooseLanguage(String key, String language) {
        changedValues.put(key, language);
    }

    @Override
    public Map<String, String> getChangedValues() {
        Map<String, String> map = new HashMap<>();
        map.putAll(changedValues);
        changedValues.clear();
        return map;
    }

    @Override
    public void setLanguage(String language) {
        return;
    }
}
