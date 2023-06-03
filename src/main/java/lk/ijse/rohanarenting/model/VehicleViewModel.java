/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/26/23, 6:29 PM
 *
 */

package lk.ijse.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rohanarenting.dto.tm.VehicleTM;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleViewModel {
    public static ArrayList<VehicleTM> getAllVehicles() throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle");
        ArrayList <VehicleTM> vehicleTMS = new ArrayList<>();
        while (resultSet.next()){
            vehicleTMS.add(new VehicleTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getString(7),new JFXButton(),new JFXButton(),new JFXButton()));
        }
        return vehicleTMS;
    }

    public static ArrayList<VehicleTM> searchVehicle(String searchPhrase) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM vehicle WHERE VID LIKE ? OR Manufacturer LIKE ? OR Model_Name LIKE ? OR Description LIKE ? OR Availability LIKE ? OR Rate_Per_Day LIKE ? OR Category LIKE ?;",searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase);
        ArrayList <VehicleTM> vehicleTMS = new ArrayList<>();
        while (resultSet.next()){
            vehicleTMS.add(new VehicleTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getString(7),new JFXButton(),new JFXButton(),new JFXButton()));
        }
        return vehicleTMS;
    }
}
