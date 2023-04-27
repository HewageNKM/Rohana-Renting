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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.hnkm.rohanarenting.dto.Insurance;
import lk.hnkm.rohanarenting.dto.Vehicle;
import lk.hnkm.rohanarenting.dto.tm.VehicleTM;
import lk.hnkm.rohanarenting.model.VehicleModel;
import lk.hnkm.rohanarenting.notification.TopUpNotifications;
import lk.hnkm.rohanarenting.utill.Regex;
import lk.hnkm.rohanarenting.utill.TableUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VehicleFormController {
    public TableColumn<Object, Object> columnReport;
    public TextField searchFld;
    public Label orderStatusLabel;
    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private Label notifyLabel;

    @FXML
    private TableView<VehicleTM> vehiclesTable;

    @FXML
    private TableColumn<VehicleTM, String> columnID;

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

    ObservableList<VehicleTM> vehicles = FXCollections.observableArrayList();

    public void initialize(){
       saveBtn.setDisable(true);
       deleteBtn.setDisable(true);
       setCellValueFactory();
       loadAllVehicles();
       setComboBox();
       TableUtil.installCopy(vehiclesTable);
    }

    private void setComboBox() {
        ObservableList<String> catogory = FXCollections.observableArrayList("A","A1","B","B1","C","C1","CE","D","D1","DE","G","G1","J");
        categoryComboBox.setItems(catogory);
    }

    private void setCellValueFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<VehicleTM,String>("VID"));
        ColumnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        columnModelName.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnRentalRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        columnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnEdit.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
        columnDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
        columnReport.setCellValueFactory(new PropertyValueFactory<>("showBtn"));
    }
    public void loadAllVehicles(){
        try {
            ArrayList <VehicleTM> allVehicles = VehicleModel.getAllVehicles();
            for (VehicleTM vehicleTM:allVehicles) {
                setShowBtnAction(vehicleTM.getShowBtn());
                setEditBtnAction(vehicleTM.getEditBtn());
                setDeleteBtnAction(vehicleTM.getDeleteBtn());
            }
            vehicles.clear();
            vehicles.addAll(allVehicles);
            vehiclesTable.setItems(vehicles);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setDeleteBtnAction(JFXButton deleteBtn) {
        deleteBtn.setOnAction(event -> {
            VehicleTM selectedItem = vehiclesTable.getSelectionModel().getSelectedItem();
            if(selectedItem!= null){
                new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this Vehicle ?",ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            boolean isDeleted = VehicleModel.deleteVehicle(selectedItem.getVID());
                            if(isDeleted){
                                TopUpNotifications.success("Vehicle Deleted Successfully!");
                                loadAllVehicles();
                                clearFields();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Vehicle Delete Failed").show();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR,"Vehicle Can't Be Deleted At the Moment!, Error Code: "+e.getErrorCode()).show();
                            e.printStackTrace();
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.ERROR,"Please Select a Vehicle ").show();
            }
        });
    }

    private void setEditBtnAction(JFXButton editBtn) {
        editBtn.setOnAction(event -> {
            VehicleTM selectedItem = vehiclesTable.getSelectionModel().getSelectedItem();
            if(selectedItem!= null) {
                licenseFld.setText(selectedItem.getVID());
                manufacturerFld.setText(selectedItem.getManufacturer());
                modelNameFld.setText(selectedItem.getModelName());
                descriptionFld.setText(selectedItem.getDescription());
                rentalRateFld.setText(String.valueOf(selectedItem.getRate()));
                categoryComboBox.setValue(selectedItem.getCategory());
                Boolean isExist = null;
                try {
                    isExist = VehicleModel.checkOrderStatus(selectedItem.getVID());
                    if(isExist){
                        orderStatusLabel.setStyle("-fx-text-fill: red");
                        orderStatusLabel.setText("This Vehicle is in an Order");
                        if (selectedItem.getAvailability().equals("Available")) {
                            availableRadiBtn.setSelected(true);
                        } else {
                            nAvailableRadioBtn.setSelected(true);
                        }
                        nAvailableRadioBtn.setDisable(true);
                        availableRadiBtn.setDisable(true);
                    }else {
                        if (selectedItem.getAvailability().equals("Available")) {
                            availableRadiBtn.setSelected(true);
                        } else {
                            nAvailableRadioBtn.setSelected(true);
                        }
                    }
                    if (selectedItem.getAvailability().equals("Available")) {
                        availableRadiBtn.setSelected(true);
                    } else {
                        nAvailableRadioBtn.setSelected(true);
                    }
                    licenseFld.setDisable(true);
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                    e.printStackTrace();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Please Select a Vehicle ").show();
            }
        });
    }

    private void setShowBtnAction(JFXButton showBtn) {
        showBtn.setOnAction(event -> {
            VehicleTM selectedItem = vehiclesTable.getSelectionModel().getSelectedItem();
            if(selectedItem!=null){
                printVehicleReport(selectedItem);
            }else {
                new Alert(Alert.AlertType.ERROR,"Please Select a Vehicle ").show();
            }
        });
    }

    private void printVehicleReport(VehicleTM vehicleTM) {
        try {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(vehicleTM));
            Insurance insurance = VehicleModel.getInsuranceDetails(vehicleTM.getVID());
            Map<String, Object> params = new HashMap<>();
            params.put("VID",vehicleTM.getVID());
            params.put("manufacturer",vehicleTM.getManufacturer());
            params.put("modelName",vehicleTM.getModelName());
            params.put("description",vehicleTM.getDescription());
            params.put("rate",vehicleTM.getRate());
            params.put("Availability",vehicleTM.getAvailability());
            params.put("codeNumber",vehicleTM.getVID());
            params.put("IID",insurance.getIID());
            params.put("insuranceName",insurance.getName());
            params.put("iPName",insurance.getInsuranceProvider());
            params.put("fax",insurance.getFax());
            params.put("email",insurance.getEmail());
            params.put("agentContact",insurance.getAgentContact());
            params.put("agentName",insurance.getAgentName());
            params.put("expireDate", Date.valueOf(insurance.getExpireDate()));
            params.put("joinedDate",Date.valueOf(insurance.getJoinedDate()));
            params.put("address",insurance.getAddress());
            try {
                JasperReport compileReport = JasperCompileManager.compileReport(
                        JRXmlLoader.load(
                                getClass().getResourceAsStream(
                                        "/reports/vehicle_Report.jrxml"
                                )
                        )
                );
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, params,dataSource);
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
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
        availableRadiBtn.setDisable(false);
        nAvailableRadioBtn.setDisable(false);
        orderStatusLabel.setText("");
        orderStatusLabel.setStyle(null);
        licenseFld.setDisable(false);
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"Vehicle Data Will Be Deleted !", ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(buttonType -> {
            if(buttonType == ButtonType.OK){
                try {
                    if(VehicleModel.deleteVehicle(licenseFld.getText())){
                        TopUpNotifications.success("Vehicle Data Deleted Successfully !");
                        clearFields();
                        loadAllVehicles();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Vehicle Data Not Deleted !").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,"Vehicle Can't Be Deleted At the Moment!, Error Code: "+e.getErrorCode()).show();
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
                Boolean isExist = VehicleModel.checkOrderStatus(licenseFld.getText());
                if(isExist){
                    orderStatusLabel.setStyle("-fx-text-fill: red");
                    orderStatusLabel.setText("This Vehicle is in an Order");
                    if (vehicle.getAvailability().equals("Available")) {
                        availableRadiBtn.setSelected(true);
                    } else {
                        nAvailableRadioBtn.setSelected(true);
                    }
                    nAvailableRadioBtn.setDisable(true);
                    availableRadiBtn.setDisable(true);
                }else {
                    if (vehicle.getAvailability().equals("Available")) {
                        availableRadiBtn.setSelected(true);
                    } else {
                        nAvailableRadioBtn.setSelected(true);
                    }
                }
                if (vehicle.getAvailability().equals("Available")) {
                    availableRadiBtn.setSelected(true);
                } else {
                    nAvailableRadioBtn.setSelected(true);
                }
                categoryComboBox.getSelectionModel().select(vehicle.getCategory());
                licenseFld.setDisable(true);
            }else {
                new Alert(Alert.AlertType.ERROR,"No Vehicle Found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void manufacturerValidate(KeyEvent event) {
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
            licenseFld.setText(licenseFld.getText().toUpperCase());

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
                new Alert(Alert.AlertType.CONFIRMATION,"New Vehicle Data Will Be Updated !", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            vehicle.setVID(licenseFld.getText());
                            vehicle.setManufacturer(manufacturerFld.getText());
                            vehicle.setModelName(modelNameFld.getText());
                            vehicle.setDescription(descriptionFld.getText());
                            vehicle.setRate(Double.valueOf(rentalRateFld.getText()));
                           if(availableRadiBtn.isSelected()){
                               vehicle.setAvailability("Available");
                           }else {
                               vehicle.setAvailability("Not Available");
                            }
                           vehicle.setCategory(categoryComboBox.getSelectionModel().getSelectedItem().toString());
                            Boolean isExist = VehicleModel.checkOrderStatus(licenseFld.getText());
                            if(isExist){
                                new Alert(Alert.AlertType.WARNING,"Due to Ongoing Order On This Vehicle Manual Availability Will Not Be Effect !").showAndWait();
                                Boolean isUpdated =VehicleModel.updateVehicleWithoutAvailability(vehicle);
                                if(isUpdated){
                                    TopUpNotifications.success("Vehicle Data Updated !");
                                    clearFields();
                                    loadAllVehicles();
                                }else {
                                    new Alert(Alert.AlertType.INFORMATION,"Vehicle Data Not Updated !").show();
                                    clearFields();
                                    loadAllVehicles();
                                }

                            }else {
                                Boolean isUpdated =VehicleModel.updateVehicle(vehicle);
                                if(isUpdated){
                                    TopUpNotifications.success("Vehicle Data Updated !");
                                    clearFields();
                                    loadAllVehicles();
                                }else {
                                    new Alert(Alert.AlertType.INFORMATION,"Vehicle Data Not Updated !").show();
                                    clearFields();
                                    loadAllVehicles();
                                }
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                            e.printStackTrace();
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"New Vehicle Data Will Be Saved !", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            vehicle.setVID(licenseFld.getText());
                            vehicle.setManufacturer(manufacturerFld.getText());
                            vehicle.setModelName(modelNameFld.getText());
                            vehicle.setDescription(descriptionFld.getText());
                            vehicle.setRate(Double.valueOf(rentalRateFld.getText()));
                            if(availableRadiBtn.isSelected()){
                                vehicle.setAvailability("Available");
                            }else {
                                vehicle.setAvailability("Not Available");
                            }
                            vehicle.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
                            if(VehicleModel.saveVehicle(vehicle)){
                                TopUpNotifications.success("Vehicle Data Saved !");
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


    public void searchOnAction(KeyEvent keyEvent) {
        if (searchFld.getText().trim().isEmpty()) {
            loadAllVehicles();
        } else {
            try {
                ArrayList<VehicleTM> arrayList = VehicleModel.getSearchVehicles("%" + searchFld.getText() + "%");
                for (VehicleTM vehicleTM : arrayList) {
                    setEditBtnAction(vehicleTM.getEditBtn());
                    setDeleteBtnAction(vehicleTM.getDeleteBtn());
                    setShowBtnAction(vehicleTM.getShowBtn());
                }
                ObservableList<VehicleTM> vehicles = FXCollections.observableArrayList(arrayList);
                if (vehicles != null) {
                    vehiclesTable.setItems(vehicles);
                } else {
                    vehiclesTable.getItems().clear();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
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
        File htmlFile = new File("D:\\GDSE\\JavaProject\\RohanaRenting\\src\\main\\resources\\html\\guide.html");
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }
}

