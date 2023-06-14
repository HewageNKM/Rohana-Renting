package lk.ijse.rohanarenting.dao.interfaces;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.QueryDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface DashboardDAO extends CruidDAO {
    QueryDAO queryDAO = (QueryDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY_DAO);
    String getEmployeeId() throws SQLException;
    String getTotalSaleValue() throws SQLException;
    String getInvoicesCount() throws SQLException;
    String getLastLogin() throws SQLException;
    ObservableList<PieChart.Data> getRentedCountPerMonthPie() throws SQLException;
    HashMap<Integer,Integer> getVehicleRefundCountPerMonth() throws SQLException;
    HashMap<Integer, Integer> getToolRefundCountPerMonth() throws SQLException;
    XYChart.Series<String, Integer> getRentedCountPerMonth() throws SQLException;
    Double getTotalRefundValue() throws SQLException;
    int getRefundCount() throws SQLException;
    ArrayList<Integer> getRentalCounts() throws SQLException;
}
