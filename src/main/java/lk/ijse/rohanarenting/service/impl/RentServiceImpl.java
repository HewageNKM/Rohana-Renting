package lk.ijse.rohanarenting.service.impl;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import lk.ijse.rohanarenting.db.DBConnection;
import lk.ijse.rohanarenting.dto.*;
import lk.ijse.rohanarenting.dto.tm.ToolCartTM;
import lk.ijse.rohanarenting.dto.tm.ToolRentOrderJesperReportDetailTM;
import lk.ijse.rohanarenting.dto.tm.VehicleCartTM;
import lk.ijse.rohanarenting.dto.tm.VehicleRentOrderJesperReportDetailTM;
import lk.ijse.rohanarenting.entity.*;
import lk.ijse.rohanarenting.service.interfaces.RentService;
import lk.ijse.rohanarenting.utill.Regex;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RentServiceImpl implements RentService {
    @Override
    public boolean placeVehicleOrder(ArrayList<VehicleOrderDetailsDTO> orderDetailsDTOS, VehicleOrderDTO vehicleOrderDTO) throws SQLException {
        Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
                boolean isOrderAdded = rentDAO.updateVehicleRentOrderTable(new VehicleOrder(vehicleOrderDTO.getRentalOrderId(),vehicleOrderDTO.getCustomerID(),vehicleOrderDTO.getDate(),vehicleOrderDTO.getTime()));
                if(isOrderAdded){
                    boolean isDetailsAdded = rentDAO.addVehicleRentOrderDetailTable(getVehicleOrderDetailsArrayList(orderDetailsDTOS));
                    if(isDetailsAdded){
                        boolean isVehicleUpdated = rentDAO.updateVehicleTable(getVehicles(orderDetailsDTOS));
                        if(isVehicleUpdated){
                            connection.commit();
                            return true;
                        }else {
                            connection.rollback();
                            return false;
                        }
                    }else {
                        connection.rollback();
                        return false;
                    }
                }else {
                    connection.rollback();
                    return false;
                }
            }finally {
                    assert connection != null;
                    connection.setAutoCommit(true);
            }
    }

    private ArrayList<Vehicle> getVehicles(ArrayList<VehicleOrderDetailsDTO> orderDetailsDTOS) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        for (VehicleOrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            vehicles.add(new Vehicle(orderDetailsDTO.getVehicleId(),null,null,null,"Not Available",null,null));
        }
        return vehicles;
    }

    private ArrayList<VehicleOrderDetails> getVehicleOrderDetailsArrayList(ArrayList<VehicleOrderDetailsDTO> orderDetailsDTOS) {
        ArrayList<VehicleOrderDetails> vehicleOrderDetails = new ArrayList<>();
        for (VehicleOrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            vehicleOrderDetails.add(new VehicleOrderDetails(orderDetailsDTO.getRentId(),orderDetailsDTO.getVehicleId(),orderDetailsDTO.getManufacturer(),orderDetailsDTO.getModel(),orderDetailsDTO.getCategory(),orderDetailsDTO.getRentDays(),orderDetailsDTO.getRate(),orderDetailsDTO.getTotal(),orderDetailsDTO.getReturnDate(),orderDetailsDTO.getReturnStatus(),orderDetailsDTO.getRefundStatus()));
        }
        return vehicleOrderDetails;
    }

    @Override
    public boolean placeToolOrder(ArrayList<ToolOrderDetailsDTO> toolOrderDetailsDTOS, ToolOrderDTO toolOrderDTO) throws SQLException {
        Connection connection=null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isRentOrderTableUpdated = rentDAO.updateToolRentOrderTable(new ToolOrder(toolOrderDTO.getOrderId(), toolOrderDTO.getCustomerId(),toolOrderDTO.getDate(),toolOrderDTO.getTime()));
            if(isRentOrderTableUpdated){
               boolean isToolDetailsTableUpdated = rentDAO.updateToolDetailsTable(getToolOrderDetailsArrayList(toolOrderDetailsDTOS));
                if(isToolDetailsTableUpdated){
                    boolean isToolTableUpdated = rentDAO.updateToolTable(getTools(toolOrderDetailsDTOS));
                    if(isToolTableUpdated){
                        connection.commit();
                        return true;
                    }else {
                        connection.rollback();
                        return false;
                    }
                }else {
                    connection.rollback();
                    return false;
                }
            }else {
                connection.rollback();
                return false;
            }
        }finally {
            assert connection != null;
            connection.setAutoCommit(true);
        }
    }

    private ArrayList<Tool> getTools(ArrayList<ToolOrderDetailsDTO> toolOrderDetailsDTOS) {
        ArrayList<Tool> tools = new ArrayList<>();
        for (ToolOrderDetailsDTO toolOrderDetailsDTO: toolOrderDetailsDTOS) {
           tools.add(new Tool(toolOrderDetailsDTO.getToolId(),null,null,null,"Not Available",null));
        }
        return tools;
    }

    private ArrayList<ToolOrderDetails> getToolOrderDetailsArrayList(ArrayList<ToolOrderDetailsDTO> toolOrderDetailsDTOS){
        ArrayList<ToolOrderDetails> toolOrderDetailsArrayList = new ArrayList<>();
        for (ToolOrderDetailsDTO toolOrderDetailsDTO:toolOrderDetailsDTOS) {
            toolOrderDetailsArrayList.add(new ToolOrderDetails(toolOrderDetailsDTO.getRentId(),toolOrderDetailsDTO.getToolId(),toolOrderDetailsDTO.getBrand(),toolOrderDetailsDTO.getName(),toolOrderDetailsDTO.getRentDays(),toolOrderDetailsDTO.getRate(),toolOrderDetailsDTO.getTotal(),toolOrderDetailsDTO.getReturnDate(),toolOrderDetailsDTO.getReturnStatus(),toolOrderDetailsDTO.getRefundStatus()));
        }
        return toolOrderDetailsArrayList;
    }

    @Override
    public boolean verifyCustomerId(String CID) throws SQLException {
        return rentDAO.verifyCustomerId(CID);
    }

    @Override
    public boolean verifyVehicleRentId(String rentId) throws SQLException {
       return rentDAO.verifyVehicleRentId(rentId);
    }

    @Override
    public boolean verifyToolRentId(String rentId) throws SQLException {
        return rentDAO.verifyToolRentId(rentId);
    }

    @Override
    public boolean verifyVehicleId(String vehicleId) throws SQLException {
        return rentDAO.verifyVehicleId(vehicleId);
    }

    @Override
    public boolean verifyToolId(String toolId) throws SQLException {
        return rentDAO.verifyToolId(toolId);
    }

    @Override
    public VehicleCartTM getVehicleCartModel(VehicleOrderDTO vehicleOrderDTO,String vehicleId,Integer rentDays) throws SQLException {
        Vehicle vehicle = rentDAO.getVehicleByAvailability(new Vehicle(vehicleId,null,null,null,null,null,null));
        if(vehicle==null){
            return null;
        }else {
            JFXButton remove = new JFXButton();
            remove.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            Double total= rentDays * vehicle.getRate();
            return new VehicleCartTM(vehicleOrderDTO.getRentalOrderId(), vehicleId, vehicle.getManufacturer(), vehicle.getModel(), vehicleOrderDTO.getCustomerID(), vehicle.getDescription(), vehicle.getCategory(), vehicle.getRate(), rentDays,total,0,remove);
        }
    }

    @Override
    public Double getVehicleTotal(ObservableList<VehicleCartTM> vehicleCartTableData) {
        Double total=0.0;
        for (VehicleCartTM vehicleCartTM:vehicleCartTableData) {
            total+=vehicleCartTM.getTotal();
        }
        return total;
    }

    @Override
    public ToolCartTM getToolCartModel(ToolOrderDTO toolOrderDTO,String id,Integer rentDays) throws SQLException {
        Tool tool = rentDAO.getToolByAvailability(new Tool(id,null,null,null,null,null));
        JFXButton remove = new JFXButton();
        remove.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
        Double tolal = rentDays* tool.getRate();
        return tool != null ? new ToolCartTM(toolOrderDTO.getOrderId(), tool.getToolID(), tool.getBrandName(), tool.getToolName(), toolOrderDTO.getCustomerId(), tool.getDescription(), tool.getRate(), rentDays,tolal,remove):null;
    }

    @Override
    public Double getToolTotal(ObservableList<ToolCartTM> toolCartTMS) {
        Double total=0.0;
        for (ToolCartTM toolCartTM:toolCartTMS) {
            total+=toolCartTM.getTotal();
        }
        return total;
    }

    @Override
    public boolean checkToolCartDuplicate(ObservableList<ToolCartTM> toolCartTMS, String toolId) {
        for (ToolCartTM toolCartTM:toolCartTMS) {
            if(toolCartTM.getToolID().equals(toolId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkVehicleCartDuplicate(ObservableList<VehicleCartTM> vehicleCartTMS, String vehicleId) {
        for (VehicleCartTM vehicleCartTM:vehicleCartTMS) {
            if(vehicleCartTM.getVehicleID().equals(vehicleId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public CustomerDTO getCustomer(String customerId) throws SQLException {
        Customer customer = rentDAO.getCustomer(customerId);
        return new CustomerDTO(customer.getCustomerId(),customer.getFirstName(),customer.getLastName(),customer.getNic(),customer.getBirthday(),customer.getMobileNumber(),customer.getEmail(),customer.getStreet(),customer.getCity(),customer.getZipCode());
    }

    @Override
    public ArrayList<VehicleRentOrderJesperReportDetailTM> getVehicleJesperReport(ObservableList<VehicleCartTM> vehicleCartTMS) {
        ArrayList<VehicleRentOrderJesperReportDetailTM> vehicleRentOrderJesperReportDetailTMS = new ArrayList<>();
        for (VehicleCartTM vehicleCartTM:vehicleCartTMS) {
            vehicleRentOrderJesperReportDetailTMS.add(new VehicleRentOrderJesperReportDetailTM(
                    vehicleCartTM.getVehicleID(),
                    vehicleCartTM.getVehicleManufacture(),
                    vehicleCartTM.getVehicleModelName(),
                    vehicleCartTM.getCategory(),
                    vehicleCartTM.getRentDays(),
                    vehicleCartTM.getRate(),
                    vehicleCartTM.getTotal(),
                    Date.valueOf(LocalDate.now().plusDays(vehicleCartTM.getRentDays()))
            ));
        }
        return vehicleRentOrderJesperReportDetailTMS;
    }

    @Override
    public ArrayList<ToolRentOrderJesperReportDetailTM> getToolJesperReport(ObservableList<ToolCartTM> toolCartTMS) {
        ArrayList<ToolRentOrderJesperReportDetailTM> toolRentOrderJesperReportDetailTMS = new ArrayList<>();
        for (ToolCartTM toolCartTM:toolCartTMS) {
            toolRentOrderJesperReportDetailTMS.add(new ToolRentOrderJesperReportDetailTM(
                    toolCartTM.getToolID(),
                    toolCartTM.getBrandName(),
                    toolCartTM.getToolName(),
                    toolCartTM.getRentDays(),
                    toolCartTM.getRate(),
                    toolCartTM.getTotal(),
                    Date.valueOf(LocalDate.now().plusDays(toolCartTM.getRentDays()))
            ));
        }
        return toolRentOrderJesperReportDetailTMS;
    }

    @Override
    public void checkInsuranceTable() throws SQLException {
        rentDAO.checkInsuranceTable();
    }

    @Override
    public boolean checkValidVehicleInsurance(VehicleCartTM vehicleCartTM,Integer rentDays) throws SQLException {
        return rentDAO.checkValidVehicleInsurance(new Vehicle(vehicleCartTM.getVehicleID(),null,null,null,null,null,null),rentDays);
    }

    @Override
    public boolean checkValidToolInsurance(ToolCartTM toolCartTM,Integer rentDays) throws SQLException {
        return rentDAO.checkValidToolInsurance(new Tool(toolCartTM.getToolID(),null,null,null,null,null),rentDays);
    }

    @Override
    public boolean validateVehicleRentId(String rentId) {
        return Regex.validateVehicleRentId(rentId);
    }

    @Override
    public boolean validateToolRentId(String rentId) {
        return Regex.validateToolRentId(rentId);
    }

    @Override
    public boolean validateRentDays(String vehicleId) {
        return Regex.validateNumberOnly(vehicleId);
    }
}
