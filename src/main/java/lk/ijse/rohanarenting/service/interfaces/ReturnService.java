package lk.ijse.rohanarenting.service.interfaces;

import javafx.collections.ObservableList;
import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.ReturnDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.ReturnDAO;
import lk.ijse.rohanarenting.dto.tm.ReturnOrderTM;
import lk.ijse.rohanarenting.dto.tm.ReturnTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnService extends SuperService {
    ReturnDAO returnDAO = (ReturnDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RETURN_DAO);
    boolean saveReturnOrder(ObservableList<ReturnTM>returnList,String id) throws SQLException;
    ArrayList<ReturnOrderTM> getOrderTM(String rentId) throws SQLException;
    boolean verifyToolRentID(String toolRentId) throws SQLException;
    boolean isReturnIdExist(String id) throws SQLException;
    ReturnTM getReturnTM(ReturnOrderTM returns, String returnId) throws SQLException;
    boolean verifyVehicleRentId(String id) throws SQLException;
    Double getTotalFine(ObservableList<ReturnTM> returnList);
    boolean checkRefund(String rentId) throws SQLException;
}
