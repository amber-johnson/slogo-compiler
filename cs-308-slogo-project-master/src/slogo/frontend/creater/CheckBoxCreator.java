package slogo.frontend.creater;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
 * This is the CheckBoxCreater class that extends an HBox. Essentially, this class helps create
 * any needed CheckBoxes by reading through the resource bundle. After the checkboxes are created,
 * they then are added to an HBox which is then displayed in the GUI.
 *
 * @author Eric Han
 */
public class CheckBoxCreator extends HBox implements ChangeableNode {
    private static final String RESOURCE_PATH = "resources.frontend.CheckBoxResource";
    private static final double LAYOUT_X= 600;
    private static final double LAYOUT_Y= 35;
    private static final double SPACING = 10;
    private static final boolean CHECKBOX_DEFAULT_CHECKED = true;
    private static final String LANGUAGE_INDEX_PATH = "resources.frontend.changingfeature.LanguageIndex";
    private static final String CHECKBOX_NAMES = "resources.frontend.changingfeature.CheckBoxNames";
    private static final String INITIAL_LANGUAGE = "English";

    private NodeController myController;
    private boolean isCheckBoxChecked = CHECKBOX_DEFAULT_CHECKED;
    private String language = INITIAL_LANGUAGE;

    public CheckBoxCreator(NodeController nodeController) {
        myController = nodeController;
        setSpacing(SPACING);
        setLayoutX(LAYOUT_X);
        setLayoutY(LAYOUT_Y);
        createCheckBoxes();
    }

    private void createCheckBoxes() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
        for(String key : Collections.list(resourceBundle.getKeys())) {
            createCheckBox(key);
        }
    }

    private void createCheckBox(String key) {
        Label checkLabel = new Label(getContentOfButton(key));
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(isCheckBoxChecked);
        checkBox.selectedProperty().addListener(e -> callAction(key));
        getChildren().addAll(checkLabel, checkBox);
    }

    @Override
    public Map<String, String> getChangedValues() {
        return new HashMap<>();
    }

    @Override
    public void setLanguage(String language) {
        getChildren().clear();
        this.language = language;
        createCheckBoxes();
    }

    private void callAction(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
        String methodName = resourceBundle.getString(key);
        try {
            Method m = myController.getClass().getDeclaredMethod(methodName);
            m.invoke(myController);
        } catch (Exception e) {
            NewScreen errorShow = new ErrorShow(e, key + " CheckBox not set well.");
            errorShow.show();
        }
    }

    private String getContentOfButton(String key) {
        ResourceBundle languageBundle = ResourceBundle.getBundle(LANGUAGE_INDEX_PATH);
        int index = Integer.parseInt(languageBundle.getString(language));
        ResourceBundle checkBoxNameBundle = ResourceBundle.getBundle(CHECKBOX_NAMES);
        return checkBoxNameBundle.getString(key).split(",")[index];
    }
}
