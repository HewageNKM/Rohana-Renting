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
import java.time.LocalDate;
import java.time.LocalTime;

public class LoginModel {
    public static Boolean verifyEmployeeId(String employeeId, String password) throws SQLException, NoSuchAlgorithmException {
        password = Encrypt.encrypt(password);
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE   `Employee ID` =? AND UPassword=?",employeeId,password);
        return resultSet.next();
    }

    public static void InsertUserEntry(String employeeId) throws SQLException {
        CruidUtil.execute("INSERT INTO  user_login_history(EID,Date,Log_Time) VALUES(?,?,?)",employeeId, LocalDate.now(), LocalTime.now());
    }

    public static void setUserLogoutEntry() throws SQLException {
        CruidUtil.execute("UPDATE user_login_history SET Logout_Time = ? WHERE Logout_Time IS NULL", LocalTime.now());
    }
}
