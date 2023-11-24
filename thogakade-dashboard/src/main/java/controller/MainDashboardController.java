package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {

    private double x;
    private double y;

    private Stage stage;

    @FXML
    private AnchorPane dashboard;
    @FXML
    private JFXButton btnDashboard;
    @FXML
    private JFXButton btnCustomers;
    @FXML
    private JFXButton btnItems;
    @FXML
    private JFXButton btnOrders;
    @FXML
    private JFXButton btnPlaceOrders;
    @FXML
    private JFXButton btnSettings;
    @FXML
    private JFXButton btnExit;
    @FXML
    private JFXButton btnMinimize;

    @FXML
    private Label lblTime;
    @FXML
    private Label lblDayOfTheWeek;
    @FXML
    private Label lblAmOrPm;
    @FXML
    private Label lblDay;
    @FXML
    private Label lblMonth;
    @FXML
    private Label lblYear;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calculateTime();
    }

    public void exit() {
        ((Stage) dashboard.getScene().getWindow()).close();
    }

    public void minimize() {
        ((Stage) dashboard.getScene().getWindow()).setIconified(true);
    }

    public void switchToCustomersPage() throws IOException {
        stage = (Stage) dashboard.getScene().getWindow();
        Scene scene = getScene("../view/CustomersDashboard.fxml");

        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.setScene(scene);
    }

    public void switchToItemsPage() throws IOException {
        stage = (Stage) dashboard.getScene().getWindow();
        Scene scene = getScene("../view/ItemsDashboard.fxml");

        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.setScene(scene);
    }

    private Scene getScene(String url) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(url)));
        scene.setFill(Color.TRANSPARENT);
        return scene;
    }

    private void calculateTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        actionEvent -> {
                            LocalDateTime localDateTime = LocalDateTime.now();
                            lblTime.setText(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                            lblDay.setText(String.valueOf(localDateTime.getDayOfMonth()));
                            lblMonth.setText(String.valueOf(localDateTime.getMonth()));
                            lblYear.setText(String.valueOf(localDateTime.getYear()));
                            lblDayOfTheWeek.setText(String.valueOf(localDateTime.getDayOfWeek()).substring(0,3)+",");
                            String ampm = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:a"));
                            lblAmOrPm.setText(
                                    ampm.substring(ampm.length()-2, ampm.length())
                            );
                        }
                ), new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
