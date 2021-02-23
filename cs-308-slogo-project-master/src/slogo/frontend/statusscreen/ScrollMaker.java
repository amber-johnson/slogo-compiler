package slogo.frontend.statusscreen;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Used to make ScrollPane that constructs the TabMaker.
 *
 * @author Eric Han, Michael Castro
 */
public class ScrollMaker extends VBox {
    private static final double WIDTH = 200;
    private static final double HEIGHT = 100;
    private ScrollPane myScrollPane;
    private Label myLabel;
    private String key;
    private VBox inputHolder = new VBox();

    public ScrollMaker(String key) {
        this.key = key;
        myScrollPane = new ScrollPane();
        myLabel = new Label(key);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        myScrollPane.setPrefSize(WIDTH, HEIGHT);
        myScrollPane.setContent(myLabel);
        myScrollPane.setContent(inputHolder);
        getChildren().addAll(myLabel, myScrollPane);
    }

    public void setLabelText(String title) {
        myLabel.setText(title);
    }

    public String getKey() {
        return key;
    }

    public void addText(Text text) {
        inputHolder.getChildren().add(text);
    }

    public void clearAll() {
        inputHolder.getChildren().clear();
    }

}
