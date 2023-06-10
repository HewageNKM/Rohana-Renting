package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.LoginHistoryDAO;
import lk.ijse.rohanarenting.entity.UserLogin;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class LoginHistoryDAOImpl implements LoginHistoryDAO {
    @Override
    public boolean insert(UserLogin entity) throws NoSuchAlgorithmException, SQLException {
        return false;
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
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user_login_history");
        ArrayList<UserLogin> loginHistoryTMS = new ArrayList<>();
        while (resultSet.next()){
            UserLogin userLogin = null;
            userLogin = new UserLogin(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getTime(3).toLocalTime(),
                    null
            );
            try {
                userLogin.setLogOutTime(LocalTime.parse(resultSet.getTime(4).toString().toString()));
            }catch (NullPointerException e) {
                userLogin.setLogOutTime(null);
            }
            loginHistoryTMS.add(userLogin);
        }
        return loginHistoryTMS;
    }

    @Override
    public boolean verify(UserLogin entity) throws SQLException, NoSuchAlgorithmException {
        return false;
    }

    @Override
    public ArrayList<UserLogin> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user_login_history WHERE EID LIKE ? OR Date LIKE ? OR Log_Time LIKE ? OR Logout_Time LIKE ?", searchPhrase, searchPhrase, searchPhrase, searchPhrase);
        ArrayList<UserLogin> loginHistoryTMS = new ArrayList<>();
        while (resultSet.next()){
            UserLogin userLogin = null;
            userLogin = new UserLogin(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getTime(3).toLocalTime(),
                    null
            );
            try {
                userLogin.setLogOutTime(LocalTime.parse(resultSet.getTime(4).toString().toString()));
            }catch (NullPointerException e) {
                userLogin.setLogOutTime(null);
            }
            loginHistoryTMS.add(userLogin);
        }
        return loginHistoryTMS;
    }
}
