package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.LoginDAO;
import lk.ijse.rohanarenting.entity.User;
import lk.ijse.rohanarenting.utill.CruidUtil;
import lk.ijse.rohanarenting.utill.security.Encrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public boolean insert(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
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

    @Override
    public void insertUserLogInEntry(String employeeId) throws SQLException {
        CruidUtil.execute("INSERT INTO  user_login_history(EID,Date,Log_Time) VALUES(?,?,?)", employeeId, LocalDate.now(), LocalTime.now());
    }
    @Override
    public void insertUserLogoutEntry() throws SQLException {
        CruidUtil.execute("UPDATE user_login_history SET Logout_Time = ? WHERE Logout_Time IS NULL", LocalTime.now());
    }
}
