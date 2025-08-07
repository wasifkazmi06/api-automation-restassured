package neo;
import api.lazypay.transaction.GetApptransactionHistory;
import api.neobank.cardControl.GetTrnsactionLimit;
import api.neobank.cardControl.SetTransactionLimit;
import api.neobank.supportapis.CardControl;
import api.neobank.transaction.Webhook;
import api.platform.GetUserData;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import neo.transaction.TransactionData;
import org.testng.Assert;
import api.neobank.transaction.AuthoriseTransaction;
import pojos.lazypay.transactionFlow.getTransactionHistory.GetAppTransactionhistory;
import pojos.lazypay.transactionFlow.getTransactionHistory.Transaction;
import pojos.neobank.cardControl.GetUserTransactionLimitPojo;
import pojos.neobank.cardControl.SetTransactionLimitPojo;
import pojos.neobank.support.CardControlPojo;
import pojos.neobank.transaction.AuthorizetransactionPojo;
import pojos.platform.getUserData.UserData;
import util.StringTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Slf4j
public class BaseTestNeo {
    public BaseTestNeo() throws Exception {
        super();
    }

    AuthoriseTransaction authoriseTransaction=new AuthoriseTransaction();
    GetApptransactionHistory getApptransactionHistory=new GetApptransactionHistory();
    Webhook webhook=new Webhook();
    CardControl cardControl=new CardControl();
    GetUserData getUserData=new GetUserData();
    public Map getTransactionData(String amount,String number)
    {
        UUID uniqueKey = UUID.randomUUID();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        HashMap<String, String> transactionData = new HashMap<>();
        String rrn=Long.toString(System.currentTimeMillis());
        String terminalId=Long.toString(System.currentTimeMillis());
        transactionData.put("rrn",rrn);
        transactionData.put("terminalID",terminalId);
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("trxId",uniqueKey.toString()+1).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(getPlatformUSerData(number).getLpUuid())).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(getPlatformUSerData(number).getLpUuid())).replace("retrievalRefNo",rrn).replace("terminalID","terminalId").toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(authorizetransactionPojo.description,"Authorized!","error transaction not authorised due to "+authorizetransactionPojo.errorCode);
        transactionData.put("txnRefNo",authorizetransactionPojo.txnRefNo);
        transactionData.putAll(TransactionData.getDebitTransactionData(authorizetransactionPojo.txnRefNo, "debit",getPlatformUSerData(number).getLpUuid()));
        transactionData.putAll(TransactionData.getLazyCardTransactionData(authorizetransactionPojo.txnRefNo));
        transactionData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return transactionData;
    }
    public boolean blockUnblockCard(String uuid,String flag)
    {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.CARD_CONTROL).replace("flag",flag).replace("uuid",uuid).toString();
        CardControlPojo cardControlPojo = cardControl.post(queryParamDetails, headerDetails, request);
        return cardControlPojo.result.equalsIgnoreCase("success");
    }

    public Map getTransactionApprovalCode(String txnRefNo)
    {
        UUID uniqueKey = UUID.randomUUID();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> callbackReqData = new HashMap<>();
        String orginalRrn=Long.toString(System.currentTimeMillis());
        String ApprovalCode=Long.toString(System.currentTimeMillis())+"1";
        callbackReqData.put("rrn",orginalRrn);
        callbackReqData.put("auth",ApprovalCode);
        String request= new StringTemplate(NeoConstants.YAP_NOTIFICATION).replace("trxId",uniqueKey.toString()+1).replace("txnRefNo",txnRefNo).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(TransactionData.TEST_USER_UUID)).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",TransactionData.TEST_MCC).replace("proxyCardNo",TransactionData.getKitNumber(TransactionData.TEST_USER_UUID)).replace("mobile",TransactionData.USER_MOBILE).replace("authCode",ApprovalCode).replace("retrievalRefNo",orginalRrn).toString();
        Response r=webhook.post1(queryParamDetails, headerDetails,request);
        Assert.assertTrue(r.getStatusCode()==200);
        callbackReqData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return callbackReqData;
    }
    public Map getTransactionData(String amount,String mcc,String number,String uniqueKey)
    {
        //UUID uniqueKey = UUID.randomUUID();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        HashMap<String, String> transactionData = new HashMap<>();
        String rrn=Long.toString(System.currentTimeMillis());
        String terminalId=Long.toString(System.currentTimeMillis());
        transactionData.put("rrn",rrn);
        transactionData.put("terminalID",terminalId);
        transactionData.put("txnId",uniqueKey.toString()+1);
        String request= new StringTemplate(NeoConstants.AUTHORIZE_TRANSACTION_REQUEST).replace("trxId",uniqueKey.toString()+1).replace("txnRefNo",uniqueKey.toString()).replace("transactionAmount",TransactionData.TEST_TRANSACTION_AMOUNT).replace("merchantId",TransactionData.TEST_TRANSACTION_MERCHANT).replace("entityId",TransactionData.getGetUserEntityId(getPlatformUSerData(number).getLpUuid())).replace("merchantName",TransactionData.TEST_TRANSACTION_MERCHANT).replace("mcc",mcc).replace("proxyCardNo",TransactionData.getKitNumber(getPlatformUSerData(number).getLpUuid())).replace("retrievalRefNo",rrn).replace("terminalID",terminalId).toString();
        AuthorizetransactionPojo authorizetransactionPojo = authoriseTransaction.post(queryParamDetails, headerDetails, request);
        //Assert.assertEquals(authorizetransactionPojo.description,"Authorized!","error transaction not authorised due to "+authorizetransactionPojo.errorCode);
        transactionData.put("txnRefNo",authorizetransactionPojo.txnRefNo);
        transactionData.putAll(TransactionData.getDebitTransactionData(authorizetransactionPojo.txnRefNo, "debit",getPlatformUSerData(number).getLpUuid()));
        transactionData.putAll(TransactionData.getLazyCardTransactionData(authorizetransactionPojo.txnRefNo));
        transactionData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return transactionData;

    }
    /**
     * method used to get list of user transaction history
     * @return
     */
    public List<Transaction> getAppTransactionData(String lpUuid)
    {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathParams = new HashMap<>();
        queryParamDetails.put("pageSize", "20");
        queryParamDetails.put("page", "0");
        pathParams.put("module","getlc");
        pathParams.put("uuid",lpUuid);
        GetAppTransactionhistory getAppTransactionhistory=new GetAppTransactionhistory();
        getAppTransactionhistory=getApptransactionHistory.get(queryParamDetails,headerDetails,pathParams);
        return getAppTransactionhistory.getTransactions();
    }
    /**
     *
     * get user data from platform service based on mobile number
     */
    public UserData getPlatformUSerData(String number)
    {
        UserData userData=new UserData();
        HashMap<String, String> headerDetails = new HashMap<>();
        Map<String, Object> queryParamDetails = new HashMap<>();
        queryParamDetails.put("mobile",number);
       return userData=getUserData.get(queryParamDetails,headerDetails);
    }
    /**
     * get user current transaction limit
     */
    public GetUserTransactionLimitPojo getUserTransactionLimit(String lpuuid) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathParams = new HashMap<>();
        GetTrnsactionLimit getTrnsactionLimit = new GetTrnsactionLimit();
        return getTrnsactionLimit.get(queryParamDetails, headerDetails, lpuuid);
    }
    /**
     * set transaction limit
     */
    public SetTransactionLimitPojo setuserTransactionLimit(String offlineAmount, String onlineAmount, String lpuuid) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathParams = new HashMap<>();
        String request = new StringTemplate(NeoConstants.SET_TRANSACTION_LIMIT).replace("offlineTxnLimit", offlineAmount).replace("onlineTxnLimit", onlineAmount).replace("uuid", lpuuid).toString();
        SetTransactionLimit setTransactionLimit = new SetTransactionLimit();
        return setTransactionLimit.post(queryParamDetails, headerDetails, request);
    }

    /**
     * method to convert amount to iso format
     * @param amount
     * @return
     */
    public static String getIsoAmount(Double amount){
        int totalDigit = 12;
        String isoAmount = "";
        String[] splitted = amount.toString().split("\\.");
        if(splitted.length > 1){
            String decimalPoint = splitted[1];
            if(decimalPoint.length() < 2){
                decimalPoint += "0";
            }
            isoAmount += splitted[0] + decimalPoint;
        }else{
            isoAmount += splitted[0] + "00";
        }
        if(isoAmount.length() < totalDigit){
            isoAmount = String.format("%0"+totalDigit+"d", Integer.valueOf(isoAmount));
        }
        return isoAmount;
    }


}
