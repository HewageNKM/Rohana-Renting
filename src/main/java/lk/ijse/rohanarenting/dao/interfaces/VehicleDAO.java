package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.entity.Insurance;
import lk.ijse.rohanarenting.entity.Vehicle;

import java.sql.SQLException;

public interface VehicleDAO extends CruidDAO<Vehicle> {
    Insurance getInsuranceDetails(Vehicle entity) throws SQLException;
    boolean checkOrderStatus(Vehicle entity) throws SQLException;
    boolean updateVehicleWithoutAvailability(Vehicle entity) throws SQLException;
}
