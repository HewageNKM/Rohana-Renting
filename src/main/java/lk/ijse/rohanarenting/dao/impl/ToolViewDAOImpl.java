package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.ToolViewDAO;
import lk.ijse.rohanarenting.entity.Tool;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToolViewDAOImpl implements ToolViewDAO {
    @Override
    public boolean insert(Tool entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean update(Tool entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean delete(Tool entity) throws SQLException {
        return false;
    }

    @Override
    public Tool get(Tool entity) throws SQLException, NoSuchAlgorithmException {
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
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool WHERE TID LIKE ? OR Brand LIKE ? OR Name LIKE ? OR Description LIKE ? OR Availability LIKE ? OR Rate_Per_Day LIKE ?",searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase);
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
}
