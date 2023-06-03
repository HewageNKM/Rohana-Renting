package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.entity.User;

import java.sql.SQLException;

public interface LoginDAO extends CruidDAO<User> {
    public void insertUserLogInEntry(String employeeId) throws SQLException;
    public void insertUserLogoutEntry() throws SQLException;
}
