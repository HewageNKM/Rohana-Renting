package lk.hnkm.rohanarenting.dao.impl;

import lk.hnkm.rohanarenting.dao.interfaces.UserAccountDAO;
import lk.hnkm.rohanarenting.entity.User;
import lk.hnkm.rohanarenting.utill.CruidUtil;
import lk.hnkm.rohanarenting.utill.security.Encrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserAccountDAOImpl implements UserAccountDAO {
    @Override
    public boolean insert(User entity) throws NoSuchAlgorithmException, SQLException {
        String password = Encrypt.encrypt(entity.getPassword());
        return CruidUtil.execute("INSERT INTO user VALUES (?,?,?,?)", entity.getEmployeeId(), entity.getUsername(),password, entity.getPermissionLevel());
    }

    @Override
    public boolean update(User entity) throws NoSuchAlgorithmException, SQLException {
        String password = Encrypt.encrypt(entity.getPassword());
        return CruidUtil.execute("UPDATE user SET `Employee ID`=?,UName = ?,UPassword=?,Permission_Level=? WHERE `Employee ID`=?", entity.getEmployeeId(), entity.getUsername(),password, entity.getPermissionLevel(), entity.getEmployeeId());
    }

    @Override
    public boolean delete(User entity) throws SQLException {
        return CruidUtil.execute("DELETE FROM user WHERE `Employee ID`=?",entity.getEmployeeId());
    }

    @Override
    public User get(User entity) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE `Employee ID` = ?",entity.getEmployeeId());
        if (resultSet.next()){
            return new User(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user");
        ArrayList<User> arrayList = new ArrayList<>();
        return getUsers(resultSet, arrayList);
    }

    @Override
    public boolean verify(User entity) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM user WHERE `Employee ID` = ? ",entity.getEmployeeId());
        return resultSet.next();
    }

    @Override
    public ArrayList<User> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM user WHERE `Employee ID` LIKE ? OR UName LIKE ?  OR Permission_Level LIKE ?" ,searchPhrase,searchPhrase,searchPhrase);
        ArrayList<User> arrayList = new ArrayList<>();
        return getUsers(resultSet, arrayList);
    }

    private ArrayList<User> getUsers(ResultSet resultSet, ArrayList<User> arrayList) throws SQLException {
        while (resultSet.next()) {
            String EID = resultSet.getString(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            String permissionLevel = resultSet.getString(4);
            arrayList.add(new User(EID, name, password, permissionLevel));
        }
        return arrayList;
    }
}
