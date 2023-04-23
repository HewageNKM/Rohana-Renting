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
import lk.hnkm.rohanarenting.dto.Tool;
import lk.hnkm.rohanarenting.dto.tm.ToolTM;
import lk.hnkm.rohanarenting.model.EmployeeModel;
import lk.hnkm.rohanarenting.model.ToolModel;
import lk.hnkm.rohanarenting.utill.Genarate;
import lk.hnkm.rohanarenting.utill.Regex;

import java.sql.SQLException;
import java.util.ArrayList;

public class ToolFormController {

    public TableColumn columnAvailability;
    public TableColumn columnEdit;
    public TableColumn columnDelete;
    public TableColumn columnRentalRate;
    public TableColumn columnBrandName;
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
            ArrayList<ToolTM> arrayList  =ToolModel.getTools();
            toolsList.clear();
            toolsList.addAll(arrayList);
            toolsTable.setItems(toolsList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
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
    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
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
                    new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
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
                if (tool.getAvalability() == 1) {
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
                                tool.setAvalability(1);
                            }else {
                                tool.setAvalability(0);
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
                                tool.setAvalability(1);
                            }else {
                                tool.setAvalability(0);
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
}
