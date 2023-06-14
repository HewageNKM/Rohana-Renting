package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.RefundDAO;
import lk.ijse.rohanarenting.dto.tm.RefundOrderTM;
import lk.ijse.rohanarenting.entity.*;
import lk.ijse.rohanarenting.utill.CruidUtil;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class RefundDAOImpl implements RefundDAO {

    @Override
    public boolean insert(Refund entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean update(Refund entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean delete(Refund entity) throws SQLException {
        return false;
    }

    @Override
    public Refund get(Refund entity) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public ArrayList<Refund> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean verify(Refund entity) throws SQLException, NoSuchAlgorithmException {
        return false;
    }

    @Override
    public ArrayList<Refund> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public Boolean verifyVehicleRentId(String rentId) throws SQLException {
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

    @Override
    public boolean checkVehicleReturn(String rentID) throws SQLException {
        ResultSet resultSet = null;
        if (Regex.validateToolRentId(rentID)){
            resultSet = CruidUtil.execute("SELECT * FROM tool_rent_order_detail WHERE Rent_ID = ? AND Return_Status = 0",rentID);
        } else if (Regex.validateVehicleRentId(rentID)) {
            resultSet = CruidUtil.execute("SELECT * FROM vehicle_rent_order_detail WHERE Rent_ID = ? AND Return_Status = 0",rentID);
        }
        return !resultSet.next();
    }

    @Override
    public ArrayList<RefundOrderTM> getRefundOrderTM(String rentId) throws SQLException {
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

    @Override
    public Boolean verifyToolRentId(String rentID) throws SQLException {
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

    @Override
    public boolean checkToolReturn(String rentID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_return WHERE Return_ID = ?",rentID);
        return resultSet.next();
    }

    @Override
    public boolean isRefundIdExist(String id) throws SQLException {
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

    @Override
    public boolean updateToolRefundTable(Refund refund) throws SQLException {
        return CruidUtil.execute("INSERT INTO tool_refund VALUES(?,?,?,?)", refund.getRefundId(),refund.getRentId(),refund.getDate(), refund.getTime());
    }

    @Override
    public boolean updateToolRefundDetailTable(ArrayList<RefundDetails> refundDetails) throws SQLException {
        int count = 0;
        for (RefundDetails refundDetail : refundDetails) {
            Boolean isUpdated = CruidUtil.execute("INSERT INTO tool_refund_detail VALUES(?,?,?,?)",refundDetail.getRefundId(),refundDetail.getId(),refundDetail.getTotal(),refundDetail.getRefundAmount());
            if(isUpdated){
                count++;
            }
        }
        return count == refundDetails.size();
    }

    @Override
    public boolean updateToolRentTable(ArrayList<Tool> tools) throws SQLException {
        int count = 0;
        for (Tool tool:tools) {
            Boolean isUpdated = CruidUtil.execute("UPDATE tool_rent_order_detail SET Refund_Status = 1,Return_Status = 1 WHERE TID = ?",tool.getToolID());
            if(isUpdated){
                count++;
            }
        }
        return count == tools.size();
    }

    @Override
    public boolean updateToolTable(ArrayList<Tool> tools) throws SQLException {
        int count = 0;
        for (Tool tool:tools) {
            Boolean isUpdated = CruidUtil.execute("UPDATE tool SET Availability = 'Available' WHERE TID = ?",tool.getToolID());
            if(isUpdated){
                count++;
            }
        }
        return count == tools.size();
    }

    @Override
    public boolean updateVehicleRefundTable(Refund refund) throws SQLException {
        return CruidUtil.execute("INSERT INTO vehicle_refund VALUES(?,?,?,?)",refund.getRefundId(),refund.getRentId(),refund.getDate(),refund.getTime());
    }

    @Override
    public boolean updateVehicleRefundDetailTable(ArrayList<RefundDetails> refundDetails) throws SQLException {
        int count = 0;
        for (RefundDetails refundDetail : refundDetails) {
            Boolean isUpdated = CruidUtil.execute("INSERT INTO vehicle_refund_detail VALUES(?,?,?,?)",refundDetail.getRefundId(),refundDetail.getId(),refundDetail.getTotal(),refundDetail.getRefundAmount());
            if(isUpdated){
                count++;
            }
        }
        return count == refundDetails.size();
    }

    @Override
    public boolean updateVehicleRentTable(ArrayList<Vehicle> vehicles) throws SQLException {
        int count = 0;
        for (Vehicle vehicle:vehicles) {
            Boolean isUpdated = CruidUtil.execute("UPDATE vehicle_rent_order_detail SET Refund_Status = 1,Return_Status = 1 WHERE VID = ?",vehicle.getVehicleID());
            if(isUpdated){
                count++;
            }
        }
        return count == vehicles.size();
    }

    @Override
    public boolean updateVehicleTable(ArrayList<Vehicle> vehicles) throws SQLException {
        int count = 0;
        for (Vehicle vehicle:vehicles) {
            Boolean isUpdated = CruidUtil.execute("UPDATE vehicle SET Availability = 'Available' WHERE VID = ?",vehicle.getVehicleID());
            if(isUpdated){
                count++;
            }
        }
        return count == vehicles.size();
    }

    @Override
    public Customer getCustomer(String rentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_rent_order WHERE Rent_ID=?",rentId);
        if(resultSet.next()){
            String customerId = resultSet.getString(2);
            ResultSet resultSet1 = CruidUtil.execute("SELECT * FROM customer WHERE CID=?",customerId);
            if(resultSet1.next()){
                return new Customer(resultSet1.getString(1),resultSet1.getString(2),resultSet1.getString(3),resultSet1.getString(4),resultSet1.getDate(5).toLocalDate(),resultSet1.getString(6),resultSet1.getString(7),resultSet1.getString(8),resultSet1.getString(9),resultSet1.getInt(10));
            }else {
                return new Customer();
            }
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT * FROM vehicle_rent_order WHERE Rent_ID=?",rentId);
        if(resultSet1.next()) {
            String customerId = resultSet1.getString(2);
            ResultSet resultSet2 = CruidUtil.execute("SELECT * FROM customer WHERE CID=?", customerId);
            if (resultSet2.next()) {
                return new Customer(resultSet2.getString(1), resultSet2.getString(2), resultSet2.getString(3), resultSet2.getString(4), resultSet2.getDate(5).toLocalDate(), resultSet2.getString(6), resultSet2.getString(7), resultSet2.getString(8), resultSet2.getString(9), resultSet2.getInt(10));
            } else {
                return new Customer();
            }
        }
        return new Customer();
    }
}
