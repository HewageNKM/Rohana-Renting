package lk.hnkm.rohanarenting.dao;

import lk.hnkm.rohanarenting.dao.impl.ForgotPasswordDAOImpl;
import lk.hnkm.rohanarenting.dao.impl.LoginDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public enum DAOType{
        LOGIN_DAO,FORGOT_PASSWORD_DAO
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
            default:
                return null;
        }
    }
}
