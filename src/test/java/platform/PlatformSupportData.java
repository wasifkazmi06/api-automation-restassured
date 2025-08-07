package platform;

import api.userRegistration.FetchOTP;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlatformSupportData  {

    public static String EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW = "6100000170";
    public static String EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_FOR_MAX_OTP_ATTEMPTS = "6100000173";
    public static String EXISTING_USER_TEST_MOBILE_NUMBER_LOGIN_FLOW = "7878787878";
    public static String EXISTING_USER_MOBILE_NUMBER_TXN_FLOW = "6100000175";
    public static String EXISTING_USER_MOBILE_NUMBER_TXN_FLOW_ALPHANUMERIC = "6100000174";
    public static String NEW_USER_MOBILE_NUMBER_TXN_FLOW = "6110000595";
    public static String NEW_USER_MOBILE_NUMBER_TXN_FLOW_ALPHANUMERIC = "6110000596";
    public static String NEW_USER_MOBILE_NUMBER_LOGIN_FLOW = "6110000598";

    public static String INVALID_NUMBER="5134267890";
    public static String SOURCE = "fetch_otp";
    public static String OTP_FORMAT="ALPHA_NUMERIC";
    protected String tenantID = "ec35b493";
    protected String SAURON_APP_LOGIN_NUMERIC_V1 = "1";
    protected String SECUREAPP_WEBAPP_LOGIN_NUMERIC_V1 = "2";
    protected String SECUREAPP_WEBAPP_TRANSACTIONS_NUMERIC_V1 = "3";
    protected String SECUREAPP_WEBAPP_TRANSACTIONS_ALPHANUMERIC_V1 = "4";
    protected String BILLPAY_TRANSACTIONS_V1 = "5";
    protected FetchOTP fetchOTP =  new FetchOTP();

    public PlatformSupportData() throws Exception {
    }

    @Step
    public void purgeUser(String mobileNumber) throws InterruptedException {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("MobileNumber", mobileNumber);
        queryParamDetails.put("token", "PuRgE_U5eR");
        queryParamDetails.put("Purge_All_User_Data", "true");
        queryParamDetails.put("kyc", "false");
        queryParamDetails.put("ds", "false");
        queryParamDetails.put("vl", "false");
        queryParamDetails.put("cl", "false");
        queryParamDetails.put("upi", "false");
        queryParamDetails.put("col", "false");
        queryParamDetails.put("sl", "false");
        queryParamDetails.put("lp", "false");
        queryParamDetails.put("rbl", "false");
        queryParamDetails.put("lpup", "false");
        queryParamDetails.put("bifrost", "false");
        queryParamDetails.put("fintech", "false");
        queryParamDetails.put("bbps", "false");
        queryParamDetails.put("platform", "false");
        queryParamDetails.put("delete_UMUUID", "false");
        Response response = RestAssured.given()
                .headers(headerDetails)
                .queryParams(queryParamDetails)
                .get("http://rakeshgouda_patil:1136335a6648e3101bea8be843354c2f01@qa-jenkins.lazypay.net:8080/job/Purge_User/buildWithParameters").prettyPeek();
        Thread.sleep(10000);
    }
}
