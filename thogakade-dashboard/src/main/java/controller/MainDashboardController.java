package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {

    private Stage stage;

    @FXML
    private JFXButton btnExit;
    @FXML
    private JFXButton btnMinimize;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void exit(ActionEvent actionEvent) {
        stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    public void minimize(ActionEvent actionEvent) {
        stage = (Stage) btnExit.getScene().getWindow();
        stage.setIconified(true);
    }
}
