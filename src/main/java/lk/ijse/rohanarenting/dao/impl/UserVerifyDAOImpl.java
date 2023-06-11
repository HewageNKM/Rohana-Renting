package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.UserVerifyDAO;
import lk.ijse.rohanarenting.utill.CruidUtil;
import lk.ijse.rohanarenting.utill.security.Encrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserVerifyDAOImpl implements UserVerifyDAO {
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
    public Boolean verifyUser(String employeeId, String password) throws SQLException, NoSuchAlgorithmException {
        password = Encrypt.encrypt(password);
        ResultSet resultSet = CruidUtil.execute("SELECT Permission_Level FROM user WHERE `Employee ID` = ? AND UPassword = ?;", employeeId, password);
        if (resultSet.next()) {
            String permissionLevel = resultSet.getString(1);
            return permissionLevel.equals("A") ? true : false;
        }
        return null;
    }

}
