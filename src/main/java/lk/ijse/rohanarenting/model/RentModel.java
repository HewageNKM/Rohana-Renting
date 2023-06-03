/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.ijse.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import lk.ijse.rohanarenting.dto.*;
import lk.ijse.rohanarenting.dto.tm.ToolCartTM;
import lk.ijse.rohanarenting.dto.tm.ToolRentOrderJesperReportDetailTM;
import lk.ijse.rohanarenting.dto.tm.VehicleCartTM;
import lk.ijse.rohanarenting.dto.tm.VehicleRentOrderJesperReportDetailTM;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class RentModel {
    public static Boolean verifyCustomerId(String CID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE CID = ?",CID);
        return resultSet.next();
    }

    public static Boolean verifyVehicleRentId(String rentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Rent_ID FROM vehicle_rent_order WHERE Rent_ID = ?",rentId);
        return resultSet.next();
    }

    public static Boolean verifyToolRentId(String rentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Rent_ID FROM vehicle_rent_order WHERE Rent_ID = ?",rentId);
        return resultSet.next();
    }

    public static Boolean verifyVehicleId(String vehicleId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID = ?",vehicleId);
        return  resultSet.next();
    }
    public static Boolean verifyToolId(String toolId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool WHERE TID = ?",toolId);
        return  resultSet.next();
    }
    public static VehicleCartTM getVehicleCartModel(VehicleOrderDTO vehicleOrderDTO) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID=? AND (Availability = 'Available')", vehicleOrderDTO.getVehicleId());
       if(resultSet.next()){
           JFXButton remove = new JFXButton();
           remove.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
           VehicleDTO vehicleDTO = new VehicleDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getString(7));
           Double total= vehicleOrderDTO.getRentDays()* vehicleDTO.getRate();
           return new VehicleCartTM(vehicleOrderDTO.getRentalOrderId(), vehicleOrderDTO.getVehicleId(), vehicleDTO.getManufacturer(), vehicleDTO.getModelName(), vehicleOrderDTO.getCustomerID(), vehicleDTO.getDescription(), vehicleDTO.getCategory(), vehicleDTO.getRate(), vehicleOrderDTO.getRentDays(),total,0,remove);
       } else {
           return null;
       }
    }

    public static Double getVehicleTotal(ObservableList<VehicleCartTM> vehicleCartTableData) {
        Double subTotal=0.0;
        for (VehicleCartTM vehicleCartTableDatum : vehicleCartTableData) {
            subTotal += vehicleCartTableDatum.getTotal();
        }
        return subTotal;
    }

    public static ToolCartTM getToolCartModel(ToolOrderDTO toolOrderDTO) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool WHERE TID=? AND (Availability = 'Available')", toolOrderDTO.getToolId());
        if(resultSet.next()){
            JFXButton remove = new JFXButton();
            remove.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            ToolDTO toolDTO = new ToolDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6));
            Double tolal = toolOrderDTO.getRentDays()* toolDTO.getRate();
            return new ToolCartTM(toolOrderDTO.getRentalOrderId(), toolDTO.getTID(), toolDTO.getBrand(), toolDTO.getName(), toolOrderDTO.getCustomerId(), toolDTO.getDescription(), toolDTO.getRate(), toolOrderDTO.getRentDays(),tolal,remove);
        }else {
             return null;
        }
    }

    public static Double getToolTotal(ObservableList<ToolCartTM> toolCartTMS) {
        Double total= 0.0;
        for (ToolCartTM toolCartTM:toolCartTMS) {
               total += toolCartTM.getTotal();
        }
        return total;
    }

    public static Boolean updateVehicleRentOrderTable(VehicleOrderDTO vehicleOrderDTO) throws SQLException {
        return CruidUtil.execute("INSERT INTO vehicle_rent_order VALUES (?,?,?,?)", vehicleOrderDTO.getRentalOrderId(), vehicleOrderDTO.getCustomerID(),LocalDate.now(),LocalTime.now());
    }

    public static Boolean addVehicleRentOrderDetailTable(ObservableList<VehicleCartTM> vehicleCartTMS) throws SQLException {
        int count=0;
        for (VehicleCartTM vehicleCartTM:vehicleCartTMS) {
            Boolean b = CruidUtil.execute("INSERT INTO vehicle_rent_order_detail VALUES (?,?,?,?,?,?,?,?,?,?,?)",vehicleCartTM.getRentOrderID(),vehicleCartTM.getVehicleID(),vehicleCartTM.getVehicleManufacture(),vehicleCartTM.getVehicleModelName(),vehicleCartTM.getCategory(),vehicleCartTM.getRentDays(),vehicleCartTM.getRate(),vehicleCartTM.getTotal(),LocalDate.now().plusDays(vehicleCartTM.getRentDays()),0,0);
            if(b) {
                count++;
            }
        }
       if(count == vehicleCartTMS.size()){
           return true;

       }else {
           return false;
       }
    }

    public static Boolean updateVehicleTable(ObservableList<VehicleCartTM> vehicleCartTMS) throws SQLException {
        int count=0;
        for (VehicleCartTM vehicleCartTM:vehicleCartTMS) {
          Boolean b =  CruidUtil.execute("UPDATE vehicle SET Availability = 'Not Available' WHERE VID = ?",vehicleCartTM.getVehicleID());
            if(b){
                count++;
            }
        }
        if(count == vehicleCartTMS.size()){
            return true;
    }else {
            return false;
        }
    }

    public static Boolean updateToolRentOrderTable(ToolOrderDTO toolOrderDTO) throws SQLException {
       return CruidUtil.execute("INSERT INTO tool_rent_order VALUES (?,?,?,?)", toolOrderDTO.getRentalOrderId(), toolOrderDTO.getCustomerId(),LocalDate.now(),LocalTime.now());
    }

    public static Boolean updateToolDetailsTable(ObservableList<ToolCartTM> toolCartTMS) throws SQLException {
        int count=0;
        for (ToolCartTM toolCartTM:toolCartTMS) {
            Boolean b = CruidUtil.execute("INSERT INTO tool_rent_order_detail VALUES (?,?,?,?,?,?,?,?,?,?)",toolCartTM.getRentOrderID(),toolCartTM.getToolID(),toolCartTM.getBrandName(),toolCartTM.getToolName(),toolCartTM.getRentDays(),toolCartTM.getRate(),toolCartTM.getTotal(),LocalDate.now().plusDays(toolCartTM.getRentDays()),0,0);
            if(b){
                count++;
            }
        }
        if(count == toolCartTMS.size()){
            return true;
        }else {
            return false;
        }
    }

    public static Boolean updateToolTable(ObservableList<ToolCartTM> toolCartTMS) throws SQLException {
        int count=0;
        for (ToolCartTM toolCartTM:toolCartTMS) {
            Boolean b = CruidUtil.execute("UPDATE tool SET Availability = 'Not Available' WHERE TID = ?",toolCartTM.getToolID());
            if(b){
                count++;
            }
        }
        if(count == toolCartTMS.size()){
            return true;
        }else {
            return false;
        }
    }

    public static boolean checkToolCartDuplicate(ObservableList<ToolCartTM> toolCartTMS, String toolId) {
        for (ToolCartTM toolCartTM:toolCartTMS) {
            if(toolCartTM.getToolID().equalsIgnoreCase(toolId)){
                return true;
            }
        }
        return false;
    }
    public static boolean checkVehicleCartDuplicate(ObservableList<VehicleCartTM> vehicleCartTMS, String vehicleId) {
        for (VehicleCartTM vehicleCartTM : vehicleCartTMS) {
            if(vehicleCartTM.getVehicleID().equalsIgnoreCase(vehicleId)){
                return true;
            }
        }
        return false;
    }

    public static CustomerDTO getCustomer(String customerId) {
        try {
            ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE CID=?", customerId);
            if(resultSet.next()){
                return new CustomerDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5).toLocalDate(),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getInt(10));
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<VehicleRentOrderJesperReportDetailTM> getVehicleJesperReport(ObservableList<VehicleCartTM> vehicleCartTMS) {
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

    public static ArrayList<ToolRentOrderJesperReportDetailTM> getToolJesperReport(ObservableList<ToolCartTM> toolCartTMS) {
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

    public static void checkInsuranceTable() throws SQLException {
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

    public static boolean checkValidVehicleInsurance(VehicleCartTM vehicleCartTM) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT Expire_Date FROM vehicle_insurance WHERE IID = ?",vehicleCartTM.getVehicleID());
        if (resultSet.next()){
            return !resultSet.getDate(1).toLocalDate().isAfter(LocalDate.now().plusDays(vehicleCartTM.getRentDays()));
        }else {
            return true;
        }
    }
    public static boolean checkValidToolInsurance(ToolCartTM toolCartTM) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Expire_Date FROM tool_insurance WHERE IID = ?",toolCartTM.getToolID());
        if (resultSet.next()){
            return !resultSet.getDate(1).toLocalDate().isAfter(LocalDate.now().plusDays(toolCartTM.getRentDays()));
        }else {
            return true;
        }
    }
}
