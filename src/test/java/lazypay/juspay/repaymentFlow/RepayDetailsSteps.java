package lazypay.juspay.repaymentFlow;

import api.lazypay.juspay.repayment.RepayDetails;

import io.qameta.allure.Step;

import lazypay.MakeTransaction;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;

import org.testng.annotations.Test;
import pojos.lazypay.juspay.repaymentFlow.RepayDetailsPojo;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepayDetailsSteps {


    public static RepayDetailsPojo repayDetailsPojo = new RepayDetailsPojo();
    public static RepayDetails repayDetails;
    public static double totalOutStanding;
    public static String identifier;
    lazypay.repaymentFlow.RepayDetailsSteps repayDetailsSteps=new lazypay.repaymentFlow.RepayDetailsSteps ();

    static {
        try {
            repayDetails = new RepayDetails();
            //TransactionData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public RepayDetailsSteps() throws Exception {
    }

    @Step

    public void verifyRepayDetailsUPI() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        identifier=repayDetailsSteps.fetchIdentifier(RepaymentData_JP.USER_MOBILE_UPI_JP);

        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
        totalOutStanding=repayDetailsPojo.totalOutstanding;
        if(totalOutStanding<=1){
            MakeTransaction.makeLPOTPTransaction(RepaymentData_JP.USER_MOBILE_UPI_JP, "", TransactionData.INITIATE_PAY_V0, TransactionData.PAY_V0,
                    TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,"", false);
            repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
            totalOutStanding=repayDetailsPojo.totalOutstanding;
            if (totalOutStanding<1) {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
                    totalOutStanding=repayDetailsPojo.totalOutstanding;
                    if (totalOutStanding>1) break;
                }
            }
        }

        List<String> payModeDisplayOrder = repayDetailsPojo.payModeDisplayOrder;
        String mode = payModeDisplayOrder.get(3);
        Assert.assertEquals(mode, "upi", "Getting Invalid mode in API response");
        Assert.assertNotNull(repayDetailsPojo.identifier, "API doesnt retrive the identifier field");
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding, "API doesnt retrive the totalOutstanding field");


    }


    @Step
    public void verifyRepayDetailsCreditCard() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        identifier=repayDetailsSteps.fetchIdentifier(RepaymentData_JP.USER_MOBILE_CC_JP);
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
        totalOutStanding=repayDetailsPojo.totalOutstanding;
        if(totalOutStanding<=1){
            MakeTransaction.makeLPOTPTransaction(RepaymentData_JP.USER_MOBILE_CC_JP, "", TransactionData.INITIATE_PAY_V0, TransactionData.PAY_V0,
                    TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,"", false);
            repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
            totalOutStanding=repayDetailsPojo.totalOutstanding;
            if (totalOutStanding<1) {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
                    totalOutStanding=repayDetailsPojo.totalOutstanding;
                    if (totalOutStanding>1) break;
                }
            }
        }
        List<String> payModeDisplayOrder = repayDetailsPojo.payModeDisplayOrder;
        String mode = payModeDisplayOrder.get(2);
        Assert.assertEquals(mode, "cc", "Getting Invalid mode in API response");
        Assert.assertNotNull(repayDetailsPojo.identifier, "API doesnt retrive the identifier field");
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding, "API doesnt retrive the totalOutstanding field");


    }

    @Step
    public void verifyRepayDetailsDebitCard() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        identifier=repayDetailsSteps.fetchIdentifier(RepaymentData_JP.USER_MOBILE_DC_JP);
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
        totalOutStanding=repayDetailsPojo.totalOutstanding;
        if(totalOutStanding<=1){
            MakeTransaction.makeLPOTPTransaction(RepaymentData_JP.USER_MOBILE_DC_JP, "", TransactionData.INITIATE_PAY_V0, TransactionData.PAY_V0,
                    TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,"", false);
            repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
            totalOutStanding=repayDetailsPojo.totalOutstanding;
            if (totalOutStanding<1) {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
                    totalOutStanding=repayDetailsPojo.totalOutstanding;
                    if (totalOutStanding>1) break;
                }
            }
        }

        List<String> payModeDisplayOrder = repayDetailsPojo.payModeDisplayOrder;
        String mode = payModeDisplayOrder.get(1);
        Assert.assertEquals(mode, "dc", "Getting Invalid mode in API response");
        Assert.assertNotNull(repayDetailsPojo.identifier, "API doesnt retrive the identifier field");
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding, "API doesnt retrive the totalOutstanding field");

    }

    @Step
    public void verifyRepayDetailsNetBanking() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        identifier=repayDetailsSteps.fetchIdentifier(RepaymentData_JP.USER_MOBILE_NB_JP);
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
        totalOutStanding=repayDetailsPojo.totalOutstanding;
        if(totalOutStanding<=1){
            MakeTransaction.makeLPOTPTransaction(RepaymentData_JP.USER_MOBILE_NB_JP, "", TransactionData.INITIATE_PAY_V0, TransactionData.PAY_V0,
                    TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,"", false);
            repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
            totalOutStanding=repayDetailsPojo.totalOutstanding;
            if (totalOutStanding<1) {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
                    totalOutStanding=repayDetailsPojo.totalOutstanding;
                    if (totalOutStanding>1) break;
                }
            }
        }
        List<String> payModeDisplayOrder = repayDetailsPojo.payModeDisplayOrder;
        String mode = payModeDisplayOrder.get(0);
        Assert.assertEquals(mode, "nb", "Getting Invalid mode in API response");
        Assert.assertNotNull(repayDetailsPojo.identifier, "API doesnt retrive the identifier field");
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding, "API doesnt retrive the totalOutstanding field");


    }

    @Step
    public void verifyRepayDetailsForRefund() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        identifier=repayDetailsSteps.fetchIdentifier(RepaymentData_JP.USER_MOBILE_RF_JP);

        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
        totalOutStanding=repayDetailsPojo.totalOutstanding;
        if(totalOutStanding<=0){
            MakeTransaction.makeLPOTPTransaction(RepaymentData_JP.USER_MOBILE_RF_JP, "", TransactionData.INITIATE_PAY_V0, TransactionData.PAY_V0,
                    TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,"", false);
            repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
            totalOutStanding=repayDetailsPojo.totalOutstanding;
        }
        Assert.assertNotNull(repayDetailsPojo.identifier, "API doesnt retrive the identifier field");
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding, "API doesnt retrive the totalOutstanding field");


    }

    @Step
    public void verifyRepayDetailsInvalidUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, "123Ab!@#45");
        Assert.assertEquals(repayDetailsPojo.errorCode, "LP_INVALID_USER_DETAIL", "Repay details for Invalid User failed as error code was different");
        Assert.assertEquals(repayDetailsPojo.message, "Oops!! User associated with corresponding transaction does not seem to exist in LazyPay", "Repay details for Invalid User failed as message was different");

    }

}
