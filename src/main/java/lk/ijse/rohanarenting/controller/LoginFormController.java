
/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 *  Project Name: RohanaRenting
 *  Date and Time: 3/29/23, 6:50 PM
 *
 */

package lk.ijse.rohanarenting.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.LoginServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.LoginService;
import lk.ijse.rohanarenting.utill.Regex;
import lk.ijse.rohanarenting.utill.notification.TopUpNotifications;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

;


public class LoginFormController {
    public Label notifyLabel;
    public TextField employeeIdFld;
    public AnchorPane root;
    public PasswordField passwordFld;
    public Button loginBtn;
    public ImageView employeeIdViewer;
    public ImageView passwordViewer;
    public TextFlow missionTextFlow;
    private final LoginService loginService = (LoginServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LOGIN_SERVICE);
    public void initialize(){
        Text missionText = new Text("To Develop A Highly Successful, Profitable All Round Car Rental Business Which Provides Quality Services In Our Community And To Become A Standard For An Ideal Car Rental Business Not Only In The Colombo But Also Throughout The Sri Lanka Where We Intend Selling Our Franchise.");
        missionText.setStyle("-fx-font-weight: bolder");
        missionText.setStyle("-fx-font-size: 17");
        missionTextFlow.getChildren().add(missionText);
    }

    //Load Password Reset Form
    @FXML
    void forgotPasswordLinkOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ForgotPasswordForm.fxml")))));
        stage.getIcons().add(new Image("/img/reset.png"));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void loginBtnOnAction(ActionEvent event) throws IOException {
        if(Regex.validateEID(employeeIdFld.getText())&&Regex.validatePassword(passwordFld.getText())) {
            try {
                if (loginService.verifyUserCredentials(new UserDTO(employeeIdFld.getText().toUpperCase(),null, passwordFld.getText(),null))) {
                    loginService.insertUserLogInEntry(employeeIdFld.getText().toUpperCase());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BoardForm.fxml"));
                    Parent parent = loader.load();
                    BoardFormController boardController = loader.getController();
                    boardController.initialize(employeeIdFld.getText());
                    Stage stage;
                    stage = (Stage) root.getScene().getWindow();
                    stage.close();
                    Stage dashboardStage = new Stage();
                    dashboardStage.setMaximized(true);
                    dashboardStage.setScene(new Scene(parent));
                    dashboardStage.getIcons().add(new Image("/img/dashboard.png"));
                    dashboardStage.setTitle("Dashboard");
                    dashboardStageSetAction(dashboardStage);
                    dashboardStage.show();
                    TopUpNotifications.logIn(employeeIdFld.getText().toUpperCase());
                } else {
                    notifyLabel.setTextFill(Color.RED);
                    notifyLabel.setText("Invalid Employee ID or Password");
                }
            } catch (SQLException | NoSuchAlgorithmException | IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }

    private void dashboardStageSetAction(Stage stage) {
        stage.setOnCloseRequest(event -> {
            try {
                new Alert(Alert.AlertType.INFORMATION, "User Will Be Log out !",ButtonType.OK,ButtonType.NO).showAndWait().ifPresent(ButtonType-> {
                    if (ButtonType == ButtonType.OK) {
                        stage.close();
                        try {
                            loginService.insertUserLogoutEntry();
                            loadLoginForm();
                            TopUpNotifications.logOut(employeeIdFld.getText().toUpperCase());
                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                        }
                    }else {
                        event.consume();
                    }
                });
                event.consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void loadLoginForm() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginForm.fxml")))));
        stage.getIcons().add(new Image("/img/Login.png"));
        stage.setResizable(false);
        stage.show();
    }

    //Validate user name
    public void employeeIdValidate(KeyEvent keyEvent) {
        loginBtn.setDisable(true);
        if (loginService.employeeIdValidate(employeeIdFld.getText())) {
            employeeIdViewer.setImage(new Image("/img/checkmark.png"));
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Employee ID !");
        }else {
            employeeIdViewer.setImage(new Image("/img/cross.png"));
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Invalid Employee ID !");
        }
    }
    //Validate password
    public void passwordValidate(KeyEvent keyEvent) {
        loginBtn.setDisable(true);
        if (loginService.passwordValidate(passwordFld.getText())) {
            passwordViewer.setImage(new Image("/img/checkmark.png"));
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Password !");
        }else {
            passwordViewer.setImage(new Image("/img/cross.png"));
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Invalid Password !");
        }
    }

    public void refreshOnAction(MouseEvent mouseEvent) {
        if(loginService.passwordValidate(passwordFld.getText())&&loginService.employeeIdValidate(employeeIdFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("All Set !");
            loginBtn.setDisable(false);
        }else {
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Check The Field !");
            loginBtn.setDisable(true);
        }
    }

}
