package lk.ijse.rohanarenting.service.impl;

import lk.ijse.rohanarenting.service.interfaces.UserVerifyService;

public class UserVerifyServiceImpl implements UserVerifyService {
    @Override
    public Boolean verifyUser(String employeeId, String password) throws Exception {
        return userVerifyDAO.verifyUser(employeeId, password);
    }
}
