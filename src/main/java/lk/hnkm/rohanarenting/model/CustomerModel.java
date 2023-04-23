/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/31/23, 2:22 PM
 *
 */
package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.Customer;
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

    public static Boolean addCustomer(Customer customer) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE NIC = ?",customer.getNIC());
        if(resultSet.next()){
            return false;
        }else {
            return CruidUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?)",customer.getCID(),customer.getFistName(),customer.getLastName(),customer.getNIC(),customer.getBirthday(),customer.getMobileNumber(),customer.getEmail(),customer.getStreet(),customer.getCity(),customer.getZipCode());
        }
    }

    public static Boolean updateCustomer(Customer customer) throws SQLException {
        return CruidUtil.execute("UPDATE customer SET First_Name=?,Last_Name=?,NIC=?,Birthday=?,Mobile_Number=?,Email=?,Street=?,City=?,Zip_Code=? WHERE CID=?",
                customer.getFistName(),
                customer.getLastName(),
                customer.getNIC(),
                customer.getBirthday(),
                customer.getMobileNumber(),
                customer.getEmail(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZipCode(),
                customer.getCID());
    }

    public static Customer getCustomer(String customerId) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM customer WHERE CID=?",customerId);
        if(resultSet.next()){
            return new Customer(
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
                    new JFXButton("Edit"),
                    new JFXButton("Delete")
            ));
        }
        return arrayList;
    }
}
