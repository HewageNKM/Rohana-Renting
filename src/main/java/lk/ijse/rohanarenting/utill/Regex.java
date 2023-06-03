/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/31/23, 10:32 AM
 *
 */

package lk.ijse.rohanarenting.utill;

public class Regex {
    private static final String NIC_REGEX_1 = "^[1-9]\\d{11}$";
    private static final String NIC_REGEX_2 = "^\\d{9}[vV]$";
    private static final String NAME_REGEX = "^[a-zA-Z]{4,60}$";
    private static final String NAME_WITH_SPACES_REGEX = "^[a0-zA9-Z ]{4,60}+$";
    private static final String ADDRESS_REGEX = "^[a-zA-Z0-9,/]{4,60}$";
    private static final String FAX_REGEX = "^[+]\\d{11}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    private static final String MOBILE_REGEX = "^\\+?\\d{10}$";
    private static final String USERNAME_REGEX = "^[A-Za-z0-9]{3,}$";
    private static final String PASSWORD_REGEX = "[aA-zZ0-9]{8,20}$";
    private static final String EID_REGEX = "^[Ee][0-9]{4}$";
    private static final String VEHICLE_ID_REGEX = "^[a-zA-Zz]{2,3}-[0-9]{4}$";
    private static final String TEXT_ONLY = "^[a-zA-Zz]{2,}$";
    public static final String NUMBER_ONLY = "^[1-9]\\d*$";
    private static final String Tool_ID_REGEX = "^[Tt][0-9]{4}$";
    private static final String CUSTOMER_ID_REGEX = "^[Cc][0-9]{4}$";
    private static final String NUMBERS_DECIMAL = "^[+]?[0-9]*\\.?[0-9]+$";
    private static final String ZIP_REGEX = "[0-9]{5}$";
    private static final String VEHICLE_RENT_ID = "^RV.[aA-zZ0-9]{7}$";
    private static final String TOOL_RENT_ID = "^RT.[aA-zZ0-9]{7}$";


    public static boolean validateNIC(String nic) {
        return nic.matches(NIC_REGEX_1) || nic.matches(NIC_REGEX_2);
    }
    public static boolean validateName(String name) {
        return name.matches(NAME_REGEX);
    }
    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
    public static boolean validateMobile(String mobile) {
        return mobile.matches(MOBILE_REGEX);
    }
    public static boolean validateUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }
    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
    public static boolean validateEID(String eid) {return eid.matches(EID_REGEX); }
    public static boolean validateVehicleID(String vid) {return vid.matches(VEHICLE_ID_REGEX);}
    public static boolean validateTextOnly(String text) {return text.matches(TEXT_ONLY);}
    public static boolean validateNumberOnly(String number) {return number.matches(NUMBER_ONLY);}
    public static boolean validateToolId(String toolId){return toolId.matches(Tool_ID_REGEX); }
    public static boolean validateCustomerCID(String customerId){return customerId.matches(CUSTOMER_ID_REGEX); }
    public static boolean validateNumbersAndDecimals(String customerId){return customerId.matches(NUMBERS_DECIMAL); }
    public static boolean validateZIP(String zipCode) {
        return zipCode.matches(ZIP_REGEX);
    }
    public static boolean validateNameWithSpaces(String name) {return name.matches(NAME_WITH_SPACES_REGEX);}
    public static boolean validateAddress(String address) {return address.matches(ADDRESS_REGEX);}
    public static boolean validateFax(String fax) {return fax.matches(FAX_REGEX);}
    public static boolean validateVehicleRentId(String vehicleRentId) {return vehicleRentId.matches(VEHICLE_RENT_ID);}
    public static boolean validateToolRentId(String toolRentId) {return toolRentId.matches(TOOL_RENT_ID);}
}
