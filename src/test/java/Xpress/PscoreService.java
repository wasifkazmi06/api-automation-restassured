package Xpress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import lombok.extern.slf4j.Slf4j;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

import static Xpress.AmsService.fetchMuid;
import static Xpress.PlatformService.fetchUserId;

@Slf4j
public class PscoreService extends XpressData {

    public PscoreService() throws Exception {
    }

    public static void creditExpireData(String mobileNumber) {
        Response creditExpireResponse = creditExpirePscoreData.getWithPathParam(creditExpireHeaders(), mobileNumber);
        System.out.println(String.valueOf(creditExpireResponse.statusCode()));
        if (String.valueOf(creditExpireResponse.statusCode()).contains("418") ||
                String.valueOf(creditExpireResponse.statusCode()).contains("401") ||
                String.valueOf(creditExpireResponse.statusCode()).contains("502") ||
                String.valueOf(creditExpireResponse.statusCode()).contains("504")) {
            Assert.fail("Failed to credit expire due to invalid HTTP status");
        }
        log.info("credit expire done successfully");
    }

    public static HashMap<String, String> creditExpireHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Automation-Key", "Dz9diCI31G7vtBt7");
        headers.put("x-sessionid", "4qwf77b4vz19zds26z5r06slj1e7ef3m");
        return headers;
    }

    public static String agreementGetOtp(String mobile) throws Exception {
        String userOtp = "";
        try {
            HashMap<String, String> headerDetails = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("phone", "+91" + mobile);
            String requestPayload = objectMapper.writeValueAsString(jsonNode);
            Response response = agreementGetOtp.postWithResponse(requestPayload, headerDetails);
            JSONObject responseJSONObject = new JSONObject(response.getBody().asString());
            userOtp = responseJSONObject.get("otp").toString();
            Assert.assertEquals(response.statusCode(), 200);
            Assert.assertNotEquals(userOtp, "null", "Otp should not be null");
        } catch (Exception e) {
            Assert.fail("Not able to fetch Otp");
        }
        return userOtp;
    }

    public static void userRegistrationOnPscore(String mobile) {
        String userRegistrationRequest = null;
        // Request creation for user registration api
        userRegistrationRequest = new StringTemplate(XpressData.USERREGISTRATION_REQUEST)
                .replace("phone", mobile)
                .replace("userId", fetchUserId(mobile))
                .toString();

        //Headers for user registration api
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("x-ps-source-key", "kRWMyMYvusSkMJrHxyGltZdKKJibOCeB");
        headers.put("x-sessionid", "e5ual43l2kw9melo24anzlqx20bi40qn");
        headers.put("Cookie", "sessionid=5wzf2wba0xs7yk1m214987y41fh52ofm; csrftoken=3c3ljBJ4Gqlbgivf5DiMNQajZb5MzNMYcVFnhuSWhky7JdZ4KhxHG9y5sRaq4fdd");
        Response userRegistrationResponse = pscoreRegistration.postWithResponse(headers, userRegistrationRequest);
        Assert.assertEquals(userRegistrationResponse.getStatusCode(), 200, "Getting Invalid status code for user registration api.");
    }

    public static void userDisbursalApi(String mobile) throws Exception {
        final int retries = 5;
        final int delay = 500; // milliseconds
        String loanId = null;

        // Polling to get loanId
        for (int i = 0; i < retries; i++) {
            loanId = getLoanId(mobile);
            if (loanId != null && !loanId.isEmpty()) {
                break;
            }
            Thread.sleep(delay);
        }

        if (loanId == null || loanId.isEmpty()) {
            Assert.fail("Loan id is either null or empty after " + retries + " retries");
        }

        String disbursalDate = convertDate(addDaysInDate(-180).toString(), "dd-MM-yyyy");
        String userDisbursalApiRequest = new StringTemplate(XpressData.DISBURSAL_API_REQUEST)
                .replace("date", disbursalDate)
                .toString();
        HashMap<String, String> headers = creditExpireHeaders();
        headers.put("Cookie", "csrftoken=rzeIgUtKanIDHaVROmQZFRnAgj2HU9cs2RbR17Xa415hlF0L7FjM9XPc02osWCQ1");

        Response userDisbursalApiResponse = null;

        // Polling to get a valid response
        for (int i = 0; i < retries; i++) {
            userDisbursalApiResponse = userDisbursalAPi.postWithPathParamsWithReponse(headers, userDisbursalApiRequest, loanId);
            if (userDisbursalApiResponse.getStatusCode() == 202) {
                break;
            } else if (userDisbursalApiResponse.getStatusCode() == 204) {
                break;
            } else if (userDisbursalApiResponse.getStatusCode() == 418) {
                Thread.sleep(delay);
            } else {
                Assert.fail("Unexpected status code: " + userDisbursalApiResponse.getStatusCode());
            }
        }

        if (userDisbursalApiResponse.getStatusCode() != 202 && userDisbursalApiResponse.getStatusCode() != 204) {
            Assert.fail("Unable to disburse user post " + retries + " retries");
        }
    }

    public static String retrunPscoreGenericAssessmentResponse(String mobile) throws Exception {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("token", "227d0a96a600");
        headers.put("Cookie", "csrftoken=njRA8LkjZMjcUoDsnc9S89qkDlJDq1PNHPUHLzRX8w8gXmFRxPJKkIGTnmoOCaqK");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("master_user_id", fetchMuid(mobile));

        Response pscoreGenericAssessmentResponse = pscoreGenericAssessmentApi.getWithResponse(queryParams, headers);
        Assert.assertEquals(pscoreGenericAssessmentResponse.getStatusCode(), 200, "Pscore generic assessment api failure due to invalid HTTP status code");
        return returnResponseBodyInString(pscoreGenericAssessmentResponse);
    }
}
