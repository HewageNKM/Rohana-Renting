/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/31/23, 2:22 PM
 *
 */
package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.EmployeeDTO;
import lk.hnkm.rohanarenting.dto.tm.EmployeeTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static Boolean verifyId(String id) throws SQLException {
        ResultSet resultSet= CruidUtil.execute("SELECT * FROM employee WHERE EID = ?", id);
        if(resultSet.next()){
            return true;
        }else {
            return false;
        }
    }

    public static Boolean verifyNIC(String NIC) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM employee WHERE NIC=?",NIC);
        return !resultSet.next();
    }
    public static Boolean isIdExist(String id) throws SQLException {
        ResultSet resultSet= CruidUtil.execute("SELECT * FROM employee WHERE EID = ?", id);
        return !resultSet.next();
    }

    public static Boolean addEmployee(EmployeeDTO employeeDTO) throws SQLException {
      return  CruidUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                employeeDTO.getEID(),
                employeeDTO.getFistName(),
                employeeDTO.getLastName(),
                employeeDTO.getNic(),
                employeeDTO.getGender(),
                employeeDTO.getDateOfBirth(),
                employeeDTO.getMobileNumber(),
                employeeDTO.getEmail(),
                employeeDTO.getZip(),
                employeeDTO.getCity(),
                employeeDTO.getStreet(),
                employeeDTO.getState(),
                employeeDTO.getJoinedDate(),
                employeeDTO.getPosition()
                );
    }

    public static Boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
      return  CruidUtil.execute("UPDATE employee SET First_Name=?,Last_Name=?,NIC=?,Gender=?,Date_Of_Birth=?,Mobile_Number=?,Email=?,Zip=?,City=?,Street=?,State=?,Joined_Date=?,Position=? WHERE EID=?",
                employeeDTO.getFistName(),
                employeeDTO.getLastName(),
                employeeDTO.getNic(),
                employeeDTO.getGender(),
                employeeDTO.getDateOfBirth(),
                employeeDTO.getMobileNumber(),
                employeeDTO.getEmail(),
                employeeDTO.getZip(),
                employeeDTO.getCity(),
                employeeDTO.getStreet(),
                employeeDTO.getState(),
                employeeDTO.getJoinedDate(),
                employeeDTO.getPosition(),
                employeeDTO.getEID()
        );
    }

    public static EmployeeDTO getEmployee(String employeeID) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM employee WHERE EID=?",employeeID);
        if(resultSet.next()){
          return new EmployeeDTO(
                  resultSet.getString(1),
                  resultSet.getString(2),
                  resultSet.getString(3),
                  resultSet.getString(4),
                  resultSet.getString(5),
                  resultSet.getDate(6).toLocalDate(),
                  resultSet.getString(7),
                  resultSet.getString(8),
                  resultSet.getInt(9),
                  resultSet.getString(10),
                  resultSet.getString(11),
                  resultSet.getString(12),
                  resultSet.getDate(13).toLocalDate(),
                  resultSet.getString(14));
        }else {
            return null;
        }
    }

    public static boolean deleteEmployee(String EmployeeId) throws SQLException {
        return CruidUtil.execute("DELETE FROM employee WHERE EID=?",EmployeeId);
    }

    public static ArrayList<EmployeeTM> getAllEmployees() throws SQLException {
      ResultSet resultSet =  CruidUtil.execute("SELECT * FROM employee");
        ArrayList<EmployeeTM> employees = new ArrayList<EmployeeTM>();
      while (resultSet.next()){
          JFXButton showBtn = new JFXButton();
          JFXButton editBtn = new JFXButton();
          JFXButton deleteBtn = new JFXButton();
          editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
          deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
          showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            employees.add(new EmployeeTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6).toLocalDate(),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getInt(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getDate(13).toLocalDate(),
                    resultSet.getString(14),
                    showBtn,
                    editBtn,
                    deleteBtn
            ));
        }
        return employees;
    }

    public static ArrayList<EmployeeTM> searchEmployee(String searchPhrase) throws SQLException {
        ArrayList<EmployeeTM> employees = new ArrayList<EmployeeTM>();
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM employee WHERE EID LIKE ? OR First_Name LIKE ? OR Last_Name LIKE ?OR NIC LIKE ? OR Gender LIKE ? OR Date_Of_Birth LIKE ? OR Mobile_Number LIKE ? OR Email LIKE ? OR Zip LIKE ? OR City LIKE ? OR Street LIKE ? OR State LIKE ? OR Joined_Date LIKE ? OR Position LIKE ?",
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase
        );
         while (resultSet.next()){
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            employees.add(new EmployeeTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6).toLocalDate(),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getInt(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getDate(13).toLocalDate(),
                    resultSet.getString(14),
                    showBtn,
                    editBtn,
                    deleteBtn
            ));
         }
      return employees;
    }
}
