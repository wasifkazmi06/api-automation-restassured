package lazypay.juspay.repaymentFlow;


import dbUtils.Lazypay_MySQL_DBAccessObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;

@Slf4j
public class RepaymentData_JP {


    String paymentMethod = "UPI";
    private static String SELECT_USER_DETAILS="select * from users where mobile = $;";
    private static String SELECT_TRANSACTION_DETAILS="select * from transactions where id = '$';";

    //NEGATIVE_TEST_NUMBER
    public static final String USER_MOBILE_NEGATIVE="6000000265";


    //UPI
    public static final String USER_MOBILE_UPI_JP="6000000511";
    public static final String USER_MOBILE_UPI_JP_Amount0_1="6000000511";
    public static final String USER_MOBILE_UPI_JP_INVALID="6000000268";
    public static final String USER_TEST_VPA_JP="9999999999@upi";

    public static final String USER_TEST_VPA_JP_INVALID="6000000268@upi";
    public static final String BANK_SHORT_NAME="ICICI";
    public static final String USER_VPA="7003846020.lzp@idfcbank";
    //public static final String VALID_VPA_USER="7003846020";

    //NET_BANKING
    public static final String USER_MOBILE_NB_JP="6000000514";
    //DEBIT_CARD
    public static final String USER_MOBILE_DC_JP="6000000513";
    //CREDIT_CARD
    public static final String USER_MOBILE_CC_JP="6000000512";

    //Payment-Method
    public static final String UPI_PAYMENT_METHOD="UPI_COLLECT";
    public static final String DC_PAYMENT_METHOD="DC";
    public static final String CC_PAYMENT_METHOD="CC";
    public static final String NB_PAYMENT_METHOD="NB";
    public static final String BANK_CODE="NB_AXIS";
    public static final String BANK_NAME="Axis Bank";
    public static final String SHORT_NAME="AXIS";



    //repay-refund
    public static final String USER_MOBILE_RF_JP="6000000260";
    public static final String USER_MOBILE_RF_JP_INVALID="9952908209";

    //MBE-APP
    public static final String USER_MOBILE_UPI_MBE="6000000261";
    public static final String USER_MOBILE_DC_MBE="6000000262";
    public static final String USER_MOBILE_CC_MBE="6000000264";
    public static final String USER_MOBILE_NB_MBE="6000000265";

    //Card-Details
    public static final String CARD_NUMBER_CC="4012001037141112";
    public static final String CARD_NUMBER_DC="5497774415170603";
    public static final String CARD_CVV_DC="412";
    public static final String CARD_CVV_CC="123";
    public static final String CARD_CVV_Month_Expiration="10";
    public static final String CARD_CVV_Year_Expiration="26";

    public static final String SOURCE="ANDROID";

    public static final String PRODUCT="BNPL";
    public static final String BANK_CODE_UPI="UPI";
    public static final String BANK_CODE_NB="NB_TEST";
    public static final String BANK_CODE_CC="VISA";
    public static final String BANK_CODE_DC="MASTERCARD";



    //MBE Negative Cases
    public static final String USER_MOBILE_MBE_NEGATIVE_INVALID_AMOUNT="6000000515";


    @SneakyThrows
    public static ResultSet getUserDetail(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_USER_DETAILS.replace("$", mobile));
    }

    @SneakyThrows
    public static ResultSet getTransactionDetail(String transactionId)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_TRANSACTION_DETAILS.replace("$", transactionId));
    }






}
