package lk.ijse.rohanarenting.service.interfaces;

import javafx.collections.ObservableList;
import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.RentDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.RentDAO;
import lk.ijse.rohanarenting.dto.*;
import lk.ijse.rohanarenting.dto.tm.ToolCartTM;
import lk.ijse.rohanarenting.dto.tm.ToolRentOrderJesperReportDetailTM;
import lk.ijse.rohanarenting.dto.tm.VehicleCartTM;
import lk.ijse.rohanarenting.dto.tm.VehicleRentOrderJesperReportDetailTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RentService extends SuperService {

    RentDAO rentDAO = (RentDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RENT_DAO);
    boolean placeVehicleOrder(ArrayList<VehicleOrderDetailsDTO> orderDetailsDTOS, VehicleOrderDTO vehicleOrderDTO) throws SQLException;
    boolean placeToolOrder(ArrayList<ToolOrderDetailsDTO> toolOrderDetailsDTOS, ToolOrderDTO toolOrderDTO) throws SQLException;
    boolean verifyCustomerId(String CID) throws SQLException;
    boolean verifyVehicleRentId(String rentId) throws SQLException;
    boolean verifyToolRentId(String rentId) throws SQLException;
    boolean verifyVehicleId(String vehicleId) throws SQLException;
    boolean verifyToolId(String toolId) throws SQLException;
    VehicleCartTM getVehicleCartModel(VehicleOrderDTO vehicleOrderDTO,String id,Integer rentDays) throws SQLException;
    Double getVehicleTotal(ObservableList<VehicleCartTM> vehicleCartTableData);
    ToolCartTM getToolCartModel(ToolOrderDTO toolOrderDTO,String id,Integer rentDays) throws SQLException;
    Double getToolTotal(ObservableList<ToolCartTM> toolCartTMS);
    boolean checkToolCartDuplicate(ObservableList<ToolCartTM> toolCartTMS, String toolId);
    boolean checkVehicleCartDuplicate(ObservableList<VehicleCartTM> vehicleCartTMS, String vehicleId);
    CustomerDTO getCustomer(String customerId) throws SQLException;
    ArrayList<VehicleRentOrderJesperReportDetailTM> getVehicleJesperReport(ObservableList<VehicleCartTM> vehicleCartTMS);
    ArrayList<ToolRentOrderJesperReportDetailTM> getToolJesperReport(ObservableList<ToolCartTM> toolCartTMS);
    void checkInsuranceTable() throws SQLException;
    boolean checkValidVehicleInsurance(VehicleCartTM vehicleCartTM,Integer rentDays) throws SQLException;
    boolean checkValidToolInsurance(ToolCartTM toolCartTM,Integer rentDays) throws SQLException;
    boolean validateVehicleRentId(String rentId);
    boolean validateToolRentId(String rentId);
    boolean validateRentDays(String vehicleId);
}
