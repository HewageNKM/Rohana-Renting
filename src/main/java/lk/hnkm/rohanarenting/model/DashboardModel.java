/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/18/23, 10:13 PM
 *
 */

package lk.hnkm.rohanarenting.model;

import javafx.scene.control.Label;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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
}
