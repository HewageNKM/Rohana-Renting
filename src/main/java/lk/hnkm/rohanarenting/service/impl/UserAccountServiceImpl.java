package lk.hnkm.rohanarenting.service.impl;

import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dao.DAOFactory;
import lk.hnkm.rohanarenting.dao.impl.UserAccountDAOImpl;
import lk.hnkm.rohanarenting.dao.interfaces.UserAccountDAO;
import lk.hnkm.rohanarenting.dto.UserDTO;
import lk.hnkm.rohanarenting.dto.tm.UserTM;
import lk.hnkm.rohanarenting.entity.User;
import lk.hnkm.rohanarenting.service.interfaces.UserAccountService;
import lk.hnkm.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountDAO userAccountDAO = (UserAccountDAOImpl)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER_ACCOUNT_DAO);
    @Override
    public ArrayList<UserTM> getUsers() throws SQLException {
        ArrayList<User> users = userAccountDAO.getAll();
        return getUserTMS(users);
    }

    @Override
    public boolean addUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException {
        return userAccountDAO.insert(new User(dto.getEID(),dto.getUName(),dto.getUPassword(),dto.getPermissionLevel()));
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException {
        return userAccountDAO.update(new User(dto.getEID(),dto.getUName(),dto.getUPassword(),dto.getPermissionLevel()));
    }

    @Override
    public boolean deleteUser(UserDTO dto) throws SQLException {
        return userAccountDAO.delete(new User());
    }

    @Override
    public UserDTO getUser(UserDTO dto) throws SQLException, NoSuchAlgorithmException {
        User user = userAccountDAO.get(new User(dto.getEID(),dto.getUName(),dto.getUPassword(),dto.getPermissionLevel()));
        return user == null ? null : new UserDTO(user.getEmployeeId(),user.getUsername(),user.getPassword(),user.getPermissionLevel());
    }

    @Override
    public ArrayList<UserTM> SearchUsers(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        ArrayList<User> users = userAccountDAO.search(searchPhrase);
        return getUserTMS(users);
    }

    @Override
    public boolean isUserExist(UserDTO dto) throws SQLException, NoSuchAlgorithmException {
        return userAccountDAO.verify(new User(dto.getEID(),dto.getUName(),dto.getUPassword(),dto.getPermissionLevel()));
    }

    @Override
    public boolean validateUserName(String userName) {
        return Regex.validateUsername(userName);
    }

    @Override
    public boolean validatePassword(String password) {
        return Regex.validatePassword(password);
    }

    @Override
    public boolean validateEmployeeId(String employeeId) {
        return Regex.validateEID(employeeId);
    }

    private ArrayList<UserTM> getUserTMS(ArrayList<User> users) {
        ArrayList<UserTM> userTMS = new ArrayList<>();
        for (User user:users) {
            JFXButton edit = new JFXButton();
            JFXButton delete = new JFXButton();
            edit.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            delete.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            userTMS.add(new UserTM(user.getEmployeeId(),user.getUsername(),user.getPassword(),user.getPermissionLevel(),edit,delete));
        }
        return userTMS;
    }
}
