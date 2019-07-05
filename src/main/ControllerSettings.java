package main;

import algorithm.AStar;
import algorithm.NodeCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.List;

public class ControllerSettings {

    @FXML
    private BorderPane root;
    @FXML
    private AnchorPane settingsRoot;
    @FXML
    private TextField xAxisTextField;
    @FXML
    private TextField yAxisTextField;
    @FXML
    private ColorPicker cellColorPicker;
    @FXML
    private ColorPicker startCellColorPicker;
    @FXML
    private ColorPicker endCellColorPicker;
    @FXML
    private ColorPicker blockedCellColorPicker;
    @FXML
    private ColorPicker pathCellColorPicker;
    @FXML
    private Button buttonApplyChanges;
    @FXML
    private Button buttonDoAlgorithm;
    @FXML
    private Button buttonClearGrid;

    @FXML
    void initialize(){

    }

    /** non-FMXL attributes and fields */
    private ObservableList<RectangleNodeCell> rectangleList = FXCollections.observableArrayList();
    private NodeCell[][] list;
    private AStar aStar;
    private AnchorPane rootAnchor;
    private int xSize;
    private int ySize;
    private Color pathColor;

    @FXML
    void buttonApplyChangesOnAction(ActionEvent event) {
        /** getting values from the controls */
        try {
            xSize = Integer.parseInt(xAxisTextField.getText());
            ySize = Integer.parseInt(yAxisTextField.getText());
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("X/Y size wrong.");
            alert.setHeaderText("X/Y size wrong.");
            alert.setContentText("Either the X or the Y grid size is wrong format. Please enter numbers only.");
            alert.showAndWait();
            event.consume();
        }
        Color c = cellColorPicker.getValue();
        Color cS = startCellColorPicker.getValue();
        Color cE = endCellColorPicker.getValue();
        Color cB = blockedCellColorPicker.getValue();

        /** initializing the grid of rectangles - cells */
        list = new NodeCell[xSize][ySize];
        double yCoord = 0.0;
        double xCoord = 0.0;
        for (int i=0; i<xSize; i++){
            xCoord += 50 + 3.0;
            yCoord = 3.0;
            for (int j=0; j<ySize; j++){
                RectangleNodeCell rect = new RectangleNodeCell(xCoord, yCoord, i, j, c, cS, cE, cB, rootAnchor);
                rectangleList.add(rect);
                list[i][j] = rect.getCell();
                rootAnchor.getChildren().add(rect);
                yCoord += 50 + 3.0;
            }
        }
        aStar = new AStar(xSize, ySize, list);
        buttonDoAlgorithm.setDisable(false);


    }

    @FXML
    void buttonDoAlgorithmOnAction(ActionEvent event) {
        boolean start = false;
        boolean end = false;
        int startCount = 0;
        int endCount = 0;
        for (int i=0; i<xSize; i++){
            for (int j=0; j<ySize; j++){
                if (list[i][j].isStart()){
                    start = true;
                    startCount++;
                }
                if (list[i][j].isEnd()){
                    end = true;
                    endCount++;
                }
                if (start && end) break;
            }
            if (start && end) break;
        }
        if (start && end && startCount==1 && endCount==1) {
            buttonDoAlgorithm.setDisable(true);
            List<NodeCell> path;
            path = aStar.doAlgorithm();
            if (path.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Path not found!");
                alert.setHeaderText("Path not found!");
                alert.setContentText("There isn't a single path that connects the starting node and the ending node!");
                alert.showAndWait();
            } else {
                pathColor = pathCellColorPicker.getValue();
                for (RectangleNodeCell r : rectangleList) {
                    for (NodeCell n : path) {
                        if (r.getCell() == n) {
                            r.setFill(pathColor);
                            r.setStroke(Color.DARKMAGENTA);
                        }
                    }
                }
            }
        }else{
            if(!start || !end) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Select both starting and ending node.");
                alert.setHeaderText("Select both starting and ending node.");
                alert.setContentText("Either the starting node or the ending node has not been selected, please do so.");
                alert.showAndWait();
            }
            else if (startCount>1 || endCount>1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("There is more than one starting/ending node.");
                alert.setHeaderText("There is more than one starting/ending node.");
                alert.setContentText("Please make sure you have only one starting node and one ending node.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void buttonClearGridOnAction(ActionEvent event) {
        rootAnchor.getChildren().clear();
        buttonDoAlgorithm.setDisable(true);
    }

    public void setRootAnchor(AnchorPane rootAnchor) {
        this.rootAnchor = rootAnchor;
    }
}
