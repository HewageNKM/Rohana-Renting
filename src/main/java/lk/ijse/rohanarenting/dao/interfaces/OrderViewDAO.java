package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.dto.tm.OrderTM;

import java.sql.SQLException;

public interface OrderViewDAO extends CruidDAO<OrderTM> {
    String getVehicleStatus(String rentId) throws SQLException;
    String getToolStatus(String rentId) throws SQLException;
}
