/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/16/23, 10:03 AM
 *
 */

package lk.ijse.rohanarenting.controller;

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
import javafx.stage.Stage;
import lk.ijse.rohanarenting.db.DBConnection;
import lk.ijse.rohanarenting.dto.CustomerDTO;
import lk.ijse.rohanarenting.dto.tm.RefundOrderTM;
import lk.ijse.rohanarenting.dto.tm.RefundTM;
import lk.ijse.rohanarenting.model.RefundModel;
import lk.ijse.rohanarenting.utill.notification.TopUpNotifications;
import lk.ijse.rohanarenting.utill.Genarate;
import lk.ijse.rohanarenting.utill.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private Stage stage = new Stage();

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

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void proceedBtnOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.CONFIRMATION,"Amount Will Be Refund !",ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(ButtonType->{
            if(ButtonType == ButtonType.OK){
                Connection connection = null;
                try {
                    connection = DBConnection.getInstance().getConnection();
                    connection.setAutoCommit(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(Regex.validateToolRentId(rentIdFld.getText())){
                    try {

                        Boolean  isRefundTableUpdated= RefundModel.updateToolRefundTable(refundIdLabel.getText(),rentIdFld.getText());
                        if(isRefundTableUpdated){
                            Boolean isRefundDetailTableUpdated = RefundModel.updateToolRefundDetailTable(refundTMS,refundIdLabel.getText());
                            if(isRefundDetailTableUpdated){
                                Boolean isToolRentTableUpdated = RefundModel.updateToolRentTable(refundTMS);
                                if(isToolRentTableUpdated){
                                    Boolean isToolTableUpdated = RefundModel.updateToolTable(refundTMS);
                                    if(isToolTableUpdated){
                                        connection.commit();
                                        printRefundInvoice();
                                        TopUpNotifications.success("Refund Request Success !");
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
                                        printRefundInvoice();
                                        TopUpNotifications.success("Refund Request Success !");
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

    private void printRefundInvoice() {
        try {
            HashMap<String,Object> params = new HashMap<>();
            CustomerDTO customerDTO = RefundModel.getCustomer(rentIdFld.getText());
            params.put("refundId",refundIdLabel.getText());
            params.put("name", customerDTO.getFirstName()+" "+ customerDTO.getLastName());
            params.put("street", customerDTO.getStreet());
            params.put("zip", customerDTO.getZipCode());
            params.put("city", customerDTO.getCity());
            params.put("mobile", customerDTO.getMobileNumber());
            params.put("email", customerDTO.getEmail());
            params.put("subTotal",RefundModel.getTotal(refundTMS));
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(refundTMS);
            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/reports/refund_Invoice.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, params, dataSource);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void clearFields() {
        rentIdFld.clear();
        refundOrderTMS.clear();
        refundTMS.clear();
        refundTable.setItems(refundTMS);
        refundOrderTable.setItems(refundOrderTMS);
        setRefundId();
        rentIdFld.setStyle(null);
        totalRefundLabel.setText("Total : 0.0");
        processedBtn.setDisable(true);
        changeBtn.setDisable(true);
        rentIdFld.setDisable(false);
        rentIdFld.setDisable(false);
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
                totalRefundLabel.setText("Total : "+String.valueOf(total));
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

    public void orderListBtnOnAction(ActionEvent actionEvent) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderViewForm.fxml"))));
        stage.setTitle("Order View");
        stage.centerOnScreen();
        stage.getIcons().add(new Image("/img/search.png"));
        stage.setResizable(false);
        stage.show();
    }
}
