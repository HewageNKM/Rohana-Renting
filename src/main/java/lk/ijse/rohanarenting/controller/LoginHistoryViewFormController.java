package lk.ijse.rohanarenting.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rohanarenting.dto.tm.LoginHistoryTM;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.LoginHistoryServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.LoginHistoryService;

import java.util.ArrayList;

public class LoginHistoryViewFormController {
    public TableView<LoginHistoryTM> loginHistoryTable;
    public TableColumn columnID;
    public TableColumn columnLogTime;
    public TableColumn ColumnDate;
    public TableColumn columnLogoutTime;
    public TextField searchFld;
    private ObservableList<LoginHistoryTM> loginHistoryTMS = FXCollections.observableArrayList();
    private LoginHistoryService loginHistoryService = (LoginHistoryServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LOGIN_HISTORY_SERVICE);
    public  void initialize(){
        setCellValueFactory();
        loadAllLoginHistory();
    }

    private void loadAllLoginHistory() {
        try {
            loginHistoryTMS.clear();
            ArrayList<LoginHistoryTM> allLoginHistory = loginHistoryService.getAllLoginHistory();
            loginHistoryTMS.addAll(allLoginHistory);
            loginHistoryTable.setItems(loginHistoryTMS);
        } catch (Exception e) {
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
        if(searchFld.getText().isEmpty()){
            loadAllLoginHistory();
        }else {
            try {
                ArrayList<LoginHistoryTM> searchLoginHistory = loginHistoryService.searchLoginHistory("%"+searchFld.getText()+"%");
                loginHistoryTable.setItems(FXCollections.observableArrayList(searchLoginHistory));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }
}
