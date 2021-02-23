package slogo.frontend.controller;

import slogo.frontend.FunctionScreen;
import slogo.frontend.NewScreen;
import slogo.frontend.statusscreen.ChangedString;
import slogo.frontend.VariableScreen;
import slogo.frontend.turtlescreen.DisplayScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * purpose: Controller used in TabMaker
 * assumptions: This is only assigned to TabMaker
 *
 * This will only be used within the TabMaker class. No other places should this be used.
 *
 * @author  Eric Han, Michael Castro
 */
public class TabController implements NodeController {

    private String variableKey;
    private String functionKey;
    private ChangedString myChangedString = new ChangedString();
    private ChangedString myFunctionString = new ChangedString();
    private Map<String, String> changedValues = new HashMap<>();

    public TabController(DisplayScreen displayScreen) {
        /**
         * TODO: displayscreen left for future extensions
         */
    }

    /**
     * action for variable scrollPane
     * @param key = "Variables"
     * @param content = content selected
     */
    public void variableAction(String key, String content) {
        variableKey = key;
        myChangedString = new ChangedString();
        NewScreen variableScreen = new VariableScreen(myChangedString, content);
        variableScreen.show();
    }

    /**
     * action for history scrollPane
     * @param key = "History"
     * @param content = content selected
     */
    public void historyAction(String key, String content) {
        changedValues.put(key, content);
    }

    /**
     * action for functions scrollPane
     * @param key = "Functions"
     * @param content = content selected
     */
    public void functionsAction(String key, String content) {
        functionKey = key;
        myFunctionString = new ChangedString();
        NewScreen functionScreen = new FunctionScreen(myFunctionString, content);
        functionScreen.show();

    }

    @Override
    public Map<String, String> getChangedValues() {
        checkChangedVariables();
        Map<String, String> map = new HashMap<>();
        map.putAll(changedValues);
        changedValues.clear();
        return map;
    }

    @Override
    public void setLanguage(String language) {
        return;
    }

    private void checkChangedVariables() {
        String variable = myChangedString.getChangedVariable();
        if(variable!="") {
            changedValues.put(variableKey, variable);
        }
        String function = myFunctionString.getChangedVariable();
        if(function!="") {
            System.out.println(functionKey);
            changedValues.put(functionKey, function);
        }
    }
}
