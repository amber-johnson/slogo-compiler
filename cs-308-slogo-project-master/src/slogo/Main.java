package slogo;

import javafx.stage.Stage;
import javafx.application.Application;
import slogo.connector.Connector;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Connector connector = new Connector(stage);
        connector.begin();
    }
}