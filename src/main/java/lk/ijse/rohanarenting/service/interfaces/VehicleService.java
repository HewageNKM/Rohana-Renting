package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.VehicleDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.VehicleDAO;
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.VehicleDTO;
import lk.ijse.rohanarenting.dto.tm.VehicleTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleService extends SuperService {
     VehicleDAO vehicleDAO = (VehicleDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE_DAO);
     boolean addVehicle(VehicleDTO dto) throws SQLException, NoSuchAlgorithmException;
        boolean updateVehicle(VehicleDTO dto) throws SQLException, NoSuchAlgorithmException;
        VehicleDTO getVehicle(VehicleDTO dto) throws SQLException, NoSuchAlgorithmException;
        boolean deleteVehicle(VehicleDTO dto) throws SQLException;
        ArrayList<VehicleTM> getAllVehicles() throws SQLException;
        ArrayList<VehicleTM> searchVehicle(String id) throws SQLException, NoSuchAlgorithmException;
        InsuranceDTO getInsuranceDetails(VehicleDTO dto) throws SQLException;
        boolean checkOrderStatus(VehicleDTO dto) throws SQLException;
        boolean updateVehicleWithoutAvailability(VehicleDTO dto) throws SQLException;
        boolean validateVehicleId(String id);
        boolean validateVehicleManufacturer(String id);
        boolean validateVehicleModel(String id);
        boolean validateVehicleDescription(String id);
        boolean validateRate(String id);
        boolean isVehicleExist(String id) throws SQLException, NoSuchAlgorithmException;
}
