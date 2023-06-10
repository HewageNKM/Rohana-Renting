package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.LoginHistoryDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.LoginHistoryDAO;
import lk.ijse.rohanarenting.dto.tm.LoginHistoryTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.util.ArrayList;

public interface LoginHistoryService extends SuperService {
    LoginHistoryDAO loginHistoryDAO = (LoginHistoryDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LOGIN_HISTORY_DAO);
    ArrayList<LoginHistoryTM> getAllLoginHistory() throws Exception;
    ArrayList<LoginHistoryTM> searchLoginHistory(String search) throws Exception;
}
