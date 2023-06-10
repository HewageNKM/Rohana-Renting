package lk.ijse.rohanarenting.service;

import lk.ijse.rohanarenting.service.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory() {
    }
   public enum ServiceType{
       FORGOT_PASSWORD, LOGIN_SERVICE, USER_ACCOUNT_SERVICE, CUSTOMER_SERVICE, EMPLOYEE_SERVICE, INSURANCE_SERVICE, TOOL_SERVICE, VEHICLE_SERVICE, BOARD_SERVICE, DASHBOARD_SERVICE, LOGIN_HISTORY_SERVICE, RENT_SERVICE, REFUND_SERVICE, Return_Service
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
            case TOOL_SERVICE:
                return new ToolServiceImpl();
            case VEHICLE_SERVICE:
                return new VehicleServiceImpl();
            case BOARD_SERVICE:
                return new BoardServiceImpl();
            case DASHBOARD_SERVICE:
                return new DashboardServiceImpl();
            case LOGIN_HISTORY_SERVICE:
                return new LoginHistoryServiceImpl();
            case RENT_SERVICE:
                return new RentServiceImpl();
            case REFUND_SERVICE:
                return new RefundServiceImpl();
            case Return_Service:
                return new ReturnServiceImpl();
            default:
                return null;
        }
    }
}
