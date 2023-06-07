package lk.ijse.rohanarenting.service.impl;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.ToolDTO;
import lk.ijse.rohanarenting.dto.tm.ToolTM;
import lk.ijse.rohanarenting.entity.Insurance;
import lk.ijse.rohanarenting.entity.Tool;
import lk.ijse.rohanarenting.service.interfaces.ToolService;
import lk.ijse.rohanarenting.utill.Genarate;
import lk.ijse.rohanarenting.utill.Regex;

import java.sql.SQLException;
import java.util.ArrayList;

public class ToolServiceImpl implements ToolService {

    @Override
    public boolean updateTool(ToolDTO dto) throws Exception {
        return toolDAO.update(new Tool(dto.getTID(), dto.getBrand(), dto.getName(), dto.getDescription(), dto.getAvalability(), dto.getRate()));
    }

    @Override
    public boolean addTool(ToolDTO dto) throws Exception {
        return toolDAO.insert(new Tool(dto.getTID(), dto.getBrand(), dto.getName(), dto.getDescription(), dto.getAvalability(), dto.getRate()));
    }

    @Override
    public boolean deleteTool(ToolDTO dto) throws Exception {
        return toolDAO.delete(new Tool(dto.getTID(), dto.getBrand(), dto.getName(), dto.getDescription(), dto.getAvalability(), dto.getRate()));
    }

    @Override
    public ArrayList<ToolTM> searchTool(String searchPhrase) throws Exception {
        return getTMS(toolDAO.search(searchPhrase));
    }

    private ArrayList<ToolTM> getTMS(ArrayList<Tool> tools) {
        ArrayList<ToolTM> toolTMS = new ArrayList<>();
        for (Tool tool : tools) {
            JFXButton showBtn = new JFXButton();
            JFXButton editBtn = new JFXButton();
            JFXButton deleteBtn = new JFXButton();
            editBtn.setStyle("-fx-background-image: url('img/edit.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            deleteBtn.setStyle("-fx-background-image: url('img/delete.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            showBtn.setStyle("-fx-background-image: url('img/show.png');-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 40px 40px;-fx-background-color: transparent");
            toolTMS.add(new ToolTM(tool.getToolID(), tool.getBrandName(), tool.getToolName(), tool.getDescription(), tool.getAvailability(), tool.getRate(), showBtn, editBtn, deleteBtn));
        }
        return toolTMS;
    }

    @Override
    public ArrayList<ToolTM> getAllTool() throws Exception {
        return getTMS(toolDAO.getAll());
    }

    @Override
    public ToolDTO getTool(ToolDTO dto) throws Exception {
         Tool tool = toolDAO.get(new Tool(dto.getTID(),null,null,null,null,null));
            return new ToolDTO(tool.getToolID(),tool.getBrandName(),tool.getToolName(),tool.getDescription(),tool.getAvailability(),tool.getRate());
    }
    public boolean checkOrderStatus(ToolDTO dto) throws Exception {
        return toolDAO.checkOrderStatus(new Tool(dto.getTID(), dto.getBrand(), dto.getName(), dto.getDescription(), dto.getAvalability(), dto.getRate()));
    }
    public boolean updateToolWithoutAvailability(ToolDTO dto) throws Exception {
        return toolDAO.updateToolWithoutAvailability(new Tool(dto.getTID(), dto.getBrand(), dto.getName(), dto.getDescription(), dto.getAvalability(), dto.getRate()));
    }

    public InsuranceDTO getInsurance(ToolDTO dto) throws Exception {
        Insurance insurance = toolDAO.getInsurance(new Tool(dto.getTID(), dto.getBrand(), dto.getName(), dto.getDescription(), dto.getAvalability(), dto.getRate()));
        return new InsuranceDTO(insurance.getInsuranceID(), insurance.getInsuranceName(), insurance.getInsuranceProvider(),insurance.getAgentName(),insurance.getAgentContact(),insurance.getEmail(),insurance.getAddress(),insurance.getFax(),insurance.getJoinedDate(),insurance.getExpireDate());
    }
    public boolean verify(ToolDTO dto) throws Exception {
        return toolDAO.verify(new Tool(dto.getTID(), dto.getBrand(), dto.getName(), dto.getDescription(), dto.getAvalability(), dto.getRate()));
    }
    public String generateID() throws SQLException {
        String id = Genarate.generateToolId();
            while (toolDAO.verifyId(id)) {
                id = Genarate.generateToolId();
            }
            return id;
    }
    @Override
    public boolean validateToolId(String toolId) {
        return Regex.validateToolId(toolId);
    }

    @Override
    public boolean validateToolName(String name) {
        return Regex.validateName(name);
    }

    @Override
    public boolean validateToolBrand(String brand) {
        return Regex.validateName(brand);
    }

    @Override
    public boolean validateToolPrice(String price) {
        return Regex.validateNumbersAndDecimals(price);
    }

    @Override
    public boolean validateToolDescription(String description) {
        return description.trim().isEmpty();
    }
}
