package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.VehicleDAO;
import lk.ijse.rohanarenting.entity.Insurance;
import lk.ijse.rohanarenting.entity.Vehicle;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {
    @Override
    public boolean insert(Vehicle entity) throws NoSuchAlgorithmException, SQLException {
        return CruidUtil.execute("INSERT INTO vehicle VALUES (?,?,?,?,?,?,?);", entity.getVehicleID(), entity.getManufacturer(), entity.getModel(), entity.getDescription(), entity.getAvailability(), entity.getRate(), entity.getCategory());
    }

    @Override
    public boolean update(Vehicle entity) throws NoSuchAlgorithmException, SQLException {
        return CruidUtil.execute("UPDATE vehicle SET Manufacturer = ?, Model_Name = ?, Description = ?,Availability = ?,Rate_Per_Day = ?, Category = ? WHERE VID = ?;", entity.getManufacturer(), entity.getModel() , entity.getDescription(), entity.getAvailability(), entity.getRate(), entity.getCategory(), entity.getVehicleID());
    }

    @Override
    public boolean delete(Vehicle entity) throws SQLException {
        return CruidUtil.execute("DELETE FROM vehicle WHERE VID = ?",entity.getVehicleID());
    }

    @Override
    public Vehicle get(Vehicle entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle  WHERE VID = ?;", entity.getVehicleID());
        if(resultSet.next()){
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleID(resultSet.getString(1));
            vehicle.setManufacturer(resultSet.getString(2));
            vehicle.setModel(resultSet.getString(3));
            vehicle.setDescription(resultSet.getString(4));
            vehicle.setAvailability(resultSet.getString(5));
            vehicle.setRate(resultSet.getDouble(6));
            vehicle.setCategory(resultSet.getString(7));
            return vehicle;
        }
        return null;
    }

    @Override
    public ArrayList<Vehicle> getAll() throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle");
        ArrayList <Vehicle> vehicles = new ArrayList<>();
        while (resultSet.next()){
            vehicles.add(new Vehicle(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getString(7)));
        }
        return vehicles;
    }

    @Override
    public boolean verify(Vehicle entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID = ?;",entity.getVehicleID());
        return resultSet.next();
    }

    @Override
    public ArrayList<Vehicle> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID LIKE ? OR Manufacturer LIKE ? OR Model_Name LIKE ? OR Description LIKE ? OR Availability LIKE ? OR Rate_Per_Day LIKE ? OR Category LIKE ?;",searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase);
        ArrayList <Vehicle> vehicles = new ArrayList<>();
        while (resultSet.next()){
            vehicles.add(new Vehicle(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getString(7)));
        }
        return vehicles;
    }
    public boolean checkOrderStatus(Vehicle entity) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_rent_order_detail WHERE VID = ? AND Return_Status = 0 ;",entity.getVehicleID());
        return resultSet.next();
    }
    public boolean updateVehicleWithoutAvailability(Vehicle entity) throws SQLException {
        return CruidUtil.execute("UPDATE vehicle SET Manufacturer = ?, Model_Name = ?, Description = ?, Rate_Per_Day = ?, Category = ? WHERE VID = ?;", entity.getManufacturer(), entity.getModel() , entity.getDescription(), entity.getRate(), entity.getCategory(), entity.getVehicleID());
    }
    public Insurance getInsuranceDetails(Vehicle entity) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID = ?;",entity.getVehicleID());
        if (resultSet.next()){
            return new Insurance(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getDate(9).toLocalDate(),resultSet.getDate(10).toLocalDate());
        }else {
            return new Insurance("No Details","No Details","No Details","No Details","No Details","No Details","No Details","No Details", LocalDate.now(),LocalDate.now());
        }
    }
}
