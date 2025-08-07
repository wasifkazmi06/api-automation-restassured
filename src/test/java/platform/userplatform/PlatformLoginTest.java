package platform.userplatform;

import api.ams.V1UserLogin;
import api.ams.userLogin;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.PlatformSupportData;
import pojos.ams.PlatformLoginResponsePojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlatformLoginTest extends UserPlatformSupportData {
    public PlatformLoginTest() throws Exception {
        super();
    }
    userLogin userLogin = new userLogin();
    V1UserLogin v1UserLogin = new V1UserLogin();

    @SneakyThrows
    @Test(priority = 1)
    @Description("Platform login with all valid inputs")
    @Step
    public String userLoginWithOtp() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        headerDetails.put("source", "APP_BACKEND");
        String payload = new StringTemplate(amsTestData.PLATFORM_LOGIN_PAYLOAD_FILE_PATH)
                .replace("clientId", amsTestData.clientId)
                .replace("clientSecret", amsTestData.clientSecret)
                .replace("mobile", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW)
                .replace("otp", fetchOTP(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW)).toString();
        PlatformLoginResponsePojo loginResponse = userLogin.post(queryParamDetails, headerDetails, payload);
        String uuid = loginResponse.getUmUuid();
        String oauthToken = loginResponse.getOauthTokenResponse().getAccess_token();
        String refreshToken = loginResponse.getOauthTokenResponse().getRefresh_token();
        String status = loginResponse.getStatus().toString();
        Assert.assertEquals(status, "true", "Login successful");
        Assert.assertEquals(getOauthToken("token_value", uuid), oauthToken, "oauth token value from db match with api response");
        Assert.assertEquals(getrefreshToken("refresh_token", uuid), refreshToken, "refresh token value from db match with api response");
        return getOauthToken("token_value", uuid);
    }

    @SneakyThrows
    //@Test(priority = 1)
    @Description("Platform login with all valid inputs")
    @Step
    public String v1userLoginWithOtp() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        headerDetails.put("source", "APP_BACKEND");
        String payload = new StringTemplate(amsTestData.PLATFORM_LOGIN_PAYLOAD_FILE_PATH)
                .replace("clientId", amsTestData.clientId)
                .replace("clientSecret", amsTestData.clientSecret)
                .replace("mobile", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW)
                .replace("otp", fetchOTP(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW)).toString();
        PlatformLoginResponsePojo loginResponse = v1UserLogin.post(queryParamDetails, headerDetails, payload);
        String uuid = loginResponse.getUmUuid();
        String oauthToken = loginResponse.getOauthTokenResponse().getAccess_token();
        String refreshToken = loginResponse.getOauthTokenResponse().getRefresh_token();
        String status = loginResponse.getStatus().toString();
        Assert.assertEquals(status, "true", "Login successful");
        Assert.assertEquals(getOauthToken("token_value", uuid), oauthToken, "oauth token value from db match with api response");
        Assert.assertEquals(getrefreshToken("refresh_token", uuid), refreshToken, "refresh token value from db match with api response");
        return getOauthToken("token_value", uuid);
    }



}
