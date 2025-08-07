package lazypay.repaymentFlow;

import api.lazypay.repayment.InititateRepay;
import io.qameta.allure.Step;
import lazypay.LazypayConstants;
import org.testng.Assert;
import pojos.lazypay.repaymentFlow.InitiateRepayPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

public class InitiateRepaySteps {

    public static InitiateRepayPojo initiateRepayPojo = new InitiateRepayPojo();
    public static InititateRepay inititateRepay;
    
    static {
        try {
            inititateRepay = new InititateRepay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public InitiateRepaySteps() throws Exception {
    }


    @Step
    public void initiateRepaymentUPI() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("pg", PreferredMethodSteps.preferredMethodPojo.paymentMethod)
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", PreferredMethodSteps.preferredMethodPojo.vpa)
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_UPI);
        Assert.assertNotNull(initiateRepayPojo.transactionId, "Transaction ID null");
        Assert.assertNotNull(initiateRepayPojo.amount,"Transaction amount null");

    }

    @Step
    public void initiateRepaymentCreditCard() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("pg", PreferredMethodSteps.preferredMethodPojo.paymentMethod)
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardName", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardName)
                .replace("cardMode", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardMode)
                .replace("cardType", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardType)
                .replace("nameOnCard", SavedOptionsSteps.savedOptionPojo.cards.get(0).nameOnCard)
                .replace("cardNo", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardNo)
                .replace("isExpired", SavedOptionsSteps.savedOptionPojo.cards.get(0).isExpired)
                .replace("cardBrand", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardBrand)
                .replace("cardToken", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardToken)
                .replace("debitInfo", SavedOptionsSteps.savedOptionPojo.cards.get(0).debitInfo)
                .replace("cardDisplayName", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardDisplayName)
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_CC);
        Assert.assertNotNull(initiateRepayPojo.transactionId, "Transaction ID null");
        Assert.assertNotNull(initiateRepayPojo.amount,"Transaction amount null");
    }

    @Step
    public void initiateRepaymentDebitCard() {


        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("pg", PreferredMethodSteps.preferredMethodPojo.paymentMethod)
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardName", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardName)
                .replace("cardMode", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardMode)
                .replace("cardType", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardType)
                .replace("nameOnCard", SavedOptionsSteps.savedOptionPojo.cards.get(0).nameOnCard)
                .replace("cardNo", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardNo)
                .replace("isExpired", SavedOptionsSteps.savedOptionPojo.cards.get(0).isExpired)
                .replace("cardBrand", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardBrand)
                .replace("cardToken", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardToken)
                .replace("debitInfo", SavedOptionsSteps.savedOptionPojo.cards.get(0).debitInfo)
                .replace("cardDisplayName", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardDisplayName)
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_DC);
        Assert.assertNotNull(initiateRepayPojo.transactionId, "Transaction ID null");
        Assert.assertNotNull(initiateRepayPojo.amount,"Transaction amount null");

    }

    @Step
    public void initiateRepaymentNetBanking() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("pg", PreferredMethodSteps.preferredMethodPojo.paymentMethod)
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NB);
        Assert.assertNotNull(initiateRepayPojo.transactionId, "Transaction ID null");
        Assert.assertNotNull(initiateRepayPojo.amount,"Transaction amount null");

    }
    @Step
    public void initiateRepaymentWithoutUPI() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "10.0")
                .replace("pg", "UPI")
                .replace("bankCode", "UPI")
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "VPA is missing in UPI request");
        Assert.assertNotNull(initiateRepayPojo.errorCode,"PAYUBIZ_REPAY_VPA_MISSING");

    }

    @Step
    public void InitiateRepaymentUPIWithWrongAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "0.376154761547651674517")
                .replace("pg", "UPI")
                .replace("bankCode", "UPI")
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", "lazypay@ybl")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "Invalid Repay amount");
        Assert.assertNotNull(initiateRepayPojo.error,"Bad Request");

    }


    @Step
    public void initiateRepaymentWithoutCCDetails() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "10.0")
                .replace("pg", "CC")
                .replace("bankCode", "CC")
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "Card details are missing in cc/dc repay request");
        Assert.assertNotNull(initiateRepayPojo.errorCode,"PAYUBIZ_REPAY_CARD_DETAILS_MISSING");

    }

    @Step
    public void initiateRepaymentCCWithWrongAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "0.7654764712474235476")
                .replace("pg", "CC")
                .replace("bankCode", "CC")
                .replace("cardName", "hello world")
                .replace("cardMode", "CC")
                .replace("cardType", "CC")
                .replace("nameOnCard", "Hello world")
                .replace("cardNo", "1234567812345678")
                .replace("isExpired", "0")
                .replace("cardBrand", "VISA")
                .replace("cardToken", "1182a84c40bece10f43ddf")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "**** 5678")
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "Invalid Repay amount");
        Assert.assertNotNull(initiateRepayPojo.error,"Bad Request");

    }


    @Step
    public void initiateRepaymentWithoutDCDetails() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "10.0")
                .replace("pg", "DC")
                .replace("bankCode", "DC")
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "Card details are missing in cc/dc repay request");
        Assert.assertNotNull(initiateRepayPojo.errorCode,"PAYUBIZ_REPAY_CARD_DETAILS_MISSING");

    }

    @Step
    public void initiateRepaymentDcWithWrongAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "0.7654764712474235476")
                .replace("pg", "DC")
                .replace("bankCode", "DC")
                .replace("cardName", "hello world")
                .replace("cardMode", "DC")
                .replace("cardType", "DC")
                .replace("nameOnCard", "Hello world")
                .replace("cardNo", "1234567812345678")
                .replace("isExpired", "0")
                .replace("cardBrand", "VISA")
                .replace("cardToken", "1182a84c40bece10f43ddf")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "**** 5678")
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "Invalid Repay amount");
        Assert.assertNotNull(initiateRepayPojo.error,"Bad Request");

    }

    @Step
    public void initiateRepaymentNBWithWrongAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "0.763254761534753")
                .replace("pg", "NB")
                .replace("bankCode", "SBIB")
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", "")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "Invalid Repay amount");
        Assert.assertNotNull(initiateRepayPojo.error,"Bad Request");

    }

    @Step
    public void initiateRepaymentWithoutPgOrBankCodeDetails() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "10.0")
                .replace("pg", "")
                .replace("bankCode", "")
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", "nitesh@ybl")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "Mandatory pg amd bankCode params are missing in repayment request");
        Assert.assertNotNull(initiateRepayPojo.errorCode,"PAYUBIZ_REPAY_INSUFFICIENT_MANDATORY_PARAMS");

    }

    @Step
    public void initiateRepaymentWithoutAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String initiateRepayRequest= new StringTemplate(LazypayConstants.INITIATE_REPAY_REQUEST)
                .replace("amount", "")
                .replace("pg", "dc")
                .replace("bankCode", "dc")
                .replace("cardName", "")
                .replace("cardMode", "")
                .replace("cardType", "")
                .replace("nameOnCard", "")
                .replace("cardNo", "")
                .replace("isExpired", "")
                .replace("cardBrand", "")
                .replace("cardToken", "")
                .replace("debitInfo", "")
                .replace("cardDisplayName", "")
                .replace("vpa", "nitesh@ybl")
                .toString();

        initiateRepayPojo = inititateRepay.postWithPathParams(queryParamDetails, headerDetails, initiateRepayRequest, RepaymentData.USER_MOBILE_NEGATIVE);
        Assert.assertNotNull(initiateRepayPojo.message, "Oops!! Something went wrong");
        Assert.assertNotNull(initiateRepayPojo.errorCode,"LP_GENERIC_ERROR");

    }


}
