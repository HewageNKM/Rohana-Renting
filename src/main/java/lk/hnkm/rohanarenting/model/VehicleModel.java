/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/2/23, 3:03 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.Vehicle;
import lk.hnkm.rohanarenting.dto.tm.VehicleTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleModel {
    public static Vehicle getVehicleDetail(String vid) throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle  WHERE VID = ?;", vid);
       if(resultSet.next()){
           Vehicle vehicle = new Vehicle();
              vehicle.setVID(resultSet.getString(1));
              vehicle.setManufacturer(resultSet.getString(2));
              vehicle.setModelName(resultSet.getString(3));
              vehicle.setDescription(resultSet.getString(4));
              vehicle.setAvailability(resultSet.getInt(5));
              vehicle.setRate(resultSet.getDouble(6));
              vehicle.setCategory(resultSet.getString(7));
              return vehicle;
       }
       return null;
    }

    public static Boolean getVehicle(String vid) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID = ?;", vid);
        return resultSet.next();
    }

    public static Boolean updateVehicle(Vehicle vehicle) throws SQLException {
        return CruidUtil.execute("UPDATE vehicle SET Manufacturer = ?, Model_Name = ?, Description = ?,Availability = ?,Rate_Per_Day = ?, Category = ? WHERE VID = ?;",vehicle.getManufacturer(),vehicle.getModelName() ,vehicle.getDescription(), vehicle.getAvailability(),vehicle.getRate(), vehicle.getCategory(),vehicle.getVID());
    }

    public static Boolean saveVehicle(Vehicle vehicle) throws SQLException {
        System.out.println(vehicle.getRate());
        return CruidUtil.execute("INSERT INTO vehicle VALUES (?,?,?,?,?,?,?);", vehicle.getVID(), vehicle.getManufacturer(), vehicle.getModelName(), vehicle.getDescription(), vehicle.getAvailability(), vehicle.getRate(),vehicle.getCategory());
    }

    public static Boolean deleteVehicle(String vid) throws SQLException {
        return CruidUtil.execute("DELETE FROM vehicle WHERE VID = ?",vid);
    }

    public static ArrayList<VehicleTM> getAllVehicles() throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle");
       ArrayList <VehicleTM> vehicleTMS = new ArrayList<>();
       while (resultSet.next()){
           vehicleTMS.add(new VehicleTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),(0<resultSet.getInt(5))? "Available" : "Not Available",resultSet.getDouble(6),resultSet.getString(7),new JFXButton("Edit"),new JFXButton("Delete")));
       }
       return vehicleTMS;
    }
}
