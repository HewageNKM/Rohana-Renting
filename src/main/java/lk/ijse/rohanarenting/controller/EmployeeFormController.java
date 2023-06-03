package lk.ijse.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.ijse.rohanarenting.dto.EmployeeDTO;
import lk.ijse.rohanarenting.dto.tm.EmployeeTM;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.EmployeeServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.EmployeeService;
import lk.ijse.rohanarenting.utill.TableUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.security.NoSuchAlgorithmException;
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

    private final EmployeeService employeeService = (EmployeeServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.EMPLOYEE_SERVICE);

    public void initialize() {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        genderComboBox.setItems(genders);
        idGenarateOnAction();
        setCellValueFactory();
        loadAllEmployees();
        TableUtil.installCopy(employeeTable);
    }

    private void loadAllEmployees() {
        try {
            ArrayList<EmployeeTM> allEmployees = employeeService.getAllEmployees();
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
        if (employeeService.validateEmployeeNIC(nicFld.getText())) {
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
        if (employeeService.validateEmployeeStreet(streetFld.getText())) {
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
        if (employeeService.validateEmployeeEmail(emailFld.getText())) {
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
        if (employeeService.validateEmployeeId(employeeIdFld.getText())) {
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
        if (employeeService.validateEmployeeMobileNumber(mobileNumberFld.getText())) {
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
        if (employeeService.validateEmployeeFirstName(firstNameFld.getText())) {
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
                employeeService.validateEmployeeCity(cityFld.getText())&&
                        employeeService.validateEmployeeEmail(emailFld.getText())&&
                        employeeService.validateEmployeeFirstName(firstNameFld.getText())&&
                        employeeService.validateEmployeeLastName(lastNameFld.getText())&&
                        employeeService.validateEmployeeMobileNumber(mobileNumberFld.getText())&&
                        employeeService.validateEmployeeNIC(nicFld.getText())&&
                        employeeService.validateEmployeeFirstName(positionFld.getText())&&
                        employeeService.validateEmployeeLastName(stateFld.getText())&&
                        employeeService.validateEmployeeStreet(streetFld.getText())&&
                        employeeService.validateEmployeeZipCode(zipFld.getText())&&
                        employeeService.validateEmployeeId(employeeIdFld.getText())

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

    public void idGenarateOnAction() {
        try {
            employeeIdFld.setText(employeeService.generateId());
        } catch (SQLException | NoSuchAlgorithmException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong, Please contact IT Team").show();
        }
    }


    public void enterOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployee(new EmployeeDTO(employeeIdFld.getText(), null, null, null, null, null, null, null, null, null, null, null, null, null));
            if(employeeDTO !=null){
                emailFld.setText(employeeDTO.getEID());
                firstNameFld.setText(employeeDTO.getFistName());
                lastNameFld.setText(employeeDTO.getLastName());
                nicFld.setText(employeeDTO.getNic());
                genderComboBox.getSelectionModel().select(employeeDTO.getGender());
                birthDayPicker.setValue(employeeDTO.getDateOfBirth());
                mobileNumberFld.setText(employeeDTO.getMobileNumber());
                emailFld.setText(employeeDTO.getEmail());
                zipFld.setText(String.valueOf(employeeDTO.getZip()));
                cityFld.setText(employeeDTO.getCity());
                streetFld.setText(employeeDTO.getStreet());
                stateFld.setText(employeeDTO.getState());
                joinedDtePicker.setValue(employeeDTO.getJoinedDate());
                positionFld.setText(employeeDTO.getPosition());
                joinedDtePicker.setDisable(true);
                employeeIdFld.setDisable(true);
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee Details Not Found !").show();
                emailFld.clear();
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void saveBtnOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            if(!employeeService.isEmployeeExists(employeeIdFld.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"New Employee Will Be Added !",ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(buttonType -> {
                    if(buttonType==ButtonType.OK){
                        try {
                        boolean isUpdate = employeeService.addEmployee(new EmployeeDTO(employeeIdFld.getText(),firstNameFld.getText(),lastNameFld.getText(),nicFld.getText(),genderComboBox.getSelectionModel().getSelectedItem(),birthDayPicker.getValue(),mobileNumberFld.getText(),emailFld.getText(),Integer.valueOf(zipFld.getText()),cityFld.getText(),streetFld.getText(),stateFld.getText(),joinedDtePicker.getValue(),positionFld.getText()));
                        if(isUpdate) {
                            new Alert(Alert.AlertType.INFORMATION, "Employee Added Successfully").show();
                            clearFields();
                            loadAllEmployees();
                            idGenarateOnAction();
                        }else {
                            new Alert(Alert.AlertType.ERROR, "Employee Added Failed !").show();
                        }
                        }catch (SQLException | NoSuchAlgorithmException e){
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        }
                    }
                });
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Details Will Be Updated !", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(ButtonType->{
                    if(ButtonType == javafx.scene.control.ButtonType.YES){
                        try {
                            boolean isUpdate = employeeService.updateEmployee(new EmployeeDTO(employeeIdFld.getText(),firstNameFld.getText(),lastNameFld.getText(),nicFld.getText(),genderComboBox.getSelectionModel().getSelectedItem(),birthDayPicker.getValue(),mobileNumberFld.getText(),emailFld.getText(),Integer.valueOf(zipFld.getText()),cityFld.getText(),streetFld.getText(),stateFld.getText(),joinedDtePicker.getValue(),positionFld.getText()));
                            if(isUpdate) {
                                new Alert(Alert.AlertType.INFORMATION, "Employee Detail Updated Successfully").show();
                                clearFields();
                                loadAllEmployees();
                            }else {
                                new Alert(Alert.AlertType.ERROR, "Employee Detail Updated Failed !").show();
                            }
                        }catch (SQLException | NoSuchAlgorithmException e){
                            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        }
                    }
                });
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
        }
    }

    public void deleteBtnOnAction(javafx.event.ActionEvent actionEvent) {
            new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure Want to Delete Employee ?", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(ButtonType->{
                if(ButtonType == ButtonType.YES){
                    try {
                     Boolean  isDeleted = employeeService.deleteEmployee(new EmployeeDTO(employeeIdFld.getText(),null,null,null,null,null,null,null,0,null,null,null,null,null));
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
                ArrayList<EmployeeTM> filteredList = employeeService.searchEmployee("%"+searchFld.getText()+"%");
                for (EmployeeTM employeeTM:filteredList) {
                    setActionsOnBtn(employeeTM.getShowBtn(),employeeTM.getEdit(),employeeTM.getDelete());
                }
                ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList(filteredList);
                if(filteredList.size()>0){
                    employeeTable.setItems(employeeTMS);
                }else {
                    employeeTable.getSelectionModel().clearSelection();
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
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
                    if(ButtonType == javafx.scene.control.ButtonType.YES){
                        try {
                            boolean isDeleted = employeeService.deleteEmployee(new EmployeeDTO(employeeTM.getEID(),null,null,null,null,null,null,null,0,null,null,null,null,null));
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
        if (employeeService.validateEmployeeLastName(lastNameFld.getText())) {
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
        if (employeeService.validateEmployeeCity(cityFld.getText())) {
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
        if (employeeService.validateEmployeeZipCode(zipFld.getText())) {
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
        if (employeeService.validateEmployeeStreet(stateFld.getText())) {
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
        if (employeeService.validateEmployeeLastName(positionFld.getText())) {
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

