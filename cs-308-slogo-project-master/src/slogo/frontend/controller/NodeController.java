package slogo.frontend.controller;

import java.util.Map;

/**
 * purpose: Controller used in ChangeableNode
 * assumptions: This is only assigned to its corresponding ChangeableNode
 *
 * @author Eric Han
 *
 */
public interface NodeController {
    /**
     * purpose : return the information that the UIManager will use to change UI
     * @return the map that holds the values in UI to be changed
     */
    Map<String, String> getChangedValues();

    /**
     * purpose : set the language of the controller
     */
    void setLanguage(String language);
}
