package lk.ijse.rohanarenting.service.impl;

import lk.ijse.rohanarenting.dto.tm.OrderTM;
import lk.ijse.rohanarenting.service.interfaces.OrderViewService;

import java.util.ArrayList;

public class OrderViewServiceImpl implements OrderViewService {
    @Override
    public ArrayList<OrderTM> getAllOrders() throws Exception {
        return orderViewDAO.getAll();
    }

    @Override
    public ArrayList<OrderTM> searchOrders(String searchPhrase) throws Exception {
        return orderViewDAO.search(searchPhrase);
    }
}
