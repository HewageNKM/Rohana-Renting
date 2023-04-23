/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/4/23, 8:45 PM
 *
 */

package lk.hnkm.rohanarenting.model;


import com.jfoenix.controls.JFXButton;
import lk.hnkm.rohanarenting.dto.Tool;
import lk.hnkm.rohanarenting.dto.tm.ToolTM;
import lk.hnkm.rohanarenting.utill.CruidUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            tool.setAvalability(resultSet.getInt(5));
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
          toolTMS.add(
                    new ToolTM(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                           0 < (resultSet.getInt(5)) ? "Available" : "Not Available",
                            resultSet.getDouble(6),
                            new JFXButton("Edit"),
                            new JFXButton("Delete")
                    )
          );
      }
        return toolTMS;
    }
}
