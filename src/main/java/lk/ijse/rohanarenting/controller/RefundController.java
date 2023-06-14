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
import lk.ijse.rohanarenting.dto.CustomerDTO;
import lk.ijse.rohanarenting.dto.tm.RefundOrderTM;
import lk.ijse.rohanarenting.dto.tm.RefundTM;
import lk.ijse.rohanarenting.entity.Refund;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.RefundServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.RefundService;
import lk.ijse.rohanarenting.utill.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private final ObservableList<RefundOrderTM> refundOrderTMS = FXCollections.observableArrayList();
    private final ObservableList<RefundTM> refundTMS = FXCollections.observableArrayList();
    private final Stage stage = new Stage();
    private final RefundService refundService = (RefundServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.REFUND_SERVICE);

    public void initialize() {
        setRefundOrderTableCellValueFactory();
        setRefundCellValueFactory();
        setRefundId();
        processedBtn.setDisable(true);
        changeBtn.setDisable(true);
    }

    private void setRefundId(){
        try {
            refundIdLabel.setText(refundService.genarateRefundId());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
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
            try {
                if(refundService.placeRefund(new Refund(refundIdLabel.getText(),rentIdFld.getText(), LocalDate.now(), LocalTime.now()),refundTMS)){
                    printRefundInvoice();
                    new Alert(Alert.AlertType.INFORMATION,"Refund Issued Success!").show();
                    clearFields();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Refund Issued  Not Success!").show();
                }
            } catch (SQLException e) {
                new  Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        });
    }

    private void printRefundInvoice() {
        try {
            HashMap<String,Object> params = new HashMap<>();
            CustomerDTO customerDTO = refundService.getCustomer(rentIdFld.getText());
            params.put("refundId",refundIdLabel.getText());
            params.put("name", customerDTO.getFirstName()+" "+ customerDTO.getLastName());
            params.put("street", customerDTO.getStreet());
            params.put("zip", customerDTO.getZipCode());
            params.put("city", customerDTO.getCity());
            params.put("mobile", customerDTO.getMobileNumber());
            params.put("email", customerDTO.getEmail());
            params.put("subTotal",refundService.getTotal(refundTMS));
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
            ArrayList<RefundOrderTM> refundOrder = refundService.getRefundOrderTM(rentIdFld.getText());
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
                Boolean status =  refundService.verifyToolRentId(rentIdFld.getText());
                if(refundService.verifyToolRentId(rentIdFld.getText())!=null){
                    if(Boolean.TRUE.equals(status)){
                        if(refundService.checkToolReturn(rentIdFld.getText())){
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
              Boolean status =  refundService.verifyVehicleRentId(rentIdFld.getText());
                if(refundService.verifyVehicleRentId(rentIdFld.getText())!=null){
                    if(Boolean.TRUE.equals(status)){
                        if(refundService.checkVehicleReturn(rentIdFld.getText())){
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
        if (refundService.validateToolRentId(rentIdFld.getText())|| refundService.validateVehicleRentId(rentIdFld.getText())) {
            rentIdFld.setStyle("-fx-border-color: green");
        } else {
            rentIdFld.setStyle("-fx-border-color: red");
        }
    }

    public void changeBtnOnAction(ActionEvent actionEvent) {
        RefundOrderTM refundOrderTM = refundOrderTable.getSelectionModel().getSelectedItem();
        if(refundOrderTM!=null) {
                RefundTM refundTM=refundService.getRefundTM(refundOrderTM);
                refundTMS.add(refundTM);
                refundTable.setItems(refundTMS);
                Double total = refundService.getTotal(refundTMS);
                totalRefundLabel.setStyle("-fx-text-fill: red");
                totalRefundLabel.setText("Total : "+String.valueOf(total));
                refundOrderTMS.remove(refundOrderTM);
                processedBtn.setDisable(false);
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
