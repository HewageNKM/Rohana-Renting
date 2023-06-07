/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/1/23, 6:50 PM
 *
 */

package lk.ijse.rohanarenting.controller;

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
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.ToolDTO;
import lk.ijse.rohanarenting.dto.tm.ToolTM;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.ToolServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.ToolService;
import lk.ijse.rohanarenting.utill.TableUtil;
import lk.ijse.rohanarenting.utill.notification.TopUpNotifications;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
    private final ToolService toolService = (ToolServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.TOOL_SERVICE);

    @SneakyThrows
    public void initialize(){
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        setCellFactory();
        loadAllTools();
        toolService.generateID();
        TableUtil.installCopy(toolsTable);
    }

    private void loadAllTools() {
        try {
            ArrayList<ToolTM> arrayList  = toolService.getAllTool();
            for (ToolTM toolTM:arrayList) {
                setShowBtnAction(toolTM.getShowBtn());
                setEditBtnAction(toolTM.getEditBtn());
                setDeleteBtnAction(toolTM.getDeleteBtn());
            }
            toolsList.clear();
            toolsList.addAll(arrayList);
            toolsTable.setItems(toolsList);
        } catch (Exception e) {
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
                            toolService.deleteTool(new ToolDTO(toolTM.getTID(),null,null,null,null,null));
                            loadAllTools();
                        } catch (Exception e) {
                            new Alert(Alert.AlertType.ERROR,"Tool Can't Delete At the Moment!, Error Code: "+e.getMessage()).show();
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
                drecriptionFld.setText(toolTM.getDescription());
                try {
                    boolean isExist = toolService.checkOrderStatus(new ToolDTO(toolTM.getTID(),null,null,null,null,null));
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
                    toolIdFld.setDisable(true);
                } catch (Exception e) {
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

    @SneakyThrows
    private void printToolReport(ToolTM toolTM) {
        HashMap<String,Object> params = new HashMap<>();
        try {
            InsuranceDTO insuranceDTO = toolService.getInsurance(new ToolDTO(toolTM.getTID(),null,null,null,null,null));
            params.put("TID",toolTM.getTID());
            params.put("codeNumber",toolTM.getTID());
            params.put("brandName",toolTM.getBrandName());
            params.put("name",toolTM.getName());
            params.put("availability",toolTM.getAvailability());
            params.put("rate",toolTM.getRate());
            params.put("description",toolTM.getDescription());
            params.put("IID", insuranceDTO.getIID());
            params.put("insuranceName", insuranceDTO.getName());
            params.put("iPName", insuranceDTO.getInsuranceProvider());
            params.put("agentName", insuranceDTO.getAgentName());
            params.put("agentContact", insuranceDTO.getAgentContact());
            params.put("fax", insuranceDTO.getFax());
            params.put("address", insuranceDTO.getAddress());
            params.put("email", insuranceDTO.getEmail());
            params.put("expireDate", Date.valueOf(insuranceDTO.getExpireDate()));
            params.put("joinedDate",Date.valueOf(insuranceDTO.getJoinedDate()));
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(toolTM));

            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/reports/tool_Report.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, params,dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (SQLException | JRException e) {
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
                    if(toolService.deleteTool(new ToolDTO(toolIdFld.getText(),null,null,null,null,null))){
                        TopUpNotifications.success("Tool Data Deleted Successfully !");
                        clearFields();
                        loadAllTools();
                        toolService.generateID();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Tool Data Not Deleted !").show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,"Tool Can't Be Delete At the Moment!, Error Code: "+e.getMessage()).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void descriptionValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(toolService.validateToolDescription(drecriptionFld.getText())){
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
            ToolDTO toolDTO = toolService.getTool(new ToolDTO(toolIdFld.getText(), null, null, null, null, null));
            if(toolDTO != null){
                nameFld.setText(toolDTO.getName());
                drecriptionFld.setText(toolDTO.getDescription());
                rentalRateFld.setText(toolDTO.getRate().toString());
                brandNameFld.setText(toolDTO.getBrand());
                saveBtn.setDisable(false);
                deleteBtn.setDisable(false);
                boolean isExist = toolService.checkOrderStatus(new ToolDTO(toolIdFld.getText(), null, null, null, null, null));
                if(isExist){
                    orderStatusLabel.setStyle("-fx-text-fill: red");
                    orderStatusLabel.setText("Tool Is In An Order ");
                    if (toolDTO.getAvalability().equals("Available")) {
                        availableRadiBtn.setSelected(true);
                    } else {
                        nAvailableRadioBtn.setSelected(true);
                    }
                    availableRadiBtn.setDisable(true);
                    nAvailableRadioBtn.setDisable(true);
                }else {
                    if (toolDTO.getAvalability().equals("Available")) {
                        availableRadiBtn.setSelected(true);
                    } else {
                        nAvailableRadioBtn.setSelected(true);
                    }
                    toolIdFld.setDisable(true);
                }
                toolIdFld.setDisable(true);
            }else {
                new Alert(Alert.AlertType.ERROR,"No Tool Found !").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @FXML
    void idGenerateOnAction(ActionEvent event) {
        toolIdFld.setText(toolService.generateID());
    }

    

    @FXML
   public void refreshOnClick(MouseEvent event) {
        if(
                toolService.validateToolId(toolIdFld.getText()) &&
                        toolService.validateToolName(nameFld.getText()) &&
                        toolService.validateToolDescription(drecriptionFld.getText()) &&
                        toolService.validateToolPrice(rentalRateFld.getText()) &&
                        toolService.validateToolBrand(brandNameFld.getText()
        )
                
        ){
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

    @SneakyThrows
    @FXML
    void saveBtnOnAction(ActionEvent event) {
        ToolDTO toolDTO = new ToolDTO();
        try {
            if(toolService.verify(new ToolDTO(toolIdFld.getText(),null,null,null,null,null))){
                new Alert(Alert.AlertType.CONFIRMATION,"New Tool Data Will Be Updated !", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            toolDTO.setTID(toolIdFld.getText());
                            toolDTO.setName(nameFld.getText());
                            toolDTO.setBrand(brandNameFld.getText());
                            toolDTO.setRate(Double.valueOf(rentalRateFld.getText()));
                            toolDTO.setDescription(drecriptionFld.getText());
                            if(availableRadiBtn.isSelected()){
                                toolDTO.setAvalability("Available");
                            }else {
                                toolDTO.setAvalability("Not Available");
                            }
                            boolean isExist = toolService.checkOrderStatus(new ToolDTO(toolIdFld.getText(),null,null,null,null,null));
                            if(isExist){
                                new Alert(Alert.AlertType.ERROR,"Tool Is In On An Order, Manual Availability Adjust Will Not Be Effect !").showAndWait();
                                if(toolService.updateToolWithoutAvailability(toolDTO)){
                                    TopUpNotifications.success("Tool Data Updated !");
                                    clearFields();
                                    loadAllTools();
                                    toolService.generateID();
                                }else {
                                    new Alert(Alert.AlertType.ERROR,"Tool Data Not Updated !").show();
                                }
                            }else {
                                if(toolService.updateTool(toolDTO)){
                                    TopUpNotifications.success("Tool Data Updated !");
                                    clearFields();
                                    loadAllTools();
                                    toolService.generateID();
                                }else {
                                    new Alert(Alert.AlertType.ERROR,"Tool Data Not Updated !").show();
                                }
                            }

                        } catch (Exception e) {
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                            e.printStackTrace();
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"New Tool Data Will Be Saved !", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.YES){
                        try {
                            toolDTO.setTID(toolIdFld.getText());
                            toolDTO.setName(nameFld.getText());
                            toolDTO.setBrand(brandNameFld.getText());
                            toolDTO.setDescription(drecriptionFld.getText());
                            toolDTO.setRate(Double.valueOf(rentalRateFld.getText()));
                            if(availableRadiBtn.isSelected()){
                                toolDTO.setAvalability("Available");
                            }else {
                                toolDTO.setAvalability("Not Available");
                            }
                            if(toolService.addTool(toolDTO)){
                                TopUpNotifications.success("Tool Data Saved !");
                                loadAllTools();
                                clearFields();
                                toolService.addTool(toolDTO);
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Tool Data Not Saved !").show();
                            }
                        } catch (Exception e) {
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
        toolIdFld.setDisable(false);
        nAvailableRadioBtn.setDisable(false);
        availableRadiBtn.setDisable(false);
        orderStatusLabel.setText(null);
    }

    @FXML
    void toolIdValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if(toolService.validateToolId(toolIdFld.getText())){
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
        if(toolService.validateToolName(nameFld.getText())){
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
        if(toolService.validateToolBrand(brandNameFld.getText())){
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
        if(toolService.validateToolPrice(rentalRateFld.getText())) {
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
                ArrayList<ToolTM> filterTools = toolService.searchTool("%"+searchFld.getText()+"%");
                ObservableList<ToolTM> filterToolsTMS = FXCollections.observableArrayList(filterTools);
                toolsTable.setItems(filterToolsTMS);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }
}
