package slogo.frontend.turtlescreen;

import javafx.scene.image.Image;

import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Returns the right image according to the index, by using the resource file
 *
 * @author Eric Han
 */
public class ImageManager {

    private static final String RESOURCE_PATH = "resources.frontend.dropdown.Image";

    private ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);

    /**
     * This method helps "manage the image" by getting the needed images from the resources
     * */

    public Image getImage(int imageIndex) {
        for(String key : Collections.list(resourceBundle.getKeys())) {
            int index = Integer.parseInt(key);
            if(index == imageIndex) {
                return new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(resourceBundle.getString(key))));
            }
        }
        return null;
    }
}
