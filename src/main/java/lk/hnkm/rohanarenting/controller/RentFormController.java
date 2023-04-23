package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lk.hnkm.rohanarenting.db.DBConnection;
import lk.hnkm.rohanarenting.dto.Customer;
import lk.hnkm.rohanarenting.dto.ToolOrder;
import lk.hnkm.rohanarenting.dto.VehicleJesperReport;
import lk.hnkm.rohanarenting.dto.VehicleOrder;
import lk.hnkm.rohanarenting.dto.tm.ToolCartTM;
import lk.hnkm.rohanarenting.dto.tm.VehicleCartTM;
import lk.hnkm.rohanarenting.model.RentModel;
import lk.hnkm.rohanarenting.utill.Genarate;
import lk.hnkm.rohanarenting.utill.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RentFormController {
    public TextField vehicleRentDaysFld;
    public TextField vehicleFld;
    public TextField vehicleCustomerFld;
    public TextField toolRentDaysFld;
    public TableColumn<Object, Object> toolNameColumn;
    public TableColumn vehicleModelNameColumn;
    public TableColumn vehicleManufactureColumn;
    public TableColumn toolBrandName;
    @FXML
    private JFXButton vehicleAddCartBtn;

    @FXML
    private TableView<VehicleCartTM> vehicleRentTable;

    @FXML
    private TableColumn<?, ?> vehicleRentIdColumn;

    @FXML
    private TableColumn<?, ?> vehicleCustomerIdColumn;

    @FXML
    private TableColumn<?, ?> vehicleIdColumn;

    @FXML
    private TableColumn<?, ?> vehicleDescriptionColumn;

    @FXML
    private TableColumn<?, ?> vehicleCategoryColumn;

    @FXML
    private TableColumn<?, ?> vehicleRentalRateColumn;

    @FXML
    private TableColumn<?, ?> vehicleRentDatsColumn;

    @FXML
    private TableColumn<?, ?> vehicleRemoveColumn;

    @FXML
    private TableColumn<?, ?> vehicleColumRemove;

    @FXML
    private Label vehicleSubTotalLabel;

    @FXML
    private JFXButton vehiclePlaceOrderBtn;

    @FXML
    private Label vehicleRentOrderLabel;

    @FXML
    private JFXButton toolAddCartBtn;

    @FXML
    private TableView<ToolCartTM> toolRentTable;

    @FXML
    private TableColumn<?, ?> toolColumnRid;

    @FXML
    private TableColumn<?, ?> toolCustomerIDColumn;

    @FXML
    private TableColumn<?, ?> toolIdColumn;

    @FXML
    private TableColumn<?, ?> toolDescriptionColum;

    @FXML
    private TableColumn<?, ?> toolRentalRateColum;

    @FXML
    private TableColumn<?, ?> toolRentDaysColum;

    @FXML
    private TableColumn<?, ?> toolTotalColum;

    @FXML
    private TableColumn<?, ?> toolRemoveColumn;

    @FXML
    private Label toolSubTotalLabel;

    @FXML
    private JFXButton toolPlaceOrderBtn;

    @FXML
    private TextField toolCustomerFld;

    @FXML
    private Label toolRentOrderIdLabel;

    @FXML
    private TextField toolFld;


   private ObservableList<VehicleCartTM> vehicleCartTMS = FXCollections.observableArrayList();
   private ObservableList<ToolCartTM> toolCartTMS = FXCollections.observableArrayList();

    public void initialize(){
        vehicleAddCartBtn.setDisable(true);
        toolAddCartBtn.setDisable(true);
        vehiclePlaceOrderBtn.setDisable(true);
        toolPlaceOrderBtn.setDisable(true);
        setToolRID();
        setVehicleRID();
        setVehicleCellValues();
        setToolCellValues();
    }


    private void setToolCellValues() {
        toolColumnRid.setCellValueFactory( new PropertyValueFactory<>("rentOrderID"));
        toolCustomerIDColumn.setCellValueFactory( new PropertyValueFactory<>("customerID"));
        toolIdColumn.setCellValueFactory( new PropertyValueFactory<>("toolID"));
        toolNameColumn.setCellValueFactory(new PropertyValueFactory<>("toolName"));
        toolDescriptionColum.setCellValueFactory( new PropertyValueFactory<>("description"));
        toolRentalRateColum.setCellValueFactory( new PropertyValueFactory<>("rate"));
        toolRentDaysColum.setCellValueFactory( new PropertyValueFactory<>("rentDays"));
        toolTotalColum.setCellValueFactory( new PropertyValueFactory<>("total"));
        toolRemoveColumn.setCellValueFactory( new PropertyValueFactory<>("remove"));
        toolBrandName.setCellValueFactory( new PropertyValueFactory<>("brandName"));
    }

    private void setVehicleCellValues() {
        vehicleRentIdColumn.setCellValueFactory( new PropertyValueFactory<>("rentOrderID"));
        vehicleCustomerIdColumn.setCellValueFactory( new PropertyValueFactory<>("customerID"));
        vehicleIdColumn.setCellValueFactory( new PropertyValueFactory<>("vehicleID"));
        vehicleManufactureColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleManufacture"));
        vehicleModelNameColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleModelName"));
        vehicleDescriptionColumn.setCellValueFactory( new PropertyValueFactory<>("description"));
        vehicleCategoryColumn.setCellValueFactory( new PropertyValueFactory<>("category"));
        vehicleRentalRateColumn.setCellValueFactory( new PropertyValueFactory<>("rate"));
        vehicleRentDatsColumn.setCellValueFactory( new PropertyValueFactory<>("rentDays"));
        vehicleRemoveColumn.setCellValueFactory( new PropertyValueFactory<>("total"));
        vehicleColumRemove.setCellValueFactory( new PropertyValueFactory<>("remove"));
    }

    private void setVehicleRID() {
        String rentId = Genarate.genarateRentId(-1);
        try {
            while(RentModel.verifyVehicleRentId(rentId)){
                rentId=Genarate.genarateRentId(-1);
            }
            vehicleRentOrderLabel.setText(rentId);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setToolRID() {
        String rentId = Genarate.genarateRentId(1);
        try {
            while(RentModel.verifyToolRentId(rentId)){
                rentId=Genarate.genarateRentId(1);
            }
            toolRentOrderIdLabel.setText(rentId);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void placeVehicleOrderOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"New Order Will Be Place !",ButtonType.NEXT,ButtonType.NO).showAndWait().ifPresent(ButtonType->{
            Connection connection = null;
            if(ButtonType == ButtonType.NEXT){
                try {
                    connection = DBConnection.getInstance().getConnection();
                    connection.setAutoCommit(false);
                    Boolean isOrderAdded= RentModel.updateVehicleRentOrderTable(new VehicleOrder(vehicleRentOrderLabel.getText(),vehicleCustomerFld.getText(),vehicleFld.getText(),Integer.getInteger(vehicleRentDaysFld.getText())));
                    if(isOrderAdded){
                        Boolean isDetailsAdded = RentModel.addVehicleRentOrderDetailTable(vehicleCartTMS);
                        if(isDetailsAdded){
                            Boolean isVehicleUpdated = RentModel.updateVehicleTable(vehicleCartTMS);
                            if(isVehicleUpdated){
                                connection.commit();
                                new Alert(Alert.AlertType.INFORMATION,"Order Place !").show();
                                printVehicleInvoice();
                                newVehicleOrder();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Order Not Place !").show();
                                connection.rollback();
                            }
                        }else {
                            new Alert(Alert.AlertType.ERROR,"Order Not Place !").show();
                            connection.rollback();
                        }
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Order Not Place !").show();
                        connection.rollback();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                    throw new RuntimeException(e);
                }finally {
                    try {
                        assert connection != null;
                        connection.setAutoCommit(true);
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        e.printStackTrace();
                    }
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Order Not Place !").show();
            }
        });
    }

    private void printVehicleInvoice() {
        Customer customer =RentModel.getCustomer(vehicleCustomerFld.getText());
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("rentId",vehicleRentOrderLabel.getText());
        params.put("subTotal",RentModel.getVehicleTotal(vehicleCartTMS));
        params.put("name",customer.getFistName()+" "+customer.getLastName());
        params.put("street",customer.getStreet());
        params.put("city",customer.getCity());
        params.put("zip",customer.getZipCode());
        params.put("mobileNumber",customer.getMobileNumber());
        params.put("email",customer.getEmail());
        params.put("barCodeNumber",vehicleRentOrderLabel.getText());
        ArrayList<VehicleJesperReport> vehicleJesperReports = RentModel.getVehicleJesperReport(vehicleCartTMS);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vehicleJesperReports);
        try {
            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/reports/vehicle_Invoice.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, params, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void newVehicleOrder() {
        vehicleCartTMS.clear();
        vehicleRentDaysFld.clear();
        vehicleCustomerFld.clear();
        vehicleFld.clear();
        vehicleRentDaysFld.clear();
        vehicleRentTable.getItems().clear();
        vehicleRentTable.refresh();
        vehicleSubTotalLabel.setText("0.00");
        vehicleFld.setStyle(null);
        vehicleRentDaysFld.setStyle(null);
        vehicleCustomerFld.setStyle(null);
        setVehicleRID();
    }
    private void newToolOrder() {
        toolCartTMS.clear();
        toolRentDaysFld.clear();
        toolCustomerFld.clear();
        toolFld.clear();
        toolRentDaysFld.clear();
        toolRentTable.getItems().clear();
        toolRentTable.refresh();
        toolSubTotalLabel.setText("0.00");
        toolFld.setStyle(null);
        toolRentDaysFld.setStyle(null);
        toolCustomerFld.setStyle(null);
        setToolRID();
    }


    @FXML
    void toolNewBtnOnAction(ActionEvent event) throws IOException {
        Stage stage =new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
        stage.centerOnScreen();
        stage.setMaximized(false);
        stage.setTitle("Customer Form");
        stage.show();
    }

    @FXML
    void vehicleCartBtnOnAction(ActionEvent event) {
        try {
            VehicleCartTM vehicleCartTM = RentModel.getVehicleCartModel(new VehicleOrder(vehicleRentOrderLabel.getText(),vehicleCustomerFld.getText(),vehicleFld.getText(),Integer.valueOf(vehicleRentDaysFld.getText())));
            if(vehicleCartTM!=null){
                vehicleCartTMS = vehicleRentTable.getItems();
                if(RentModel.checkVehicleCartDuplicate(vehicleCartTMS,vehicleFld.getText())){
                    new Alert(Alert.AlertType.ERROR,"Vehicle Already Added !").show();
                }else {
                    vehicleCartTMS.add(vehicleCartTM);
                    Double subTotal = RentModel.getVehicleTotal(vehicleCartTMS);
                    vehicleSubTotalLabel.setTextFill(Color.GREEN);
                    vehicleSubTotalLabel.setText("Sub Total Is :"+subTotal);
                    vehicleRentTable.setItems(vehicleCartTMS);
                    clearVehicleFields();
                    vehiclePlaceOrderBtn.setDisable(false);
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Vehicle Not Available !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }


    @FXML
    void vehicleNewBtnOnAction(ActionEvent event) throws IOException {
        Stage stage =new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Customer Form");
        stage.show();
    }

    public void vehicleCustomerIdValidate(KeyEvent keyEvent) {
        vehicleAddCartBtn.setDisable(true);
        try {
            if(RentModel.verifyCustomerId(vehicleCustomerFld.getText())){
                vehicleCustomerFld.setStyle("-fx-border-color: green");
                vehicleCustomerFld.setEditable(false);
            }else{
                vehicleCustomerFld.setEditable(true);
                vehicleCustomerFld.setStyle("-fx-border-color: red");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void vehicleIdValidate(KeyEvent keyEvent) {
        try {
            if(RentModel.verifyVehicleId(vehicleFld.getText())){
               vehicleFld.setStyle("-fx-border-color: green");
            }else {
                vehicleFld.setStyle("-fx-border-color: red");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void vehicleRentDaysValidate(KeyEvent keyEvent) {
        if(Regex.validateNumberOnly(vehicleRentDaysFld.getText())){
            vehicleRentDaysFld.setStyle("-fx-border-color: green");
        }else {
            vehicleRentDaysFld.setStyle("-fx-border-color: red");
        }
    }

    public void vehicleOnClickRefresh(MouseEvent mouseEvent) {
        try {
            if((RentModel.verifyCustomerId(vehicleCustomerFld.getText())&&RentModel.verifyVehicleId(vehicleFld.getText())&&Regex.validateNumberOnly(vehicleRentDaysFld.getText()))){
                vehicleCustomerFld.setText(vehicleCustomerFld.getText().toUpperCase());
                vehicleFld.setText(vehicleFld.getText().toUpperCase());
                vehicleAddCartBtn.setDisable(false);
            }else {
                vehicleAddCartBtn.setDisable(true);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }
    private void clearVehicleFields(){
        vehicleFld.clear();
        vehicleRentDaysFld.clear();
        vehicleFld.setStyle(null);
        vehicleRentDaysFld.setStyle(null);
        vehicleAddCartBtn.setDisable(true);
    }

    public void toolOnClickRefresh(MouseEvent mouseEvent) {
        try {
            if(RentModel.verifyCustomerId(toolCustomerFld.getText())&&RentModel.verifyToolId(toolFld.getText())&&Regex.validateNumberOnly(toolRentDaysFld.getText())){
                toolCustomerFld.setText(toolCustomerFld.getText().toUpperCase());
                toolFld.setText(toolFld.getText().toUpperCase());
                toolAddCartBtn.setDisable(false);
            }else {
                toolAddCartBtn.setDisable(true);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    public void toolCartBtnOnAction(ActionEvent actionEvent) {
        try {
            ToolCartTM toolCartTM = RentModel.getToolCartModel(new ToolOrder(toolRentOrderIdLabel.getText(),toolCustomerFld.getText(),toolFld.getText(),Integer.valueOf(toolRentDaysFld.getText())));
           if(toolCartTM!=null){
               toolCartTMS = toolRentTable.getItems();
               if(RentModel.checkToolCartDuplicate(toolCartTMS,toolFld.getText())){
                   new Alert(Alert.AlertType.ERROR,"Tool Already Added !").show();
               }else {
                   toolCartTMS.add(toolCartTM);
                   Double subTotal = RentModel.getToolTotal(toolCartTMS);
                   toolSubTotalLabel.setTextFill(Color.GREEN);
                   toolSubTotalLabel.setText("Sub Total Is :"+subTotal);
                   toolRentTable.setItems(toolCartTMS);
                   clearToolFields();
                   toolPlaceOrderBtn.setDisable(false);
               }
           }else{
               new Alert(Alert.AlertType.ERROR,"Tool Not Available !").show();
           }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private void clearToolFields() {
        toolFld.clear();
        toolRentDaysFld.clear();
        toolFld.setStyle(null);
        toolRentDaysFld.setStyle(null);
        toolAddCartBtn.setDisable(true);
    }

    public void customerIdValidate(KeyEvent keyEvent) {
        try {
            if(RentModel.verifyCustomerId(toolCustomerFld.getText())){
                toolCustomerFld.setStyle("-fx-border-color: green");
                toolCustomerFld.setEditable(false);
            }else {
                toolCustomerFld.setStyle("-fx-border-color: red");
                toolCustomerFld.setEditable(true);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    public void toolIdValidate(KeyEvent keyEvent) {
        try {
            if(RentModel.verifyToolId(toolFld.getText())){
                toolFld.setStyle("-fx-border-color: green");
            }else {
                toolFld.setStyle("-fx-border-color: red");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    public void toolRentDaysValidate(KeyEvent keyEvent) {
        if(Regex.validateNumberOnly(toolRentDaysFld.getText())){
            toolRentDaysFld.setStyle("-fx-border-color: green");
        }else {
            toolRentDaysFld.setStyle("-fx-border-color: red");
        }
    }

    public void placeToolOrderOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.CONFIRMATION,"Tool Order Placed !",ButtonType.NEXT,ButtonType.CANCEL).showAndWait().ifPresent(buttonType -> {
            if(buttonType==ButtonType.NEXT){
               Connection connection=null;
                try {
                    connection = DBConnection.getInstance().getConnection();
                    connection.setAutoCommit(false);
                 Boolean isRentOrderTableUpdated =  RentModel.updateToolRentOrderTable(new ToolOrder(toolRentOrderIdLabel.getText(), toolCustomerFld.getText(),toolFld.getText(),Integer.getInteger(toolRentDaysFld.getText())));
                 if(isRentOrderTableUpdated){
                     Boolean isToolDetailsTableUpdated = RentModel.updateToolDetailsTable(toolCartTMS);
                     if(isToolDetailsTableUpdated){
                         Boolean  isToolTableUpdated = RentModel.updateToolTable(toolCartTMS);
                         if(isToolTableUpdated){
                             connection.commit();
                             new Alert(Alert.AlertType.INFORMATION,"Tool Order Placed !").show();
                             newToolOrder();
                         }else {
                           new Alert(Alert.AlertType.ERROR,"Tool Order Not Placed !").show();
                           connection.rollback();
                         }
                     }else {
                            new Alert(Alert.AlertType.ERROR,"Tool Order Not Placed !").show();
                            connection.rollback();
                     }
                 }else {
                     new Alert(Alert.AlertType.ERROR,"Tool Order Not Placed !").show();
                     connection.rollback();
                 }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Tool Order Not Placed !").show();
            }
        });
    }

    public void newToolBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/ToolForm.fxml"))));
        stage.setTitle("Tool Form");
        stage.show();
    }

    public void newVehicleBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/VehicleForm.fxml"))));
        stage.setTitle("Vehicle Form");
        stage.show();
    }
}
