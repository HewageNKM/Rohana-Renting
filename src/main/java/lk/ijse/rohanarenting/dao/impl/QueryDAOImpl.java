package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.QueryDAO;
import lk.ijse.rohanarenting.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomDTO> execute(String query, Object... args) throws SQLException {
        return null;
    }
}
