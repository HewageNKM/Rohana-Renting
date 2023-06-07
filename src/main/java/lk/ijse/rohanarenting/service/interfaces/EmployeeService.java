package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.EmployeeDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.EmployeeDAO;
import lk.ijse.rohanarenting.dto.EmployeeDTO;
import lk.ijse.rohanarenting.dto.tm.EmployeeTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeService extends SuperService {
    EmployeeDAO employeeDAO = (EmployeeDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE_DAO);
    EmployeeDTO getEmployee(EmployeeDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean updateEmployee(EmployeeDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean addEmployee(EmployeeDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean deleteEmployee(EmployeeDTO dto) throws SQLException;
    ArrayList<EmployeeTM> getAllEmployees() throws SQLException;
    ArrayList<EmployeeTM> searchEmployee(String searchPhrase) throws SQLException, NoSuchAlgorithmException;
    boolean validateEmployeeId(String EmployeeId);
    boolean validateEmployeeNIC(String EmployeeNIC);
    boolean validateEmployeeMobileNumber(String employeeMobileNumber);
    boolean validateEmployeeEmail(String employeeEmail);
    boolean validateEmployeeZipCode(String employeeZipCode);
    boolean validateEmployeeFirstName(String employeeFirstName);
    boolean validateEmployeeLastName(String employeeLastName);
    boolean validateEmployeeStreet(String employeeStreet);
    boolean validateEmployeeCity(String employeeCity);
    String generateId() throws SQLException, NoSuchAlgorithmException;
    boolean isEmployeeExists(String employeeId) throws SQLException, NoSuchAlgorithmException;
}
