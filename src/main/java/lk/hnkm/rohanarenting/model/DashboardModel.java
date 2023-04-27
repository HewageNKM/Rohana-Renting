/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/18/23, 10:13 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lk.hnkm.rohanarenting.dto.UserLogin;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardModel {
    public static ArrayList<Integer> getRentalCounts(Label carRentalCountLabel) throws SQLException {
        LocalDate date = LocalDate.now();
        ResultSet vehicleRentCount = CruidUtil.execute("SELECT COUNT(vehicle_rent_order_detail.Rent_ID) FROM vehicle_rent_order_detail  RIGHT JOIN vehicle_rent_order ON vehicle_rent_order.Rent_ID = vehicle_rent_order_detail.Rent_ID  WHERE vehicle_rent_order.DATE = ? AND vehicle_rent_order_detail.Refund_Status = 0;", date);
        ArrayList<Integer> counts = new ArrayList<>();
        if (vehicleRentCount.next()) {
            counts.add(vehicleRentCount.getInt(1));
        } else {
            counts.add(0);
        }
        ResultSet toolRentCount = CruidUtil.execute("SELECT COUNT(tool_rent_order_detail.Rent_ID) FROM tool_rent_order_detail  RIGHT JOIN tool_rent_order ON tool_rent_order.Rent_ID = tool_rent_order_detail.Rent_ID  WHERE tool_rent_order.DATE = ? AND tool_rent_order_detail.Refund_Status = 0;", date);
        if (toolRentCount.next()) {
            counts.add(toolRentCount.getInt(1));
        } else {
            counts.add(0);
        }
        return counts;
    }

    public static int getRefundCount() throws SQLException {
        int count  =  0;
       ResultSet resultSet = CruidUtil.execute("SELECT COUNT(vehicle_rent_order_detail.Rent_ID) FROM vehicle_rent_order_detail  RIGHT JOIN vehicle_rent_order ON vehicle_rent_order.Rent_ID = vehicle_rent_order_detail.Rent_ID  WHERE vehicle_rent_order.DATE = ? AND vehicle_rent_order_detail.Refund_Status = 1;", LocalDate.now());
        if(resultSet.next()){
            count = resultSet.getInt(1);
        }
       ResultSet resultSet1 = CruidUtil.execute("SELECT COUNT(tool_rent_order_detail.Rent_ID) FROM tool_rent_order_detail  RIGHT JOIN tool_rent_order ON tool_rent_order.Rent_ID = tool_rent_order_detail.Rent_ID  WHERE tool_rent_order.DATE = ? AND tool_rent_order_detail.Refund_Status = 1;", LocalDate.now());
        if(resultSet1.next()){
            count += resultSet1.getInt(1);
        }
        return count;
    }

    public static Double getTotalRefundValue() throws SQLException {
        Double totalValue = 0.0;
        ResultSet resultSet = CruidUtil.execute("SELECT SUM(vehicle_refund_detail.Refund_Amount) FROM vehicle_refund_detail  RIGHT JOIN vehicle_refund ON vehicle_refund.Refund_ID = vehicle_refund_detail.Refund_ID  WHERE vehicle_refund.DATE = ?", LocalDate.now());
        if(resultSet.next()){
            totalValue = resultSet.getDouble(1);
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT SUM(tool_refund_detail.Refund_Amount) FROM tool_refund_detail  RIGHT JOIN tool_refund ON tool_refund.Refund_ID = tool_refund_detail.Refund_ID  WHERE tool_refund.DATE = ?", LocalDate.now());
        if(resultSet1.next()){
            totalValue += resultSet1.getDouble(1);
        }
        return totalValue;
    }

    public static XYChart.Series<String, Integer> getRentedCountPerMonth() throws SQLException {
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
        for(int i=0;i<12;i++){
            series.getData().add(new XYChart.Data<>(months.get(i+1),vehicle.get(i+1)+tool.get(i+1)));
        }
        return series;
    }

    public static ObservableList<PieChart.Data> getRentedCountPerMonthPie() throws SQLException {
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

    public static UserLogin getLastLogin() throws SQLException {
       ResultSet resultSet = CruidUtil.execute("SELECT * FROM user_login_history ORDER BY Log_Time  DESC LIMIT 1");
       UserLogin userLogin = null;
       if(resultSet.next()){
          userLogin = new UserLogin(resultSet.getString(1),resultSet.getDate(2).toLocalDate(),resultSet.getTime(4).toLocalTime(),resultSet.getTime(4).toLocalTime());
       }
       return userLogin;
    }

    public static String getInvoicesCount() throws SQLException {
        int count = 0;
        ResultSet resultSet = CruidUtil.execute("SELECT COUNT(Rent_ID) FROM vehicle_rent_order");
        if(resultSet.next()){
            count += resultSet.getInt(1);
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT COUNT(Rent_ID) FROM tool_rent_order");
        if(resultSet1.next()){
            count += resultSet1.getInt(1);
        }
        return String.valueOf(count);
    }

    public static String getTotalSaleValue() throws SQLException {
        Double totalValue = 0.0;
        ResultSet resultSet = CruidUtil.execute("SELECT SUM(Total) FROM vehicle_rent_order_detail");
        if(resultSet.next()){
            totalValue += resultSet.getDouble(1);
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT SUM(Total) FROM tool_rent_order_detail");
        if(resultSet1.next()){
            totalValue += resultSet1.getDouble(1);
        }
        return String.valueOf(totalValue);
    }
}
