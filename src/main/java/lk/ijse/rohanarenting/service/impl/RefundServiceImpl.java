package lk.ijse.rohanarenting.service.impl;

import javafx.collections.ObservableList;
import lk.ijse.rohanarenting.db.DBConnection;
import lk.ijse.rohanarenting.dto.CustomerDTO;
import lk.ijse.rohanarenting.dto.tm.RefundOrderTM;
import lk.ijse.rohanarenting.dto.tm.RefundTM;
import lk.ijse.rohanarenting.entity.*;
import lk.ijse.rohanarenting.service.interfaces.RefundService;
import lk.ijse.rohanarenting.utill.Genarate;
import lk.ijse.rohanarenting.utill.Regex;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RefundServiceImpl implements RefundService {
    @Override
    public boolean placeRefund(Refund refund, ObservableList<RefundTM> refundTMS) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            System.out.println(refund.getRentId());
            System.out.println(Regex.validateToolRentId(refund.getRentId()));
            if (Regex.validateToolRentId(refund.getRentId())) {
                boolean isRefundTableUpdated = refundDAO.updateToolRefundTable(refund);
                if (isRefundTableUpdated) {
                    boolean isRefundDetailTableUpdated =refundDAO.updateToolRefundDetailTable(getToolRefundDetailsArray(refundTMS,refund));
                    if (isRefundDetailTableUpdated) {
                        boolean isToolRentTableUpdated = refundDAO.updateToolRentTable(getToolsArrayList(refundTMS));
                        if (isToolRentTableUpdated) {
                            boolean isToolTableUpdated = refundDAO.updateToolTable(getToolsArrayList(refundTMS));
                            if (isToolTableUpdated) {
                                connection.commit();
                                System.out.println("Success");
                                return true;
                            } else {
                                connection.rollback();
                                System.out.println("Tool Rollback 1");
                                return false;
                            }
                        } else {
                            connection.rollback();
                            System.out.println("Tool Rollback 2");
                            return false;
                        }
                    } else {
                        connection.rollback();
                        System.out.println("Tool Rollback 3");
                        return false;
                    }
                } else {
                    connection.rollback();
                    System.out.println("Tool Rollback 4");
                    return false;
                }
            } else {
                    boolean isRefundTableUpdated = refundDAO.updateVehicleRefundTable(refund);
                    if (isRefundTableUpdated) {
                       boolean isRefundDetailTableUpdated = refundDAO.updateVehicleRefundDetailTable(getVehicleRefundDetailsArray(refundTMS,refund));
                        if (isRefundDetailTableUpdated) {
                            boolean isEquipRentTableUpdated = refundDAO.updateVehicleRentTable(getVehicleArrayList(refundTMS));
                            if (isEquipRentTableUpdated) {
                                boolean isEquipTableUpdated = refundDAO.updateVehicleTable(getVehicleArrayList(refundTMS));
                                if (isEquipTableUpdated) {
                                    connection.commit();
                                    System.out.println("Success");
                                    return true;
                                } else {
                                    connection.rollback();
                                    System.out.println("Vehicle Rollback 1");
                                    return false;
                                }
                            } else {
                                connection.rollback();
                                System.out.println("Vehicle Rollback 2");
                                return false;
                            }
                        } else {
                            connection.rollback();
                            System.out.println("Vehicle Rollback 3");
                            return false;
                        }
                    } else {
                        connection.rollback();
                        System.out.println("Vehicle Rollback 4");
                        return false;
                    }
            }
        }finally {
            assert connection != null;
            connection.setAutoCommit(true);
        }
    }

    private ArrayList<Vehicle> getVehicleArrayList(ObservableList<RefundTM> refundTMS) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        for (RefundTM refundTM:refundTMS) {
            vehicles.add(new Vehicle(refundTM.getProductId(),null,null,null,null,null,null));
        }
        return vehicles;
    }

    private ArrayList<RefundDetails> getVehicleRefundDetailsArray(ObservableList<RefundTM> refundTMS, Refund refund) {
        ArrayList<RefundDetails> refundDetails = new ArrayList<>();
        for (RefundTM refundTM: refundTMS) {
            refundDetails.add(new RefundDetails(refund.getRefundId(),refundTM.getProductId(),refundTM.getTotal(),refundTM.getRefundAmount()));
        }
        return refundDetails;
    }

    private ArrayList<Tool> getToolsArrayList(ObservableList<RefundTM> refundTMS) {
        ArrayList<Tool> tools = new ArrayList<>();
         for (RefundTM refundTM: refundTMS ) {
            tools.add(new Tool(refundTM.getProductId(),null,null,null,null,null));
        }
         return tools;
    }

    private ArrayList<RefundDetails> getToolRefundDetailsArray(ObservableList<RefundTM> refundTMS, Refund refund) {
        ArrayList<RefundDetails> refundDetails = new ArrayList<>();
        for (RefundTM refundTM: refundTMS) {
            refundDetails.add(new RefundDetails(refund.getRefundId(),refundTM.getProductId(),refundTM.getTotal(),refundTM.getRefundAmount()));
        }
        return refundDetails;
    }

    @Override
    public CustomerDTO getCustomer(String id) throws SQLException {
        Customer customer = refundDAO.getCustomer(id);
        return new CustomerDTO(customer.getCustomerId(),customer.getFirstName(),customer.getLastName(),customer.getNic(),customer.getBirthday(),customer.getMobileNumber(),customer.getEmail(),customer.getStreet(),customer.getCity(),customer.getZipCode());
    }

    @Override
    public Double getTotal(ObservableList<RefundTM> refundTMS) {
        Double total = 0.0;
        for (RefundTM refundTM : refundTMS) {
            total += refundTM.getRefundAmount();
        }
        return total;
    }


    @Override
    public ArrayList<RefundOrderTM> getRefundOrderTM(String id) throws SQLException {
        return refundDAO.getRefundOrderTM(id);
    }

    @Override
    public Boolean verifyToolRentId(String id) throws SQLException {
        return refundDAO.verifyToolRentId(id);
    }

    @Override
    public boolean checkToolReturn(String id) throws SQLException {
        return refundDAO.checkToolReturn(id);
    }

    @Override
    public Boolean verifyVehicleRentId(String id) throws SQLException {
        return refundDAO.verifyVehicleRentId(id);
    }

    @Override
    public boolean checkVehicleReturn(String id) throws SQLException {
        return refundDAO.checkVehicleReturn(id);
    }

    @Override
    public RefundTM getRefundTM(RefundOrderTM refundOrderTM) {
        return new RefundTM(refundOrderTM.getProductId(),refundOrderTM.getRentDays(),refundOrderTM.getTotal(),refundOrderTM.getTotal()*0.95);
    }

    @Override
    public boolean validateToolRentId(String id) {
        return Regex.validateToolRentId(id);
    }

    @Override
    public boolean validateVehicleRentId(String id) {
        return Regex.validateVehicleRentId(id);
    }

    @Override
    public String genarateRefundId() throws SQLException {
        String id = Genarate.genarateRefundId();
        while (true) {
          if (refundDAO.isRefundIdExist(id)) {
              id = Genarate.genarateRefundId();
          } else {
              return id;
          }
        }
    }
}
