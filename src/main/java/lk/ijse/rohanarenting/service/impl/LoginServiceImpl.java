package lk.ijse.rohanarenting.service.impl;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.LoginDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.LoginDAO;
import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.entity.User;
import lk.ijse.rohanarenting.service.interfaces.LoginService;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    private final LoginDAO loginDAO = (LoginDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LOGIN_DAO);

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
    public void insertUserLogInEntry(String employeeId) throws SQLException {
        loginDAO.insertUserLogInEntry(employeeId);
    }

    @Override
    public void insertUserLogoutEntry() throws SQLException {
        loginDAO.insertUserLogoutEntry();
    }
}
