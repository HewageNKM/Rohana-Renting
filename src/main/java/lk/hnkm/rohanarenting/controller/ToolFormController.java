/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/1/23, 6:50 PM
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
import lk.hnkm.rohanarenting.dto.Insurance;
import lk.hnkm.rohanarenting.dto.Tool;
import lk.hnkm.rohanarenting.dto.tm.JasperReportToolTM;
import lk.hnkm.rohanarenting.dto.tm.ToolTM;
import lk.hnkm.rohanarenting.model.EmployeeModel;
import lk.hnkm.rohanarenting.model.ToolModel;
import lk.hnkm.rohanarenting.utill.Genarate;
import lk.hnkm.rohanarenting.utill.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ToolFormController {

    public TableColumn columnAvailability;
    public TableColumn columnEdit;
    public TableColumn columnDelete;
    public TableColumn columnRentalRate;
    public TableColumn columnBrandName;
    public Label orderStatusLabel;
    public TableColumn columnReport;
    public TextField searchFld;
    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private Label notifyLabel;

    @FXML
    private TableView<ToolTM> toolsTable;

    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TableColumn<?, ?> ColumnName;

    @FXML
    private TableColumn<?, ?> columnDescription;

    @FXML
    private JFXRadioButton availableRadiBtn;

    @FXML
    private JFXRadioButton nAvailableRadioBtn;

    @FXML
    private TextField toolIdFld;

    @FXML
    private TextField brandNameFld;

    @FXML
    private TextField nameFld;

    @FXML
    private TextField rentalRateFld;

    @FXML
    private TextArea drecriptionFld;
    ObservableList<ToolTM> toolsList = FXCollections.observableArrayList();

    public void initialize(){
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        setCellFactory();
        loadAllTools();
        genarateID();
    }

    private void loadAllTools() {
        try {
            ArrayList<ToolTM> arrayList  = ToolModel.getTools();
            for (ToolTM toolTM:arrayList) {
                setShowBtnAction(toolTM.getShowBtn());
                setEditBtnAction(toolTM.getEditBtn());
                setDeleteBtnAction(toolTM.getDeleteBtn());
            }
            toolsList.clear();
            toolsList.addAll(arrayList);
            toolsTable.setItems(toolsList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setDeleteBtnAction(JFXButton deleteBtn) {
        deleteBtn.setOnAction(event -> {
            new Alert(Alert.AlertType.CONFIRMATION,"Are Sure You Want To Delete ?",ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(ButtonType->{
                if(ButtonType == javafx.scene.control.ButtonType.YES){
                    ToolTM toolTM = toolsTable.getSelectionModel().getSelectedItem();
                    if(toolTM!=null){
                        try {
                            ToolModel.deleteTool(toolTM.getTID());
                            loadAllTools();
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR,"Tool Can't Delete At the Moment!, Error Code: "+e.getErrorCode()).show();
                            e.printStackTrace();
                        }
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Please Select Row !").show();
                    }
                }
            });
        });
    }

    private void setEditBtnAction(JFXButton editBtn) {
        editBtn.setOnAction(event -> {
            ToolTM toolTM = toolsTable.getSelectionModel().getSelectedItem();
            if(toolTM != null){
                toolIdFld.setText(toolTM.getTID());
                brandNameFld.setText(toolTM.getBrandName());
                nameFld.setText(toolTM.getName());
                rentalRateFld.setText(String.valueOf(toolTM.getRate()));
                try {
                    Boolean isExist = ToolModel.checkOrderStatus(toolTM.getTID());
                    if(isExist){
                        orderStatusLabel.setStyle("-fx-text-fill: red");
                        orderStatusLabel.setText("Tool is in an Order ");
                        if(toolTM.getAvailability().equals("Available")){
                            availableRadiBtn.setSelected(true);
                        }else {
                            nAvailableRadioBtn.setSelected(true);
                        }
                        availableRadiBtn.setDisable(true);
                        nAvailableRadioBtn.setDisable(true);
                    }else {
                        if(toolTM.getAvailability().equals("Available")){
                            availableRadiBtn.setSelected(true);
                        }else {
                            nAvailableRadioBtn.setSelected(true);
                        }
                        availableRadiBtn.setDisable(false);
                        nAvailableRadioBtn.setDisable(false);
                        orderStatusLabel.setText(null);
                        orderStatusLabel.setStyle(null);
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                    e.printStackTrace();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Please Select Row !").show();
            }
        });
    }

    private void setShowBtnAction(JFXButton showBtn) {
        showBtn.setOnAction(event -> {
            ToolTM toolTM = toolsTable.getSelectionModel().getSelectedItem();
            if(toolTM != null){
                printToolReport(toolTM);
            }else {
                new Alert(Alert.AlertType.WARNING,"Please Select Row !").show();
            }
        });
    }

    private void printToolReport(ToolTM toolTM) {
        HashMap<String,Object> params = new HashMap<>();
        try {
            Insurance insurance = ToolModel.getInsurance(toolTM.getTID());
            params.put("TID",toolTM.getTID());
            params.put("codeNumber",toolTM.getTID());
            params.put("brandName",toolTM.getBrandName());
            params.put("name",toolTM.getName());
            params.put("availability",toolTM.getAvailability());
            params.put("rate",toolTM.getRate());
            params.put("description",toolTM.getDescription());
            params.put("IID",insurance.getIID());
            params.put("insuranceName",insurance.getName());
            params.put("iPName",insurance.getInsuranceProvider());
            params.put("agentName",insurance.getAgentName());
            params.put("agentContact",insurance.getAgentContact());
            params.put("fax",insurance.getFax());
            params.put("address",insurance.getAddress());
            params.put("email",insurance.getEmail());
            params.put("expireDate", Date.valueOf(insurance.getExpireDate()));
            params.put("joinedDate",Date.valueOf(insurance.getJoinedDate()));

            ArrayList<JasperReportToolTM> jasperToolTMS = ToolModel.getJasperToolTM(toolTM.getTID());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jasperToolTMS);


            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/reports/tool_Report.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, params,dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setCellFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("tID"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnRentalRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        columnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        columnBrandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        columnEdit.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
        columnDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
        columnReport.setCellValueFactory(new PropertyValueFactory<>("showBtn"));
    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"Tool Data Will Be Deleted !", ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(buttonType -> {
            if(buttonType == ButtonType.OK){
                try {
                    if(ToolModel.deleteTool(toolIdFld.getText())){
                        new Alert(Alert.AlertType.INFORMATION,"Tool Data Deleted !").show();
                        clearFields();
                        loadAllTools();
                        genarateID();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Tool Data Not Deleted !").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,"Tool Can't Be Delete At the Moment!, Error Code: "+e.getErrorCode()).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void descriptionValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(drecriptionFld.getText().trim().isEmpty()){
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Description !");
            drecriptionFld.setStyle("-fx-border-color: red");
        }else {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Description!");
            drecriptionFld.setStyle("-fx-border-color: green");
        }
    }

    @FXML
    void enterOnAction(ActionEvent event) {
        try {
            Tool tool = ToolModel.getToolDetail(toolIdFld.getText());
            if(tool!= null){
                nameFld.setText(tool.getName());
                drecriptionFld.setText(tool.getDescription());
                rentalRateFld.setText(tool.getRate().toString());
                brandNameFld.setText(tool.getBrand());
                saveBtn.setDisable(false);
                deleteBtn.setDisable(false);
                if (tool.getAvalability().equals("Available")) {
                    availableRadiBtn.setSelected(true);
                } else {
                    nAvailableRadioBtn.setSelected(true);
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"No Tool Found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void idGenarateOnAction(ActionEvent event) {
        genarateID();
    }

    private void genarateID() {
        String id = Genarate.generateToolId();
        try {
            while (EmployeeModel.verifyId(id)) {
                id = Genarate.generateToolId();
            }
            toolIdFld.setText(id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
   public void refreshOnClick(MouseEvent event) {
        if(Regex.validateToolId(toolIdFld.getText())&&(!drecriptionFld.getText().trim().isEmpty())&&(!nameFld.getText().trim().isEmpty())&&(!brandNameFld.getText().trim().isEmpty())&&Regex.validateNumbersAndDecimals(rentalRateFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            toolIdFld.setText(toolIdFld.getText().toUpperCase());
            notifyLabel.setText("All Set !");
            saveBtn.setDisable(false);
            deleteBtn.setDisable(false);
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Check the Fields !");
            saveBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        Tool tool = new Tool();
        try {
            if(ToolModel.getToolDetail(toolIdFld.getText())!=null){
                new Alert(Alert.AlertType.CONFIRMATION,"New Tool Data Will Be Updated !", ButtonType.YES).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            tool.setTID(toolIdFld.getText());
                            tool.setName(nameFld.getText());
                            tool.setBrand(brandNameFld.getText());
                            tool.setRate(Double.valueOf(rentalRateFld.getText()));
                            tool.setDescription(drecriptionFld.getText());
                            if(availableRadiBtn.isSelected()){
                                tool.setAvalability("Available");
                            }else {
                                tool.setAvalability("Not Available");
                            }
                            if(ToolModel.updateTool(tool)){
                                new Alert(Alert.AlertType.INFORMATION,"Tool Data Updated !").show();
                                clearFields();
                                loadAllTools();
                                genarateID();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Tool Data Not Updated !").show();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                            e.printStackTrace();
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"New Tool Data Will Be Saved !", ButtonType.YES).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            tool.setTID(toolIdFld.getText());
                            tool.setName(nameFld.getText());
                            tool.setBrand(brandNameFld.getText());
                            tool.setDescription(drecriptionFld.getText());
                            tool.setRate(Double.valueOf(rentalRateFld.getText()));
                            if(availableRadiBtn.isSelected()){
                                tool.setAvalability("Available");
                            }else {
                                tool.setAvalability("Not Available");
                            }
                            if(ToolModel.saveTool(tool)){
                                new Alert(Alert.AlertType.INFORMATION,"Tool Data Saved !").show();
                                loadAllTools();
                                clearFields();
                                genarateID();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Tool Data Not Saved !").show();
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

    private void clearFields() {
        toolIdFld.setStyle(null);
        nameFld.setStyle(null);
        drecriptionFld.setStyle(null);
        rentalRateFld.setStyle(null);
        brandNameFld.setStyle(null);
        toolIdFld.clear();
        nameFld.clear();
        drecriptionFld.clear();
        rentalRateFld.clear();
        brandNameFld.clear();
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        availableRadiBtn.setSelected(false);
        nAvailableRadioBtn.setSelected(false);
        notifyLabel.setText("");
    }

    @FXML
    void toolIdValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(Regex.validateToolId(toolIdFld.getText())){
            toolIdFld.setStyle("-fx-border-color: green");
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Tool ID !");
        }else {
            toolIdFld.setStyle("-fx-border-color: red");
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Tool ID !");
        }
    }

    public void nameValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(Regex.validateName(nameFld.getText())){
            nameFld.setStyle("-fx-border-color: green");
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Name!");
        }else {
           nameFld.setStyle("-fx-border-color: red");
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Name !");
        }
    }

    public void brandNameValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(brandNameFld.getText().trim().isEmpty()){
            brandNameFld.setStyle("-fx-border-color: red");
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Brand Name !");
        }else {
            brandNameFld.setStyle("-fx-border-color: green");
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Brand !");
        }
    }

    public void rentalRateValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(Regex.validateNumberOnly(rentalRateFld.getText())) {
            rentalRateFld.setStyle("-fx-border-color: green");
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Value !");
        }else {
            rentalRateFld.setStyle("-fx-border-color: red");
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Value !");
        }
    }

    public void searchFldOnAction(KeyEvent keyEvent) {
        if(searchFld.getText().trim().isEmpty()){
            loadAllTools();
        }else {
            try {
                ArrayList<ToolTM> filterTools = ToolModel.searchTools("%"+searchFld.getText()+"%");
                ObservableList<ToolTM> filterToolsTMS = FXCollections.observableArrayList(filterTools);
                if(filterToolsTMS!=null){
                  toolsTable.setItems(filterToolsTMS);
                }else {
                    toolsTable.getItems().clear();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }
}
