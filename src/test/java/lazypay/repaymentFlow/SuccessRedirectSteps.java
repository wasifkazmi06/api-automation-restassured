package lazypay.repaymentFlow;

import api.lazypay.repayment.SuccessRedirect;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.lazypay.repaymentFlow.SuccessRedirectPojo;

import java.util.HashMap;
import java.util.Map;

public class SuccessRedirectSteps {
    SuccessRedirectPojo successRedirectPojo = new SuccessRedirectPojo();
    SuccessRedirect successRedirect = new SuccessRedirect();
    InitiateRepaySteps initiateRepayTests = new InitiateRepaySteps();
    public SuccessRedirectSteps() throws Exception {
    }

    @Step
    public void verifySuccessRedirectUPI() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        successRedirectPojo = successRedirect.get(queryParamDetails, headerDetails, initiateRepayTests.initiateRepayPojo.transactionId );
        Assert.assertEquals(successRedirectPojo.mobile, RepaymentData.USER_MOBILE_UPI,"redirect user to the success screen after a UPI repayment failed as mobile number did not match");
        Assert.assertEquals(successRedirectPojo.txnId, initiateRepayTests.initiateRepayPojo.transactionId,"redirect user to the success screen after a UPI repayment failed as transaction id did not match");
    }

    @Step
    public void verifySuccessRedirectCreditCard() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        successRedirectPojo = successRedirect.get(queryParamDetails, headerDetails, initiateRepayTests.initiateRepayPojo.transactionId );
        Assert.assertEquals(successRedirectPojo.mobile, RepaymentData.USER_MOBILE_CC,"redirect user to the success screen after a CC repayment failed as mobile number did not match");
        Assert.assertEquals(successRedirectPojo.txnId, initiateRepayTests.initiateRepayPojo.transactionId,"redirect user to the success screen after a CC repayment failed as transaction id did not match");
    }

    @Step
    public void verifySuccessRedirectDebitCard() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        successRedirectPojo = successRedirect.get(queryParamDetails, headerDetails, initiateRepayTests.initiateRepayPojo.transactionId );
        Assert.assertEquals(successRedirectPojo.mobile, RepaymentData.USER_MOBILE_DC,"redirect user to the success screen after a DC repayment failed as mobile number did not match");
        Assert.assertEquals(successRedirectPojo.txnId, initiateRepayTests.initiateRepayPojo.transactionId,"redirect user to the success screen after a DC repayment failed as transaction id did not match");
    }

    @Step
    public void verifySuccessRedirectNetBanking() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        successRedirectPojo = successRedirect.get(queryParamDetails, headerDetails, initiateRepayTests.initiateRepayPojo.transactionId );
        Assert.assertEquals(successRedirectPojo.mobile, RepaymentData.USER_MOBILE_NB,"redirect user to the success screen after a NB repayment failed as mobile number did not match");
        Assert.assertEquals(successRedirectPojo.txnId, initiateRepayTests.initiateRepayPojo.transactionId,"redirect user to the success screen after a NB repayment failed as transaction id did not match");
    }

    @Step
    public void verifySuccessRedirectInvalidTxnId() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        successRedirectPojo = successRedirect.get(queryParamDetails, headerDetails, "TXNINVALIDID1234567890" );
        Assert.assertEquals(successRedirectPojo.message, "Transaction not found","invalid transction ID on SuccessRedirect API failed as error message did not match");
        Assert.assertEquals(successRedirectPojo.status, "400","invalid transction ID on SuccessRedirect API failed as status did not match");

    }

}
