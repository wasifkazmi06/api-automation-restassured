package platform.userregistration;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.ams.ProfileData;
import pojos.ams.validateUserPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserRegistrationTests extends UrsSupportData {
    public UserRegistrationTests() throws Exception {
        super();
    }


    @SneakyThrows
    @Test(priority = 1, dataProvider = "ursFindOrCreateUserData")
    @Feature("URS")
    @Description("Create unique user in user table in User Registration db or return Existing user details along with OTP details")
    public void findOrCreateUser(String findOrCreateUserTestCase, String mobileNumber, String tenantId, String policyId, String mobileVerifiedDate, int otpLength, String otpMessage) {
        log.info("Started Execution with the findOrCreateUser");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        StringTemplate payload;
        String requestBody;
        payload = new StringTemplate(FIND_OR_CREATE_PAYLOAD_FILE_PATH)
                .replace("policyId", policyId)
                .replace("tenantId", tenantId)
                .replace("mobile", mobileNumber);
        if (findOrCreateUserTestCase.equals("existingUserLoginFlow")) {
            deleteUserData();
        }
        requestBody = payload.toString();
        if (findOrCreateUserTestCase.equals("existingUserMaxRequestAttempts")) {
            for (int i = 1; i <= 10; i++) {
                createUser.postWithResponse(queryParamDetails, headerDetails, requestBody);
            }
        }
        Response response = createUser.postWithResponse(queryParamDetails, headerDetails, requestBody);
        validateUserPojo findOrCreateUserResponse = response.as(validateUserPojo.class);
        switch (findOrCreateUserTestCase) {
            case "existingUserLoginFlow":
            case "existingTestUserLoginFlow":
            case "existingUserTransactionNumericFlow":
            case "existingUserTransactionAlphaNumericFlow":
            case "newUserLoginFlow":
            case "newUserTransactionNumericFlow":
            case "newUserTransactionAlphaNumericFlow": {
                String otpId = findOrCreateUserResponse.getResponseData().getOtpId().toString();
                String otpValue = findOrCreateUserResponse.getResponseData().getOtpValue();
                mobileOtpIdDetails.put(mobileNumber, otpId);
                otpValueDetails.put(otpId, otpValue);
                UUID = findOrCreateUserResponse.getResponseData().getProfileData().getUuid();
                String MOBILE_VERIFIED_DATE = String.valueOf(findOrCreateUserResponse.getResponseData().getProfileData().getMobileVerifiedDate());
                Assert.assertEquals(String.valueOf(getMobileVerifiedDateUserDb("mobile_verified_date", UUID)), MOBILE_VERIFIED_DATE, "Mobile validation Date not matching");
                Assert.assertEquals(mobileVerifiedDate, MOBILE_VERIFIED_DATE, "Mobile validation Date not matching");
                Assert.assertEquals(getuuidFromUserDb("um_uuid", mobileNumber), UUID, "UUID Mismatch");
                Assert.assertEquals(findOrCreateUserResponse.getResponseData().getProfileData().getMobile(), mobileNumber);
                Assert.assertEquals(findOrCreateUserResponse.getResponseData().getOtpValue().length(), otpLength);
                Assert.assertEquals(findOrCreateUserResponse.getResponseCode(), otpMessage);
                if (findOrCreateUserTestCase.equals("existingUserLoginFlow"))
                    existingUserUUID = UUID;
                if (findOrCreateUserTestCase.equals("newUserLoginFlow"))
                    newUserLoginFlowUUID = UUID;
                break;
            }
            case "invalidNumberLoginFlow":
            case "invalidNumberTxnFlow": {
                Assert.assertEquals(response.getStatusCode(), 400);
                Assert.assertEquals(findOrCreateUserResponse.getEndUserMessage(), "Please provide valid mobile number");
                Assert.assertEquals(findOrCreateUserResponse.getInternalMessage(), "Please provide valid mobile number");
                Assert.assertEquals(findOrCreateUserResponse.getInternalCode(), "URS-1001");
                break;
            }
            case "withoutMobile":
            case "withoutTenantId":
            case "withoutPolicyId":{
                Assert.assertEquals(findOrCreateUserResponse.getEndUserMessage(), "Invalid field values, Please update and try again");
                Assert.assertEquals(findOrCreateUserResponse.getInternalMessage(), "Invalid field values, Please update and try again");
                Assert.assertEquals(findOrCreateUserResponse.getInternalCode(), "URS-1002");
                break;
            }
            case "withoutBody": {
                Assert.assertEquals(response.getStatusCode(), 400);
                break;
            }
            case "existingUserMaxRequestAttempts": {
                Assert.assertTrue(findOrCreateUserResponse.getEndUserMessage().contains("User can request otp again after"), "Failed to block otp Request attempts");
                Assert.assertTrue(findOrCreateUserResponse.getInternalMessage().contains("User can request otp again after"), "Failed to block otp Request attempts");
                Assert.assertEquals(findOrCreateUserResponse.getInternalCode(), "URS-1002");
                break;
            }
            default:
                throw new IllegalStateException("Unexpected data: " + findOrCreateUserTestCase);
        }
    }


    @SneakyThrows
    @Test(priority = 2, dataProvider = "ursReSendOTPUserData")
    @Feature("URS")
    @Description("Create unique user in user table in User Registration db or return Existing user details along with OTP details")
    public void reSendOTP(String findOrCreateUserTestCase, String mobileNumber, String tenantId, String mobileVerifiedDate, int otpLength, String otpMessage) {
        log.info("Started Execution with the findOrCreateUser");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        StringTemplate payload;
        String requestBody;
        if (findOrCreateUserTestCase.equals("withoutId")) {
            mobileOtpIdDetails.put("invalidOTPId", "A1234");
            requestBody = "";
        } else {
            String otpId = mobileOtpIdDetails.get(mobileNumber);
            payload = new StringTemplate(RESEND_OTP_PAYLOAD_FILE_PATH)
                    .replace("tenantId", tenantId)
                    .replace("otpId", otpId);
            requestBody = payload.toString();
        }
        Response resendOTPResponse = resendOTP.postWithResponse(queryParamDetails, headerDetails, requestBody);
        validateUserPojo findOrCreateUserResponse = resendOTPResponse.as(validateUserPojo.class);
        switch (findOrCreateUserTestCase) {
            case "existingUserLoginFlow":
            case "existingTestUserLoginFlow":
            case "existingUserTransactionNumericFlow":
            case "existingUserTransactionAlphaNumericFlow":
            case "newUserLoginFlow":
            case "newUserTransactionNumericFlow":
            case "newUserTransactionAlphaNumericFlow": {
                UUID = findOrCreateUserResponse.getResponseData().getProfileData().getUuid();
                String MOBILE_VERIFIED_DATE = String.valueOf(findOrCreateUserResponse.getResponseData().getProfileData().getMobileVerifiedDate());
                Assert.assertEquals(String.valueOf(getMobileVerifiedDateUserDb("mobile_verified_date", UUID)), MOBILE_VERIFIED_DATE, "Mobile validation Date not matching");
                Assert.assertEquals(mobileVerifiedDate, MOBILE_VERIFIED_DATE, "Mobile validation Date not matching");
                Assert.assertEquals(getuuidFromUserDb("um_uuid", mobileNumber), UUID, "UUID Mismatch");
                Assert.assertEquals(findOrCreateUserResponse.getResponseData().getProfileData().getMobile(), mobileNumber);
                Assert.assertEquals(findOrCreateUserResponse.getResponseData().getOtpValue().length(), otpLength);
                Assert.assertEquals(findOrCreateUserResponse.getResponseCode(), otpMessage);
                break;
            }
            case "invalidOTPId": {
                Assert.assertEquals(resendOTPResponse.getStatusCode(),400,"Failed to validate for invalid request");
                Assert.assertTrue(findOrCreateUserResponse.getEndUserMessage().contains("is not valid"), "Failed to validate for invalid request");
                Assert.assertTrue(findOrCreateUserResponse.getInternalMessage().contains("is not valid"), "Failed to validate for invalid request");
                Assert.assertEquals(findOrCreateUserResponse.getInternalCode(), "URS-1008");
                break;
            }
            case "withoutId": {
                Assert.assertEquals(resendOTPResponse.getStatusCode(),400,"Failed to validate for invalid request");
                Assert.assertTrue(findOrCreateUserResponse.getEndUserMessage().contains("Some error happened, Please try again"), "Failed to validate for invalid request");
                Assert.assertTrue(findOrCreateUserResponse.getInternalMessage().contains("Required request body is missing"), "Failed to validate for invalid request");
                Assert.assertEquals(findOrCreateUserResponse.getInternalCode(), "URS-1009");
                break;
            }
            default:
                throw new IllegalStateException("Unexpected data: " + findOrCreateUserTestCase);
        }
    }

    @SneakyThrows
    @Test(priority = 3, dataProvider = "ursFindUserData")
    @Feature("URS")
    @Description("Create unique user in user table in User Registration db or return Existing user details along with OTP details")
    public void findUser(String findUserTestCase, String mobileNumber, String tenantId, String MOBILE_VERIFIED_status, String mobileVerifiedDate, String uuid) {
        log.info("Started Execution with the findOrCreateUser");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        switch (findUserTestCase) {
            case "existingUserLoginFlow":
            case "existingUserTransactionNumericFlow":
            case "newUserLoginFlow":
            case "newUserTransactionNumericFlow": {
                queryParamDetails.put("identifier", mobileNumber);
                queryParamDetails.put("tenantId", tenantId);
                Response findUserResponse = findUser.getWithResponse(queryParamDetails, headerDetails);
                ProfileData findUserPojoResponse = findUserResponse.as(ProfileData.class);
                UUID = findUserPojoResponse.getUuid();
                String MOBILE_VERIFIED_DATE = String.valueOf(findUserPojoResponse.getMobileVerifiedDate());
                Assert.assertEquals(mobileVerifiedDate, MOBILE_VERIFIED_DATE, "Mobile validation Date not matching");
                Assert.assertEquals(getuuidFromUserDb("um_uuid", mobileNumber), UUID, "UUID Mismatch");
                Assert.assertEquals(findUserPojoResponse.getMobileVerified(), MOBILE_VERIFIED_status, "Mobile validation Date not matching");
                Assert.assertEquals(findUserPojoResponse.getMobile(), mobileNumber);
                break;
            }
            case "existingUserLoginFlowWithUUID":
            case "newUserLoginFlowWithUUID":{
                queryParamDetails.put("identifier",uuid);
                queryParamDetails.put("tenantId",tenantId);
                Response findUserResponse = findUser.getWithResponse(queryParamDetails, headerDetails);
                ProfileData findUserPojoResponse = findUserResponse.as(ProfileData.class);
                UUID = findUserPojoResponse.getUuid();
                String MOBILE_VERIFIED_DATE = String.valueOf(findUserPojoResponse.getMobileVerifiedDate());
                Assert.assertEquals(mobileVerifiedDate, MOBILE_VERIFIED_DATE, "Mobile validation Date not matching");
                Assert.assertEquals(findUserPojoResponse.getMobileVerified(), MOBILE_VERIFIED_status, "Mobile validation Date not matching");
                Assert.assertEquals(findUserPojoResponse.getMobile(), mobileNumber);
                break;
            }
            case "invalidNumberLoginFlow": {
                queryParamDetails.put("identifier",mobileNumber);
                queryParamDetails.put("tenantId",tenantId);
                Response findUserResponse = findUser.getWithResponse(queryParamDetails, headerDetails);
                ProfileData findUserPojoResponse = findUserResponse.as(ProfileData.class);
                Assert.assertEquals(findUserResponse.getStatusCode(), 400, "Failed to validated request for invalid number");
                Assert.assertTrue(findUserPojoResponse.getEndUserMessage().contains("The identifier is invalid"), "Failed to validate invalid number");
                Assert.assertTrue(findUserPojoResponse.getInternalMessage().contains("The identifier is invalid"), "Failed to validate invalid number");
                Assert.assertEquals(findUserPojoResponse.getInternalCode(), "URS-1009");
                break;
            }
            case "withoutMobile":
            case "withoutTenantId":
            case "withoutBody": {
                queryParamDetails.put("identifier",mobileNumber);
                queryParamDetails.put("tenantId",tenantId);
                Response findUserResponse = findUser.getWithResponse(queryParamDetails, headerDetails);
                Assert.assertEquals(findUserResponse.getStatusCode(), 400);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected data: " + findUserTestCase);
        }
    }


    @SneakyThrows
    @Test(priority = 4, dataProvider = "ursValidateUserData")
    @Feature("URS")
    @Description("Create unique user in user table in User Registration db or return Existing user details along with OTP details")
    public void validateUser(String validateUserData, String mobileNumber, String tenantId) {
        log.info("Started Execution with the findOrCreateUser");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        StringTemplate payload;
        String requestBody;
        String otpId, otpValue;
        otpId = mobileOtpIdDetails.get(mobileNumber);
        otpValue = otpValueDetails.get(otpId);
        if (validateUserData.equals("invalidOTPId")){
            otpId ="1234";
            otpValue="123456";
        } else if (validateUserData.equals("withoutId")){
            otpId ="";
            otpValue="123456";
        }
        payload = new StringTemplate(VALIDATE_USER_PAYLOAD_FILE_PATH)
                .replace("mobile", mobileNumber)
                .replace("otpValue", otpValue)
                .replace("tenantId", tenantId)
                .replace("otpId", otpId);
        requestBody = payload.toString();
        Response validateUserResponse = validateUser.postWithResponse(queryParamDetails, headerDetails, requestBody);
        validateUserPojo validateUserPojo = validateUserResponse.as(validateUserPojo.class);
        switch (validateUserData) {
            case "existingUserLoginFlow":
            case "existingTestUserLoginFlow":
            case "existingUserTransactionNumericFlow":
            case "existingUserTransactionAlphaNumericFlow":
            case "newUserLoginFlow":
            case "newUserTransactionNumericFlow":
            case "newUserTransactionAlphaNumericFlow": {
                Assert.assertEquals("Success", validateUserPojo.getStatus(), "Failed to login");
                break;
            }
            case "invalidNumberLoginFlow":
            case "invalidNumberTxnFlow": {
                Assert.assertEquals(validateUserResponse.getStatusCode(), 401);
                Assert.assertTrue(validateUserPojo.getEndUserMessage().contains("is not present in the system"), "Failed to validate invalid number");
                Assert.assertTrue(validateUserPojo.getInternalMessage().contains("is not present in the system"), "Failed to validate invalid number");
                Assert.assertEquals(validateUserPojo.getInternalCode(), "URS-1005");
                break;
            }
            case "withoutId":{
                Assert.assertEquals(validateUserResponse.getStatusCode(), 400);
                Assert.assertEquals(validateUserPojo.getEndUserMessage(), "Invalid field values, Please update and try again");
                Assert.assertEquals(validateUserPojo.getInternalMessage(), "Invalid field values, Please update and try again");
                Assert.assertEquals(validateUserPojo.getInternalCode(), "URS-1002");
                break;
            }
            case "invalidOTPId": {
                Assert.assertEquals(validateUserResponse.getStatusCode(), 401);
                Assert.assertTrue(validateUserPojo.getEndUserMessage().contains("Bad credentials for user"), "Failed to validate invalid otp");
                Assert.assertTrue(validateUserPojo.getInternalMessage().contains("Bad credentials for user"), "Failed to validate invalid otp");
                Assert.assertEquals(validateUserPojo.getInternalCode(), "URS-1004");
                deleteUserData();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected data: " + validateUserData);
        }
    }
}