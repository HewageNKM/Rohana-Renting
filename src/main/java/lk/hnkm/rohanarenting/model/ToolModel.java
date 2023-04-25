/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/4/23, 8:45 PM
 *
 */

package lk.hnkm.rohanarenting.model;


import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.Insurance;
import lk.hnkm.rohanarenting.dto.Tool;
import lk.hnkm.rohanarenting.dto.tm.JasperReportToolTM;
import lk.hnkm.rohanarenting.dto.tm.ToolTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ToolModel {
    public static Boolean saveTool(Tool tool) throws SQLException {
        return CruidUtil.execute("INSERT INTO tool VALUES(?,?,?,?,?,?)",tool.getTID(),tool.getBrand(),tool.getName(),tool.getDescription(),tool.getAvalability(),tool.getRate());
    }
    public static Boolean updateTool(Tool tool) throws SQLException {
        return CruidUtil.execute("UPDATE tool SET Brand =? , Name = ?, Description = ?, Availability = ?,Rate_Per_Day=? WHERE TID = ? ",tool.getBrand(),tool.getName(),tool.getDescription(),tool.getAvalability(),tool.getRate(),tool.getTID());
    }

    public static Tool getToolDetail(String TID) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT * FROM tool  WHERE TID = ?;",TID);
        if(resultSet.next()){
            Tool tool = new Tool();
            tool.setTID(resultSet.getString(1));
            tool.setBrand(resultSet.getString(2));
            tool.setName(resultSet.getString(3));
            tool.setDescription(resultSet.getString(4));
            tool.setAvalability(resultSet.getString(5));
            tool.setRate(resultSet.getDouble(6));
            return tool;
        }
        return null;
    }
    public static Boolean deleteTool(String TID) throws SQLException {
        return CruidUtil.execute("DELETE FROM tool WHERE TID = ?",TID);
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

    public static ArrayList<ToolTM> searchTools(String searchPhrase) throws SQLException {
         ArrayList<ToolTM> filterTools = new ArrayList<>();
         ResultSet resultSet =CruidUtil.execute("SELECT * FROM tool WHERE TID LIKE ? OR Brand LIKE ? OR Name LIKE ? OR Description LIKE ? OR Availability LIKE ? OR Rate_Per_Day LIKE ?",searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase,searchPhrase);
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

    public static Boolean checkOrderStatus(String tid) throws SQLException {
        ResultSet resultSet =  CruidUtil.execute("SELECT * FROM tool_rent_order_detail WHERE TID = ? AND Return_Status = 0 ",tid);
        return resultSet.next();

    }

    public static Insurance getInsurance(String tid) throws SQLException {
        ResultSet resultSet =CruidUtil.execute("SELECT * FROM tool_insurance WHERE IID = ?",tid);
        Insurance insurance = null;
        if (resultSet.next()){
           insurance =  new Insurance(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getDate(9).toLocalDate(),resultSet.getDate(10).toLocalDate());
        }
        if(insurance != null){
            return  insurance;
        }else {
            return new Insurance("No Details","No Details","No Details","No Details","No Details","No Details","No Details","No Details",LocalDate.now(), LocalDate.now());
        }
    }

    public static ArrayList<JasperReportToolTM> getJasperToolTM(String tid) throws SQLException {
        ResultSet resultSet = CruidUtil.execute("SELECT tool_rent_order_detail.Rent_ID,tool_rent_order_detail.TID,tool_rent_order.Date FROM tool_rent_order_detail LEFT JOIN tool_rent_order ON tool_rent_order_detail.Rent_ID = tool_rent_order.Rent_ID WHERE tool_rent_order_detail.TID = ? ORDER BY tool_rent_order.Date DESC LIMIT 10;",tid);
        ArrayList<JasperReportToolTM> jasperReportToolTMS = new ArrayList<>();
        while (resultSet.next()){
           jasperReportToolTMS.add(new JasperReportToolTM(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3)));
        }
        if(jasperReportToolTMS.size()>0){
            return jasperReportToolTMS;
        }else {
            jasperReportToolTMS.add(new JasperReportToolTM("No Details", "No Details",Date.valueOf(LocalDate.now())));
            return jasperReportToolTMS;
        }
    }
}
