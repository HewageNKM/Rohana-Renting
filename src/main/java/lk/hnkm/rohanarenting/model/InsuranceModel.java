/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/5/23, 6:56 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.Insurance;
import lk.hnkm.rohanarenting.dto.tm.InsuranceTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsuranceModel {
    public static Insurance getVehicleInsurance(String IID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID = ?", IID);
        if (resultSet.next()) {
            return new Insurance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDate(9).toLocalDate(),
                    resultSet.getDate(10).toLocalDate()
            );
        } else {
            return null;
        }

    }

    public static Insurance getToolInsurance(String IID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID = ?", IID);
        if (resultSet.next()) {
            return new Insurance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDate(9).toLocalDate(),
                    resultSet.getDate(10).toLocalDate()
            );
        } else {
            return null;
        }
    }

    public static Boolean isVehicleInsuranceDetailsExist(String IID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID = ?", IID);
        return resultSet.next();
    }

    public static Boolean updateVehicleInsuranceDetails(Insurance insurance) throws SQLException {
        return CruidUtil.execute("UPDATE vehicle_insurance SET Name =? Provider = ? ,Agent_Name = ? , Agent_Contact = ? , Email = ? , Address = ? , Fax = ? , Joined_Date = ? ,Expire_Date = ? WHERE IID = ?", insurance.getName(), insurance.getInsuranceProvider(), insurance.getAgentName(), insurance.getAgentContact(), insurance.getEmail(), insurance.getAddress(), insurance.getFax(), insurance.getJoinedDate(), insurance.getExpireDate(), insurance.getIID());
    }

    public static Boolean addVehicleInsuranceDetails(Insurance insurance) throws SQLException {
        return CruidUtil.execute("INSERT INTO vehicle_insurance VALUES(?,?,?,?,?,?,?,?,?,?)", insurance.getIID(), insurance.getName(), insurance.getInsuranceProvider(), insurance.getAgentName(), insurance.getAgentContact(), insurance.getEmail(), insurance.getAddress(), insurance.getFax(), insurance.getJoinedDate(), insurance.getExpireDate());
    }

    public static Boolean isToolInsuranceDetailsExist(String IID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID = ?", IID);
        return resultSet.next();
    }

    public static Boolean addToolInsuranceDetails(Insurance insurance) throws SQLException {
        return CruidUtil.execute("INSERT INTO tool_insurance VALUES(?,?,?,?,?,?,?,?,?,?)", insurance.getIID(), insurance.getName(), insurance.getInsuranceProvider(), insurance.getAgentName(), insurance.getAgentContact(), insurance.getEmail(), insurance.getAddress(), insurance.getFax(), insurance.getJoinedDate(), insurance.getExpireDate());
    }

    public static Boolean updateToolInsuranceDetails(Insurance insurance) throws SQLException {
        return CruidUtil.execute("UPDATE tool_insurance SET Name =?,Provider = ? ,Agent_Name = ? , Agent_Contact = ? , Email = ? , Address = ? , Fax = ? , Joined_Date = ? ,Expire_Date = ? WHERE IID = ?", insurance.getName(), insurance.getInsuranceProvider(), insurance.getAgentName(), insurance.getAgentContact(), insurance.getEmail(), insurance.getAddress(), insurance.getFax(), insurance.getJoinedDate(), insurance.getExpireDate(), insurance.getIID());
    }

    public static ArrayList<InsuranceTM> getAllInsurance() throws SQLException {
        ResultSet vehicleResultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance");
        ArrayList<InsuranceTM> insuranceTMS = new ArrayList<>();
        while (vehicleResultSet.next()) {
            insuranceTMS.add(new InsuranceTM(
                        vehicleResultSet.getString(1),
                        vehicleResultSet.getString(2),
                        vehicleResultSet.getString(3),
                        vehicleResultSet.getString(4),
                        vehicleResultSet.getString(5),
                        vehicleResultSet.getString(6),
                        vehicleResultSet.getString(7),
                        vehicleResultSet.getString(8),
                        vehicleResultSet.getDate(9).toLocalDate(),
                        vehicleResultSet.getDate(10).toLocalDate(),
                        new JFXButton("Update")
            ));
        }
        ResultSet toolResultSet = CruidUtil.execute("SELECT * FROM tool_insurance");
        while (toolResultSet.next()) {
            insuranceTMS.add(new InsuranceTM(
                    toolResultSet.getString(1),
                    toolResultSet.getString(2),
                    toolResultSet.getString(3),
                    toolResultSet.getString(4),
                    toolResultSet.getString(5),
                    toolResultSet.getString(6),
                    toolResultSet.getString(7),
                    toolResultSet.getString(8),
                    toolResultSet.getDate(9).toLocalDate(),
                    toolResultSet.getDate(10).toLocalDate(),
                    new JFXButton("Update")
            ));
        }
        return insuranceTMS;
    }

    public static boolean isToolIdExit(String tid) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM tool WHERE TID = ?", tid);
        return resultSet.next();
    }

    public static boolean isVehicleIdExists(String vid) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID = ?", vid);
       return resultSet.next();
    }
}