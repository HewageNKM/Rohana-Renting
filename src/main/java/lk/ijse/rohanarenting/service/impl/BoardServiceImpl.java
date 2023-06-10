package lk.ijse.rohanarenting.service.impl;

import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.dto.UserLoginDTO;
import lk.ijse.rohanarenting.entity.Employee;
import lk.ijse.rohanarenting.entity.User;
import lk.ijse.rohanarenting.entity.UserLogin;
import lk.ijse.rohanarenting.service.interfaces.BoardService;

public class BoardServiceImpl implements BoardService {
    @Override
    public void addUserLogin(UserLoginDTO dto) throws Exception {
        boardDAO.insert(new UserLogin(dto.getEmployeeId(),dto.getDate(),dto.getLogTime(),dto.getLogOutTime()));
    }

    @Override
    public UserDTO getUserNames(String id) throws Exception {
        User user =  boardDAO.getUserName(new Employee(id,null,null,null,null,null,null,null,null,null,null,null,null,null));
        return new UserDTO(null,user.getUsername(),null,null);
    }
}
