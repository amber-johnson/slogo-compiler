package slogo.frontend;

import slogo.frontend.creater.ChangeableNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.HashMap;

/**
 * Holds all the ChangeableNode instances in the Visualization, extracts the changed data by user action on the
 * UI elements, and makes UI update language.
 *
 * @author Eric Han
 */
public class UIManager {

    private static final String RESOURCE_PATH = "resources.frontend.UIControllerResource";

    private ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
    private List<ChangeableNode> changeableNodes = new ArrayList<>();
    private UIController myUIController;
    private Map<String, String> changedValues = new HashMap<>();
    private String language = "English";

    public UIManager(CommandLine commandLine, List<ChangeableNode> changeableNodes) {
        myUIController = new UIController(commandLine);
        this.changeableNodes.addAll(changeableNodes);
    }

    /**
     * Makes the UIManager update the UI if anything has to be changed.
     */
    public void update() {
        for(ChangeableNode changeableNode : changeableNodes) {
            changedValues.putAll(changeableNode.getChangedValues());
        }
        if(changedValues.size() == 0) {
            return;
        }
        for(Map.Entry<String, String> entry : changedValues.entrySet()) {
            if(entry.getKey().equals("Functions")) {
                System.out.println("FOUND FOUND");
            }
            try {
                Method m = myUIController.getClass().getDeclaredMethod(resourceBundle.getString(entry.getKey()), String.class);
                m.invoke(myUIController, entry.getValue());
            } catch (Exception e) {
                ErrorShow errorShow = new ErrorShow(e, resourceBundle.getString(entry.getKey()) + " method not found");
                errorShow.show();
            }
        }
        changedValues.clear();
        updateLanguage(myUIController.getLanguage());
    }

    /**
     * Called by Visualization to get data from commandline
     */
    public String getInput() {
        return myUIController.getInput();
    }

    /**
     * true if new button is clicked.
     */
    public boolean isNewButtonClicked() {
        return myUIController.isNewButtonClicked();
    }

    private void updateLanguage(String language) {
        if(!this.language.equals(language)) {
            for(ChangeableNode changeableNode : changeableNodes) {
                changeableNode.setLanguage(language);
            }
            this.language = language;
        }
    }

    /**
     * returns the chosen language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * returns the information that the visualization will use to change the UI
     */
    public Map<String, Double> getChangedVariables() {return myUIController.getChangedVariables();}
}
