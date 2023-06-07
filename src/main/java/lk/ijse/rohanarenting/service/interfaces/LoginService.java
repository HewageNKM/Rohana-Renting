package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.LoginDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.LoginDAO;
import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface LoginService extends SuperService {
    LoginDAO loginDAO = (LoginDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LOGIN_DAO);
    boolean verifyUserCredentials(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean passwordValidate(String password);
    boolean employeeIdValidate(String employeeId);
    void insertUserLogInEntry(String employeeId) throws SQLException;
    void insertUserLogoutEntry() throws SQLException;
}
