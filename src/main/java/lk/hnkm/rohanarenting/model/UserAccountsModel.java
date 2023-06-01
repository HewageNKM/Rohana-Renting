/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/30/23, 9:44 AM
 *
 */

package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.UserDTO;
import lk.hnkm.rohanarenting.dto.tm.UserTM;
import lk.hnkm.rohanarenting.utill.security.Encrypt;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
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

    public static UserDTO getUserDetail(String employeeID) throws SQLException{
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE `Employee ID` = ?",employeeID);
        if (resultSet.next()){
            return new UserDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        }else {
            return null;
        }
    }


    public static Boolean updateUser(UserDTO userDTO) throws SQLException, NoSuchAlgorithmException {
        String password = Encrypt.encrypt(userDTO.getUPassword());
        return CruidUtil.execute("UPDATE user SET `Employee ID`=?,UName = ?,UPassword=?,Permission_Level=? WHERE `Employee ID`=?", userDTO.getEID(), userDTO.getUName(),password, userDTO.getPermissionLevel(), userDTO.getEID());
    }

    public static boolean addUser(UserDTO userDTO) throws SQLException, NoSuchAlgorithmException {
        String password = Encrypt.encrypt(userDTO.getUPassword());
        return CruidUtil.execute("INSERT INTO user VALUES (?,?,?,?)", userDTO.getEID(), userDTO.getUName(),password, userDTO.getPermissionLevel());
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
           JFXButton edit = new JFXButton();
           JFXButton delete = new JFXButton();
           edit.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
           delete.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");

           String EID = resultSet.getString(1);
           String name = resultSet.getString(2);
           String password = resultSet.getString(3);
           String permissionLevel = resultSet.getString(4);
          arrayList.add(new UserTM(EID,name,password,permissionLevel,edit,delete));
       }
       return arrayList;
    }

    public static ArrayList<UserTM> searchUser(String searchPhrase) throws SQLException {
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM user WHERE `Employee ID` LIKE ? OR UName LIKE ?  OR Permission_Level LIKE ?" ,searchPhrase,searchPhrase,searchPhrase);
        ArrayList<UserTM> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            JFXButton edit = new JFXButton();
            JFXButton delete = new JFXButton();
            edit.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            delete.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");

            String EID = resultSet.getString(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            String permissionLevel = resultSet.getString(4);
            arrayList.add(new UserTM(EID, name, password, permissionLevel, edit, delete));
        }
        return arrayList;
    }
}
