package mbe.authentication;



import io.qameta.allure.Step;
import lazypay.juspay.repaymentFlow.JPRepaymentTests;
import org.testng.Assert;
import pojos.lazypay.juspay.repaymentFlow.SendOtpPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

public class SendOtp {


    public static SendOtpPojo sendOtpPojo=new SendOtpPojo();

    public SendOtp() throws Exception {
    }
    static api.lazypay.juspay.repayment.SendOtp sendOTP;

    static {
        try {
            sendOTP = new api.lazypay.juspay.repayment.SendOtp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Properties propFile = new Properties();
    @Step
    public static String SendingOTP(String userMobile) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        //String userMobile="6000000278";
        //headerDetails.put("Accept","*/*");

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

        String message;
        String OTPRequest= new StringTemplate(MbeConstants.SEND_OTP)
                .replace("mobile", userMobile)
                .toString();

        sendOtpPojo = sendOTP.post(queryParamDetails, headerDetails, OTPRequest);

        message=sendOtpPojo.meta.message;
        Assert.assertNotNull(message);
        if(!message.contains("OTP Sent")){
            System.out.println("Invalid");
        }
        return sendOtpPojo.meta.otpId;
    }

}
