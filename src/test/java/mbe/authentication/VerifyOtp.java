package mbe.authentication;



import api.lazypay.juspay.repayment.VerifyOTP;
import io.qameta.allure.Step;
import lazypay.juspay.repaymentFlow.JPRepaymentTests;
import pojos.lazypay.juspay.repaymentFlow.VerifyOtpPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

public class VerifyOtp {


    public static VerifyOtpPojo verifyOtpPojo=new VerifyOtpPojo();

    public VerifyOtp() throws Exception {
    }
    static VerifyOTP verifyOTP;

    static {
        try {
            verifyOTP = new VerifyOTP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Properties propFile = new Properties();
    @Step
    public static String verifyOTP(String userMobile, String otp, String otpId) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("PF-App-Lock-Token", "");
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", "");
        headerDetails.put("PF-Location", AuthTestData.location);

        String message ="";
        String OTPRequest= new StringTemplate(MbeConstants.VERIFY_OTP)
                .replace("mobile", userMobile)
                .replace("otp", otp)
                .replace("whatsAppConsent", "true")
                .replace("clientId", AuthTestData.otpClientId)
                .replace("otpId", otpId)
                .toString();

        verifyOtpPojo = verifyOTP.post(queryParamDetails, headerDetails, OTPRequest);

      //  message=verifyOtpPojo.meta.message;
      //  Assert.assertNotNull(message);
//        if(!message.contains("OTP Sent")){
//            System.out.println("Invalid");
//        }

        return verifyOtpPojo.getOauthTokenResponse().getAuthToken();
    }

}
