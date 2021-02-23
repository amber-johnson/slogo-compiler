package slogo.frontend;

import javafx.scene.control.Alert;

/**
 * Class that helps with error showing. We assume and consider that there will be various type of message errors.
 * @author Michael Castro and Eric Han
 *  */

public class ErrorShow implements NewScreen{
    private Throwable ex;
    private String message;

    /**
     * The error constructor.
     *  */

    public ErrorShow(Throwable ex, String message){
        this.ex = ex;
        this.message = message;
    }
    /**
     * Method that helps take in the error messages.
     * Example - > Someone types in english but the language is set to something different.
     *  */

    public ErrorShow(String message) {
        this.message = message;
    }

    /**
     * Method that allows us tp call the alert class to show the error popup/
     *  */


    public void show() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }
}
