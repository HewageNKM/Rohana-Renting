package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.RentDAO;
import lk.ijse.rohanarenting.entity.*;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class RentDAOImpl implements RentDAO {
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
    public boolean verifyCustomerId(String CID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE CID = ?",CID);
        return resultSet.next();
    }

    @Override
    public boolean verifyVehicleRentId(String rentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Rent_ID FROM vehicle_rent_order WHERE Rent_ID = ?",rentId);
        return resultSet.next();
    }

    @Override
    public boolean verifyToolRentId(String rentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Rent_ID FROM vehicle_rent_order WHERE Rent_ID = ?",rentId);
        return resultSet.next();
    }

    @Override
    public boolean verifyVehicleId(String vehicleId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID = ?",vehicleId);
        return  resultSet.next();
    }

    @Override
    public boolean verifyToolId(String toolId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool WHERE TID = ?",toolId);
        return  resultSet.next();
    }

    @Override
    public Vehicle getVehicleByAvailability(Vehicle entity) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID=? AND (Availability = 'Available')", entity.getVehicleID());
        if (resultSet.next()) {
            return new Vehicle(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6),resultSet.getString(7));
        } else {
            return null;
        }
    }

    @Override
    public Tool getToolByAvailability(Tool entity) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool WHERE TID=? AND (Availability = 'Available')", entity.getToolID());
        if (resultSet.next()) {
            return new Tool(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6));
        } else {
            return null;
        }
    }

    @Override
    public boolean updateVehicleRentOrderTable(VehicleOrder entity) throws SQLException {
        return CruidUtil.execute("INSERT INTO vehicle_rent_order VALUES (?,?,?,?)", entity.getRentId(), entity.getCustomerId(), entity.getDate(), entity.getTime());

    }

    @Override
    public boolean addVehicleRentOrderDetailTable(ArrayList<VehicleOrderDetails> entities) throws SQLException {
        int count=0;
        for (VehicleOrderDetails vehicleOrderDetail:entities) {
            Boolean b = CruidUtil.execute("INSERT INTO vehicle_rent_order_detail VALUES (?,?,?,?,?,?,?,?,?,?,?)",vehicleOrderDetail.getRentId(),vehicleOrderDetail.getVehicleId(),vehicleOrderDetail.getManufacturer(),vehicleOrderDetail.getModel(),vehicleOrderDetail.getCategory(),vehicleOrderDetail.getRentDays(),vehicleOrderDetail.getRate(),vehicleOrderDetail.getTotal(),vehicleOrderDetail.getReturnDate(),0,0);
            if(b) {
                count++;
            }
        }
        return count == entities.size();
    }

    @Override
    public boolean updateVehicleTable(ArrayList<Vehicle> entities) throws SQLException {
        int count=0;
        for (Vehicle vehicle:entities) {
            Boolean b = CruidUtil.execute("UPDATE vehicle SET Availability = ? WHERE VID = ?",vehicle.getAvailability(),vehicle.getVehicleID());
            if(b) {
                count++;
            }
        }
        return count == entities.size();
    }

    @Override
    public boolean updateToolRentOrderTable(ToolOrder entity) throws SQLException {
        return CruidUtil.execute("INSERT INTO tool_rent_order VALUES (?,?,?,?)", entity.getRentId(), entity.getCustomerId(),LocalDate.now(),LocalTime.now());
    }

    @Override
    public boolean updateToolDetailsTable(ArrayList<ToolOrderDetails> entities) throws SQLException {
        int count=0;
        for (ToolOrderDetails toolOrderDetails:entities) {
            Boolean b = CruidUtil.execute("INSERT INTO tool_rent_order_detail VALUES (?,?,?,?,?,?,?,?,?,?)",toolOrderDetails.getRentId(),toolOrderDetails.getToolId(),toolOrderDetails.getBrand(),toolOrderDetails.getName(),toolOrderDetails.getRentDays(),toolOrderDetails.getRate(),toolOrderDetails.getTotal(),LocalDate.now().plusDays(toolOrderDetails.getRentDays()),0,0);
            if(b){
                count++;
            }
        }
        if(count == entities.size()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateToolTable(ArrayList<Tool> entities) throws SQLException {
        int count=0;
        for (Tool tool:entities) {
            Boolean b = CruidUtil.execute("UPDATE tool SET Availability = ? WHERE TID = ?",tool.getToolID(),tool.getAvailability());
            if(b){
                count++;
            }
        }
        return count == entities.size();
    }

    @Override
    public Customer getCustomer(String customerId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE CID=?", customerId);
        if(resultSet.next()){
            return new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5).toLocalDate(),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getInt(10));
        }else {
            return null;
        }
    }

    @Override
    public void checkInsuranceTable() throws SQLException {
        ArrayList<String> vehicleIds = new ArrayList<>();
        ArrayList<String> toolIds = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT VID FROM vehicle LEFT JOIN vehicle_insurance vi on vehicle.VID = vi.IID WHERE vi.Expire_Date < CURDATE() OR vi.Expire_Date IS NULL;");
        while (resultSet.next()){
            vehicleIds.add(resultSet.getString(1));
        }
        for (String vehicleId:vehicleIds) {
            CruidUtil.execute("UPDATE vehicle SET Availability = 'Not Available' WHERE VID = ?", vehicleId);
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT TID FROM tool LEFT JOIN tool_insurance ti on tool.TID = ti.IID WHERE ti.Expire_Date < CURDATE() OR ti.Expire_Date IS NULL;");
        while (resultSet1.next()){
            toolIds.add(resultSet1.getString(1));
        }
        for (String toolId:toolIds) {
            CruidUtil.execute("UPDATE tool SET Availability = 'Not Available' WHERE TID = ?", toolId);
        }
    }

    @Override
    public boolean checkValidVehicleInsurance(Vehicle entity,Integer rentDays) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Expire_Date FROM vehicle_insurance WHERE IID = ?",entity.getVehicleID());
        if (resultSet.next()){
            return !resultSet.getDate(1).toLocalDate().isAfter(LocalDate.now().plusDays(rentDays));
        }else {
            return true;
        }
    }

    @Override
    public boolean checkValidToolInsurance(Tool entity,Integer rentDays) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Expire_Date FROM tool_insurance WHERE IID = ?",entity.getToolID());
        if (resultSet.next()){
            return !resultSet.getDate(1).toLocalDate().isAfter(LocalDate.now().plusDays(rentDays ));
        }else {
            return true;
        }
    }
}
