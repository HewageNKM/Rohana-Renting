package lk.ijse.rohanarenting.service.impl;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rohanarenting.dto.EmployeeDTO;
import lk.ijse.rohanarenting.dto.tm.EmployeeTM;
import lk.ijse.rohanarenting.entity.Employee;
import lk.ijse.rohanarenting.service.interfaces.EmployeeService;
import lk.ijse.rohanarenting.utill.Genarate;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public EmployeeDTO getEmployee(EmployeeDTO dto) throws SQLException, NoSuchAlgorithmException {
        Employee employee = employeeDAO.get(new Employee(dto.getEID(),null,null,null,null,null,null,null,null,null,null,null,null,null));
        return new EmployeeDTO(employee.getEmployeeId(),employee.getFirstName(),employee.getLastName(),employee.getNIC(),employee.getGender(),employee.getBirthday(),employee.getMobileNumber(),employee.getEmail(),employee.getZipCode(),employee.getCity(),employee.getStreet(),employee.getState(),employee.getJoinedDate(),employee.getPosition());
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, NoSuchAlgorithmException {
        return employeeDAO.update(new Employee(dto.getEID(),dto.getFistName(),dto.getLastName(),dto.getNic(),dto.getGender(),dto.getDateOfBirth(),dto.getMobileNumber(),dto.getEmail(),dto.getZip(),dto.getCity(),dto.getStreet(),dto.getState(),dto.getJoinedDate(),dto.getPosition()));
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, NoSuchAlgorithmException {
        return employeeDAO.insert(new Employee(dto.getEID(),dto.getFistName(),dto.getLastName(),dto.getNic(),dto.getGender(),dto.getDateOfBirth(),dto.getMobileNumber(),dto.getEmail(),dto.getZip(),dto.getCity(),dto.getStreet(),dto.getState(),dto.getJoinedDate(),dto.getPosition()));
    }

    @Override
    public boolean deleteEmployee(EmployeeDTO dto) throws SQLException {
        return employeeDAO.delete(new Employee(dto.getEID(),dto.getFistName(),dto.getLastName(),dto.getNic(),dto.getGender(),dto.getDateOfBirth(),dto.getMobileNumber(),dto.getEmail(),dto.getZip(),dto.getCity(),dto.getStreet(),dto.getState(),dto.getJoinedDate(),dto.getPosition()));
    }

    @Override
    public ArrayList<EmployeeTM> getAllEmployees() throws SQLException {
        return getEmployeesTMS(employeeDAO.getAll());
    }

    @Override
    public ArrayList<EmployeeTM> searchEmployee(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        return getEmployeesTMS(employeeDAO.search(searchPhrase));
    }

    @Override
    public boolean validateEmployeeId(String EmployeeId) {
        return Regex.validateEID(EmployeeId);
    }

    @Override
    public boolean validateEmployeeNIC(String EmployeeNIC) {
        return Regex.validateNIC(EmployeeNIC);
    }

    @Override
    public boolean validateEmployeeMobileNumber(String employeeMobileNumber) {
        return Regex.validateMobile(employeeMobileNumber);
    }

    @Override
    public boolean validateEmployeeEmail(String employeeEmail) {
        return Regex.validateEmail(employeeEmail);
    }

    @Override
    public boolean validateEmployeeZipCode(String employeeZipCode) {
        return Regex.validateZIP(employeeZipCode);
    }

    @Override
    public boolean validateEmployeeFirstName(String employeeFirstName) {
        return Regex.validateName(employeeFirstName);
    }

    @Override
    public boolean validateEmployeeLastName(String employeeLastName) {
        return Regex.validateName(employeeLastName);
    }

    @Override
    public boolean validateEmployeeStreet(String employeeStreet) {
        return Regex.validateName(employeeStreet);
    }

    @Override
    public boolean validateEmployeeCity(String employeeCity) {
        return Regex.validateName(employeeCity);
    }

    @Override
    public String generateId() throws SQLException, NoSuchAlgorithmException {
        String id = Genarate.generateEmployeeId();
        while (employeeDAO.verify(new Employee(id,null,null,null,null,null,null,null,null,null,null,null,null,null))) {
            id = Genarate.generateEmployeeId();
        }
        return id;
    }

    @Override
    public boolean isEmployeeExists(String employeeId) throws SQLException, NoSuchAlgorithmException {
        return employeeDAO.verify(new Employee(employeeId,null,null,null,null,null,null,null,null,null,null,null,null,null));
    }
    private ArrayList<EmployeeTM> getEmployeesTMS(ArrayList<Employee> employees) {
        ArrayList<EmployeeTM> employeeTMS = new ArrayList<>();
        for (Employee employee : employees) {
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            employeeTMS.add(new EmployeeTM(employee.getEmployeeId(),employee.getFirstName(),employee.getLastName(),employee.getNIC(),employee.getGender(),employee.getBirthday(),employee.getMobileNumber(),employee.getEmail(),employee.getZipCode(),employee.getCity(),employee.getStreet(),employee.getState(),employee.getJoinedDate(),employee.getPosition(),showBtn,editBtn,deleteBtn));
        }
        return employeeTMS;
    }
}
