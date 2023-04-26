package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.hnkm.rohanarenting.dto.Employee;
import lk.hnkm.rohanarenting.dto.tm.EmployeeTM;
import lk.hnkm.rohanarenting.model.EmployeeModel;
import lk.hnkm.rohanarenting.utill.Genarate;
import lk.hnkm.rohanarenting.utill.Regex;
import lk.hnkm.rohanarenting.utill.TableUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class EmployeeFormController {

    public TextField positionFld;
    public DatePicker birthDayPicker;
    public ComboBox<String> genderComboBox;
    public DatePicker joinedDtePicker;
    public TableColumn columnID;
    public TableColumn columnFirstName;
    public TableColumn columnLastName;
    public TableColumn columnStreet;
    public TableColumn columnState;
    public TableColumn columnCity;
    public TableColumn columnMobileNumber;
    public TableColumn columnEmail;
    public TableColumn columnJoinDate;
    public TableColumn columnNic;
    public TableColumn columnPosition;
    public TableColumn columnGender;
    public TableColumn columnEdit;
    public TableColumn columnDelete;
    public TableColumn columnBirthday;
    public TableColumn columnReport;
    public TextField searchFld;
    public TableColumn columnZipCode;
    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private Label notifyLabel;

    @FXML
    private TableView<EmployeeTM> employeeTable;


    @FXML
    private TextField employeeIdFld;

    @FXML
    private TextField firstNameFld;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField mobileNumberFld;

    @FXML
    private TextField nicFld;

    @FXML
    private TextField streetFld;

    @FXML
    private TextField lastNameFld;

    @FXML
    private TextField cityFld;

    @FXML
    private TextField zipFld;

    @FXML
    private TextField stateFld;
    private ObservableList <EmployeeTM> employees = FXCollections.observableArrayList();
    private final ObservableList<String> genders = FXCollections.observableArrayList("Male","Female");

    public void initialize() {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        genderComboBox.setItems(genders);
        getId();
        setCellValueFactory();
        loadAllEmployees();
        TableUtil.installCopy(employeeTable);
    }

    private void loadAllEmployees() {
        try {
            ArrayList<EmployeeTM> allEmployees = EmployeeModel.getAllEmployees();
            for (EmployeeTM employeeTM:allEmployees) {
                setActionsOnBtn(employeeTM.getShowBtn(),employeeTM.getEdit(),employeeTM.getDelete());
            }
            employees.clear();
            employees.addAll(allEmployees);
            employeeTable.setItems(employees);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("EID"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnNic.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        columnMobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnJoinDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnEdit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        columnDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        columnBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        columnReport.setCellValueFactory(new PropertyValueFactory<>("showBtn"));
        columnZipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
    }

    @FXML
    void nicValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateNIC(nicFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid NIC !");
            nicFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid NIC !");
            nicFld.setStyle("-fx-border-color: Red");
        }
    }

    @FXML
    void streetValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(streetFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Address !");
            streetFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Address !");
            streetFld.setStyle("-fx-border-color: Red");
        }
    }
    @FXML
    void emailValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateEmail(emailFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Email !");
            emailFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Email !");
            emailFld.setStyle("-fx-border-color: Red");
        }
    }
    @FXML
    void employeeIdValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateEID(employeeIdFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Employee ID");
            employeeIdFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Employee ID");
            employeeIdFld.setStyle("-fx-border-color: Red");
        }
    }

    @FXML
    void mobileNumberValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateMobile(mobileNumberFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Mobile Number !");
            mobileNumberFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Mobile Number !");
            mobileNumberFld.setStyle("-fx-border-color: Red");
        }
    }

    @FXML
    void firstNameValidate(KeyEvent event) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(firstNameFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Name !");
            firstNameFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Name !");
            firstNameFld.setStyle("-fx-border-color: Red");
        }
    }

    @FXML
    void refreshOnClick(MouseEvent event) {
        if(
                Regex.validateName(firstNameFld.getText()) &&
                        Regex.validateName(lastNameFld.getText())&&
                        Regex.validateNIC(nicFld.getText()) &&
                        Regex.validateMobile(mobileNumberFld.getText()) &&
                        Regex.validateEmail(emailFld.getText()) &&
                        Regex.validateName(streetFld.getText()) &&
                        Regex.validateName(cityFld.getText()) &&
                        Regex.validateName(stateFld.getText())&&
                       Regex.validateZIP(zipFld.getText())&&
                        Regex.validateEID(employeeIdFld.getText()) &&
                        (genderComboBox.getSelectionModel().getSelectedItem()!=null) &&
                        (birthDayPicker.getValue()!=null)&&
                        (joinedDtePicker.getValue()!=null)&&
                        Regex.validateName(positionFld.getText())

        ){
            employeeIdFld.setText(employeeIdFld.getText().toUpperCase());
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
        emailFld.setText("");
        firstNameFld.setText("");
        lastNameFld.setText("");
        nicFld.setText("");
        mobileNumberFld.setText("");
        streetFld.setText("");
        cityFld.setText("");
        stateFld.setText("");
        zipFld.setText("");
        employeeIdFld.setText("");
        positionFld.setText("");
        birthDayPicker.setValue(null);
        joinedDtePicker.setValue(null);
        genderComboBox.getSelectionModel().clearSelection();
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        employeeIdFld.setStyle("-fx-border-color: null");
        emailFld.setStyle("-fx-border-color: null");
        firstNameFld.setStyle("-fx-border-color: null");
        lastNameFld.setStyle("-fx-border-color: null");
        nicFld.setStyle("-fx-border-color: null");
        mobileNumberFld.setStyle("-fx-border-color: null");
        streetFld.setStyle("-fx-border-color: null");
        cityFld.setStyle("-fx-border-color: null");
        stateFld.setStyle("-fx-border-color: null");
        zipFld.setStyle("-fx-border-color: null");
        positionFld.setStyle("-fx-border-color: null");
        birthDayPicker.setStyle("-fx-border-color: null");
        joinedDtePicker.setStyle("-fx-border-color: null");
        notifyLabel.setText("");
        employeeIdFld.setDisable(false);
   }

    public void idGenarateOnAction(javafx.event.ActionEvent actionEvent) {
        getId();
    }

    private void getId() {
        String id = Genarate.generateEmployeeId();
        try {
            while (EmployeeModel.verifyId(id)) {
                id = Genarate.generateEmployeeId();
            }
            employeeIdFld.setText(id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void enterOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            Employee employee = EmployeeModel.getEmployee(employeeIdFld.getText());
            if(employee!=null){
                emailFld.setText(employee.getEID());
                firstNameFld.setText(employee.getFistName());
                lastNameFld.setText(employee.getLastName());
                nicFld.setText(employee.getNic());
                genderComboBox.getSelectionModel().select(employee.getGender());
                birthDayPicker.setValue(employee.getDateOfBirth());
                mobileNumberFld.setText(employee.getMobileNumber());
                emailFld.setText(employee.getEmail());
                zipFld.setText(String.valueOf(employee.getZip()));
                cityFld.setText(employee.getCity());
                streetFld.setText(employee.getStreet());
                stateFld.setText(employee.getState());
                joinedDtePicker.setValue(employee.getJoinedDate());
                positionFld.setText(employee.getPosition());
                joinedDtePicker.setDisable(true);
                employeeIdFld.setDisable(true);
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee Details Not Found !").show();
                emailFld.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void saveBtnOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            if(EmployeeModel.isIdExist(employeeIdFld.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"New Employee Will Be Added !",ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(buttonType -> {
                    if(buttonType==ButtonType.OK){
                        try {
                        Boolean  isUpdate = EmployeeModel.addEmployee(new Employee(employeeIdFld.getText(),firstNameFld.getText(),lastNameFld.getText(),nicFld.getText(),genderComboBox.getSelectionModel().getSelectedItem(),birthDayPicker.getValue(),mobileNumberFld.getText(),emailFld.getText(),Integer.valueOf(zipFld.getText()),cityFld.getText(),streetFld.getText(),stateFld.getText(),joinedDtePicker.getValue(),positionFld.getText()));
                        if(isUpdate) {
                            new Alert(Alert.AlertType.INFORMATION, "Employee Added Successfully").show();
                            clearFields();
                            loadAllEmployees();
                            getId();
                        }else {
                            new Alert(Alert.AlertType.ERROR, "Employee Added Failed !").show();
                        }
                        }catch (SQLException e){
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Details Will Be Updated !", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(ButtonType->{
                    if(ButtonType == ButtonType.YES){
                        try {
                            Boolean  isUpdate = EmployeeModel.updateEmployee(new Employee(employeeIdFld.getText(),firstNameFld.getText(),lastNameFld.getText(),nicFld.getText(),genderComboBox.getSelectionModel().getSelectedItem(),birthDayPicker.getValue(),mobileNumberFld.getText(),emailFld.getText(),Integer.valueOf(zipFld.getText()),cityFld.getText(),streetFld.getText(),stateFld.getText(),joinedDtePicker.getValue(),positionFld.getText()));
                            if(isUpdate) {
                                new Alert(Alert.AlertType.INFORMATION, "Employee Detail Updated Successfully").show();
                                clearFields();
                                loadAllEmployees();
                            }else {
                                new Alert(Alert.AlertType.ERROR, "Employee Detail Updated Failed !").show();
                            }
                        }catch (SQLException e){
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        }
                    }
                });
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
        }
    }

    public void deleteBtnOnAction(javafx.event.ActionEvent actionEvent) {
            new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure Want to Delete Employee ?", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(ButtonType->{
                if(ButtonType == ButtonType.YES){
                    try {
                     Boolean  isDeleted = EmployeeModel.deleteEmployee(employeeIdFld.getText());
                        if (isDeleted) {
                            new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully !").show();
                            clearFields();
                            loadAllEmployees();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Employee Not Found !").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                        throw new RuntimeException(e);
                    }
                }
            });
    }

    public void clearBtnOnAction(javafx.event.ActionEvent actionEvent) {
        clearFields();
    }

    public void searchFldOnAction(KeyEvent keyEvent) {
        if(searchFld.getText().trim().isEmpty()){
            loadAllEmployees();
        }else {
            try {
                ArrayList<EmployeeTM> filteredList = EmployeeModel.searchEmployee("%"+searchFld.getText()+"%");
                for (EmployeeTM employeeTM:filteredList) {
                    setActionsOnBtn(employeeTM.getShowBtn(),employeeTM.getEdit(),employeeTM.getDelete());
                }
                ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList(filteredList);
                if(filteredList.size()>0){
                    employeeTable.setItems(employeeTMS);
                }else {
                    employeeTable.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }

    private void setActionsOnBtn(JFXButton showBtn, JFXButton edit, JFXButton delete) {
        showBtn.setOnAction(event -> {
            EmployeeTM employeeTM = employeeTable.getSelectionModel().getSelectedItem();
            if(employeeTM != null){
                printEmployeeReport(employeeTM);
            }else {
                new Alert(Alert.AlertType.ERROR,"Please, Select a Row").show();
            }
        });
        edit.setOnAction(event -> {
            EmployeeTM employeeTM = employeeTable.getSelectionModel().getSelectedItem();
            if(employeeTM != null){
                employeeIdFld.setText(employeeTM.getEID());
                firstNameFld.setText(employeeTM.getFirstName());
                lastNameFld.setText(employeeTM.getLastName());
                nicFld.setText(employeeTM.getNIC());
                genderComboBox.getSelectionModel().select(employeeTM.getGender());
                birthDayPicker.setValue(employeeTM.getBirthday());
                mobileNumberFld.setText(employeeTM.getMobile());
                emailFld.setText(employeeTM.getEmail());
                zipFld.setText(String.valueOf(employeeTM.getZipCode()));
                cityFld.setText(employeeTM.getCity());
                streetFld.setText(employeeTM.getStreet());
                stateFld.setText(employeeTM.getState());
                joinedDtePicker.setValue(employeeTM.getJoinedDate());
                positionFld.setText(employeeTM.getPosition());
                joinedDtePicker.setDisable(true);
                employeeIdFld.setDisable(true);
            }else {
                new Alert(Alert.AlertType.ERROR,"Please, Select a Row").show();
            }
        });
        delete.setOnAction(event -> {
            EmployeeTM employeeTM = employeeTable.getSelectionModel().getSelectedItem();
            if(employeeTM != null){
                new Alert(Alert.AlertType.CONFIRMATION,"Are You sure Want to Delete Employee ?", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(ButtonType->{
                    if(ButtonType == ButtonType.YES){
                        try {
                            Boolean isDeleted = EmployeeModel.deleteEmployee(employeeTM.getEID());
                            if (isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully !").show();
                                clearFields();
                                loadAllEmployees();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Employee Not Found !").show();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                            throw new RuntimeException(e);
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.ERROR,"Please, Select a Row").show();
            }
        });
    }

    private void printEmployeeReport(EmployeeTM employeeTM) {
        HashMap<String, Object> params = new HashMap<>();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(employeeTM));
        params.put("employeeId", employeeTM.getEID());
        params.put("firstName", employeeTM.getFirstName());
        params.put("lastName", employeeTM.getLastName());
        params.put("NIC", employeeTM.getNIC());
        params.put("position",employeeTM.getPosition());
        params.put("gender", employeeTM.getGender());
        params.put("birthday", Date.valueOf(employeeTM.getBirthday()));
        params.put("mobile", employeeTM.getMobile());
        params.put("email", employeeTM.getEmail());
        params.put("zipCode", employeeTM.getZipCode());
        params.put("city", employeeTM.getCity());
        params.put("street", employeeTM.getStreet());
        params.put("state", employeeTM.getState());
        params.put("joinedDate", Date.valueOf(employeeTM.getJoinedDate()));

        try {
            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/reports/employee_Report.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, params, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

        public void lastNameValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(lastNameFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Last Name !");
            lastNameFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Last Name !");
            lastNameFld.setStyle("-fx-border-color: Red");
        }
    }

    public void cityValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(cityFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid City Name !");
            cityFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid City Name !");
            cityFld.setStyle("-fx-border-color: Red");
        }
    }

    public void zipValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateZIP(zipFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid ZIP Code !");
            zipFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid ZIP Code !");
            zipFld.setStyle("-fx-border-color: Red");
        }
    }

    public void stateValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(stateFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid State Name !");
            stateFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid State Name !");
            stateFld.setStyle("-fx-border-color: Red");
        }
    }

    public void positionValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateName(positionFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Position Name !");
            positionFld.setStyle("-fx-border-color: Green");
        } else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Position Name !");
            positionFld.setStyle("-fx-border-color: Red");
        }
    }
}

