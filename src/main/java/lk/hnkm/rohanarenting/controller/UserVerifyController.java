/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/22/23, 2:33 PM
 *
 */

package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.hnkm.rohanarenting.model.UserVerifyModel;
import lk.hnkm.rohanarenting.utill.Regex;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserVerifyController {
    public TextField employeeIdFld;
    public PasswordField passwordFld;
    public JFXButton verifyBtn;
    public ImageView employeeIdViewer;
    public ImageView passwordViewer;
    public Label notifyLabel;
    private String title;
    private AnchorPane changeRoot;
    private Label titleLabel;
    private Stage verifyUserStage;

    public void initialize(String employeeId, String title, AnchorPane changeRoot, Label titleLabel, Stage verifyUserStage) {
        this.changeRoot = changeRoot;
        this.title = title;
        this.titleLabel = titleLabel;
        this.verifyUserStage = verifyUserStage;
        employeeIdFld.setText(employeeId.toUpperCase());
        verifyBtn.setDisable(true);
    }
    public void employeeIdValidate(KeyEvent keyEvent) {
        verifyBtn.setDisable(true);
        if(Regex.validateEID(employeeIdFld.getText())){
            employeeIdFld.setStyle("-fx-border-color: green");
            employeeIdViewer.setImage(new Image("/img/checkmark.png"));
            notifyLabel.setText("Valid Employee ID");
        }else {
            employeeIdFld.setStyle("-fx-border-color: red");
            employeeIdViewer.setImage(new Image("/img/cross.png"));
            notifyLabel.setText("Invalid Employee ID");
        }
    }

    public void passwordValidate(KeyEvent keyEvent) {
        verifyBtn.setDisable(true);
        if(Regex.validatePassword(passwordFld.getText())){
            passwordFld.setStyle("-fx-border-color: green");
            passwordViewer.setImage(new Image("/img/checkmark.png"));
            notifyLabel.setText("Valid Password");
        }else {
            passwordFld.setStyle("-fx-border-color: red");
            passwordViewer.setImage(new Image("/img/cross.png"));
            notifyLabel.setText("Invalid Password");
        }
    }

    public void verifyBtnOnAction(ActionEvent actionEvent) throws IOException {
        try {
           Boolean isExist = UserVerifyModel.verifyUser(employeeIdFld.getText(),passwordFld.getText());
            if(isExist!=null){
                if(isExist){
                    verifyUserStage.close();
                    loadForm();
                }else {
                    notifyLabel.setStyle("-fx-text-fill: green");
                    notifyLabel.setText("You Don't Have Permission");
                }
            }else {
                notifyLabel.setStyle("-fx-text-fill: red");
                notifyLabel.setText("Invalid Employee ID or Password");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadForm() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/"+title+"Form.fxml"));
        changeRoot.getChildren().removeAll();
        changeRoot.getChildren().setAll(parent);
        titleLabel.setText("Manage"+" "+title);
    }

    public void onClickRefresh(MouseEvent mouseEvent) {
        if(Regex.validateEID(employeeIdFld.getText())&&Regex.validatePassword(passwordFld.getText())) {
            verifyBtn.setDisable(false);
        }else {
            verifyBtn.setDisable(true);
        }
    }
}
