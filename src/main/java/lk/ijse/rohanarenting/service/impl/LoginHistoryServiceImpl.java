package lk.ijse.rohanarenting.service.impl;

import lk.ijse.rohanarenting.dto.tm.LoginHistoryTM;
import lk.ijse.rohanarenting.entity.UserLogin;
import lk.ijse.rohanarenting.service.interfaces.LoginHistoryService;

import java.util.ArrayList;

public class LoginHistoryServiceImpl implements LoginHistoryService {
    @Override
    public ArrayList<LoginHistoryTM> getAllLoginHistory() throws Exception {
        return getTMS(loginHistoryDAO.getAll());
    }

    private ArrayList<LoginHistoryTM> getTMS(ArrayList<UserLogin> userLogins) {
        ArrayList<LoginHistoryTM> loginHistoryTMS = new ArrayList<>();
        for (UserLogin userLogin : userLogins) {
            LoginHistoryTM loginHistoryTM = new LoginHistoryTM(
                    userLogin.getEmployeeId(),
                    userLogin.getDate().toString(),
                    userLogin.getLogTime().toString(),
                    userLogin.getLogOutTime().toString()
            );
            loginHistoryTMS.add(loginHistoryTM);
        }
        return loginHistoryTMS;
    }

    @Override
    public ArrayList<LoginHistoryTM> searchLoginHistory(String search) throws Exception {
        return getTMS(loginHistoryDAO.search(search));
    }
}
