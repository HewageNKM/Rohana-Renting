package lk.ijse.rohanarenting.service.impl;

import lk.ijse.rohanarenting.dto.tm.VehicleTM;
import lk.ijse.rohanarenting.entity.Vehicle;
import lk.ijse.rohanarenting.service.interfaces.VehicleViewService;

import java.util.ArrayList;

public class VehicleViewServiceImpl implements VehicleViewService {
    @Override
    public ArrayList<VehicleTM> getAllVehicles() throws Exception {
        ArrayList<Vehicle> vehicleArrayList = vehicleViewDAO.getAll();
        ArrayList<VehicleTM> vehicleTMS = new ArrayList<>();
        for (Vehicle vehicle:vehicleArrayList) {
           vehicleTMS.add(new VehicleTM(vehicle.getVehicleID(),vehicle.getManufacturer(),vehicle.getModel(),vehicle.getDescription(),vehicle.getAvailability(),vehicle.getRate(),vehicle.getCategory(),null,null,null));
        }
        return vehicleTMS;
    }

    @Override
    public ArrayList<VehicleTM> searchVehicle(String searchPhrase) throws Exception {
        ArrayList<Vehicle> vehicleArrayList = vehicleViewDAO.search(searchPhrase);
        ArrayList<VehicleTM> vehicleTMS = new ArrayList<>();
        for (Vehicle vehicle:vehicleArrayList) {
            vehicleTMS.add(new VehicleTM(vehicle.getVehicleID(),vehicle.getManufacturer(),vehicle.getModel(),vehicle.getDescription(),vehicle.getAvailability(),vehicle.getRate(),vehicle.getCategory(),null,null,null));
        }
        return vehicleTMS;
    }
}
