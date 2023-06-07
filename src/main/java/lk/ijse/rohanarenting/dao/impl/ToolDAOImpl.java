package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.ToolDAO;
import lk.ijse.rohanarenting.entity.Insurance;
import lk.ijse.rohanarenting.entity.Tool;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ToolDAOImpl implements ToolDAO {
    @Override
    public boolean insert(Tool entity) throws NoSuchAlgorithmException, SQLException {
        return CruidUtil.execute("INSERT INTO tool VALUES(?,?,?,?,?,?)", entity.getToolID(), entity.getBrandName(), entity.getToolName(), entity.getDescription(), entity.getAvailability(), entity.getRate());
    }

    @Override
    public boolean update(Tool entity) throws NoSuchAlgorithmException, SQLException {
        return CruidUtil.execute("UPDATE tool SET Brand =? , Name = ?, Description = ?, Availability = ?,Rate_Per_Day=? WHERE TID = ? ", entity.getBrandName(), entity.getToolName(), entity.getDescription(), entity.getAvailability(), entity.getRate(), entity.getToolID());
    }

    @Override
    public boolean delete(Tool entity) throws SQLException {
        return CruidUtil.execute("DELETE FROM tool WHERE TID = ?",entity.getToolID());
    }

    @Override
    public Tool get(Tool entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool  WHERE TID = ?;",entity.getToolID());
        if(resultSet.next()){
            Tool tool = new Tool();
            tool.setToolID(resultSet.getString(1));
            tool.setBrandName(resultSet.getString(2));
            tool.setToolName(resultSet.getString(3));
            tool.setDescription(resultSet.getString(4));
            tool.setAvailability(resultSet.getString(5));
            tool.setRate(resultSet.getDouble(6));
            return tool;
        }
        return null;
    }

    @Override
    public ArrayList<Tool> getAll() throws SQLException {
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM tool");
        ArrayList <Tool> tools = new ArrayList<>();
        while (resultSet.next()){
            tools.add(
                    new Tool(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getDouble(6)
                    )
            );
        }
        return tools;
    }

    @Override
    public boolean verify(Tool entity) throws SQLException, NoSuchAlgorithmException {
        return false;
    }

    @Override
    public ArrayList<Tool> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ArrayList<Tool> filterTools = new ArrayList<>();
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM tool WHERE TID LIKE ? OR Brand LIKE ? OR Name LIKE ? OR Description LIKE ? OR Availability LIKE ? OR Rate_Per_Day LIKE ?",searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase);
        while (resultSet.next()){
            filterTools.add(new Tool(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            ));
        }
        return filterTools;
    }
    public Insurance getInsurance(Tool entity) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID = ?",entity.getToolID());
        Insurance insurance = null;
        if (resultSet.next()){
            insurance =  new Insurance(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getDate(9).toLocalDate(),resultSet.getDate(10).toLocalDate());
        }
        if(insurance != null){
            return insurance;
        }else {
            return new Insurance("No Details","No Details","No Details","No Details","No Details","No Details","No Details","No Details", LocalDate.now(), LocalDate.now());
        }
    }
    public boolean checkOrderStatus(Tool entity) throws SQLException {
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM tool_rent_order_detail WHERE TID = ? AND Return_Status = 0 ",entity.getToolID());
        return resultSet.next();
    }

    @Override
    public boolean verifyId(String id) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool WHERE TID = ?",id);
        return resultSet.next();
    }

    public boolean updateToolWithoutAvailability(Tool entity) throws SQLException {
        return CruidUtil.execute("UPDATE tool SET Brand = ?,Name = ?,Description = ?,Rate_Per_Day = ? WHERE TID = ?", entity.getBrandName(), entity.getToolName(), entity.getDescription(), entity.getRate(), entity.getToolID());
    }
}
