package platform.userplatform;

import api.ams.PlatformOTP;
import api.ams.V1PlatformOTP;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.ams.PlatformOtpPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SendOtpTests extends UserPlatformSupportData {

    public SendOtpTests() throws Exception {
        super();
    }

    PlatformOTP sendOTP = new PlatformOTP();
    V1PlatformOTP v1PlatformOTP = new V1PlatformOTP();

    @SneakyThrows
    @Test(priority = 1,dataProvider = "sendOTPTestData")
    @Description("It will create unique user in user table in lazypayplatform db")
    public void otpTests(String sendOtpTestData,String mobile, String clientId, String clientSecret, String statusCode) {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        JsonObject jsonobject = new JsonObject();
        String payload = null;
        jsonobject.addProperty("clientId", clientId);
        jsonobject.addProperty("clientSecret", clientSecret);
        jsonobject.addProperty("mobile", mobile);
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("webLogin", "true");
        Response generateResponse = sendOTP.postWithResponse(queryParamDetails, headerDetails, jsonobject.toString());
        PlatformOtpPojo generateResponsePojo = generateResponse.as(PlatformOtpPojo.class);
        Assert.assertEquals(String.valueOf(generateResponse.getStatusCode()),statusCode,"Failed to match the status code");
        if (sendOtpTestData.equals("inValidNumber")){
            Assert.assertEquals(generateResponsePojo.getStatus(), "401", "status doesn't match");
            Assert.assertEquals(generateResponsePojo.getError(), "Unauthorized", "Error doesn't match");
            Assert.assertEquals(generateResponsePojo.getMessage(), "Please provide valid mobile number","Message doesn't match");
            Assert.assertEquals(generateResponsePojo.getMessage(), "Please provide valid mobile number","Message doesn't match");
        } else {
            String status = generateResponsePojo.getStatus().toString();
            Assert.assertEquals(generateResponsePojo.getOtpType(), "m-otp", "otp type doesn't match");
            Assert.assertEquals(status, "true", "Status value doesn't match");
        }
    }

    @SneakyThrows
   // @Test(priority = 1,dataProvider = "sendOTPTestData")
    @Description("It will create unique user in user table in lazypayplatform db")
    public void v1otpTests(String sendOtpTestData,String mobile, String clientId, String clientSecret, String statusCode) {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        JsonObject jsonobject = new JsonObject();
        String payload = null;
        jsonobject.addProperty("clientId", clientId);
        jsonobject.addProperty("clientSecret", clientSecret);
        jsonobject.addProperty("mobile", mobile);
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("webLogin", "true");
        Response generateResponse = v1PlatformOTP.postWithResponse(queryParamDetails, headerDetails, jsonobject.toString());
        PlatformOtpPojo generateResponsePojo = generateResponse.as(PlatformOtpPojo.class);
        Assert.assertEquals(String.valueOf(generateResponse.getStatusCode()),statusCode,"Failed to match the status code");
        if (sendOtpTestData.equals("inValidNumber")){
            Assert.assertEquals(generateResponsePojo.getStatus(), "401", "status doesn't match");
            Assert.assertEquals(generateResponsePojo.getError(), "Unauthorized", "Error doesn't match");
            Assert.assertEquals(generateResponsePojo.getMessage(), "Please provide valid mobile number","Message doesn't match");
            Assert.assertEquals(generateResponsePojo.getMessage(), "Please provide valid mobile number","Message doesn't match");
        } else {
            String status = generateResponsePojo.getStatus().toString();
            Assert.assertEquals(generateResponsePojo.getOtpType(), "m-otp", "otp type doesn't match");
            Assert.assertEquals(status, "true", "Status value doesn't match");
        }
    }
}
