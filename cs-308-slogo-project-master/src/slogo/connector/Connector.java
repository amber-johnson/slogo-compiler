package slogo.connector;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.backend.exceptions.BackendException;
import slogo.backend.external_api.BackendManager;
import slogo.backend.parser.ParserException;
import slogo.backend.utils.TurtleHistory;
import slogo.frontend.Visualization;

import java.util.HashMap;
import java.util.Map;

/**
 * Connects the backend and the frontend;
 * Gets user input from Visualizaton.java; and,
 * Creates a new BackendManager object with the user input.
 * Throws either a BackendException or ParserException if either of these exceptions are thrown from BackendManager.
 * Used in Main.java
 * Ex:  Connector c = new Connector(stage);
 *      c.begin();
 *
 * @author Eric Han and Amber Johnson
 */
public class Connector {

    private static final double DURATION_MILLIS = 10;

    private Map<Stage, Visualization> visualizationMap;
    private Map<Stage, BackendManager> backendManagerMap;
    private boolean newWindow = false;

    public Connector(Stage stage) {
        visualizationMap = new HashMap<>();
        backendManagerMap = new HashMap<>();
        createNewWorkSpace(stage);
    }

    /**
     * Starts a new Timeline for user display;
     * Calls the step() method; and,
     * Initializes the animation.
     */
    public void begin() {
        var frame = new KeyFrame(Duration.millis(DURATION_MILLIS), e -> step());
        Timeline myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    private void step()  {
        for(Stage stage : visualizationMap.keySet()) {
            try {
                update(visualizationMap.get(stage), backendManagerMap.get(stage));
            } catch (Exception e) {
                visualizationMap.get(stage).showError(e, e.getMessage());
            }
            if(newWindow) {
                break;
            }
        }
        if(newWindow) {
            newWindow = false;
            createWorkSpace();
        }
    }

    private void update(Visualization myVisualization, BackendManager myBackEndManager) throws BackendException, ParserException {
        String input = myVisualization.getInput();
        if (!input.equals("")) {
            System.out.println(input);
            myBackEndManager.setCommands(input, myVisualization.getLanguage());
            myBackEndManager.executeCommands();
            myVisualization.setHistory(myBackEndManager.getHistory());
            myVisualization.setUserFunctions(myBackEndManager.getUserFunctions());
        }
        myVisualization.update();
        if(myVisualization.needNewWindow()) {
            newWindow = true;
        }
    }

    private void createWorkSpace() {
        Stage stage = new Stage();
        createNewWorkSpace(stage);
    }

    private void createNewWorkSpace(Stage stage) {
        Visualization myVisualization = new Visualization(stage);
        BackendManager myBackEndManager = new BackendManager(myVisualization.getInput(), myVisualization.getLanguage(), new TurtleHistory());
        visualizationMap.put(stage, myVisualization);
        backendManagerMap.put(stage, myBackEndManager);
        stage.setOnCloseRequest(e -> {
            visualizationMap.remove(stage);
            backendManagerMap.remove(stage);
        });
    }
}
