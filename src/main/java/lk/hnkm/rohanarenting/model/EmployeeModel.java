/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/31/23, 2:22 PM
 *
 */
package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.Employee;
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

    public static Boolean addEmployee(Employee employee) throws SQLException {
      return  CruidUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                employee.getEID(),
                employee.getFistName(),
                employee.getLastName(),
                employee.getNic(),
                employee.getGender(),
                employee.getDateOfBirth(),
                employee.getMobileNumber(),
                employee.getEmail(),
                employee.getZip(),
                employee.getCity(),
                employee.getStreet(),
                employee.getState(),
                employee.getJoinedDate(),
                employee.getPosition()
                );
    }

    public static Boolean updateEmployee(Employee employee) throws SQLException {
      return  CruidUtil.execute("UPDATE employee SET First_Name=?,Last_Name=?,NIC=?,Gender=?,Date_Of_Birth=?,Mobile_Number=?,Email=?,Zip=?,City=?,Street=?,State=?,Joined_Date=?,Position=? WHERE EID=?",
                employee.getFistName(),
                employee.getLastName(),
                employee.getNic(),
                employee.getGender(),
                employee.getDateOfBirth(),
                employee.getMobileNumber(),
                employee.getEmail(),
                employee.getZip(),
                employee.getCity(),
                employee.getStreet(),
                employee.getState(),
                employee.getJoinedDate(),
                employee.getPosition(),
                employee.getEID()
        );
    }

    public static Employee getEmployee(String employeeID) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM employee WHERE EID=?",employeeID);
        if(resultSet.next()){
          return new Employee(
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
                    new JFXButton("Edit"),
                    new JFXButton("Delete")
            ));
        }
        return employees;
    }
}
