/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/2/23, 7:37 AM
 *
 */

package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.hnkm.rohanarenting.db.DBConnection;
import lk.hnkm.rohanarenting.dto.tm.ReturnOrderTM;
import lk.hnkm.rohanarenting.dto.tm.ReturnTM;
import lk.hnkm.rohanarenting.model.ReturnModel;
import lk.hnkm.rohanarenting.utill.Genarate;
import lk.hnkm.rohanarenting.utill.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnFormController {
    public TableView<ReturnOrderTM> orderTable;
    public TableColumn columnID;
    public TableColumn columnProductId;
    public TableColumn columnReturnDate;
    public TableColumn returnColumnProductId;
    public TableColumn returnColumnReturnDate;
    public TableColumn returnColumnLateDays;
    public TableColumn returnColumnFine;
    public JFXButton changeBtn;
    public Label returnIdLabel;
    public Label fineLabel;
    public TextField idFld;
    public TableColumn returnColumnReturnedDate;
    public JFXButton processedBtn;
    public TableView returnOrderTable;
    Stage stage = new Stage();
    ObservableList<ReturnOrderTM> orderList = FXCollections.observableArrayList();
    ObservableList<ReturnTM> returnList = FXCollections.observableArrayList();

    public void initialize() {
        changeBtn.setDisable(true);
        processedBtn.setDisable(true);
        setOrderCellValueFactory();
        setReturnCellValueFactory();
        setReturnId();
    }

    private void setReturnId() {
        String id = Genarate.genarateReturnId();
        while (true) {
            try {
                if (ReturnModel.isReturnIdExist(id)) {
                    id = Genarate.genarateReturnId();
                } else {
                    returnIdLabel.setText(id.toUpperCase());
                    break;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                throw new RuntimeException(e);
            }
        }
    }

    private void setReturnCellValueFactory() {
        returnColumnProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        returnColumnReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        returnColumnReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        returnColumnLateDays.setCellValueFactory(new PropertyValueFactory<>("lateDays"));
        returnColumnFine.setCellValueFactory(new PropertyValueFactory<>("fine"));
    }

    private void loadReturnOrderTable() {
        try {
            ArrayList<ReturnOrderTM> arrayList = ReturnModel.getOrderTM(idFld.getText());
            if(0 >= arrayList.size()){
                new Alert(Alert.AlertType.WARNING,"No Item To Return !").show();
            }else {
                orderList.clear();
                orderList.addAll(arrayList);
                orderTable.setItems(orderList);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private void setOrderCellValueFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("rentId"));
        columnProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        columnReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }
    public void refreshOnClick(MouseEvent mouseEvent) {

    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        orderList.clear();
        returnList.clear();
        idFld.setStyle(null);
        fineLabel.setText("0.0");
        idFld.clear();
    }

    public void changeBtnOnAction(ActionEvent actionEvent) {
       ReturnOrderTM returnOrderTM = orderTable.getSelectionModel().getSelectedItem();
        if(returnOrderTM !=null){
            ReturnTM returnTM;
            try {
                returnTM = ReturnModel.getReturnTM(returnOrderTM,returnIdLabel.getText());
                System.out.print(returnTM);
                returnList.add(returnTM);
                orderList.remove(returnOrderTM);
                returnOrderTable.setItems(returnList);
                processedBtn.setDisable(false);
                Double totalFine = ReturnModel.getTotalFine(returnList);
                fineLabel.setStyle("-fx-text-fill: red");
                fineLabel.setText("Total Fine: "+String.valueOf(totalFine));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Please select a row").show();
        }
    }

    public void idValidate(KeyEvent keyEvent) {
        try {
            if(ReturnModel.verifyVehicleRentId(idFld.getText())||Regex.validateVehicleRentId(idFld.getText())) {
                idFld.setStyle("-fx-border-color: green");
                changeBtn.setDisable(false);
                processedBtn.setDisable(false);
            }else if(ReturnModel.verifyToolRentID(idFld.getText())||Regex.validateToolRentId(idFld.getText())){
                idFld.setStyle("-fx-border-color: green");
                changeBtn.setDisable(false);
                processedBtn.setDisable(false);
            }else{
                idFld.setStyle("-fx-border-color: red");
                changeBtn.setDisable(true);
                processedBtn.setDisable(true);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    public void procceedBtnOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure ?,This Will Take Immediate Effect !",ButtonType.NEXT,ButtonType.CANCEL).showAndWait().ifPresent(ButtonType->{
            if(ButtonType == ButtonType.NEXT){
                Connection connection=null;
                if(Regex.validateToolRentId(idFld.getText())){
                    try {
                        connection = DBConnection.getInstance().getConnection();
                        connection.setAutoCommit(false);
                        Boolean isReturnSaved =  ReturnModel.saveToolReturn(returnIdLabel.getText(),idFld.getText());
                        if(isReturnSaved){
                        Boolean isReturnDetailsSaved = ReturnModel.saveToolReturnDetails(returnList);
                            if(isReturnDetailsSaved){
                                Boolean isReturnUpdated = ReturnModel.updateToolRent(returnList,idFld.getText());
                                if(isReturnUpdated){
                                    Boolean isToolUpdated = ReturnModel.updateTool(returnList);
                                    if(isToolUpdated){
                                        connection.commit();
                                        new Alert(Alert.AlertType.INFORMATION,"Return Saved !").show();
                                        clearFields();
                                    }else {
                                        connection.rollback();
                                        new Alert(Alert.AlertType.ERROR,"Return Not Saved !").show();
                                    }
                                }else {
                                    new Alert(Alert.AlertType.ERROR,"Return Not Saved !").show();
                                    connection.rollback();
                                }
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Return Not Saved !").show();
                                connection.rollback();
                            }
                        }else {
                            new Alert(Alert.AlertType.ERROR,"Return Not Saved !").show();
                            connection.rollback();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        e.printStackTrace();
                    }finally {
                        try {
                            connection.setAutoCommit(true);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    try {
                        connection = DBConnection.getInstance().getConnection();
                        connection.setAutoCommit(false);
                        Boolean  isVehicleReturnSaved =  ReturnModel.saveVehicleReturn(returnIdLabel.getText(),idFld.getText());
                        if(isVehicleReturnSaved){
                            Boolean isVehicleReturnDetailsSaved = ReturnModel.saveVehicleReturnDetails(returnList);
                            if(isVehicleReturnDetailsSaved){
                                Boolean isVehicleRentUpdated = ReturnModel.updateVehicleRent(returnList,idFld.getText());
                                if(isVehicleRentUpdated){
                                    Boolean isVehicleUpdated = ReturnModel.updateVehicle(returnList);
                                    if(isVehicleUpdated){
                                        connection.commit();
                                        new Alert(Alert.AlertType.INFORMATION,"Return Saved !").show();
                                        clearFields();
                                    }else {
                                        connection.rollback();
                                        new Alert(Alert.AlertType.ERROR,"Return Not Saved !").show();
                                    }
                                    connection.commit();
                                    new Alert(Alert.AlertType.INFORMATION,"Return Saved !").show();
                                    clearFields();
                                }else {
                                    new Alert(Alert.AlertType.ERROR,"Return Not Saved !").show();
                                    connection.rollback();
                                }
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Return Not Saved !").show();
                                connection.rollback();
                            }
                        }else {
                            new Alert(Alert.AlertType.ERROR,"Return Not Saved !").show();
                            connection.rollback();
                        }

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        e.printStackTrace();
                    }finally {
                        try {
                            connection.setAutoCommit(true);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void enterOnAction(ActionEvent actionEvent) {
        try {
            if(ReturnModel.verifyVehicleRentId(idFld.getText())) {
                if(ReturnModel.checkRefund(idFld.getText())){
                    new Alert(Alert.AlertType.ERROR, "This Order Has Already Been Refunded !").show();
                }else {
                    loadReturnOrderTable();
                    idFld.setDisable(true);
                }
            }else if(ReturnModel.verifyToolRentID(idFld.getText())){
                if(ReturnModel.checkRefund(idFld.getText())){
                    new Alert(Alert.AlertType.ERROR, "This Order Has Already Been Refunded !").show();
                }else {
                    loadReturnOrderTable();
                    idFld.setDisable(true);
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "No Details Found For That ID !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    public void orderListViewBtnOnAction(ActionEvent actionEvent) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderViewForm.fxml"))));
        stage.setTitle("Order View");
        stage.centerOnScreen();
        stage.getIcons().add(new Image("/img/search.png"));
        stage.show();
    }
}
