/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/1/23, 6:14 PM
 *
 */

package lk.ijse.rohanarenting.controller;

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
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.tm.InsuranceTM;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.InsuranceServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.InsuranceService;
import lk.ijse.rohanarenting.utill.Regex;
import lk.ijse.rohanarenting.utill.TableUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class InsuranceFormController {

    public JFXButton saveBtn;
    public TableView<InsuranceTM> insuranceTable;
    public TextField insuranceIdFld;
    public TableColumn<InsuranceTM, String> columnAddress;
    public TableColumn<InsuranceTM, LocalDate> columnjoinedDate;
    public TextField searchFld;
    @FXML
    private Label notifyLabel;

    @FXML
    private TableColumn<InsuranceTM, String> columnID;

    @FXML
    private TableColumn<InsuranceTM, String> columnName;

    @FXML
    private TableColumn<InsuranceTM, String> columnProvider;

    @FXML
    private TableColumn<InsuranceTM, String> colunmAgentName;

    @FXML
    private TableColumn<InsuranceTM, String> colunmContact;

    @FXML
    private TableColumn<InsuranceTM, String> columnFax;

    @FXML
    private TableColumn<InsuranceTM, String> columnEmail;

    @FXML
    private TableColumn<InsuranceTM, LocalDate> columnExpireDate;

    @FXML
    private TableColumn<InsuranceTM, JFXButton> columnUpdate;

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
    private final InsuranceService insuranceService = (InsuranceServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.INSURANCE_SERVICE);

    public void initialize(){
        saveBtn.setDisable(true);
        setCellValueFactory();
        loadAllInsurance();
        TableUtil.installCopy(insuranceTable);
    }


    private void loadAllInsurance() {
        try {
            ArrayList<InsuranceTM> allInsurance = insuranceService.getAllInsurance();
            for (InsuranceTM insuranceTM:allInsurance) {
                setOnActionOnBtn(insuranceTM);
            }
            insuranceTMS.clear();
            insuranceTMS.addAll(allInsurance);
            insuranceTable.setItems(insuranceTMS);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private void setOnActionOnBtn(InsuranceTM insuranceTM) {
        insuranceTM.getUpdate().setOnAction((e)->{
            insuranceIdFld.setText(insuranceTM.getIID());
            insuranceNameFld.setText(insuranceTM.getName());
            insuranceProviderFld.setText(insuranceTM.getInsuranceProvider());
            agentNameFld.setText(insuranceTM.getAgentName());
            agentContactFld.setText(insuranceTM.getAgentContact());
            addressFld.setText(insuranceTM.getAddress());
            emailFld.setText(insuranceTM.getEmail());
            faxFld.setText(insuranceTM.getFax());
            joingDatePicker.setValue(insuranceTM.getJoinedDate());
            expireDatePicker.setValue(insuranceTM.getExpireDate());
            insuranceIdFld.setDisable(true);
        });
    }

    private void setCellValueFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<InsuranceTM,String>("IID"));
        columnName.setCellValueFactory(new PropertyValueFactory<InsuranceTM,String>("name"));
        columnProvider.setCellValueFactory(new PropertyValueFactory<InsuranceTM,String>("insuranceProvider"));
        colunmAgentName.setCellValueFactory(new PropertyValueFactory<InsuranceTM,String>("agentName"));
        colunmContact.setCellValueFactory(new PropertyValueFactory<InsuranceTM,String>("agentContact"));
        columnFax.setCellValueFactory(new PropertyValueFactory<InsuranceTM,String>("fax"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<InsuranceTM,String>("email"));
        columnjoinedDate.setCellValueFactory(new PropertyValueFactory<InsuranceTM,LocalDate>("joinedDate"));
        columnExpireDate.setCellValueFactory(new PropertyValueFactory<InsuranceTM,LocalDate>("expireDate"));
        columnUpdate.setCellValueFactory(new PropertyValueFactory<InsuranceTM,JFXButton>("update"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<InsuranceTM,String>("address"));
    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void enterOnAction(ActionEvent event) {
        if(Regex.validateVehicleID(insuranceIdFld.getText())) {
            try {
                InsuranceDTO insuranceDTO = insuranceService.getInsurance(new InsuranceDTO(insuranceIdFld.getText(), null, null, null, null, null, null, null, null, null));
                if(insuranceDTO !=null){
                    insuranceIdFld.setText(insuranceDTO.getIID());
                    insuranceNameFld.setText(insuranceDTO.getName());
                    insuranceProviderFld.setText(insuranceDTO.getInsuranceProvider());
                    agentNameFld.setText(insuranceDTO.getAgentName());
                    agentContactFld.setText(insuranceDTO.getAgentContact());
                    addressFld.setText(insuranceDTO.getAddress());
                    emailFld.setText(insuranceDTO.getEmail());
                    faxFld.setText(insuranceDTO.getFax());
                    joingDatePicker.setValue(insuranceDTO.getJoinedDate());
                    expireDatePicker.setValue(insuranceDTO.getExpireDate());
                    insuranceIdFld.setDisable(true);
                }else {
                    new Alert(Alert.AlertType.ERROR,"Insurance Details  Not Found !").show();
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }else {
            try {
                InsuranceDTO insuranceDTO = insuranceService.getInsurance(new InsuranceDTO(insuranceIdFld.getText(), null, null, null, null, null, null, null, null, null));
                if(insuranceDTO !=null){
                    insuranceIdFld.setText(insuranceDTO.getIID());
                    insuranceNameFld.setText(insuranceDTO.getName());
                    insuranceProviderFld.setText(insuranceDTO.getInsuranceProvider());
                    agentNameFld.setText(insuranceDTO.getAgentName());
                    agentContactFld.setText(insuranceDTO.getAgentContact());
                    addressFld.setText(insuranceDTO.getAddress());
                    emailFld.setText(insuranceDTO.getEmail());
                    faxFld.setText(insuranceDTO.getFax());
                    joingDatePicker.setValue(insuranceDTO.getJoinedDate());
                    expireDatePicker.setValue(insuranceDTO.getExpireDate());
                    insuranceIdFld.setDisable(true);
                }else {
                    new Alert(Alert.AlertType.ERROR,"Insurance Details  Not Found !").show();
                }

            } catch (SQLException | NoSuchAlgorithmException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void refreshOnClick(MouseEvent event) {
            if (
                    insuranceService.validateInsuranceId(insuranceIdFld.getText()) &&
                            insuranceService.validateInsuranceName(insuranceNameFld.getText()) &&
                            insuranceService.validateInsuranceProvider(insuranceProviderFld.getText()) &&
                            insuranceService.validateInsuranceName(agentNameFld.getText()) &&
                            insuranceService.validateInsuranceMobileNumber(agentContactFld.getText()) &&
                            insuranceService.validateInsuranceAddress(addressFld.getText()) &&
                            insuranceService.validateInsuranceEmail(emailFld.getText()) &&
                            insuranceService.validateInsuranceFax(faxFld.getText()) &&
                            insuranceService.validateInsuranceDuration(joingDatePicker, expireDatePicker)
            ) {
                notifyLabel.setTextFill(Color.GREEN);
                insuranceIdFld.setText(insuranceIdFld.getText().toUpperCase());
                notifyLabel.setText("All Set !");
                saveBtn.setDisable(false);
            } else {
                notifyLabel.setTextFill(Color.RED);
                notifyLabel.setText("Check the Fields  !");
                saveBtn.setDisable(true);
            }
    }
    @FXML
    void saveBtnOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"Invalid Date Will Not Be Proceed !",ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(ButtonType->{
            if(ButtonType== javafx.scene.control.ButtonType.OK){
                if(insuranceService.validateInsuranceDuration(joingDatePicker,expireDatePicker)) {
                    try {
                        if (insuranceService.verifyInsurance(new InsuranceDTO(insuranceIdFld.getText(), null, null, null, null, null, null, null, null, null))) {
                            new Alert(Alert.AlertType.CONFIRMATION, "New  Insurance Data Will Be Updated ! ", javafx.scene.control.ButtonType.OK, ButtonType.CANCEL).showAndWait().ifPresent(Button -> {
                                if (Button == javafx.scene.control.ButtonType.OK) {
                                    try {
                                        if (insuranceService.updateInsurance(new InsuranceDTO(insuranceIdFld.getText(), insuranceNameFld.getText(), insuranceProviderFld.getText(), agentNameFld.getText(), agentContactFld.getText(), emailFld.getText(), addressFld.getText(), faxFld.getText(), joingDatePicker.getValue(), expireDatePicker.getValue()))) {
                                            new Alert(Alert.AlertType.CONFIRMATION, "Insurance Data Updated !").show();
                                            clearFields();
                                            loadAllInsurance();
                                        } else {
                                            new Alert(Alert.AlertType.ERROR, "Data Not Updated !").show();
                                        }
                                    } catch (SQLException | NoSuchAlgorithmException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "New Insurance Data Will Be Saved !", javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL).showAndWait().ifPresent(BT -> {
                                if (BT == javafx.scene.control.ButtonType.OK) {
                                    try {
                                        if (insuranceService.addInsurance(new InsuranceDTO(insuranceIdFld.getText(), insuranceNameFld.getText(), insuranceProviderFld.getText(), agentNameFld.getText(), agentContactFld.getText(), emailFld.getText(), addressFld.getText(), faxFld.getText(), joingDatePicker.getValue(), expireDatePicker.getValue()))) {
                                            new Alert(Alert.AlertType.CONFIRMATION, "Insurance Data Saved !").show();
                                            clearFields();
                                            loadAllInsurance();
                                        } else {
                                            new Alert(Alert.AlertType.ERROR, "Data Not Saved !").show();
                                        }
                                    } catch (SQLException | NoSuchAlgorithmException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    } catch (SQLException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
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
       insuranceIdFld.setDisable(false);
    }

    public void searchFldOnAction(KeyEvent keyEvent) {
        if(searchFld.getText().trim().isEmpty()){
            loadAllInsurance();
        }else {
            try {
                ArrayList<InsuranceTM> filteredList = insuranceService.searchInsurance("%"+searchFld.getText()+"%");
                ObservableList<InsuranceTM> filterInsuranceTMS = FXCollections.observableArrayList(filteredList);
                for (InsuranceTM insuranceTM:filterInsuranceTMS) {
                    setOnActionOnBtn(insuranceTM.getUpdate());
                }
                if(filterInsuranceTMS.size()>0){
                    insuranceTable.setItems(filterInsuranceTMS);
                }else {
                    insuranceTable.getItems().clear();
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }

    private void setOnActionOnBtn(JFXButton update) {
        update.setOnAction(event -> {
            InsuranceTM insuranceTM = insuranceTable.getSelectionModel().getSelectedItem();
            if(insuranceTM != null){
                insuranceIdFld.setText(insuranceTM.getIID());
                insuranceNameFld.setText(insuranceTM.getName());
                insuranceProviderFld.setText(insuranceTM.getInsuranceProvider());
                addressFld.setText(insuranceTM.getAddress());
                agentContactFld.setText(insuranceTM.getAgentContact());
                agentNameFld.setText(insuranceTM.getAgentName());
                emailFld.setText(insuranceTM.getEmail());
                faxFld.setText(insuranceTM.getFax());
                joingDatePicker.setValue(insuranceTM.getJoinedDate());
                expireDatePicker.setValue(insuranceTM.getExpireDate());
                insuranceIdFld.setDisable(true);
            }else {
               new Alert(Alert.AlertType.ERROR,"Please Select a Row !").show();
            }
        });
    }

    public void insuranceProviderValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(insuranceService.validateInsuranceProvider(insuranceProviderFld.getText())){
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
        if(insuranceService.validateInsuranceName(insuranceNameFld.getText())){
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
            if(insuranceService.validateInsuranceId(insuranceIdFld.getText())){
                notifyLabel.setTextFill(Color.GREEN);
                notifyLabel.setText("Valid ID !");
                insuranceIdFld.setStyle("-fx-border-color: green");
            }else {
                notifyLabel.setTextFill(Color.RED);
                notifyLabel.setText("Invalid ID !");
                insuranceIdFld.setStyle("-fx-border-color: red");
            }
    }

    public void agentNameValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        if(insuranceService.validateInsuranceName(agentNameFld.getText())){
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
        if(insuranceService.validateInsuranceMobileNumber(agentContactFld.getText())){
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
        if(insuranceService.validateInsuranceAddress(addressFld.getText())){
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
        if(insuranceService.validateInsuranceEmail(emailFld.getText())){
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
        if(insuranceService.validateInsuranceFax(faxFld.getText())){
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
        if(insuranceService.validateInsuranceFax(faxFld.getText())){
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
        if(insuranceService.validateInsuranceFax(faxFld.getText())){
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
