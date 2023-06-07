package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.UserAccountDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.UserAccountDAO;
import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.dto.tm.UserTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserAccountService extends SuperService {
     UserAccountDAO userAccountDAO = (UserAccountDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER_ACCOUNT_DAO);
     ArrayList<UserTM> getUsers() throws SQLException;
     boolean addUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
     boolean updateUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
     boolean deleteUser(UserDTO dto) throws SQLException;
     UserDTO getUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
     ArrayList<UserTM> SearchUsers(String searchPhrase) throws SQLException, NoSuchAlgorithmException;
     boolean isUserExist(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
     boolean validateUserName(String userName);
     boolean validatePassword(String password);
     boolean validateEmployeeId(String employeeId);
}
