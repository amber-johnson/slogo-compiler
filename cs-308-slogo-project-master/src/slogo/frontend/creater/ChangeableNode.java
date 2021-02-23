package slogo.frontend.creater;

import java.util.Map;

/**
 * purpose: To group all the sections of the UI that should change according to the language, and can affect other parts of the UI
 *
 * Ex.
 * ChangeableNode node = new ButtnoCreater(new ButtonController(displayScreen));
 * node.setLanguage("Chinese");
 *
 * @author Eric Han
 */
public interface ChangeableNode {

    /**
     * purpose : return the information that the UIManager will use to change UI
     * @return the map that holds the values in UI to be changed
     */
    Map<String, String> getChangedValues();

    /**
     * Sets the language of the ChangeableNode
     * @param language = the language
     */
    void setLanguage(String language);
}
