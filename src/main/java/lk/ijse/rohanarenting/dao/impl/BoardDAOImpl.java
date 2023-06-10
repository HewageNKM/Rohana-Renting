package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.BoardDAO;
import lk.ijse.rohanarenting.entity.Employee;
import lk.ijse.rohanarenting.entity.User;
import lk.ijse.rohanarenting.entity.UserLogin;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAOImpl implements BoardDAO {
    @Override
    public boolean insert(UserLogin entity) throws NoSuchAlgorithmException, SQLException {
        return CruidUtil.execute("UPDATE user_login_history SET Logout_Time = ? WHERE Logout_Time IS NULL", java.time.LocalTime.now());
    }

    @Override
    public boolean update(UserLogin entity) throws NoSuchAlgorithmException, SQLException {
        return false;
    }

    @Override
    public boolean delete(UserLogin entity) throws SQLException {
        return false;
    }

    @Override
    public UserLogin get(UserLogin entity) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public ArrayList<UserLogin> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean verify(UserLogin entity) throws SQLException, NoSuchAlgorithmException {
        return false;
    }

    @Override
    public ArrayList<UserLogin> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        return null;
    }
    public User getUserName(Employee entity) throws SQLException{
        ResultSet resultSet = CruidUtil.execute("SELECT UName FROM user WHERE `Employee ID` = ?", entity.getEmployeeId());
        if (resultSet.next()) {
            return new User(null,resultSet.getString(1),null,null);
        }
        return new User("User",null,null,null);
    }
}
