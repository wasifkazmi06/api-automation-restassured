package neo.transaction;
import api.neobank.transaction.CreditTransaction;
import io.qameta.allure.Description;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.BaseTestNeo;
import neo.NeoConstants;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.getTransactionHistory.Transaction;
import pojos.neobank.transaction.CreditApiPojo;
import util.StringTemplate;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Slf4j
public class Surcharge extends BaseTestNeo {
    public Surcharge() throws Exception {
        super();
    }
    CreditTransaction creditTransaction=new CreditTransaction();
    CreditApiPojo creditApiPojo = new CreditApiPojo();
    private Map<String,String> trasactionData;
    private Map<String,String> refundData;
    private String refund_txnRefNo=new String();
    private String []listOfWaivedMcc=null;
    private Float userTotalWaivedOffAmount;
    HashMap <String,String>surcharge_ProcessedPostTransactionDbData=new HashMap();
    HashMap <String,String>surcharge_WaivedPostTransactionDbData=new HashMap();
    HashMap lazycardSurcharge_ProcessedPostTransactionDbData=new HashMap();
    HashMap lazycardSurcharge_WaivedPostTransactionDbData=new HashMap();
    HashMap <String,String>surchargeUser=new HashMap<>();


    public void surchargeTransactionSetup(Boolean surchargeWaived)
    {
        UUID uniqueKey = UUID.randomUUID();
        listOfWaivedMcc=TransactionData.getDefaultValueFromGlobalDefault(TransactionData.GET_SURCHARGE_WAIVED_MCC);
        if (surchargeWaived==true)
            trasactionData= getTransactionData(TransactionData.TEST_TRANSACTION_AMOUNT,listOfWaivedMcc[0],TransactionData.SURCHARGE_USER_MOBILE,uniqueKey.toString());
        else
            trasactionData= getTransactionData(TransactionData.TEST_TRANSACTION_AMOUNT,listOfWaivedMcc[0]+"121",TransactionData.SURCHARGE_USER_MOBILE,uniqueKey.toString());
        refundData= getTransactionApprovalCode(trasactionData.get("txnRefNo"));
        refund_txnRefNo= UUID.randomUUID().toString()+1;
        userTotalWaivedOffAmount=TransactionData.getSurcharegeWaivedOfAmount(getPlatformUSerData(TransactionData.SURCHARGE_USER_MOBILE).getLpUuid());
    }

    @SneakyThrows
    @Description("Surcharge should be be waived off only for MCC present in {surchargeWaiverMcc ::globalDefaults table lazycard}\n")
    @Test(priority = 1,groups = { "transaction","credit","sanity","SURCHARGE","DIGI-TC-369","DIGI-TC-371","DIGI-TC-373"})
    public void surchargeWithsurchargeWaiverMcc() {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse", "false");
        surchargeTransactionSetup(true);
        processSurchargeAndGetData(queryParamDetails,headerDetails,true,true);
        surchageTestAssertion(true);
        Assert.assertEquals(creditApiPojo.description,TransactionData.SURCHARGE_SUCCESS_DESCRIPTION,"error transaction not refunded due to "+creditApiPojo.description);
        Assert.assertTrue(creditApiPojo.status,creditApiPojo.description);
        Assert.assertEquals(Float.sum(userTotalWaivedOffAmount,Float.parseFloat(surcharge_ProcessedPostTransactionDbData.get("amount"))),TransactionData.getSurcharegeWaivedOfAmount(surchargeUser.get("lazypayUuid")),"Surcharge amount not added to fuel surcharge waiver");
        Assert.assertEquals(trasactionData.get("lazycardTransactionId"),lazycardSurcharge_ProcessedPostTransactionDbData.get("lazyCardParentTxnId"),"parent transaction id not present in lazycard surcharge processed record");
        Assert.assertEquals(lazycardSurcharge_ProcessedPostTransactionDbData.get("lazycardTransactionId"),lazycardSurcharge_WaivedPostTransactionDbData.get("lazyCardParentTxnId"),"parent transaction id not present in lazycard surcharge processed record");

    }
    @SneakyThrows
    @Description("No surcharge should be waived for for for MCC other than  {surchargeWaiverMcc ::globalDefaults table lazycard}")
    @Test(priority = 4,groups = { "transaction","credit","sanity","SURCHARGE","DIGI-TC-370","DIGI-TC-372","DIGI-TC-373","DIGI-TC-375"})
    public void SurchargeWithNonSurchargeWaiverMcc() {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse", "false");
        surchargeTransactionSetup(false);
        processSurchargeAndGetData(queryParamDetails,headerDetails,false,true);
        surchageTestAssertion(false);
        Assert.assertEquals(creditApiPojo.description,TransactionData.SURCHARGE_SUCCESS_DESCRIPTION,"error transaction not refunded due to "+creditApiPojo.description);
        Assert.assertTrue(creditApiPojo.status,creditApiPojo.description);
        Assert.assertEquals(trasactionData.get("lazycardTransactionId"),lazycardSurcharge_ProcessedPostTransactionDbData.get("lazyCardParentTxnId"),"parent transaction id not present in lazycard surcharge processed record");
        Assert.assertEquals(surcharge_ProcessedPostTransactionDbData.get("id"),lazycardSurcharge_ProcessedPostTransactionDbData.get("lazypayTransactionId"),"parent transaction id not present in lazycard surcharge processed record");
        Assert.assertEquals(userTotalWaivedOffAmount+0,TransactionData.getSurcharegeWaivedOfAmount(surchargeUser.get("lazypayUuid")),"Surcharge aount not added to fuel surcharge waiver");
    }

    @Description("Show Surcharge Waived transaction in Ledger")
    @Test(priority =2 ,dependsOnMethods ={ "surchargeWithsurchargeWaiverMcc" },groups = { "transaction","credit","sanity","SURCHARGE","DIGI-TC-376"})
    public void surchargeWaiverTransactionShouldBePresentInLedger()
    {
        List<Transaction>lpTransactionData=getAppTransactionData(surchargeUser.get("lazypayUuid"));
        Assert.assertEquals(lpTransactionData.get(0).getId(),surcharge_WaivedPostTransactionDbData.get("id"));
        Assert.assertEquals(lpTransactionData.get(1).getId(),surcharge_ProcessedPostTransactionDbData.get("id"));
        Assert.assertEquals(lpTransactionData.get(2).getId(),trasactionData.get("id"));
    }

    @Description("Show Surcharge processed transaction in Ledger")
    @Test(priority = 5,dependsOnMethods ={ "SurchargeWithNonSurchargeWaiverMcc" },groups = { "transaction","credit","sanity","SURCHARGE","DIGI-TC-377"})
    public void surchargeTransactionShouldBePresentInLedger()
    {
        List<Transaction>lpTransactionData=getAppTransactionData(surchargeUser.get("lazypayUuid"));
        Assert.assertEquals(lpTransactionData.get(0).getId(),surcharge_ProcessedPostTransactionDbData.get("id"));
        Assert.assertEquals(lpTransactionData.get(1).getId(),trasactionData.get("id"));
    }

    @Description("LazypayTransaction id should be present in lazycard transaction table against surchage waiver & surcharge Processed")
    @Test(priority = 3,dependsOnMethods ={ "SurchargeWithNonSurchargeWaiverMcc" },groups = { "transaction","credit","sanity","SURCHARGE","DIGI-TC-400"})
    public void checkLazyapytransactionidIsPrensentInlazyCardTransactIonTable()
    {
        Assert.assertEquals(lazycardSurcharge_ProcessedPostTransactionDbData.get("lazypayTransactionId"), surcharge_ProcessedPostTransactionDbData.get("id"));
        Assert.assertEquals(lazycardSurcharge_WaivedPostTransactionDbData.get("lazypayTransactionId"), surcharge_WaivedPostTransactionDbData.get("id"));
    }
    @Description("If Parent transaction mapped to surcharge then match MCC of parent as well while doing waiver")
    @Test(priority = 6,groups = { "transaction","credit","sanity","SURCHARGE","DIGI-TC-401"})
    public void verifyMccOfParentIsConsideredWhileWaivingSurcharge()
    {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse", "false");
        surchargeTransactionSetup(false);
        processSurchargeAndGetData(queryParamDetails,headerDetails,true,true);
        surchageTestAssertion(false);
        Assert.assertEquals(creditApiPojo.description,TransactionData.SURCHARGE_SUCCESS_DESCRIPTION,"error transaction not refunded due to "+creditApiPojo.description);
        Assert.assertTrue(creditApiPojo.status,creditApiPojo.description);
        Assert.assertEquals(trasactionData.get("lazycardTransactionId"),lazycardSurcharge_ProcessedPostTransactionDbData.get("lazyCardParentTxnId"),"parent transaction id not present in lazycard surcharge processed record");
        Assert.assertEquals(trasactionData.get("lazycardTransactionId"),lazycardSurcharge_ProcessedPostTransactionDbData.get("lazyCardParentTxnId"),"parent transaction id not present in lazycard surcharge processed record");
        Assert.assertEquals(surcharge_ProcessedPostTransactionDbData.get("id"),lazycardSurcharge_ProcessedPostTransactionDbData.get("lazypayTransactionId"),"parent transaction id not present in lazycard surcharge processed record");
        Assert.assertEquals(Float.sum(userTotalWaivedOffAmount,0),TransactionData.getSurcharegeWaivedOfAmount(surchargeUser.get("lazypayUuid")),"Surcharge aount not added to fuel surcharge waiver");
    }
    @Description("If Parent transaction  not mapped to surcharge then surcharge is stored in failed surcharge table")
    @Test(priority = 7,groups = { "transaction","credit","sanity","SURCHARGE","DIGI-TC-402"})
    public void storeFailedSurcgarge()
    {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse", "false");
        surchargeTransactionSetup(false);
        processSurchargeAndGetData(queryParamDetails,headerDetails,true,false);
    }



    /***
     * method used to create surcharge transaction and returns relevent data
     * @param queryParamDetails
     * @param headerDetails
     * @param isSurchargeWaivedOff
     */
    public void processSurchargeAndGetData(Map queryParamDetails,HashMap headerDetails,Boolean isSurchargeWaivedOff,Boolean matchParent)
    {
        String request=new String();
        try {
            if (isSurchargeWaivedOff==true && matchParent==true)
                request= new StringTemplate(NeoConstants.CREDIT).replace("txnRefNo", refund_txnRefNo).replace("transactionAmount","000000000900").replace("entityId",surchargeUser.get("entityId")).replace("proxyCardNo",surchargeUser.get("kitNumber")).replace("approvalCode",refundData.get("auth")).replace("originalRrn",trasactionData.get("txnId")).replace("merchantID",trasactionData.get("terminalID")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHNAT_NAME).replace("rrn",Long.toString(System.currentTimeMillis())).replace("mcc",listOfWaivedMcc[0]).replace("crdr","d").toString();
            else if (isSurchargeWaivedOff==false && matchParent)
                request= new StringTemplate(NeoConstants.CREDIT).replace("txnRefNo", refund_txnRefNo).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("entityId",surchargeUser.get("entityId")).replace("proxyCardNo",surchargeUser.get("kitNumber")).replace("approvalCode",refundData.get("auth")+"232").replace("originalRrn",trasactionData.get("txnId")).replace("merchantID",trasactionData.get("terminalID")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHNAT_NAME).replace("rrn",Long.toString(System.currentTimeMillis())).replace("mcc",listOfWaivedMcc[0]+"1").replace("crdr","d").toString();
            else request= new StringTemplate(NeoConstants.CREDIT).replace("txnRefNo", refund_txnRefNo).replace("transactionAmount",TransactionData.ISO_REFUND_AMOUNT).replace("entityId",surchargeUser.get("entityId")).replace("proxyCardNo",surchargeUser.get("kitNumber")).replace("approvalCode",refundData.get("auth")+"1").replace("originalRrn",trasactionData.get("rrn")).replace("merchantID",trasactionData.get("terminalID")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHNAT_NAME).replace("rrn",Long.toString(System.currentTimeMillis())).replace("mcc",listOfWaivedMcc[0]+"121").replace("crdr","d").toString();
            creditApiPojo= creditTransaction.post(queryParamDetails, headerDetails, request);
            surcharge_ProcessedPostTransactionDbData = TransactionData.getDebitTransactionData(refund_txnRefNo, "surcharge_processed",surchargeUser.get("lazypayUuid"));
            lazycardSurcharge_ProcessedPostTransactionDbData = TransactionData.getLazycardTransactionData( refund_txnRefNo, "SURCHARGED_PROCESSED");
            Thread.sleep(5000);
            surcharge_WaivedPostTransactionDbData = TransactionData.getDebitTransactionData(refund_txnRefNo, "surcharge_waived",surchargeUser.get("lazypayUuid"));
            lazycardSurcharge_WaivedPostTransactionDbData = TransactionData.getLazycardTransactionData( refund_txnRefNo, "SURCHARGED_WAIVED");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assertions of surcharge test
     * @param isSurchargeWaivedOff
     */
    public void surchageTestAssertion(Boolean isSurchargeWaivedOff) {
        if (isSurchargeWaivedOff == true) {
            Assert.assertEquals(surcharge_ProcessedPostTransactionDbData.get("merchantTxnId"), "SUR_REQID_" + refund_txnRefNo);
            Assert.assertEquals(surcharge_ProcessedPostTransactionDbData.get("type"), TransactionData.SURCHARE_PROCESSED_TRANSACTION_TYPE);
            Assert.assertEquals(surcharge_WaivedPostTransactionDbData.get("type"), TransactionData.SURCHARE_WAIVED_TRANSACTION_TYPE);
            Assert.assertEquals(surcharge_ProcessedPostTransactionDbData.get("parentTxnId"), trasactionData.get("id"));
            Assert.assertEquals(surcharge_WaivedPostTransactionDbData.get("parentTxnId"), surcharge_ProcessedPostTransactionDbData.get("id"));
            Assert.assertEquals(lazycardSurcharge_ProcessedPostTransactionDbData.get("merchantName"), TransactionData.TEST_TRANSACTION_MERCHNAT_NAME);
            //  Assert.assertEquals(lazycardSurcharge_WaivedPostTransactionDbData.get("merchantName"), TransactionData.TEST_TRANSACTION_MERCHNAT_NAME);
            Assert.assertEquals(lazycardSurcharge_ProcessedPostTransactionDbData.get("lazypayTransactionId"), surcharge_ProcessedPostTransactionDbData.get("id"));
            Assert.assertEquals(lazycardSurcharge_WaivedPostTransactionDbData.get("lazypayTransactionId"), surcharge_WaivedPostTransactionDbData.get("id"));
        }
        else {
            Assert.assertEquals(surcharge_ProcessedPostTransactionDbData.get("merchantTxnId"), "SUR_REQID_"+refund_txnRefNo);
            Assert.assertEquals(surcharge_ProcessedPostTransactionDbData.get("type"), TransactionData.SURCHARE_PROCESSED_TRANSACTION_TYPE);
            Assert.assertEquals(surcharge_WaivedPostTransactionDbData.get("type"), null);
            Assert.assertEquals(surcharge_ProcessedPostTransactionDbData.get("parentTxnId"), trasactionData.get("id"));
            Assert.assertEquals(surcharge_WaivedPostTransactionDbData.get("parentTxnId"), null);
            Assert.assertEquals(lazycardSurcharge_ProcessedPostTransactionDbData.get("merchantName"), TransactionData.TEST_TRANSACTION_MERCHNAT_NAME);
            Assert.assertEquals(lazycardSurcharge_WaivedPostTransactionDbData.get("merchantName"), null);
            // Assert.assertEquals(lazycarPostTransactionDbData.get("lazypayTransactionId"), lazypayPostTransactionDbData.get("id"));

        }
    }

    /**
     * get lazycard user data for test
     */
    @BeforeMethod(alwaysRun = true)
    public void getSurchargeUSer()
    {
        surchargeUser=TransactionData.getLazycardData(getPlatformUSerData(TransactionData.SURCHARGE_USER_MOBILE).getLpUuid());
    }

    @Description("refunds for transaction that as surcharge associated with it")
    @Test(dataProviderClass = TransactionData.class,dataProvider = "refundType",groups = { "transaction","credit","sanity","SURCHARGE"})
    public void refundForTransactionWithSurcharge(String SurChargerefundtype) throws Exception
    {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request=new String();
        HashMap postTransactionDbData=new HashMap();
        if (SurChargerefundtype.equalsIgnoreCase("withWaiver")) {
            surchargeTransactionSetup(true);
            processSurchargeAndGetData(queryParamDetails, headerDetails, true, true);
        }
        else if (SurChargerefundtype.equalsIgnoreCase("WithoutWaiver"))
        {
            surchargeTransactionSetup(false);
            processSurchargeAndGetData(queryParamDetails, headerDetails, false, true);
        }
        String txnRefNo=refund_txnRefNo+System.currentTimeMillis();
        request= new StringTemplate(NeoConstants.CREDIT).replace("txnRefNo", txnRefNo).replace("transactionAmount",calculateRefundAmount(trasactionData.get("LazycardAmount"),surcharge_ProcessedPostTransactionDbData.get("amount"))).replace("entityId",surchargeUser.get("entityId")).replace("proxyCardNo",surchargeUser.get("kitNumber")).replace("approvalCode",refundData.get("auth")+"1").replace("originalRrn",trasactionData.get("rrn")).replace("merchantID",trasactionData.get("terminalID")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHNAT_NAME).replace("rrn",Long.toString(System.currentTimeMillis())).replace("mcc",TransactionData.TEST_MCC).replace("crdr","c").toString();
        Thread.sleep(1000);
        CreditApiPojo creditApiPojo = creditTransaction.post(queryParamDetails, headerDetails, request);
        postTransactionDbData = TransactionData.getDebitTransactionData(trasactionData.get("txnRefNo"), "credit",surchargeUser.get("lazypayUuid"));
        Assert.assertEquals(postTransactionDbData.get("merchantTxnId"), trasactionData.get("txnRefNo"));
        Assert.assertEquals(postTransactionDbData.get("type"), TransactionData.REFUND_TRANSACTION_NAME);
        Assert.assertEquals(postTransactionDbData.get("amount"),trasactionData.get("LazycardAmount"));
        Assert.assertEquals(creditApiPojo.description,TransactionData.CREDIT_AMOUNT_SUCCESS_DESCRIPTION,"error transaction not refunded due to "+creditApiPojo.description);
        Assert.assertTrue(creditApiPojo.status,creditApiPojo.description);
        Thread.sleep(2000);
        postTransactionDbData.putAll(TransactionData.getLazyCardTransactionData(txnRefNo));
        if (SurChargerefundtype.equalsIgnoreCase("withWaiver")) {
            surchargeSettelmentAssertions(lazycardSurcharge_WaivedPostTransactionDbData.get("lazycardTransactionId").toString(), true, postTransactionDbData);
            Assert.assertEquals(trasactionData.get("LazycardAmount"),postTransactionDbData.get("LazycardAmount"));

        }
        else if (SurChargerefundtype.equalsIgnoreCase("WithoutWaiver")) {
            surchargeSettelmentAssertions(lazycardSurcharge_ProcessedPostTransactionDbData.get("lazycardTransactionId").toString(), false, postTransactionDbData);
            Assert.assertEquals(lazycardSurcharge_ProcessedPostTransactionDbData.get("LazyCardAmount"),postTransactionDbData.get("LazycardAmount"));
            Assert.assertEquals(trasactionData.get("LazycardAmount"),TransactionData.getRefundAmountByParentId(lazycardSurcharge_ProcessedPostTransactionDbData.get("lazyCardParentTxnId").toString()));


        }


    }

    /**
     *
     * @param saleAmount
     * @param surChargeAmount
     * @return
     */
    public String calculateRefundAmount(String saleAmount,String surChargeAmount)
    {
        return (getIsoAmount(Double.parseDouble(saleAmount)+Double.parseDouble(surChargeAmount)));
    }

    /**
     *
     * @param surchargeParentId
     * @param isWaived
     * @param postTransactionData
     */

    public void surchargeSettelmentAssertions(String surchargeParentId,Boolean isWaived,HashMap postTransactionData)
    {
        if (!isWaived) {
            Assert.assertEquals(TransactionData.getRefundedSurcharge(surchargeParentId).get("refund_txn_id"), postTransactionData.get("lazycardTransactionId"));
            Assert.assertEquals(TransactionData.getRefundedSurcharge(surchargeParentId).get("status"), "REFUND_SUCCESS");
            Assert.assertEquals(TransactionData.getRefundedSurcharge(surchargeParentId).get("amount"), lazycardSurcharge_ProcessedPostTransactionDbData.get("LazyCardAmount"));
        }
        else {
            Assert.assertEquals(TransactionData.getWavierAdjustments(surchargeParentId).get("waiver_txn_id"), lazycardSurcharge_WaivedPostTransactionDbData.get("lazycardTransactionId"));
            Assert.assertEquals(TransactionData.getWavierAdjustments(surchargeParentId).get("settled"), "1");
            Assert.assertEquals(TransactionData.getWavierAdjustments(surchargeParentId).get("amount"), lazycardSurcharge_WaivedPostTransactionDbData.get("LazyCardAmount"));
        }

    }

}

