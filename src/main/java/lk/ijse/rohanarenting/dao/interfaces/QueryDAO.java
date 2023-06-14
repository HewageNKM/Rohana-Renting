package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.SuperDAO;
import lk.ijse.rohanarenting.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomDTO> execute(String query,Object...args) throws SQLException;
}
