/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/16/23, 2:01 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import javafx.collections.ObservableList;
import lk.hnkm.rohanarenting.dto.tm.RefundOrderTM;
import lk.hnkm.rohanarenting.dto.tm.RefundTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;
import lk.hnkm.rohanarenting.utill.Regex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;

public class RefundModel {
    public static Boolean verifyVehicleRentId(String rentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Date FROM vehicle_rent_order WHERE Rent_ID=?",rentId);
        if(resultSet.next()){
            LocalDate date = resultSet.getDate(1).toLocalDate();
            Period period = Period.between(date,LocalDate.now());
            int days = period.getDays();
            return (days >= 0) && (days <= 1);
        }else {
            return null;
        }
    }

    public static Boolean checkVehicleReturn(String rentID) throws SQLException {
        ResultSet resultSet = null;
        if (Regex.validateToolRentId(rentID)){
            resultSet = CruidUtil.execute("SELECT * FROM tool_rent_order_detail WHERE Rent_ID = ? AND Return_Status = 0",rentID);
        } else if (Regex.validateVehicleRentId(rentID)) {
            resultSet = CruidUtil.execute("SELECT * FROM vehicle_rent_order_detail WHERE Rent_ID = ? AND Return_Status = 0",rentID);
        }
        return !resultSet.next();
    }

    public static ArrayList<RefundOrderTM> getRefundOrderTM(String rentId) throws SQLException {
        if(Regex.validateVehicleRentId(rentId)){
            ResultSet resultSet = CruidUtil.execute("SELECT vehicle_rent_order_detail.VID,vehicle_rent_order_detail.Rent_Days,vehicle_rent_order_detail.Total,vehicle_rent_order.Date FROM vehicle_rent_order_detail RIGHT JOIN vehicle_rent_order ON vehicle_rent_order_detail.Rent_ID = vehicle_rent_order.Rent_ID = vehicle_rent_order_detail.Rent_ID = vehicle_rent_order.Rent_ID WHERE vehicle_rent_order.Rent_ID = ? AND vehicle_rent_order_detail.Return_Status = 0 AND vehicle_rent_order_detail.Refund_Status = 0",rentId);
            ArrayList<RefundOrderTM> refundOrderTMS = new ArrayList<>();
            while (resultSet.next()) {
                String productId = resultSet.getString(1);
                int rentDays = resultSet.getInt(2);
                double total = resultSet.getDouble(3);
                LocalDate date = resultSet.getDate(4).toLocalDate();
                refundOrderTMS.add(new RefundOrderTM(productId,rentDays,total,date));
            }
            return refundOrderTMS;
        }else {
            ResultSet resultSet = CruidUtil.execute("SELECT tool_rent_order_detail.TID,tool_rent_order_detail.Rent_Days,tool_rent_order_detail.Total,tool_rent_order.Date FROM tool_rent_order_detail RIGHT JOIN tool_rent_order ON tool_rent_order_detail.Rent_ID = tool_rent_order.Rent_ID = tool_rent_order_detail.Rent_ID = tool_rent_order.Rent_ID WHERE tool_rent_order.Rent_ID = ? AND tool_rent_order_detail.Return_Status = 0 AND tool_rent_order_detail.Refund_Status = 0;",rentId);
            ArrayList<RefundOrderTM> refundOrderTMS = new ArrayList<>();
            while (resultSet.next()) {
                String productId = resultSet.getString(1);
                int rentDays = resultSet.getInt(2);
                double total = resultSet.getDouble(3);
                LocalDate date = resultSet.getDate(4).toLocalDate();
                refundOrderTMS.add(new RefundOrderTM(productId,rentDays,total,date));
            }
            return refundOrderTMS;
        }
    }

    public static RefundTM getRefundTM(RefundOrderTM refundOrderTM) throws SQLException {
        return new RefundTM(refundOrderTM.getProductId(),refundOrderTM.getRentDays(),refundOrderTM.getTotal(),refundOrderTM.getTotal()*0.95);
    }

    public static Boolean verifyToolRentId(String rentID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Date FROM tool_rent_order WHERE Rent_ID=?",rentID);
        if(resultSet.next()){
            LocalDate date = resultSet.getDate(1).toLocalDate();
            Period period = Period.between(date,LocalDate.now());
            int days = period.getDays();
            return (days >= 0) && (days <= 1);
        }else {
            return null;
        }
    }

    public static boolean checkToolReturn(String rentID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_return WHERE Return_ID = ?",rentID);
        return resultSet.next();
    }

    public static boolean isRefundIdExist(String id) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_refund WHERE Refund_ID=?",id);
        if(resultSet.next()){
            ResultSet resultSet1 =  CruidUtil.execute("SELECT * FROM tool_refund WHERE Refund_ID = ?",id);
            if(resultSet1.next()){
                return true;
            }else {
                return true;
            }
        }else {
            ResultSet resultSet1 =  CruidUtil.execute("SELECT * FROM tool_refund WHERE Refund_ID = ?",id);
            if(resultSet1.next()){
                return true;
            }else {
                return false;
            }
        }
    }

    public static Boolean updateToolRefundTable(String refundId,String rentId) throws SQLException {
        return CruidUtil.execute("INSERT INTO tool_refund VALUES(?,?,?,?)",refundId,rentId,LocalDate.now(), LocalTime.now());
    }

    public static Boolean updateToolRefundDetailTable(ObservableList<RefundTM> refundTMS,String refundId) throws SQLException {
        int count = 0;
        for (RefundTM refundTM : refundTMS) {
          Boolean isUpdated = CruidUtil.execute("INSERT INTO tool_refund_detail VALUES(?,?,?,?)",refundId,refundTM.getProductId(),refundTM.getTotal(),refundTM.getRefundAmount());
           if(isUpdated){
                count++;
           }
        }
        return count == refundTMS.size();
    }

    public static Double getTotal(ObservableList<RefundTM> refundTMS) {
        Double total = 0.0;
        for (RefundTM refundTM : refundTMS) {
            total += refundTM.getRefundAmount();
        }
        return total;
    }

    public static Boolean updateToolRentTable(ObservableList<RefundTM> refundTMS) throws SQLException {
        int count = 0;
        for (RefundTM refundTM:refundTMS) {
            Boolean isUpdated = CruidUtil.execute("UPDATE tool_rent_order_detail SET Refund_Status = 1 WHERE TID = ?",refundTM.getProductId());
            if(isUpdated){
                count++;
            }
        }
        return count == refundTMS.size();
    }

    public static Boolean updateToolTable(ObservableList<RefundTM> refundTMS) throws SQLException {
        int count = 0;
        for (RefundTM refundTM:refundTMS) {
            Boolean isUpdated = CruidUtil.execute("UPDATE tool SET Availability = 1 WHERE TID = ?",refundTM.getProductId());
            if(isUpdated){
                count++;
            }
        }
        return count == refundTMS.size();
    }

    public static Boolean updateVehicleRefundTable(String refundId, String rentId) throws SQLException {
        return CruidUtil.execute("INSERT INTO vehicle_refund VALUES(?,?,?,?)",refundId,rentId,LocalDate.now(), LocalTime.now());
    }

    public static Boolean updateVehicleRefundDetailTable(ObservableList<RefundTM> refundTMS, String refundId) throws SQLException {
        int count = 0;
        for (RefundTM refundTM : refundTMS) {
          Boolean isUpdated = CruidUtil.execute("INSERT INTO vehicle_refund_detail VALUES(?,?,?,?)",refundId,refundTM.getProductId(),refundTM.getTotal(),refundTM.getRefundAmount());
          if(isUpdated){
              count++;
          }
        }
        return count == refundTMS.size();
    }

    public static Boolean updateVehicleRentTable(ObservableList<RefundTM> refundTMS) throws SQLException {
        int count = 0;
        for (RefundTM refundTM:refundTMS) {
            Boolean isUpdated = CruidUtil.execute("UPDATE vehicle_rent_order_detail SET Refund_Status = 1 WHERE VID = ?",refundTM.getProductId());
            if(isUpdated){
                count++;
            }
        }
        return count == refundTMS.size();
    }

    public static Boolean updateVehicleTable(ObservableList<RefundTM> refundTMS) throws SQLException {
        int count = 0;
        for (RefundTM refundTM:refundTMS) {
            Boolean isUpdated = CruidUtil.execute("UPDATE vehicle SET Availability = 1 WHERE VID = ?",refundTM.getProductId());
            if(isUpdated){
                count++;
            }
        }
        return count == refundTMS.size();
    }
}
