package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.ToolViewDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.ToolViewDAO;
import lk.ijse.rohanarenting.dto.tm.ToolTM;
import lk.ijse.rohanarenting.service.SuperService;

import java.util.ArrayList;

public interface ToolViewService extends SuperService {
    ToolViewDAO toolViewDAO = (ToolViewDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TOOL_VIEW_DAO);
    ArrayList<ToolTM> getAllTools() throws Exception;
    ArrayList<ToolTM> searchTool(String searchPhrase) throws Exception;
}
