package platform.userplatform;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.ams.validateUserPojo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FindOrCreateUserTest extends UserPlatformSupportData {

    public FindOrCreateUserTest() throws Exception {
        super();
    }

    String UUID;
    String newUserLoginFlowUUID, existingUserUUID, newUserTransactionFlowUUID;

    @SneakyThrows
    @Test(priority = 1, enabled = true, groups = {"AAA_Sanity", "AAA_Regression"}, dataProvider = "findOrCreateUserData")
    @Feature("AAA")
    @Description("Create unique user in user table in lazypayplatform db or return Existing user details along with OTP")
    public void findOrCreateUser(String findOrCreateUserTestCase, String mobileNumber, String policyId, String clientId, String mobileVerifiedDate, int otpLength, String otpMessage) {
        log.info("Started Execution with the findOrCreateUser");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("clientId", clientId);
        queryParamDetails.put("clientSecret", amsTestData.clientSecret);
        queryParamDetails.put("mobile", mobileNumber);
        queryParamDetails.put("source", SOURCE);
        queryParamDetails.put("policyId", policyId);
        if (findOrCreateUserTestCase.equals("existingUserLoginFlow")){
            deleteUserData();
        }
        switch (findOrCreateUserTestCase) {
            case "withoutMobileParam":
                queryParamDetails.remove("mobile");
                break;
            case "withoutPolicyId":
                queryParamDetails.remove("policyId");
                break;
            case "withoutSourceParam":
                queryParamDetails.remove("source");
                break;
            case "withoutParam":
                sendOTP(NEW_USER_MOBILE_NUMBER_TXN_FLOW);
                sendOTP(NEW_USER_MOBILE_NUMBER_LOGIN_FLOW);
                queryParamDetails.remove("source");
                queryParamDetails.remove("mobile");
                break;
        }
        switch (findOrCreateUserTestCase) {
            case "existingUserLoginFlow":
            case "newUserLoginFlow":
            case "newUserTransactionFlow":
            case "existingUserTransactionFlow": {
                validateUserPojo findOrCreateUserResponse = createuser.post(queryParamDetails, headerDetails, "");
                UUID = findOrCreateUserResponse.getResponseData().getProfileData().getUuid();
                if (findOrCreateUserTestCase.equals("existingUserLoginFlow"))
                    existingUserUUID = UUID;
                if (findOrCreateUserTestCase.equals("newUserLoginFlow"))
                    newUserLoginFlowUUID = UUID;
                String MOBILE_VERIFIED_DATE = String.valueOf(findOrCreateUserResponse.getResponseData().getProfileData().getMobileVerifiedDate());
                Assert.assertEquals(mobileVerifiedDate, MOBILE_VERIFIED_DATE, "Mobile validation Date not matching");
                Assert.assertEquals(findOrCreateUserResponse.getResponseData().getProfileData().getMobile(), mobileNumber);
                Assert.assertEquals(findOrCreateUserResponse.getResponseData().getOtpValue().length(), otpLength);
                Assert.assertEquals(findOrCreateUserResponse.getResponseCode(), otpMessage);
                break;
            }
            case "invalidNumberLoginFlow":
            case "invalidNumberTxnFlow": {
                validateUserPojo findOrCreateUserResponse = createuser.post(queryParamDetails, headerDetails, "");
                Assert.assertEquals(findOrCreateUserResponse.getStatus(), "400");
                Assert.assertEquals(findOrCreateUserResponse.getError(), "Bad Request");
                Assert.assertEquals(findOrCreateUserResponse.getMessage(), "Please provide valid mobile number");
                Assert.assertEquals(findOrCreateUserResponse.getPath(), "/api/lazypay/platform/um/find_or_create_user");
                break;
            }
            case "withoutMobileParam":
            case "withoutSourceParam":
            case "withoutPolicyId":
            case "withoutParam": {
                Response findOrCreateUserResponse = createuser.postWithResponse(queryParamDetails, headerDetails, "");
                Assert.assertEquals(findOrCreateUserResponse.statusCode(), 400);
                break;
            }
        }
    }

    @SneakyThrows
    @Test(priority = 2, dataProvider = "getUserInfoTestData")
    @Description("It will fetch the user details from user db ")
    public void getUserInfoTest(String getUserInfoTestData, String aioCaseKey) {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        switch (getUserInfoTestData) {
            case "withIdentifier":
                queryParamDetails.put("identifier", existingUserUUID);
                Response response = RestAssured.given().headers(headerDetails)
                        .queryParams(queryParamDetails).get(amsTestData.USER_INFO_PATH);
                log.debug("response is " + response.body().asString());
                log.debug("response is " + response.statusCode());
                Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
                Assert.assertEquals(response.statusCode(), 200, "Failed to provide valid status code");
                Assert.assertTrue(response.getBody().prettyPrint().contains(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW), "Failed to provide valid response with mobile");

                break;
            case "withoutIdentifier":
                Response response1 = RestAssured.given().headers(headerDetails)
                        .queryParams(queryParamDetails).get(amsTestData.USER_INFO_PATH);
                Assert.assertEquals(response1.statusCode(), 400);
                break;
            case "withNewUserIdentifier":
                queryParamDetails.put("identifier", newUserLoginFlowUUID);
                Response response3 = RestAssured.given().headers(headerDetails)
                        .queryParams(queryParamDetails).get(amsTestData.USER_INFO_PATH);
                log.debug("response is " + response3.body().asString());
                log.debug("response is " + response3.statusCode());
                Allure.addAttachment(getClass().getSimpleName(), response3.body().asString());
                Assert.assertEquals(response3.statusCode(), 200, "Failed to provide valid status code");
                Assert.assertTrue(response3.getBody().prettyPrint().contains(NEW_USER_MOBILE_NUMBER_LOGIN_FLOW), "Failed to provide valid response with mobile");
                break;
            default:
                throw new IllegalStateException("Unexpected user data: " + getUserInfoTestData);
        }
    }

    @SneakyThrows
    private void deleteUserData() {
        purgeUser(NEW_USER_MOBILE_NUMBER_LOGIN_FLOW);
        purgeUser(NEW_USER_MOBILE_NUMBER_TXN_FLOW);

    }
}
