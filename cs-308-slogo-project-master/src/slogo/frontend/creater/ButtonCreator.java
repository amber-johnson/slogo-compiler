package slogo.frontend.creater;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
 * This is the ButtonCreator class that extends an HBox. Essentially, this class helps create
 * any needed button by reading through the resource bundle. After the buttons are created,
 * they then are added to an HBox which is then displayed in the GUI.
 *
 * @author Eric Han, Michael Castro
 */



public class ButtonCreator extends HBox implements ChangeableNode {
    private static final double INSET_PADDING = 10;
    private static final double SPACING = 25;
    private static final double Y_LAYOUT = 430;
    private static final double X_LAYOUT = 20;
    private static final String RESOURCE_PATH = "resources.frontend.ButtonResource";
    private static final String LANGUAGE_INDEX_PATH = "resources.frontend.changingfeature.LanguageIndex";
    private static final String BUTTON_NAMES = "resources.frontend.changingfeature.ButtonNames";
    private static final String INITIAL_LANGUAGE = "English";

    private NodeController myButtonController;
    private ResourceBundle resourceBundle;
    private String language = INITIAL_LANGUAGE;
    private ResourceBundle languageBundle = ResourceBundle.getBundle(LANGUAGE_INDEX_PATH);
    private ResourceBundle buttonNameBundle = ResourceBundle.getBundle(BUTTON_NAMES);


    public ButtonCreator(NodeController nodeController) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
        myButtonController = nodeController;
        GridPane buttons = new GridPane();
        buttons.setPadding(new Insets(INSET_PADDING, INSET_PADDING, INSET_PADDING, INSET_PADDING));
        createButtons();
        setSpacing(SPACING);
        setLayoutY(Y_LAYOUT);
        setLayoutX(X_LAYOUT);
    }

    public Map<String, String> getChangedValues() {
        Map<String, String> map = new HashMap<>();
        map.putAll(myButtonController.getChangedValues());
        return map;
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
        createButtons();
    }


    /** * This is is the method that helps create the buttons */

    private void createButtons() {
        getChildren().clear();
        for(String key : Collections.list(resourceBundle.getKeys())) {
            Button button = new Button(getContentOfButton(key));
            button.setOnAction(e -> callAction(key));
            button.setPrefHeight(getHeight());
            getChildren().add(button);
        }
    }

    private void callAction(String key) {
        String methodName = resourceBundle.getString(key).split(",")[0];
        try {
            Method m = myButtonController.getClass().getDeclaredMethod(methodName, String.class);
            m.invoke(myButtonController, key);
        } catch (Exception e) {
            NewScreen errorShow = new ErrorShow(e, e.getMessage());
            errorShow.show();
        }
    }

    private String getContentOfButton(String key) {
        int index = Integer.parseInt(languageBundle.getString(language));
        return buttonNameBundle.getString(key).split(",")[index];
    }
}
