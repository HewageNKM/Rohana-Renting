package lk.ijse.rohanarenting.model;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rohanarenting.dto.tm.ToolTM;
import lk.ijse.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToolViewModel {
    public static ArrayList<ToolTM> searchTools(String searchPhrase) throws SQLException {
        ArrayList<ToolTM> filterTools = new ArrayList<>();
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool WHERE TID LIKE ? OR Brand LIKE ? OR Name LIKE ? OR Description LIKE ? OR Availability LIKE ? OR Rate_Per_Day LIKE ?",searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase);
        while (resultSet.next()){
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            filterTools.add(new ToolTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6),
                    showBtn,editBtn,deleteBtn
            ));
        }
        return filterTools;
    }

    public static ArrayList<ToolTM> getTools() throws SQLException {
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM tool");
        ArrayList <ToolTM> toolTMS = new ArrayList<>();
        while (resultSet.next()){
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            toolTMS.add(
                    new ToolTM(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getDouble(6),showBtn,
                            editBtn,deleteBtn
                    )
            );
        }
        return toolTMS;
    }
}
