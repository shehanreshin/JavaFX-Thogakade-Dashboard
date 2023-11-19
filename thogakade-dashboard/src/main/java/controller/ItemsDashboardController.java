package controller;

import com.jfoenix.controls.JFXButton;
import dto.tm.CustomerTm;
import dto.tm.ItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ItemsDashboardController implements Initializable {

    private double x;
    private double y;

    private Stage stage;

    @FXML
    private AnchorPane dashboard;
    @FXML
    private TableView tblItems;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colUnitPrice;
    @FXML
    private TableColumn colQtyOnHand;
    @FXML
    private TableColumn colOption;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        loadCustomerTable();
    }

    private void loadCustomerTable() {
        ObservableList<Object> tmList = FXCollections.observableArrayList();

        String sql = "select * from thogakade.item";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "thisisnotasecurepassword");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ItemTm itemTm = new ItemTm();
                itemTm.setCode(resultSet.getString(1));
                itemTm.setDescription(resultSet.getString(2));
                itemTm.setUnitPrice(resultSet.getDouble(3));
                itemTm.setQtyOnHand(resultSet.getInt(4));
                itemTm.setButton(new Button("Delete"));
                tmList.add(itemTm);
            }

            statement.close();
            connection.close();

            tblItems.setItems(tmList);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void exit() {
        ((Stage) dashboard.getScene().getWindow()).close();
    }

    public void minimize() {
        ((Stage) dashboard.getScene().getWindow()).setIconified(true);
    }

    public void switchToHomePage() throws IOException {
        stage = (Stage) dashboard.getScene().getWindow();
        Scene scene = getScene("../view/MainDashboard.fxml");

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

    private Scene getScene(String url) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(url)));
        scene.setFill(Color.TRANSPARENT);
        return scene;
    }
}
