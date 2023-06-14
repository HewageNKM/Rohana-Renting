package lk.ijse.rohanarenting.service.interfaces;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.DashboardDAOImpl;
import lk.ijse.rohanarenting.dao.impl.QueryDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.DashboardDAO;
import lk.ijse.rohanarenting.dao.interfaces.QueryDAO;
import lk.ijse.rohanarenting.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface DashboardService extends SuperService {
    QueryDAO queryDAO = (QueryDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY_DAO);
    DashboardDAO dashboardDAO = (DashboardDAOImpl)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DASHBOARD_DAO);
    ArrayList<Integer> getRentalCounts() throws SQLException;
    int getRefundCount() throws SQLException;
    Double getTotalRefundValue() throws SQLException;
    XYChart.Series<String, Integer> getRentedCountPerMonth() throws SQLException;
    HashMap<Integer, Integer> getToolRefundCountPerMonth() throws SQLException;
    HashMap<Integer,Integer> getVehicleRefundCountPerMonth() throws SQLException;
    ObservableList<PieChart.Data> getRentedCountPerMonthPie() throws SQLException;
    String getInvoicesCount() throws SQLException;
    String getTotalSaleValue() throws SQLException;
    String getEmployeeId() throws SQLException;
    String getLastLogin() throws SQLException;
}
