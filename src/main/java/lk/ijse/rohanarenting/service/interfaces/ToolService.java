package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.ToolDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.ToolDAO;
import lk.ijse.rohanarenting.dto.InsuranceDTO;
import lk.ijse.rohanarenting.dto.ToolDTO;
import lk.ijse.rohanarenting.dto.tm.ToolTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ToolService extends SuperService {
      ToolDAO toolDAO = (ToolDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TOOL_DAO);
      boolean updateTool(ToolDTO dto) throws Exception;
        boolean addTool(ToolDTO dto) throws Exception;
        boolean deleteTool(ToolDTO dto) throws Exception;
        ArrayList<ToolTM> searchTool(String searchPhrase) throws Exception;
        ArrayList<ToolTM> getAllTool() throws Exception;
        ToolDTO getTool(ToolDTO dto) throws Exception;
        boolean checkOrderStatus(ToolDTO dto) throws Exception;
        InsuranceDTO getInsurance(ToolDTO dto) throws Exception;
        boolean updateToolWithoutAvailability(ToolDTO dto) throws Exception;
        public boolean verify(ToolDTO dto) throws Exception;
        String generateID() throws SQLException;
        boolean validateToolId(String toolId);
        boolean validateToolName(String name);
        boolean validateToolBrand(String brand);
        boolean validateToolPrice(String price);
        boolean validateToolDescription(String description);
}
