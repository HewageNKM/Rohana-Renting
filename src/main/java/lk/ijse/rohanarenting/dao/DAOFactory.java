package lk.ijse.rohanarenting.dao;

import lk.ijse.rohanarenting.dao.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public enum DAOType{
        LOGIN_DAO,FORGOT_PASSWORD_DAO,USER_ACCOUNT_DAO,CUSTOMER_DAO,EMPLOYEE_DAO, INSURANCE_DAO, TOOL_DAO, VEHICLE_DAO
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
            default:
                return null;
        }
    }
}
