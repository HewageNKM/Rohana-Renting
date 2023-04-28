package lk.hnkm.rohanarenting.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.hnkm.rohanarenting.dto.tm.LoginHistoryTM;
import lk.hnkm.rohanarenting.model.LoginHistoryModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginHistoryViewFormController {
    public TableView<LoginHistoryTM> loginHistoryTable;
    public TableColumn columnID;
    public TableColumn columnLogTime;
    public TableColumn ColumnDate;
    public TableColumn columnLogoutTime;
    public TextField searchFld;
    private ObservableList<LoginHistoryTM> loginHistoryTMS = FXCollections.observableArrayList();
    public  void initialize(){
        setCellValueFactory();
        loadAllLoginHistory();
    }

    private void loadAllLoginHistory() {
        try {
            loginHistoryTMS.clear();
            ArrayList<LoginHistoryTM> allLoginHistory = LoginHistoryModel.getAllLoginHistory();
            loginHistoryTMS.addAll(allLoginHistory);
            loginHistoryTable.setItems(loginHistoryTMS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnLogTime.setCellValueFactory(new PropertyValueFactory<>("logTime"));
        ColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnLogoutTime.setCellValueFactory(new PropertyValueFactory<>("logoutTime"));
    }

    public void searchOnAction(KeyEvent keyEvent) {

    }
}
