package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.ForgotPasswordDAO;
import lk.ijse.rohanarenting.entity.User;
import lk.ijse.rohanarenting.utill.CruidUtil;
import lk.ijse.rohanarenting.utill.security.Encrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ForgotPasswordDAOImpl implements ForgotPasswordDAO {
    @Override
    public boolean insert(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) throws NoSuchAlgorithmException, SQLException {
        String password = Encrypt.encrypt(entity.getPassword());
        return CruidUtil.execute(" UPDATE user SET UPassword = ? WHERE `Employee ID` = ?;",password, entity.getEmployeeId());
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public User get(User entity) {
        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public boolean verify(User entity) throws SQLException, NoSuchAlgorithmException {
        String password = Encrypt.encrypt(entity.getPassword());
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE   `Employee ID` =? AND UPassword=?",entity.getEmployeeId(),password);
        return resultSet.next();
    }

    @Override
    public ArrayList<User> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        return null;
    }
}
