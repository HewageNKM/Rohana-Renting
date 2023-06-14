package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.dto.tm.RefundOrderTM;
import lk.ijse.rohanarenting.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RefundDAO extends CruidDAO<Refund> {
    Boolean verifyVehicleRentId(String rentId) throws SQLException;
    boolean checkVehicleReturn(String rentID) throws SQLException;
    ArrayList<RefundOrderTM> getRefundOrderTM(String rentId) throws SQLException;
    Boolean verifyToolRentId(String rentID) throws SQLException;
    boolean checkToolReturn(String rentID) throws SQLException;
    boolean isRefundIdExist(String id) throws SQLException;
    boolean updateToolRefundTable(Refund refund) throws SQLException;
    boolean updateToolRefundDetailTable(ArrayList<RefundDetails> refundDetails) throws SQLException;
    boolean updateToolRentTable(ArrayList<Tool> tools) throws SQLException;
    boolean updateToolTable(ArrayList<Tool> tools) throws SQLException;
    boolean updateVehicleRefundTable(Refund refund) throws SQLException;
    boolean updateVehicleRefundDetailTable(ArrayList<RefundDetails> refundDetails) throws SQLException;
    boolean updateVehicleRentTable(ArrayList<Vehicle> vehicles) throws SQLException;
    boolean updateVehicleTable(ArrayList<Vehicle> vehicles) throws SQLException;
    Customer getCustomer(String rentId) throws SQLException;
}
