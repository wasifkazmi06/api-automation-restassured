package upi;

import api.upi.PostIntent;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.upi.PostIntentPojo;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PostIntentSteps {

    public static PostIntentPojo postIntentPojo = new PostIntentPojo();
    PostIntent postIntent = new PostIntent();
    public int upiTxnIntentId;

    public PostIntentSteps() throws Exception {
    }

    @BeforeClass
    public void PostIntentTestsPrerequisites() throws Exception {
    }


    @Step
    public void verifyPostIntentLPTxnScanAndPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data",UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.POST_INTENT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantTxnId","TID")
                .replace("merchantTxnRefId", "TR")
                .replace("merchantUrl", "URL")
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnType", UPIData.ACQUISITION_CHANNEL_SCANPAY)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantMccCode", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantMccCode)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("merchantLogo", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantLogo)
                .replace("merchantName", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantName)
                .replace("orgTxnId", "")
                .replace("payeeMobNo", "")
                .replace("payeeCode", "")
                .replace("custRefId", "")
                .replace("expiryPeriod", "")
                .replace("accStatus", "")
                .replace("payeeName", "")
                .replace("txnTimeStamp", "")
                .replace("txnRefId", "")
                .replace("refUrl", "")
                .replace("payerVirAddr", "")
                .replace("remarks", "")
                .replace("payeeVirAddr", "")
                .toString();

        postIntentPojo = postIntent.post(queryParamDetails, headerDetails, postIntentRequest);

        upiTxnIntentId = Integer.valueOf(postIntentPojo.upiTxnIntentId);
        Assert.assertEquals(postIntentPojo.upiTxnIntentStatus, "CREATED", "Make sure user is eligible for transaction");
        Assert.assertTrue(postIntentPojo.eligibilityResponse.bnplEligible, "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.eligibilityResponse.reasonCode, "LP_ELIGIBLE", "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.merchantDetails.merchantVpa,UPIData.MERCHANT_VPA, "Make sure the merchant used is valid and eligible for UPI transaction");


    }

    @Step
    public void verifyPostIntentLPTxnEnteredVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data",UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.POST_INTENT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantTxnId","TID")
                .replace("merchantTxnRefId", "TR")
                .replace("merchantUrl", "URL")
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnType", UPIData.ACQUISITION_CHANNEL_ENTEREDVPA)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantMccCode", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantMccCode)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("merchantLogo", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantLogo)
                .replace("merchantName", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantName)
                .replace("orgTxnId", "")
                .replace("payeeMobNo", "")
                .replace("payeeCode", "")
                .replace("custRefId", "")
                .replace("expiryPeriod", "")
                .replace("accStatus", "")
                .replace("payeeName", "")
                .replace("txnTimeStamp", "")
                .replace("txnRefId", "")
                .replace("refUrl", "")
                .replace("payerVirAddr", "")
                .replace("remarks", "")
                .replace("payeeVirAddr", "")
                .toString();

        postIntentPojo = postIntent.post(queryParamDetails, headerDetails, postIntentRequest);

        upiTxnIntentId = Integer.valueOf(postIntentPojo.upiTxnIntentId);
        Assert.assertEquals(postIntentPojo.upiTxnIntentStatus, "CREATED", "Make sure user is eligible for transaction");
        Assert.assertTrue(postIntentPojo.eligibilityResponse.bnplEligible, "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.eligibilityResponse.reasonCode, "LP_ELIGIBLE", "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.merchantDetails.merchantVpa,UPIData.MERCHANT_VPA, "Make sure the merchant used is valid and eligible for UPI transaction");

    }

    @Step
    public void verifyPostIntentLPTxnCollectRequest() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.POST_INTENT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("simId", UPIData.REGISTERED_SIM_ID)
                .replace("mobile", UPIData.REGISTERED_USER)
                .replace("txnType", UPIData.ACQUISITION_CHANNEL_COLLECTREQUEST)
                .replace("merchantTxnId", "")
                .replace("merchantTxnRefId", "")
                .replace("merchantUrl", "")
                .replace("orgTxnId", "IDF7678E58D85044E2A9C7C7AF0B705D462")
                .replace("payeeMobNo", "+917010174401")
                .replace("payeeCode", "0000")
                .replace("custRefId", "918915722316")
                .replace("expiryPeriod", String.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(4320)))
                .replace("accStatus", "ONON")
                .replace("payeeName", "UPI Merchant")
                .replace("txnTimeStamp", "1628838412354")
                .replace("txnRefId", "918915722316")
                .replace("refUrl", "")
                .replace("payerVirAddr", UPIData.REGISTERED_USER + ".lzp@idfb")
                .replace("remarks", "test")
                .replace("payeeVirAddr", UPIData.MERCHANT_VPA)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantMccCode", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantMccCode)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("merchantLogo", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantLogo)
                .replace("merchantName", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantName)
                .toString();

        postIntentPojo = postIntent.post(queryParamDetails, headerDetails, postIntentRequest);

        upiTxnIntentId = Integer.valueOf(postIntentPojo.upiTxnIntentId);
        Assert.assertEquals(postIntentPojo.upiTxnIntentStatus, "CREATED", "Make sure user is eligible for transaction");
        Assert.assertTrue(postIntentPojo.eligibilityResponse.bnplEligible, "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.eligibilityResponse.reasonCode, "LP_ELIGIBLE", "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.merchantDetails.merchantVpa, UPIData.MERCHANT_VPA, "Make sure the merchant used is valid and eligible for UPI transaction");
    }

        @Step
    public void verifyPostIntentLPTxnIntentPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data",UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.POST_INTENT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantTxnId","TID")
                .replace("merchantTxnRefId", "TR")
                .replace("merchantUrl", "URL")
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnType", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantMccCode", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantMccCode)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("merchantLogo", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantLogo)
                .replace("merchantName", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantName)
                .replace("orgTxnId", "")
                .replace("payeeMobNo", "")
                .replace("payeeCode", "")
                .replace("custRefId", "")
                .replace("expiryPeriod", "")
                .replace("accStatus", "")
                .replace("payeeName", "")
                .replace("txnTimeStamp", "")
                .replace("txnRefId", "")
                .replace("refUrl", "")
                .replace("payerVirAddr", "")
                .replace("remarks", "")
                .replace("payeeVirAddr", "")
                .toString();

        postIntentPojo = postIntent.post(queryParamDetails, headerDetails, postIntentRequest);

        upiTxnIntentId = Integer.valueOf(postIntentPojo.upiTxnIntentId);
            Assert.assertEquals(postIntentPojo.upiTxnIntentStatus, "CREATED", "Make sure user is eligible for transaction");
            Assert.assertTrue(postIntentPojo.eligibilityResponse.bnplEligible, "Make sure user is eligible for transaction");
            Assert.assertEquals(postIntentPojo.eligibilityResponse.reasonCode, "LP_ELIGIBLE", "Make sure user is eligible for transaction");
            Assert.assertEquals(postIntentPojo.merchantDetails.merchantVpa,UPIData.MERCHANT_VPA, "Make sure the merchant used is valid and eligible for UPI transaction");

        }

    @Step
    public void verifyPostIntentEMITxnScanAndPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data",UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.POST_INTENT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantTxnId","TID")
                .replace("merchantTxnRefId", "TR")
                .replace("merchantUrl", "URL")
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnType", UPIData.ACQUISITION_CHANNEL_SCANPAY)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantMccCode", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantMccCode)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("merchantLogo", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantLogo)
                .replace("merchantName", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantName)
                .replace("orgTxnId", "")
                .replace("payeeMobNo", "")
                .replace("payeeCode", "")
                .replace("custRefId", "")
                .replace("expiryPeriod", "")
                .replace("accStatus", "")
                .replace("payeeName", "")
                .replace("txnTimeStamp", "")
                .replace("txnRefId", "")
                .replace("refUrl", "")
                .replace("payerVirAddr", "")
                .replace("remarks", "")
                .replace("payeeVirAddr", "")
                .toString();

        postIntentPojo = postIntent.post(queryParamDetails, headerDetails, postIntentRequest);

        upiTxnIntentId = Integer.valueOf(postIntentPojo.upiTxnIntentId);
        Assert.assertEquals(postIntentPojo.upiTxnIntentStatus, "CREATED", "Make sure user is eligible for transaction and converting to EMI");
        Assert.assertTrue(postIntentPojo.eligibilityResponse.bnplEligible, "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.eligibilityResponse.reasonCode, "LP_ELIGIBLE", "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.merchantDetails.merchantVpa,UPIData.MERCHANT_VPA, "Make sure the merchant used is valid and eligible for UPI transaction");
    }

    @Step
    public void verifyPostIntentEMITxnEnteredVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data",UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.POST_INTENT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantTxnId","TID")
                .replace("merchantTxnRefId", "TR")
                .replace("merchantUrl", "URL")
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnType", UPIData.ACQUISITION_CHANNEL_ENTEREDVPA)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantMccCode", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantMccCode)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("merchantLogo", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantLogo)
                .replace("merchantName", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantName)
                .replace("orgTxnId", "")
                .replace("payeeMobNo", "")
                .replace("payeeCode", "")
                .replace("custRefId", "")
                .replace("expiryPeriod", "")
                .replace("accStatus", "")
                .replace("payeeName", "")
                .replace("txnTimeStamp", "")
                .replace("txnRefId", "")
                .replace("refUrl", "")
                .replace("payerVirAddr", "")
                .replace("remarks", "")
                .replace("payeeVirAddr", "")
                .toString();

        postIntentPojo = postIntent.post(queryParamDetails, headerDetails, postIntentRequest);

        upiTxnIntentId = Integer.valueOf(postIntentPojo.upiTxnIntentId);
        Assert.assertEquals(postIntentPojo.upiTxnIntentStatus, "CREATED", "Make sure user is eligible for transaction and converting to EMI");
        Assert.assertTrue(postIntentPojo.eligibilityResponse.bnplEligible, "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.eligibilityResponse.reasonCode, "LP_ELIGIBLE", "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.merchantDetails.merchantVpa,UPIData.MERCHANT_VPA, "Make sure the merchant used is valid and eligible for UPI transaction");

    }

    @Step
    public void verifyPostIntentEMITxnCollectRequest() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data",UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.POST_INTENT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnType", UPIData.ACQUISITION_CHANNEL_COLLECTREQUEST)
                .replace("merchantTxnId", "")
                .replace("merchantTxnRefId", "")
                .replace("merchantUrl", "")
                .replace("orgTxnId", "IDF7678E58D85044E2A9C7C7AF0B705D462")
                .replace("payeeMobNo", "+917010174401")
                .replace("payeeCode", "0000")
                .replace("custRefId", "918915722316")
                .replace("expiryPeriod", String.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(4320)))
                .replace("accStatus", "ONON")
                .replace("payeeName", "UPI Merchant")
                .replace("txnTimeStamp", "1628838412354")
                .replace("txnRefId", "918915722316")
                .replace("refUrl", "")
                .replace("payerVirAddr", UPIData.REGISTERED_USER+".lzp@idfb")
                .replace("remarks", "test")
                .replace("payeeVirAddr", UPIData.MERCHANT_VPA)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantMccCode", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantMccCode)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("merchantLogo", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantLogo)
                .replace("merchantName", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantName)
                .toString();

        postIntentPojo = postIntent.post(queryParamDetails, headerDetails, postIntentRequest);

        upiTxnIntentId = Integer.valueOf(postIntentPojo.upiTxnIntentId);
        Assert.assertEquals(postIntentPojo.upiTxnIntentStatus, "CREATED", "Make sure user is eligible for transaction and converting to EMI");
        Assert.assertTrue(postIntentPojo.eligibilityResponse.bnplEligible, "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.eligibilityResponse.reasonCode, "LP_ELIGIBLE", "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.merchantDetails.merchantVpa,UPIData.MERCHANT_VPA, "Make sure the merchant used is valid and eligible for UPI transaction");

    }


    @Step
    public void verifyPostIntentEMITxnIntentPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data",UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.POST_INTENT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantTxnId","TID")
                .replace("merchantTxnRefId", "TR")
                .replace("merchantUrl", "URL")
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnType", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantMccCode", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantMccCode)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("merchantLogo", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantLogo)
                .replace("merchantName", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantName)
                .replace("orgTxnId", "")
                .replace("payeeMobNo", "")
                .replace("payeeCode", "")
                .replace("custRefId", "")
                .replace("expiryPeriod", "")
                .replace("accStatus", "")
                .replace("payeeName", "")
                .replace("txnTimeStamp", "")
                .replace("txnRefId", "")
                .replace("refUrl", "")
                .replace("payerVirAddr", "")
                .replace("remarks", "")
                .replace("payeeVirAddr", "")
                .toString();

        postIntentPojo = postIntent.post(queryParamDetails, headerDetails, postIntentRequest);

        upiTxnIntentId = Integer.valueOf(postIntentPojo.upiTxnIntentId);
        Assert.assertEquals(postIntentPojo.upiTxnIntentStatus, "CREATED", "Make sure user is eligible for transaction and converting to EMI");
        Assert.assertTrue(postIntentPojo.eligibilityResponse.bnplEligible, "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.eligibilityResponse.reasonCode, "LP_ELIGIBLE", "Make sure user is eligible for transaction");
        Assert.assertEquals(postIntentPojo.merchantDetails.merchantVpa,UPIData.MERCHANT_VPA, "Make sure the merchant used is valid and eligible for UPI transaction");

    }
}
