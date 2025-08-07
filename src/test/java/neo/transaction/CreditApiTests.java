package neo.transaction;

import api.neobank.transaction.CreditTransaction;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import neo.BaseTestNeo;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojos.neobank.transaction.CreditApiPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class CreditApiTests extends BaseTestNeo {
    public CreditApiTests() throws Exception {
        super();
    }
    CreditTransaction creditTransaction=new CreditTransaction();

    private Map<String,String> trasactionData;
    private Map<String,String> refundData;
    private String refund_txnRefNo=new String();

    @BeforeMethod(alwaysRun = true)
    public void refundtransactionSetUP()
    {

        trasactionData= getTransactionData(TransactionData.TEST_TRANSACTION_AMOUNT,TransactionData.USER_MOBILE);
        refundData= getTransactionApprovalCode(trasactionData.get("txnRefNo"));
        refund_txnRefNo=UUID.randomUUID().toString()+1;


    }
    @Description("IF both txnId & txnRefNo is present then  the transaction should be termed refund")
    @Test(priority = 1,dataProviderClass = TransactionData.class,dataProvider = "refund_fallback",groups = { "transaction","credit","sanity"})
    public void initiateRefund(String refundFallbackLogic,String aioCaseKey)
    {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        HashMap postTransactionDbData=new HashMap();
        String request=new String();
        switch(refundFallbackLogic) {
            case "ValidParent_orginalRrn":
                request= new StringTemplate(NeoConstants.CREDIT).replace("txnRefNo", refund_txnRefNo).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).replace("approvalCode",refundData.get("auth")+"1").replace("originalRrn",trasactionData.get("rrn")).replace("merchantID",TransactionData.TEST_TRANSACTION_MERCHANT).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHNAT_NAME).replace("rrn",Long.toString(System.currentTimeMillis())).replace("mcc",TransactionData.TEST_MCC).replace("crdr","c").toString();
                break;
            case "ValidParent_ApprovalCode":
                request= new StringTemplate(NeoConstants.CREDIT).replace("txnRefNo",refund_txnRefNo).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).replace("approvalCode",refundData.get("auth")).replace("originalRrn",trasactionData.get("rrn")+"1").replace("merchantID",TransactionData.TEST_TRANSACTION_MERCHANT).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHNAT_NAME).replace("rrn",Long.toString(System.currentTimeMillis())).replace("mcc",TransactionData.TEST_MCC).replace("crdr","c").toString();
                break;
            case "NoValidParent_WithoutMerchant":
                request= new StringTemplate(NeoConstants.CREDIT).replace("txnRefNo",refund_txnRefNo).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).replace("approvalCode",refundData.get("auth")+"121").replace("originalRrn",trasactionData.get("rrn")+"121").replace("merchantID","NOMERCHANT").replace("merchantName","NOMERCHANT").replace("rrn",Long.toString(System.currentTimeMillis())).replace("mcc",TransactionData.TEST_MCC).replace("crdr","c").toString();
                break;
            case "NoValidParent_WithMerchant":
                request= new StringTemplate(NeoConstants.CREDIT).replace("txnRefNo",refund_txnRefNo).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).replace("approvalCode",refundData.get("auth")+"121").replace("originalRrn",trasactionData.get("rrn")+"121").replace("merchantID",trasactionData.get("terminalID")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHNAT_NAME).replace("rrn",Long.toString(System.currentTimeMillis())).replace("mcc",TransactionData.TEST_MCC).replace("crdr","c").toString();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + refundFallbackLogic);
        }
        CreditApiPojo creditApiPojo = creditTransaction.post(queryParamDetails, headerDetails, request);
        if (!refundFallbackLogic.equals("ValidParent_orginalRrn")&&!refundFallbackLogic.equals("ValidParent_ApprovalCode")) {
            postTransactionDbData = TransactionData.getDebitTransactionData("LAZYCARD_" + refund_txnRefNo, "credit",getPlatformUSerData(TransactionData.USER_MOBILE).getLpUuid());
            Assert.assertEquals(postTransactionDbData.get("merchantTxnId"), "LAZYCARD_" + refund_txnRefNo);
            Assert.assertEquals(postTransactionDbData.get("type"), TransactionData.REFUND_TRANSACTION_NAME_WITHOUT_PARENT);
        }
        else {
            postTransactionDbData = TransactionData.getDebitTransactionData(trasactionData.get("txnRefNo"), "credit",getPlatformUSerData(TransactionData.USER_MOBILE).getLpUuid());
            Assert.assertEquals(postTransactionDbData.get("merchantTxnId"), trasactionData.get("txnRefNo"));
            Assert.assertEquals(postTransactionDbData.get("type"), TransactionData.REFUND_TRANSACTION_NAME);
        }

        Assert.assertEquals(creditApiPojo.description,TransactionData.CREDIT_AMOUNT_SUCCESS_DESCRIPTION,"error transaction not refunded due to "+creditApiPojo.description);
        Assert.assertTrue(creditApiPojo.status,creditApiPojo.description);
        postTransactionDbData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        Assert.assertEquals(postTransactionDbData.get("amount"),TransactionData.REFUND_AMOUNT);
        Assert.assertEquals(postTransactionDbData.get("status"),TransactionData.TRANSACTION_SUCCESS_STATUS);
        Assert.assertEquals(postTransactionDbData.get("txnSource"),TransactionData.TRANSACTION_SOURCE);
    }
}
