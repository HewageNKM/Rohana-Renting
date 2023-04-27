package lk.hnkm.rohanarenting.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.hnkm.rohanarenting.dto.tm.ToolTM;
import lk.hnkm.rohanarenting.model.ToolModel;
import lk.hnkm.rohanarenting.model.ToolViewModel;
import lk.hnkm.rohanarenting.utill.TableUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ToolViewFormController {
    public TableView<ToolTM> toolsTable;
    public TableColumn columnID;
    public TableColumn columnName;
    public TableColumn columnAvailability;
    public TableColumn columnRentalRate;
    public TableColumn columnBrandName;
    public TextField searchFld;
    public void initialize(){
        setCellValueFactories();
        loadToolTable();
        TableUtil.installCopy(toolsTable);
    }

    private void loadToolTable() {
        try {
            ArrayList<ToolTM> toolTMS = ToolViewModel.getTools();
            toolsTable.getItems().clear();
            toolsTable.getItems().addAll(toolTMS);
        } catch (SQLException e) {
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
                ArrayList<ToolTM> filterList = ToolViewModel.searchTools("%"+searchFld.getText()+"%");
                toolsTable.getItems().clear();
                toolsTable.getItems().addAll(FXCollections.observableArrayList(filterList));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
                e.printStackTrace();
            }
        }
    }
}
