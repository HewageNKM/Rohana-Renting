package lk.hnkm.rohanarenting.service;

import lk.hnkm.rohanarenting.service.impl.ForgotPasswordServiceImpl;
import lk.hnkm.rohanarenting.service.impl.LoginServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory() {
    }
   public enum ServiceType{
       FORGOT_PASSWORD, LOGIN_SERVICE
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
            default:
                return null;
        }
    }
}
