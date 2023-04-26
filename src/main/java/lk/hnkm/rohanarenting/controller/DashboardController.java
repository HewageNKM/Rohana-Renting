/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/6/23, 4:00 PM
 *
 */

package lk.hnkm.rohanarenting.controller;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.hnkm.rohanarenting.model.DashboardModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardController {
    public Label carRentalCountLabel;
    public Label toolRentalCountLabel;
    public Label refundCountLabel;
    public Label valueRefundTable;
    public Label InvoiceCount;
    public Label totalSaleLabel;
    public BarChart barChart;
    public PieChart pieChart;
    String employeeId;
    public Label employeeIdFld;
    public Label userNameFld;
    public Label lastLoginFld;

    public void initialize() {
        rentalCounts();
        setSaleDetails();
        setRefundDetails();
        setUserDetails();
    }


    private void setRefundDetails() {
        try {
            int count = DashboardModel.getRefundCount();
            refundCountLabel.setText(String.valueOf(count));
            Double totalValue = DashboardModel.getTotalRefundValue();
            valueRefundTable.setText(String.valueOf(totalValue));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setSaleDetails() {

    }

    private void rentalCounts() {
        try {
            ArrayList<Integer> counts = DashboardModel.getRentalCounts(carRentalCountLabel);
            carRentalCountLabel.setText(String.valueOf(counts.get(0)));
            toolRentalCountLabel.setText(String.valueOf(counts.get(1)));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }
    public void setUserDetails() {
        System.out.println(employeeId);
//        employeeIdFld.setText(employeeId.toUpperCase());
    }
}
