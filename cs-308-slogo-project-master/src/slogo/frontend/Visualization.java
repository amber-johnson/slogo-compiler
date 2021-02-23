package slogo.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.backend.utils.TurtleHistory;
import slogo.frontend.controller.ButtonController;
import slogo.frontend.controller.DropDownController;
import slogo.frontend.controller.SliderController;
import slogo.frontend.controller.CheckBoxController;
import slogo.frontend.controller.ColorPaletteController;
import slogo.frontend.controller.TabController;
import slogo.frontend.creater.ButtonCreator;
import slogo.frontend.creater.CheckBoxCreator;
import slogo.frontend.creater.DropDownCreator;
import slogo.frontend.creater.SliderCreator;
import slogo.frontend.statusscreen.TabMaker;
import slogo.frontend.creater.ColorPalette;
import slogo.frontend.turtlescreen.DisplayScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that calls everything needed to create the GUI, like the DisPlayScreen and the CommandLine.
 * This is where we call everything. In order for this class to meet its purpose, it depends on the
 * other classes.
 * @author Michael Castro and Eric Han
 *  */

public class Visualization {
    private static final double SCENE_WIDTH = 830;
    private static final double SCENE_HEIGHT = 630;
    private static final String TITLE = "SLOGO IDLE";

    private Scene scene;
    private Stage stage;
    private DisplayScreen displayScreen = new DisplayScreen();
    private CommandLine commandLine = new CommandLine();
    private UIManager myUIManager;
    private int index = 0;
    private TabMaker tabPane = new TabMaker(new TabController(displayScreen));
    private List<Map<String, Double>> myVariables = new ArrayList<>();

    public Visualization(Stage stage) {
        this.stage = stage;
        initialize();
    }

    /**
     * The purpose of this method is to grab & know what language we are working with.
     * Example - > a user choosing "English" in the GUI is important to know to "tell" the
     * CommandLine.
     * @return a String which allows us to know what lanuage has been chosen.
     */
    public String getLanguage() {return myUIManager.getLanguage();}

    /**
     * The purpose of this method is to tell us is we need to pop up a new GUI.
     * Example - > If the user clicks the new button, it means we need to create a new window.
     * This true value lets us know the user wants this.
     * @return a boolean. True tells us if we need to create a new window.
     */
    public boolean needNewWindow() {
        return myUIManager.isNewButtonClicked();
    }

    /**
     * The purpose of this method is to update the specific tabs and scenes when needed.
     */
    public void update() {
        myUIManager.update();
        displayScreen.update();
        if(displayScreen.getCurrentIndex() != index) {
            tabPane.setVariables(myVariables.get(index));
            index = displayScreen.getCurrentIndex();
        }
        Map<String, Double> changedVariables = myUIManager.getChangedVariables();
        if(!changedVariables.isEmpty()) {
            index = index < myVariables.size()? index : myVariables.size()-1;
            myVariables.get(index).putAll(changedVariables);
            tabPane.setVariables(myVariables.get(index));
        }
    }

    /**
     * The purpose of this method is to assist with with adding commands to
     * the history tab.
     */
    public void setHistory(TurtleHistory turtleHistory) {
        displayScreen.setHistory(turtleHistory);
        myVariables = turtleHistory.getMyVariables();
        index = 0;
    }

    private void startStage() {
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.show();
    }

    /**
     * A method that returns the command. This is important because it allows
     * us to grab the input in the GUI and use it to tell the backend what to do.
     * Example - > Needing to know how to move the turtle. Getting the user input "fd" 50
     * and telling the turtle to move that up that value.
     * @return a String. A string that represents the user input.
     */
    public String getInput() {
        String command = myUIManager.getInput();
        if(!command.equals("")) {
            tabPane.addHistory(command);
        }
        return command;
    }

    /**
     * returns the updated variables
     */
    public Map<String, Double> getUpdatedVariables() {
        Map<String, Double> map = new HashMap<>();
        map.putAll(myVariables.get(myVariables.size()-1));
        return map;
    }

    /**
     * Makes the UI show an error screen with the message
     * @param message = message that will be in the error screen.
     */
    public void showError(Throwable ex, String message) {
        ErrorShow errorShow = new ErrorShow(ex, message);
        errorShow.show();
    }

    private void initialize() {
        Pane root = new AnchorPane();
        ButtonCreator buttonCreator = new ButtonCreator(new ButtonController(displayScreen));
        DropDownCreator dropDownCreator = new DropDownCreator(new DropDownController(displayScreen));
        SliderCreator sliderCreator = new SliderCreator(new SliderController(displayScreen));
        ColorPalette colorPalette = new ColorPalette(new ColorPaletteController(displayScreen));
        CheckBoxCreator checkBoxCreator = new CheckBoxCreator(new CheckBoxController(displayScreen));

        root.getChildren().addAll(displayScreen, commandLine, colorPalette, tabPane, buttonCreator, dropDownCreator, sliderCreator, checkBoxCreator);
        myUIManager = new UIManager(commandLine, List.of(colorPalette, buttonCreator, dropDownCreator, tabPane, checkBoxCreator, sliderCreator));
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        startStage();
    }

    /**
     * sets the functions defined by user, which will show up on the screen.
     */
    public void setUserFunctions(List<String> userFunctions) {
        tabPane.setFunctions(userFunctions);
    }
}