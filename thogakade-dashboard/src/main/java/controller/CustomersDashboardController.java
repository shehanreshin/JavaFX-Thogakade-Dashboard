package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import dto.CustomerDTO;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomersDashboardController implements Initializable {

    private double x;
    private double y;
    private final CustomerModel customerModel = new CustomerModelImpl();

    private Stage stage;

    @FXML
    private AnchorPane dashboard;
    @FXML
    private TableView<CustomerTm> tblCustomers;
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
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnReload;

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtSalary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        loadCustomerTable();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        try {
            List<CustomerDTO> dtoList  = customerModel.getAllCustomers();
            for (CustomerDTO customerDTO : dtoList) {
                Button btn = new Button("Delete");
                CustomerTm customerTm = new CustomerTm(
                        customerDTO.getId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getSalary(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomer(customerTm.getId());
                });

                tmList.add(customerTm);
            }
            tblCustomers.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
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

    public void reloadCustomerTable() {
        loadCustomerTable();
    }

    public void updateCustomer() {
        if (isAnyInputDataInvalid()) {
            return;
        }
        try {
            boolean isUpdated = customerModel.updateCustomer(new CustomerDTO(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            ));
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
                loadCustomerTable();
                clearFields();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tblCustomers.refresh();
        txtSalary.clear();
        txtAddress.clear();
        txtName.clear();
        txtId.clear();
        txtId.setEditable(true);
    }

    public void saveCustomer() {
        if (isAnyInputDataInvalid()) {
            return;
        }
        try {
            boolean isSaved = customerModel.createCustomer(new CustomerDTO(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(String id) {
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                loadCustomerTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setData(CustomerTm newValue) {
        if (newValue != null) {
            txtId.setEditable(false);
            txtId.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }

    private boolean validateSalary() {
        double salary;
        try {
            salary = Double.parseDouble(txtSalary.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Salary");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid salary");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateId() {
        Pattern pattern = Pattern.compile("^C[0-9]{3}$");
        Matcher matcher = pattern.matcher(txtId.getText());

        if (matcher.find() && matcher.group().equals(txtId.getText())) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid ID");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid ID");
        alert.showAndWait();
        return false;
    }

    private boolean isAnyInputDataInvalid() {
        return !validateId() | !validateSalary();
    }
}


