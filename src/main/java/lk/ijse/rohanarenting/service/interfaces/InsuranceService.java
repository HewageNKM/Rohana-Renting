package lk.ijse.rohanarenting.service.interfaces;

import javafx.scene.control.DatePicker;
import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.InsuranceDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.InsuranceDAO;
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.tm.InsuranceTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InsuranceService extends SuperService {
    InsuranceDAO insuranceDAO = (InsuranceDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSURANCE_DAO);
    boolean addInsurance(InsuranceDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean updateInsurance(InsuranceDTO dto) throws SQLException, NoSuchAlgorithmException;
    InsuranceDTO getInsurance(InsuranceDTO dto) throws SQLException, NoSuchAlgorithmException;
    ArrayList<InsuranceTM> getAllInsurance() throws SQLException;
    boolean verifyInsurance(InsuranceDTO dto) throws SQLException, NoSuchAlgorithmException;
    boolean validateInsuranceDuration(DatePicker joinedDate, DatePicker expireDate);
    ArrayList<InsuranceTM> searchInsurance(String searchPhrase) throws SQLException, NoSuchAlgorithmException;
    boolean validateInsuranceId(String id);
    boolean validateInsuranceName(String id);
    boolean validateInsuranceProvider(String id);
    boolean validateInsuranceMobileNumber(String id);
    boolean validateInsuranceAddress(String id);
    boolean validateInsuranceEmail(String id);
    boolean validateInsuranceFax(String id);
}
