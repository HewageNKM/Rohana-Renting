package lk.hnkm.rohanarenting.service.interfaces;

import lk.hnkm.rohanarenting.dto.UserDTO;
import lk.hnkm.rohanarenting.dto.tm.UserTM;
import lk.hnkm.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserAccountService extends SuperService {
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
