package lk.ijse.rohanarenting.service;

import lk.ijse.rohanarenting.service.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory() {
    }
   public enum ServiceType{
       FORGOT_PASSWORD, LOGIN_SERVICE, USER_ACCOUNT_SERVICE, CUSTOMER_SERVICE, EMPLOYEE_SERVICE, INSURANCE_SERVICE
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
            case EMPLOYEE_SERVICE:
                return new EmployeeServiceImpl();
            case INSURANCE_SERVICE:
                return new InsuranceServiceImpl();
            default:
                return null;
        }
    }
}
