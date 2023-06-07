package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.entity.Insurance;
import lk.ijse.rohanarenting.entity.Tool;

import java.sql.SQLException;

public interface ToolDAO extends CruidDAO<Tool> {
     Insurance getInsurance(Tool entity) throws SQLException;
     boolean updateToolWithoutAvailability(Tool entity) throws SQLException;
     boolean checkOrderStatus(Tool entity) throws SQLException;
     boolean verifyId(String id) throws SQLException;
}
