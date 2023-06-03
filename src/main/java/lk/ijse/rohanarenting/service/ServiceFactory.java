package lk.ijse.rohanarenting.service;

import lk.ijse.rohanarenting.service.impl.CustomerServiceImpl;
import lk.ijse.rohanarenting.service.impl.ForgotPasswordServiceImpl;
import lk.ijse.rohanarenting.service.impl.LoginServiceImpl;
import lk.ijse.rohanarenting.service.impl.UserAccountServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory() {
    }
   public enum ServiceType{
       FORGOT_PASSWORD, LOGIN_SERVICE, USER_ACCOUNT_SERVICE, CUSTOMER_SERVICE
   }
    public static ServiceFactory getInstance(){
        if(serviceFactory==null){
            serviceFactory=new ServiceFactory();
        }
        return serviceFactory;
    }
    public SuperService getService(ServiceType type){
        switch (type){
            case LOGIN_SERVICE:
                return new LoginServiceImpl();
            case FORGOT_PASSWORD:
                return new ForgotPasswordServiceImpl();
            case USER_ACCOUNT_SERVICE:
                return new UserAccountServiceImpl();
            case CUSTOMER_SERVICE:
                return new CustomerServiceImpl();
            default:
                return null;
        }
    }
}
