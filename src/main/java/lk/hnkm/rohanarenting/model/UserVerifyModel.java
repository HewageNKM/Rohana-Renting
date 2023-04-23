/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/22/23, 7:30 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserVerifyModel {

    public static Boolean verifyUser(String employeeId, String password) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Permission_Level FROM user WHERE `Employee ID` = ? AND UPassword = ?;", employeeId, password);
        if (resultSet.next()) {
           String permissionLevel = resultSet.getString(1);
          return permissionLevel.equals("A") ? true : false;
        }
        return null;
    }
}
