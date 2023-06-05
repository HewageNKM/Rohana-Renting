package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.InsuranceDAO;
import lk.ijse.rohanarenting.entity.Insurance;
import lk.ijse.rohanarenting.utill.CruidUtil;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsuranceDAOImpl implements InsuranceDAO {
    @Override
    public boolean insert(Insurance entity) throws NoSuchAlgorithmException, SQLException {
        if(Regex.validateVehicleID(entity.getInsuranceID())){
            return CruidUtil.execute("INSERT INTO vehicle_insurance VALUES(?,?,?,?,?,?,?,?,?,?)", entity.getInsuranceID(), entity.getInsuranceName(), entity.getInsuranceProvider(), entity.getAgentName(), entity.getAgentContact(), entity.getEmail(), entity.getAddress(), entity.getFax(), entity.getJoinedDate(), entity.getExpireDate());
        }else {
            return CruidUtil.execute("INSERT INTO tool_insurance VALUES(?,?,?,?,?,?,?,?,?,?)", entity.getInsuranceID(), entity.getInsuranceName(), entity.getInsuranceProvider(), entity.getAgentName(), entity.getAgentContact(), entity.getEmail(), entity.getAddress(), entity.getFax(), entity.getJoinedDate(), entity.getExpireDate());
        }
    }

    @Override
    public boolean update(Insurance entity) throws NoSuchAlgorithmException, SQLException {
      if(Regex.validateVehicleID(entity.getInsuranceID())){
          return CruidUtil.execute("UPDATE vehicle_insurance SET Name=?,Provider=?,Agent_Name=?,Agent_Contact=?,Email=?,Address=?,Fax=?,Joined_Date=?,Expire_Date=? WHERE IID=?", entity.getInsuranceName(), entity.getInsuranceProvider(), entity.getAgentName(), entity.getAgentContact(), entity.getEmail(), entity.getAddress(), entity.getFax(), entity.getJoinedDate(), entity.getExpireDate(), entity.getInsuranceID());
      }else {
          return CruidUtil.execute("UPDATE tool_insurance SET Name=?,Provider=?,Agent_Name=?,Agent_Contact=?,Email=?,Address=?,Fax=?,Joined_Date=?,Expire_Date=? WHERE IID=?", entity.getInsuranceName(), entity.getInsuranceProvider(), entity.getAgentName(), entity.getAgentContact(), entity.getEmail(), entity.getAddress(), entity.getFax(), entity.getJoinedDate(), entity.getExpireDate(), entity.getInsuranceID());
      }
    }

    @Override
    public boolean delete(Insurance entity) throws SQLException {
        if(Regex.validateVehicleID(entity.getInsuranceID())){
            return CruidUtil.execute("DELETE FROM vehicle_insurance WHERE IID=?", entity.getInsuranceID());
        }else {
            return CruidUtil.execute("DELETE FROM tool_insurance WHERE IID=?", entity.getInsuranceID());
        }
    }

    @Override
    public Insurance get(Insurance entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet;
        if (Regex.validateVehicleID(entity.getInsuranceID())){
            resultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID=?", entity.getInsuranceID());
        }else {
            resultSet = CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID=?", entity.getInsuranceID());
        }
        return new Insurance(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8),
                resultSet.getDate(9).toLocalDate(),
                resultSet.getDate(10).toLocalDate()
        );
    }

    @Override
    public ArrayList<Insurance> getAll() throws SQLException {
        ResultSet vehicleResultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance");
        ArrayList<Insurance> insurances = new ArrayList<>();
        while (vehicleResultSet.next()) {
            insurances.add(new Insurance(
                    vehicleResultSet.getString(1),
                    vehicleResultSet.getString(2),
                    vehicleResultSet.getString(3),
                    vehicleResultSet.getString(4),
                    vehicleResultSet.getString(5),
                    vehicleResultSet.getString(6),
                    vehicleResultSet.getString(7),
                    vehicleResultSet.getString(8),
                    vehicleResultSet.getDate(9).toLocalDate(),
                    vehicleResultSet.getDate(10).toLocalDate()
            ));
        }
        ResultSet toolResultSet = CruidUtil.execute("SELECT * FROM tool_insurance");
        while (toolResultSet.next()) {
            insurances.add(new Insurance(
                    toolResultSet.getString(1),
                    toolResultSet.getString(2),
                    toolResultSet.getString(3),
                    toolResultSet.getString(4),
                    toolResultSet.getString(5),
                    toolResultSet.getString(6),
                    toolResultSet.getString(7),
                    toolResultSet.getString(8),
                    toolResultSet.getDate(9).toLocalDate(),
                    toolResultSet.getDate(10).toLocalDate()
            ));
        }
        return insurances;
    }

    @Override
    public boolean verify(Insurance entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet;
        if(Regex.validateVehicleID(entity.getInsuranceID())){
            resultSet = CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID=?", entity.getInsuranceID());
        }else {
            resultSet = CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID=?", entity.getInsuranceID());
        }
        return resultSet.next();
    }

    @Override
    public ArrayList<Insurance> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM vehicle_insurance WHERE IID LIKE ? OR Name LIKE ? OR Provider LIKE ? OR Agent_Name LIKE ? OR Agent_Contact LIKE ? OR Email LIKE ? OR Address LIKE ? OR Fax LIKE ? OR Joined_Date LIKE ? OR Expire_Date LIKE ?", searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase);
        ArrayList<Insurance> insurances = new ArrayList<>();
        while (resultSet.next()) {
            insurances.add(new Insurance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDate(9).toLocalDate(),
                    resultSet.getDate(10).toLocalDate()
            ));
        }
        ResultSet resultSet1 = CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID LIKE ? OR Name LIKE ? OR Provider LIKE ? OR Agent_Name LIKE ? OR Agent_Contact LIKE ? OR Email LIKE ? OR Address LIKE ? OR Fax LIKE ? OR Joined_Date LIKE ? OR Expire_Date LIKE ?", searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase);
        while (resultSet1.next()){
            insurances.add(new Insurance(
                    resultSet1.getString(1),
                    resultSet1.getString(2),
                    resultSet1.getString(3),
                    resultSet1.getString(4),
                    resultSet1.getString(5),
                    resultSet1.getString(6),
                    resultSet1.getString(7),
                    resultSet1.getString(8),
                    resultSet1.getDate(9).toLocalDate(),
                    resultSet1.getDate(10).toLocalDate()
            ));
        }
        return insurances;
    }
}
