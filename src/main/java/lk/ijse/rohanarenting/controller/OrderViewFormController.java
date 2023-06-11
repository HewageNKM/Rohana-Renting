package lk.ijse.rohanarenting.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rohanarenting.dto.tm.OrderTM;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.OrderViewServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.OrderViewService;
import lk.ijse.rohanarenting.utill.TableUtil;

import java.util.ArrayList;

public class OrderViewFormController {
    public TextField searchFld;
    public TableView<OrderTM> vehiclesTable;
    public TableColumn columnID;
    public TableColumn ColumnCustomerId;
    public TableColumn columnDate;
    public TableColumn columnTime;
    public TableColumn columnStatus;
    private ObservableList<OrderTM> orderTMS = FXCollections.observableArrayList();
    private final OrderViewService orderViewService = (OrderViewServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.ORDER_VIEW_SERVICE);
    public void initialize(){
        setCellValueFactories();
        loadOrderTable();
        TableUtil.installCopy(vehiclesTable);
    }

    private void loadOrderTable() {
        try {
            orderTMS.clear();
            ArrayList<OrderTM> orderList = orderViewService.getAllOrders();
            orderTMS.addAll(orderList);
            vehiclesTable.setItems(orderTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setCellValueFactories() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    public void searchOnAction(KeyEvent keyEvent) {
        if (searchFld.getText().trim().isEmpty()) {
            loadOrderTable();
        } else {
            try {
                ArrayList<OrderTM> filterList = orderViewService.searchOrders("%" + searchFld.getText() + "%");
                orderTMS.clear();
                orderTMS.addAll(FXCollections.observableArrayList(filterList));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }
}
