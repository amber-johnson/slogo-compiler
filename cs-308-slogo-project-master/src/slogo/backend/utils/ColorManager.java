package slogo.backend.utils;

import javafx.scene.paint.Color;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Purpose: ColorManager holds all the Colors that correspond to the index
 * Example of usage: ColorManager cm = new ColorManager();
 *                   cm.addColor(3, 90, 180, 100);
 *                   Color c = cm.getColor(3, false)
 * Additional details: none
 * @author Eric Han
 */
public class ColorManager {

    private static final Color INITIAL_PEN_COLOR = Color.BLACK;
    private static final Color INITIAL_BACKGROUND_COLOR = Color.WHITE;
    private static final double MAX_VALUE = 256;
    private static final String RESOURCE_PATH = "resources.frontend.ColorRGBResource";

    private Map<Integer, Color> myColorMap = new TreeMap<>();

    public ColorManager() {
        initialize();
    }

    /**
     * gets the color corresponding to that index
     *
     * @param index: the index
     * @param background: is this called for choosing background color
     */
    public Color getColor(int index, boolean background) {
        if(myColorMap.containsKey(index)) {
            return myColorMap.get(index);
        }
        return background? INITIAL_BACKGROUND_COLOR : INITIAL_PEN_COLOR;
    }

    /**
     * sets the color corresponding to that index
     *
     * @param index: the index
     * @param red: Value of red for making color
     * @param blue: Value of blue for making color
     * @param green: Value of green for making color
     */
    public void addColor(int index, int red, int blue, int green) {
        Color color = new Color(red/MAX_VALUE, blue/MAX_VALUE, green/MAX_VALUE, 1);
        myColorMap.put(index, color);
    }

    private void addColor(int index, String s) {
        String[] arr = s.split(",");
        addColor(index, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
    }

    private void initialize() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
        for(String key : resourceBundle.keySet()) {
            addColor(Integer.parseInt(key), resourceBundle.getString(key));
        }
    }
}
