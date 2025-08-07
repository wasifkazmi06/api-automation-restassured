package upi;

import dbUtils.UPI_MySQLSsh_DBAccessObject;
import dbUtils.UPI_MySQL_DBAccessObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UPIData {

    private static String DELETE_USER_REGISTRATION="DELETE FROM upi_registration WHERE mobile=$;";
    private static String DELETE_USER="DELETE FROM upi_user WHERE mobile=$;";
    private static String UPDATE_USER_REGISTRATION="UPDATE upi_registration SET status = 'REGISTERED' WHERE mobile=$ order by id desc limit 1;";
    private static String UPDATE_USER="UPDATE upi_user SET status = 'REGISTERED' WHERE mobile=$;";



    @SneakyThrows
    public static void deleteUserRegistration(String mobile)
    {
        UPI_MySQLSsh_DBAccessObject.deleteOnMySqlDb(DELETE_USER_REGISTRATION.replace("$",mobile));
        UPI_MySQLSsh_DBAccessObject.deleteOnMySqlDb(DELETE_USER.replace("$",mobile));
    }

    @SneakyThrows
    public static void updateUserAsRegistered(String mobile)
    {
        UPI_MySQLSsh_DBAccessObject.updateOnMySqlDb(UPDATE_USER_REGISTRATION.replace("$",mobile));
        UPI_MySQLSsh_DBAccessObject.updateOnMySqlDb(UPDATE_USER.replace("$",mobile));
    }

    public static final String CURRENCY = "INR";
    public static final String LP_AMOUNT = "12.34";
    public static final String LOAN_AMOUNT = "3123.45";
    public static final String LOAN_TENURE = "3";
    public static final String DEVICE_DATA = "{\"latitude\":\"1.2\",\"longitude\":\"1.2\",\"devIp\":\"1.1.1.1\",\"devType\":\"IOS\",\"devOS\":\"Android 5.1.1\",\"devLocation\":\"BANGALORE\"}";

    //Transction Types
    public static final String ACQUISITION_CHANNEL_SCANPAY = "SCAN_PAY";
    public static final String ACQUISITION_CHANNEL_ENTEREDVPA = "ENTERED_VPA";
    public static final String ACQUISITION_CHANNEL_COLLECTREQUEST = "COLLECT_REQ";
    public static final String ACQUISITION_CHANNEL_INTENTPAY = "INTENT_PAY";

    //User
    public static final String REGISTERED_USER = "6000000116";
    public static final String REGISTERED_DEVICE_ID = "TestDeviceID116";
    public static final String REGISTERED_SIM_ID = "TestSIMID116";

    public static final String UPI_USER = "6000000117";
    public static final String DEVICE_ID = "TestDeviceID117";
    public static final String SIM_ID = "TestSIMID117";

    //Merchant
    //public static final String MERCHANT_VPA = "AMZN1212121234@apl";
    public static final String MERCHANT_VPA = "ultracash@idfb";
    public static final String MERCHANT_MCC = "6002";
    public static final String INVALID_MERCHANT_VPA = "pmcares@sbi";






}
