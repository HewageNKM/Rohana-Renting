package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.DashboardDAO;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
