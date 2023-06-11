package lk.ijse.rohanarenting.dao.interfaces;

import lk.ijse.rohanarenting.dao.CruidDAO;
import lk.ijse.rohanarenting.dto.tm.ReturnOrderTM;
import lk.ijse.rohanarenting.dto.tm.ReturnTM;
import lk.ijse.rohanarenting.entity.Return;
import lk.ijse.rohanarenting.entity.ReturnDetails;
import lk.ijse.rohanarenting.entity.Tool;
import lk.ijse.rohanarenting.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnDAO extends CruidDAO {
    boolean verifyVehicleRentId(String id) throws SQLException;
    ReturnTM getReturnTM(ReturnOrderTM returnOrderTM, String returnId) throws SQLException;
    boolean isReturnIdExist(String id) throws SQLException;
    boolean verifyToolRentID(String toolRentId) throws SQLException;
    ArrayList<ReturnOrderTM> getOrder(String rentId) throws SQLException;
    boolean saveVehicleReturn(Return entity) throws SQLException;
    boolean saveToolReturn(Return entity) throws SQLException;
    boolean saveToolReturnDetails(ArrayList<ReturnDetails> returnList) throws SQLException;
    boolean updateToolRent(ArrayList<Tool> returnList,String rentId) throws SQLException;
    boolean saveVehicleReturnDetails(ArrayList<ReturnDetails> returnDetailsList) throws SQLException;
    boolean updateVehicleRent(ArrayList<Vehicle> returnList, String rentId) throws SQLException;
    boolean updateTool(ArrayList<Tool> tools) throws SQLException;
    boolean updateVehicle(ArrayList<Vehicle> vehicles) throws SQLException;
    boolean checkRefund(String rentId) throws SQLException;
}
