package slogo.frontend.statusscreen;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import slogo.frontend.ErrorShow;
import slogo.frontend.controller.NodeController;
import slogo.frontend.creater.ChangeableNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collections;

/**
 * This is the Tabmaker class that extends an VBox. Essentially, this class helps create
 * any needed ScrollPanes by reading through the resource bundle. After the scrollPanes are created,
 * they then are added to an HBox which is then displayed in the GUI.
 *
 * @author Eric Han, Michael Castro
 */
public class TabMaker extends VBox implements ChangeableNode {
    private static final double LAYOUT_X = 600;
    private static final double LAYOUT_Y = 70;
    private static final String RESOURCE_PATH = "resources.frontend.TabsResource";
    private static final String LANGUAGE_INDEX_PATH = "resources.frontend.changingfeature.LanguageIndex";
    private static final String SCROLL_NAME_PATH = "resources.frontend.changingfeature.ScrollPaneNames";

    private List<ScrollMaker> myScrolls = new ArrayList<>();
    private String language = "English";
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
    private NodeController myController;
    private Map<String, Double> myVariables = new TreeMap<>();

    public TabMaker(NodeController nodeController) {
        myController = nodeController;
        setLayoutX(LAYOUT_X);
        setLayoutY(LAYOUT_Y);
        createTabPane();
    }

    /**
     * adds the executed command to the history scrollpane
     */
    public void addHistory(String command) {
        for (ScrollMaker scrollMaker : myScrolls) {
            if (scrollMaker.getKey().equals("History")) {
                scrollMaker.addText(createText(scrollMaker.getKey(), command));
            }
        }
    }

    /**
     * sets the text on the variable scrollpane
     */
    public void setVariables(Map<String, Double> variables) {
        for (ScrollMaker scrollMaker : myScrolls) {
            if (!scrollMaker.getKey().equals("Variables")) {
                continue;
            }
            scrollMaker.clearAll();
            myVariables.putAll(variables);
            for (Map.Entry<String, Double> entry : myVariables.entrySet()) {
                String str = entry.getKey() + " = " + entry.getValue();
                scrollMaker.addText(createText("Variables", str));
            }
        }
    }

    /**
     * sets the text on the function scrollpane
     */
    public void setFunctions(List<String> myFunctions) {
        for (ScrollMaker scrollMaker : myScrolls) {
            if (!scrollMaker.getKey().equals("Functions")) {
                continue;
            }
            scrollMaker.clearAll();
            for(String function : myFunctions) {
                scrollMaker.addText(createText("Functions", function));
            }
        }
    }

    @Override
    public Map<String, String> getChangedValues() {
        return myController.getChangedValues();
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
        for(ScrollMaker scroll : myScrolls) {
            scroll.setLabelText(getScrollPaneName(scroll.getKey()));
        }
    }

    private void callAction(String key, String content) {
        String methodName = resourceBundle.getString(key);
        try {
            Method m = myController.getClass().getDeclaredMethod(methodName, String.class, String.class);
            m.invoke(myController, key, content);
        } catch (Exception e) {
            ErrorShow errorShow = new ErrorShow(e, key + "  control not set well.");
            errorShow.show();
        }
    }

    private void createTabPane() {
        getChildren().clear();
        for (String key : Collections.list(resourceBundle.getKeys())) {
            ScrollMaker myScroll = new ScrollMaker(key);
            getChildren().addAll(myScroll);
            myScroll.setLabelText(getScrollPaneName(key));
            myScrolls.add(myScroll);
        }
    }

    private String getScrollPaneName(String key) {
        ResourceBundle languageBundle = ResourceBundle.getBundle(LANGUAGE_INDEX_PATH);
        int index = Integer.parseInt(languageBundle.getString(language));
        ResourceBundle scrollNameBundle = ResourceBundle.getBundle(SCROLL_NAME_PATH);
        return scrollNameBundle.getString(key).split(",")[index];
    }

    private Text createText(String key, String content) {
        Text text = new Text(content + "\n------------");
        text.setOnMouseClicked(e -> callAction(key, content));
        return text;
    }
}
