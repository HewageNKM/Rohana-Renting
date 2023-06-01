package lk.hnkm.rohanarenting.service.interfaces;

import lk.hnkm.rohanarenting.dto.UserDTO;
import lk.hnkm.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface LoginService extends SuperService {
    boolean verifyUserCredentials(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean passwordValidate(String password);
    boolean employeeIdValidate(String employeeId);
    void insertUserEntry(String employeeId) throws SQLException;
    void setUserLogoutEntry() throws SQLException;
}
