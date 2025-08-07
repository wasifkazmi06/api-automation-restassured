package upi;

import api.upi.RefundUPI;
import io.qameta.allure.Step;
import org.apache.poi.ss.formula.NameIdentifier;
import org.mockito.internal.matchers.Null;
import org.testng.Assert;
import pojos.upi.RefundUPIPojo;
import util.ReturnRandomTxnId;
import util.StringTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import static upi.PostIntentSteps.postIntentPojo;

public class RefundUPISteps {

    RefundUPIPojo refundUPIPojo = new RefundUPIPojo();
    RefundUPI refundUPI = new RefundUPI();

    DecimalFormat df = new DecimalFormat("###.##");

    public RefundUPISteps() throws Exception {
    }

    @Step
    public void refundUPIScanAndPay() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, UPIData.LP_AMOUNT, "The refund amount is not as expected.");

    }

    @Step
    public void refundUPIEnteredVPA() throws NullPointerException{

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, UPIData.LP_AMOUNT, "The refund amount is not as expected.");

    }

    @Step
    public void refundUPICollectRequest() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, UPIData.LP_AMOUNT, "The refund amount is not as expected.");

    }

    @Step
    public void refundUPIIntentPay() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, UPIData.LP_AMOUNT, "The refund amount is not as expected.");

    }

    @Step
    public void refundUPIEMIScanAndPay() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, UPIData.LOAN_AMOUNT, "The refund amount is not as expected.");

    }

    @Step
    public void refundUPIEMIEnteredVPA() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, UPIData.LOAN_AMOUNT, "The refund amount is not as expected.");

    }

    @Step
    public void refundUPIEMICollectRequest() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, UPIData.LOAN_AMOUNT, "The refund amount is not as expected.");

    }

    @Step
    public void refundUPIEMIIntentPay() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, UPIData.LOAN_AMOUNT, "The refund amount is not as expected.");

    }

    @Step
    public void refundUPILPPartial() throws NullPointerException{

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", df.format(Double.parseDouble(UPIData.LP_AMOUNT)/2))
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, df.format(Double.parseDouble(UPIData.LP_AMOUNT)/2), "The refund amount is not as expected.");

    }

    @Step
    public void refundUPIEMIPartial() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", df.format(Double.parseDouble(UPIData.LP_AMOUNT)/2))
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.status, "SUCCESS", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.respMessage, "Refund is successful", "The refund is not successful, check logs!");
        Assert.assertEquals(refundUPIPojo.amount, df.format(Double.parseDouble(UPIData.LP_AMOUNT)/2), "The refund amount is not as expected.");
    }

    @Step
    public void refundUPIGreaterThanSaleAmount() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", postIntentPojo.upiTxnIntentId)
                .replace("amount", "50000")
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.error, "Bad Request", "");
        Assert.assertEquals(refundUPIPojo.errorCode, "LP_REFUND_GT_SALE", "");
        Assert.assertEquals(refundUPIPojo.message, "Oops!! Refund amount seems to be greater than the sale transaction amount", "");
    }

    @Step
    public void refundUPIIncorrectMTX() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String refundUPIRequest = new StringTemplate(UPIConstants.REFUND_UPI)
                .replace("merchantTxnId", "InvalidMTX")
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("merchantId", postIntentPojo.merchantDetails.merchantId)
                .toString();

        refundUPIPojo = refundUPI.post(queryParamDetails, headerDetails, refundUPIRequest);
        Assert.assertEquals(refundUPIPojo.error, "Bad Request", "");
        Assert.assertEquals(refundUPIPojo.errorCode, "LP_TXN_NOT_FOUND", "");
        Assert.assertEquals(refundUPIPojo.message, "Oops!! No corresponding transaction could be found", "The refund amount is not as expected.");

    }


}
