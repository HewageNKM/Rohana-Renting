package lk.ijse.rohanarenting.dao.impl;

import lk.ijse.rohanarenting.dao.interfaces.CustomerDAO;
import lk.ijse.rohanarenting.entity.Customer;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean insert(Customer entity) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE NIC = ?", entity.getNic());
        if(resultSet.next()){
            return false;
        }else {
            return CruidUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?)", entity.getCustomerId(), entity.getFirstName(), entity.getLastName(), entity.getNic(), entity.getBirthday(), entity.getMobileNumber(), entity.getEmail(), entity.getStreet(), entity.getCity(), entity.getZipCode());
        }
    }

    @Override
    public boolean update(Customer entity) throws NoSuchAlgorithmException, SQLException {
        return CruidUtil.execute("UPDATE customer SET First_Name=?,Last_Name=?,NIC=?,Birthday=?,Mobile_Number=?,Email=?,Street=?,City=?,Zip_Code=? WHERE CID=?",
                entity.getFirstName(),
                entity.getLastName(),
                entity.getNic(),
                entity.getBirthday(),
                entity.getMobileNumber(),
                entity.getEmail(),
                entity.getStreet(),
                entity.getCity(),
                entity.getZipCode(),
                entity.getCustomerId());
    }

    @Override
    public boolean delete(Customer entity) throws SQLException {
        return CruidUtil.execute("DELETE FROM customer WHERE CID=?",entity.getCustomerId());
    }

    @Override
    public Customer get(Customer entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM customer WHERE CID=?",entity.getCustomerId());
        if(resultSet.next()){
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getInt(10)
            );
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer");
        ArrayList<Customer> arrayList = new ArrayList<>();
        return getCustomers(resultSet, arrayList);
    }

    private ArrayList<Customer> getCustomers(ResultSet resultSet, ArrayList<Customer> arrayList) throws SQLException {
        while (resultSet.next()){
            arrayList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getInt(10)

            ));
        }
        return arrayList;
    }

    @Override
    public boolean verify(Customer entity) throws SQLException, NoSuchAlgorithmException {
        ResultSet resultSet= CruidUtil.execute("SELECT * FROM customer WHERE CID = ?", entity.getCustomerId());
        return resultSet.next();
    }

    @Override
    public ArrayList<Customer> search(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ArrayList<Customer> arrayList = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM customer WHERE CID LIKE ? OR First_Name LIKE ? OR Last_Name LIKE ? OR NIC LIKE ? OR Mobile_Number LIKE ? OR Email LIKE ? OR Street LIKE ? OR City LIKE ? OR Zip_Code LIKE ? OR Birthday LIKE ?",
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase,
                searchPhrase);
        return getCustomers(resultSet, arrayList);
    }
}
