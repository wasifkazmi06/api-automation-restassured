package lazypay.transactionFlow;

import dbUtils.Lazypay_MySQL_DBAccessObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mpl.TestData;
import org.testng.annotations.DataProvider;
import java.sql.ResultSet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dbUtils.Device_Binding_MySQL_DBAccessObject;
import java.util.HashMap;
import static com.google.gson.JsonParser.*;

@Slf4j
public class TransactionData {

    /**
     * Amount
     */

    public static  String TRANSACTION_AMOUNT="1.23";
    public static  String TRANSACTION_AMOUNT_FOURDIGIT_AFTERDECIMAL="1.2312";
    public static String TRANSACTION_AMOUNT2="2.34";
    public static final String TRANSACTION_AMOUNT_HIGH="4500.00";
    public static final String TRANSACTION_MAX_AMOUNT="9999.00";
    public static final String TRANSACTION_ABOVE_MAX_AMOUNT="9999.01";
    public static final String TRANSACTION_MAX_AMOUNT_AGG="4999.99";
    public static final String TRANSACTION_ABOVE_MAX_AMOUNT_AGG="5000.00";
    public static final String TRANSACTION_AMOUNT3="600";
    public static final String TRANSACTION_AMOUNT4="2000";
    public static final String TRANSACTION_AMOUNT5="11";
    public static final String TRANSACTION_AMOUNT_INVALID="2.75abc";
    public static final String TRANSACTION_AMOUNT_ZERO="0";


    /**
     * User Details
     */
    public static final String USER_MOBILE_INVALID="600000019011";
    public static final String USER_MOBILE_VALID1="6000000190";
    public static final String USER_MOBILE_VALID2="6000000197";
    public static final String USER_MOBILE_VALID3="6000000198";
    public static final String USER_MOBILE_VALID4="6000000199";
    public static final String USER_MOBILE_VALID5="6000000200";
    public static final String USER_MOBILE_VALID6="6000000201";
    public static final String USER_MOBILE_VALID7="6000000202";
    public static final String USER_MOBILE_VALID8="6000000203";
    public static final String USER_MOBILE_VALID9="6000000204";
    public static final String USER_MOBILE_VALID10="6000000205";
    public static final String USER_NON_TRANSACTED="6000000206";
    public static final String USER_NEW="6000000207";
    public static final String USER_MOBILE_VALID13="6000000208";
    public static final String USER_MOBILE_VALID14="6000000209";
    public static final String USER_MOBILE_VALID15="6000000210";
    public static final String USER_MOBILE_VALID16="6000000541";
    public static final String USER_MOBILE_VALID17="6000000542";
    public static final String USER_MOBILE_VALID18="6000000543";
    public static final String USER_MOBILE_VALID19="6000000544";
    public static final String USER_MOBILE_VALID20="6000000545";
//The below listed mobile number used for 2FA transaction. Please don't use or change anything
    public static final String USER_MOBILE_VALID21="6000000571";
    public static final String USER_MOBILE_VALID22="6000000572";
    public static final String USER_MOBILE_VALID23="6000000573";
    public static final String USER_MOBILE_VALID24="6000000574";
    public static final String USER_MOBILE_VALID25="6000000575";
    public static final String USER_MOBILE_VALID26="6000000576";
    public static final String USER_MOBILE_VALID27="6000000577";
    public static final String USER_MOBILE_VALID28="6000000578";
    public static final String USER_MOBILE_VALID29="6000000579";
    public static final String USER_MOBILE_VALID30="6000000580";
    public static final String USER_MOBILE_VALID31="6000000581";
    public static final String USER_MOBILE_VALID32="6000000582";
    public static final String USER_MOBILE_VALID33="6000000583";
    public static final String USER_MOBILE_VALID34="6000000584";
    public static final String USER_MOBILE_VALID35="6000000585";
    public static final String USER_MOBILE_VALID36="6000000586";
    public static final String USER_MOBILE_VALID37="6000000587";
    public static final String USER_MOBILE_VALID38_NEWUSER="6000000588";
    public static final String USER_MOBILE_VALID39_NEWUSER="6000000589";
    public static final String USER_MOBILE_VALID40_NEWUSER="6000000590";// This data is used for only eleigibility API for 2FA and Device Rule Enabled Merchant
    public static final String USER_MOBILE_VALID41_NEWUSER="6000000641";
    public static final String USER_MOBILE_VALID42="6000000642";
    public static final String USER_MOBILE_VALID43="6000000643";
    public static final String USER_MOBILE_VALID44="6000000644";
    public static final String USER_MOBILE_VALID45="6000000645";

    //Non Transacted New User
    public static final String USER_MOBILE_VALID46_NONTRANSACTED="6000000646";

    public static final String USER_MOBILE_BLOCKED="6000000191";
    public static final String USER_MOBILE_BLACKLISTED="6000000192";
    public static final String USER_MOBILE_OPTED_OUT="6000000193";
    public static final String USER_MOBILE_LOCKED="6000000194";
    public static final String USER_MOBILE_LOW_CREDIT_LIMIT="6000000195";
    public static final String USER_MOBILE_MAXED_OUT="6000000196";
    public static final String USER_MOBILE_INELIGIBLE="7000000201";
    public static final String USER_MOBILE_NONMITCANDKYC="6000007001";

    public static final String USER_MOBILE_VALID21_LPUSERID="668e8ba5dd3b164cafe2bb45";
    public static final String USER_MOBILE_VALID22_LPUSERID="66718a2650120e1577f8a5f3";
    public static final String USER_MOBILE_VALID23_LPUSERID="66718a5d09c3e63816b647ce";
    public static final String USER_MOBILE_VALID24_LPUSERID="662f286550e09117262646f1";
    public static final String USER_MOBILE_VALID25_LPUSERID="66718ac909c3e63816b647d1";
    public static final String USER_MOBILE_VALID26_LPUSERID="66718b0109c3e63816b647d6";
    public static final String USER_MOBILE_VALID27_LPUSERID="66718b3909c3e63816b647d7";
    public static final String USER_MOBILE_VALID28_LPUSERID="66718b6e50120e1577f8a600";
    public static final String USER_MOBILE_VALID29_LPUSERID="66718ba209c3e63816b647dc";
    public static final String USER_MOBILE_VALID30_LPUSERID="6671661250120e1577f8a58f";
    public static final String USER_MOBILE_VALID31_LPUSERID="66718c0e09c3e63816b647df";
    public static final String USER_MOBILE_VALID32_LPUSERID="66718c4409c3e63816b647e0";
    public static final String USER_MOBILE_VALID33_LPUSERID="66718c7909c3e63816b647e3";
    public static final String USER_MOBILE_VALID34_LPUSERID="66718cad09c3e63816b647e5";
    public static final String USER_MOBILE_VALID35_LPUSERID="66718ce009c3e63816b647ed";
    public static final String USER_MOBILE_VALID37_LPUSERID="66718d4709c3e63816b647f3";
    public static final String USER_MOBILE_VALID38_LPUSERID="6791da7899f7f9028e2d9f40";
    public static final String USER_MOBILE_VALID39_LPUSERID="66d85482897f8b36e7aa2854";
    public static final String USER_MOBILE_VALID42_LPUSERID="66728f5550120e1577f8a66e";
    public static final String USER_MOBILE_VALID43_LPUSERID="67976889917ad6155bc76ebe";
    public static final String USER_MOBILE_VALID44_LPUSERID="679784a4917ad6155bc76eee";

    public static final String USER_EMAIL="testuser121@gmail.com";
    public static final String USER_EMAIL_FRAUD="testuser@test.com";
    public static final String USER_EMAIL10="email201@mail.com";

    public static final String FIRST_NAME= "firstname";
    public static final String LAST_NAME= "lastname";



    /**
     * Tokens
     */
    public static String AUTH_TOKEN="Bearer 2ed461a5-4202-4a40-976c-50660c2ae9e3";
    public static String AUTH_TOKEN_DISABLED_SUB_MERCHANT = "Bearer 4df6dfd8-320a-4b6d-96fe-ee3e7a33805e";
    public static String AUTH_TOKEN_1_AGG_USER_13 = "Bearer bff45f6b-a6c1-4f5d-abd7-2c13fc40dea7";
    public static String AUTH_TOKEN_2_AGG_USER_14 = "Bearer 45b99fda-096c-47d1-8df1-26c09f5e9496";
    public static String AUTH_TOKEN_3_AGG_USER_13 = "Bearer 376e4754-6f36-4554-b0c7-b96cd247d698";
    public static String AUTH_TOKEN_4_AGG_USER_14 = "Bearer af951d06-7948-4cfd-bd00-24219259864c";
    public static String AUTH_TOKEN_5_AGG_USER_13 = "Bearer 7acf7b5c-3e90-4a88-9ce5-6d30a67de667";
    public static String AUTH_TOKEN_6_AGG_USER_14 = "Bearer 3a3df28b-38a9-49b1-b06f-3d301150b6d3";
    public static String AUTH_TOKEN_7_AGG_USER_13 = "Bearer 35e6d585-35f5-49ea-9754-dafd0ccf80a4";
    public static String AUTH_TOKEN_8_AGG_USER_14 = "Bearer 629bd335-5712-4681-883f-46dd5a85cba2";
    public static String AUTH_TOKEN_9_DIRECT_USER_13 = "Bearer 395777a1-ae90-48c2-91db-fe51f20becfa";
    public static String AUTH_TOKEN_10_DIRECT_USER_14 = "Bearer dfc1577d-e3a3-43bb-9983-ccac69343f29";
    public static String AUTH_TOKEN_DISABLED_SUB_MERCHANT_USER_15 = "Bearer 0fb70194-ef02-42d7-a0a8-747ace258826";
    public static String AUTH_TOKEN_USER_MOBILE_VALID38_NEWUSER = "Bearer 04d78b25-f741-412d-9fad-7330e986e9bb";
    public static String AUTH_TOKEN_USER_MOBILE_VALID43_ACCESS_KEY_2FA_DEVICERULE_MAgg = "Bearer ddbf3e57-dc72-44f5-aa9a-0c27da64f93d";
    public static String AUTH_TOKEN_USER_MOBILE_VALID34_ACCESS_KEY_2FA_DEVICERULE_SMAgg = "Bearer 3c1155df-549e-4d21-ad9b-8b316cc18171";
    public static String AUTH_TOKEN_USER_MOBILE_VALID43_ACCESS_KEY_2FA_DEVICERULE_SMAgg = "Bearer 9a0f8f94-0a72-450d-851f-f907845d4bca";

    /**
     * Merchant
     */

    public static final String ACCESS_KEY="CCWHRSH9UULECPLQCFD2"; //AY1TO74L84R4ST6H7LDI
    public static final String ACCESS_KEY_AUTODEBIT_FALSE="2HHDMY2E7LW6DUM6YKNI";
    public static final String ACCESS_KEY_EMAIL_REQUIRED="M9OZT7LFHPCK91UDVJC8";
    public static final String ACCESS_KEY_INVALID="M9OZT7LFHPCK91UDVJC9";
    public static final String ACCESS_KEY_FNAME_REQUIRED="RWM1KQ3W2VFK7CWLYPPZ";
    public static final String ACCESS_KEY_LNAME_REQUIRED="K3IAG9I36URW2W0BMVZW";
    public static final String ACCESS_KEY_PRODUCT_REQUIRED="KHFHUAOXXG4D3RD7SD4U";
    public static final String ACCESS_KEY_ADDRESS_REQUIRED="IT58UPQ2XMV4B31LBOPV";
    public static final String ACCESS_KEY7="LV0XS2E1ST1WXR7JL831";
    public static final String ACCESS_KEY_2FA="O0TIKD3ZG0PGRF4EFNMH";
    public static final String ACCESS_KEY_2FA_DEVICERULE="7V3CQ1JHM1UHI1ZLIST6"; // MaxTxn Amount set in fraud=10
    public static final String ACCESS_KEY_2FA_DEVICERULE2="4014QLTXQCONHZV4RBSZ";
    public static final String ACCESS_KEY_2FA_DEVICERULE_MAgg="SVYT1V5TVAGP75A1T0D9"; //MaxTxn Amount set in fraud=10
    public static final String ACCESS_KEY_2FA_DEVICERULE_SMAgg="AH7ZDFDO3XGKC4CGW437";

    public static final String SUB_MERCHANT_ID="100";
    public static final String SUB_MERCHANT_TXN_ID="TEST_SUB_MERCHANT_TXN_ID";

    public static final String ACCESS_KEY_AGGREGATOR_1="TIDVFU4CLZO1DA89UWHI";
    public static final String SUB_MERCHANT_ID_1="TestAutomationSM_1";
    public static final String SUB_MERCHANT_TXN_ID_1="TEST_SUB_MERCHANT_TXN_ID";
    public static final String SUB_MERCHANT_ID_2="TestAutomationSM_2";
    public static final String SUB_MERCHANT_ID_DISABLED="TestAutomationSM_Disabled";

    public static final String ACCESS_KEY_AGGREGATOR_2="JZSAR0Q56APXWZY4GAH7";
    public static final String SUB_MERCHANT_ID_3="TestAutomationSM_3";
    public static final String SUB_MERCHANT_ID_AUTO_DEBIT_DISABLED="TestAutomationSM_AutoDebitDisabled";
    public static final String SUB_MERCHANT_ID_2FA_DeviceRule1="2FATestSMDeviceRule_1";
    public static final String SUB_MERCHANT_ID_2FA_DeviceRule2="2FATestSMDeviceRule_2";
    public static final String SUB_MERCHANT_ID_2FA_DeviceRule3="2FATestSMDeviceRule_3";

    public static final String ACCESS_KEY_AGGREGATOR_ALLOW_MERCHANT_LEVEL_AUTODEBIT="LBIFS9ZUOZIP0WAXGWNO";
    public static final String SUB_MERCHANT_ID_4="TestAutomationSM_4";
    public static final String SUB_MERCHANT_ID_5="TestAutomationSM_5";

    public static final String ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED="XS0WLOG4AFSXOZGO3NF6";
    public static final String SUB_MERCHANT_ID_EMAIL_REQUIRED="TestAutomationSM_6";

    public static final String ACCESS_KEY_AGGREGATOR_EXCLUDE_CAP="QVEJ0GVLZYRDDBBEC5U1";
    public static final String SUB_MERCHANT_ID_EXCLUDE_CAP="129465";

    public static final String INVALID_SUBMERCHANT_ID="testsubmerchant12";

    public static final String ACCESS_KEY_2FA_AGG="3VR5Q3MYXS07GXHD8JX2";
    public static final String SUB_MERCHANT_ID_2FA="2FATestSubMerchant";

    public static final String ACCESS_KEY_AGGREGATOR_PRODUCT_REQUIRED="Q0GK1Y057766EPXRFBVR";
    public static final String SUB_MERCHANT_ID_2FA_PRODUCT_REQUIRED="TestAutomationSM_ProductRequired";

    /**
     * User Address Details
     */
    public static final String ZIP="560037";
    public static final String COUNTRY="TestCountry";
    public static final String STREET1="TestStreet1";
    public static final String STREET2="TestStreet2";
    public static final String STATE="TestState";
    public static final String CITY="TestCity";

    /**
     * Source
     */
    public static final String SOURCE_CITRUS_SDK = "CITRUS_SDK";
    public static final String SOURCE_CITRUS_SSLV2= "CITRUS_SSLV2";
    public static final String SOURCE_STANDALONE_API= "STANDALONE_API";
    public static final String SOURCE_STANDALONE_REDIRECT= "STANDALONE_REDIRECT";
    public static final String SOURCE_MOTORINSURANCE= "MOTOR_INSURANCE";

    /**
     * Miscellaneous
     */
    public static final String CURRENCY="INR";
    public static final String INVALIDCURRENCY="XYZ";
    public static final String MTX_DUPLICATE_ID="TXN1193658";
    public static final String RETURN_URL =  "https://sboxapi.lazypay.in/temp/merchant/makePayReturn";
    public static final String EXPIRED_TRANSACTION = "TXN1376747";
    public static final String TRUSTED_DEVICE1="AutomationTestTrusted1";
    public static final String TRUSTED_DEVICE2 ="AutomationTestTrusted2";
    public static final String TRUSTED_DEVICE3="AutomationTestTrusted3";
    public static final String TRUSTED_DEVICE4="AutomationTestTrusted4";
    public static final String UNTRUSTED_DEVICE="AutomationTestUnTrusted";
    public static final String UNTRUSTED_DEVICE2="AutomationTestUnTrusted2";
    public static final String UNTRUSTED_DEVICE3="AutomationTestUnTrusted3";
    public static final String UNTRUSTED_DEVICE4="AutomationTestUnTrusted4";
    public static final String UNTRUSTED_DEVICE5="AutomationTestUnTrusted5";
    public static final String UNTRUSTED_DEVICE6="AutomationTestUnTrusted6";
    public static final String UNTRUSTED_DEVICE7="AutomationTestUnTrusted7";
    public static final String UNTRUSTED_DEVICE8="AutomationTestUnTrusted8";
    public static final String UNTRUSTED_DEVICE9="AutomationTestUnTrusted9";
    public static final String UNTRUSTED_DEVICE10="AutomationTestUnTrusted10";
    public static final String UNTRUSTED_DEVICE11="AutomationTestUnTrusted11";
    public static final String UNTRUSTED_DEVICE12="AutomationTestUnTrusted12";
    public static final String PLATFORM_NAME_ANDROID="ANDROID";
    public static final String PLATFORM_NAME_IOS="IOS";
    public static final String PLATFORM_NAME_WEB="WEB";
    public static final String POLICY_ID="41";
    /**
     * API Versions
     */
    public static final String VERSION_0 =  "V0";
    public static final String VERSION_1 =  "V1";
    public static final String VERSION_2 =  "V2";
    public static final String VERSION_3 =  "V3";
    public static final String VERSION_4 =  "V4";
    public static final String VERSION_5 =  "V5";
    public static final String VERSION_6 =  "V6";
    public static final String VERSION_7 =  "V7";

    public static final String ELIGIBILITY_V0 =  "V0";
    public static final String ELIGIBILITY_V1 =  "V1";
    public static final String ELIGIBILITY_V2 =  "V2";
    public static final String ELIGIBILITY_V3 =  "V3";
    public static final String ELIGIBILITY_V4 =  "V4";
    public static final String ELIGIBILITY_V5 =  "V5";
    public static final String ELIGIBILITY_V6 =  "V6";
    public static final String ELIGIBILITY_V7 =  "V7";
    public static final String INITIATE_PAY_V0 =  "V0";
    public static final String INITIATE_PAY_V1 =  "V1";
    public static final String INITIATE_PAY_V2 =  "V2";
    public static final String INITIATE_PAY_V4 =  "V4";
    public static final String INITIATE_PAY_V5 =  "V5";
    public static final String PAY_V0 =  "V0";
    public static final String PAY_V4 =  "V4";
    public static final String PAY_V5 =  "V5";
    public static final int MAX_RETRIES_ALLOWED=1;
    public static final int MAX_RETRIES_ALLOWED2=0;

    /**
     * SQL Queries
     */
    private static String SELECT_USER_DETAILS="select * from lazypay.users where mobile = '$';";
    private static String SELECT_ACCOUNT_DETAILS="select * from lazypay.account where tenant = '#' and user_id in (select userId from lazypay.users where mobile = '$');";
    private static String UPDATE_USER_OPTEDOUT="update lazypay.users set optedOut = '#', status = '&' where mobile = '$';";
    private static String UPDATE_USER_RESTRICTION="update lazypay.account set restrictions = '@' where user_id in (select userId from lazypay.users where mobile = '$');";
    private static String UPDATE_USER_LOCKED_Multiple="update lazypay.users set blockReason = '@', isBlocked = '#', optedOut = '#', status = '&' where mobile in ('$');";
    private static String SELECT_TXN_CUSTOM_PARAMS="SELECT additionalParams FROM lazypay.txnCustomParams where id='$';";
    private static String DELETE_USER_DETAILS ="DELETE FROM lazypay.users where userId = '$';";
    private static String DELETE_USER_ACCOUNT_DETAILS ="DELETE FROM lazypay.account where user_id = '$';";
    private static String DELETE_USER = "DELETE from lazypay.users where mobile = '$';";
    private static String DELETE_ACCOUNT = "DELETE * from lazypay.account where user_id in (select userId from lazypay.users where mobile = '$');";

    public static final String MAX_TXN_AMOUNT_ERROR_MSG = "Oops!! Currently, LazyPay is not available for transaction amount above Rs 9999.0 with the Pay Later option.";
    public static final String MAX_TXN_AMOUNT_ERROR_MSG_PAY = "Oops!! Currently, LazyPay is not available for transaction amount above Rs 9999 with the Pay Later option.";

    @SneakyThrows
    @DataProvider(name = "user-mobile")
    public final static Object[][] getUserMobileForTransactions()
    {
        return new Object[][] {{USER_MOBILE_VALID1}, {USER_MOBILE_VALID2}, {USER_MOBILE_VALID3}, {USER_MOBILE_VALID4},{USER_MOBILE_VALID5},
                {USER_MOBILE_VALID6}, {USER_MOBILE_VALID7}, {USER_MOBILE_VALID8}, {USER_MOBILE_VALID9}, {USER_MOBILE_VALID10},{USER_MOBILE_MAXED_OUT},
                {USER_MOBILE_LOW_CREDIT_LIMIT},{USER_MOBILE_VALID13}, {USER_MOBILE_VALID14},{USER_MOBILE_VALID15},{USER_MOBILE_VALID16},
                {USER_MOBILE_VALID17},{USER_MOBILE_VALID18},{USER_MOBILE_VALID19},{USER_MOBILE_VALID20},{USER_MOBILE_VALID21},{USER_MOBILE_VALID22},
                {USER_MOBILE_VALID23},{USER_MOBILE_VALID24},{USER_MOBILE_VALID25},{USER_MOBILE_VALID26},{USER_MOBILE_VALID27},{USER_MOBILE_VALID28},
                {USER_MOBILE_VALID29},{USER_MOBILE_VALID30},{USER_MOBILE_VALID31},{USER_MOBILE_VALID32},{USER_MOBILE_VALID33},{USER_MOBILE_VALID34},
                {USER_MOBILE_VALID35},{USER_MOBILE_VALID36},{USER_MOBILE_VALID37}};
    }

    @SneakyThrows
    public static ResultSet getUserDetail(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_USER_DETAILS.replace("$", mobile));
    }

    @SneakyThrows
    public static void updateOptedOutUser(String mobile, String optedOut, String status)
    {
        Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_USER_OPTEDOUT.replace("#",optedOut).replace("&",status).replace("$", mobile));
    }

    @SneakyThrows
    public static void updateRestriction(String mobile, String blockReason)
    {
        switch (blockReason) {
            case "blocked":
                Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_USER_RESTRICTION.replace("@", "[\"LATE_FEE_BLOCK\"]").replace("$", mobile));
                break;
            case "locked":
                Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_USER_RESTRICTION.replace("@", "[\"OTP_BLOCK\"]").replace("$", mobile));
                break;
            case "blacklisted":
                Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_USER_RESTRICTION.replace("@", "[\"FRAUD_BLOCK\"]").replace("$", mobile));
                break;
            default:
                Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_USER_RESTRICTION.replace("'@'", "null").replace("$", mobile));

        }
    }

    @SneakyThrows
    public static String getAccountDetails(String mobile, String tenant, String columnName)
    {
        String columnData = "";
        ResultSet rs;
        rs = Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_ACCOUNT_DETAILS.replace("$", mobile).replace("#", tenant));
        while(rs.next()) {
            columnData = rs.getString(columnName);
            }
        return columnData;
    }

    @SneakyThrows
    public static void updateLockedUserMultiple(String mobile, String blockReason, String locked, String status)
    {
        if(blockReason == "null"){
            Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_USER_LOCKED_Multiple.replace("'@'",blockReason).replace("#",locked).replace("&",status).replace("$", mobile));
        }
        else
            Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_USER_LOCKED_Multiple.replace("@",blockReason).replace("#",locked).replace("&",status).replace("$", mobile));
    }

    @SneakyThrows
    public static String getAdditionalParamsValueFromCustomParam(String txnId, String parameter)
    {
        String additionalParamsData="";
        String parameterValue="";
        String selectCommand=SELECT_TXN_CUSTOM_PARAMS.replace("$", txnId);
        log.info(selectCommand);
        ResultSet rs=  Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(selectCommand);
        while (rs.next()) {
            additionalParamsData = rs.getString("additionalParams");
            System.out.println(additionalParamsData);
            JsonObject jsonObject = new JsonParser().parse(additionalParamsData).getAsJsonObject();
            parameterValue = jsonObject.get(parameter).getAsString();
            System.out.println(parameterValue);
        }
        return parameterValue;
    }

    @SneakyThrows
    public static void deleteUserFromUsersTable(HashMap<String, String> request) {
        try {
            String deleteQuery=(DELETE_USER_DETAILS.replace("$", request.get("userId")));
            log.info("User Table delete query is " +deleteQuery);
            Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(deleteQuery);
        } catch (Exception e) {
            log.debug("User details not present in LP user table" + e);
        }
    }
    @SneakyThrows
    public static void deleteUserAccount(HashMap<String, String> request) {
        try {
            String deleteQuery=(DELETE_USER_ACCOUNT_DETAILS.replace("$", request.get("user_id")));
            log.info("Account Table delete query is " +deleteQuery);
            Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(deleteQuery);
        } catch (Exception e) {
            log.debug("Account details not present in LP user table" + e);
        }
    }

    @SneakyThrows
    public static void deleteUser(String mobile) {
        try {
            String deleteAccountQuery=(DELETE_ACCOUNT.replace("$", mobile));
            String deleteUserQuery=(DELETE_USER.replace("$", mobile));
            log.info("Account Table delete query: {}" + deleteAccountQuery);
            Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(deleteAccountQuery);
            log.info("User table delete query: {}" + deleteUserQuery);
            Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(deleteUserQuery);
        } catch (Exception e) {
            log.debug("Account details not present in LP user table" + e);
        }
    }
}
