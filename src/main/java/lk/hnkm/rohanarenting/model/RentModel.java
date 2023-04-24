/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import lk.hnkm.rohanarenting.dto.*;
import lk.hnkm.rohanarenting.dto.tm.ToolCartTM;
import lk.hnkm.rohanarenting.dto.tm.ToolRentOrderJesperReportDetailTM;
import lk.hnkm.rohanarenting.dto.tm.VehicleCartTM;
import lk.hnkm.rohanarenting.dto.tm.VehicleRentOrderJesperReportDetailTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

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
    public static VehicleCartTM getVehicleCartModel(VehicleOrder vehicleOrder) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID=? AND (Availability > 0)", vehicleOrder.getVehicleId());
       if(resultSet.next()){
            Vehicle vehicle = new Vehicle(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getDouble(6),resultSet.getString(7));
            Double total= vehicleOrder.getRentDays()*vehicle.getRate();
            return new VehicleCartTM(vehicleOrder.getRentalOrderId(), vehicleOrder.getVehicleId(),vehicle.getManufacturer(),vehicle.getModelName(), vehicleOrder.getCustomerID(),vehicle.getDescription(),vehicle.getCategory(),vehicle.getRate(), vehicleOrder.getRentDays(),total,0,new JFXButton("Remove"));
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

    public static ToolCartTM getToolCartModel(ToolOrder toolOrder) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool WHERE TID=? AND (Availability > 0)",toolOrder.getToolId());
        if(resultSet.next()){
            Tool tool = new Tool(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getDouble(6));
            Double tolal = toolOrder.getRentDays()*tool.getRate();
            return new ToolCartTM(toolOrder.getRentalOrderId(),tool.getTID(),tool.getBrand(),tool.getName(),toolOrder.getCustomerId(),tool.getDescription(),tool.getRate(),toolOrder.getRentDays(),tolal,new JFXButton("Remove"));
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

    public static Boolean updateVehicleRentOrderTable(VehicleOrder vehicleOrder) throws SQLException {
        return CruidUtil.execute("INSERT INTO vehicle_rent_order VALUES (?,?,?,?)",vehicleOrder.getRentalOrderId(),vehicleOrder.getCustomerID(),LocalDate.now(),LocalTime.now());
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
          Boolean b =  CruidUtil.execute("UPDATE vehicle SET Availability = Availability - 1 WHERE VID = ?",vehicleCartTM.getVehicleID());
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

    public static Boolean updateToolRentOrderTable(ToolOrder toolOrder) throws SQLException {
       return CruidUtil.execute("INSERT INTO tool_rent_order VALUES (?,?,?,?)",toolOrder.getRentalOrderId(),toolOrder.getCustomerId(),LocalDate.now(),LocalTime.now());
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
            Boolean b = CruidUtil.execute("UPDATE tool SET Availability = Availability - 1 WHERE TID = ?",toolCartTM.getToolID());
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

    public static Customer getCustomer(String customerId) {
        try {
            ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE CID=?", customerId);
            if(resultSet.next()){
                return new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5).toLocalDate(),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getInt(10));
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
}
