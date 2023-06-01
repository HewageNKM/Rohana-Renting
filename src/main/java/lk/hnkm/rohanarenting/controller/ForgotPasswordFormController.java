/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/29/23, 6:51 PM
 *
 */

package lk.hnkm.rohanarenting.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lk.hnkm.rohanarenting.dto.UserDTO;
import lk.hnkm.rohanarenting.service.ServiceFactory;
import lk.hnkm.rohanarenting.service.impl.ForgotPasswordServiceImpl;
import lk.hnkm.rohanarenting.service.interfaces.ForgotPasswordService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ForgotPasswordFormController {

    public Label notifyLabel;
    public JFXButton changePasswordBtn;
    public AnchorPane root;
    public ImageView employeeImageViewer;
    public ImageView prFldImageViewer;
    public TextFlow aboutUsTextFlow;
    public TextField employeeFld;
    public TextField oldPasswordFld;
    public TextField newPasswordFld;
    public TextField reenterPasswordFld;

    @FXML
    private ImageView secondPasswordFlsImageViewer;

    @FXML
    private ImageView newPasswordFldImageViewver;
    private final ForgotPasswordService forgotPasswordService = (ForgotPasswordServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.FORGOT_PASSWORD);

    @FXML
    void CancelBtnOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel?", ButtonType.NO, ButtonType.YES).showAndWait().ifPresent(buttonType -> {
            if(buttonType==ButtonType.YES){
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
            }
        });
    }
    public void initialize(){
        Text aboutUsText = new Text("Welcome to Our Vehicle Rental Business! We Are Committed to Providing Our Customers with the Best Possible Experience When It Comes to Renting a Car. Our Mission Is to Make Sure That You Have a Safe, Comfortable, and Enjoyable Ride Every Time You Rent from Us. We Pride Ourselves on Our Excellent Customer Service, and We Are Always Here to Help You with Any Questions or Concerns You May Have. Thank You for Choosing Us for Your Vehicle Rental Needs!");
        aboutUsText.setStyle("-fx-text-fill: black");
        aboutUsText.setStyle("-fx-font-size: 17");
        aboutUsTextFlow.getChildren().add(aboutUsText);
        changePasswordBtn.setDisable(true);
    }
    @FXML
    void changePasswordBtnOnAction(ActionEvent event) {
        try {
            if(forgotPasswordService.verifyUser(new UserDTO(employeeFld.getText(),null,oldPasswordFld.getText(),null))){
                if (forgotPasswordService.UpdateUserPassword(new UserDTO(employeeFld.getText(),null,newPasswordFld.getText(),null) )){
                    new Alert(Alert.AlertType.INFORMATION,"New Password Update Successfully !").show();
                    clearFields();
                }else {
                    new Alert(Alert.AlertType.ERROR,"New Password Update Fails !").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"User Details Not Found !").show();
            }

        } catch (SQLException | NoSuchAlgorithmException e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
        }
    }

    public void refreshOnClick(MouseEvent mouseEvent) {
        if(forgotPasswordService.employeeIdValidate(employeeFld.getText()) && forgotPasswordService.passwordValidate(oldPasswordFld.getText()) && forgotPasswordService.passwordValidate(newPasswordFld.getText()) && reenterPasswordFld.getText().equals(newPasswordFld.getText())){
            changePasswordBtn.setDisable(false);
            newPasswordFldImageViewver.setImage(new Image("img/checkmark.png"));
            secondPasswordFlsImageViewer.setImage(new Image("/img/checkmark.png"));
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("All Set !");
        }
        else {
            changePasswordBtn.setDisable(true);
            notifyLabel.setTextFill(Color.RED);
            notifyLabel.setText("Please Fill All Fields Correctly");
        }
    }
    private void clearFields() {
        employeeFld.clear();
        oldPasswordFld.clear();
        newPasswordFld.clear();
        reenterPasswordFld.clear();
        employeeImageViewer.setImage(null);
        prFldImageViewer.setImage(null);
        newPasswordFldImageViewver.setImage(null);
        secondPasswordFlsImageViewer.setImage(null);
        notifyLabel.setText("");
    }

    public void employeeIdValidate(KeyEvent keyEvent) {
        changePasswordBtn.setDisable(true);
        if(forgotPasswordService.employeeIdValidate(employeeFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            employeeImageViewer.setImage(new Image("/img/checkmark.png"));
            notifyLabel.setText("Correct User Name !");
        }else {
            notifyLabel.setTextFill(Color.RED);
            employeeImageViewer.setImage(new Image("/img/cross.png"));
            notifyLabel.setText("Incorrect User Name !");
        }
    }

    public void oldPasswordValidate(KeyEvent keyEvent) {
        changePasswordBtn.setDisable(true);
        if(forgotPasswordService.passwordValidate(oldPasswordFld.getText())){
            prFldImageViewer.setImage(new Image("/img/checkmark.png"));
        }else {
            prFldImageViewer.setImage(new Image("/img/cross.png"));
        }
    }

    public void newPasswordValidate(KeyEvent keyEvent) {
        changePasswordBtn.setDisable(true);
        if(forgotPasswordService.passwordValidate(newPasswordFld.getText())){
            notifyLabel.setTextFill(Color.GREEN);
            notifyLabel.setText("Valid Password !");
            newPasswordFldImageViewver.setImage(new Image("img/checkmark.png"));
        }else {
            newPasswordFldImageViewver.setImage(new Image("img/cross.png"));

        }
    }

    public void reenterPasswordValidate(KeyEvent keyEvent) {
        if(newPasswordFld.getText().equals(reenterPasswordFld.getText())){
            secondPasswordFlsImageViewer.setImage(new Image("/img/checkmark.png"));
        }else {
            secondPasswordFlsImageViewer.setImage(new Image("/img/cross.png"));
            changePasswordBtn.setDisable(true);
        }
    }
}
