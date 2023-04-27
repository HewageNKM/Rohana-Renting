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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lk.hnkm.rohanarenting.dto.User;
import lk.hnkm.rohanarenting.dto.tm.UserTM;
import lk.hnkm.rohanarenting.model.UserAccountsModel;
import lk.hnkm.rohanarenting.notification.TopUpNotifications;
import lk.hnkm.rohanarenting.utill.Regex;
import lk.hnkm.rohanarenting.utill.TableUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserFormController {

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
    private Stage stage = new Stage();
    ObservableList<UserTM> usersList = FXCollections.observableArrayList();

    public void initialize() {
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
        setCellValueFactory();
        loadTableData();
        TableUtil.installCopy(usersTable);
    }
    // Set Cell  Value Factory
    private void setCellValueFactory() {
        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("EID"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("UPassword"));
        permissionColumn.setCellValueFactory(new PropertyValueFactory<>("permissionLevel"));
        editColumn.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
    }
    // Load Table Data
    private void loadTableData() {
        try {
            usersList.clear();
            ArrayList<UserTM> arrayList =  UserAccountsModel.getAllUsers();
            for (UserTM userTM: arrayList) {                                // Set Edit and Delete Button Action
                setEditButtonAction(userTM.getEditBtn());
                setDeleteButtonAction(userTM.getDeleteBtn());
            }
            usersList.addAll(arrayList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            throw new RuntimeException(e);
        }
        usersTable.setItems(usersList);
    }

    private void setDeleteButtonAction(JFXButton deleteBtn) {
        deleteBtn.setOnAction(event -> {
        UserTM user = usersTable.getSelectionModel().getSelectedItem();
        if(user!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure ?").showAndWait().ifPresent(buttonType -> {
                if(buttonType==ButtonType.OK){
                    try {
                        if(UserAccountsModel.deleteUser(user.getEID())){
                            new Alert(Alert.AlertType.INFORMATION,"User Deleted !").show();
                            loadTableData();
                            clearFields();
                        }else {
                            new Alert(Alert.AlertType.ERROR,"User Not Deleted !").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
                        e.printStackTrace();
                    }
                }
            });
        }else {
           new Alert(Alert.AlertType.ERROR,"Please Select A User !").show();
        }
        });
    }

    private void setEditButtonAction(JFXButton editBtn) {
       editBtn.setOnAction(event -> {
           UserTM user = usersTable.getSelectionModel().getSelectedItem();
           if(user!=null){
               employeeFld.setText(user.getEID());
               userNameFld.setText(user.getName());
               if(user.getPermissionLevel().equals("A")){
                   aPermission.setSelected(true);
               }else {
                   bPermission.setSelected(true);
               }
               saveBtn.setDisable(false);
               deleteBtn.setDisable(false);
               employeeFld.setDisable(true);
           }else {
               new Alert(Alert.AlertType.ERROR,"Please Select A User !").show();
           }
       });
    }

    // Employee ID Fld Enter Action
    public void enterOnAction(ActionEvent actionEvent) {
        User user = null;
        try {
            user = UserAccountsModel.getUserDetail(employeeFld.getText());
            if(user==null){
                new Alert(Alert.AlertType.ERROR,"No User Account Found !").show();
                clearFields();
            }else {
                if(user.getPermissionLevel().equals("A")){
                    aPermission.setSelected(true);
                }else {
                    bPermission.setSelected(true);
                }
                userNameFld.setText(user.getUName());
            }
            employeeFld.setDisable(true);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }
    //Save Button Action
    public void saveBtnOnAction(ActionEvent actionEvent) {
        try {
           if(UserAccountsModel.isUserExist(employeeFld.getText())){
               new Alert(Alert.AlertType.CONFIRMATION,"New User Data Will Be Updated !",ButtonType.YES).showAndWait().ifPresent(buttonType -> {
                   if(buttonType==ButtonType.YES){
                       try {
                           if(UserAccountsModel.updateUser(new User(employeeFld.getText(),userNameFld.getText(), passwordFld.getText(),aPermission.isSelected()?"A":"B"))) {
                               TopUpNotifications.success("User Data Updated !");
                               clearFields();
                               loadTableData();
                           }else {
                               new Alert(Alert.AlertType.ERROR, "User Data Not Updated !").show();
                           }
                       } catch (SQLException | NoSuchAlgorithmException e) {
                           new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                          e.printStackTrace();
                       }
                   }
               });
           }else {
                if(UserAccountsModel.addUser(new User(employeeFld.getText(),userNameFld.getText(),passwordFld.getText(),aPermission.isSelected()?"A":"B"))){
                    TopUpNotifications.success("User Data Saved !");
                    loadTableData();
                     clearFields();
                }else {
                     new Alert(Alert.AlertType.ERROR,"User Data Not Saved !").show();
                }
           }
        } catch (SQLException | NoSuchAlgorithmException e) {
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
        employeeFld.setDisable(false);
    }
    //Clear All Fields
    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    // Validate Employee ID
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
    // Validate Password
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
    // Validate All the Fields
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
    // Delete Button Action
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
    // Validate User Name
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
    // Search User
    public void searchPhaseFldOnAction(KeyEvent keyEvent) {
        if (serachPhaseFld.getText().trim().isEmpty()) {
            loadTableData();
        } else {
            try {
                ArrayList<UserTM> arrayList = UserAccountsModel.searchUser("%"+serachPhaseFld.getText()+"%");
                for (UserTM userTM:arrayList) {
                    setEditButtonAction(userTM.getEditBtn());
                    setDeleteButtonAction(userTM.getDeleteBtn());
                }
                ObservableList<UserTM> users = FXCollections.observableArrayList(arrayList);
                usersTable.setItems(users);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }

    public void loginHistoryBtnOnAction(ActionEvent actionEvent) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginHistoryView.fxml"))));
        stage.setTitle("Login History");
        stage.centerOnScreen();
        stage.getIcons().add(new Image("/img/search.png"));
        stage.show();
    }
}
