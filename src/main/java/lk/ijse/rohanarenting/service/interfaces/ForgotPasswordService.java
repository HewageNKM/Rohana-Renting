package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.ForgotPasswordDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.ForgotPasswordDAO;
import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface ForgotPasswordService extends SuperService {
    ForgotPasswordDAO forgotPasswordDAO = (ForgotPasswordDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FORGOT_PASSWORD_DAO);
    boolean UpdateUserPassword(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean verifyUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean passwordValidate(String password);
    boolean employeeIdValidate(String employeeId);
}
