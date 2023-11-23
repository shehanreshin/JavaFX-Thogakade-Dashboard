package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import dto.CustomerDTO;
import dto.tm.CustomerTm;
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
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class CustomersDashboardController implements Initializable {

    private double x;
    private double y;
    private final CustomerModel customerModel = new CustomerModelImpl();

    private Stage stage;

    @FXML
    private AnchorPane dashboard;
    @FXML
    private TableView tblCustomers;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colSalary;
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
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        loadCustomerTable();
    }

    private void loadCustomerTable() {
        ObservableList<Object> tmList = FXCollections.observableArrayList();
        try {
            List<CustomerDTO> customerDTOS = customerModel.getAllCustomers();
            for (CustomerDTO customerDTO : customerDTOS) {
                CustomerTm customerTm = new CustomerTm();
                customerTm.setId(customerDTO.getId());
                customerTm.setName(customerDTO.getName());
                customerTm.setAddress(customerDTO.getAddress());
                customerTm.setSalary(customerDTO.getSalary());
                customerTm.setButton(new Button("Delete"));
                tmList.add(customerTm);
            }
            tblCustomers.setItems(tmList);
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
}
