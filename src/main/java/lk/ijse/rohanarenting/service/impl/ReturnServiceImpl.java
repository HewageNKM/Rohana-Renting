package lk.ijse.rohanarenting.service.impl;

import javafx.collections.ObservableList;
import lk.ijse.rohanarenting.db.DBConnection;
import lk.ijse.rohanarenting.dto.tm.ReturnOrderTM;
import lk.ijse.rohanarenting.dto.tm.ReturnTM;
import lk.ijse.rohanarenting.entity.Return;
import lk.ijse.rohanarenting.entity.ReturnDetails;
import lk.ijse.rohanarenting.entity.Tool;
import lk.ijse.rohanarenting.entity.Vehicle;
import lk.ijse.rohanarenting.service.interfaces.ReturnService;
import lk.ijse.rohanarenting.utill.Regex;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReturnServiceImpl implements ReturnService {
    @Override
    public boolean saveReturnOrder(ObservableList<ReturnTM>returnList,String id) throws SQLException {
            Connection connection = null;
            if (Regex.validateToolRentId(id)) {
                try {
                    connection = DBConnection.getInstance().getConnection();
                    connection.setAutoCommit(false);
                    boolean isReturnSaved = returnDAO.saveToolReturn(new Return(returnList.get(0).getReturnId(),id, LocalDate.now(), LocalTime.now()));
                    if (isReturnSaved) {
                       boolean isReturnDetailsSaved = returnDAO.saveToolReturnDetails(getToolReturnDetailsArrayList(returnList));
                        if (isReturnDetailsSaved) {
                            boolean isReturnUpdated = returnDAO.updateToolRent(getToolsArrayList(returnList),id);
                            if (isReturnUpdated) {
                                boolean isToolUpdated = returnDAO.updateTool(getToolsArrayList(returnList));
                                if (isToolUpdated) {
                                    connection.commit();
                                    return true;
                                } else {
                                    connection.rollback();
                                    return false;
                                }
                            } else {
                                connection.rollback();
                                return false;
                            }
                        } else {
                            connection.rollback();
                            return false;
                        }
                    } else {
                        connection.rollback();
                        return false;
                    }
                } finally {
                    assert connection != null;
                    connection.setAutoCommit(true);
                }
            } else {
                try {
                    connection = DBConnection.getInstance().getConnection();
                    connection.setAutoCommit(false);
                      boolean  isVehicleReturnSaved = returnDAO.saveVehicleReturn(new Return(returnList.get(0).getReturnId(),id, LocalDate.now(), LocalTime.now()));
                    if (isVehicleReturnSaved) {
                        boolean isVehicleReturnDetailsSaved = returnDAO.saveVehicleReturnDetails(getVehicleReturnDetailsArrayList(returnList));
                        if (isVehicleReturnDetailsSaved) {
                            boolean isVehicleRentUpdated = returnDAO.updateVehicleRent(getVehicleArraylist(returnList),id);
                            if (isVehicleRentUpdated) {
                                boolean isVehicleUpdated = returnDAO.updateVehicle(getVehicleArraylist(returnList));
                                if (isVehicleUpdated) {
                                    connection.commit();
                                    return true;
                                } else {
                                    connection.rollback();
                                    return false;
                                }
                            } else {
                                connection.rollback();
                                return false;
                            }
                        } else {
                            connection.rollback();
                            return false;
                        }
                    } else {
                        connection.rollback();
                        return false;
                    }
                } finally {
                    assert connection != null;
                    connection.setAutoCommit(true);
                }
            }
    }

    private ArrayList<Vehicle> getVehicleArraylist(ObservableList<ReturnTM> returnList) {
        ArrayList<Vehicle> vehicleArrayList = new ArrayList<>();
        for (ReturnTM returnTM:returnList) {
            vehicleArrayList.add(new Vehicle(returnTM.getProductId(),null,null,null,"Available",null,null));
        }
        return vehicleArrayList;
    }

    private ArrayList<Return> getVehicleReturnArraylist(ObservableList<ReturnTM> returnList) {
        ArrayList<Return> returnArrayList = new ArrayList<>();
        for (ReturnTM returnTM: returnList) {
            returnArrayList.add(new Return(returnTM.getReturnId(),returnTM.getProductId(),null,null));
        }
        return returnArrayList;
    }

    private ArrayList<ReturnDetails> getVehicleReturnDetailsArrayList(ObservableList<ReturnTM> returnList) {
        ArrayList<ReturnDetails> returnDetailsArrayList = new ArrayList<>();
        for (ReturnTM returnTM:returnList) {
            returnDetailsArrayList.add(new ReturnDetails(returnTM.getReturnId(),returnTM.getProductId(),returnTM.getReturnDate(),returnTM.getReturnedDate(),returnTM.getLateDays(),returnTM.getFine()));
        }
        return returnDetailsArrayList;
    }

    private ArrayList<Return> getToolReturnArraylist(ObservableList<ReturnTM> returnList) {
        ArrayList<Return> returnArrayList = new ArrayList<>();
        for (ReturnTM returnTM:returnList) {
            returnArrayList.add(new Return(returnTM.getReturnId(),returnTM.getProductId(),null,null));
        }
        return returnArrayList;
    }

    private ArrayList<Tool> getToolsArrayList(ObservableList<ReturnTM> returnList) {
        ArrayList<Tool> toolArrayList = new ArrayList<>();
        for (ReturnTM returnTM:returnList) {
            toolArrayList.add(new Tool(returnTM.getProductId(),null,null,null,"Available",null));
        }
        return toolArrayList;
    }

    private ArrayList<ReturnDetails> getToolReturnDetailsArrayList(ObservableList<ReturnTM> returnList) {
        ArrayList<ReturnDetails> returnDetailsArrayList = new ArrayList<>();
        for (ReturnTM returnTM:returnList) {
            returnDetailsArrayList.add(new ReturnDetails(returnTM.getReturnId(),returnTM.getProductId(),returnTM.getReturnDate(),returnTM.getReturnedDate(),returnTM.getLateDays(),returnTM.getFine()));
        }
        return returnDetailsArrayList;
    }

    @Override
    public ArrayList<ReturnOrderTM> getOrderTM(String rentId) throws SQLException {
        return returnDAO.getOrder(rentId);
    }

    @Override
    public boolean verifyToolRentID(String toolRentId) throws SQLException {
        return returnDAO.verifyToolRentID(toolRentId);
    }

    @Override
    public boolean isReturnIdExist(String id) throws SQLException {
        return returnDAO.isReturnIdExist(id);
    }

    @Override
    public ReturnTM getReturnTM(ReturnOrderTM returns, String returnId) throws SQLException {
        return returnDAO.getReturnTM(returns,returnId);
    }

    @Override
    public boolean verifyVehicleRentId(String id) throws SQLException {
        return returnDAO.verifyVehicleRentId(id);
    }

    @Override
    public Double getTotalFine(ObservableList<ReturnTM> returnList) {
        Double total = 0.0;
        for (ReturnTM returnTM:returnList) {
            total+=returnTM.getFine();
        }
        return total;
    }

    @Override
    public boolean checkRefund(String rentId) throws SQLException {
        return  returnDAO.checkRefund(rentId);
    }
}
