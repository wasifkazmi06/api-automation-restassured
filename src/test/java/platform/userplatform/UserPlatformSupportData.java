package platform.userplatform;

import api.ams.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbUtils.OTP_MySQL_DBAccessObject;
import dbUtils.ams_MySQL_DBAccessObject;
import dbUtils.userRegistration_MySQL_DBAccessObject;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import platform.PlatformSupportData;
import pojos.ams.*;
import pojos.userRegistration.FetchOTPPojo;
import util.StringTemplate;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserPlatformSupportData extends PlatformSupportData {


    public static String CLIENT_ID_ONE = "4gmux44oim-server-consumer";
    public static String CLIENT_ID_TWO = "g9xpxctq5f-server-consumer";
    public static String EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW = "6100000170";
    public static String EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE = "7848862348";
    public static String EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_2 = "6100000170";
    public static String EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO = "6100001010";
    public static String EXISTING_USER_MOBILE_NUMBER_TXN_FLOW = "6100000175";
    public static String NEW_USER_MOBILE_NUMBER_TXN_FLOW = "6110000595";
    public static String NEW_USER_MOBILE_NUMBER_LOGIN_FLOW = "6110000598";
    public static String NEW_USER_MOBILE_NUMBER_OTP_FLOW = "9181716151";
    public static String NEW_USER_MOBILE_NUMBER_OTP_FLOW1 = "6110000601";
    public static String NEW_USER_MOBILE_NUMBER_OTP_FLOW2 = "6110000602";
    public static String NEW_USER_MOBILE_NUMBER_OTP_FLOW3 = "6110000603";
    public static String NEW_USER_MOBILE_NUMBER_OTP_FLOW4 = "6110000604";
    public static String NEW_USER_MOBILE_NUMBER_OTP_FLOW5 = "6110000605";
    public static String INVALID_NUMBER = "5134267890";
    public static String TENANT_ID = "ec35b493";
    public static String SOURCE = "fetch_otp";
    public static String BBPS_OTP_SOURCE = "MBE";
    public static String AUTO_OTP_SOURCE = "AUTO_OTP";
    public static String BILLER_NAME = "SWIGGY";
    public static String IVR_SOURCE = "IVR";
    public static String OTP_FORMAT = "ALPHA_NUMERIC";
    public static String NEW_USER_MOBILE_NUMBER_BNPL_OTP_FLOW = "6100001001";
    public static String NEW_USER_MOBILE_NUMBER_TXN_FLOW_ONE = "6100001002";
    protected static final String GET_OTP_ATTEMPT_REQUEST = "SELECT # FROM userregistration.user_login_details WHERE um_uuid in (SELECT um_uuid  FROM `user` u WHERE mobile = '$')";
    protected static final String GET_UUID = "SELECT # FROM userregistration.user WHERE mobile='$'";
    protected static final String DELETE_MOBILE = "DELETE FROM userregistration.user WHERE mobile='$'";
    protected static final String GET_OAUTH_ACCESS_TOKEN = "SELECT # FROM lazypayplatform.oauth_access_token WHERE user_uuid='$'";
    protected static final String GET_OAUTH_ACCESS_TOKEN_WITH_CLIENT_ID = "SELECT # FROM lazypayplatform.oauth_access_token WHERE user_uuid='$' AND client_id='&'";
    protected static final String GET_REFRESH_ACCESS_TOKEN_WITH_CLIENT_ID = "SELECT # FROM lazypayplatform.oauth_access_token WHERE user_uuid='$' AND client_id='&'";
    protected static final String GET_REFRESH_ACCESS_TOKEN = "SELECT # FROM lazypayplatform.oauth_access_token WHERE user_uuid='$'";

    protected static final String UPDATE_EXPIRY_TOKEN_DATE = "UPDATE `lazypayplatform`.`oauth_access_token` SET `token_expiry` = '2022-07-22 15:56:21' WHERE `user_uuid` = '$'";
    protected static final String UPDATE_OTP_REQUESTED_ATTEMPTS = "UPDATE userregistration.user_login_details SET otp_request_attempts=0 WHERE um_uuid in (SELECT um_uuid FROM userregistration.user WHERE mobile='$')";
    protected static final String UPDATE_OTP_LOGIN_ATTEMPTS = "UPDATE userregistration.user_login_details SET login_attempts=0 WHERE um_uuid in (SELECT um_uuid FROM userregistration.user WHERE mobile='$')";
    protected static final String GET_OTP_VALUE = "SELECT # from lazypayplatform.authentication_otp WHERE mobile='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_TYPE = "SELECT # from lazypayplatform.authentication_otp WHERE mobile='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_FORMAT = "SELECT # from lazypayplatform.authentication_otp WHERE mobile='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_STATUS = "SELECT # from otpdb.otp WHERE otp_id='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_REF_ID = "SELECT # from lazypayplatform.authentication_otp WHERE mobile='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_SENT_TIME = "SELECT # from otpdb.otp WHERE otp_id='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_EXPIRY_TIME = "SELECT # from otpdb.otp WHERE otp_id='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_CLIENT_ID = "SELECT # from lazypayplatform.authentication_otp WHERE mobile='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_CREATED_AT = "SELECT # from otpdb.otp WHERE otp_id='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_OTP_UPDATED_AT = "SELECT # from otpdb.otp WHERE otp_id='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_IVR_REQUEST_ID = "SELECT # from lazypayplatform.authentication_ivr WHERE mobile='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_IVR_ID = "SELECT # from lazypayplatform.authentication_ivr WHERE id='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_IVR_STATUS = "SELECT # from lazypayplatform.authentication_ivr WHERE id='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String GET_IVR_CALL_STATUS = "SELECT # from lazypayplatform.authentication_ivr WHERE id='$'ORDER BY created_at DESC LIMIT 1";
    protected static final String DELETE_OTP_REQUEST_FROM_OTP = "DELETE FROM lazypayplatform.authentication_otp WHERE id = $";
    protected static final String DELETE_IVR_REQUEST_FROM_IVR = "DELETE FROM lazypayplatform.authentication_ivr WHERE id = $";
    protected static final String UPDATE_IVR_STATUS = "UPDATE lazypayplatform.authentication_ivr SET status = 'SENT_TO_KARIX' WHERE id = $";
    protected static final String UPDATE_IVR_CALL_STATUS_AND_DETAILS = "UPDATE lazypayplatform.authentication_ivr SET details = NULL,callStatus = NULL WHERE id = $";
    protected static final String LOGIN_PAYLOAD_FILE_PATH = "src/test/resources/ams/loginrequest.txt";
    protected static final String SEND_OTP_PAYLOAD_FILE_PATH = "src/test/resources/ams/sendotp.txt";
    protected static final String LOGIN_PAYLOAD_NULL_CLIENT_FILE_PATH = "src/test/resources/ams/loginrequest_nullclient.txt";
    protected static final String CLICK_ONBOARD_REQUEST_FILE_PATH = "src/test/resources/ams/clinkOnboardRequest.txt";


    oauthToken oauthtoken = new oauthToken();
    oauthTokenError oauthtokenerror = new oauthTokenError();
    verifyMobileSignIn verifymobilesignin = new verifyMobileSignIn();
    bbpsInitiateOtp bbpsinitiateotp = new bbpsInitiateOtp();
    bbpsValidateOtp bbpsvalidateotp = new bbpsValidateOtp();
    bbpsIvrCallBack bbpsivrcallback = new bbpsIvrCallBack();
    bbpsInitiateOtpRequestPojo bbpsinitiateotprequest = new bbpsInitiateOtpRequestPojo();
    bbpsValidateOtpRequestPojo bbpsvalidateotprequest = new bbpsValidateOtpRequestPojo();
    bbpsCallBackApiRequestPojo bbpscallbackrequestpojo = new bbpsCallBackApiRequestPojo();
    SourceRequest sourcerequest = new SourceRequest();


    public UserPlatformSupportData() throws Exception {
    }

    public static String clientRefIdGenerator() {
        int min = 10000;
        int max = 99999;
        int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return "TXN" + randomInt;
    }


    PlatformOTP generateotp = new PlatformOTP();
    amsTestData amstestdata = new amsTestData();
    createUser createuser = new createUser();


    @Step
    public String loginRequestPayload(String mobileNumber, String clientID, String clientSecret, boolean validOtp) {
        String loginRequest;
        if (clientID == null) {
            loginRequest = new StringTemplate(LOGIN_PAYLOAD_NULL_CLIENT_FILE_PATH)
                    .replace("clientSecret", clientSecret)
                    .replace("mobile", mobileNumber)
                    .replace("otp", generateOtp(mobileNumber, validOtp)).toString();
        } else {
            loginRequest = new StringTemplate(LOGIN_PAYLOAD_FILE_PATH)
                    .replace("clientId", clientID)
                    .replace("clientSecret", clientSecret)
                    .replace("mobile", mobileNumber)
                    .replace("otp", generateOtp(mobileNumber, validOtp)).toString();
        }
        return loginRequest;
    }


    public String clickOnboardRequestPayload() {
        return new StringTemplate(CLICK_ONBOARD_REQUEST_FILE_PATH)
                .replace("mobile", amstestdata.mobileNumber)
                .replace("email", amsTestData.email)
                .replace("accessKey", amsTestData.accessKey)
                .replace("source", amsTestData.source)
                .toString();
    }

    public String clickOnboardReqPayloadWrongMobileNumber() {
        return new StringTemplate(CLICK_ONBOARD_REQUEST_FILE_PATH)
                .replace("mobile", amstestdata.wrongMobileNumber)
                .replace("email", amsTestData.email)
                .replace("accessKey", amsTestData.accessKey)
                .replace("source", amsTestData.source)
                .toString();
    }

    public String clickOnboardReqPayloadWrongsource() {
        return new StringTemplate(CLICK_ONBOARD_REQUEST_FILE_PATH)
                .replace("mobile", amstestdata.wrongMobileNumber)
                .replace("email", amsTestData.email)
                .replace("accessKey", amsTestData.accessKey)
                .replace("source", amstestdata.wrongSource)
                .toString();
    }


    @Step
    public String generateOtp(String mobileNumber, boolean validOtp) {
        String otpValue = "123456";
        if (validOtp) {
            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();
            headerDetails.put("Content-Type", "application/json");
            headerDetails.put("Accept", "application/json");
            queryParamDetails.put("clientId", "n5uxkxcm1q-signin-new");
            queryParamDetails.put("clientSecret", "8cjyxu6xgiit0459c5js75yj76m5g2mh");
            queryParamDetails.put("mobile", mobileNumber);
            queryParamDetails.put("lazypayLogin", "true");
            queryParamDetails.put("source", SOURCE);
            queryParamDetails.put("policyId", "1");
            validateUserPojo findOrCreateUserResponse = createuser.post(queryParamDetails, headerDetails, "");
            otpValue = findOrCreateUserResponse.getResponseData().getOtpValue();
        }
        return otpValue;
    }

    public void sendOTP(String mobileNumber) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("webLogin", "true");
        String otpRequest = new StringTemplate(SEND_OTP_PAYLOAD_FILE_PATH)
                .replace("clientId", amsTestData.clientId)
                .replace("clientSecret", amsTestData.clientSecret)
                .replace("mobile", mobileNumber).toString();
        PlatformOtpPojo generateResponse = generateotp.postWithResponse(queryParamDetails, headerDetails, otpRequest).as(PlatformOtpPojo.class);
        String status = generateResponse.getStatus();
        Assert.assertEquals(status, "true", "Status value doesn't match");

    }

    public static String ivrDateValidation() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdf.format(date);
    }


    @SneakyThrows
    @Step
    public static String getuuidFromUserDb(String uuid, String mobile) {
        String s = "";
        ResultSet rs = userRegistration_MySQL_DBAccessObject.selectFromMySqlDb(GET_UUID.replace("#", uuid).replace("$", mobile));

        while (rs.next())
            s = rs.getString(uuid);
        Allure.addAttachment("Execution Result==> ", " MySqlDbAccessObject: selectFromMySqlDb - " + s);
        return s;

    }


    @SneakyThrows
    @Step
    public static void deleteUserDataFromUser(String uuid) {
        try {
            userRegistration_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_MOBILE.replace("$", uuid));
        } catch (Exception e) {
            log.debug("User entry is already deleted in the Table" + e);
        }


    }

    @SneakyThrows
    @Step
    public static String getOauthToken(String accessToken, String uuid) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OAUTH_ACCESS_TOKEN.replace("#", accessToken).replace("$", uuid));
        while (rs.next())
            s = rs.getString(accessToken);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getrefreshToken(String refreshToken, String uuid) {
        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_REFRESH_ACCESS_TOKEN.replace("#", refreshToken).replace("$", uuid));
        while (rs.next())
            s = rs.getString(refreshToken);
        return s;
    }


    @SneakyThrows
    @Step
    public static String getClientId(String clientId, String uuid) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_CLIENT_ID.replace("#", clientId).replace("$", uuid));
        while (rs.next())
            s = rs.getString(clientId);
        return s;
    }


    @SneakyThrows
    @Step
    public static void updateExpiryTokenDate(String uuid) {
        ams_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_EXPIRY_TOKEN_DATE.replace("$", uuid));
    }

    @SneakyThrows
    @Step
    public static void updateOtpRequestedAttempt(String mobile) {
        userRegistration_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_OTP_REQUESTED_ATTEMPTS.replace("$", mobile));
    }

    @SneakyThrows
    @Step
    public static void updateIvrStatus(String requestId) {
        ams_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_IVR_STATUS.replace("$", requestId));
    }

    @SneakyThrows
    @Step
    public static void updateIvrCallStatusAndDetails(String requestId) {
        ams_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_IVR_CALL_STATUS_AND_DETAILS.replace("$", requestId));
    }

    @SneakyThrows
    @Step
    public static void updateLoginAttempts(String mobile) {
        userRegistration_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_OTP_LOGIN_ATTEMPTS.replace("$", mobile));
    }

    @SneakyThrows
    @Step
    public static String getOauthToken(String accessToken, String uuid, String clientId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OAUTH_ACCESS_TOKEN_WITH_CLIENT_ID.replace("#", accessToken).replace("$", uuid).replace("&", clientId));
        while (rs.next())
            s = rs.getString(accessToken);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getrefreshToken(String refreshToken, String uuid, String clientId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_REFRESH_ACCESS_TOKEN_WITH_CLIENT_ID.replace("#", refreshToken).replace("$", uuid).replace("&", clientId));
        while (rs.next())
            s = rs.getString(refreshToken);
        return s;
    }

    @SneakyThrows
    @Step
    public static int getOtpRequestAttemptedFromUserDb(String otpRequestAttempt, String mobile) {

        int s = 0;
        ResultSet rs = userRegistration_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_ATTEMPT_REQUEST.replace("#", otpRequestAttempt).replace("$", mobile));
        while (rs.next())
            s = rs.getInt(otpRequestAttempt);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getOtpValue(String otpValue, String mobile) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_VALUE.replace("#", otpValue).replace("$", mobile));
        while (rs.next())
            s = rs.getString(otpValue);
        return s;
    }

    @SneakyThrows
    @Step
    public static int getIvrRequestId(String ivrRequestId, String mobile) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_IVR_REQUEST_ID.replace("#", ivrRequestId).replace("$", mobile));
        while (rs.next())
            s = rs.getInt(ivrRequestId);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getIvrId(String ivrId, String ivrRequestId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_IVR_ID.replace("#", ivrId).replace("$", ivrRequestId));
        while (rs.next())
            s = rs.getString(ivrId);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getIvrStatus(String status, String ivrRequestId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_IVR_STATUS.replace("#", status).replace("$", ivrRequestId));
        while (rs.next())
            s = rs.getString(status);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getIvrCallStatus(String callStatus, String ivrRequestId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_IVR_CALL_STATUS.replace("#", callStatus).replace("$", ivrRequestId));
        while (rs.next())
            s = rs.getString(callStatus);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getOtpType(String otpType, String mobile) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_TYPE.replace("#", otpType).replace("$", mobile));
        while (rs.next())
            s = rs.getString(otpType);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getOtpFormat(String otpFormat, String mobile) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_FORMAT.replace("#", otpFormat).replace("$", mobile));
        while (rs.next())
            s = rs.getString(otpFormat);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getOtpStatus(String otpStatus, String mobile) {

        String s = "";
        ResultSet rs = OTP_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_STATUS.replace("#", otpStatus).replace("$", mobile));
        while (rs.next())
            s = rs.getString(otpStatus);
        return s;
    }

    @SneakyThrows
    @Step
    public static String getClientRefId(String clientRefId, String mobile) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_REF_ID.replace("#", clientRefId).replace("$", mobile));
        while (rs.next())
            s = rs.getString(clientRefId);
        return s;
    }

    @SneakyThrows
    @Step
    public static Timestamp getOtpCreatedAt(String otpCreatedAt, String mobile) {

        Timestamp s = null;
        ResultSet rs = OTP_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_CREATED_AT.replace("#", otpCreatedAt).replace("$", mobile));
        while (rs.next())
            s = rs.getTimestamp(otpCreatedAt);
        Allure.addAttachment("Execution Result==> ", " MySqlDbAccessObject: selectFromMySqlDb - " + s);
        return s;
    }

    @SneakyThrows
    @Step
    public static Timestamp getOtpUpdatedAt(String otpUpdatedAt, String mobile) {

        Timestamp s = null;
        ResultSet rs = OTP_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_UPDATED_AT.replace("#", otpUpdatedAt).replace("$", mobile));
        while (rs.next())
            s = rs.getTimestamp(otpUpdatedAt);
        Allure.addAttachment("Execution Result==> ", " MySqlDbAccessObject: selectFromMySqlDb - " + s);
        return s;
    }

    @SneakyThrows
    @Step
    public static Timestamp getOtpSentTime(String otpSentTime, String mobile) {

        Timestamp s = null;
        ResultSet rs = OTP_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_SENT_TIME.replace("#", otpSentTime).replace("$", mobile));
        while (rs.next())
            s = rs.getTimestamp(otpSentTime);
        return s;
    }

    @SneakyThrows
    @Step
    public static Timestamp getOtpExpiryTime(String otpExpiryTime, String mobile) {

        Timestamp s = null;
        ResultSet rs = OTP_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_EXPIRY_TIME.replace("#", otpExpiryTime).replace("$", mobile));
        while (rs.next())
            s = rs.getTimestamp(otpExpiryTime);
        return s;
    }

    @SneakyThrows
    @Step
    public static void deleteRequestIdDataFromAuthenticationOtp(String requestId) {
        try {
            ams_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_OTP_REQUEST_FROM_OTP.replace("$", requestId));
        } catch (Exception e) {
            log.debug("otp entry is already deleted in the Table" + e);
        }

    }

    @SneakyThrows
    @Step
    public static void deleteRequestIdDataFromAuthenticationIvr(String requestId) {
        try {
            ams_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_IVR_REQUEST_FROM_IVR.replace("$", requestId));
        } catch (Exception e) {
            log.debug("ivr entry is already deleted in the Table" + e);
        }
    }

    /**
     * TestCaseName, MobileNumber, LazypayloginParam,ClientId,MOBILE_VERIFIED_DATE
     */
    @DataProvider(name = "findOrCreateUserData")
    public Object[][] findOrCreateUserData() {
        return new Object[][]{
                // TestCaseName, MobileNumber, PolicyID,ClientId,MOBILE_VERIFIED_DATE, OTPLength, OtpMessage, userType
                {"existingUserLoginFlow", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, SAURON_APP_LOGIN_NUMERIC_V1, amsTestData.clientId, "1649322956000", 6, "R-222-01"},
                {"existingUserTransactionFlow", EXISTING_USER_MOBILE_NUMBER_TXN_FLOW, SECUREAPP_WEBAPP_TRANSACTIONS_NUMERIC_V1, amsTestData.clientId, "1649325501000", 4, "R-222-01"},
                {"newUserLoginFlow", NEW_USER_MOBILE_NUMBER_LOGIN_FLOW, SAURON_APP_LOGIN_NUMERIC_V1, amsTestData.clientId + "test", "null", 6, "R-222-04"},
                {"newUserTransactionFlow", NEW_USER_MOBILE_NUMBER_TXN_FLOW, SECUREAPP_WEBAPP_TRANSACTIONS_NUMERIC_V1, amsTestData.clientId + "test", "null", 4, "R-222-04"},
                {"invalidNumberLoginFlow", "4366600000", SAURON_APP_LOGIN_NUMERIC_V1, amsTestData.clientId, "", 0, ""},
                {"invalidNumberTxnFlow", "4366600001", SECUREAPP_WEBAPP_TRANSACTIONS_NUMERIC_V1, amsTestData.clientId, "", 0, ""},
                {"withoutMobileParam", null, SAURON_APP_LOGIN_NUMERIC_V1, amsTestData.clientId, "", 0, ""},
                {"withoutPolicyId", "6100000171", "", amsTestData.clientId + "test1", "1649323251000", 4, "R-222-01"},
                {"withoutSourceParam", "4366600001", SAURON_APP_LOGIN_NUMERIC_V1, amsTestData.clientId, "", 0, ""},
                {"withoutParam", null, null, amsTestData.clientId, "", 0, ""}
        };
    }

    @DataProvider(name = "getUserInfoTestData")
    public Object[][] getUserInfoTestData() {
        return new Object[][]{
                {"withIdentifier", "DIGI-011"},
                {"withoutIdentifier", "DIGI-012"},
                {"withNewUserIdentifier", "DIGI-011"}

        };
    }


    @DataProvider(name = "sendOTPTestData")
    public Object[][] sendOTPTestData() {
        return new Object[][]{
                //TestCaseName,MobileNumber,ClientId,ClientSecret,statusCode
                {"validNumberForAppLogin", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, amsTestData.clientSecret, "200"},
                {"validNumberForMerchantLogin", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, amsTestData.clientSecret, "200"},
                {"inValidNumber", "2100000170", amsTestData.clientId, amsTestData.clientSecret, "401"},
                {"withoutHeaders", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, amsTestData.clientSecret, "200"},
                {"withoutClientID", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, "", amsTestData.clientSecret, "200"},
                {"withoutClientSecret", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, "", "200"},
                {"withoutParams", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, amsTestData.clientSecret, "200"},
        };
    }

    @DataProvider(name = "getUserDetailsTestData")
    public Object[][] getUserDetailsTestData() {
        return new Object[][]{
                {"validNumber", "DIGI-01"},
                {"validumuuid", "DIGI-02"},
                {"validlpuuid", "DIGI-03"},
                {"validToken", "DIGI-04"},
                {"inValidNumber", "DIGI-05"},
                {"withoutHeaders", "DIGI-06"},
                {"withoutParams", "DIGI-07"},
        };
    }

    @DataProvider(name = "userLoginTokenGenData")
    public Object[][] userLoginTokenGenData() {
        return new Object[][]{
                {"existingUserLoginFlow", "DIGI-01"},
                {"newUserLoginFlow", "DIGI-02"},
                {"withoutHeaders", "DIGI-03"},
                {"withoutClientID", "DIGI-04"},
                {"withoutClientSecret", "DIGI-05"},
                {"invalidNumber", "DIGI-06"},
                {"validNumberWithInvalidOTP", "DIGI-07"},
                //TODO:Rakesh need to add the functions once the P2P2 is live in sbox
                //{"validNumberWithExpiredOTP", "DIGI-08"},
                //{"testNumberWithStaticOTP", "DIGI-09"},
                {"withoutPayload", "DIGI-10"}
        };
    }

    @DataProvider(name = "validateTokenData")
    public Object[][] validateTokenData() {
        return new Object[][]{
                {"withoutScopeParam", "DIGI-03"},
                {"withoutParam", "DIGI-04"},
                {"validToken", "DIGI-01"},
                {"invalidToken", "DIGI-02"},
        };
    }

    @DataProvider(name = "refreshTokenData")
    public Object[][] refreshTokenData() {
        return new Object[][]{
                {"validToken", "DIGI-01"},
                {"invalidToken", "DIGI-02"},
                {"withoutHeaders", "DIGI-03"},
                {"invalidClientId", "DIGI-04"},
                {"withoutClientId", "DIGI-05"},
                {"invalidClientSecret", "DIGI-06"},
                {"withoutClientSecret", "DIGI-07"},
                {"withoutToken", "DIGI-08"}
        };
    }

    @DataProvider(name = "invalidateTokenData")
    public Object[][] invalidateTokenData() {
        return new Object[][]{
                {"validToken", "DIGI-01"},
                {"invalidToken", "DIGI-02"},
                {"withoutHeaders", "DIGI-03"},
                {"withoutParam", "DIGI-04"},
                {"expiredToken", "DIGI-05"}
        };
    }

    @SneakyThrows
    @Step
    public String fetchOTP(String mobileNumber) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("mobile", mobileNumber);
        queryParamDetails.put("tenantId", "ec35b493");
        FetchOTPPojo fetchOTPPojo = fetchOTP.get(queryParamDetails, headerDetails);
        return fetchOTPPojo.getOtpValue();
    }

    @SneakyThrows
    @Step
    public String getFourDigitNumericOTP(String clientRefId, String clientId, String mobile) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("clientId", clientId);
        queryParamDetails.put("clientRefId", clientRefId);
        queryParamDetails.put("mobile", mobile);
        queryParamDetails.put("lazypayLogin", "false");
        queryParamDetails.put("source", SOURCE);
        queryParamDetails.put("policyId", SECUREAPP_WEBAPP_TRANSACTIONS_NUMERIC_V1);
        validateUserPojo findOrCreateUserResponse = createuser.post(queryParamDetails, headerDetails, "");
        String otp = findOrCreateUserResponse.getResponseData().otpValue;
        uuid = findOrCreateUserResponse.getResponseData().getProfileData().getUuid();
        OtpId = findOrCreateUserResponse.getResponseData().getOtpId().toString();
        Assert.assertEquals(findOrCreateUserResponse.getResponseData().getOtpValue().length(), 4);
        return otp;
    }


    @SneakyThrows
    @Step
    public String requestTxnOTP(String policyId, String mobile) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("mobile", mobile);
        queryParamDetails.put("policyId", policyId);
        validateUserPojo findOrCreateUserResponse = createuser.post(queryParamDetails, headerDetails, "");
        otp = findOrCreateUserResponse.getResponseData().otpValue;
        uuid = findOrCreateUserResponse.getResponseData().getProfileData().getUuid();
        OtpId = findOrCreateUserResponse.getResponseData().getOtpId().toString();
        return otp;
    }


    String OtpId, uuid, otp;

    @SneakyThrows
    @Step
    public String getFourDigitAlphanumericOTP(String clientRefId, String clientId, String mobileNumber) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("clientId", clientId);
        queryParamDetails.put("mobile", mobileNumber);
        queryParamDetails.put("lazypayLogin", "false");
        queryParamDetails.put("source", SOURCE);
        queryParamDetails.put("clientRefId", clientRefId);
        queryParamDetails.put("otpFormat", OTP_FORMAT);
        queryParamDetails.put("policyId", SECUREAPP_WEBAPP_TRANSACTIONS_ALPHANUMERIC_V1);
        validateUserPojo findOrCreateUserResponse = createuser.post(queryParamDetails, headerDetails, "");
        String otp = findOrCreateUserResponse.getResponseData().otpValue;
        OtpId = findOrCreateUserResponse.getResponseData().getOtpId().toString();
        uuid = findOrCreateUserResponse.getResponseData().getProfileData().getUuid();
        Assert.assertEquals(findOrCreateUserResponse.getResponseData().getOtpValue().length(), 4);
        return otp;
    }

    @SneakyThrows
    @Step
    public OauthTokenResponse oauthTokenResponse(String otp, String clientId, String clientRefId, String mobile) {
        HashMap<String, Object> headerDetails = new HashMap<>();
        HashMap<String, Object> formParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/x-www-form-urlencoded");
        formParamDetails.put("grant_type", "onetimepass");
        formParamDetails.put("client_id", clientId);
        formParamDetails.put("password", otp);
        formParamDetails.put("username", mobile);
        formParamDetails.put("clientRefId", clientRefId);
        formParamDetails.put("otpId", OtpId);
        return oauthtoken.postWithFormParam(headerDetails, formParamDetails);
    }

    @SneakyThrows
    @Step
    public OauthTokenResponse verifyMobileSignInResponse(String uuid, String otp, String clientId, String clientRefId) {
        HashMap<String, Object> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("uuid", uuid);
        queryParamDetails.put("verificationCode", otp);
        queryParamDetails.put("clientId", clientId);
        queryParamDetails.put("clientRefId", clientRefId);
        queryParamDetails.put("otpId", OtpId);
        return verifymobilesignin.postWithQueryParam(headerDetails, queryParamDetails);
    }

    @SneakyThrows
    @Step
    public oauthTokenWrongOtpResponse oauthTokenWithWrongOtpResponse(String otp, String clientId, String clientRefId, String mobile) {
        HashMap<String, Object> headerDetails = new HashMap<>();
        HashMap<String, Object> formParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/x-www-form-urlencoded");
        formParamDetails.put("grant_type", "onetimepass");
        formParamDetails.put("client_id", clientId);
        formParamDetails.put("password", otp);
        formParamDetails.put("username", mobile);
        formParamDetails.put("clientRefId", clientRefId);
        formParamDetails.put("otpId", OtpId);
        return oauthtokenerror.postWithFormParam(headerDetails, formParamDetails);
    }

    public String initiatebbpsOtpRequest(String clientId, String mobile, String clientrefId, int otpExpiryTime,
                                         String otpsource, String source, String billername) throws JsonProcessingException {
        sourcerequest.setBillerName(billername);
        bbpsinitiateotprequest.setClientId(clientId);
        bbpsinitiateotprequest.setMobile(mobile);
        bbpsinitiateotprequest.setClientRefId(clientrefId);
        bbpsinitiateotprequest.setOtpType("SixDigit");
        bbpsinitiateotprequest.setOtpFormat("NUMERIC");
        bbpsinitiateotprequest.setOtpExpiry(otpExpiryTime);
        bbpsinitiateotprequest.setOtpSource(otpsource);
        bbpsinitiateotprequest.setSource(source);
        bbpsinitiateotprequest.setSourceRequest(sourcerequest);
        ObjectMapper objectmapper = new ObjectMapper();
        return objectmapper.writeValueAsString(bbpsinitiateotprequest);
    }

    public String bbpsivrcallBackrequest(String ivrId, String mobile, String status, String reason, String duration, String keyPress) throws JsonProcessingException, ParseException {
        bbpscallbackrequestpojo.setCamp_id("525713");
        bbpscallbackrequestpojo.setIvr_id(ivrId);
        bbpscallbackrequestpojo.setMobile(mobile);
        bbpscallbackrequestpojo.setPickup_Time(ivrDateValidation());
        bbpscallbackrequestpojo.setHangup_Time(ivrDateValidation());
        bbpscallbackrequestpojo.setStatus(status);
        bbpscallbackrequestpojo.setReason(reason);
        bbpscallbackrequestpojo.setDuration(duration);
        bbpscallbackrequestpojo.setKeyPress(keyPress);
        ObjectMapper objectmapper = new ObjectMapper();
        return objectmapper.writeValueAsString(bbpscallbackrequestpojo);
    }

    @SneakyThrows
    @Step
    public bbpsInitiateOtpResponse bbpsinitiateotp(String clientId, String mobile, String clientrefId, int otpExpiry, String otpsource, String source, String billername) {
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        return bbpsinitiateotp.post(queryParamDetails, headerDetails, initiatebbpsOtpRequest(clientId, mobile,
                clientrefId, otpExpiry, otpsource, source, billername));
    }

    @SneakyThrows
    @Step
    public Response bbpsinitiateotpForWrongSource(String clientId, String mobile, String clientrefId, int otpExpiryTime,
                                                  String otpsource, String source, String billername) {
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        return bbpsinitiateotp.postWithResponse(queryParamDetails, headerDetails,
                initiatebbpsOtpRequest(clientId, mobile, clientrefId, otpExpiryTime, otpsource, source, billername));
    }

    @SneakyThrows
    @Step
    public bbpsValidateOtpResponse bbpsvalidateotp(String clientId, String mobile, String clientrefId,
                                                   String otpsource, String source, String otpValue, String requestId) {
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        bbpsvalidateotprequest.setClientId(clientId);
        bbpsvalidateotprequest.setMobile(mobile);
        bbpsvalidateotprequest.setClientRefId(clientrefId);
        bbpsvalidateotprequest.setRequestId(requestId);
        bbpsvalidateotprequest.setOtpValue(otpValue);
        bbpsvalidateotprequest.setOtpSource(otpsource);
        bbpsvalidateotprequest.setSource(source);
        ObjectMapper objectmapper = new ObjectMapper();
        String JsonBody = objectmapper.writeValueAsString(bbpsvalidateotprequest);
        return bbpsvalidateotp.post(queryParamDetails, headerDetails, JsonBody);
    }

    @SneakyThrows
    @Step
    public Response bbpsCallBack(String ivrId, String mobile, String status, String reason, String duration, String keyPress) {
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        return bbpsivrcallback.postWithResponse(queryParamDetails, headerDetails, bbpsivrcallBackrequest(ivrId, mobile, status, reason, duration, keyPress));
    }

    @SneakyThrows
    @Step
    public bbpsCallBackResponse bbpsCallBackError(String ivrId, String mobile, String status, String reason, String duration, String keyPress) {
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        return bbpsivrcallback.post(queryParamDetails, headerDetails, bbpsivrcallBackrequest(ivrId, mobile, status, reason, duration, keyPress));
    }


}
