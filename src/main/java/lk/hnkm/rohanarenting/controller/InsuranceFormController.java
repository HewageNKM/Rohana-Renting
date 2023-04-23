/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/1/23, 6:14 PM
 *
 */

package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.hnkm.rohanarenting.dto.Insurance;
import lk.hnkm.rohanarenting.dto.tm.InsuranceTM;
import lk.hnkm.rohanarenting.model.InsuranceModel;
import lk.hnkm.rohanarenting.utill.Regex;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class InsuranceFormController {

    public JFXButton saveBtn;
    public TableView<InsuranceTM> insuranceTable;
    public TextField insuranceIdFld;
    public TableColumn columnAddress;
    public TableColumn<Object, Object> columnjoinedDate;
    @FXML
    private Label notifyLabel;

    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnProvider;

    @FXML
    private TableColumn<?, ?> colunmAgentName;

    @FXML
    private TableColumn<?, ?> colunmContact;

    @FXML
    private TableColumn<?, ?> columnFax;

    @FXML
    private TableColumn<?, ?> columnEmail;

    @FXML
    private TableColumn<?, ?> columnExpireDate;

    @FXML
    private TableColumn<?, ?> columnUpdate;

    @FXML
    private JFXDatePicker joingDatePicker;

    @FXML
    private JFXDatePicker expireDatePicker;

    @FXML
    private TextField insuranceProviderFld;

    @FXML
    private TextField insuranceNameFld;

    @FXML
    private TextField agentNameFld;

    @FXML
    private TextField agentContactFld;

    @FXML
    private TextField addressFld;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField faxFld;

    ObservableList<InsuranceTM> insuranceTMS =  FXCollections.observableArrayList();

    public void initialize(){
        saveBtn.setDisable(true);
        setCellValueFactory();
        loadAllInsurance();
    }

    private void loadAllInsurance() {
        try {
            ArrayList<InsuranceTM> allInsurance = InsuranceModel.getAllInsurance();
            insuranceTMS.clear();
            insuranceTMS.addAll(allInsurance);
            insuranceTable.setItems(insuranceTMS);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("IID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProvider.setCellValueFactory(new PropertyValueFactory<>("insuranceProvider"));
        colunmAgentName.setCellValueFactory(new PropertyValueFactory<>("agentName"));
        colunmContact.setCellValueFactory(new PropertyValueFactory<>("agentContact"));
        columnFax.setCellValueFactory(new PropertyValueFactory<>("fax"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnjoinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        columnExpireDate.setCellValueFactory(new PropertyValueFactory<>("expireDate"));
        columnUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void enterOnAction(ActionEvent event) {
        if(Regex.validateVehicleID(insuranceIdFld.getText())) {
            try {
                Insurance insurance = InsuranceModel.getVehicleInsurance(insuranceIdFld.getText());
                if(insurance !=null){
                    insuranceIdFld.setText(insurance.getIID());
                    insuranceNameFld.setText(insurance.getName());
                    insuranceProviderFld.setText(insurance.getInsuranceProvider());
                    agentNameFld.setText(insurance.getAgentName());
                    agentContactFld.setText(insurance.getAgentContact());
                    addressFld.setText(insurance.getAddress());
                    emailFld.setText(insurance.getEmail());
                    faxFld.setText(insurance.getFax());
                    joingDatePicker.setValue(insurance.getJoinedDate());
                    expireDatePicker.setValue(insurance.getExpireDate());
                }else {
                    new Alert(Alert.AlertType.ERROR,"Insurance Details  Not Found !").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }else {
            try {
                Insurance insurance = InsuranceModel.getToolInsurance(insuranceIdFld.getText());
                if(insurance !=null){
                    insuranceIdFld.setText(insurance.getIID());
                    insuranceNameFld.setText(insurance.getName());
                    insuranceProviderFld.setText(insurance.getInsuranceProvider());
                    agentNameFld.setText(insurance.getAgentName());
                    agentContactFld.setText(insurance.getAgentContact());
                    addressFld.setText(insurance.getAddress());
                    emailFld.setText(insurance.getEmail());
                    faxFld.setText(insurance.getFax());
                    joingDatePicker.setValue(insurance.getJoinedDate());
                    expireDatePicker.setValue(insurance.getExpireDate());
                }else {
                    new Alert(Alert.AlertType.ERROR,"Insurance Details  Not Found !").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void refreshOnClick(MouseEvent event) {
        try {
            if ((InsuranceModel.isVehicleIdExists(insuranceIdFld.getText()) || InsuranceModel.isToolIdExit(insuranceIdFld.getText())) && Regex.validateNameWithSpaces(insuranceNameFld.getText()) && Regex.validateNameWithSpaces(insuranceProviderFld.getText()) && Regex.validateNameWithSpaces(agentNameFld.getText()) && Regex.validateMobile(agentContactFld.getText()) && Regex.validateEmail(emailFld.getText()) && Regex.validateFax(faxFld.getText()) && Regex.validateAddress(addressFld.getText()) && joingDatePicker.getValue() != null && expireDatePicker.getValue() != null && (expireDatePicker.getValue()!=null)) {
                notifyLabel.setTextFill(Color.GREEN);
                insuranceIdFld.setText(insuranceIdFld.getText().toUpperCase());
                notifyLabel.setText("All Set !");
                saveBtn.setDisable(false);
            } else {
                notifyLabel.setTextFill(Color.RED);
                notifyLabel.setText("Check the Fields  !");
                saveBtn.setDisable(true);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }
    @FXML
    void saveBtnOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"Invalid Date Will Not Be Proceed !",ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(ButtonType->{
            if(ButtonType==ButtonType.OK){
                if((ChronoUnit.MONTHS.between(joingDatePicker.getValue(), expireDatePicker.getValue())>=12)&&expireDatePicker.getValue().isAfter(LocalDate.now())) {
                    if (Regex.validateVehicleID(insuranceIdFld.getText())) {
                        try {
                            if (InsuranceModel.isVehicleInsuranceDetailsExist(insuranceIdFld.getText())) {
                                new Alert(Alert.AlertType.CONFIRMATION, "New Vehicle Insurance Data Will Be Updated ! ", javafx.scene.control.ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(Button -> {
                                    if (Button == javafx.scene.control.ButtonType.OK) {
                                        try {
                                            Boolean isUpdated = InsuranceModel.updateVehicleInsuranceDetails(new Insurance(insuranceIdFld.getText(), insuranceNameFld.getText(), insuranceProviderFld.getText(), agentNameFld.getText(), agentContactFld.getText(), emailFld.getText(), addressFld.getText(), faxFld.getText(), joingDatePicker.getValue(), expireDatePicker.getValue()));
                                            if (isUpdated) {
                                                new Alert(Alert.AlertType.INFORMATION, "Data Updated !").show();
                                                clearFields();
                                                loadAllInsurance();
                                            } else {
                                                new Alert(Alert.AlertType.ERROR, "Data Not Updated ! ").show();
                                            }
                                        } catch (SQLException e) {
                                            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                new Alert(Alert.AlertType.CONFIRMATION, "New Vehicle Insurance Data Will Be Saved !", ButtonType.OK, ButtonType.CANCEL).showAndWait().ifPresent(Btn -> {
                                    if (Btn == ButtonType.OK) {
                                        try {
                                            Boolean isUpdated = InsuranceModel.addVehicleInsuranceDetails(new Insurance(insuranceIdFld.getText(), insuranceNameFld.getText(), insuranceProviderFld.getText(), agentNameFld.getText(), agentContactFld.getText(), emailFld.getText(), addressFld.getText(), faxFld.getText(), joingDatePicker.getValue(), expireDatePicker.getValue()));
                                            if (isUpdated) {
                                                new Alert(Alert.AlertType.INFORMATION, "Data Saved !").show();
                                                clearFields();
                                                loadAllInsurance();
                                            } else {
                                                new Alert(Alert.AlertType.ERROR, "Data Not Saved ! ").show();
                                            }
                                        } catch (SQLException e) {
                                            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (InsuranceModel.isToolInsuranceDetailsExist(insuranceIdFld.getText())) {
                                new Alert(Alert.AlertType.CONFIRMATION, "New Tool Insurance Data Will Be Updated !", ButtonType.OK, ButtonType.CANCEL).showAndWait().ifPresent(BT -> {
                                    if (BT == javafx.scene.control.ButtonType.OK) {
                                        try {
                                            Boolean isUpdated = InsuranceModel.updateToolInsuranceDetails(new Insurance(insuranceIdFld.getText(), insuranceNameFld.getText(), insuranceProviderFld.getText(), agentNameFld.getText(), agentContactFld.getText(), emailFld.getText(), addressFld.getText(), faxFld.getText(), joingDatePicker.getValue(), expireDatePicker.getValue()));
                                            if (isUpdated) {
                                                new Alert(Alert.AlertType.INFORMATION, "Data Updated !").show();
                                                clearFields();
                                                loadAllInsurance();
                                            } else {
                                                new Alert(Alert.AlertType.ERROR, "Data Not Updated ! ").show();
                                            }
                                        } catch (SQLException e) {
                                            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                new Alert(Alert.AlertType.CONFIRMATION, "New Tool Insurance Data Will Be Saved !", ButtonType.OK, ButtonType.CANCEL).showAndWait().ifPresent(b -> {
                                    if (b == ButtonType.OK) {
                                        try {
                                            Boolean isUpdated = InsuranceModel.addToolInsuranceDetails(new Insurance(insuranceIdFld.getText(), insuranceNameFld.getText(), insuranceProviderFld.getText(), agentNameFld.getText(), agentContactFld.getText(), emailFld.getText(), addressFld.getText(), faxFld.getText(), joingDatePicker.getValue(), expireDatePicker.getValue()));
                                            if (isUpdated) {
                                                new Alert(Alert.AlertType.INFORMATION, "Data Saved !").show();
                                                clearFields();
                                                loadAllInsurance();
                                            } else {
                                                new Alert(Alert.AlertType.ERROR, "Data Not Saved ! ").show();
                                            }
                                        } catch (SQLException e) {
                                            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                            e.printStackTrace();
                        }
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Insurance Period Should Be At Least 12 Months, Check the Date !").show();
                }
            }
        });
    }
    private void clearFields() {
        insuranceIdFld.clear();
        insuranceNameFld.clear();
        insuranceProviderFld.clear();
        addressFld.clear();
        agentContactFld.clear();
        agentNameFld.clear();
        emailFld.clear();
        faxFld.clear();
        saveBtn.setDisable(true);
       notifyLabel.setText("");
       faxFld.setStyle(null);
       insuranceNameFld.setStyle(null);
       insuranceProviderFld.setStyle(null);
       addressFld.setStyle(null);
       agentContactFld.setStyle(null);
       agentNameFld.setStyle(null);
       emailFld.setStyle(null);
       joingDatePicker.setStyle(null);
       joingDatePicker.setValue(null);
       expireDatePicker.setStyle(null);
       expireDatePicker.setValue(null);
       insuranceIdFld.setStyle(null);
    }

    public void searchFldOnAction(KeyEvent keyEvent) {

    }

    public void insuranceProviderValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateNameWithSpaces(insuranceProviderFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Provider Name !");
            insuranceProviderFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Provider Name !");
            insuranceProviderFld.setStyle("-fx-border-color: red");
        }
    }

    public void insuranceNameValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateNameWithSpaces(insuranceNameFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Insurance Name !");
            insuranceNameFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Insurance Name !");
            insuranceNameFld.setStyle("-fx-border-color: red");
        }
    }

    public void IIDValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        try {
            if(InsuranceModel.isToolIdExit(insuranceIdFld.getText())||(InsuranceModel.isVehicleIdExists(insuranceIdFld.getText()))){
                notifyLabel.setTextFill(Color.GREEN);
                notifyLabel.setText("Valid ID !");
                insuranceIdFld.setStyle("-fx-border-color: green");
            }else {
                notifyLabel.setTextFill(Color.RED);
                notifyLabel.setText("Invalid ID !");
                insuranceIdFld.setStyle("-fx-border-color: red");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    public void agentNameValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateNameWithSpaces(agentNameFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Name !");
            agentNameFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Name !");
            agentNameFld.setStyle("-fx-border-color: red");
        }
    }

    public void agentContactValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateMobile(agentContactFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Contact !");
            agentContactFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Contact !");
            agentContactFld.setStyle("-fx-border-color: red");
        }
    }

    public void agentAddressValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateAddress(addressFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Address !");
            addressFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Address !");
            addressFld.setStyle("-fx-border-color: red");
        }
    }

    public void emailValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateEmail(emailFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Address !");
            emailFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Address !");
            emailFld.setStyle("-fx-border-color: red");
        }
    }

    public void faxValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateFax(faxFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Fax !");
            faxFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Fax !");
            faxFld.setStyle("-fx-border-color: red");
        }
    }

    public void joinedKeyDateValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateFax(faxFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Date !");
            faxFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Date !");
            faxFld.setStyle("-fx-border-color: red");
        }
    }
    public void joinedMouseDateValidate(MouseEvent mouseEvent) {
        saveBtn.setDisable(true);
        if(Regex.validateFax(faxFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Date !");
            faxFld.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Date !");
            faxFld.setStyle("-fx-border-color: red");
        }
    }
    public void expireKeyDateValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(expireDatePicker.getValue()!=null && ChronoUnit.MONTHS.between(joingDatePicker.getValue(), expireDatePicker.getValue())>=12){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Date !");
            expireDatePicker.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            expireDatePicker.setStyle("-fx-border-color: red");
        }
    }
    public void expireMouseDateValidate(MouseEvent mouseEvent) {
        saveBtn.setDisable(true);
        if(expireDatePicker.getValue()!=null && ChronoUnit.MONTHS.between(joingDatePicker.getValue(), expireDatePicker.getValue())>=12){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Date !");
            expireDatePicker.setStyle("-fx-border-color: green");
        }else {
            notifyLabel.setTextFill(Color.RED);
            expireDatePicker.setStyle("-fx-border-color: red");
        }
    }
}
