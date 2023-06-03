package lk.hnkm.rohanarenting.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CruidDAO<S> extends SuperDAO{
    public boolean insert(S entity) throws NoSuchAlgorithmException, SQLException;
    public boolean update(S entity) throws NoSuchAlgorithmException, SQLException;
    public boolean delete(S entity) throws SQLException;
    public S get(S entity) throws SQLException, NoSuchAlgorithmException;
    public ArrayList<S> getAll() throws SQLException;
    public boolean verify(S entity) throws SQLException, NoSuchAlgorithmException;
    public ArrayList<S> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException;
}
