package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.OrderViewDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.OrderViewDAO;
import lk.ijse.rohanarenting.dto.tm.OrderTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.util.ArrayList;

public interface OrderViewService extends SuperService {
    OrderViewDAO orderViewDAO = (OrderViewDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_VIEW_DAO);
    ArrayList<OrderTM> getAllOrders() throws Exception;
    ArrayList<OrderTM> searchOrders(String searchPhrase) throws Exception;
}
