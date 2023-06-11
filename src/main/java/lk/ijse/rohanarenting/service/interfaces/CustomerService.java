package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.CustomerDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.CustomerDAO;
import lk.ijse.rohanarenting.dto.CustomerDTO;
import lk.ijse.rohanarenting.dto.tm.CustomerTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerService extends SuperService {
    CustomerDAO customerDAO = (CustomerDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER_DAO);
    CustomerDTO getCustomer(CustomerDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean updateCustomer(CustomerDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean addCustomer(CustomerDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean deleteCustomer(CustomerDTO dto) throws SQLException;
    ArrayList<CustomerTM> getAllCustomers() throws SQLException;
    ArrayList<CustomerTM> searchCustomer(String searchPhrase) throws SQLException, NoSuchAlgorithmException;
    boolean validateCustomerId(String customerId);
    boolean validateCustomerNIC(String customerNIC);
    boolean validateCustomerMobileNumber(String customerMobileNumber);
    boolean validateCustomerEmail(String customerEmail);
    boolean validateCustomerZipCode(String customerZipCode);
    boolean validateCustomerFirstName(String customerFirstName);
    boolean validateCustomerLastName(String customerLastName);
    boolean validateCustomerStreet(String customerStreet);
    boolean validateCustomerCity(String customerCity);
    String generateId() throws SQLException, NoSuchAlgorithmException;
    boolean isCustomerExists(String customerId) throws SQLException, NoSuchAlgorithmException;

    interface UserVerifyService {
    }
}
