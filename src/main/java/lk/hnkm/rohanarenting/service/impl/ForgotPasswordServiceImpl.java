package lk.hnkm.rohanarenting.service.impl;

import lk.hnkm.rohanarenting.dao.DAOFactory;
import lk.hnkm.rohanarenting.dao.impl.ForgotPasswordDAOImpl;
import lk.hnkm.rohanarenting.dao.interfaces.ForgotPasswordDAO;
import lk.hnkm.rohanarenting.dto.UserDTO;
import lk.hnkm.rohanarenting.entity.User;
import lk.hnkm.rohanarenting.service.interfaces.ForgotPasswordService;
import lk.hnkm.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final ForgotPasswordDAO forgotPasswordDAO = (ForgotPasswordDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FORGOT_PASSWORD_DAO);
    @Override
    public boolean UpdateUserPassword(UserDTO dto) throws SQLException, NoSuchAlgorithmException {
        return forgotPasswordDAO.update(new User(dto.getEID(),null,dto.getUPassword(),null));
    }

    @Override
    public boolean verifyUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException {
        return forgotPasswordDAO.verify(new User(dto.getEID(),null,dto.getUPassword(),null));
    }

    @Override
    public boolean passwordValidate(String password) {
        return Regex.validatePassword(password);
    }

    @Override
    public boolean employeeIdValidate(String employeeId) {
        return Regex.validateEID(employeeId);
    }
}
