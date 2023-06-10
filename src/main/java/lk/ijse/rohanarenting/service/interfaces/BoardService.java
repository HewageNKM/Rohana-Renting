package lk.ijse.rohanarenting.service.interfaces;

import lk.ijse.rohanarenting.dao.DAOFactory;
import lk.ijse.rohanarenting.dao.impl.BoardDAOImpl;
import lk.ijse.rohanarenting.dao.interfaces.BoardDAO;
import lk.ijse.rohanarenting.dto.UserDTO;
import lk.ijse.rohanarenting.dto.UserLoginDTO;
import lk.ijse.rohanarenting.service.SuperService;

public interface BoardService extends SuperService {
    BoardDAO boardDAO = (BoardDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BOARD_DAO);
    void addUserLogin(UserLoginDTO dto) throws Exception;
    UserDTO getUserNames(String id) throws Exception;
}
