package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.EmployeeDAO;
import lk.ijse.rohanarenting.entity.Employee;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean insert(Employee entity) throws NoSuchAlgorithmException, SQLException {
        return  CruidUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getNIC(),
                entity.getGender(),
                entity.getBirthday(),
                entity.getMobileNumber(),
                entity.getEmail(),
                entity.getZipCode(),
                entity.getCity(),
                entity.getStreet(),
                entity.getState(),
                entity.getJoinedDate(),
                entity.getPosition()
        );
    }

    @Override
    public boolean update(Employee entity) throws NoSuchAlgorithmException, SQLException {
        return  CruidUtil.execute("UPDATE employee SET First_Name=?,Last_Name=?,NIC=?,Gender=?,Date_Of_Birth=?,Mobile_Number=?,Email=?,Zip=?,City=?,Street=?,State=?,Joined_Date=?,Position=? WHERE EID=?",
                entity.getFirstName(),
                entity.getLastName(),
                entity.getNIC(),
                entity.getGender(),
                entity.getBirthday(),
                entity.getMobileNumber(),
                entity.getEmail(),
                entity.getZipCode(),
                entity.getCity(),
                entity.getStreet(),
                entity.getState(),
                entity.getJoinedDate(),
                entity.getPosition(),
                entity.getEmployeeId()
        );
    }

    @Override
    public boolean delete(Employee entity) throws SQLException {
        return CruidUtil.execute("DELETE FROM employee WHERE EID=?",entity.getEmployeeId());
    }

    @Override
    public Employee get(Employee entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM employee WHERE EID=?",entity.getEmployeeId());
        if(resultSet.next()){
            return new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6).toLocalDate(),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getInt(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getDate(13).toLocalDate(),
                    resultSet.getString(14));
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM employee");
        ArrayList<Employee> employees = new ArrayList<>();
        while (resultSet.next()){
            employees.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6).toLocalDate(),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getInt(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getDate(13).toLocalDate(),
                    resultSet.getString(14)
            ));
        }
        return employees;
    }

    @Override
    public boolean verify(Employee entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet= CruidUtil.execute("SELECT * FROM employee WHERE EID = ?", entity.getEmployeeId());
        return resultSet.next();
    }

    @Override
    public ArrayList<Employee> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ArrayList<Employee> employees = new ArrayList<>();
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM employee WHERE EID LIKE ? OR First_Name LIKE ? OR Last_Name LIKE ?OR NIC LIKE ? OR Gender LIKE ? OR Date_Of_Birth LIKE ? OR Mobile_Number LIKE ? OR Email LIKE ? OR Zip LIKE ? OR City LIKE ? OR Street LIKE ? OR State LIKE ? OR Joined_Date LIKE ? OR Position LIKE ?",
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase
        );
        while (resultSet.next()){
            employees.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6).toLocalDate(),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getInt(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getDate(13).toLocalDate(),
                    resultSet.getString(14)
            ));
        }
        return employees;
    }
}
