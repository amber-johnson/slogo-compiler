package slogo.frontend.creater;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import slogo.frontend.ErrorShow;
import slogo.frontend.NewScreen;
import slogo.frontend.controller.NodeController;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This is the ColorPalette class that extends an VBox. Essentially, this class helps create
 * any needed componets for the ColorPalette by reading through the resource bundle. After the checkboxes are created,
 * they then are added to an VBox which is then displayed in the GUI.
 *
 * @author Eric Han, Michale Castro
 */
public class ColorPalette extends VBox implements ChangeableNode{
    private static final double PICKER_WIDTH = 100;
    private static final double PICKER_HEIGHT = 40;
    private static final double CIRCLE_RADIUS = 50;
    private static final double INSET_PADDING = 10;
    private static final double PALETTE_Y = 420;
    private static final double PALETTE_X = 630;
    private static final double CHECK_X = 570;
    private static final double CHECK_Y = 630;
    private static final double CHECK_SPACING = 10;
    private static final double SPACING = 10;
    private static final String RESOURCE_PATH = "resources.frontend.ColorPalette";
    private static final String INITIAL_LANGUAGE = "English";
    private static final String LANGUAGE_INDEX_PATH = "resources.frontend.changingfeature.LanguageIndex";
    private static final String PALETTE_NAMES = "resources.frontend.changingfeature.ColorPaletteNames";

    private ColorPicker colorPicker;
    private Circle colorCircle;
    private NodeController colorPaletteController;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
    private Map<CheckBox, String> checkBoxMap = new HashMap<>();
    private String language = INITIAL_LANGUAGE;
    private HBox myCheckBox;

    public ColorPalette(NodeController nodeController) {
        colorPaletteController = nodeController;
        colorPicker = new ColorPicker();
        colorPicker.setMaxSize(PICKER_WIDTH,PICKER_HEIGHT);
        colorCircle = new Circle(CIRCLE_RADIUS);
        colorCircle.setFill(colorPicker.getValue());
        createCheckBox();
        getChildren().addAll(colorCircle, colorPicker, myCheckBox);
        colorPicker.setOnAction(event -> {
            colorCircle.setFill(colorPicker.getValue());
            callMethods(colorPicker.getValue());
        });
        setPadding(new Insets(INSET_PADDING));
        setLayoutY(PALETTE_Y);
        setSpacing(SPACING);
        setLayoutX(PALETTE_X);
    }

    @Override
    public Map<String, String> getChangedValues() {
        return colorPaletteController.getChangedValues();
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
        myCheckBox.getChildren().clear();
        addCheckBoxes();
    }

    private void createCheckBox() {
        myCheckBox = new HBox();
        addCheckBoxes();
        myCheckBox.setLayoutY(CHECK_Y);
        myCheckBox.setLayoutX(CHECK_X);
        myCheckBox.setSpacing(CHECK_SPACING);
    }

    private void addCheckBoxes() {
        for(String key : Collections.list(resourceBundle.getKeys())) {
            CheckBox checkBox = new CheckBox(getContentOfPalette(key));
            checkBoxMap.put(checkBox, key);
            myCheckBox.getChildren().add(checkBox);
        }
    }

    private void callMethods(Color color) {
        for(CheckBox checkBox : checkBoxMap.keySet()) {
            if(checkBox.isSelected()) {
                try {
                    String methodName = resourceBundle.getString(checkBoxMap.get(checkBox));
                    Method m = colorPaletteController.getClass().getDeclaredMethod(methodName, Color.class);
                    m.invoke(colorPaletteController, color);
                } catch (Exception e) {
                    NewScreen errorShow = new ErrorShow(e, "ColorPicker Setting Wrong");
                    errorShow.show();
                }
            }
        }
    }

    private String getContentOfPalette(String key) {
        ResourceBundle languageBundle = ResourceBundle.getBundle(LANGUAGE_INDEX_PATH);
        int index = Integer.parseInt(languageBundle.getString(language));
        ResourceBundle dropDownBundle = ResourceBundle.getBundle(PALETTE_NAMES);
        return dropDownBundle.getString(key).split(",")[index];
    }
}
