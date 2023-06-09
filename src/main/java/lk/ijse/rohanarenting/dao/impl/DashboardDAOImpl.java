package lk.ijse.rohanarenting.dao.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import lk.ijse.rohanarenting.dao.interfaces.DashboardDAO;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardDAOImpl implements DashboardDAO {
    @Override
    public boolean insert(Object entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean update(Object entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean delete(Object entity) throws SQLException {
        return false;
    }

    @Override
    public Object get(Object entity) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public ArrayList getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean verify(Object entity) throws SQLException, NoSuchAlgorithmException {
        return false;
    }

    @Override
    public ArrayList search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public String getEmployeeId() throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT EID FROM user_login_history ORDER BY Log_Time  DESC LIMIT 1;");
        if(resultSet.next()){
            return resultSet.getString(1).toUpperCase();
        }
        return "No Data";
    }

    @Override
    public String getTotalSaleValue() throws SQLException {
        Double totalValue = 0.0;
        ResultSet resultSet = CruidUtil.execute("SELECT SUM(vehicle_rent_order_detail.Total) FROM vehicle_rent_order_detail LEFT JOIN vehicle_rent_order vro on vehicle_rent_order_detail.Rent_ID = vro.Rent_ID WHERE Date = CURDATE()");
        if(resultSet.next()){
            totalValue += resultSet.getDouble(1);
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT SUM(tool_rent_order_detail.Total) FROM tool_rent_order_detail LEFT JOIN tool_rent_order tro on tool_rent_order_detail.Rent_ID = tro.Rent_ID WHERE Date = CURDATE()");
        if(resultSet1.next()){
            totalValue += resultSet1.getDouble(1);
        }
        ResultSet resultSet2 = CruidUtil.execute("SELECT SUM(vehicle_refund_detail.Total) FROM vehicle_refund_detail LEFT JOIN vehicle_refund vr on vehicle_refund_detail.Refund_ID = vr.Refund_ID WHERE Date = CURDATE()");
        if(resultSet2.next()){
            totalValue -= resultSet2.getDouble(1);
        }
        ResultSet resultSet3 = CruidUtil.execute("SELECT SUM(tool_refund_detail.Total) FROM tool_refund_detail LEFT JOIN tool_refund tr on tool_refund_detail.Refund_ID = tr.Refund_ID WHERE Date = CURDATE()");
        if(resultSet3.next()){
            totalValue -= resultSet3.getDouble(1);
        }
        return String.valueOf(totalValue);
    }

    @Override
    public String getInvoicesCount() throws SQLException {
        int count = 0;
        ResultSet resultSet = CruidUtil.execute("SELECT COUNT(vehicle_rent_order_detail.Rent_ID) FROM vehicle_rent_order_detail RIGHT JOIN vehicle_rent_order vro on vro.Rent_ID = vehicle_rent_order_detail.Rent_ID WHERE vro.Date = CURDATE();");
        if(resultSet.next()){
            count += resultSet.getInt(1);
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT COUNT(tool_rent_order_detail.Rent_ID) FROM tool_rent_order_detail LEFT JOIN tool_rent_order tro on tro.Rent_ID = tool_rent_order_detail.Rent_ID WHERE tro.Date = CURDATE()");
        if(resultSet1.next()){
            count += resultSet1.getInt(1);
        }
        ResultSet resultSet2 = CruidUtil.execute("SELECT COUNT(vehicle_refund_detail.Refund_ID) FROM vehicle_refund_detail left outer join vehicle_refund vr on vr.Refund_ID = vehicle_refund_detail.Refund_ID WHERE vr.Date = CURDATE()");
        if(resultSet2.next()){
            count -= resultSet2.getInt(1);
        }
        ResultSet resultSet3 = CruidUtil.execute("SELECT COUNT(tool_refund_detail.Refund_ID) FROM tool_refund_detail LEFT JOIN tool_refund tr on tr.Refund_ID = tool_refund_detail.Refund_ID WHERE tr.Date = CURDATE()");
        if(resultSet3.next()){
            count -= resultSet3.getInt(1);
        }
        return String.valueOf(count);
    }

    @Override
    public String getLastLogin() throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT Log_Time FROM user_login_history ORDER BY Log_Time  DESC LIMIT 1;");
        if(resultSet.next()){
            return String.valueOf(resultSet.getTime(1).toLocalTime());
        }
        return "No Data";
    }

    @Override
    public ObservableList<PieChart.Data> getRentedCountPerMonthPie() throws SQLException {
        HashMap<Integer,Integer> vehicle = new HashMap<>();
        HashMap<Integer,Integer> tool = new HashMap<>();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        HashMap<Integer,String> months = new HashMap<>();
        months.put(1,"January");
        months.put(2,"February");
        months.put(3,"March");
        months.put(4,"April");
        months.put(5,"May");
        months.put(6,"June");
        months.put(7,"July");
        months.put(8,"August");
        months.put(9,"September");
        months.put(10,"October");
        months.put(11,"November");
        months.put(12,"December");
        ArrayList<Integer> vehicleMonth = new ArrayList<>();
        ArrayList<Integer> toolMonth = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT MONTH(vehicle_rent_order.Date),SUM(vrod.Total) FROM vehicle_rent_order RIGHT JOIN vehicle_rent_order_detail vrod on vehicle_rent_order.Rent_ID = vrod.Rent_ID GROUP BY  MONTH(vehicle_rent_order.Date);");
        while (resultSet.next()){
            vehicle.put(resultSet.getInt(1),resultSet.getInt(2));
            vehicleMonth.add(resultSet.getInt(1));
        }
        for (int i = 1; i <= 12; i++) {
            if(!vehicleMonth.contains(i)){
                pieChartData.add(new PieChart.Data(months.get(i),0));
                vehicle.put(i,0);
            }
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT MONTH(tool_rent_order.Date),SUM(trod.Total) FROM tool_rent_order RIGHT JOIN tool_rent_order_detail trod on tool_rent_order.Rent_ID = trod.Rent_ID GROUP BY  MONTH(tool_rent_order.Date);");
        while (resultSet1.next()){
            tool.put(resultSet1.getInt(1),resultSet1.getInt(2));
            toolMonth.add(resultSet1.getInt(1));
        }
        for (int i = 1; i <= 12; i++) {
            if(!toolMonth.contains(i)){
                tool.put(i,0);
            }
        }
        for(int i=0;i<12;i++){
            pieChartData.add(new PieChart.Data(months.get(i+1),vehicle.get(i+1)+tool.get(i+1)));
        }
        return pieChartData;
    }

    @Override
    public HashMap<Integer, Integer> getVehicleRefundCountPerMonth() throws SQLException {
        HashMap<Integer,Integer> vehicleRefund = new HashMap<>();
        HashMap<Integer,String> months = new HashMap<>();
        months.put(1,"January");
        months.put(2,"February");
        months.put(3,"March");
        months.put(4,"April");
        months.put(5,"May");
        months.put(6,"June");
        months.put(7,"July");
        months.put(8,"August");
        months.put(9,"September");
        months.put(10,"October");
        months.put(11,"November");
        months.put(12,"December");
        ArrayList<Integer> vehicleMonth = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT MONTH(vehicle_refund.Date), COUNT( DISTINCT vehicle_refund.Rent_ID) FROM vehicle_refund LEFT JOIN vehicle_rent_order vro on vehicle_refund.Rent_ID = vro.Rent_ID GROUP BY MONTH(vehicle_refund.Date);");
        while (resultSet.next()){
            vehicleRefund.put(resultSet.getInt(1),resultSet.getInt(2));
            vehicleMonth.add(resultSet.getInt(1));
        }
        for (int i = 1; i <= 12; i++) {
            if(!vehicleMonth.contains(i)){
                vehicleRefund.put(i,0);
            }
        }return vehicleRefund;
    }

    @Override
    public HashMap<Integer, Integer> getToolRefundCountPerMonth() throws SQLException {
        HashMap<Integer,Integer> toolRefund = new HashMap<>();
        HashMap<Integer,String> months = new HashMap<>();
        months.put(1,"January");
        months.put(2,"February");
        months.put(3,"March");
        months.put(4,"April");
        months.put(5,"May");
        months.put(6,"June");
        months.put(7,"July");
        months.put(8,"August");
        months.put(9,"September");
        months.put(10,"October");
        months.put(11,"November");
        months.put(12,"December");
        ArrayList<Integer> toolMonth = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT MONTH(tool_refund.Date), COUNT( DISTINCT tool_refund.Rent_ID) FROM tool_refund LEFT JOIN vehicle_rent_order tro on tool_refund.Rent_ID = tro.Rent_ID GROUP BY MONTH(tool_refund.Date);");
        while (resultSet.next()){
            toolRefund.put(resultSet.getInt(1),resultSet.getInt(2));
            toolMonth.add(resultSet.getInt(1));
        }
        for (int i = 1; i <= 12; i++) {
            if(!toolMonth.contains(i)){
                toolRefund.put(i,0);
            }
        }
        return toolRefund;
    }

    @Override
    public XYChart.Series<String, Integer> getRentedCountPerMonth() throws SQLException {
        HashMap<Integer,Integer> vehicle = new HashMap<>();
        HashMap<Integer,Integer> tool = new HashMap<>();
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        HashMap<Integer,String> months = new HashMap<>();
        months.put(1,"January");
        months.put(2,"February");
        months.put(3,"March");
        months.put(4,"April");
        months.put(5,"May");
        months.put(6,"June");
        months.put(7,"July");
        months.put(8,"August");
        months.put(9,"September");
        months.put(10,"October");
        months.put(11,"November");
        months.put(12,"December");
        series.setName("Order Count Per Month");
        ArrayList<Integer> vehicleMonth = new ArrayList<>();
        ArrayList<Integer> toolMonth = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT MONTH(Date),COUNT(Rent_ID) FROM vehicle_rent_order GROUP BY MONTH(Date)");
        while (resultSet.next()){
            vehicle.put(resultSet.getInt(1),resultSet.getInt(2));
            vehicleMonth.add(resultSet.getInt(1));
        }
        for (int i = 1; i <= 12; i++) {
            if(!vehicleMonth.contains(i)){
                series.getData().add(new XYChart.Data<>(months.get(i),0));
                vehicle.put(i,0);
            }
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT MONTH(Date),COUNT(Rent_ID) FROM tool_rent_order GROUP BY MONTH(Date)");
        while (resultSet1.next()){
            tool.put(resultSet1.getInt(1),resultSet1.getInt(2));
            toolMonth.add(resultSet1.getInt(1));
        }
        for (int i = 1; i <= 12; i++) {
            if(!toolMonth.contains(i)){
                tool.put(i,0);
            }
        }
        HashMap<Integer,Integer> vehicleRefundCountPerMonth = getVehicleRefundCountPerMonth();
        HashMap<Integer,Integer> toolRefundCountPerMonth = getToolRefundCountPerMonth();
        for(int i=0;i<12;i++){
            series.getData().add(new XYChart.Data<>(months.get(i+1),(vehicle.get(i+1)+tool.get(i+1)-vehicleRefundCountPerMonth.get(i+1)-toolRefundCountPerMonth.get(i+1))));
        }
        return series;
    }

    @Override
    public Double getTotalRefundValue() throws SQLException {
        Double totalValue = 0.0;
        ResultSet resultSet = CruidUtil.execute("SELECT SUM(vehicle_refund_detail.Refund_Amount) FROM vehicle_refund_detail  RIGHT JOIN vehicle_refund ON vehicle_refund.Refund_ID = vehicle_refund_detail.Refund_ID  WHERE vehicle_refund.DATE = CURDATE()");
        if(resultSet.next()){
            totalValue = resultSet.getDouble(1);
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT SUM(tool_refund_detail.Refund_Amount) FROM tool_refund_detail  RIGHT JOIN tool_refund ON tool_refund.Refund_ID = tool_refund_detail.Refund_ID  WHERE tool_refund.DATE = CURDATE()");
        if(resultSet1.next()){
            totalValue += resultSet1.getDouble(1);
        }
        System.out.println(totalValue);
        return totalValue;
    }

    @Override
    public int getRefundCount() throws SQLException {
        int count  =  0;
        ResultSet resultSet = CruidUtil.execute("SELECT COUNT(vehicle_rent_order_detail.Rent_ID) FROM vehicle_rent_order_detail  RIGHT JOIN vehicle_rent_order ON vehicle_rent_order.Rent_ID = vehicle_rent_order_detail.Rent_ID  WHERE vehicle_rent_order.DATE = CURDATE() AND vehicle_rent_order_detail.Refund_Status = 1;");
        if(resultSet.next()){
            count = resultSet.getInt(1);
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT COUNT(tool_rent_order_detail.Rent_ID) FROM tool_rent_order_detail  RIGHT JOIN tool_rent_order ON tool_rent_order.Rent_ID = tool_rent_order_detail.Rent_ID  WHERE tool_rent_order.DATE = CURDATE() AND tool_rent_order_detail.Refund_Status = 1;");
        if(resultSet1.next()){
            count += resultSet1.getInt(1);
        }
        return count;
    }

    @Override
    public ArrayList<Integer> getRentalCounts() throws SQLException {
        ResultSet vehicleRentCount = CruidUtil.execute("SELECT COUNT(vehicle_rent_order_detail.Rent_ID) FROM vehicle_rent_order_detail  RIGHT JOIN vehicle_rent_order ON vehicle_rent_order.Rent_ID = vehicle_rent_order_detail.Rent_ID  WHERE vehicle_rent_order.DATE = CURDATE() AND vehicle_rent_order_detail.Return_Status = 0;");
        ArrayList<Integer> counts = new ArrayList<>();
        if (vehicleRentCount.next()) {
            counts.add(vehicleRentCount.getInt(1));
        } else {
            counts.add(0);
        }
        ResultSet toolRentCount = CruidUtil.execute("SELECT COUNT(tool_rent_order_detail.Rent_ID) FROM tool_rent_order_detail  RIGHT JOIN tool_rent_order ON tool_rent_order.Rent_ID = tool_rent_order_detail.Rent_ID  WHERE tool_rent_order.DATE = CURDATE() AND tool_rent_order_detail.Return_Status = 0;");
        if (toolRentCount.next()) {
            counts.add(toolRentCount.getInt(1));
        } else {
            counts.add(0);
        }
        return counts;
    }
}
