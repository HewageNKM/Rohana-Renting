/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/29/23, 6:50 PM
 *
 */

package lk.ijse.rohanarenting.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.rohanarenting.model.BoardModel;
import lk.ijse.rohanarenting.utill.notification.TopUpNotifications;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;


public class BoardFormController {
    public Label dateLabel;
    public AnchorPane changeRoot;
    public Label titleLabel;
    public BorderPane root;
    public Label employeeNameFld;
    public Label employeeIdFld;
    private Stage userVerifyStage = new  Stage();
    private String employeeId;

    public void initialize(String id) throws IOException {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
            dateLabel.setText(sdf.format(cal.getTime()));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        employeeId = id;
        setEmployeeDetails();
        loadDashboard();
    }

    private void setEmployeeDetails() {
        try {
            employeeNameFld.setText("User:  "+ BoardModel.getUserName(employeeId).toUpperCase());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
        employeeIdFld.setText("EID:  "+employeeId.toUpperCase());
    }

    public void loadDashboard() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));
        changeRoot.getChildren().removeAll();
        changeRoot.getChildren().setAll(parent);
        titleLabel.setText("Dashboard");
    }

    public void manageUserAccountBtnOnAction(ActionEvent actionEvent) throws IOException {
        loadSecureCheck("User");
    }

    public void manageEmployeeBtnOnAction(ActionEvent actionEvent) throws IOException {
        loadSecureCheck("Employee");
    }
    @FXML
    void dashboardBtnOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));
       changeRoot.getChildren().removeAll();
       changeRoot.getChildren().setAll(parent);
       titleLabel.setText("Dashboard");
    }

    @FXML
    void insuranceBtnOnAction(ActionEvent event) throws IOException {
       loadSecureCheck("Insurance");
    }

    private void loadSecureCheck(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserVerifyForm.fxml"));
        Parent parent = loader.load();
        UserVerifyController userVerifyController = loader.getController();
        userVerifyController.initialize(employeeId,title,changeRoot,titleLabel,userVerifyStage);
        userVerifyStage.setScene(new Scene(parent));
        userVerifyStage.centerOnScreen();
        userVerifyStage.setAlwaysOnTop(true);
        userVerifyStage.setTitle("User Verify");
        userVerifyStage.setResizable(false);
        userVerifyStage.getIcons().add(new Image("/img/user.png"));
        userVerifyStage.show();
    }

    @FXML
    void logoutBtnOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"User Will Be Logout !", ButtonType.OK,ButtonType.CANCEL).showAndWait().ifPresent(ButtonType->{
            if(ButtonType == ButtonType.OK){
                try {
                    Stage stage;
                    stage = (Stage) root.getScene().getWindow();
                    stage.close();
                    Stage loginFormStage = new Stage();
                    loginFormStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginForm.fxml")))));
                    loginFormStage.getIcons().add(new Image("/img/Login.png"));
                    loginFormStage.centerOnScreen();
                    loginFormStage.setTitle("Login Form");
                    loginFormStage.show();
                    BoardModel.insertUserLogout();
                    TopUpNotifications.logOut(employeeId.toUpperCase());
                } catch (IOException | SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                    e.printStackTrace();
                }
            }
        });
    }


    @FXML
    void refundBtnOnAction(ActionEvent event) throws IOException {
        loadSecureCheck("Refund");
    }

    @FXML
    void rentBtnOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/RentForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().setAll(parent);
        titleLabel.setText("Manage Rent");

    }

    @FXML
    void returnBtnOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/ReturnForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().setAll(parent);
        titleLabel.setText("Manage Return");
    }

    @FXML
    void toolBtnONAction(ActionEvent event) throws IOException {
        loadSecureCheck("Tool");
    }

    @FXML
    void vehicleBtnOnAction(ActionEvent event) throws IOException {
        loadSecureCheck("Vehicle");
    }

    public void customerBtnOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().setAll(parent);
        titleLabel.setText("Customer Form");
    }

}
