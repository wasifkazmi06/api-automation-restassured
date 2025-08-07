package neo.transaction;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import neo.BaseTestNeo;
import neo.NeoConstants;
import neo.supportapisTest.SupportApiData;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.testng.Assert;
import org.testng.annotations.*;
import api.neobank.transaction.AuthoriseTransaction;
import pojos.neobank.transaction.AuthorizetransactionPojo;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static neo.transaction.TransactionData.OFLINE_TRANSACTION_LIMIT_EXCEED_ERRORMSG;

@Slf4j
public class AuthorizeTransactionTests extends BaseTestNeo {
    public AuthorizeTransactionTests() throws Exception {
        super();
    }
    AuthoriseTransaction authoriseTransaction=new AuthoriseTransaction();

    private String []listOfBlocketMerchant=null;
    private String []listOfBlocketMcc=null;
    private String []listOfWhiteListedMerchant=null;
    private String rrn=new String();
    private String treminalId=new String();

    @BeforeClass(alwaysRun = true)
    public void transactiontestPrerequisites()
    {
        listOfBlocketMerchant=TransactionData.getDefaultValueFromGlobalDefault(TransactionData.GET_BLOCKED_MERCHANT);
        listOfBlocketMcc=TransactionData.getDefaultValueFromGlobalDefault(TransactionData.GET_BLOCKED_MCC);
        listOfWhiteListedMerchant=TransactionData.getDefaultValueFromGlobalDefault(TransactionData.GET_WHITELISTED_MERCHANT);
    }
    @BeforeMethod(alwaysRun = true)
    public void SetTransPrequsiteInTest()
    {
        rrn=Long.toString(System.currentTimeMillis());
        treminalId=Long.toString(System.currentTimeMillis());
    }


    @Description("Test To verify transaction amount is honoured in standard iso format provided by yap")
    @Feature("Transaction")
    @Test(priority = 1,groups = { "transaction","Authorise","sanity"},dataProviderClass = TransactionData.class,dataProvider = "test_authTransactionData")
    public void verifyAuthTrasaction(String authParams)
    {
        UUID uniqueKey = UUID.randomUUID();
        String request=new String();
        log.info("TransRef Number"+uniqueKey.toString());
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap <String, String> postTransactionDbData=new HashMap<>();
        HashMap <String, String> lazycardTransactionTableData=new HashMap<>();
        headerDetails.put("encryptResponse","false");
        if (authParams.equalsIgnoreCase("regular"))
            request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).replace("terminalID",treminalId).toString();
        else if (authParams.equalsIgnoreCase("WithSIData"))
            request= new StringTemplate(NeoConstants.AUTH_POS_TRANSACTION).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).replace("terminalID",treminalId).replace("visaSIData",new StringTemplate(NeoConstants.VISASIDATA).toString()).replace("posCode","91").toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        postTransactionDbData=TransactionData.getDebitTransactionData(uniqueKey.toString(),"debit",getPlatformUSerData(TransactionData.USER_MOBILE).getLpUuid());
        lazycardTransactionTableData=TransactionData.getLazyCardTransactionData(uniqueKey.toString());
        Assert.assertEquals(authorizetransactionPojo.description,"Authorized!","error transaction not authorised due to "+authorizetransactionPojo.errorCode);
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.AUTH_SUCCESS_STATUS_CODE);
        lazyPayTransactionTableDBDataAssertions(postTransactionDbData,uniqueKey.toString());
        lazyCardTransactionDBDataAssertions(lazycardTransactionTableData);
        Assert.assertEquals(postTransactionDbData.get("id"),lazycardTransactionTableData.get("lazypayTransactionId"),"Lazypay transaction id doesnot match");
        Assert.assertEquals(getAppTransactionData(TransactionData.TEST_USER_UUID).get(0).getId(),postTransactionDbData.get("id"),"transaction not present in ledger");
        Assert.assertEquals(postTransactionDbData.get("amount"),lazycardTransactionTableData.get("LazycardAmount"),"transaction amount missmatch between lazypay and lazycard transaction table");
        Assert.assertEquals(lazycardTransactionTableData.get("mcc"),TransactionData.TEST_MCC,"MCC not getting stored");


    }
    @Description("Test To verify transaction amount is honoured in standard iso format provided by yap and throws error when transaction amount is not in iso format")
    @Feature("Transaction")
    @Test(priority = 2,groups = { "transaction","Authorise"})
    public void apiShouldThrowErrorForNonIsoFormatAmount()
    {

        UUID uniqueKey = UUID.randomUUID();
        String NonIsoFormatTransactingAmount="00000000010";
        log.info(uniqueKey.toString());
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",NonIsoFormatTransactingAmount).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(authorizetransactionPojo.description,null,"error transaction not authorised due to "+authorizetransactionPojo.errorCode);
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.SUSPECTED_FRAUD_ERROR_CODE);
        Assert.assertEquals(authorizetransactionPojo.errorCode,"Wrong amount format!","transaction getting approved for amount in non iso format ->>>reason"+authorizetransactionPojo.description);
    }
    @Description("If a transaction request comes for a blocked merchant ,then authorise api should not allow the transaction")
    @Feature("Transaction")
    @Test(priority =3 ,groups = { "transaction","Authorise"})
    public void transactionNotApprovedForBlockedMerchant()
    {
        UUID uniqueKey = UUID.randomUUID();
        String IsoFormatTransactingAmount="000000000100";
        log.info("Blocked Merchant->>"+listOfBlocketMerchant[0]);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",IsoFormatTransactingAmount).replace("merchantId",listOfBlocketMerchant[0]).replace("terminalID",treminalId).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(authorizetransactionPojo.description,null,"error transaction not authorised due to "+authorizetransactionPojo.errorCode);
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.AUTH_BLOCKEDMERCHANT_STATUS_CODE);
        Assert.assertEquals(authorizetransactionPojo.errorCode,TransactionData.AUTH_BLOCKEDMERCHANT_ERROR_MSG,"transaction getting approved for blocked merchant ->>>reason"+authorizetransactionPojo.description);
    }
    @Description("If a transaction request comes for a blocked mcc ,then authorise api should not allow the transaction")
    @Feature("Transaction")
    @Test(priority = 4,groups = { "transaction","Authorise"})
    public void transactionNotApprovedForBlockedMcc()
    {
        UUID uniqueKey = UUID.randomUUID();
        String IsoFormatTransactingAmount="000000000100";
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("trxId",uniqueKey.toString()+1).replace("terminalID",treminalId).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",listOfBlocketMcc[0]).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(authorizetransactionPojo.description,null,"error transaction not authorised due to "+authorizetransactionPojo.errorCode);
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.AUTH_BLOCKEDMERCHANT_STATUS_CODE);
        Assert.assertEquals(authorizetransactionPojo.errorCode,TransactionData.AUTH_BLOCKEDMERCHANT_ERROR_MSG,"transaction getting approved for blocked merchant ->>>reason"+authorizetransactionPojo.description);
    }
    @Description("If a transaction request comes for a blocked mcc ,then authorise api should not allow the transaction")
    @Feature("Transaction")
    @Test(priority = 5,groups = { "transaction","Authorise"})
    public void allowTransactionForWhiteListedMearchant()
    {
        UUID uniqueKey = UUID.randomUUID();
        String IsoFormatTransactingAmount="000000000100";
        log.info("Blocked Merchant->>"+listOfBlocketMerchant[0]);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("terminalID",treminalId).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",IsoFormatTransactingAmount).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc","6011").replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(authorizetransactionPojo.description,"Authorized!","error transaction not authorised due to "+authorizetransactionPojo.errorCode);
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.AUTH_SUCCESS_STATUS_CODE);
    }

    @Description("If a transaction request comes for a blocked user or if card is temp locked ,then authorise api should not allow the transaction")
    @Feature("Transaction")
    @Test(priority = 6,groups = { "transaction","Authorise","sanity"})
    public void transactionTestForLockedCards()
    {
        UUID uniqueKey = UUID.randomUUID();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        blockUnblockCard(TransactionData.TEST_USER_UUID, SupportApiData.LOCK_CARD_FLAG);
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("terminalID",treminalId).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId("5837325")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(authorizetransactionPojo.errorCode,TransactionData.INACTIVE_ERROR_MSG,"error transaction  authorised for locked card "+authorizetransactionPojo.description);
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.INACTIVE_ERROR_CODE);
    }

    @Description("If a transaction request comes for a blocked user then authorise api should not allow the transaction")
    @Feature("Transaction")
    @Test(priority = 9,groups = { "transaction","Authorise"})
    public void transactionTestForBlockedUsers()
    {
        UUID uniqueKey = UUID.randomUUID();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        SupportApiData.blockUnBlockLazyPayUser(TransactionData.TEST_USER_UUID,"block");
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("terminalID",treminalId).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId("5837325")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.INACTIVE_ERROR_CODE,"error transaction  authorised for locked card "+authorizetransactionPojo.errorCode);

    }
    @Description("Lazycard transaction should fail if user tries to do a transaction which is greater then the current available credit limit ")
    @Test(priority = 7,groups = {"transaction","sanity"})
    public void tarnsactionShouldFailIfDebitAmountIsMoreThanAvailableLimit() throws Exception {
        UUID uniqueKey = UUID.randomUUID();
        HashMap<String,String>lazycardUserData=new HashMap<>(TransactionData.getLazycardData(getPlatformUSerData(TransactionData.LAZYCARD_LOW_LIMIT_USER).getLpUuid()));
        setuserTransactionLimit("3000","3000",lazycardUserData.get("lazypayUuid"));
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap <String, String> lazycardTransactionTableData=new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount","000000300000").replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",lazycardUserData.get("entityId")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",lazycardUserData.get("kitNumber")).replace("terminalID",treminalId).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        lazycardTransactionTableData=TransactionData.getLazyCardTransactionData(uniqueKey.toString());
        Assert.assertEquals(authorizetransactionPojo.errorCode,TransactionData.EXCEEDS_LIMIT_ERRORMSG,"Exceed limit eror msg incorrect");
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.EXCEEDS_LIMIT_ERRORCODE,"Exceed limit eror code incorrect");
        Assert.assertEquals(lazycardTransactionTableData.get("LazycardStatus"),TransactionData.EXCEEDS_LIMIT_TRANSACTION_STATUS);
    }
    @Description("Transaction should fail if the lazycard transaction amount is > the user's online/offline transaction amount")
    @Test(priority = 8,groups = {"transaction","sanity"},dataProviderClass = TransactionData.class,dataProvider = "test_transactionLimit")
    public void transactionShouldFailIfAmountExceedsSetTransactionLimit(String transactionType,String aiKey) throws Exception {
        UUID uniqueKey = UUID.randomUUID();
        String request=new String();
        HashMap<String,String>lazycardUserData=new HashMap<>(TransactionData.getLazycardData(getPlatformUSerData(TransactionData.LAZYCARD_LOW_LIMIT_USER).getLpUuid()));
        setuserTransactionLimit("1000","1000",lazycardUserData.get("lazypayUuid"));
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap <String, String> lazycardTransactionTableData=new HashMap<>();
        headerDetails.put("encryptResponse","false");
        if (transactionType.equalsIgnoreCase("offline"))
            request= new StringTemplate(NeoConstants.DEBIT_TRANSACTION).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount","000000250000").replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",lazycardUserData.get("entityId")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",lazycardUserData.get("kitNumber")).replace("terminalID",treminalId).replace("channel","POS").replace("transactionType","POS").toString();
        else if (transactionType.equalsIgnoreCase("online"))
            request= new StringTemplate(NeoConstants.DEBIT_TRANSACTION).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount","000000250000").replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",lazycardUserData.get("entityId")).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",lazycardUserData.get("kitNumber")).replace("terminalID",treminalId).replace("channel","ECOM").replace("transactionType","ECOM").toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        lazycardTransactionTableData=TransactionData.getLazyCardTransactionData(uniqueKey.toString());
        if (transactionType.equalsIgnoreCase("offline"))
            Assert.assertEquals(OFLINE_TRANSACTION_LIMIT_EXCEED_ERRORMSG,authorizetransactionPojo.errorCode);
        else
            Assert.assertEquals(TransactionData.ONLINE_TRANSACTION_LIMIT_EXCEED_ERRORMSG,authorizetransactionPojo.errorCode);
        Assert.assertEquals(lazycardTransactionTableData.get("LazycardStatus"),TransactionData.SET_LIMIT_TRANSACTION_ERROE_STATUS);
        Assert.assertEquals(authorizetransactionPojo.status,TransactionData.EXCEEDS_LIMIT_ERRORCODE,"Exceed limit eror code incorrect");
    }

    @Test(dataProviderClass =TransactionData.class,dataProvider = "posEntryMode")
    public void verifyPosEntryMode(String posEntryMode,String code,String message)
    {
        UUID uniqueKey = UUID.randomUUID();

        log.info("TransRef Number"+uniqueKey.toString());
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap <String, String> lazycardTransactionTableData=new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.AUTH_POS_TRANSACTION).replace("trxId",uniqueKey.toString()+1).replace("retrievalRefNo",rrn).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).replace("terminalID",treminalId).replace("visaSIData",new StringTemplate(NeoConstants.VISASIDATA).toString()).replace("posCode",code).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        lazycardTransactionTableData=TransactionData.getLazyCardTransactionData(uniqueKey.toString());
        Assert.assertEquals(authorizetransactionPojo.description,"Authorized!","error transaction not authorised due to "+authorizetransactionPojo.errorCode);
        Assert.assertEquals(lazycardTransactionTableData.get("posEntryMode"),posEntryMode,message);
        Assert.assertEquals(lazycardTransactionTableData.get("vsc"),"N");
        Assert.assertTrue(lazycardTransactionTableData.get("visaSIData")!=null);
        if (posEntryMode.equalsIgnoreCase("CONTACTLESS_VSDC")||posEntryMode.equalsIgnoreCase("CONTACTLESS_MAGNETIC"))
            Assert.assertEquals(lazycardTransactionTableData.get("tapNpay"),"1",message);
    }

    @AfterClass(alwaysRun = true)
    public void resetData() {
        try {
            SupportApiData.blockUnBlockLazyPayUser(TransactionData.TEST_USER_UUID,"unblock");
            blockUnblockCard(TransactionData.TEST_USER_UUID, SupportApiData.UNLOCK_CARD_FLAG);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }
    public void lazyCardTransactionDBDataAssertions(HashMap dbData)
    {
        dbData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        Assert.assertEquals(dbData.get("LazycardStatus"),TransactionData.TRANSACTION_DB_SUCCESS_STATUS,"TRANSACTION DB DATA MISSMATCH->"+dbData.get("status"));
        Assert.assertEquals(dbData.get("rrn"),rrn);
        Assert.assertEquals(dbData.get("kitNumber"),TransactionData.getKitNumber(TransactionData.TEST_USER_UUID));

    }
    public void lazyPayTransactionTableDBDataAssertions(HashMap postTransactionDbData,String txnRefNo)
    {
        postTransactionDbData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        Assert.assertEquals(postTransactionDbData.get("amount"),TransactionData.AMOUNT);
        Assert.assertEquals(postTransactionDbData.get("status"),TransactionData.TRANSACTION_SUCCESS_STATUS);
        Assert.assertEquals(postTransactionDbData.get("merchantTxnId"),txnRefNo);
        Assert.assertEquals(postTransactionDbData.get("txnSource"),TransactionData.TRANSACTION_SOURCE);
    }


}
