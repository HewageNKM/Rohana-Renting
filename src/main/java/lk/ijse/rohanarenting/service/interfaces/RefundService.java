package lk.ijse.rohanarenting.service.interfaces;

import javafx.collections.ObservableList;
import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.RefundDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.RefundDAO;
import lk.ijse.rohanarenting.dto.CustomerDTO;
import lk.ijse.rohanarenting.dto.tm.RefundOrderTM;
import lk.ijse.rohanarenting.dto.tm.RefundTM;
import lk.ijse.rohanarenting.entity.Refund;
import lk.ijse.rohanarenting.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RefundService extends SuperService {
    RefundDAO refundDAO = (RefundDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REFUND_DAO);
    boolean placeRefund(Refund refund, ObservableList<RefundTM> refundTMS) throws SQLException;
    CustomerDTO getCustomer(String id) throws SQLException;

    Double getTotal(ObservableList<RefundTM> refundTMS);


    ArrayList<RefundOrderTM> getRefundOrderTM(String id) throws SQLException;

    Boolean verifyToolRentId(String id) throws SQLException;

    boolean checkToolReturn(String id) throws SQLException;

    Boolean verifyVehicleRentId(String id) throws SQLException;

    boolean checkVehicleReturn(String id) throws SQLException;

    RefundTM getRefundTM(RefundOrderTM refundOrderTM);

    boolean validateToolRentId(String id);

    boolean validateVehicleRentId(String id);

    String genarateRefundId() throws SQLException;
}
