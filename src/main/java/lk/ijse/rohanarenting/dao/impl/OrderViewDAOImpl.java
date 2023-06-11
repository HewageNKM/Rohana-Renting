package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.OrderViewDAO;
import lk.ijse.rohanarenting.dto.tm.OrderTM;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderViewDAOImpl implements OrderViewDAO {
    @Override
    public boolean insert(OrderTM entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean update(OrderTM entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean delete(OrderTM entity) throws SQLException {
        return false;
    }

    @Override
    public OrderTM get(OrderTM entity) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public ArrayList<OrderTM> getAll() throws SQLException {
        ArrayList<OrderTM> orderTMS = new ArrayList<>();
        ResultSet resultSet  = CruidUtil.execute("SELECT * FROM `vehicle_rent_order`");
        while (resultSet.next()){
            orderTMS.add(new OrderTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3).toLocalDate(),
                    resultSet.getTime(4).toLocalTime(),
                    getVehicleStatus(resultSet.getString(1))
            ));
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT * FROM `tool_rent_order`");
        while (resultSet1.next()){
            orderTMS.add(new OrderTM(
                    resultSet1.getString(1),
                    resultSet1.getString(2),
                    resultSet1.getDate(3).toLocalDate(),
                    resultSet1.getTime(4).toLocalTime(),
                    getToolStatus(resultSet1.getString(1))
            ));
        }
        return orderTMS;
    }

    @Override
    public boolean verify(OrderTM entity) throws SQLException, NoSuchAlgorithmException {
        return false;
    }

    @Override
    public ArrayList<OrderTM> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ArrayList<OrderTM> orderTMS = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM `vehicle_rent_order` WHERE `Rent_ID` LIKE ? OR `CID` LIKE ? OR `Date` LIKE ? OR `Time` LIKE ?", searchPhrase, searchPhrase, searchPhrase, searchPhrase);
        while (resultSet.next()){
            orderTMS.add(new OrderTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3).toLocalDate(),
                    resultSet.getTime(4).toLocalTime(),
                    getVehicleStatus(resultSet.getString(1))
            ));
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT * FROM `tool_rent_order` WHERE `Rent_ID` LIKE ? OR `CID` LIKE ? OR `Date` LIKE ? OR `Time` LIKE ?", searchPhrase, searchPhrase, searchPhrase, searchPhrase);
        while (resultSet1.next()){
            orderTMS.add(new OrderTM(
                    resultSet1.getString(1),
                    resultSet1.getString(2),
                    resultSet1.getDate(3).toLocalDate(),
                    resultSet1.getTime(4).toLocalTime(),
                    getToolStatus(resultSet1.getString(1))
            ));
        }
        return orderTMS;
    }

    @Override
    public String getVehicleStatus(String rentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT COUNT(Rent_ID) FROM `vehicle_rent_order_detail` WHERE `Rent_ID` = ?",rentId);
        resultSet.next();
        int count = resultSet.getInt(1);
        ResultSet resultSet1 = CruidUtil.execute("SELECT COUNT(Rent_ID) FROM `vehicle_rent_order_detail` WHERE `Rent_ID` = ? AND `Return_Status` = 1",rentId);
        resultSet1.next();
        int count1 = resultSet1.getInt(1);
        if(count1 == count){
            return "Completed";
        }else {
            return "Not Completed";
        }
    }

    @Override
    public String getToolStatus(String rentId) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT COUNT(Rent_ID) FROM `tool_rent_order_detail` WHERE `Rent_ID` = ?",rentId);
        resultSet.next();
        int count = resultSet.getInt(1);
        ResultSet resultSet1 = CruidUtil.execute("SELECT COUNT(Rent_ID) FROM `tool_rent_order_detail` WHERE `Rent_ID` = ? AND `Return_Status` = 1",rentId);
        resultSet1.next();
        int count1 = resultSet1.getInt(1);
        if(count1 == count){
            return "Completed";
        }else {
            return "Not Completed";
        }
    }
}
