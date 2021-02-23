package slogo.frontend.creater;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import slogo.frontend.ErrorShow;
import slogo.frontend.NewScreen;
import slogo.frontend.controller.NodeController;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This is the SlideCreator class that extends an HBox. Essentially, this class helps create
 * any needed sliders by reading through the resource bundle. After the sliders are created,
 * they then are added to an HBox which is then displayed in the GUI.
 *
 * @author Eric Han, Michale Castro
 */
public class SliderCreator extends HBox implements ChangeableNode {
    private static final String RESOURCE = "resources.frontend.SliderResource";
    private static final double LAYOUT_X= 600;
    private static final double LAYOUT_Y= 5;
    private static final double SPACING = 10;
    private static final double SLIDER_MIN = 0;
    private static final double SLIDER_MAX = 1;
    private static final double SLIDER_START = SLIDER_MAX/2;
    private static final String LANGUAGE_INDEX_PATH = "resources.frontend.changingfeature.LanguageIndex";
    private static final String SLIDER_NAMES = "resources.frontend.changingfeature.SliderNames";
    private static final String INITIAL_LANGUAGE = "English";

    private ResourceBundle resourceBundle;
    private double sliderValue = SLIDER_START;
    private NodeController myController;
    private String language = INITIAL_LANGUAGE;

    public SliderCreator(NodeController nodeController) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE);
        myController = nodeController;
        setLayoutX(LAYOUT_X);
        setLayoutY(LAYOUT_Y);
        setSpacing(SPACING);
        createSliders();

    }

    private void createSliders() {
        for(String key : Collections.list(resourceBundle.getKeys())) {
            createSlider(key);
        }
    }

    private void createSlider(String key) {
        Label sliderLabel = new Label(getContentOfButton(key));
        Slider slider = new Slider();
        slider.setMin(SLIDER_MIN);
        slider.setMax(SLIDER_MAX);
        slider.setValue(sliderValue);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> callAction(key, (Double) newValue));
        getChildren().addAll(sliderLabel, slider);
    }

    @Override
    public Map<String, String> getChangedValues() {
        return new HashMap<>();
    }

    @Override
    public void setLanguage(String language) {
        getChildren().clear();
        this.language = language;
        createSliders();
    }

    private void callAction(String key, double percentage) {
        String methodName = resourceBundle.getString(key);
        try {
            Method m = myController.getClass().getDeclaredMethod(methodName, Double.TYPE);
            m.invoke(myController, percentage);
        } catch (Exception e) {
            NewScreen errorShow = new ErrorShow(e, e.getMessage());
            errorShow.show();
        }
    }

    private String getContentOfButton(String key) {
        ResourceBundle languageBundle = ResourceBundle.getBundle(LANGUAGE_INDEX_PATH);
        int index = Integer.parseInt(languageBundle.getString(language));
        ResourceBundle sliderNameBundle = ResourceBundle.getBundle(SLIDER_NAMES);
        return sliderNameBundle.getString(key).split(",")[index];
    }
}
