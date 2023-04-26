
/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 *  Project Name: RohanaRenting
 *  Date and Time: 3/29/23, 6:49 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import lk.hnkm.rohanarenting.dto.User;
import lk.hnkm.rohanarenting.security.Encrypt;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordModel {
    public static Boolean verifyPassword(String uName,String uPassword) throws SQLException, NoSuchAlgorithmException {
        uPassword = Encrypt.encrypt(uPassword);
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE `Employee ID` = ? AND UPassword = ?",uName,uPassword);
        return resultSet.next();
    }

    public static Boolean updateUserPassword(User user) throws SQLException, NoSuchAlgorithmException {
        String password = Encrypt.encrypt(user.getUPassword());
        return CruidUtil.execute(" UPDATE user SET UPassword = ? WHERE `Employee ID` = ?;",password,user.getEID());
    }

    public static Boolean verifyEmployeeId(String employeeId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT `Employee ID` FROM user WHERE `Employee ID`=?;", employeeId);
        return resultSet.next();
    }
}
