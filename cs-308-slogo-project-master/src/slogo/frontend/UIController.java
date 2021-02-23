package slogo.frontend;

import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for the UIManager class. Has public methods that makes the commandLine information to change,
 * or update changedVariables/language, which the visualization class will use to update the UI
 *
 * @author Eric Han
 */
public class UIController {

    private CommandLine commandLine;
    private boolean startButtonClicked = false;
    private boolean newButtonClicked = false;
    private String language = "English";
    private Map<String, Double> changedVariables = new HashMap<>();

    public UIController(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    /**
     * Called when startButton clicked
     * @param key = "Start"
     */
    public void startAction(String key) {
        startButtonClicked = true;
    }

    /**
     * Called when clearButton clicked
     * @param key = "Clear"
     */
    public void clearAction(String key) {
        commandLine.getCommand().clear();
    }

    /**
     * Called when 'new' button clicked
     * @param key = "New"
     */
    public void newWindowAction(String key) {
        newButtonClicked = true;
    }

    /**
     * Called when a element in language dropdown clicked
     * @param language = language selected
     */
    public void languageAction(String language) {
        this.language = language;
    }

    /**
     * Called when an text in the history scrollpane is clicked
     * @param content = text that was clicked
     */
    public void historyAction(String content) { commandLine.getCommand().setText(content);}

    /**
     * Called when a variable in the variables scrollpane is changed
     */
    public void variablesAction(String variable) {
        String[] variableArr = variable.split(" = ");
        changedVariables.put(variableArr[0], Double.parseDouble(variableArr[1]));
    }

    /**
     * Called when user input parameters for a function on functions scrollpane
     */
    public void functionsAction(String function) {
        commandLine.getCommand().setText(function);
    }

    /**
     * Called by visualization to get the text on the commandline if start Button is clicked
     * @return
     */
    public String getInput() {
        String command = commandLine.getCommand().getText();
        if(startButtonClicked && !command.equals("")) {
            startButtonClicked = false;
            Text readerText = new Text(command + "\n");
            return readerText.getText();
        }
        return "";
    }

    /**
     * true if new button is clicked
     */
    public boolean isNewButtonClicked() {
        boolean bool = newButtonClicked;
        newButtonClicked = false;
        return bool;
    }

    public String getLanguage() {
        return language;
    }

    public Map<String, Double> getChangedVariables() {
        Map<String, Double> map  = new HashMap<>();
        map.putAll(changedVariables);
        changedVariables.clear();
        return map;
    }
}
