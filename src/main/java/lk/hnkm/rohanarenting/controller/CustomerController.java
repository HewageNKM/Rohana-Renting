package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.hnkm.rohanarenting.dto.Customer;
import lk.hnkm.rohanarenting.dto.tm.CustomerTM;
import lk.hnkm.rohanarenting.model.CustomerModel;
import lk.hnkm.rohanarenting.model.EmployeeModel;
import lk.hnkm.rohanarenting.notification.TopUpNotifications;
import lk.hnkm.rohanarenting.utill.Genarate;
import lk.hnkm.rohanarenting.utill.Regex;
import lk.hnkm.rohanarenting.utill.TableUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CustomerController {
    public TableView<CustomerTM> customerTable;
    public TableColumn columnFirstName;
    public TableColumn columnLastName;
    public TableColumn columnMobileNumber;
    public TableColumn columnBirthday;
    public TableColumn columnZipCode;
    public TextField lastNameFld;
    public TextField cityFld;
    public TextField zipCodeFld;
    public TextField streetFld;
    public TextField firstNameFld;
    public TableColumn columnStreet;
    public TableColumn columnCity;
    public DatePicker birthdayDatePicker;
    public TableColumn columnShow;
    public TextField searchFld;
    @FXML
    private ImageView CustomerIdImgViewer;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private Label notifyLabel;

    @FXML
    private TableColumn<Object, Object> columnID;


    @FXML
    private TableColumn<Object, Object> columnNIC;


    @FXML
    private TableColumn<Object, Object> columnEmail;

    @FXML
    private TableColumn<Object, Object> columnEdit;

    @FXML
    private TableColumn<Object, Object> columnDelete;

    @FXML
    private TextField customerIdFld;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField nicFld;

    ObservableList<CustomerTM> customerList = FXCollections.observableArrayList();

    @FXML
    private TextField mobileNumberFld;
    public void initialize() {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        setCellValueFromTableToTextFields();
        loadAllCustomers();
        genarateId();
        TableUtil.installCopy(customerTable);
    }

    private void loadAllCustomers() {
        try {
            customerList.clear();
            ArrayList<CustomerTM> customerTMArrayList = CustomerModel.getAllCustomers();
            for (CustomerTM customerTM:customerTMArrayList) {
                setActionOnBtn(customerTM.getEditBtn(),customerTM.getDeleteBtn(),customerTM.getShowBtn());
            }
            customerList.addAll(customerTMArrayList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
        customerTable.setItems(customerList);
    }

    private void setActionOnBtn(JFXButton editBtn, JFXButton deleteBtn, JFXButton showBtn) {
        deleteBtn.setOnAction(event -> {
            CustomerTM customerTM = (CustomerTM) customerTable.getSelectionModel().getSelectedItem();
            if(customerTM != null){
                loadAllCustomers();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure Want to Delete Customer ?").showAndWait().ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK){
                        try {
                            boolean isDeleted = CustomerModel.deleteCustomer(editBtn.getId());
                            if(isDeleted){
                                loadAllCustomers();
                                genarateId();
                                TopUpNotifications.success("Customer Deleted Successfully !");
                                clearFields();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Customer Not Deleted !").show();
                            };
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });

        editBtn.setOnAction(event -> {
            CustomerTM customerTM =  customerTable.getSelectionModel().getSelectedItem();
                if(customerTM != null){
                    customerIdFld.setText(customerTM.getCID());
                    firstNameFld.setText(customerTM.getFirstName());
                    lastNameFld.setText(customerTM.getLastName());
                    nicFld.setText(customerTM.getNIC());
                    birthdayDatePicker.setValue(customerTM.getBirthday());
                    mobileNumberFld.setText(customerTM.getMobileNumber());
                    emailFld.setText(customerTM.getEmail());
                    streetFld.setText(customerTM.getStreet());
                    cityFld.setText(customerTM.getCity());
                    zipCodeFld.setText(String.valueOf(customerTM.getZipCode()));
                    customerIdFld.setDisable(true);
                }else {
                    new Alert(Alert.AlertType.ERROR,"Please Select Customer !").show();
                }
        });

        showBtn.setOnAction(event -> {
            CustomerTM customerTM = customerTable.getSelectionModel().getSelectedItem();
            if(customerTM != null){
                printCustomerDetails(customerTM);
            }else {
                new Alert(Alert.AlertType.ERROR,"Please Select Customer !").show();
            }
        });
    }

    private void printCustomerDetails(CustomerTM customerTM) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/customer_Report.jrxml"));
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(customerTM));
            HashMap<String, Object> params = new HashMap<>();
            params.put("customerId",customerTM.getCID());
            params.put("firstName",customerTM.getFirstName());
            params.put("lastName",customerTM.getLastName());
            params.put("NIC",customerTM.getNIC());
            params.put("birthday", Date.valueOf(customerTM.getBirthday()));
            params.put("mobile",customerTM.getMobileNumber());
            params.put("email",customerTM.getEmail());
            params.put("street",customerTM.getStreet());
            params.put("city",customerTM.getCity());
            params.put("zipCode",customerTM.getZipCode());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,dataSource );
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFromTableToTextFields() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("CID"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        columnBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        columnMobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnZipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        columnEdit.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
        columnDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
        columnShow.setCellValueFactory(new PropertyValueFactory<>("showBtn"));
    }

    @FXML
    void nicValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateNIC(nicFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid NIC !");
            nicFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid NIC !");
            nicFld.setStyle("-fx-border-color: red");
        }
    }


    @FXML
    void streetValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(streetFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Street !");
            streetFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Street !");
            streetFld.setStyle("-fx-border-color: red");
        }
    }


    @FXML
    void emailValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateEmail(emailFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Email !");
            emailFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Email !");
            emailFld.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void customerIdValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateCustomerCID(customerIdFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Employee ID");
            customerIdFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Employee ID");
            customerIdFld.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void mobileNumberValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateMobile(mobileNumberFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Mobile Number !");
            mobileNumberFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Mobile Number !");
            mobileNumberFld.setStyle("-fx-border-color: red");
            mobileNumberFld.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void firstNameValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(firstNameFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Name !");
            firstNameFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Name !");
            firstNameFld.setStyle("-fx-border-color: red");
        }
    }
    @FXML
    void lastNameValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(lastNameFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Name !");
            lastNameFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Name !");
            lastNameFld.setStyle("-fx-border-color: red");
        }
    }
    @FXML
   public void refreshOnClick(MouseEvent event) {
        if(Regex.validateCustomerCID(customerIdFld.getText()) && Regex.validateName(firstNameFld.getText()) && Regex.validateName(streetFld.getText()) && Regex.validateMobile(mobileNumberFld.getText()) && Regex.validateNIC(nicFld.getText()) && Regex.validateEmail(emailFld.getText())){
            customerIdFld.setText(customerIdFld.getText().toUpperCase());
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("All Set !");
            saveBtn.setDisable(false);
            deleteBtn.setDisable(false);
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Please Fill All Fields Correctly !");
            saveBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }
    }

   private void clearFields(){
       notifyLabel.setText("");
       CustomerIdImgViewer.setImage(null);
       customerIdFld.clear();
       firstNameFld.clear();
       lastNameFld.clear();
       birthdayDatePicker.setValue(null);
       cityFld.clear();
       zipCodeFld.clear();
       streetFld.clear();
       nicFld.clear();
       mobileNumberFld.clear();
       emailFld.clear();
       saveBtn.setDisable(true);
       deleteBtn.setDisable(true);
       customerIdFld.setDisable(false);
   }

    public void idGenarateOnAction(javafx.event.ActionEvent actionEvent) {
        genarateId();
    }

    private void genarateId() {
        String id = Genarate.generateCustomerId();
        try {
            while (EmployeeModel.verifyId(id)) {
                id = Genarate.generateCustomerId();
            }
            customerIdFld.setText(id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }
    }

    public void enterOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            Customer customer = CustomerModel.getCustomer(customerIdFld.getText());
            if(customer!=null){
                firstNameFld.setText(customer.getFistName());
                lastNameFld.setText(customer.getLastName());
                cityFld.setText(customer.getCity());
                zipCodeFld.setText(String.valueOf(customer.getZipCode()));
                streetFld.setText(customer.getStreet());
                nicFld.setText(customer.getNIC());
                mobileNumberFld.setText(customer.getMobileNumber());
                emailFld.setText(customer.getEmail());
                birthdayDatePicker.setValue(customer.getBirthday());
            }else {
                new Alert(Alert.AlertType.ERROR,"Customer ID Not Found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void saveBtnOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            if(CustomerModel.isIdExist(customerIdFld.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"New Customer Will Be Added !",ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(ButtonType->{
                    if(ButtonType == ButtonType.OK){
                        try {
                           Boolean isUpdated = CustomerModel.addCustomer(new Customer(customerIdFld.getText(),firstNameFld.getText(),lastNameFld.getText(),nicFld.getText(),birthdayDatePicker.getValue(),mobileNumberFld.getText(),emailFld.getText(),streetFld.getText(),cityFld.getText(),Integer.parseInt(zipCodeFld.getText())));
                            if(isUpdated){
                                new Alert(Alert.AlertType.INFORMATION,customerIdFld.getText()+" Customer Added Successfully").show();
                                loadAllCustomers();
                                clearFields();
                                genarateId();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Customer Not Added Or Same NIC Used!").show();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                            e.printStackTrace();
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Details Will Be Updated !",ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(ButtonType->{
                    if(ButtonType == ButtonType.OK){
                        try {
                            Boolean isUpdated = CustomerModel.updateCustomer(new Customer(customerIdFld.getText(),firstNameFld.getText(),lastNameFld.getText(),nicFld.getText(),birthdayDatePicker.getValue(),mobileNumberFld.getText(),emailFld.getText(),streetFld.getText(),cityFld.getText(),Integer.parseInt(zipCodeFld.getText())));
                            if(isUpdated){
                                new Alert(Alert.AlertType.INFORMATION,customerIdFld.getText()+" Customer Detail Updated Successfully").show();
                                loadAllCustomers();
                                clearFields();
                                genarateId();
                            }else {
                                new Alert(Alert.AlertType.INFORMATION,customerIdFld.getText()+" Customer Detail Update Fails !").show();
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
        }
    }

    public void deleteBtnOnAction(javafx.event.ActionEvent actionEvent) {
        new Alert(Alert.AlertType.CONFIRMATION, "Customer Will Be Deleted !", ButtonType.OK, ButtonType.CANCEL).showAndWait().ifPresent(ButtonType -> {
            if (ButtonType == ButtonType.OK) {
                try {
                    Boolean isDeleted = CustomerModel.deleteCustomer(customerIdFld.getText());
                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Successfully !").show();
                        loadAllCustomers();
                        clearFields();
                        genarateId();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Customer Not Found !").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void clearBtnOnAction(javafx.event.ActionEvent actionEvent) {
        clearFields();
    }

    public void searchFldOnAction(KeyEvent keyEvent) {
        if(searchFld.getText().trim().isEmpty()){
           loadAllCustomers();
        }else {
            try {
                ArrayList<CustomerTM> filterList = CustomerModel.searchCustomer("%"+searchFld.getText()+"%");
                for (CustomerTM customerTM:filterList) {
                 setActionOnBtn(customerTM.getEditBtn(),customerTM.getDeleteBtn(),customerTM.getShowBtn());
                }
                customerTable.setItems(FXCollections.observableArrayList(filterList));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }

    }


    @FXML
    void cityValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(cityFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Name !");
            cityFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Name !");
            cityFld.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void zipCodeValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateZIP(zipCodeFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Zip !");
            zipCodeFld.setStyle("-fx-border-color: green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Zip !");
            zipCodeFld.setStyle("-fx-border-color: red");
        }
    }
}

