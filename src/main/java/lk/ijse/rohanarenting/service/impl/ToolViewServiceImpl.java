package lk.ijse.rohanarenting.service.impl;

import lk.ijse.rohanarenting.dto.tm.ToolTM;
import lk.ijse.rohanarenting.entity.Tool;
import lk.ijse.rohanarenting.service.interfaces.ToolViewService;

import java.util.ArrayList;

public class ToolViewServiceImpl implements ToolViewService {
    @Override
    public ArrayList<ToolTM> getAllTools() throws Exception {
        ArrayList<ToolTM> toolTMS = new ArrayList<>();
        ArrayList<Tool> tools = toolViewDAO.getAll();
        for (Tool tool : tools) {
            toolTMS.add(new ToolTM(
                    tool.getToolID(),
                    tool.getBrandName(),
                    tool.getToolName(),
                    tool.getDescription(),
                    tool.getAvailability(),
                    tool.getRate(),
                    null,
                    null,
                    null
            ));
        }
       return toolTMS;
    }

    @Override
    public ArrayList<ToolTM> searchTool(String searchPhrase) throws Exception {
        ArrayList<ToolTM> toolTMS = new ArrayList<>();
        ArrayList<Tool> tools = toolViewDAO.search(searchPhrase);
        for (Tool tool : tools) {
            toolTMS.add(new ToolTM(
                    tool.getToolID(),
                    tool.getBrandName(),
                    tool.getToolName(),
                    tool.getDescription(),
                    tool.getAvailability(),
                    tool.getRate(),
                    null,
                    null,
                    null
            ));
        }
        return toolTMS;
    }
}
