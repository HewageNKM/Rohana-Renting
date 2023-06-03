package lk.ijse.rohanarenting.service.impl;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Alert;
import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.CustomerDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.CustomerDAO;
import lk.ijse.rohanarenting.dto.CustomerDTO;
import lk.ijse.rohanarenting.dto.tm.CustomerTM;
import lk.ijse.rohanarenting.entity.Customer;
import lk.ijse.rohanarenting.model.EmployeeModel;
import lk.ijse.rohanarenting.service.interfaces.CustomerService;
import lk.ijse.rohanarenting.utill.Genarate;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO = (CustomerDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER_DAO);
    @Override
    public CustomerDTO getCustomer(CustomerDTO dto) throws SQLException, NoSuchAlgorithmException {
        Customer customer = customerDAO.get(new Customer(dto.getCID(),null,null,null,null,null,null,null,null,null));
        return new CustomerDTO(customer.getCustomerId(),customer.getFirstName(),customer.getLastName(),customer.getNic(),customer.getBirthday(),customer.getMobileNumber(),customer.getEmail(),customer.getStreet(),customer.getCity(),customer.getZipCode());
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, NoSuchAlgorithmException {
        return customerDAO.update(new Customer(dto.getCID(),dto.getFirstName(),dto.getLastName(),dto.getNIC(),dto.getBirthday(),dto.getMobileNumber(),dto.getEmail(),dto.getStreet(),dto.getCity(),dto.getZipCode()));
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, NoSuchAlgorithmException {
        return customerDAO.insert(new Customer(dto.getCID(),dto.getFirstName(),dto.getLastName(),dto.getNIC(),dto.getBirthday(),dto.getMobileNumber(),dto.getEmail(),dto.getStreet(),dto.getCity(),dto.getZipCode()));
    }

    @Override
    public boolean deleteCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.delete(new Customer(dto.getCID(),null,null,null,null,null,null,null,null,null));
    }

    @Override
    public ArrayList<CustomerTM> getAllCustomers() throws SQLException {
        return getCustomerTMS();
    }

    private ArrayList<CustomerTM> getCustomerTMS() throws SQLException {
        ArrayList<Customer> customers =  customerDAO.getAll();
        ArrayList<CustomerTM> customerTMS = new ArrayList<>();
        for (Customer customer : customers) {
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            customerTMS.add(new CustomerTM(customer.getCustomerId(),customer.getFirstName(),customer.getLastName(),customer.getNic(),customer.getBirthday(),customer.getMobileNumber(),customer.getEmail(),customer.getStreet(),customer.getCity(),customer.getZipCode(),showBtn,editBtn,deleteBtn));
        }
        return customerTMS;
    }

    @Override
    public ArrayList<CustomerTM> searchCustomer(String searchPhrase) throws SQLException {
        return getCustomerTMS();
    }

    @Override
    public boolean validateCustomerId(String customerId) {
        return Regex.validateCustomerCID(customerId);
    }

    @Override
    public boolean validateCustomerNIC(String customerNIC) {
        return Regex.validateNIC(customerNIC);
    }

    @Override
    public boolean validateCustomerMobileNumber(String customerMobileNumber) {
        return Regex.validateName(customerMobileNumber);
    }

    @Override
    public boolean validateCustomerEmail(String customerEmail) {
        return Regex.validateEmail(customerEmail);
    }

    @Override
    public boolean validateCustomerZipCode(String customerZipCode) {
        return Regex.validateZIP(customerZipCode);
    }


    @Override
    public boolean validateCustomerFirstName(String customerFirstName) {
        return Regex.validateName(customerFirstName);
    }

    @Override
    public boolean validateCustomerLastName(String customerLastName) {
        return Regex.validateName(customerLastName);
    }

    @Override
    public boolean validateCustomerStreet(String customerStreet) {
        return Regex.validateName(customerStreet);
    }

    @Override
    public boolean validateCustomerCity(String customerCity) {
        return Regex.validateName(customerCity);
    }

    @Override
    public String generateId() {
        String id = Genarate.generateCustomerId();
        try {
            while (EmployeeModel.verifyId(id)) {
                id = Genarate.generateCustomerId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }
        return id;
    }

    @Override
    public boolean isCustomerExists(String customerId) throws SQLException, NoSuchAlgorithmException {
        return customerDAO.verify(new Customer(customerId,null,null,null,null,null,null,null,null,null));
    }
}
