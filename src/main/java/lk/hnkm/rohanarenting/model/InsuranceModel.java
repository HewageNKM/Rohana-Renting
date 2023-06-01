/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/5/23, 6:56 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.InsuranceDTO;
import lk.hnkm.rohanarenting.dto.tm.InsuranceTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsuranceModel {
    public static InsuranceDTO getVehicleInsurance(String IID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID = ?", IID);
        if (resultSet.next()) {
            return new InsuranceDTO(
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

    public static InsuranceDTO getToolInsurance(String IID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID = ?", IID);
        if (resultSet.next()) {
            return new InsuranceDTO(
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

    public static Boolean updateVehicleInsuranceDetails(InsuranceDTO insuranceDTO) throws SQLException {
        return CruidUtil.execute("UPDATE vehicle_insurance SET Name =?, Provider = ? ,Agent_Name = ? , Agent_Contact = ? , Email = ? , Address = ? , Fax = ? , Joined_Date = ? ,Expire_Date = ? WHERE IID = ?", insuranceDTO.getName(), insuranceDTO.getInsuranceProvider(), insuranceDTO.getAgentName(), insuranceDTO.getAgentContact(), insuranceDTO.getEmail(), insuranceDTO.getAddress(), insuranceDTO.getFax(), insuranceDTO.getJoinedDate(), insuranceDTO.getExpireDate(), insuranceDTO.getIID());
    }

    public static Boolean addVehicleInsuranceDetails(InsuranceDTO insuranceDTO) throws SQLException {
        return CruidUtil.execute("INSERT INTO vehicle_insurance VALUES(?,?,?,?,?,?,?,?,?,?)", insuranceDTO.getIID(), insuranceDTO.getName(), insuranceDTO.getInsuranceProvider(), insuranceDTO.getAgentName(), insuranceDTO.getAgentContact(), insuranceDTO.getEmail(), insuranceDTO.getAddress(), insuranceDTO.getFax(), insuranceDTO.getJoinedDate(), insuranceDTO.getExpireDate());
    }

    public static Boolean isToolInsuranceDetailsExist(String IID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID = ?", IID);
        return resultSet.next();
    }

    public static Boolean addToolInsuranceDetails(InsuranceDTO insuranceDTO) throws SQLException {
        return CruidUtil.execute("INSERT INTO tool_insurance VALUES(?,?,?,?,?,?,?,?,?,?)", insuranceDTO.getIID(), insuranceDTO.getName(), insuranceDTO.getInsuranceProvider(), insuranceDTO.getAgentName(), insuranceDTO.getAgentContact(), insuranceDTO.getEmail(), insuranceDTO.getAddress(), insuranceDTO.getFax(), insuranceDTO.getJoinedDate(), insuranceDTO.getExpireDate());
    }

    public static Boolean updateToolInsuranceDetails(InsuranceDTO insuranceDTO) throws SQLException {
        return CruidUtil.execute("UPDATE tool_insurance SET Name =?,Provider = ? ,Agent_Name = ? , Agent_Contact = ? , Email = ? , Address = ? , Fax = ? , Joined_Date = ? ,Expire_Date = ? WHERE IID = ?", insuranceDTO.getName(), insuranceDTO.getInsuranceProvider(), insuranceDTO.getAgentName(), insuranceDTO.getAgentContact(), insuranceDTO.getEmail(), insuranceDTO.getAddress(), insuranceDTO.getFax(), insuranceDTO.getJoinedDate(), insuranceDTO.getExpireDate(), insuranceDTO.getIID());
    }

    public static ArrayList<InsuranceTM> getAllInsurance() throws SQLException {
        ResultSet vehicleResultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance");
        ArrayList<InsuranceTM> insuranceTMS = new ArrayList<>();
        while (vehicleResultSet.next()) {
            JFXButton update = new JFXButton();
            update.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
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
                        update
            ));
        }
        ResultSet toolResultSet = CruidUtil.execute("SELECT * FROM tool_insurance");
        while (toolResultSet.next()) {
            JFXButton update = new JFXButton();
            update.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
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
                    update
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

    public static ArrayList<InsuranceTM> searchInsurance(String searchPhrase) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID LIKE ? OR Name LIKE ? OR Provider LIKE ? OR Agent_Name LIKE ? OR Agent_Contact LIKE ? OR Email LIKE ? OR Address LIKE ? OR Fax LIKE ? OR Joined_Date LIKE ? OR Expire_Date LIKE ?", searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase);
        ArrayList<InsuranceTM> insuranceTMS = new ArrayList<>();
        while (resultSet.next()) {
            JFXButton update = new JFXButton();
            update.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            insuranceTMS.add(new InsuranceTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDate(9).toLocalDate(),
                    resultSet.getDate(10).toLocalDate(),
                    update
            ));
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID LIKE ? OR Name LIKE ? OR Provider LIKE ? OR Agent_Name LIKE ? OR Agent_Contact LIKE ? OR Email LIKE ? OR Address LIKE ? OR Fax LIKE ? OR Joined_Date LIKE ? OR Expire_Date LIKE ?", searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase);
        while (resultSet1.next()){
            JFXButton update = new JFXButton();
            update.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            insuranceTMS.add(new InsuranceTM(
                    resultSet1.getString(1),
                    resultSet1.getString(2),
                    resultSet1.getString(3),
                    resultSet1.getString(4),
                    resultSet1.getString(5),
                    resultSet1.getString(6),
                    resultSet1.getString(7),
                    resultSet1.getString(8),
                    resultSet1.getDate(9).toLocalDate(),
                    resultSet1.getDate(10).toLocalDate(),
                    update
            ));
        }
        return insuranceTMS;
    }
}