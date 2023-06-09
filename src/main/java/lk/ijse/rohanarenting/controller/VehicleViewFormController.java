/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/26/23, 6:22 PM
 *
 */

package lk.ijse.rohanarenting.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rohanarenting.dto.tm.VehicleTM;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.VehicleViewServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.VehicleViewService;
import lk.ijse.rohanarenting.utill.TableUtil;

import java.util.ArrayList;

public class VehicleViewFormController {
    public TextField searchFld;
    public TableView vehiclesTable;
    public TableColumn<Object, Object> columnID;
    public TableColumn<Object, Object> columnModelName;
    public TableColumn<Object, Object> ColumnManufacturer;
    public TableColumn<Object, Object> columnRentalRate;
    public TableColumn columnCategory;
    public TableColumn columnAvailability;
    private ObservableList<VehicleTM> vehiclesTMS = FXCollections.observableArrayList();
    private final VehicleViewService vehicleViewService = (VehicleViewServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_VIEW_SERVICE);

    public void initialize() {
        TableUtil.installCopy(vehiclesTable);
        setCellValueFactories();
        loadVehicleTable();
    }

    private void loadVehicleTable() {
        try {
            ArrayList<VehicleTM> allVehicles = vehicleViewService.getAllVehicles();
            vehiclesTMS.clear();
            vehiclesTMS.addAll(allVehicles);
            vehiclesTable.setItems(vehiclesTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setCellValueFactories() {
        columnModelName.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        ColumnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        columnRentalRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnAvailability.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        columnID.setCellValueFactory(new PropertyValueFactory<>("VID"));
    }

    public void searchOnAction(KeyEvent keyEvent) {
        if (searchFld.getText().trim().isEmpty()){
            loadVehicleTable();
        }else {
            try {
                ArrayList<VehicleTM> allVehicles = vehicleViewService.searchVehicle("%"+searchFld.getText()+"%");
                vehiclesTMS.clear();
                vehiclesTMS.addAll(allVehicles);
                vehiclesTable.setItems(vehiclesTMS);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }
}

