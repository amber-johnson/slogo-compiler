package slogo.frontend.reference;

import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.ResourceBundle;

/**
 * This is the ReferenceWindow class that extends is going to be called when
 * the help button is clicked. It is used to open a new window that shows the types of commands
 *
 * @author Eric Han
 */
public class ReferenceWindow {
    private static final String RESOURCE_PATH = "resources.frontend.commandreference.CommandTypes";
    private static final String TITLE = "Command References";
    private static final double WIDTH = 800;
    private static final double HEIGHT = 400;
    private static final double XPOS = 600;
    private static final double YPOS = 800;

    private ResourceBundle resourceBundle;
    private String language;

    public ReferenceWindow(String language) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
        this.language = language;
    }

    /**
     * Opens the new window
     */
    public void createNewWindow() {
        Stage newStage = createNewStage();
        Scene scene = new Scene(createReference());
        newStage.setScene(scene);

    }
    private HBox createReference() {
        HBox hBox = new HBox();
        for(String key : Collections.list(resourceBundle.getKeys())) {
            Hyperlink link = new Hyperlink(key + " Commands");
            hBox.getChildren().add(link);
            link.setOnAction(e -> linkAction(resourceBundle.getString(key)));
        }
        return hBox;
    }

    private Stage createNewStage() {
        Stage newStage = new Stage();
        newStage.setTitle(TITLE);
        newStage.setWidth(WIDTH);
        newStage.setHeight(HEIGHT);
        newStage.setX(XPOS);
        newStage.setY(YPOS);
        newStage.show();
        return newStage;
    }

    private void linkAction(String commandType) {
        TableWindow tableWindow = new TableWindow(language, commandType);
        tableWindow.createNewWindow();
    }
}
