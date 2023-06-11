package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface UserVerifyDAO extends CruidDAO {
    Boolean verifyUser(String employeeId, String password) throws SQLException, NoSuchAlgorithmException;
}

