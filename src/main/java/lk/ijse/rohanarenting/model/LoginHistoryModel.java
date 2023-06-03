package lk.ijse.rohanarenting.model;

import lk.ijse.rohanarenting.dto.tm.LoginHistoryTM;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginHistoryModel {

    public static ArrayList<LoginHistoryTM> getAllLoginHistory() throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user_login_history");
        ArrayList<LoginHistoryTM> loginHistoryTMS = new ArrayList<>();
        while (resultSet.next()){
            LoginHistoryTM loginHistoryTM = null;
            loginHistoryTM = new LoginHistoryTM(
                    resultSet.getString(1),
                    resultSet.getDate(2).toString(),
                    resultSet.getTime(3).toString(),
                    null
            );
            try {
                loginHistoryTM.setLogoutTime(resultSet.getTime(4).toString().toString());
            }catch (NullPointerException e) {
                loginHistoryTM.setLogoutTime("Not Logout Data");
            }
            loginHistoryTMS.add(loginHistoryTM);
        }
        return loginHistoryTMS;
    }

    public static ArrayList<LoginHistoryTM> searchLoginHistory(String searchPhrase) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user_login_history WHERE EID LIKE ? OR Date LIKE ? OR Log_Time LIKE ? OR Logout_Time LIKE ?", searchPhrase, searchPhrase, searchPhrase, searchPhrase);
        ArrayList<LoginHistoryTM> loginHistoryTMS = new ArrayList<>();
        while (resultSet.next()){
            LoginHistoryTM loginHistoryTM=new LoginHistoryTM(
                    resultSet.getString(1),
                    resultSet.getDate(2).toString(),
                    resultSet.getTime(3).toString(),
                    null
            );
            try {
                loginHistoryTM.setLogoutTime(resultSet.getTime(4).toString().toString());
            }catch (NullPointerException e) {
                loginHistoryTM.setLogoutTime("Not Logout Data");
            }
            loginHistoryTMS.add(loginHistoryTM);
        }
        return loginHistoryTMS;
    }
}
