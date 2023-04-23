/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/22/23, 6:44 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardModel {

    public static String getUserName(String employeeId) throws SQLException {
      ResultSet resultSet = CruidUtil.execute("SELECT UName FROM user WHERE `Employee ID` = ?", employeeId);
      if (resultSet.next()) {
          return resultSet.getString(1);
      }
      return "User";
    }
}
