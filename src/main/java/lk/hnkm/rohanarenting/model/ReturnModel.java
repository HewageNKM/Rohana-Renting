/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/14/23, 9:32 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import javafx.collections.ObservableList;
import lk.hnkm.rohanarenting.dto.tm.ReturnOrderTM;
import lk.hnkm.rohanarenting.dto.tm.ReturnTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;
import lk.hnkm.rohanarenting.utill.Regex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;

public class ReturnModel {

    public static ArrayList<ReturnOrderTM> getVehicleOrderTM(String id) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT Rent_ID,VID,Return_Date FROM vehicle_rent_order_detail WHERE Rent_ID=? AND Return_Status = 0 AND Refund_Status = 0",id);
       ArrayList<ReturnOrderTM> returnOrderTMS = new ArrayList<>();
       while (resultSet.next()){
           returnOrderTMS.add(new ReturnOrderTM(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3).toLocalDate()));
       }
       return returnOrderTMS;
    }

    public static boolean verifyVehicleRentId(String id) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_rent_order_detail WHERE Rent_ID=?",id);
       return resultSet.next();
    }

    public static ReturnTM getReturnTM(ReturnOrderTM returnOrderTM, String returnId) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT Rate_Per_Day FROM vehicle WHERE VID=?", returnOrderTM.getProductId());
       Double rate = 0.0;
        if(resultSet.next()){
            rate = resultSet.getDouble(1);
        }
        Period period = Period.between(returnOrderTM.getReturnDate(),LocalDate.now());
        Double fine = rate*period.getDays()*2;
        if (fine>0) {
            return new ReturnTM(returnId, returnOrderTM.getProductId(), returnOrderTM.getReturnDate(),LocalDate.now(),period.getDays(),fine);
        }else {
            return new ReturnTM(returnId, returnOrderTM.getProductId(), returnOrderTM.getReturnDate(),LocalDate.now(),0,0.0);
        }
    }

    public static boolean isReturnIdExist(String id) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_return WHERE Return_ID=?",id);
        if(resultSet.next()){
            ResultSet resultSet1 =  CruidUtil.execute("SELECT * FROM tool_return WHERE Return_ID = ?",id);
            if(resultSet1.next()){
                return true;
            }else {
                return true;
            }
        }else {
            ResultSet resultSet1 =  CruidUtil.execute("SELECT * FROM tool_return WHERE Return_ID = ?",id);
            if(resultSet1.next()){
                return true;
            }else {
                return false;
            }
        }
    }

    public static boolean verifyToolRentID(String toolRentId) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_rent_order WHERE Rent_ID = ?",toolRentId);
       return resultSet.next();
    }

    public static ArrayList<ReturnOrderTM> getOrderTM(String rentId) throws SQLException {
        ArrayList<ReturnOrderTM> returnOrderTMS = new ArrayList<>();
        if(Regex.validateVehicleRentId(rentId)){
            ResultSet resultSet = CruidUtil.execute("SELECT Rent_ID,VID,Return_Date FROM vehicle_rent_order_detail WHERE Rent_ID=? AND Return_Status = 0 AND Refund_Status = 0",rentId);
            while (resultSet.next()){
                returnOrderTMS.add(new ReturnOrderTM(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3).toLocalDate()));
            }
        }else if(Regex.validateToolRentId(rentId)){
            ResultSet resultSet = CruidUtil.execute("SELECT Rent_ID,TID,Return_Date FROM tool_rent_order_detail WHERE Rent_ID= ? AND Return_Status = 0 AND Refund_Status = 0", rentId);
            while (resultSet.next()){
                returnOrderTMS.add(new ReturnOrderTM(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3).toLocalDate()));
            }
        }
        return returnOrderTMS;
    }

    public static Boolean saveVehicleReturn(String returnId,String rentId) throws SQLException {
        return CruidUtil.execute("INSERT IGNORE INTO vehicle_return VALUES(?,?,?,?)",returnId,rentId,LocalDate.now(),LocalTime.now());
    }

    public static Boolean saveToolReturn(String returnId,String rentId) throws SQLException {
       return CruidUtil.execute("INSERT IGNORE INTO tool_return VALUES(?,?,?,?)",returnId,rentId,LocalDate.now(),LocalTime.now());
    }

    public static Boolean saveToolReturnDetails(ObservableList<ReturnTM> returnList) throws SQLException {
        int count = 0;
        for (ReturnTM returnTM:returnList) {
            Boolean isInserted = CruidUtil.execute("INSERT INTO tool_return_detail VALUES(?,?,?,?,?,?)",returnTM.getReturnId(),returnTM.getProductId(),returnTM.getReturnDate(),returnTM.getReturnedDate(),returnTM.getLateDays(),returnTM.getFine());
            if (isInserted){
                count++;
            }
        }
        return count == returnList.size();
    }

    public static Boolean updateToolRent(ObservableList<ReturnTM> returnList,String rentId) throws SQLException {
        int count = 0;
        for (ReturnTM returnTM:returnList) {
            Boolean isUpdated = CruidUtil.execute("UPDATE tool_rent_order_detail SET Return_Status = 1 WHERE Rent_ID = ? AND TID = ?",rentId,returnTM.getProductId());
            if (isUpdated){
                count++;
            }
        }
        return count == returnList.size();
    }

    public static Boolean saveVehicleReturnDetails(ObservableList<ReturnTM> returnList) throws SQLException {
        int count = 0;
        for (ReturnTM returnTM:returnList) {
           Boolean isInserted = CruidUtil.execute("INSERT INTO vehicle_return_detail VALUES(?,?,?,?,?,?)",returnTM.getReturnId(),returnTM.getProductId(),returnTM.getReturnDate(),returnTM.getReturnedDate(),returnTM.getLateDays(),returnTM.getFine());
           if (isInserted){
               count++;
           }
        }
        return count == returnList.size();
    }

    public static Boolean updateVehicleRent(ObservableList<ReturnTM> returnList, String rentId) throws SQLException {
        int count = 0;
        for (ReturnTM returnTM:returnList) {
            Boolean isUpdated = CruidUtil.execute("UPDATE vehicle_rent_order_detail SET Return_Status = 1 WHERE Rent_ID = ? AND VID = ?",rentId,returnTM.getProductId());
            if (isUpdated){
                count++;
            }
        }
        return count == returnList.size();
    }

    public static Boolean updateTool(ObservableList<ReturnTM> returnList) throws SQLException {
        int count = 0;
        for (ReturnTM returnTM:returnList) {
            Boolean isUpdated  = CruidUtil.execute("UPDATE tool SET Availability = 1 WHERE TID = ?",returnTM.getProductId());
            if(isUpdated){
                count++;
            }
        }
        return count == returnList.size();
    }

    public static Boolean updateVehicle(ObservableList<ReturnTM> returnList) throws SQLException {
        int count = 0;
        for (ReturnTM returnTM:returnList) {
            Boolean isUpdated  = CruidUtil.execute("UPDATE vehicle SET Availability = 1 WHERE VID = ?",returnTM.getProductId());
            if(isUpdated){
                count++;
            }
        }
        return count == returnList.size();
    }

    public static Double getTotalFine(ObservableList<ReturnTM> returnList) {
        Double total = 0.0;
        for (ReturnTM returnTM:returnList) {
            total+=returnTM.getFine();
        }
        return total;
    }

    public static boolean checkRefund(String rentId) throws SQLException {
        ResultSet resultSet = null;
        if(Regex.validateVehicleRentId(rentId)){
            resultSet = CruidUtil.execute("SELECT * FROM vehicle_rent_order_detail WHERE Rent_ID = ? AND Refund_Status = 0", rentId);
        }else if(Regex.validateToolRentId(rentId)){
            resultSet = CruidUtil.execute("SELECT * FROM tool_rent_order_detail WHERE Rent_ID = ? AND Refund_Status = 0", rentId);
        }
        assert resultSet != null;
        return !resultSet.next();
    }
}
