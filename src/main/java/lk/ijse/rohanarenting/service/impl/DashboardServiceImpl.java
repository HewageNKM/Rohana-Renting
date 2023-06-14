package lk.ijse.rohanarenting.service.impl;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import lk.ijse.rohanarenting.service.interfaces.DashboardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardServiceImpl implements DashboardService {
    @Override
    public ArrayList<Integer> getRentalCounts() throws SQLException {
       return dashboardDAO.getRentalCounts();
    }

    @Override
    public int getRefundCount() throws SQLException {
        return dashboardDAO.getRefundCount();
    }

    @Override
    public Double getTotalRefundValue() throws SQLException {
        return dashboardDAO.getTotalRefundValue();
    }

    @Override
    public XYChart.Series<String, Integer> getRentedCountPerMonth() throws SQLException {
        return dashboardDAO.getRentedCountPerMonth();
    }

    @Override
    public HashMap<Integer, Integer> getToolRefundCountPerMonth() throws SQLException {
        return dashboardDAO.getToolRefundCountPerMonth();
    }

    @Override
    public HashMap<Integer, Integer> getVehicleRefundCountPerMonth() throws SQLException {
        return dashboardDAO.getVehicleRefundCountPerMonth();
    }

    @Override
    public ObservableList<PieChart.Data> getRentedCountPerMonthPie() throws SQLException {
        return dashboardDAO.getRentedCountPerMonthPie();
    }

    @Override
    public String getInvoicesCount() throws SQLException {
        return dashboardDAO.getInvoicesCount();
    }

    @Override
    public String getTotalSaleValue() throws SQLException {
        return dashboardDAO.getTotalSaleValue();
    }

    @Override
    public String getEmployeeId() throws SQLException {
        return dashboardDAO.getEmployeeId();
    }

    @Override
    public String getLastLogin() throws SQLException {
        return dashboardDAO.getLastLogin();
    }
}
