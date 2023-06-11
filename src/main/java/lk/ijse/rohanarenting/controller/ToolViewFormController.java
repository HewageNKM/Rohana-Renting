package lk.ijse.rohanarenting.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rohanarenting.dto.tm.ToolTM;
import lk.ijse.rohanarenting.service.ServiceFactory;
import lk.ijse.rohanarenting.service.impl.ToolViewServiceImpl;
import lk.ijse.rohanarenting.service.interfaces.ToolViewService;
import lk.ijse.rohanarenting.utill.TableUtil;

import java.util.ArrayList;

public class ToolViewFormController {
    public TableView<ToolTM> toolsTable;
    public TableColumn columnID;
    public TableColumn columnName;
    public TableColumn columnAvailability;
    public TableColumn columnRentalRate;
    public TableColumn columnBrandName;
    public TextField searchFld;

    private final ToolViewService toolViewService = (ToolViewServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.TOOL_VIEW_SERVICE);
    public void initialize(){
        setCellValueFactories();
        loadToolTable();
        TableUtil.installCopy(toolsTable);
    }

    private void loadToolTable() {
        try {
            ArrayList<ToolTM> toolTMS = toolViewService.getAllTools();
            toolsTable.getItems().clear();
            toolsTable.getItems().addAll(toolTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            e.printStackTrace();
        }
    }

    private void setCellValueFactories() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("tID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        columnRentalRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        columnBrandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
    }

    public void searchFldOnAction(KeyEvent keyEvent) {
        if(searchFld.getText().trim().isEmpty()){
            loadToolTable();
        }else{
            try {
                ArrayList<ToolTM> filterList = toolViewService.searchTool("%"+searchFld.getText()+"%");
                toolsTable.getItems().clear();
                toolsTable.getItems().addAll(FXCollections.observableArrayList(filterList));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }
}
