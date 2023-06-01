package lk.hnkm.rohanarenting.service.interfaces;

import lk.hnkm.rohanarenting.dto.UserDTO;
import lk.hnkm.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface ForgotPasswordService extends SuperService {
    boolean UpdateUserPassword(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean verifyUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean passwordValidate(String password);
    boolean employeeIdValidate(String employeeId);
}
