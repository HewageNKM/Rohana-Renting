/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/16/23, 10:03 AM
 *
 */

package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.hnkm.rohanarenting.db.DBConnection;
import lk.hnkm.rohanarenting.dto.tm.RefundOrderTM;
import lk.hnkm.rohanarenting.dto.tm.RefundTM;
import lk.hnkm.rohanarenting.model.RefundModel;
import lk.hnkm.rohanarenting.utill.Genarate;
import lk.hnkm.rohanarenting.utill.Regex;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RefundController {
    public TableColumn columnProductId;
    public TableColumn columnRentDays;
    public TableColumn columnTotal;
    public TableColumn columnPurchaseDate;
    public TableColumn refundColumnProductId;
    public TableColumn refundColumnRefundAmount;
    public TableColumn refundColumnTotal;
    public TableColumn refundColumnRentDays;
    public JFXButton changeBtn;
    public TextField rentIdFld;
    public Label refundIdLabel;
    public JFXButton processedBtn;
    public TableView<RefundOrderTM> refundOrderTable;
    public TableView<RefundTM> refundTable;
    public Label totalRefundLabel;
    private ObservableList<RefundOrderTM> refundOrderTMS = FXCollections.observableArrayList();
    private ObservableList<RefundTM> refundTMS = FXCollections.observableArrayList();

    public void initialize() {
        setRefundOrderTableCellValueFactory();
        setRefundCellValueFactory();
        setRefundId();
        processedBtn.setDisable(true);
        changeBtn.setDisable(true);
    }

    private void setRefundId(){
        String id = Genarate.genarateRefundId();
        while (true) {
            try {
                if (RefundModel.isRefundIdExist(id)) {
                    id = Genarate.genarateRefundId();
                } else {
                    refundIdLabel.setText(id.toUpperCase());
                    break;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }

    private void setRefundCellValueFactory() {
        refundColumnProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        refundColumnRentDays.setCellValueFactory(new PropertyValueFactory<>("rentDays"));
        refundColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        refundColumnRefundAmount.setCellValueFactory(new PropertyValueFactory<>("refundAmount"));
    }

    private void setRefundOrderTableCellValueFactory() {
        columnProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        columnRentDays.setCellValueFactory(new PropertyValueFactory<>("rentDays"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        columnPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
    }

    public void refreshOnClick(MouseEvent mouseEvent) {

    }

    public void clearBtnOnAction(ActionEvent actionEvent) {

    }

    public void proceedBtnOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.CONFIRMATION,"Amount Will Be Refund !",ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(ButtonType->{
            if(ButtonType == ButtonType.OK){
                Connection connection=null;
                if(Regex.validateToolRentId(rentIdFld.getText())){
                    try {
                        connection = DBConnection.getInstance().getConnection();
                        connection.setAutoCommit(false);
                        Boolean  isRefundTableUpdated= RefundModel.updateToolRefundTable(refundIdLabel.getText(),rentIdFld.getText());
                        if(isRefundTableUpdated){
                            Boolean isRefundDetailTableUpdated = RefundModel.updateToolRefundDetailTable(refundTMS,refundIdLabel.getText());
                            if(isRefundDetailTableUpdated){
                                Boolean isToolRentTableUpdated = RefundModel.updateToolRentTable(refundTMS);
                                if(isToolRentTableUpdated){
                                    Boolean isToolTableUpdated = RefundModel.updateToolTable(refundTMS);
                                    if(isToolTableUpdated){
                                        connection.commit();
                                        new Alert(Alert.AlertType.INFORMATION,"Refund Request Processed !").show();
                                        clearFields();
                                    }else {
                                        connection.rollback();
                                        new Alert(Alert.AlertType.ERROR,"Refund Request Fail !").show();
                                    }
                                }else {
                                    connection.rollback();
                                    new Alert(Alert.AlertType.ERROR,"Refund Request Fail !").show();
                                }
                            }else {
                                connection.rollback();
                                new Alert(Alert.AlertType.ERROR,"Refund Request Fail !").show();
                            }
                        }else {
                            connection.rollback();
                            new Alert(Alert.AlertType.ERROR,"Refund Request Fail !").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        e.printStackTrace();
                    }
                }else if (Regex.validateVehicleRentId(rentIdFld.getText())){
                    try {
                        connection.setAutoCommit(false);
                        Boolean  isRefundTableUpdated= RefundModel.updateVehicleRefundTable(refundIdLabel.getText(),rentIdFld.getText());
                        if(isRefundTableUpdated){
                            Boolean isRefundDetailTableUpdated = RefundModel.updateVehicleRefundDetailTable(refundTMS,refundIdLabel.getText());
                            if(isRefundDetailTableUpdated){
                                Boolean isEquipRentTableUpdated = RefundModel.updateVehicleRentTable(refundTMS);
                                if(isEquipRentTableUpdated){
                                    Boolean isEquipTableUpdated = RefundModel.updateVehicleTable(refundTMS);
                                    if(isEquipTableUpdated){
                                        connection.commit();
                                        new Alert(Alert.AlertType.INFORMATION,"Refund Request Processed !").show();
                                        clearFields();
                                    }else {
                                        connection.rollback();
                                        new Alert(Alert.AlertType.ERROR,"Refund Request Fail !").show();
                                    }
                                }else {
                                    connection.rollback();
                                    new Alert(Alert.AlertType.ERROR,"Refund Request Fail !").show();
                                }
                            }else {
                                connection.rollback();
                                new Alert(Alert.AlertType.ERROR,"Refund Request Fail !").show();
                            }
                        }else {
                            connection.rollback();
                            new Alert(Alert.AlertType.ERROR,"Refund Request Fail !").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void clearFields() {
        rentIdFld.clear();
        refundOrderTMS.clear();
        refundTMS.clear();
        refundTable.setItems(refundTMS);
        refundOrderTable.setItems(refundOrderTMS);
        setRefundId();
        rentIdFld.setStyle(null);
        totalRefundLabel.setText("0.0");
        processedBtn.setDisable(true);
        changeBtn.setDisable(true);
    }

    private void loadRefundOrderTable() {
        try {
            ArrayList<RefundOrderTM> refundOrder = RefundModel.getRefundOrderTM(rentIdFld.getText());
            if(refundOrder.size()<=0){
                new Alert(Alert.AlertType.ERROR,"No Items To Refund!").show();
                clearFields();
            }else {
                System.out.println(refundOrder.size());
                refundOrderTMS.addAll(refundOrder);
                refundOrderTable.setItems(refundOrderTMS);
                changeBtn.setDisable(false);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void enterOnAction(ActionEvent actionEvent) {
        if(Regex.validateToolRentId(rentIdFld.getText())){
            try {
                Boolean status =  RefundModel.verifyToolRentId(rentIdFld.getText());
                if(RefundModel.verifyToolRentId(rentIdFld.getText())!=null){
                    if(Boolean.TRUE.equals(status)){
                        if(RefundModel.checkToolReturn(rentIdFld.getText())){
                            new Alert(Alert.AlertType.WARNING,"Can't Issue Refund ! AR").show();
                        }else {
                            loadRefundOrderTable();;
                            rentIdFld.setDisable(true);
                        }
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Refund Issue Only Withing 24 Hours").show();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"No Details Found !").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }else if(Regex.validateVehicleRentId(rentIdFld.getText())){
            try {
              Boolean status =  RefundModel.verifyVehicleRentId(rentIdFld.getText());
                if(RefundModel.verifyVehicleRentId(rentIdFld.getText())!=null){
                    if(Boolean.TRUE.equals(status)){
                        if(RefundModel.checkVehicleReturn(rentIdFld.getText())){
                            new Alert(Alert.AlertType.WARNING,"Can't Issue Refund ! AOR").show();
                        }else {
                            loadRefundOrderTable();
                            rentIdFld.setDisable(true);
                        }

                    }else {
                        new Alert(Alert.AlertType.WARNING,"Refund Issue Only Withing 24 Hours").show();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"No Details Found !").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid Rent ID !").show();
        }
    }

    public void validateRentId(KeyEvent keyEvent) {
        if (Regex.validateToolRentId(rentIdFld.getText())||Regex.validateVehicleRentId(rentIdFld.getText())) {
            rentIdFld.setStyle("-fx-border-color: green");
        } else {
            rentIdFld.setStyle("-fx-border-color: red");
        }
    }

    public void changeBtnOnAction(ActionEvent actionEvent) {
        RefundOrderTM refundOrderTM = refundOrderTable.getSelectionModel().getSelectedItem();
        if(refundOrderTM!=null) {
            try {
                RefundTM refundTM=RefundModel.getRefundTM(refundOrderTM);
                refundTMS.add(refundTM);
                refundTable.setItems(refundTMS);
                Double total = RefundModel.getTotal(refundTMS);
                totalRefundLabel.setStyle("-fx-text-fill: red");
                totalRefundLabel.setText(String.valueOf(total));
                refundOrderTMS.remove(refundOrderTM);
                processedBtn.setDisable(false);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Please Select Row !").show();
        }
    }
}
