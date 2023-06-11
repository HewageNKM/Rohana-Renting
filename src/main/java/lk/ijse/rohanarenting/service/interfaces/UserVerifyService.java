package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.UserVerifyDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.UserVerifyDAO;
import lk.ijse.rohanarenting.service.SuperService;

public interface UserVerifyService extends SuperService {
    UserVerifyDAO userVerifyDAO = (UserVerifyDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER_VERIFY_DAO);
    Boolean verifyUser(String employeeId, String password) throws Exception;
}
