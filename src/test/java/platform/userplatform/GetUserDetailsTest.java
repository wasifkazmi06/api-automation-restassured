package platform.userplatform;

import api.ams.GetUserDetails;
import io.qameta.allure.Description;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import platform.PlatformSupportData;
import pojos.ams.PlatformLoginResponsePojo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GetUserDetailsTest extends UserPlatformSupportData {
    public GetUserDetailsTest() throws Exception {
        super();
    }

    GetUserDetails getUserDetails = new GetUserDetails();


    @SneakyThrows
    @Test(priority = 1,dataProvider = "getUserDetailsTestData")
    @Description("It will fetch user details from the mongo db")
    public void getUserDetails(String getUserDetailsTestData, String aioCaseKey){
        log.info("Started Execution with the getUserDetails");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        switch (getUserDetailsTestData){
            case "validNumber":
                headerDetails.put("Accept", "application/json");
                queryParamDetails.put("mobile",EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW);
                break;
            case "validumuuid":
                headerDetails.put("Accept", "application/json");
                queryParamDetails.put("umUuid","8352860290216457903");
                break;
            case "validlpuuid":
                headerDetails.put("Accept", "application/json");
                queryParamDetails.put("lpUuid","12603344");
                break;
            case "validToken":
                PlatformLoginTest platformLoginTest = new PlatformLoginTest();
                headerDetails.put("Accept", "application/json");
                queryParamDetails.put("token",platformLoginTest.userLoginWithOtp());
                break;
            case "invalidToken":
                headerDetails.put("Accept", "application/json");
                queryParamDetails.put("token","6b13fc3c-63ed-472c-80e0-79b4e14e010c");
                break;
            case "inValidNumber":
                headerDetails.put("Accept", "application/json");
                queryParamDetails.put("mobile","2100000170");
                break;
            case "withoutHeaders":
                queryParamDetails.put("mobile",EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW);
                break;
            case "withoutParams":
                break;
            default:
                throw new IllegalStateException("Unexpected data: " + getUserDetailsTestData);

        }
        PlatformLoginResponsePojo  userDetailsResponse = getUserDetails.get(queryParamDetails,headerDetails);
        if (getUserDetailsTestData.equals("validNumber") || getUserDetailsTestData.equals("validumuuid") || getUserDetailsTestData.equals("validlpuuid") || getUserDetailsTestData.equals("validToken") || getUserDetailsTestData.equals("withoutHeaders")){
            Assert.assertTrue(userDetailsResponse.getId().equals("63286e44d2d35e0001592c50") || userDetailsResponse.getId().equals("626ba33cad21ea0001e872ce"), "Document id mismatch");
        } else if (getUserDetailsTestData.equals("inValidNumber")) {
            Assert.assertTrue(userDetailsResponse.getStatus().equals("400"), "Failed to provide bad request response");
            Assert.assertTrue(userDetailsResponse.getMessage().equals("User with this identifier doesnt exist"), "Failed to provide bad request response");
            Assert.assertTrue(userDetailsResponse.getError().equals("Bad Request"), "Failed to provide bad request response");
        } else if (getUserDetailsTestData.equals("withoutParams")) {
            Assert.assertTrue(userDetailsResponse.getStatus().equals("400"), "Failed to provide bad request response");
            Assert.assertTrue(userDetailsResponse.getMessage().equals("Please either enter email or mobile or id or token"), "Failed to provide bad request response");
            Assert.assertTrue(userDetailsResponse.getError().equals("Bad Request"), "Failed to provide bad request response");


        }
    }
}
