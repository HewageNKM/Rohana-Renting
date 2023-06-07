package lk.ijse.rohanarenting.service.impl;

import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.entity.User;
import lk.ijse.rohanarenting.service.interfaces.ForgotPasswordService;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ForgotPasswordServiceImpl implements ForgotPasswordService {
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
