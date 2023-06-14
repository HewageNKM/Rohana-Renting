/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/6/23, 4:00 PM
 *
 */

package lk.ijse.rohanarenting.controller;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.DashboardServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.DashboardService;
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
    private final DashboardService dashboardService = (DashboardServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.DASHBOARD_SERVICE);

    public void initialize() {
        rentalCounts();
        setSaleDetails();
        setRefundDetails();
        setUserDetails();
        loadBarChart();
        loadPieChart();
    }

    @SneakyThrows
    private void loadPieChart() {
        ObservableList<PieChart.Data> pieChartData = dashboardService.getRentedCountPerMonthPie();
        pieChart.setData(pieChartData);
        pieChart.setTitle("Sales Value Through the Year");
    }

    private void loadBarChart() {
        try {
            barChart.setTitle("Orders Through the Year");
            XYChart.Series<String, Integer> series  = dashboardService.getRentedCountPerMonth();
            barChart.getData().add(series);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setRefundDetails() {
        try {
            int count = dashboardService.getRefundCount();
            refundCountLabel.setText(String.valueOf(count));
            Double totalValue = dashboardService.getTotalRefundValue();
            valueRefundTable.setText(String.valueOf(totalValue));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setSaleDetails() {
        try {
            InvoiceCount.setText(dashboardService.getInvoicesCount());
            totalSaleLabel.setText(dashboardService.getTotalSaleValue());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void rentalCounts() {
        try {
            ArrayList<Integer> counts = dashboardService.getRentalCounts();
            carRentalCountLabel.setText(String.valueOf(counts.get(0)));
            toolRentalCountLabel.setText(String.valueOf(counts.get(1)));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }
    public void setUserDetails() {
        try {
            employeeIdFld.setText(dashboardService.getEmployeeId());
            lastLoginFld.setText(dashboardService.getLastLogin());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }
}
