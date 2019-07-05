package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Controller {

    /** FMXL attributes and fields */
    @FXML
    private BorderPane root;
    @FXML
    private AnchorPane gridRoot;
    @FXML
    private MenuItem menuItemNew;

    @FXML
    void initialize(){

    }

    @FXML
    void menuItemCloseOnAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void menuItemNewOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            BorderPane form = loader.load(getClass().getResource("/main/settings.fxml").openStream());
            ControllerSettings controller = (ControllerSettings) loader.getController();
            controller.setRootAnchor(gridRoot);
            Scene scene = new Scene(form);
            stage.setScene(scene);
            stage.setTitle("Settings");
            stage.toFront();
            stage.show();
            menuItemNew.setDisable(true);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    menuItemNew.setDisable(false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuItemPaperReviewOnAction(ActionEvent event) {

    }

}

