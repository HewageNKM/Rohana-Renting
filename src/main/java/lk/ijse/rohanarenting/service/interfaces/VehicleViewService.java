package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.VehicleViewDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.VehicleViewDAO;
import lk.ijse.rohanarenting.dto.tm.VehicleTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.util.ArrayList;

public interface VehicleViewService extends SuperService {
    VehicleViewDAO vehicleViewDAO = (VehicleViewDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE_VIEW_DAO);
    ArrayList<VehicleTM> getAllVehicles() throws Exception;
    ArrayList<VehicleTM> searchVehicle(String searchPhrase) throws Exception;
}
