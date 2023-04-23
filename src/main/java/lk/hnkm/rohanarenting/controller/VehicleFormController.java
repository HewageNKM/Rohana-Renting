/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/2/23, 1:51 PM
 *
 */

package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.hnkm.rohanarenting.db.DBConnection;
import lk.hnkm.rohanarenting.dto.Vehicle;
import lk.hnkm.rohanarenting.dto.tm.VehicleTM;
import lk.hnkm.rohanarenting.model.VehicleModel;
import lk.hnkm.rohanarenting.utill.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleFormController {
    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private Label notifyLabel;

    @FXML
    private TableView<?> vehiclesTable;

    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TableColumn<?, ?> ColumnManufacturer;

    @FXML
    private TableColumn<?, ?> columnModelName;

    @FXML
    private TableColumn<?, ?> columnDescription;

    @FXML
    private TableColumn<?, ?> columnRentalRate;

    @FXML
    private TableColumn<?, ?> columnAvailability;

    @FXML
    private TableColumn<?, ?> columnCategory;

    @FXML
    private TableColumn<?, ?> columnEdit;

    @FXML
    private TableColumn<?, ?> columnDelete;

    @FXML
    private JFXRadioButton availableRadiBtn;

    @FXML
    private JFXRadioButton nAvailableRadioBtn;

    @FXML
    private TextField licenseFld;

    @FXML
    private TextField manufacturerFld;

    @FXML
    private TextField modelNameFld;

    @FXML
    private TextField rentalRateFld;

    @FXML
    private TextArea descriptionFld;

    @FXML
    private ComboBox<String> categoryComboBox;

    ObservableList vehicles = FXCollections.observableArrayList();

    public void initialize(){
       saveBtn.setDisable(true);
       deleteBtn.setDisable(true);
       setCellValueFactory();
       loadAllVehicles();
       setComboBox();
    }

    private void setComboBox() {
        ObservableList<String> catogory = FXCollections.observableArrayList("A","A1","B","B1","C","C1","CE","D","D1","DE","G","G1","J");
        categoryComboBox.setItems(catogory);
    }

    private void setCellValueFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("VID"));
        ColumnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        columnModelName.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnRentalRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        columnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnEdit.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
        columnDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
    }
    public void loadAllVehicles(){
        try {
            ArrayList <VehicleTM> allVehicles = VehicleModel.getAllVehicles();
            vehicles.clear();
            vehicles.addAll(allVehicles);
            vehiclesTable.setItems(vehicles);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }


    @FXML
    void modelNameValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(modelNameFld.getText().trim().isEmpty()) {
            modelNameFld.setStyle("-fx-border-color: red");
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Brand Name !");
        }else {
            notifyLabel.setTextFill(Color.GREEN);
            modelNameFld.setStyle("-fx-border-color: green");
            notifyLabel.setText("Invalid Brand Name !");
        }
    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        licenseFld.clear();
        manufacturerFld.clear();
        descriptionFld.clear();
        rentalRateFld.clear();
        modelNameFld.clear();
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        nAvailableRadioBtn.setSelected(false);
        availableRadiBtn.setSelected(false);
        notifyLabel.setText("");
        categoryComboBox.getSelectionModel().clearSelection();
        licenseFld.setStyle(null);
        manufacturerFld.setStyle(null);
        modelNameFld.setStyle(null);
        rentalRateFld.setStyle(null);
        descriptionFld.setStyle(null);
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"Vehicle Data Will Be Deleted !", ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(buttonType -> {
            if(buttonType == ButtonType.OK){
                try {
                    if(VehicleModel.deleteVehicle(licenseFld.getText())){
                        new Alert(Alert.AlertType.INFORMATION,"Vehicle Data Deleted !").show();
                        clearFields();
                        loadAllVehicles();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Vehicle Data Not Deleted !").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void enterOnAction(ActionEvent event) {
        try {
            Vehicle vehicle = VehicleModel.getVehicleDetail(licenseFld.getText());
            if(vehicle!= null){
                modelNameFld.setText(vehicle.getModelName());
                System.out.println(modelNameFld.getText());
                descriptionFld.setText(vehicle.getDescription());
                rentalRateFld.setText(vehicle.getRate().toString());
                manufacturerFld.setText(vehicle.getManufacturer());
                System.out.println(manufacturerFld.getText());
                saveBtn.setDisable(false);
                deleteBtn.setDisable(false);
                if (vehicle.getAvailability() == 1) {
                    availableRadiBtn.setSelected(true);
                } else {
                    nAvailableRadioBtn.setSelected(true);
                }
                categoryComboBox.getSelectionModel().select(vehicle.getCategory());
            }else {
                new Alert(Alert.AlertType.ERROR,"No Vehicle Found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void manufacturereValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(Regex.validateName(manufacturerFld.getText())){
            manufacturerFld.setStyle("-fx-border-color: green");
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Name !");
        }else {
            manufacturerFld.setStyle("-fx-border-color: red");
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Name !");
        }
    }

    @FXML
    void rentalRateValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(Regex.validateNumberOnly(rentalRateFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Value !");
            rentalRateFld.setStyle("-fx-border-color: green");
        }else {
            rentalRateFld.setStyle("-fx-border-color: red");
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Value !");
        }
    }

    @FXML
   public void refreshOnClick(MouseEvent event) {
        if(Regex.validateName(manufacturerFld.getText())&&(!modelNameFld.getText().trim().isEmpty())&&Regex.validateVehicleID(licenseFld.getText())&&Regex.validateNumbersAndDecimals(rentalRateFld.getText())&&(!descriptionFld.getText().trim().isEmpty())&&(!categoryComboBox.getSelectionModel().isEmpty())){
            saveBtn.setDisable(false);
            deleteBtn.setDisable(false);
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("All Set !");

        }else {
            saveBtn.setDisable(true);
            deleteBtn.setDisable(true);
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Check the Fields !");
        }
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        Vehicle vehicle = new Vehicle();
        try {
            if(VehicleModel.getVehicle(licenseFld.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"New Vehicle Data Will Be Updated !", ButtonType.YES).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            vehicle.setVID(licenseFld.getText());
                            vehicle.setManufacturer(manufacturerFld.getText());
                            vehicle.setModelName(modelNameFld.getText());
                            vehicle.setDescription(descriptionFld.getText());
                            vehicle.setRate(Double.valueOf(rentalRateFld.getText()));
                           if(availableRadiBtn.isSelected()){
                               vehicle.setAvailability(1);
                           }else {
                               vehicle.setAvailability(0);
                            }
                           vehicle.setCategory(categoryComboBox.getSelectionModel().getSelectedItem().toString());
                            if(VehicleModel.updateVehicle(vehicle)){
                                new Alert(Alert.AlertType.INFORMATION,"Vehicle Data Updated !").show();
                                clearFields();
                                loadAllVehicles();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Vehicle Data Not Updated !").show();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                            e.printStackTrace();
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"New Vehicle Data Will Be Saved !", ButtonType.YES).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            vehicle.setVID(licenseFld.getText());
                            vehicle.setManufacturer(manufacturerFld.getText());
                            vehicle.setModelName(modelNameFld.getText());
                            vehicle.setDescription(descriptionFld.getText());
                            vehicle.setRate(Double.valueOf(rentalRateFld.getText()));
                            if(availableRadiBtn.isSelected()){
                                vehicle.setAvailability(1);
                            }else {
                                vehicle.setAvailability(0);
                            }
                            vehicle.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
                            if(VehicleModel.saveVehicle(vehicle)){
                                new Alert(Alert.AlertType.INFORMATION,"Vehicle Data Saved !").show();
                                clearFields();
                                loadAllVehicles();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Vehicle Data Not Saved !").show();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void licenseValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(Regex.validateVehicleID(licenseFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid ID !");
            licenseFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid ID !");
            licenseFld.setStyle("-fx-border-color: red");
        }
    }

    public void idGenarateOnAction(ActionEvent actionEvent) {

    }

    public void searchOnAction(KeyEvent keyEvent) {

    }

    public void descriptionValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(descriptionFld.getText().trim().isEmpty()){
            descriptionFld.setStyle("-fx-border-color: red");
            notifyLabel.setText("Invalid Description !");
        }else {
            descriptionFld.setStyle("-fx-border-color: green");
            notifyLabel.setText("Valid Description !");
        }
    }

    public void guideBtnOnAction(ActionEvent actionEvent) {

    }

    public void printReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/reports/vehicleList.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

