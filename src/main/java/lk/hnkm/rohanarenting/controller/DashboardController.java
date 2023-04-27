/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/6/23, 4:00 PM
 *
 */

package lk.hnkm.rohanarenting.controller;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.hnkm.rohanarenting.model.DashboardModel;
import lombok.SneakyThrows;

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
    public Label employeeIdFld;
    public Label lastLoginFld;

    public void initialize() {
        rentalCounts();
        setSaleDetails();
        setRefundDetails();
        setUserDetails();
        loardBarChart();
        loardPieChart();
    }

    @SneakyThrows
    private void loardPieChart() {
        ObservableList<PieChart.Data> pieChartData = DashboardModel.getRentedCountPerMonthPie();
        pieChart.setData(pieChartData);
        pieChart.setTitle("Sales Value Through the Year");
    }

    private void loardBarChart() {
        try {
            barChart.setTitle("Orders Through the Year");
            XYChart.Series<String, Integer> series = series = DashboardModel.getRentedCountPerMonth();
            barChart.setStyle("-fx-font-size: 12px;");
            barChart.setStyle("-fx-font-weight: bold;");
            barChart.getData().add(series);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
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
        try {
            InvoiceCount.setText(DashboardModel.getInvoicesCount());
            totalSaleLabel.setText(DashboardModel.getTotalSaleValue());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
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
        try {
            employeeIdFld.setText(DashboardModel.getEmployeeId());
            lastLoginFld.setText(DashboardModel.getLastLogin());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }
}
