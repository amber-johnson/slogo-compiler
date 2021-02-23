package slogo.frontend.reference;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.frontend.ErrorShow;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Creates a new window that shows a tableView that shows instruction on how to write logo codes
 *
 * @author Eric Han
 */
public class TableWindow {

    private static final String RESOURCE_PATH = "resources.frontend.commandreference.";
    private static final String DESCRIPTION_FILE_NAME="CommandDescription";
    private static final String TABLE_COL_PATH="resources.frontend.commandreference.TableColumn";
    private static final double WIDTH = 1200;
    private static final double HEIGHT = 800;
    private static final double XPOS = 600;
    private static final double YPOS = 600;

    private ResourceBundle commandDescriptionResource;
    private ResourceBundle columnResource = ResourceBundle.getBundle(TABLE_COL_PATH);
    private String title;
    private String path;
    private String language;
    private TableView myTableView = new TableView();


    public TableWindow(String language, String commandType) {
        this.path = RESOURCE_PATH + commandType;
        commandDescriptionResource = ResourceBundle.getBundle(path + DESCRIPTION_FILE_NAME);
        title = commandType.substring(0, commandType.length()-1).toUpperCase();
        this.language = language;
    }

    /**
     * Creates the new window and returns the stage
     */
    public Stage createNewWindow() {
        Stage newStage = createNewStage();
        newStage.show();
        newStage.setScene(new Scene(createVBox()));
        return newStage;
    }

    private Stage createNewStage() {
        Stage newStage = new Stage();
        newStage.setTitle(title);
        newStage.setWidth(WIDTH);
        newStage.setHeight(HEIGHT);
        newStage.setX(XPOS);
        newStage.setY(YPOS);
        return newStage;
    }

    private VBox createVBox() {
        VBox vBox = new VBox();
        vBox.getChildren().add(myTableView);
        myTableView.setItems(getTableData());
        addColumns();
        return vBox;
    }

    private ObservableList<TableRowData> getTableData() {
        ObservableList<TableRowData> list = FXCollections.observableArrayList();
        for(String commandName : Collections.list(commandDescriptionResource.getKeys())) {
            list.add(new TableRowData(commandName, path, language));
        }
        return list;
    }

    private void addColumns() {
        for(String colName : Collections.list(columnResource.getKeys())) {
            TableColumn<TableRowData, String> column = new TableColumn<>(colName);
            column.setCellValueFactory(e -> new ReadOnlyStringWrapper(getStringValue(e.getValue(), colName)));
            myTableView.getColumns().add(column);
        }
    }

    private String getStringValue(TableRowData rowData, String str) {
        String methodName = "get" + str;
        try {
            Method m = rowData.getClass().getDeclaredMethod(methodName);
            return m.invoke(rowData).toString();
        } catch (Exception e) {
            ErrorShow errorShow = new ErrorShow(e, str + " not fetched");
            errorShow.show();
        }
        return "";
    }
}
