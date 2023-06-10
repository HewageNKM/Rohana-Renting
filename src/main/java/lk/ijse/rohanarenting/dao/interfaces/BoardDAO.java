package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.entity.Employee;
import lk.ijse.rohanarenting.entity.User;
import lk.ijse.rohanarenting.entity.UserLogin;

import java.sql.SQLException;

public interface BoardDAO extends CruidDAO<UserLogin> {
      User getUserName(Employee entity) throws SQLException;
}
