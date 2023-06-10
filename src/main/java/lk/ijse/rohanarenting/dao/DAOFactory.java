package lk.ijse.rohanarenting.dao;

import lk.ijse.rohanarenting.dao.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public enum DAOType{
        LOGIN_DAO,FORGOT_PASSWORD_DAO,USER_ACCOUNT_DAO,CUSTOMER_DAO,EMPLOYEE_DAO, INSURANCE_DAO, TOOL_DAO, VEHICLE_DAO, BOARD_DAO, QUERY_DAO, DASHBOARD_DAO, LOGIN_HISTORY_DAO, RENT_DAO
    }
    public static DAOFactory getInstance(){
        if(daoFactory==null){
            daoFactory=new DAOFactory();
        }
        return daoFactory;
    }
    public SuperDAO getDAO(DAOType type){
        switch (type){
            case LOGIN_DAO:
                return new LoginDAOImpl();
            case FORGOT_PASSWORD_DAO:
                return new ForgotPasswordDAOImpl();
            case USER_ACCOUNT_DAO:
                return new UserAccountDAOImpl();
            case CUSTOMER_DAO:
                return new CustomerDAOImpl();
            case EMPLOYEE_DAO:
                return new EmployeeDAOImpl();
            case INSURANCE_DAO:
                return new InsuranceDAOImpl();
            case TOOL_DAO:
                return new ToolDAOImpl();
            case VEHICLE_DAO:
                return new VehicleDAOImpl();
            case BOARD_DAO:
                return new BoardDAOImpl();
            case QUERY_DAO:
                return new QueryDAOImpl();
            case DASHBOARD_DAO:
                return new DashboardDAOImpl();
            case LOGIN_HISTORY_DAO:
                return new LoginHistoryDAOImpl();
            case RENT_DAO:
                return new RentDAOImpl();
            default:
                return null;
        }
    }
}
