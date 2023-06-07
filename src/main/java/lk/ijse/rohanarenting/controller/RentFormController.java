package lk.ijse.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lk.ijse.rohanarenting.db.DBConnection;
import lk.ijse.rohanarenting.dto.CustomerDTO;
import lk.ijse.rohanarenting.dto.ToolOrderDTO;
import lk.ijse.rohanarenting.dto.VehicleOrderDTO;
import lk.ijse.rohanarenting.dto.tm.ToolCartTM;
import lk.ijse.rohanarenting.dto.tm.ToolRentOrderJesperReportDetailTM;
import lk.ijse.rohanarenting.dto.tm.VehicleCartTM;
import lk.ijse.rohanarenting.dto.tm.VehicleRentOrderJesperReportDetailTM;
import lk.ijse.rohanarenting.model.RentModel;
import lk.ijse.rohanarenting.utill.Genarate;
import lk.ijse.rohanarenting.utill.toolService;
import lk.ijse.rohanarenting.utill.notification.TopUpNotifications;
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
        try {
            RentModel.checkInsuranceTable();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
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
                    Boolean isOrderAdded= RentModel.updateVehicleRentOrderTable(new VehicleOrderDTO(vehicleRentOrderLabel.getText(),vehicleCustomerFld.getText(),vehicleFld.getText(),Integer.getInteger(vehicleRentDaysFld.getText())));
                    if(isOrderAdded){
                        Boolean isDetailsAdded = RentModel.addVehicleRentOrderDetailTable(vehicleCartTMS);
                        if(isDetailsAdded){
                            Boolean isVehicleUpdated = RentModel.updateVehicleTable(vehicleCartTMS);
                            if(isVehicleUpdated){
                                connection.commit();
                                TopUpNotifications.success("Order Placed Successfully !");
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
                    e.printStackTrace();
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
        CustomerDTO customerDTO =RentModel.getCustomer(vehicleCustomerFld.getText());
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("subTotal",RentModel.getVehicleTotal(vehicleCartTMS));
        params.put("name", customerDTO.getFirstName()+" "+ customerDTO.getLastName());
        params.put("street", customerDTO.getStreet());
        params.put("city", customerDTO.getCity());
        params.put("zip", customerDTO.getZipCode());
        params.put("mobileNumber", customerDTO.getMobileNumber());
        params.put("email", customerDTO.getEmail());
        params.put("barCodeNumber",vehicleRentOrderLabel.getText());
        ArrayList<VehicleRentOrderJesperReportDetailTM> vehicleRentOrderJesperReportDetailTMS = RentModel.getVehicleJesperReport(vehicleCartTMS);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vehicleRentOrderJesperReportDetailTMS);
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
        stage.setResizable(false);
        stage.setTitle("Customer Form");
        stage.show();
    }

    @FXML
    void vehicleCartBtnOnAction(ActionEvent event) {
        try {
            VehicleCartTM vehicleCartTM = RentModel.getVehicleCartModel(new VehicleOrderDTO(vehicleRentOrderLabel.getText(),vehicleCustomerFld.getText(),vehicleFld.getText(),Integer.valueOf(vehicleRentDaysFld.getText())));
            if(vehicleCartTM!=null){
                setActionVehicleBtn(vehicleCartTM.getRemove());
                vehicleCartTMS = vehicleRentTable.getItems();
                if(RentModel.checkVehicleCartDuplicate(vehicleCartTMS,vehicleFld.getText())){
                    new Alert(Alert.AlertType.ERROR,"Vehicle Already Added !").show();
                }else {
                    if(RentModel.checkValidVehicleInsurance(vehicleCartTM)){
                        new Alert(Alert.AlertType.ERROR,"Rental Period Longer Than Insurance Period Or No Insurance Details !").show();
                    }else {
                        vehicleCartTMS.add(vehicleCartTM);
                        Double subTotal = RentModel.getVehicleTotal(vehicleCartTMS);
                        vehicleSubTotalLabel.setTextFill(Color.GREEN);
                        vehicleSubTotalLabel.setText("Sub Total Is :"+subTotal);
                        vehicleRentTable.setItems(vehicleCartTMS);
                        clearVehicleFields();
                        vehiclePlaceOrderBtn.setDisable(false);
                    }
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
        stage.setResizable(false);
        stage.setMaximized(false);
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
        if(toolService.validateNumberOnly(vehicleRentDaysFld.getText())){
            vehicleRentDaysFld.setStyle("-fx-border-color: green");
        }else {
            vehicleRentDaysFld.setStyle("-fx-border-color: red");
        }
    }

    public void vehicleOnClickRefresh(MouseEvent mouseEvent) {
        try {
            if((RentModel.verifyCustomerId(vehicleCustomerFld.getText())&&RentModel.verifyVehicleId(vehicleFld.getText())&& toolService.validateNumberOnly(vehicleRentDaysFld.getText()))){
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
            if(RentModel.verifyCustomerId(toolCustomerFld.getText())&&RentModel.verifyToolId(toolFld.getText())&& toolService.validateNumberOnly(toolRentDaysFld.getText())){
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
            ToolCartTM toolCartTM = RentModel.getToolCartModel(new ToolOrderDTO(toolRentOrderIdLabel.getText(),toolCustomerFld.getText(),toolFld.getText(),Integer.valueOf(toolRentDaysFld.getText())));
           if(toolCartTM!=null){
               setActionOnToolBtn(toolCartTM.getRemove());
               toolCartTMS = toolRentTable.getItems();
               if(RentModel.checkToolCartDuplicate(toolCartTMS,toolFld.getText())){
                   new Alert(Alert.AlertType.ERROR,"Tool Already Added !").show();
               }else {
                   if(RentModel.checkValidToolInsurance(toolCartTM)){
                       new Alert(Alert.AlertType.ERROR,"Rental Period Longer Than Insurance Period Or No Insurance Details !").show();
                   }else {
                       toolCartTMS.add(toolCartTM);
                       Double subTotal = RentModel.getToolTotal(toolCartTMS);
                       toolSubTotalLabel.setTextFill(Color.GREEN);
                       toolSubTotalLabel.setText("Sub Total Is :"+subTotal);
                       toolRentTable.setItems(toolCartTMS);
                       clearToolFields();
                       toolPlaceOrderBtn.setDisable(false);
                   }
               }
           }else{
               new Alert(Alert.AlertType.ERROR,"Tool Not Available !").show();
           }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private void setActionOnToolBtn(JFXButton remove) {
        remove.setOnAction(event -> {
            ToolCartTM toolCartTM = toolRentTable.getSelectionModel().getSelectedItem();
            if(toolCartTM!=null){
                toolCartTMS.remove(toolRentTable.getSelectionModel().getSelectedItem());
                Double subTotal = RentModel.getToolTotal(toolCartTMS);
                toolSubTotalLabel.setTextFill(Color.GREEN);
                toolSubTotalLabel.setText("Sub Total Is :"+subTotal);
                if(toolCartTMS.size()==0){
                    toolPlaceOrderBtn.setDisable(true);
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Please Select Row !").show();
            }
        });
    }
    private void setActionVehicleBtn(JFXButton remove) {
        remove.setOnAction(event -> {
            VehicleCartTM vehicleTM = vehicleRentTable.getSelectionModel().getSelectedItem();
            if(vehicleTM!=null){
                vehicleCartTMS.remove(vehicleRentTable.getSelectionModel().getSelectedItem());
                Double subTotal = RentModel.getToolTotal(toolCartTMS);
                vehicleSubTotalLabel.setTextFill(Color.GREEN);
                vehicleSubTotalLabel.setText("Sub Total Is :"+subTotal);
                if(vehicleCartTMS.size()==0){
                    vehiclePlaceOrderBtn.setDisable(true);
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Please Select Row !").show();
            }
        });
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
        if(toolService.validateNumberOnly(toolRentDaysFld.getText())){
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
                 Boolean isRentOrderTableUpdated =  RentModel.updateToolRentOrderTable(new ToolOrderDTO(toolRentOrderIdLabel.getText(), toolCustomerFld.getText(),toolFld.getText(),Integer.getInteger(toolRentDaysFld.getText())));
                 if(isRentOrderTableUpdated){
                     Boolean isToolDetailsTableUpdated = RentModel.updateToolDetailsTable(toolCartTMS);
                     if(isToolDetailsTableUpdated){
                         Boolean  isToolTableUpdated = RentModel.updateToolTable(toolCartTMS);
                         if(isToolTableUpdated){
                             connection.commit();
                             TopUpNotifications.success("Tool Order Placed !");
                             printToolInvoice();
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

    private void printToolInvoice() {
        CustomerDTO customerDTO =RentModel.getCustomer(toolCustomerFld.getText());
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("subTotal",RentModel.getToolTotal(toolCartTMS));
        params.put("name", customerDTO.getFirstName()+" "+ customerDTO.getLastName());
        params.put("street", customerDTO.getStreet());
        params.put("city", customerDTO.getCity());
        params.put("zip", customerDTO.getZipCode());
        params.put("mobileNumber", customerDTO.getMobileNumber());
        params.put("email", customerDTO.getEmail());
        params.put("barCodeNumber",toolRentOrderIdLabel.getText());
        ArrayList<ToolRentOrderJesperReportDetailTM> toolRentOrderJesperReportDetailTM = RentModel.getToolJesperReport(toolCartTMS);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(toolRentOrderJesperReportDetailTM);
        try {
            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/reports/tool_Invoice.jrxml"
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

    public void newToolBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/ToolViewForm.fxml"))));
        stage.setTitle("Tool View Form");
        stage.getIcons().add(new Image("/img/search.png"));
        stage.setResizable(false);
        stage.show();
    }

    public void newVehicleBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/VehicleViewForm.fxml"))));
        stage.getIcons().add(new Image("/img/search.png"));
        stage.setTitle("Vehicle View Form");
        stage.setResizable(false);
        stage.show();
    }
}
