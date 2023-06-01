package lk.hnkm.rohanarenting.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CruidDAO<S> extends SuperDAO{
    public boolean insert(S entity);
    public boolean update(S entity) throws NoSuchAlgorithmException, SQLException;
    public boolean delete(S entity);
    public S get();
    public ArrayList<S> getAll();
    public boolean verify(S entity) throws SQLException, NoSuchAlgorithmException;
}
