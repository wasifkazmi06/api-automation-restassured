package platform.userregistration;

import api.userRegistration.FindOrCreateUser;
import api.userRegistration.FindUser;
import api.userRegistration.ResendOTP;
import api.userRegistration.ValidateUser;
import dbUtils.userRegistration_MySQL_DBAccessObject;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import platform.PlatformSupportData;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class UrsSupportData extends PlatformSupportData {

    String UUID;
    HashMap<String, String> mobileOtpIdDetails = new HashMap<>();
    HashMap<String, String> otpValueDetails = new HashMap<>();

    String newUserLoginFlowUUID, existingUserUUID;

    FindOrCreateUser createUser = new FindOrCreateUser();
    ResendOTP resendOTP = new ResendOTP();
    FindUser findUser = new FindUser();

    ValidateUser validateUser = new ValidateUser();


    private static final String GET_UUID = "SELECT # FROM userregistration.user WHERE mobile='$'";
    private static final String MOBILE_VERIFIED_DATE = "SELECT # FROM userregistration.user WHERE um_uuid='$'";
    private static final String DELETE_MOBILE = "DELETE FROM userregistration.user WHERE mobile='$'";

    protected static final String FIND_OR_CREATE_PAYLOAD_FILE_PATH = "src/test/resources/userregistration/findOrCreateUser.txt";
    protected static final String RESEND_OTP_PAYLOAD_FILE_PATH = "src/test/resources/userregistration/resendOTP.txt";
    protected static final String VALIDATE_USER_PAYLOAD_FILE_PATH = "src/test/resources/userregistration/validateUser.txt";



    public UrsSupportData() throws Exception {
    }


    @SneakyThrows
    @Step
    public static String getuuidFromUserDb(String uuid, String mobile) {
        String s = "";
        ResultSet rs = userRegistration_MySQL_DBAccessObject.selectFromMySqlDb(GET_UUID.replace("#", uuid).replace("$", mobile));
        while (rs.next())
            s = rs.getString(uuid);

        return s;

    }

    @SneakyThrows
    @Step
    public static Long getMobileVerifiedDateUserDb(String mobile_verified_date, String uuid) {
        try {
            String s = "";
            ResultSet rs = userRegistration_MySQL_DBAccessObject.selectFromMySqlDb(MOBILE_VERIFIED_DATE.replace("#", mobile_verified_date).replace("$", uuid));
            while (rs.next())
                s = rs.getString(mobile_verified_date);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = df.parse(s);
            return date.getTime();
        } catch (Exception e) {
            log.info(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @SneakyThrows
    @Step
    public static void deleteUserDataFromUser(String uuid) {
        try {
            userRegistration_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_MOBILE.replace("$", uuid));
        } catch (Exception e) {
            log.debug("User entry is already deleted in the Table" + e);
        }
        Thread.sleep(10000);
    }

    @DataProvider(name = "ursFindOrCreateUserData")
    public Object[][] ursFindOrCreateUserData() {
        return new Object[][]{
                //TestCaseName,MobileNumber,TenantId,PolicyId,MOBILE_VERIFIED_DATE, OTPLength, OtpMessage
                {"existingUserLoginFlow", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, SAURON_APP_LOGIN_NUMERIC_V1, "1649322956000", 6, "R-222-01"},
                {"existingTestUserLoginFlow", EXISTING_USER_TEST_MOBILE_NUMBER_LOGIN_FLOW, tenantID, SAURON_APP_LOGIN_NUMERIC_V1, "1691164595000", 4, "R-222-01"},
                {"existingUserTransactionNumericFlow", EXISTING_USER_MOBILE_NUMBER_TXN_FLOW, tenantID, SECUREAPP_WEBAPP_TRANSACTIONS_NUMERIC_V1, "1649325501000", 4, "R-222-01"},
                {"existingUserTransactionAlphaNumericFlow", EXISTING_USER_MOBILE_NUMBER_TXN_FLOW_ALPHANUMERIC, tenantID, SECUREAPP_WEBAPP_TRANSACTIONS_ALPHANUMERIC_V1, "1649857201000", 4, "R-222-01"},
                {"newUserLoginFlow", NEW_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, SAURON_APP_LOGIN_NUMERIC_V1, "null", 6, "R-222-04"},
                {"newUserTransactionNumericFlow", NEW_USER_MOBILE_NUMBER_TXN_FLOW, tenantID, SECUREAPP_WEBAPP_TRANSACTIONS_NUMERIC_V1, "null", 4, "R-222-04"},
                {"newUserTransactionAlphaNumericFlow", NEW_USER_MOBILE_NUMBER_TXN_FLOW_ALPHANUMERIC, tenantID, SECUREAPP_WEBAPP_TRANSACTIONS_ALPHANUMERIC_V1, "null", 4, "R-222-04"},
                {"invalidNumberLoginFlow", "4366600000", tenantID, SAURON_APP_LOGIN_NUMERIC_V1, "", 0, ""},
                {"invalidNumberTxnFlow", "4366600001", tenantID, SAURON_APP_LOGIN_NUMERIC_V1, "", 0, ""},
                {"withoutMobile", "", tenantID, SAURON_APP_LOGIN_NUMERIC_V1, "", 0, ""},
                {"withoutTenantId", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, "", SAURON_APP_LOGIN_NUMERIC_V1, "", 0, ""},
                {"withoutPolicyId", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, "", "", 0, ""},
                {"withoutBody", "", "", "", "", 0, ""},
                {"existingUserMaxRequestAttempts", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_FOR_MAX_OTP_ATTEMPTS, tenantID, SAURON_APP_LOGIN_NUMERIC_V1, "1649322956000", 6, "R-222-04"},

        };
    }

    @DataProvider(name = "ursReSendOTPUserData")
    public Object[][] ursReSendOTPUserData() {
        return new Object[][]{
                //TestCaseName,MobileNumber,TenantId,MOBILE_VERIFIED_DATE, OTPLength, OtpMessage
                {"existingUserLoginFlow", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, "1649322956000", 6, "R-222-01"},
                {"existingTestUserLoginFlow", EXISTING_USER_TEST_MOBILE_NUMBER_LOGIN_FLOW, tenantID, "1691164595000", 4, "R-222-01"},
                {"existingUserTransactionNumericFlow", EXISTING_USER_MOBILE_NUMBER_TXN_FLOW, tenantID, "1649325501000", 4, "R-222-01"},
                {"existingUserTransactionAlphaNumericFlow", EXISTING_USER_MOBILE_NUMBER_TXN_FLOW, tenantID, "1649325501000", 4, "R-222-01"},
                {"newUserLoginFlow", NEW_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, "null", 6, "R-222-04"},
                {"newUserTransactionNumericFlow", NEW_USER_MOBILE_NUMBER_TXN_FLOW, tenantID, "null", 4, "R-222-04"},
                {"newUserTransactionAlphaNumericFlow", NEW_USER_MOBILE_NUMBER_TXN_FLOW, tenantID, "null", 4, "R-222-04"},
                {"withoutId", "", tenantID, "", 0, ""},
                {"invalidOTPId", "4366600000", tenantID, "", 0, ""},
        };
    }

    @DataProvider(name = "ursValidateUserData")
    public Object[][] ursValidateUserData() {
        return new Object[][]{
                //TestCaseName,MobileNumber,TenantId
                {"existingUserLoginFlow", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID},
                {"existingTestUserLoginFlow", EXISTING_USER_TEST_MOBILE_NUMBER_LOGIN_FLOW, tenantID},
                {"existingUserTransactionNumericFlow", EXISTING_USER_MOBILE_NUMBER_TXN_FLOW, tenantID},
                {"existingUserTransactionAlphaNumericFlow", EXISTING_USER_MOBILE_NUMBER_TXN_FLOW_ALPHANUMERIC, tenantID},
                {"newUserLoginFlow", NEW_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID},
                {"newUserTransactionNumericFlow", NEW_USER_MOBILE_NUMBER_TXN_FLOW, tenantID},
                {"newUserTransactionAlphaNumericFlow", NEW_USER_MOBILE_NUMBER_TXN_FLOW_ALPHANUMERIC, tenantID},
                {"withoutId", "", tenantID},
                {"invalidOTPId", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID},
        };
    }

    @DataProvider(name = "ursFindUserData")
    public Object[][] ursFindUserData() {
        return new Object[][]{
                //TestCaseName,MobileNumber, tenantId,MOBILE_VERIFIED_status,MOBILE_VERIFIED_DATE, uuid
                {"existingUserLoginFlow", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, "true", "1649322956000", "8352860290216457903"},
                {"existingUserTransactionNumericFlow", EXISTING_USER_MOBILE_NUMBER_TXN_FLOW, tenantID, "true", "1649325501000", "9003369316324599877"},
                {"existingUserLoginFlowWithUUID", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, "true", "1649322956000", "8352860290216457903"},
                {"newUserLoginFlow", NEW_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, "false", "null", ""},
                {"newUserLoginFlowWithUUID", NEW_USER_MOBILE_NUMBER_LOGIN_FLOW, tenantID, "false", "null", newUserLoginFlowUUID},
                {"newUserTransactionNumericFlow", NEW_USER_MOBILE_NUMBER_TXN_FLOW, tenantID, "false", "null", ""},
                {"withoutMobile", "", tenantID, "false", "null", ""},
                {"withoutTenantId", NEW_USER_MOBILE_NUMBER_TXN_FLOW, "", "false", "null", ""},
                {"withoutBody", "", "", "false", "null", ""},
                {"invalidNumberLoginFlow", INVALID_NUMBER, tenantID, "false", "null", ""},
        };
    }


    @SneakyThrows
    protected void deleteUserData() {
        purgeUser(NEW_USER_MOBILE_NUMBER_LOGIN_FLOW);
        purgeUser(NEW_USER_MOBILE_NUMBER_TXN_FLOW);
        purgeUser(NEW_USER_MOBILE_NUMBER_TXN_FLOW_ALPHANUMERIC);
    }
}

