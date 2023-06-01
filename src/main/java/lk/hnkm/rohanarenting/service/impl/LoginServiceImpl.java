package lk.hnkm.rohanarenting.service.impl;

import lk.hnkm.rohanarenting.dao.DAOFactory;
import lk.hnkm.rohanarenting.dao.impl.LoginDAOImpl;
import lk.hnkm.rohanarenting.dao.interfaces.LoginDAO;
import lk.hnkm.rohanarenting.dto.UserDTO;
import lk.hnkm.rohanarenting.entity.User;
import lk.hnkm.rohanarenting.service.interfaces.LoginService;
import lk.hnkm.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    private final LoginDAO loginDAO = (LoginDAOImpl)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LOGIN_DAO);

    @Override
    public boolean verifyUserCredentials(UserDTO dto) throws SQLException, NoSuchAlgorithmException {
        return loginDAO.verify(new User(dto.getEID(),null,dto.getUPassword(),null));
    }

    @Override
    public boolean passwordValidate(String password) {
        return Regex.validatePassword(password);
    }

    @Override
    public boolean employeeIdValidate(String employeeId) {
        return Regex.validateEID(employeeId);
    }

    @Override
    public void insertUserEntry(String employeeId) throws SQLException {
        loginDAO.InsertUserEntry(employeeId);
    }

    @Override
    public void setUserLogoutEntry() throws SQLException {
        loginDAO.setUserLogoutEntry();
    }
}
