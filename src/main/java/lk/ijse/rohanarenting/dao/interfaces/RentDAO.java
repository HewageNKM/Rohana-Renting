package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RentDAO extends CruidDAO {
        boolean verifyCustomerId(String CID) throws SQLException;
        boolean verifyVehicleRentId(String rentId) throws SQLException;
        boolean verifyToolRentId(String rentId) throws SQLException;
        boolean verifyVehicleId(String vehicleId) throws SQLException;
        boolean verifyToolId(String toolId) throws SQLException;
        Vehicle getVehicleByAvailability(Vehicle entity) throws SQLException;
        Tool getToolByAvailability(Tool entity) throws SQLException;
        boolean updateVehicleRentOrderTable(VehicleOrder entity) throws SQLException;
        boolean addVehicleRentOrderDetailTable(ArrayList<VehicleOrderDetails> entities) throws SQLException;
        boolean updateVehicleTable(ArrayList<Vehicle> entities) throws SQLException;
        boolean updateToolRentOrderTable(ToolOrder entity) throws SQLException;
        boolean updateToolDetailsTable(ArrayList<ToolOrderDetails> entities) throws SQLException;
        boolean updateToolTable(ArrayList<Tool> entities) throws SQLException;
        Customer getCustomer(String customerId) throws SQLException;
        void checkInsuranceTable() throws SQLException;
        boolean checkValidVehicleInsurance(Vehicle entity,Integer rentDays) throws SQLException;
        boolean checkValidToolInsurance(Tool entity,Integer rentDays) throws SQLException;

}
