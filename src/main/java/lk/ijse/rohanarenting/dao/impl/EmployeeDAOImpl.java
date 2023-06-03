package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.EmployeeDAO;
import lk.ijse.rohanarenting.entity.Employee;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean insert(Employee entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean update(Employee entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean delete(Employee entity) throws SQLException {
        return false;
    }

    @Override
    public Employee get(Employee entity) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean verify(Employee entity) throws SQLException, NoSuchAlgorithmException {
        return false;
    }

    @Override
    public ArrayList<Employee> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        return null;
    }
}
