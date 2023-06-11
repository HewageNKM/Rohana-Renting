package lk.ijse.rohanarenting.service.impl;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.VehicleDTO;
import lk.ijse.rohanarenting.dto.tm.VehicleTM;
import lk.ijse.rohanarenting.entity.Insurance;
import lk.ijse.rohanarenting.entity.Vehicle;
import lk.ijse.rohanarenting.service.interfaces.VehicleService;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleServiceImpl implements VehicleService {

    @Override
    public boolean addVehicle(VehicleDTO dto) throws SQLException, NoSuchAlgorithmException {
        return vehicleDAO.insert(new Vehicle(dto.getVID(),dto.getManufacturer(),dto.getModelName(),dto.getDescription(),dto.getAvailability(),dto.getRate(),dto.getCategory()));
    }

    @Override
    public boolean updateVehicle(VehicleDTO dto) throws SQLException, NoSuchAlgorithmException {
        return vehicleDAO.update(new Vehicle(dto.getVID(),dto.getManufacturer(),dto.getModelName(),dto.getDescription(),dto.getAvailability(),dto.getRate(),dto.getCategory()));
    }

    @Override
    public VehicleDTO getVehicle(VehicleDTO dto) throws SQLException, NoSuchAlgorithmException {
        Vehicle vehicle = vehicleDAO.get(new Vehicle(dto.getVID(),dto.getManufacturer(),dto.getModelName(),dto.getDescription(),dto.getAvailability(),dto.getRate(),dto.getCategory()));
        return new VehicleDTO(vehicle.getVehicleID(),vehicle.getManufacturer(),vehicle.getModel(),vehicle.getDescription(),vehicle.getAvailability(),vehicle.getRate(),vehicle.getCategory());
    }

    @Override
    public boolean deleteVehicle(VehicleDTO dto) throws SQLException {
        return vehicleDAO.delete(new Vehicle(dto.getVID(),dto.getManufacturer(),dto.getModelName(),dto.getDescription(),dto.getAvailability(),dto.getRate(),dto.getCategory()));
    }

    @Override
    public ArrayList<VehicleTM> getAllVehicles() throws SQLException {
        return getTMS(vehicleDAO.getAll());
    }

    @Override
    public ArrayList<VehicleTM> searchVehicle(String id) throws SQLException, NoSuchAlgorithmException {
        return getTMS(vehicleDAO.search(id));
    }

    private ArrayList<VehicleTM> getTMS(ArrayList<Vehicle> vehicles) {
        ArrayList<VehicleTM> vehicleTMS = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            vehicleTMS.add(new VehicleTM(vehicle.getVehicleID(),vehicle.getManufacturer(),vehicle.getModel(),vehicle.getDescription(),vehicle.getAvailability(),vehicle.getRate(),vehicle.getCategory(),showBtn,editBtn,deleteBtn));
        }
        return vehicleTMS;
    }

    @Override
    public InsuranceDTO getInsuranceDetails(VehicleDTO dto) throws SQLException {
        Insurance insurance = vehicleDAO.getInsuranceDetails(new Vehicle(dto.getVID(),dto.getManufacturer(),dto.getModelName(),dto.getDescription(),dto.getAvailability(),dto.getRate(),dto.getCategory()));
        return new InsuranceDTO(insurance.getInsuranceID(),insurance.getInsuranceName(),insurance.getInsuranceProvider(),insurance.getAgentName(),insurance.getAgentContact(),insurance.getEmail(),insurance.getAddress(),insurance.getFax(),insurance.getJoinedDate(),insurance.getExpireDate());
    }

    @Override
    public boolean checkOrderStatus(VehicleDTO dto) throws SQLException {
        return vehicleDAO.checkOrderStatus(new Vehicle(dto.getVID(),dto.getManufacturer(),dto.getModelName(),dto.getDescription(),dto.getAvailability(),dto.getRate(),dto.getCategory()));
    }

    @Override
    public boolean updateVehicleWithoutAvailability(VehicleDTO dto) throws SQLException {
        return vehicleDAO.updateVehicleWithoutAvailability(new Vehicle(dto.getVID(),dto.getManufacturer(),dto.getModelName(),dto.getDescription(),dto.getAvailability(),dto.getRate(),dto.getCategory()));
    }

    @Override
    public boolean validateVehicleId(String id) {
        return Regex.validateVehicleID(id);
    }

    @Override
    public boolean validateVehicleManufacturer(String id) {
        return Regex.validateName(id);
    }

    @Override
    public boolean validateVehicleModel(String id) {
        return !Regex.validateName(id);
    }

    @Override
    public boolean validateVehicleDescription(String id) {
        return (id.trim().isEmpty());
    }

    @Override
    public boolean validateRate(String id) {
        return Regex.validateNumbersAndDecimals(id);
    }

    @Override
    public boolean isVehicleExist(String id) throws SQLException, NoSuchAlgorithmException {
        return  vehicleDAO.verify(new Vehicle(id,null,null,null,null,null,null));
    }
}
