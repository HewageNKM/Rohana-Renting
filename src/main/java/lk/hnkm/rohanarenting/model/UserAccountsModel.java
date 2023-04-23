/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/30/23, 9:44 AM
 *
 */

package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.User;
import lk.hnkm.rohanarenting.dto.tm.UserTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserAccountsModel {

    public static Boolean verifyEmployeeID(String employeeID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM employee WHERE EID =?",employeeID);
        if(resultSet.next()){
            return true;
        }else {
            return false;
        }
    }

    public static User getUserDetail(String employeeID) throws SQLException{
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE `Employee ID` = ?",employeeID);
        if (resultSet.next()){
            return new User(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        }else {
            return null;
        }
    }


    public static Boolean updateUser(User user) throws SQLException {
     Boolean isUpdate = CruidUtil.execute("UPDATE user SET `Employee ID`=?,UName = ?,UPassword=?,Permission_Level=? WHERE `Employee ID`=?",user.getEID(),user.getUName(),user.getUPassword(),user.getPermissionLevel(),user.getEID());
     if(isUpdate) {
         return true;
     }else {
         return false;
     }
    }

    public static boolean addUser(User user) throws SQLException {
        return CruidUtil.execute("INSERT INTO user VALUES (?,?,?,?)",user.getEID(),user.getUName(),user.getUPassword(),user.getPermissionLevel());
    }

    public static Boolean deleteUser(String employeeID) throws SQLException {
        return CruidUtil.execute("DELETE FROM user WHERE `Employee ID`=?",employeeID);
    }

    public static Boolean isUserExist(String employeeId) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE `Employee ID`=?",employeeId);
        return resultSet.next();
    }

    public static ArrayList<UserTM> getAllUsers() throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM user");
        ArrayList<UserTM> arrayList = new ArrayList<UserTM>();
       while (resultSet.next()){
           String EID = resultSet.getString(1);
           String name = resultSet.getString(2);
           String password = resultSet.getString(3);
           String permissionLevel = resultSet.getString(4);
          arrayList.add(new UserTM(EID,name,password,permissionLevel,new JFXButton("Edit"),new JFXButton("Delete")));
       }
       return arrayList;
    }
}
