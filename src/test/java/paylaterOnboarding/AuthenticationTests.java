package paylaterOnboarding;

import api.paylaterOnboarding.authentication.GetUserStatus;
import api.paylaterOnboarding.authentication.InitiateOTP;
import api.paylaterOnboarding.authentication.ValidateOTP;
import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.paylaterOnboarding.PLInitiateOTPPojo;
import pojos.paylaterOnboarding.PLValidateOTPPojo;
import util.FindOrCreateUserOTP;
import util.StringTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
public class AuthenticationTests {
    public static String signature;
    public static String data;
    public static FindOrCreateUserOTP findOrCreateUserOTP;

    InitiateOTP initiateOTP = new InitiateOTP();
    ValidateOTP validateOTP = new ValidateOTP();
    GetUserStatus getUserStatus = new GetUserStatus();


    public AuthenticationTests() throws Exception {
    }

    @Description("To verify the initiate OTP API with valid testdata")
    @Test(priority = 1)
    public void initiateOTPTest() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerParams = new LinkedHashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        // Generate signature with merchantAccessKey, mobile & email - Signature header params
        headerParams.put("merchantAccessKey", BaseTestData.MERCHANT_ACCESS_KEY);
        headerParams.put("mobile", BaseTestData.USER_MOBILE);
        headerParams.put("email", BaseTestData.EMAIL);
        // Fetching the signature
        signature = GenerateSignature.generateSignature(headerParams, BaseTestData.MERCHANT_ACCESS_KEY);
        System.out.println("Signature is " + signature);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", BaseTestData.MERCHANT_ACCESS_KEY);
        log.info("API headers " + headerDetails);
        String initiateOtpRequest = new StringTemplate(PayLaterConstants.INITIATE_OTP_REQUEST)
                .replace("mobile", BaseTestData.USER_MOBILE)
                .replace("email", BaseTestData.EMAIL).toString();
        PLInitiateOTPPojo initiateOTPPojo = initiateOTP.post(queryParamDetails, headerDetails, initiateOtpRequest);
        Assert.assertEquals(initiateOTPPojo.status, true, "check the error response/check if signature is generated with valid data or not");
        Assert.assertEquals(initiateOTPPojo.otpType, "m-otp", "check the error response/check if signature is generated with valid data or not");

    }

    @Description("To verify the initiate OTP API with valid testdata")
    @Test(priority = 2)

    public void validateOTPTest() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();

        // fetching OTP
        findOrCreateUserOTP = new FindOrCreateUserOTP();
        String otp = FindOrCreateUserOTP.findOrCreateOTP(BaseTestData.USER_MOBILE);
        log.info("returing OTP " + otp);

        // Generate signature with merchantAccessKey, mobile, email & otp - Signature header params
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> headerParams = new LinkedHashMap<>();
        headerParams.put("merchantAccessKey", BaseTestData.MERCHANT_ACCESS_KEY);
        headerParams.put("mobile", BaseTestData.USER_MOBILE);
        headerParams.put("email", BaseTestData.EMAIL);
        headerParams.put("otp", otp);

        // Fetching the signature
        signature = GenerateSignature.generateSignature(headerParams, BaseTestData.MERCHANT_ACCESS_KEY);
        System.out.println("Signature is " + signature);

        // validate OTP API
        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", BaseTestData.MERCHANT_ACCESS_KEY);
        String validateOTPReq = new StringTemplate(PayLaterConstants.VALIDATE_OTP)
                .replace("mobile", BaseTestData.USER_MOBILE)
                .replace("email", BaseTestData.EMAIL)
                .replace("otp", otp).toString();
        PLValidateOTPPojo validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPReq);
        Assert.assertEquals(validateOTPPojo.mobile, BaseTestData.USER_MOBILE, "Check error response");
        Assert.assertNotNull(validateOTPPojo.accessToken, "check error response");

    }

    @Description("To verify the GET status of user with valid data")
    @Test(priority = 3)

    public void GetUserSatusTest() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();

        // Generate signature with merchantAccessKey, mobile - Signature header params
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> headerParams = new LinkedHashMap<>();
        headerParams.put("merchantAccessKey", BaseTestData.MERCHANT_ACCESS_KEY);
        headerParams.put("mobile", BaseTestData.USER_MOBILE);

        // Fetching the signature
        signature = GenerateSignature.generateSignature(headerParams, BaseTestData.MERCHANT_ACCESS_KEY);
        System.out.println("Signature is " + signature);

        // GET user-status API headers
        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", BaseTestData.MERCHANT_ACCESS_KEY);
        // query params
        queryParamDetails.put("mobile", BaseTestData.USER_MOBILE);
        Response getUserStatusRes = getUserStatus.postReturnResponse(queryParamDetails, headerDetails);
        JsonPath jsonPathEvaluator = getUserStatusRes.jsonPath();
        String status = jsonPathEvaluator.get("status");
        log.info("user response "+ status);
        Assert.assertNotNull(status, "check for error code");


    }

}
