package lk.ijse.rohanarenting.service.impl;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.DatePicker;
import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.InsuranceDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.InsuranceDAO;
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.tm.InsuranceTM;
import lk.ijse.rohanarenting.entity.Insurance;
import lk.ijse.rohanarenting.service.interfaces.InsuranceService;
import lk.ijse.rohanarenting.utill.Regex;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class InsuranceServiceImpl implements InsuranceService {
    private final InsuranceDAO insuranceDAO = (InsuranceDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSURANCE_DAO);

    @Override
    public boolean addInsurance(InsuranceDTO dto) throws SQLException, NoSuchAlgorithmException {
        return insuranceDAO.insert(new Insurance(dto.getIID(),dto.getName(), dto.getInsuranceProvider(), dto.getAgentName(), dto.getAgentContact(), dto.getEmail(), dto.getAddress(), dto.getFax(),dto.getJoinedDate(),dto.getExpireDate()));
    }

    @Override
    public boolean updateInsurance(InsuranceDTO dto) throws SQLException, NoSuchAlgorithmException {
        return insuranceDAO.update(new Insurance(dto.getIID(),dto.getName(), dto.getInsuranceProvider(), dto.getAgentName(), dto.getAgentContact(), dto.getEmail(), dto.getAddress(), dto.getFax(),dto.getJoinedDate(),dto.getExpireDate()));
    }

    @Override
    public InsuranceDTO getInsurance(InsuranceDTO dto) throws SQLException, NoSuchAlgorithmException {
        Insurance insurance = insuranceDAO.get(new Insurance(dto.getIID(),dto.getName(), dto.getInsuranceProvider(), dto.getAgentName(), dto.getAgentContact(), dto.getEmail(), dto.getAddress(), dto.getFax(),dto.getJoinedDate(),dto.getExpireDate()));
        return new InsuranceDTO(insurance.getInsuranceID(),insurance.getInsuranceName(), insurance.getInsuranceProvider(), insurance.getAgentName(), insurance.getAgentContact(), insurance.getEmail(), insurance.getAddress(), insurance.getFax(),insurance.getJoinedDate(),insurance.getExpireDate());
    }

    @Override
    public ArrayList<InsuranceTM> getAllInsurance() throws SQLException {
        return getTMS(insuranceDAO.getAll());
    }

    @Override
    public boolean verifyInsurance(InsuranceDTO dto) throws SQLException, NoSuchAlgorithmException {
        return insuranceDAO.verify(new Insurance(dto.getIID(),dto.getName(), dto.getInsuranceProvider(), dto.getAgentName(), dto.getAgentContact(), dto.getEmail(), dto.getAddress(), dto.getFax(),dto.getJoinedDate(),dto.getExpireDate()));
    }

    @Override
    public boolean validateInsuranceDuration(DatePicker joinedDate, DatePicker expireDate) {
       return ChronoUnit.MONTHS.between(joinedDate.getValue(), expireDate.getValue())>=12&&expireDate.getValue().isAfter(LocalDate.now());
    }

    @Override
    public ArrayList<InsuranceTM> searchInsurance(String searchPhrase) throws SQLException, NoSuchAlgorithmException {
        return getTMS(insuranceDAO.search(searchPhrase));
    }
    private ArrayList<InsuranceTM> getTMS(ArrayList<Insurance> insurances){
        ArrayList<InsuranceTM> insuranceTMS = new ArrayList<>();
        for (Insurance insurance : insurances) {
            JFXButton update = new JFXButton();
            update.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            insuranceTMS.add(new InsuranceTM(insurance.getInsuranceID(),insurance.getInsuranceName(), insurance.getInsuranceProvider(), insurance.getAgentName(), insurance.getAgentContact(), insurance.getEmail(), insurance.getAddress(), insurance.getFax(),insurance.getJoinedDate(),insurance.getExpireDate(),update));
        }
        return insuranceTMS;
    }

    @Override
    public boolean validateInsuranceId(String id) {
        return Regex.validateToolId(id) || Regex.validateVehicleID(id);
    }

    @Override
    public boolean validateInsuranceName(String id) {
        return Regex.validateNameWithSpaces(id);
    }

    @Override
    public boolean validateInsuranceProvider(String id) {
        return Regex.validateNameWithSpaces(id);
    }

    @Override
    public boolean validateInsuranceMobileNumber(String id) {
        return Regex.validateMobile(id);
    }

    @Override
    public boolean validateInsuranceAddress(String id) {
        return Regex.validateAddress(id);
    }

    @Override
    public boolean validateInsuranceEmail(String id) {
        return Regex.validateEmail(id);
    }

    @Override
    public boolean validateInsuranceFax(String id) {
        return Regex.validateFax(id);
    }
}
