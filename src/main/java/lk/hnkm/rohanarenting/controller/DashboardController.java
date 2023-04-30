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
        /*ObservableList<PieChart.Data> pieChartData = DashboardModel.getRentedCountPerMonthPie();
        pieChart.setData(pieChartData);
        pieChart.setTitle("Sales Value Through the Year");*/
        ObservableList<PieChart.Data> pieChartData = DashboardModel.getRentedCountPerMonthPie();
        pieChartData.add(new PieChart.Data("January", 100000));
        pieChartData.add(new PieChart.Data("February", 287000));
        pieChartData.add(new PieChart.Data("March", 43359));
        pieChartData.add(new PieChart.Data("April", 764654));
        pieChartData.add(new PieChart.Data("May", 400084));
        pieChartData.add(new PieChart.Data("June", 113121));
        pieChartData.add(new PieChart.Data("July", 232224));
        pieChartData.add(new PieChart.Data("August", 122434));
        pieChartData.add(new PieChart.Data("September", 323133));
        pieChartData.add(new PieChart.Data("October", 324432));
        pieChartData.add(new PieChart.Data("November", 324442));
        pieChartData.add(new PieChart.Data("December", 676545));
        pieChart.setData(pieChartData);
        pieChart.setTitle("Sales Value Through the Year");
    }

    private void loardBarChart() {
        try {
            barChart.setTitle("Orders Through the Year");
            //XYChart.Series<String, Integer> series  = DashboardModel.getRentedCountPerMonth();
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("January", 1000));
            series.getData().add(new XYChart.Data<>("February", 2800));
            series.getData().add(new XYChart.Data<>("March", 4339));
            series.getData().add(new XYChart.Data<>("April", 7654));
            series.getData().add(new XYChart.Data<>("May", 4084));
            series.getData().add(new XYChart.Data<>("June", 121));
            series.getData().add(new XYChart.Data<>("July", 2324));
            series.getData().add(new XYChart.Data<>("August", 1234));
            series.getData().add(new XYChart.Data<>("September", 3231));
            series.getData().add(new XYChart.Data<>("October", 3244));
            series.getData().add(new XYChart.Data<>("November", 3442));
            series.getData().add(new XYChart.Data<>("December", 6545));
            barChart.setStyle("-fx-font-size: 12px;");
            barChart.setStyle("-fx-font-weight: bold;");
            barChart.getData().add(series);
        } finally {

        }
        /* catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }*/
    }

    private void setRefundDetails() {
        /*try {
            int count = DashboardModel.getRefundCount();
            refundCountLabel.setText(String.valueOf(count));
            Double totalValue = DashboardModel.getTotalRefundValue();
            valueRefundTable.setText(String.valueOf(totalValue));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }*/
        refundCountLabel.setText("34");
        valueRefundTable.setText("234234");
    }

    private void setSaleDetails() {
        /*try {
            InvoiceCount.setText(DashboardModel.getInvoicesCount());
            totalSaleLabel.setText(DashboardModel.getTotalSaleValue());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }*/
        InvoiceCount.setText("64");
        totalSaleLabel.setText("2342564");
    }

    private void rentalCounts() {
        /*try {
            ArrayList<Integer> counts = DashboardModel.getRentalCounts(carRentalCountLabel);
            carRentalCountLabel.setText(String.valueOf(counts.get(0)));
            toolRentalCountLabel.setText(String.valueOf(counts.get(1)));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }*/
        carRentalCountLabel.setText("178");
        toolRentalCountLabel.setText("234");
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
