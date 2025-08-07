package upi;

import api.upi.bnpl.ValidateTransaction;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pojos.upi.bnpl.ValidateTransactionPojo;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

public class ValidateTransactionSteps {

    public static ValidateTransactionPojo validateTransactionPojo = new ValidateTransactionPojo();
    ValidateTransaction validateTransaction = new ValidateTransaction();

    public ValidateTransactionSteps() throws Exception {
    }

    @BeforeMethod
    public void ValidateTransactionTestsPrerequisites() {

    }


    @Step
    public void verifyValidateTransactionLPScanAndPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","APPROVE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "SUCCESS","The transaction has failed, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionLPEnteredVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","APPROVE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "SUCCESS","The transaction has failed, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionLPCollectRequest() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","APPROVE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "SUCCESS","The transaction has failed, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionLPIntentPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","APPROVE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "SUCCESS","The transaction has failed, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionLPScanAndPayDecline() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","DECLINE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "CANCELLED","The transaction has not been marked CANCELLED, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionLPEnteredVPADecline() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","DECLINE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "CANCELLED","The transaction has not been marked CANCELLED, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionLPCollectRequestDecline() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","DECLINE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "CANCELLED","The transaction has not been marked CANCELLED, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionLPIntentPayDecline() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","DECLINE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "CANCELLED","The transaction has not been marked CANCELLED, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");
    }

    @Step
    public void verifyValidateTransactionEMIScanAndPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","APPROVE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "SUCCESS","The transaction has failed, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionEMIEnteredVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","APPROVE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "SUCCESS","The transaction has failed, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionEMICollectRequest() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","APPROVE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "SUCCESS","The transaction has failed, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionEMIIntentPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","APPROVE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "SUCCESS","The transaction has failed, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionEMIScanAndPayDecline() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","DECLINE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "CANCELLED","The transaction has not been marked CANCELLED, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionEMIEnteredVPADecline() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","DECLINE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "CANCELLED","The transaction has not been marked CANCELLED, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionEMICollectRequestDecline() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","DECLINE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "CANCELLED","The transaction has not been marked CANCELLED, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");

    }

    @Step
    public void verifyValidateTransactionEMIIntentPayDecline() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String validateTransactionRequest = new StringTemplate(UPIConstants.VALIDATE_TRANSACTION)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("txnRefNo", InitiateTransactionSteps.txnRefNo)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("action","DECLINE")
                .toString();

        validateTransactionPojo = validateTransaction.post(queryParamDetails, headerDetails, validateTransactionRequest);
        Assert.assertEquals(validateTransactionPojo.txnRefNo, InitiateTransactionSteps.txnRefNo, "Make sure user is eligible for transaction");
        Assert.assertEquals(validateTransactionPojo.responseCode, "CANCELLED","The transaction has not been marked CANCELLED, check logs!");
        Assert.assertEquals(validateTransactionPojo.vpa, FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa, "Make sure correct merchant was pass in the request for transaction");
    }
}


