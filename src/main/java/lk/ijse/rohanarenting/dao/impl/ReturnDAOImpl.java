package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.ReturnDAO;
import lk.ijse.rohanarenting.dto.tm.ReturnOrderTM;
import lk.ijse.rohanarenting.dto.tm.ReturnTM;
import lk.ijse.rohanarenting.entity.Return;
import lk.ijse.rohanarenting.entity.ReturnDetails;
import lk.ijse.rohanarenting.entity.Tool;
import lk.ijse.rohanarenting.entity.Vehicle;
import lk.ijse.rohanarenting.utill.CruidUtil;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ReturnDAOImpl implements ReturnDAO {
    @Override
    public boolean insert(Object entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean update(Object entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean delete(Object entity) throws SQLException {
        return false;
    }

    @Override
    public Object get(Object entity) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public ArrayList getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean verify(Object entity) throws SQLException, NoSuchAlgorithmException {
        return false;
    }

    @Override
    public ArrayList search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public boolean verifyVehicleRentId(String id) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_rent_order_detail WHERE Rent_ID=?",id);
        return resultSet.next();
    }

    @Override
    public ReturnTM getReturnTM(ReturnOrderTM returnOrderTM, String returnId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Rate_Per_Day FROM vehicle WHERE VID=?", returnOrderTM.getProductId());
        double rate = 0.0;
        if(resultSet.next()){
            rate = resultSet.getDouble(1);
        }
        Period period = Period.between(returnOrderTM.getReturnDate(),LocalDate.now());
        double fine = rate*period.getDays()*2;
        if (fine>0) {
            return new ReturnTM(returnId, returnOrderTM.getProductId(), returnOrderTM.getReturnDate(),LocalDate.now(),period.getDays(),fine);
        }else {
            return new ReturnTM(returnId, returnOrderTM.getProductId(), returnOrderTM.getReturnDate(),LocalDate.now(),0,0.0);
        }
    }

    @Override
    public boolean isReturnIdExist(String id) throws SQLException {
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

    @Override
    public boolean verifyToolRentID(String toolRentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool_rent_order WHERE Rent_ID = ?",toolRentId);
        return resultSet.next();
    }

    @Override
    public ArrayList<ReturnOrderTM> getOrder(String rentId) throws SQLException {
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

    @Override
    public boolean saveVehicleReturn(Return entity) throws SQLException {
        return CruidUtil.execute("INSERT IGNORE INTO vehicle_return VALUES(?,?,?,?)",entity.getReturnId(),entity.getRentId(),entity.getReturnDate(),entity.getReturnTime());
    }

    @Override
    public boolean saveToolReturn(Return entity) throws SQLException {
        return CruidUtil.execute("INSERT IGNORE INTO tool_return VALUES(?,?,?,?)",entity.getReturnId(),entity.getRentId(),entity.getReturnDate(),entity.getReturnTime());
    }

    @Override
    public boolean saveToolReturnDetails(ArrayList<ReturnDetails> returnList) throws SQLException {
        int count = 0;
        for (ReturnDetails returnTM:returnList) {
            Boolean isInserted = CruidUtil.execute("INSERT INTO tool_return_detail VALUES(?,?,?,?,?,?)",returnTM.getReturnId(),returnTM.getId(),returnTM.getReturnDate(),returnTM.getReturnedDate(),returnTM.getLateDays(),returnTM.getLateFee());
            if (isInserted){
                count++;
            }
        }
        return count == returnList.size();
    }

    @Override
    public boolean updateToolRent(ArrayList<Tool> tools, String rentId) throws SQLException {
        int count = 0;
        for (Tool tool:tools) {
            Boolean isUpdated = CruidUtil.execute("UPDATE tool_rent_order_detail SET Return_Status = 1 WHERE Rent_ID = ? AND TID = ?",rentId,tool.getToolID());
            if (isUpdated){
                count++;
            }
        }
        return count == tools.size();
    }

    @Override
    public boolean saveVehicleReturnDetails(ArrayList<ReturnDetails> returnDetailsList) throws SQLException {
        int count = 0;
        for (ReturnDetails returnTM:returnDetailsList) {
            Boolean isInserted = CruidUtil.execute("INSERT INTO vehicle_return_detail VALUES(?,?,?,?,?,?)",returnTM.getReturnId(),returnTM.getId(),returnTM.getReturnDate(),returnTM.getReturnedDate(),returnTM.getLateDays(),returnTM.getLateFee());
            if (isInserted){
                count++;
            }
        }
        return count == returnDetailsList.size();
    }

    @Override
    public boolean updateVehicleRent(ArrayList<Vehicle> vehicles, String rentId) throws SQLException {
        int count = 0;
        for (Vehicle vehicle:vehicles) {
            Boolean isUpdated = CruidUtil.execute("UPDATE vehicle_rent_order_detail SET Return_Status = 1 WHERE Rent_ID = ? AND VID = ?",rentId,vehicle.getVehicleID());
            if (isUpdated){
                count++;
            }
        }
        return count == vehicles.size();
    }

    @Override
    public boolean updateTool(ArrayList<Tool> tools) throws SQLException {
        int count = 0;
        for (Tool returnTM:tools) {
            Boolean isUpdated  = CruidUtil.execute("UPDATE tool SET Availability = 'Available' WHERE TID = ?",returnTM.getToolID());
            if(isUpdated){
                count++;
            }
        }
        return count == tools.size();
    }

    @Override
    public boolean updateVehicle(ArrayList<Vehicle> vehicles) throws SQLException {
        int count = 0;
        for (Vehicle returnTM:vehicles) {
            Boolean isUpdated  = CruidUtil.execute("UPDATE vehicle SET Availability = 'Available' WHERE VID = ?",returnTM.getVehicleID());
            if(isUpdated){
                count++;
            }
        }
        return count == vehicles.size();
    }

    @Override
    public boolean checkRefund(String rentId) throws SQLException {
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
