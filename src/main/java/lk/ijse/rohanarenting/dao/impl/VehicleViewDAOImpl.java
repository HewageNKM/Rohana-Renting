package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.VehicleViewDAO;
import lk.ijse.rohanarenting.entity.Vehicle;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleViewDAOImpl implements VehicleViewDAO {
    @Override
    public boolean insert(Vehicle entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean update(Vehicle entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean delete(Vehicle entity) throws SQLException {
        return false;
    }

    @Override
    public Vehicle get(Vehicle entity) throws SQLException, NoSuchAlgorithmException {
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
        return false;
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
}
