package upi;

import api.upi.bnpl.FOCMerchantUPI;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pojos.upi.bnpl.FOCMerchantUPIPojo;
import util.StringTemplate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FindOrCreateMerchantSteps {

    FOCMerchantUPI focMerchantUPI = new FOCMerchantUPI();
    Date date = new Date();
    String timeNowMilli = String.valueOf(date.getTime());
    public static FOCMerchantUPIPojo focMerchantUPIPojo = new FOCMerchantUPIPojo();
    public static String merchantId;


    public FindOrCreateMerchantSteps() throws Exception {
    }

    @BeforeMethod
    public void FindOrCreateMerchantTestsPrerequisites() {

    }

    @Step
    public void verifyFOCMerchantForValidMerchantUsingScanAndPay() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String focMerchantUPIRequest = new StringTemplate(UPIConstants.FOC_MERCHANT_UPI)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_SCANPAY)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantMccCode", UPIData.MERCHANT_MCC)
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .replace("simId", UPIData.REGISTERED_SIM_ID)
                .toString();

        focMerchantUPIPojo = focMerchantUPI.post(queryParamDetails, headerDetails, focMerchantUPIRequest);
        merchantId = focMerchantUPIPojo.lpMerchantId;

        Assert.assertTrue(focMerchantUPIPojo.active, "If not true, then the merchant data is corrupted -> Check lazypay.merchants table for isDisabled flag and decisionCode");
    }

    @Step
    public void verifyFOCMerchantForValidMerchantUsingEnteredVPA() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String focMerchantUPIRequest = new StringTemplate(UPIConstants.FOC_MERCHANT_UPI)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_ENTEREDVPA)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantMccCode", UPIData.MERCHANT_MCC)
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .replace("simId", UPIData.REGISTERED_SIM_ID)
                .toString();

        focMerchantUPIPojo = focMerchantUPI.post(queryParamDetails, headerDetails, focMerchantUPIRequest);
        merchantId = focMerchantUPIPojo.lpMerchantId;
        Assert.assertTrue(focMerchantUPIPojo.active, "If not true, then the merchant data is corrupted -> Check lazypay.merchants table for isDisabled flag and decisionCode");
    }

    @Step
    public void verifyFOCMerchantForValidMerchantUsingCollectRequest() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String focMerchantUPIRequest = new StringTemplate(UPIConstants.FOC_MERCHANT_UPI)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_COLLECTREQUEST)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantMccCode", UPIData.MERCHANT_MCC)
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .replace("simId", UPIData.REGISTERED_SIM_ID)
                .toString();

        focMerchantUPIPojo = focMerchantUPI.post(queryParamDetails,headerDetails,focMerchantUPIRequest);
        merchantId = focMerchantUPIPojo.lpMerchantId;
        Assert.assertTrue(focMerchantUPIPojo.active, "If not true, then the merchant data is corrupted -> Check lazypay.merchants table for isDisabled flag and decisionCode");
    }

    @Step
    public void verifyFOCMerchantForValidMerchantUsingIntentPay() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String focMerchantUPIRequest = new StringTemplate(UPIConstants.FOC_MERCHANT_UPI)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantMccCode", UPIData.MERCHANT_MCC)
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .replace("simId", UPIData.REGISTERED_SIM_ID)
                .toString();

        focMerchantUPIPojo = focMerchantUPI.post(queryParamDetails,headerDetails,focMerchantUPIRequest);
        merchantId = focMerchantUPIPojo.lpMerchantId;
        Assert.assertTrue(focMerchantUPIPojo.active, "If not true, then the merchant data is corrupted -> Check lazypay.merchants table for isDisabled flag and decisionCode");
    }

    @Step
    public void verifyFOCMerchantForInvalidMerchant() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String focMerchantUPIRequest = new StringTemplate(UPIConstants.FOC_MERCHANT_UPI)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantMccCode", UPIData.MERCHANT_MCC)
                .replace("merchantId", "")
                .replace("merchantVpa", "InvalidMerchant")
                .replace("userMobile", UPIData.REGISTERED_USER)
                .replace("simId", UPIData.REGISTERED_SIM_ID)
                .toString();

        focMerchantUPIPojo = focMerchantUPI.post(queryParamDetails, headerDetails, focMerchantUPIRequest);
        merchantId = focMerchantUPIPojo.lpMerchantId;
        Assert.assertEquals(focMerchantUPIPojo.errorDescription,"Merchant not valid for UPI.", "The merchant data might be incorrect -> Check if the merchant is valid");
        Assert.assertFalse(focMerchantUPIPojo.active, "If not false, then the merchant data is incorrect -> Check lazypay.merchants table for isDisabled flag and decisionCode");
    }

    @Step
    public void verifyMerchantVPAIsMandatoryForFOCMerchant() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String focMerchantUPIRequest = new StringTemplate(UPIConstants.FOC_MERCHANT_UPI)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantMccCode", UPIData.MERCHANT_MCC)
                .replace("merchantId", "")
                .replace("merchantVpa", "")
                .replace("userMobile", UPIData.REGISTERED_USER)
                .replace("simId", UPIData.REGISTERED_SIM_ID)
                .toString();

        focMerchantUPIPojo = focMerchantUPI.post(queryParamDetails, headerDetails, focMerchantUPIRequest);
        merchantId = focMerchantUPIPojo.lpMerchantId;
        Assert.assertEquals(focMerchantUPIPojo.errorCode,"INVALID_FIELD", "Check that merchant VPA is not passed in the request or if the developer has changed the error code");
        Assert.assertEquals(focMerchantUPIPojo.message, "Error in the field : merchantParameters", "Check that merchant VPA is not passed in the request");
    }

}
