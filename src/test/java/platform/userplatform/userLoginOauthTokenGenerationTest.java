package platform.userplatform;

import api.ams.*;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.PlatformSupportData;
import pojos.ams.InvalidateTokenPojo;
import pojos.ams.RefreshTokenResponse;
import pojos.ams.ValidateTokenPojo;
import pojos.ams.PlatformLoginResponsePojo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class userLoginOauthTokenGenerationTest extends UserPlatformSupportData {
    public userLoginOauthTokenGenerationTest() throws Exception {
        super();
    }

    userLogin userlogin = new userLogin();
    ValidateToken validateToken = new ValidateToken();
    InvalidateToken invalidateToken = new InvalidateToken();
    TokenValidate tokenValidate = new TokenValidate();
    loginRefresh loginrefresh = new loginRefresh();

    String userUuid ;
    String existingUserUUID;
    String oauthToken = "";
    String existingUserValidToken;
    String existingUserRefreshedAuthToken;
    amsTestData amstestdata = new amsTestData();


    @SneakyThrows
    @Test(priority = 1, groups = {"AAA_Sanity", "AAA_Regression"}, dataProvider = "userLoginTokenGenData")
    @Description("It will login the user and generate oauthToken and refreshToken. API: lazypay/platform/login")
    public void userLoginTokenGen(String userLoginTokenGenData, String aioKey) {
        long id = Thread.currentThread().getId();
        log.info("Test Execution Started for userLoginTokenGen. Thread id is: " + id);
        if (userLoginTokenGenData.equals("existingUserLoginFlow")) {
            purgeUser(NEW_USER_MOBILE_NUMBER_LOGIN_FLOW);
        }
        String request = null;
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        if (!userLoginTokenGenData.equals("withoutHeaders")) {
            headerDetails.put("Content-Type", "application/json");
            headerDetails.put("Accept", "application/json");
            headerDetails.put("source", "APP_BACKEND");
        }
        switch (userLoginTokenGenData) {
            case "existingUserLoginFlow":
                request = loginRequestPayload(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, amsTestData.clientSecret, true);
                break;
            case "newUserLoginFlow":
                sendOTP(NEW_USER_MOBILE_NUMBER_LOGIN_FLOW);
                request = loginRequestPayload(NEW_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, amsTestData.clientSecret, true);
                break;
            case "withoutHeaders":
                request = loginRequestPayload("6100000169", amsTestData.clientId, amsTestData.clientSecret, true);
                break;
            case "withoutClientID":
                request = loginRequestPayload(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, null, amsTestData.clientSecret, true);
                break;
            case "withoutClientSecret":
                request = loginRequestPayload(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, null, true);
                break;
            case "invalidNumber":
                request = loginRequestPayload("2300000020", amsTestData.clientId, amsTestData.clientSecret, false);
                break;
            case "validNumberWithInvalidOTP":
                request = loginRequestPayload(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, amsTestData.clientId, amsTestData.clientSecret, false);
                break;
            case "validNumberWithExpiredOTP":
                //TODO:Rakesh need to add the functions once the P2P2 is live in sbox
                request = loginRequestPayload("EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW", amsTestData.clientId, "", true);
                break;
            case "testNumberWithStaticOTP":
                //TODO:Rakesh need to add the functions once the P2P2 is live in sbox
                request = loginRequestPayload("8888888888", amsTestData.clientId, "", true);
                break;
            case "withoutPayload":
                request = "";
        }

        if (userLoginTokenGenData.equals("existingUserLoginFlow") || (userLoginTokenGenData.equals("newUserLoginFlow"))) {
            PlatformLoginResponsePojo loginResponse = userlogin.post(queryParamDetails, headerDetails, request);
            userUuid = loginResponse.getUmUuid();
            if (userLoginTokenGenData.equals("existingUserLoginFlow")) {
                existingUserValidToken = loginResponse.getOauthTokenResponse().getAccess_token();
                existingUserUUID = loginResponse.getUmUuid();
            }
            oauthToken = loginResponse.getOauthTokenResponse().getAccess_token();
            String refreshToken = loginResponse.getOauthTokenResponse().getRefresh_token();
            String status = loginResponse.getStatus().toString();
            String client_id = amsTestData.clientId;
            Assert.assertEquals(status, "true", "Login not successful");
            Assert.assertEquals(getOauthToken("token_value", userUuid), oauthToken, "oauth token value from db doesn't match with api response");
            Assert.assertEquals(getrefreshToken("refresh_token", userUuid), refreshToken, "refresh token value from db doesn't match with api response");

        } else if (userLoginTokenGenData.equals("withoutHeaders")) {
            Response loginResponse = userlogin.postWithResponse(queryParamDetails, headerDetails, "");
            Assert.assertEquals(loginResponse.statusCode(), 400);
        } else if (userLoginTokenGenData.equals("withoutClientID")) {
            PlatformLoginResponsePojo loginResponse = userlogin.post(queryParamDetails, headerDetails, request);
            Assert.assertEquals(loginResponse.getStatus(), "400", "Failed to throw bad request");
            Assert.assertEquals(loginResponse.getError(), "Bad Request", "Failed to throw bad request");
            Assert.assertEquals(loginResponse.getMessage(), "Client Id not present", "Failed to throw bad request");
        } else if (userLoginTokenGenData.equals("withoutClientSecret")) {
            Response loginResponse = userlogin.postWithResponse(queryParamDetails, headerDetails, "");
            Assert.assertEquals(loginResponse.statusCode(), 400);
        } else if (userLoginTokenGenData.equals("invalidNumber")) {
            PlatformLoginResponsePojo loginResponse = userlogin.post(queryParamDetails, headerDetails, request);
            Assert.assertEquals(loginResponse.getStatus(), "404", "Failed to throw resource not found error");
            Assert.assertEquals(loginResponse.getError(), "Not Found", "Failed to throw resource not found error");
            Assert.assertEquals(loginResponse.getMessage(), "User was not found in our system to verify this otp", "Failed to throw resource not found error");
        } else if (userLoginTokenGenData.equals("validNumberWithInvalidOTP")) {
            PlatformLoginResponsePojo loginResponse = userlogin.post(queryParamDetails, headerDetails, request);
            Assert.assertEquals(loginResponse.getStatus(), "401", "Failed to Invalidate m-otp");
            Assert.assertEquals(loginResponse.getError(), "Unauthorized", "Failed to Invalidate m-otp");
            Assert.assertTrue(loginResponse.getMessage().contains("Bad credentials for user 6100000170. Remaining Attempts are:"), "Failed to check for Invalid OTP");
        } else if (userLoginTokenGenData.equals("withoutPayload")) {
            Response loginResponse = userlogin.postWithResponse(queryParamDetails, headerDetails, "");
            Assert.assertEquals(loginResponse.statusCode(), 400);
        }
    }


    @SneakyThrows
    @Test(priority = 2, groups = {"AAA_Sanity", "AAA_Regression"}, dataProvider = "validateTokenData")
    @Description("Cases to validate oauthToken generated")
    public void validateToken(String validateTokenData, String aioKey) {
        log.info("Test Execution Started for userLoginTokenGen");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        switch (validateTokenData) {
            case "validToken":
                queryParamDetails.put("authToken", existingUserValidToken);
                queryParamDetails.put("scope", "wallet_profile");
                break;
            case "invalidToken":
                queryParamDetails.put("authToken", existingUserValidToken + "test");
                queryParamDetails.put("scope", "wallet_profile");
                break;
            case "withoutScopeParam":
                queryParamDetails.put("authToken", existingUserValidToken);
                break;
            case "withoutParam":
                break;
        }
        if (validateTokenData.equals("validToken")) {
            ValidateTokenPojo validateTokenResponse = validateToken.get(queryParamDetails, headerDetails);
            Assert.assertEquals(validateTokenResponse.getMobile(), EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, "Failed to provide valid details");
            Assert.assertEquals(validateTokenResponse.getUuid(), existingUserUUID, "Failed to provide valid details for the valid token");
            Assert.assertEquals(validateTokenResponse.getClientId(), "n5uxkxcm1q-signin-new", "Failed to provide valid details for the valid token");
            Assert.assertEquals(validateTokenResponse.getImplicitType(), "false", "Failed to provide valid details for the valid token");
            Assert.assertEquals(validateTokenResponse.getScopes(), "wallet_profile", "Failed to provide valid details for the valid token");
        } else if (validateTokenData.equals("invalidToken")) {
            ValidateTokenPojo validateTokenResponse = validateToken.get(queryParamDetails, headerDetails);
            Assert.assertEquals(validateTokenResponse.getStatus(), "401", "Failed to validate the invalid token");
            Assert.assertEquals(validateTokenResponse.getError(), "Unauthorized", "Failed to validate the invalid token");
            Assert.assertEquals(validateTokenResponse.getMessage(), "This is not a valid token", "Failed to validate the invalid token");
        } else if (validateTokenData.equals("withoutScopeParam") || validateTokenData.equals("withoutParam")) {
            Response validateTokenResponse = validateToken.getWithResponse(queryParamDetails, headerDetails);
            Assert.assertEquals(validateTokenResponse.getStatusCode(), 400, "Failed to check for bad request");
        }
    }

    @SneakyThrows
    @Test(priority = 3, groups = {"AAA_Sanity", "AAA_Regression"}, dataProvider = "validateTokenData")
    @Description("Cases to validate oauthToken generated umToken/validate")
    public void tokenValidate(String validateTokenData, String aioKey) {
        log.info("Test Execution Started for umToken/validate tokenValidate");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        switch (validateTokenData) {
            case "validToken":
                headerDetails.put("Accept", "application/json");
                headerDetails.put("OwnerAuthorization", "Bearer " + existingUserValidToken);
                headerDetails.put("OwnerScope", "wallet_profile");
                break;
            case "invalidToken":
                headerDetails.put("Accept", "application/json");
                headerDetails.put("OwnerAuthorization", "Bearer " + existingUserValidToken + "test");
                headerDetails.put("OwnerScope", "wallet_profile");
                break;
            case "withoutScopeParam":
                headerDetails.put("Accept", "application/json");
                headerDetails.put("OwnerAuthorization", "Bearer " + existingUserValidToken);
                break;
            case "withoutParam":
                break;
        }
        if (validateTokenData.equals("validToken")) {
            ValidateTokenPojo tokenValidateResponse = tokenValidate.get(queryParamDetails, headerDetails);
            Assert.assertEquals(tokenValidateResponse.getMobile(), EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, "Failed to provide valid details");
            Assert.assertEquals(tokenValidateResponse.getUuid(), existingUserUUID, "Failed to provide valid details for the valid token");
            Assert.assertEquals(tokenValidateResponse.getClientId(), "n5uxkxcm1q-signin-new", "Failed to provide valid details for the valid token");
            Assert.assertEquals(tokenValidateResponse.getImplicitType(), "false", "Failed to provide valid details for the valid token");
            Assert.assertEquals(tokenValidateResponse.getScopes(), "wallet_profile", "Failed to provide valid details for the valid token");
        } else if (validateTokenData.equals("invalidToken")) {
            ValidateTokenPojo tokenValidateResponse = tokenValidate.get(queryParamDetails, headerDetails);
            Assert.assertEquals(tokenValidateResponse.getStatus(), "401", "Failed to validate the invalid token");
            Assert.assertEquals(tokenValidateResponse.getError(), "Unauthorized", "Failed to validate the invalid token");
            Assert.assertEquals(tokenValidateResponse.getMessage(), "This is not a valid token", "Failed to validate the invalid token");
        } else if (validateTokenData.equals("withoutScopeParam") || validateTokenData.equals("withoutParam")) {
            Response tokenValidateResponse = tokenValidate.getWithResponse(queryParamDetails, headerDetails);
            Assert.assertEquals(tokenValidateResponse.getStatusCode(), 400, "Failed to check for bad request" + tokenValidateResponse.getBody());
        }
    }


    @SneakyThrows
    @Test(priority = 4, groups = {"AAA_Sanity", "AAA_Regression"}, dataProvider = "refreshTokenData")
    @Description("This test will validate oauthToken getting refreshed in DB when the refresh api getting called.")
    public void refreshLoginTest(String refreshTokenData, String aioKey) {
        log.info("Test Execution started for refreshLoginTest");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String payload = null;
        JsonObject jsonobject = new JsonObject();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        headerDetails.put("source", "APP_BACKEND");
        switch (refreshTokenData) {
            case "validToken":
                jsonobject.addProperty("clientId", amsTestData.clientId);
                jsonobject.addProperty("clientSecret", amsTestData.clientSecret);
                jsonobject.addProperty("authToken", existingUserValidToken);
                payload = jsonobject.toString();
                break;
            case "invalidToken":
                jsonobject.addProperty("clientId", amsTestData.clientId);
                jsonobject.addProperty("clientSecret", amsTestData.clientSecret);
                jsonobject.addProperty("authToken", existingUserValidToken + "test");
                payload = jsonobject.toString();
                break;
            case "withoutHeaders":
                headerDetails.remove("Content-Type", "application/json");
                headerDetails.remove("Accept", "application/json");
                headerDetails.remove("source", "APP_BACKEND");
                jsonobject.addProperty("clientId", amsTestData.clientId);
                jsonobject.addProperty("clientSecret", amsTestData.clientSecret);
                jsonobject.addProperty("authToken", existingUserValidToken);
                payload = jsonobject.toString();
                break;
            case "invalidClientId":
                jsonobject.addProperty("clientId", "testInvalidClientId");
                jsonobject.addProperty("clientSecret", amsTestData.clientSecret);
                jsonobject.addProperty("authToken", existingUserValidToken);
                payload = jsonobject.toString();
                break;
            case "withoutClientId":
                jsonobject.addProperty("clientSecret", amsTestData.clientSecret);
                jsonobject.addProperty("authToken", existingUserValidToken);
                payload = jsonobject.toString();
                break;
            case "invalidClientSecret":
                jsonobject.addProperty("clientId", amsTestData.clientId);
                jsonobject.addProperty("clientSecret", "testInvalidClientSecret");
                jsonobject.addProperty("authToken", existingUserValidToken);
                payload = jsonobject.toString();
                break;
            case "withoutClientSecret":
                jsonobject.addProperty("clientId", amsTestData.clientId);
                jsonobject.addProperty("authToken", existingUserValidToken);
                payload = jsonobject.toString();
                break;
            case "withoutToken":
                jsonobject.addProperty("clientId", amsTestData.clientId);
                jsonobject.addProperty("clientSecret", amsTestData.clientSecret);
                payload = jsonobject.toString();
                break;
        }

        if (refreshTokenData.equals("validToken")) {
            RefreshTokenResponse refreshResponse = loginrefresh.post(queryParamDetails, headerDetails, payload);
            String mobile = refreshResponse.getMobile();
            existingUserRefreshedAuthToken = refreshResponse.getAuthToken();
            Assert.assertEquals(mobile, EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, "Mobile number not matching");
        } else if (refreshTokenData.equals("invalidToken") || (refreshTokenData.equals("withoutToken"))) {
            RefreshTokenResponse refreshResponse = loginrefresh.post(queryParamDetails, headerDetails, payload);
            Assert.assertEquals(refreshResponse.getStatus(), "401", "Failed to validate the invalid token");
            Assert.assertEquals(refreshResponse.getError(), "Unauthorized", "Failed to validate the invalid token");
            Assert.assertEquals(refreshResponse.getMessage(), "Refresh token not present", "Failed to validate the invalid token");
        } else if (refreshTokenData.equals("invalidClientId") || refreshTokenData.equals("invalidClientSecret")) {
            RefreshTokenResponse refreshResponse = loginrefresh.post(queryParamDetails, headerDetails, payload);
            String mobile = refreshResponse.getMobile();
            existingUserRefreshedAuthToken = refreshResponse.getAuthToken();
            Assert.assertEquals(mobile, EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW, "Mobile number not matching");
        } else if (refreshTokenData.equals("withoutClientId") || refreshTokenData.equals("withoutClientSecret")) {
            RefreshTokenResponse refreshResponse = loginrefresh.post(queryParamDetails, headerDetails, payload);
            Assert.assertEquals(refreshResponse.getStatus(), "400", "Failed to validate the invalid token");
            Assert.assertEquals(refreshResponse.getError(), "Bad Request", "Failed to validate the invalid token");
            Assert.assertEquals(refreshResponse.getMessage(), "Client Id not present", "Failed to validate the invalid token");
        } else if (refreshTokenData.equals("withoutHeaders")) {
            Response tokenValidateResponse = loginrefresh.postWithResponse(queryParamDetails, headerDetails, payload);
            Assert.assertEquals(tokenValidateResponse.getStatusCode(), 400, "Failed to check for bad request" + tokenValidateResponse.getBody());
        }
    }


    @SneakyThrows
    @Test(priority = 5, groups = {"AAA_Sanity", "AAA_Regression"}, dataProvider = "invalidateTokenData")
    @Description("This test will validate oauthToken getting refreshed in DB when the refresh api getting called.")
    public void invalidateToken(String invalidateTokenData, String aioKey){
        log.info("Test Execution started for refreshLoginTest");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        switch (invalidateTokenData){
            case "validToken":
                queryParamDetails.put("authorization",existingUserRefreshedAuthToken);
                break;
            case "invalidToken":
                queryParamDetails.put("authorization",existingUserRefreshedAuthToken+"a");
                break;
            case "withoutParam":
                break;
            case "expiredToken":
                queryParamDetails.put("authorization",existingUserValidToken);
                break;
        }
        if (invalidateTokenData.equals("validToken")){
            Response invalidateTokenResponse = invalidateToken.deleteWithResponse(queryParamDetails, headerDetails);
            Assert.assertEquals(invalidateTokenResponse.getStatusCode(), 200, "Failed to invalidate the valid token");
         } else if (invalidateTokenData.equals("invalidToken") || invalidateTokenData.equals("expiredToken")) {
            InvalidateTokenPojo invalidateTokenResponse = invalidateToken.delete(queryParamDetails, headerDetails);
            Assert.assertEquals(invalidateTokenResponse.getStatus(), "401", "Failed to invalidate the invalid token");
            Assert.assertEquals(invalidateTokenResponse.getError(), "Unauthorized", "Failed to invalidate the invalid token");
            Assert.assertEquals(invalidateTokenResponse.getMessage(), "This is not a valid token", "Failed to invalidate the invalid token");
        } else if (invalidateTokenData.equals("withoutParam")) {
            Response invalidateTokenResponse = invalidateToken.deleteWithResponse(queryParamDetails, headerDetails);
            Assert.assertEquals(invalidateTokenResponse.getStatusCode(), 400, "Failed to validate the bad request");
        }
    }

    @SneakyThrows
    @Test(priority = 6)
    @Description("This cron will remove the user entry from oauth_token table if token expiry date is lesser than current date")
    public void cleaningExpiryTest() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        log.info("updateExpiryTokenDate user id is: " + userUuid);
        updateExpiryTokenDate(userUuid);
        Response response = RestAssured.given()
                .headers(headerDetails)
                .get(amsTestData.CRON_PATH);
        response.then().statusCode(200);
        Assert.assertEquals(getOauthToken("token_value", userUuid), "", "oauth token deleted from oauthToken db");

    }

}
