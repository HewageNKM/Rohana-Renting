package lk.ijse.rohanarenting.service.impl;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.dto.tm.UserTM;
import lk.ijse.rohanarenting.entity.User;
import lk.ijse.rohanarenting.service.interfaces.UserAccountService;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserAccountServiceImpl implements UserAccountService {
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
