package Xpress;

import io.restassured.response.Response;
import org.testng.Assert;
import pojos.platform.getUserData.UserData;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

public class PlatformService extends XpressData {

    public PlatformService() throws Exception {
    }

    public static void generateUserId(String mobile) {
        String platformV1OtpRequest = null;
        try {
            platformV1OtpRequest = new StringTemplate(OTP_V1_PLATFORM_REGISTRATION_REQUEST).replace("mobile", mobile).toString();
            Response platformV1Otp = v1PlatformOTP.postWithResponse(xpressGenericHeaders(), platformV1OtpRequest);
            Assert.assertEquals(platformV1Otp.getStatusCode(), 200, "Getting Invalid status code for platform v1 otp api.");
        } catch (Exception e) {
            Assert.assertTrue(false, "Getting exception while sending platform otp " + e.getMessage());
        }
    }

    public static String fetchUserId(String mobile) {
        String userId = "";
        try {
            Response userStatusResponse = getUserData.getWithResponse(returnQueryParamsForUserDataApi(mobile), xpressGenericHeaders());
            UserData userData = userStatusResponse.as(UserData.class);
            if (userStatusResponse.getStatusCode() == 400 && userData.getMessage().equalsIgnoreCase(USER_NOT_EXISTS_MSG)) {
                generateUserId(mobile);
                UserData platformData = getUserData.get(returnQueryParamsForUserDataApi(mobile), xpressGenericHeaders());
                userId = platformData.getId();
                return userId;
            } else if (userStatusResponse.getStatusCode() != 400 && userStatusResponse.getStatusCode() != 200) {
                Assert.assertEquals(userStatusResponse.getStatusCode(), 200, "Incorrect response from fetch user data platform api");
            } else {
                userId = userData.getId();
            }
        } catch (Exception e) {
            Assert.assertTrue(false, "Getting exception while fetching user id from platform api " + e.getMessage());
        }
        return userId;
    }

    public static String returnUmuuid(String mobile) {
        return returnUserData(mobile).getUmUuid();
    }

    public static UserData returnUserData(String mobile) {
        Response userStatusResponse = getUserData.getWithResponse(returnQueryParamsForUserDataApi(mobile), xpressGenericHeaders());
        Assert.assertEquals(userStatusResponse.getStatusCode(), 200, "Incorrect response from fetch user data platform api");
        UserData userData = userStatusResponse.as(UserData.class);
        return userData;
    }

    public static Map<String, Object> returnQueryParamsForUserDataApi(String mobile) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("mobile", mobile);
        return queryParam;
    }
}
