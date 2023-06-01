/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/31/23, 2:22 PM
 *
 */
package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.CustomerDTO;
import lk.hnkm.rohanarenting.dto.tm.CustomerTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public static Boolean isIdExist(String id) throws SQLException {
        ResultSet resultSet= CruidUtil.execute("SELECT * FROM customer WHERE CID = ?", id);
        return !resultSet.next();
    }

    public static Boolean addCustomer(CustomerDTO customerDTO) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE NIC = ?", customerDTO.getNIC());
        if(resultSet.next()){
            return false;
        }else {
            return CruidUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?)", customerDTO.getCID(), customerDTO.getFistName(), customerDTO.getLastName(), customerDTO.getNIC(), customerDTO.getBirthday(), customerDTO.getMobileNumber(), customerDTO.getEmail(), customerDTO.getStreet(), customerDTO.getCity(), customerDTO.getZipCode());
        }
    }

    public static Boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CruidUtil.execute("UPDATE customer SET First_Name=?,Last_Name=?,NIC=?,Birthday=?,Mobile_Number=?,Email=?,Street=?,City=?,Zip_Code=? WHERE CID=?",
                customerDTO.getFistName(),
                customerDTO.getLastName(),
                customerDTO.getNIC(),
                customerDTO.getBirthday(),
                customerDTO.getMobileNumber(),
                customerDTO.getEmail(),
                customerDTO.getStreet(),
                customerDTO.getCity(),
                customerDTO.getZipCode(),
                customerDTO.getCID());
    }

    public static CustomerDTO getCustomer(String customerId) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM customer WHERE CID=?",customerId);
        if(resultSet.next()){
            return new CustomerDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getInt(10)
            );
        }
        return null;
    }

    public static boolean deleteCustomer(String EmployeeId) throws SQLException {
        return CruidUtil.execute("DELETE FROM customer WHERE CID=?",EmployeeId);
    }

    public static ArrayList<CustomerTM> getAllCustomers() throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer");
       ArrayList<CustomerTM> arrayList = new ArrayList<>();
        while (resultSet.next()){
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            arrayList.add(new CustomerTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getInt(10),
                    editBtn,
                    deleteBtn,
                    showBtn

            ));
        }
        return arrayList;
    }

    public static ArrayList<CustomerTM> searchCustomer(String searchPhrase) throws SQLException {
        ArrayList<CustomerTM> arrayList = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE CID LIKE ? OR First_Name LIKE ? OR Last_Name LIKE ? OR NIC LIKE ? OR Mobile_Number LIKE ? OR Email LIKE ? OR Street LIKE ? OR City LIKE ? OR Zip_Code LIKE ? OR Birthday LIKE ?",
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase);
        while (resultSet.next()){
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            arrayList.add(new CustomerTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getInt(10),
                    editBtn,
                    deleteBtn,
                    showBtn

            ));
        }
        return arrayList;
    }
}
