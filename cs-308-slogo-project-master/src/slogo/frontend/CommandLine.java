package slogo.frontend;

import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;


/**
 * The class helps create the input box/the command line object. It extends a TilePane which
 * contains a TextArea object to type in text. This class will be called in Visualization to
 * implement the command line to the GUI.
 *
 * The class contains a getter, "getCommand()", which allows other classes to get the text
 * in the command line. This helps facilitate various scenarios and actions, like adding to the history.
 */

public class CommandLine extends TilePane {
    private static final double WIDTH = 600;
    private static final double HEIGHT = 100;
    private static final double LAYOUT = 500;
    private TextArea inputField;

    public CommandLine() {
        inputField = new TextArea();
        inputField.setPrefSize(WIDTH, HEIGHT);
        getChildren().addAll(inputField);
        setLayoutY(LAYOUT);
    }

    public TextArea getCommand() {
        return inputField;
    }
}
