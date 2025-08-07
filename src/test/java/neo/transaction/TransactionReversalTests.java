package neo.transaction;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import neo.BaseTestNeo;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import api.neobank.transaction.RefundTransaction;
import pojos.neobank.transaction.RefundTransactionPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class TransactionReversalTests extends BaseTestNeo {
    public TransactionReversalTests() throws Exception {
        super();
    }
    RefundTransaction refundTransaction=new RefundTransaction();
    private String transactionref=new String();

    public void refundtransactionSetUP()
    {
        transactionref= getTransactionData(TransactionData.TEST_TRANSACTION_AMOUNT,TransactionData.USER_MOBILE).get("txnRefNo").toString();
    }
    @Description("Test to verify full amount is refunded back to user")
    @Feature("Transaction")
    @Test(priority = 1,groups = { "transaction","refund","sanity" })
    public void refundAmount()
    {
        refundtransactionSetUP();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        HashMap postTransactionDbData=new HashMap();
        String request= new StringTemplate(NeoConstants.REFUND_TRANSACTION_REQUEST).replace("txnRefNo",transactionref).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        RefundTransactionPojo refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        postTransactionDbData=TransactionData.getDebitTransactionData(transactionref,"credit",getPlatformUSerData(TransactionData.USER_MOBILE).getLpUuid());
        Assert.assertEquals(refundTransactionPojo.description,"Authorized!","error transaction not refunded due to "+refundTransactionPojo.description);
        Assert.assertEquals(refundTransactionPojo.status,TransactionData.AUTH_SUCCESS_STATUS_CODE);
        postTransactionDbData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        refundTransactionDbDataAssertions(postTransactionDbData);


    }
    @Description("Test to verify if duplicate call for refund comes , its not allowed")
    @Feature("Transaction")
    @Test(priority = 2,groups = { "transaction","refund"})
    public void VerifyDuplicateRefundNotAllowed()
    {
        refundtransactionSetUP();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.REFUND_TRANSACTION_REQUEST).replace("txnRefNo",transactionref).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        RefundTransactionPojo refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(refundTransactionPojo.description,TransactionData.REFUND_DUPLICATE_ENTRY_ERROR_MSG,"error transaction not refunded due to "+refundTransactionPojo.description);
        Assert.assertEquals(refundTransactionPojo.status,TransactionData.TRANSACTION_ERROR_CODE_14);
    }
    @Description("Test to verify if duplicate call for refund comes , its not allowed")
    @Feature("Transaction")
    @Test(priority = 3,groups = { "transaction","refund"})
    public void RefundApiThrowsErrorIfMerchantIdMissMatch()
    {
        refundtransactionSetUP();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.REFUND_TRANSACTION_REQUEST).replace("txnRefNo",transactionref).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT1).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        RefundTransactionPojo refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(refundTransactionPojo.description,TransactionData.REFUND_DUPLICATE_ENTRY_ERROR_MSG,"error transaction not refunded due to "+refundTransactionPojo.description);
        Assert.assertEquals(refundTransactionPojo.status,TransactionData.TRANSACTION_ERROR_CODE_14);
    }

    @Description("Test to verify request for partial refund is honoured")
    @Feature("Transaction")
    @Test(priority = 4,groups = { "transaction","refund","sanity"})
    public void partialRefundTest()
    {
        refundtransactionSetUP();
        HashMap postTransactionDbData=new HashMap();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.REFUND_TRANSACTION_REQUEST).replace("txnRefNo",transactionref).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        RefundTransactionPojo refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(refundTransactionPojo.description,"Authorized!","error transaction not refunded due to "+refundTransactionPojo.description);
        Assert.assertEquals(refundTransactionPojo.status,TransactionData.AUTH_SUCCESS_STATUS_CODE);
        postTransactionDbData=TransactionData.getDebitTransactionData(transactionref,"credit",getPlatformUSerData(TransactionData.USER_MOBILE).getLpUuid());
        postTransactionDbData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        refundTransactionDbDataAssertions(postTransactionDbData);
        //try 2nd refund with total refund amount greater the transacted amount
        request= new StringTemplate(NeoConstants.REFUND_TRANSACTION_REQUEST).replace("txnRefNo",transactionref).replace("transactionAmount",TransactionData.INVALID_REFUND_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(refundTransactionPojo.status,TransactionData.TRANSACTION_ERROR_CODE_14,"Refund allowed for amount greater than transaction amount"+TransactionData.TRANSACTION_ERROR_CODE_14);
        //try 2nd refund with total refund amount equal to the transacted amount
        request= new StringTemplate(NeoConstants.REFUND_TRANSACTION_REQUEST).replace("txnRefNo",transactionref).replace("transactionAmount",TransactionData.REFUND_AMOUNT2).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(refundTransactionPojo.description,"Authorized!","error transaction not refunded due to "+refundTransactionPojo.description);
        Assert.assertEquals(refundTransactionPojo.status,TransactionData.AUTH_SUCCESS_STATUS_CODE);
        postTransactionDbData=TransactionData.getDebitTransactionData(transactionref,"credit",getPlatformUSerData(TransactionData.USER_MOBILE).getLpUuid());
        postTransactionDbData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        refundTransactionDbDataAssertions(postTransactionDbData);

    }
    @Description("Test To verify transaction amount is honoured in standard iso format provided by yap and throws error when transaction amount is not in iso format")
    @Feature("Transaction")
    @Test(priority = 5,groups = { "transaction","refund"})
    public void apiShouldThrowErrorForNonIsoFormatAmount()
    {
        refundtransactionSetUP();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.REFUND_TRANSACTION_REQUEST).replace("txnRefNo",transactionref).replace("transactionAmount",TransactionData.WRONG_AMOUNT_FORMAT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        RefundTransactionPojo refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(refundTransactionPojo.status,TransactionData.SUSPECTED_FRAUD_ERROR_CODE);
        Assert.assertEquals(refundTransactionPojo.description,TransactionData.WRONG_AMOUNT_FORMAT_ERROR,"transaction getting approved for amount in non iso format ->>>reason"+refundTransactionPojo.description);
    }

    private void refundTransactionDbDataAssertions(HashMap postTransactionDbData)
    {
        Assert.assertEquals(postTransactionDbData.get("amount"),TransactionData.REFUND_AMOUNT);
        Assert.assertEquals(postTransactionDbData.get("status"),TransactionData.TRANSACTION_SUCCESS_STATUS);
        Assert.assertEquals(postTransactionDbData.get("merchantTxnId"),transactionref);
        Assert.assertEquals(postTransactionDbData.get("txnSource"),TransactionData.TRANSACTION_SOURCE);
        Assert.assertEquals(postTransactionDbData.get("type"),TransactionData.REFUND_TRANSACTION_NAME);
    }

    @Test
    public void reversalBeforeAuth()
    {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        Map<String,String> trasactionData;
        headerDetails.put("encryptResponse","false");
        UUID uniqueKey = UUID.randomUUID();
        String request= new StringTemplate(NeoConstants.REFUND_TRANSACTION_REQUEST).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        RefundTransactionPojo refundTransactionPojo = refundTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(refundTransactionPojo.description,TransactionData.REVERSAL_BEFORE_AUTH_MSG,"error transaction not refunded due to "+refundTransactionPojo.description);
        Assert.assertEquals(refundTransactionPojo.status,TransactionData.REVERSAL_BEFORE_AUTH_STATUS_CODE);
        Assert.assertTrue(!TransactionData.getreversalPendingTransactionData(uniqueKey.toString()));
        trasactionData= getTransactionData(TransactionData.TEST_TRANSACTION_AMOUNT,"2345",TransactionData.USER_MOBILE,uniqueKey.toString());
        Assert.assertEquals(trasactionData.get("LazycardReason"),"REVERSAL_BEFORE_AUTH");
        Assert.assertEquals(trasactionData.get("LazycardStatus"),"SECURITY_VIOLATION");


    }
}
