/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.hnkm.rohanarenting.model;

import lk.hnkm.rohanarenting.security.Encrypt;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    public static Boolean verifyEmployeeId(String employeeId, String password) throws SQLException, NoSuchAlgorithmException {
        password = Encrypt.encrypt(password);
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE   `Employee ID` =? AND UPassword=?",employeeId,password);
        return resultSet.next();
    }
}
