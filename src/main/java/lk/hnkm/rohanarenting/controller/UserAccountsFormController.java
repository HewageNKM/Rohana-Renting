/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/29/23, 9:17 PM
 *
 */

package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.hnkm.rohanarenting.dto.User;
import lk.hnkm.rohanarenting.dto.tm.UserTM;
import lk.hnkm.rohanarenting.model.UserAccountsModel;
import lk.hnkm.rohanarenting.utill.Regex;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserAccountsFormController {

    public TextField employeeFld;
    public TextField serachPhaseFld;
    public PasswordField passwordFld;
    public Label notifyLabel;
    public JFXButton deleteBtn;
    public JFXButton saveBtn;
    public JFXRadioButton bPermission;
    public ToggleGroup permissionGroup;
    public TableView<UserTM> usersTable;
    public JFXRadioButton aPermission;
    public TableColumn empIdColumn;
    public TableColumn employeeNameColumn;
    public TableColumn permissionColumn;
    public TableColumn editColumn;
    public TableColumn deleteColumn;
    public TextField userNameFld;
    public TableColumn passwordColumn;
    ObservableList<UserTM> usersList = FXCollections.observableArrayList();

    public void initialize() {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        setCellValueFactory();
        loadTableData();
    }

    private void setCellValueFactory() {
        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("EID"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("UPassword"));
        permissionColumn.setCellValueFactory(new PropertyValueFactory<>("permissionLevel"));
        editColumn.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
    }

    private void loadTableData() {
        try {
            usersList.clear();
            ArrayList<UserTM> arrayList =  UserAccountsModel.getAllUsers();
            usersList.addAll(arrayList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
        usersTable.setItems(usersList);
    }

    public void enterOnAction(ActionEvent actionEvent) {
        User user = null;
        try {
            user = UserAccountsModel.getUserDetail(employeeFld.getText());
            if(user==null){
                new Alert(Alert.AlertType.ERROR,"No User Account Found !").show();
                clearFields();
            }else {
                passwordFld.setText(user.getUPassword());
                if(user.getPermissionLevel().equals("A")){
                    aPermission.setSelected(true);
                }else {
                    bPermission.setSelected(true);
                }
                userNameFld.setText(user.getUName());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        try {
           if(UserAccountsModel.isUserExist(employeeFld.getText())){
               new Alert(Alert.AlertType.CONFIRMATION,"New User Data Will Be Updated !",ButtonType.YES).showAndWait().ifPresent(buttonType -> {
                   if(buttonType==ButtonType.YES){
                       try {
                           if(UserAccountsModel.updateUser(new User(employeeFld.getText(),userNameFld.getText(),passwordFld.getText(),aPermission.isSelected()?"A":"B"))) {
                               new Alert(Alert.AlertType.INFORMATION, "User Data Updated !").show();
                               clearFields();
                               loadTableData();
                           }else {
                               new Alert(Alert.AlertType.ERROR, "User Data Not Updated !").show();
                           }
                       } catch (SQLException e) {
                           new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                          e.printStackTrace();
                       }
                   }
               });
           }else {
                if(UserAccountsModel.addUser(new User(employeeFld.getText(),userNameFld.getText(),passwordFld.getText(),aPermission.isSelected()?"A":"B"))){
                     new Alert(Alert.AlertType.INFORMATION,"User Data Saved !").show();
                    loadTableData();
                     clearFields();
                }else {
                     new Alert(Alert.AlertType.ERROR,"User Data Not Saved !").show();
                }
           }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void clearFields() {
        employeeFld.clear();
        passwordFld.clear();
        userNameFld.clear();
        aPermission.setSelected(false);
        bPermission.setSelected(false);
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        notifyLabel.setText("");
        employeeFld.setStyle(null);
        passwordFld.setStyle(null);
        userNameFld.setStyle(null);
    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void employeeValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        try {
            if (Regex.validateEID(employeeFld.getText())&& UserAccountsModel.verifyEmployeeID(employeeFld.getText())) {
                notifyLabel.setTextFill(Color.GREEN);
                notifyLabel.setText("Valid Employee ID !");
                employeeFld.setStyle("-fx-border-color: green;");
            }else {
                notifyLabel.setTextFill(Color.RED);
                notifyLabel.setText("InValid Employee ID !");
                employeeFld.setStyle("-fx-border-color: red;");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void passwordValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validatePassword(passwordFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Password !");
            passwordFld.setStyle("-fx-border-color: green;");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Password !");
            passwordFld.setStyle("-fx-border-color: red;");
        }
    }

    public void refreshOnClick(MouseEvent mouseEvent) {
        try {
            if(Regex.validateEID(employeeFld.getText())&&Regex.validatePassword(passwordFld.getText())&&Regex.validatePassword(passwordFld.getText())& UserAccountsModel.verifyEmployeeID(employeeFld.getText())&&Regex.validateUsername(userNameFld.getText())){
                notifyLabel.setTextFill(Color.GREEN);
                notifyLabel.setText("All Set !");
                userNameFld.setText(userNameFld.getText().toUpperCase());
                employeeFld.setText(employeeFld.getText().toUpperCase());
                saveBtn.setDisable(false);
                deleteBtn.setDisable(false);
            }else {
                notifyLabel.setTextFill(Color.RED);
                notifyLabel.setText("Please Fill All Fields Correctly !");
                saveBtn.setDisable(true);
                deleteBtn.setDisable(true);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.WARNING, "User Details Will Be Deleted !", ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                try {
                    if (UserAccountsModel.deleteUser(employeeFld.getText())) {
                        new Alert(Alert.AlertType.INFORMATION, "User Details Deleted !").show();
                        clearFields();
                        loadTableData();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "User Details Not Deleted !").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                    e.printStackTrace();
                }
            }
        });
    }

    public void filteringFld(KeyEvent keyEvent) {

    }

    public void userNameValidate(KeyEvent keyEvent) {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        if (Regex.validateUsername(userNameFld.getText())) {
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Name !");
            userNameFld.setStyle("-fx-border-color: green;");
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Name !");
            userNameFld.setStyle("-fx-border-color: red;");
        }
    }
}
