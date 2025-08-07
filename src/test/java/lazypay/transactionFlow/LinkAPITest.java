package lazypay.transactionFlow;

import api.lazypay.transaction.SendOTP;
import api.lazypay.transaction.ValidateOTP;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lazypay.LazypayConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.ValidateOTPPojo;
import util.FindOrCreateUserOTP;
import util.StringTemplate;
import java.util.HashMap;

public class LinkAPITest {

    SendOTP sendOTP  = new SendOTP();
    String signature = null;
    String otp = null;
    ValidateOTPPojo validateOTPPojo = new ValidateOTPPojo();
    ValidateOTP validateOTP = new ValidateOTP();
    static int retryCount = 0;

    public LinkAPITest() throws Exception {
    }

    @Description("To verify that an eligible user can send OTP for account linking for a merchant that does not require user's email address")
    @Feature(value = "Link API")
    @Test(priority = 1, groups = {"transaction", "regression", "sanity"})
    public void sendOTPEligibleUserMobileOnly() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile=" + TransactionData.USER_MOBILE_VALID6
                + "&email=" , TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_VALID6)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 200, "Check that the user is correctly whitelisted from DS");

    }

    @Description("To verify that an eligible user can validate OTP for account linking for a merchant that does not require user's email address")
    @Feature(value = "Link API")
    @Test(priority = 2, groups = {"transaction", "regression", "sanity"})
    public void validateOTPEligibleUserMobileOnly() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        try{
            do{
                if(System.getProperty("env").equals("QA"))
                {
                    otp = "1234";
                }else {
                    otp = FindOrCreateUserOTP.getLatestOTP(TransactionData.USER_MOBILE_VALID6);
                }
                signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile=" + TransactionData.USER_MOBILE_VALID6
                        + "&email=" + "&otp=" + otp, TransactionData.ACCESS_KEY);

                headerDetails.put("signature", signature);
                headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

                String validateOTPRequest = new StringTemplate(LazypayConstants.VALIDATE_OTP)
                        .replace("mobile", TransactionData.USER_MOBILE_VALID6)
                        .replace("email", "")
                        .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                        .replace("otp", otp)
                        .replace("firstName", TransactionData.FIRST_NAME)
                        .replace("lastName", TransactionData.LAST_NAME)
                        .toString();

                validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPRequest);
                retryCount++;
            }while(validateOTPPojo.status.equals("401") && validateOTPPojo.errorCode.equals("LP_INCORRECT_OTP") && retryCount <=2);
        }catch(NullPointerException e){
        }

        Assert.assertNotNull(validateOTPPojo.access_token, "Check that the user is correctly whitelisted from DS");

    }

    @Description("To verify that an ineligible user should not be able to send OTP for account linking")
    @Feature(value = "Link API")
    @Test(priority = 3, groups = {"transaction", "regression", "sanity"})
    public void sendOTPIneligibleUser() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile=" + TransactionData.USER_MOBILE_INELIGIBLE +
                "&email=", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_INELIGIBLE)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 400, "Check that if user has been whitelisted from DS");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode") , "LP_USER_INELIGIBLE", "Check that if user has been whitelisted from DS");

    }

    @Description("To verify that an error is thrown when an incorrect OTP is passed in the validate OTP request")
    @Feature(value = "Link API")
    @Test(priority = 4, groups = {"transaction", "regression"})
    public void validateOTPInvalidOTP() throws Exception {


        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        otp="1234";
        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile=" + TransactionData.USER_MOBILE_VALID6
                + "&email=" + "&otp=" + otp, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String validateOTPRequest= new StringTemplate(LazypayConstants.VALIDATE_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_VALID6)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("otp", otp)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPRequest);
        Assert.assertEquals(validateOTPPojo.status, "401", "Check that an incorrect OTP has been passed in the request");
        Assert.assertEquals(validateOTPPojo.errorCode , "LP_INCORRECT_OTP","Check that an incorrect OTP has been passed in the request" );

    }

    @Description("To verify that a user who has opted out of Lazypay services should not be able to send OTP for account linking")
    @Feature(value = "Link API")
    @Test(priority = 5, groups = {"transaction", "regression"})
    public void sendOTPOptedOutUser() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile="
                + TransactionData.USER_MOBILE_OPTED_OUT + "&email=", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_OPTED_OUT)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 400, "Check that the user used is opted out or not.");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode") , "LP_USER_OPTED_OUT","Check that the user used is opted out or not.");

    }

    @Description("To verify that a blocked user should not be able to send OTP for account linking")
    @Feature(value = "Link API")
    @Test(priority = 6, groups = {"transaction", "regression"})
    public void sendOTPBlockedUser() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile="
                + TransactionData.USER_MOBILE_BLOCKED + "&email=", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_BLOCKED)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 400, "Check that the user used is blocked out or not.");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode") , "LP_USER_BLOCKED","Check that the user used is blocked out or not.");
    }

    @Description("To verify that a blacklisted user should not be able to send OTP for account linking")
    @Feature(value = "Link API")
    @Test(priority = 7, groups = {"transaction", "regression"})
    public void sendOTPBlacklistedUser() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile="
                + TransactionData.USER_MOBILE_BLACKLISTED + "&email=", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_BLACKLISTED)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 400, "Check that the user used is blacklisted out or not.");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode") , "LP_USER_BLACKLISTED","Check that the user used is blacklisted out or not.");
    }

    @Description("To verify that an ineligible user should not be able to send OTP for account linking")
    @Feature(value = "Link API")
    @Test(priority = 8, groups = {"transaction", "regression"})
    public void sendOTPLockedUser() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile="
                + TransactionData.USER_MOBILE_LOCKED + "&email=", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_LOCKED)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 400, "Check that the should be marked locked");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode") , "LP_USER_LOCKED", "Check that the should be marked locked");

    }

    @Description("To verify that a user should not be able to send OTP for account linking for an invalid mobile number")
    @Feature(value = "Link API")
    @Test(priority = 9, groups = {"transaction", "regression"})
    public void sendOTPInvalidMobile() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile=123456789" + "&email=",
                TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", "123456789")
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 400, "Check that the user's mobile number passed is invalid");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode") , "LP_INVALID_MOBILE","Check that the user's mobile number passed is invalid" );

    }

    @Description("To verify that an error is thrown when mobile is not passed in send OTP request")
    @Feature(value = "Link API")
    @Test(priority = 10, groups = {"transaction", "regression"})
    public void sendOTPNoMobile() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile=" + "&email=",
                TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", "")
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 400, "Check that the user's mobile number passed is invalid");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode") , "LP_USER_DETAILS_REQUIRED","Check that the user's mobile number is not passed in the request." );

    }

    @Description("To verify that an error is thrown when email is not passed in send OTP request for a merchant which requires an email")
    @Feature(value = "Link API")
    @Test(priority = 11, groups = {"transaction", "regression"})
    public void sendOTPEmailRequired() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY_EMAIL_REQUIRED + "&mobile="
                + TransactionData.USER_MOBILE_VALID9 + "&email=", TransactionData.ACCESS_KEY_EMAIL_REQUIRED);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY_EMAIL_REQUIRED);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_VALID9)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 400, "Check that the user's mobile number passed is invalid");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode") , "LP_USER_DETAILS_REQUIRED","Check that the user's mobile number is not passed in the request." );

    }

    @Description("To verify that an eligible user can send OTP for account linking for a merchant that require user's email address")
    @Feature(value = "Link API")
    @Test(priority = 12, groups = {"transaction", "regression"})
    public void sendOTPEligibleUserEmailRequired() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY_EMAIL_REQUIRED + "&mobile=" + TransactionData.USER_MOBILE_VALID9
                + "&email=" + TransactionData.USER_EMAIL10, TransactionData.ACCESS_KEY_EMAIL_REQUIRED);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY_EMAIL_REQUIRED);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_VALID9)
                .replace("email", TransactionData.USER_EMAIL10)
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 200, "Check that the user is correctly whitelisted from DS");

    }

    @Description("To verify that an eligible user can validate OTP for account linking for a merchant that require user's email address")
    @Feature(value = "Link API")
    @Test(priority = 13, groups = {"transaction", "regression"})
    public void validateOTPEligibleUserEmailRequired() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        try{
            do{
                otp = FindOrCreateUserOTP.getLatestOTP(TransactionData.USER_MOBILE_VALID6);
                signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY_EMAIL_REQUIRED + "&mobile=" + TransactionData.USER_MOBILE_VALID9
                        + "&email=" + TransactionData.USER_EMAIL10 + "&otp=" + otp, TransactionData.ACCESS_KEY_EMAIL_REQUIRED);

                headerDetails.put("signature", signature);
                headerDetails.put("accessKey", TransactionData.ACCESS_KEY_EMAIL_REQUIRED);

                String validateOTPRequest = new StringTemplate(LazypayConstants.VALIDATE_OTP)
                        .replace("mobile", TransactionData.USER_MOBILE_VALID9)
                        .replace("email", TransactionData.USER_EMAIL10)
                        .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                        .replace("otp", otp)
                        .replace("firstName", TransactionData.FIRST_NAME)
                        .replace("lastName", TransactionData.LAST_NAME)
                        .toString();

                validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPRequest);
                retryCount++;
            }while(validateOTPPojo.status.equals("401") && validateOTPPojo.errorCode.equals("LP_INCORRECT_OTP") && retryCount <=2);
        }catch(NullPointerException e){
        }

        Assert.assertNotNull(validateOTPPojo.access_token, "Check that the user is correctly whitelisted from DS");
    }

    @Description("To verify that a user should not be able to validate OTP for account linking for an invalid mobile number")
    @Feature(value = "Link API")
    @Test(priority = 14, groups = {"transaction", "regression"})
    public void validateOTPInvalidMobile() throws Exception {


        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        otp="1234";
        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile=" + TransactionData.USER_MOBILE_VALID9
                + "&email=" + "&otp=" + otp, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String validateOTPRequest= new StringTemplate(LazypayConstants.VALIDATE_OTP)
                .replace("mobile", "123456789")
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("otp", otp)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPRequest);
        Assert.assertEquals(validateOTPPojo.status, "400", "Check that the user's mobile number passed is invalid");
        Assert.assertEquals(validateOTPPojo.errorCode , "LP_INVALID_MOBILE","Check that the user's mobile number passed is invalid" );

    }

    @Description("To verify that an error is thrown when mobile is not passed in validate OTP request")
    @Feature(value = "Link API")
    @Test(priority = 15, groups = {"transaction", "regression"})
    public void validateOTPNoMobile() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        otp="1234";
        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&mobile="
                + "&email=" + "&otp=" + otp, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String validateOTPRequest= new StringTemplate(LazypayConstants.VALIDATE_OTP)
                .replace("mobile", "")
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("otp", otp)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPRequest);
        Assert.assertEquals(validateOTPPojo.status, "400", "Check that the user's mobile number passed is invalid");
        Assert.assertEquals(validateOTPPojo.errorCode , "LP_INVALID_MOBILE","Check that the user's mobile number is not passed in the request");

    }


    @Description("To verify that a user should not be able to send OTP for account linking if an invalid signature is passed in the request")
    @Feature(value = "Link API")
    @Test(priority = 16, groups = {"transaction", "regression"})
    public void sendOTPInvalidSignature() {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = "IncorrectSignature";

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_VALID9)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 401, "Check that the an incorrect signature is passed in the request");
        Assert.assertEquals(sendOTPResponse.getBody().jsonPath().getString("errorCode"), "LP_SIGNATURE_MISMATCH", "Check that the an incorrect signature is passed in the request");

    }

    @Description("To verify that a user should not be able to validate OTP for account linking if an invalid signature is passed in the request")
    @Feature(value = "Link API")
    @Test(priority = 17, groups = {"transaction", "regression"})
    public void validateOTPInvalidSignature() {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        otp="1234";
        signature = "IncorrectSignature";

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String validateOTPRequest= new StringTemplate(LazypayConstants.VALIDATE_OTP)
                .replace("mobile", TransactionData.USER_MOBILE_VALID6)
                .replace("email", "")
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("otp", otp)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", TransactionData.LAST_NAME)
                .toString();

        validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPRequest);
        Assert.assertEquals(validateOTPPojo.status, "401", "Check that the an incorrect signature is passed in the request");
        Assert.assertEquals(validateOTPPojo.errorCode, "LP_SIGNATURE_MISMATCH", "Check that the an incorrect signature is passed in the request");

    }
}