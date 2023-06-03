/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/2/23, 3:03 PM
 *
 */

package lk.ijse.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.VehicleDTO;
import lk.ijse.rohanarenting.dto.tm.JasperReportVehicleTM;
import lk.ijse.rohanarenting.dto.tm.VehicleTM;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class VehicleModel {
    public static VehicleDTO getVehicleDetail(String vid) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle  WHERE VID = ?;", vid);
       if(resultSet.next()){
           VehicleDTO vehicleDTO = new VehicleDTO();
              vehicleDTO.setVID(resultSet.getString(1));
              vehicleDTO.setManufacturer(resultSet.getString(2));
              vehicleDTO.setModelName(resultSet.getString(3));
              vehicleDTO.setDescription(resultSet.getString(4));
              vehicleDTO.setAvailability(resultSet.getString(5));
              vehicleDTO.setRate(resultSet.getDouble(6));
              vehicleDTO.setCategory(resultSet.getString(7));
              return vehicleDTO;
       }
       return null;
    }

    public static Boolean getVehicle(String vid) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID = ?;", vid);
        return resultSet.next();
    }

    public static Boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException {
        return CruidUtil.execute("UPDATE vehicle SET Manufacturer = ?, Model_Name = ?, Description = ?,Availability = ?,Rate_Per_Day = ?, Category = ? WHERE VID = ?;", vehicleDTO.getManufacturer(), vehicleDTO.getModelName() , vehicleDTO.getDescription(), vehicleDTO.getAvailability(), vehicleDTO.getRate(), vehicleDTO.getCategory(), vehicleDTO.getVID());
    }

    public static Boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException {
        System.out.println(vehicleDTO.getRate());
        return CruidUtil.execute("INSERT INTO vehicle VALUES (?,?,?,?,?,?,?);", vehicleDTO.getVID(), vehicleDTO.getManufacturer(), vehicleDTO.getModelName(), vehicleDTO.getDescription(), vehicleDTO.getAvailability(), vehicleDTO.getRate(), vehicleDTO.getCategory());
    }

    public static Boolean deleteVehicle(String vid) throws SQLException {
        return CruidUtil.execute("DELETE FROM vehicle WHERE VID = ?",vid);
    }

    public static ArrayList<VehicleTM> getAllVehicles() throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle");
       ArrayList <VehicleTM> vehicleTMS = new ArrayList<>();
       while (resultSet.next()){
           JFXButton showBtn = new JFXButton();
           JFXButton editBtn = new JFXButton();
           JFXButton deleteBtn = new JFXButton();
           editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
           deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
           showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");

           vehicleTMS.add(new VehicleTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getString(7),showBtn,editBtn,deleteBtn));
       }
       return vehicleTMS;
    }

    public static Boolean checkOrderStatus(String vehicleId) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_rent_order_detail WHERE VID = ? AND Return_Status = 0 ;",vehicleId);
       return resultSet.next();
    }

    public static Boolean updateVehicleWithoutAvailability(VehicleDTO vehicleDTO) throws SQLException {
        return CruidUtil.execute("UPDATE vehicle SET Manufacturer = ?, Model_Name = ?, Description = ?, Rate_Per_Day = ?, Category = ? WHERE VID = ?;", vehicleDTO.getManufacturer(), vehicleDTO.getModelName() , vehicleDTO.getDescription(), vehicleDTO.getRate(), vehicleDTO.getCategory(), vehicleDTO.getVID());
    }

    public static ArrayList<JasperReportVehicleTM> jesperReportVehicleTMS(String vid) throws SQLException {
        ArrayList<JasperReportVehicleTM> jasperReportVehicleTMS = new ArrayList<>();
       ResultSet resultSet = CruidUtil.execute("SELECT vehicle_rent_order_detail.Rent_ID,vehicle_rent_order_detail.VID,vehicle_rent_order.Date FROM vehicle_rent_order_detail LEFT JOIN vehicle_rent_order ON vehicle_rent_order_detail.Rent_ID = vehicle_rent_order.Rent_ID WHERE vehicle_rent_order_detail.VID = ? ORDER BY vehicle_rent_order.Date DESC LIMIT 10;",vid);
       while (resultSet.next()){
           jasperReportVehicleTMS.add(new JasperReportVehicleTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)));
       }
       return jasperReportVehicleTMS;
    }

    public static ArrayList<VehicleTM> getSearchVehicles(String searchPhrase) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID LIKE ? OR Manufacturer LIKE ? OR Model_Name LIKE ? OR Description LIKE ? OR Availability LIKE ? OR Rate_Per_Day LIKE ? OR Category LIKE ?;",searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase);
        ArrayList <VehicleTM> vehicleTMS = new ArrayList<>();
        while (resultSet.next()){
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            vehicleTMS.add(new VehicleTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getString(7),showBtn,editBtn,deleteBtn));
        }
        return vehicleTMS;
    }

    public static InsuranceDTO getInsuranceDetails(String vid) throws SQLException {
            ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID = ?;",vid);
            if (resultSet.next()){
                return new InsuranceDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getDate(9).toLocalDate(),resultSet.getDate(10).toLocalDate());
            }else {
                return new InsuranceDTO("No Details","No Details","No Details","No Details","No Details","No Details","No Details","No Details", LocalDate.now(),LocalDate.now());
            }

    }
}