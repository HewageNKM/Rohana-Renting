package lk.hnkm.rohanarenting.dao.interfaces;

import lk.hnkm.rohanarenting.dao.CruidDAO;
import lk.hnkm.rohanarenting.entity.User;

import java.sql.SQLException;

public interface LoginDAO extends CruidDAO<User> {
    public void insertUserLogInEntry(String employeeId) throws SQLException;
    public void insertUserLogoutEntry() throws SQLException;
}
