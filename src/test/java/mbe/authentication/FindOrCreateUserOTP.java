package mbe.authentication;

import api.lazypay.juspay.repayment.FindUserOTP;
import api.lazypay.juspay.repayment.OauthToken;
import constants.UtilConstants;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.lazypay.juspay.repaymentFlow.FindUserOTPPojo;
import pojos.lazypay.juspay.repaymentFlow.OauthTokenPojo;
import util.ReadProperties;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FindOrCreateUserOTP {
    static String otp;

    public static OauthTokenPojo oauthTokenPojo=new OauthTokenPojo();
    public FindOrCreateUserOTP() throws Exception {
    }

    static FindUserOTP findUserOTP;

    static {
        try {
            findUserOTP = new FindUserOTP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static OauthToken oAuthToken;

    static {
        try {
            oAuthToken = new OauthToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Properties propFile = new Properties();

    @Step

    public static String findOrCreateOTP(String userMobile) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String authToken = "";
        // userMobile="6000000278";
        authToken = generateOAuthToken();
        headerDetails.put("Authorization", "Bearer " +authToken);

        String OTPRequest= new StringTemplate(MbeConstants.OTP_REQUEST)
                .replace("mobile", userMobile)
                .toString();

        FindUserOTPPojo findUserOTPPojo = findUserOTP.post(queryParamDetails, headerDetails, OTPRequest);

        Assert.assertNotNull(findUserOTPPojo.responseData);
        otp = findUserOTPPojo.responseData.otpValue;
        return otp;
    }

    public static String generateOAuthToken() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        queryParamDetails.put("grant_type", "implicit");
        queryParamDetails.put("client_id", AuthTestData.CLIENT_ID);

        queryParamDetails.put("client_secret",AuthTestData.CLIENT_SECRET);

        headerDetails.put("Content-Type", "application/x-www-form-urlencoded");

        String oAuthRequest= "";
        oauthTokenPojo = oAuthToken.post(queryParamDetails, headerDetails, oAuthRequest);
        System.out.println(oauthTokenPojo.access_token.toString());
        Assert.assertNotNull(oauthTokenPojo.access_token);
        return oauthTokenPojo.access_token;
    }

}
