package neo.transaction;

import dbUtils.Lazypay_MySQL_DBAccessObject;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import dbUtils.Lazycard_MySQL_DBAccessObject;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Slf4j
public class TransactionData {

    /**
     * DB QUERY
     */
    private static final String GET_KIT_NUMBER="SELECT kitNumber FROM lazycards l WHERE lazypayUuid ='$' and status='ACTIVE';";
    private static final String GET_USER_ENTITY_ID="SELECT entityId FROM lazycardUserEntityMappings luem WHERE lazypayUuid ='$';";
    public static final String GET_BLOCKED_MERCHANT="SELECT defaultValue FROM  globalDefaults gd WHERE gd .defaultKey ='merchantBlacklist';";
    public static final String GET_BLOCKED_MCC="SELECT defaultValue FROM  globalDefaults gd WHERE gd .defaultKey ='blockedMcc';";
    public static final String GET_WHITELISTED_MERCHANT="SELECT defaultValue FROM  globalDefaults gd WHERE gd .defaultKey ='mccWhitelist6011';";
    public static final String GET_DEBIT_TRANSACTION_DATA="select * from transactions t WHERE debit =$1 and merchantTxnId ='$2'";
    public static final String GET_CREDIT_TRANSACTION_DATA="select * from transactions t WHERE credit =$1 and merchantTxnId ='$2'";
    public static final String GET_REPAY_TRANSACTION_DATA="select * from transactions t WHERE credit =$1";
    public static final String GET_LAZYCARD_TRANSACTION_DATA="select * FROM lazycardTransactions lt WHERE externalTransactionId ='$';";
    public static final String GET_SURCHARGE_WAIVED_MCC="SELECT defaultValue FROM  globalDefaults gd WHERE gd .defaultKey ='surchargeWaiverMcc';";
    public static final String GET_LP_SURCHARGE_PROCESSSED_TRANSACTION_DATA="select * from transactions t WHERE t.merchantTxnId = 'SUR_REQID_$1' and `type`='SURCHARGE';";
    public static final String GET_LP_SURCHARGE_WAIVED_TRANSACTION_DATA="select * from transactions t WHERE t.merchantTxnId = 'SUR_REQID_$1' and `type`='SURCHARGE_WAIVER';";
    public static final String GET_LAZYCARD_DEBIT_TRANSACTION_DATA="SELECT * from lazycardTransactions lt WHERE lt .externalTransactionId='$1' AND status='$2';";
    public static final String GET_SURCHARGE_WAIVEDOFF_AMOUNT="SELECT * FROM lazycardSurchargeWaived lsw WHERE lsw .lazypayUuid ='$';";
    public static final String GET_LAZYCARD_DETAILS="select l.kitNumber ,luem.entityId ,l.lazypayUuid,l.offlineTxnLimit,l.onlineTransactionEnabled ,l.onlineTxnLimit,l.formFactor,l.contactLessTransactionEnabled,l.freezeStatus,l.physicalCardRequested,l.cardType FROM  lazycardUserEntityMappings luem  INNER JOIN lazycards l ON l.lazypayUuid =luem .lazypayUuid WHERE luem .lazypayUuid IN ($1);";
    public static final String GET_PENDING_REVERSAL_DATA="SELECT * FROM pending_reversals pr WHERE pr.external_transaction_id ='$';";
    public static final String GET_WAVIER_ADJUSTMENT_DATA="SELECT * FROM surcharge_waiver_adjustments sw WHERE sw.waiver_txn_id ='$';";
    public static final String GET_SURCHARGE_REFUND_DATA="SELECT * FROM surcharge_refunds sr WHERE sr.surcharge_txn_id ='$';";
    public static final String GET_LAZYCARD_REFUND_BY_ID="select * FROM lazycardTransactions lt WHERE lazyCardParentTxnId ='$';";








    /**
     * TEST DATA
     */
    public static final String AUTH_BLOCKEDMERCHANT_ERROR_MSG="Transaction declined due to high risk";
    public static final String AUTH_BLOCKEDMERCHANT_STATUS_CODE="74";
    public static final String REFUND_DUPLICATE_ENTRY_ERROR_MSG="Error cancelling transaction!";
    public static final String AUTH_SUCCESS_STATUS_CODE="00";
    public static final String TEST_TRANSACTION_AMOUNT ="000000010000";
    public static final String TEST_TRANSACTION_MERCHANT="swatTest1";
    public static final String TEST_USER_UUID="12603343";
    public static final String TEST_MCC="6221";
    public static final String TEST_TRANSACTION_MERCHANT1="swatTest";
    public static final String TRANSACTION_ERROR_CODE_14="14";
    public static final String ISO_REFUND_AMOUNT ="000000005000";
    public static final String REFUND_AMOUNT2="000000005000";
    public static final String INVALID_REFUND_AMOUNT="000000006000";
    public static final String WRONG_AMOUNT_FORMAT_ERROR="Wrong amount format!";
    public static final String SUSPECTED_FRAUD_ERROR_CODE="34";
    public static final String WRONG_AMOUNT_FORMAT="00000000100";
    public static final String AMOUNT="100.0";
    public static final String TRANSACTION_SUCCESS_STATUS="SUCCESS";
    public static final String TRANSACTION_SOURCE="LAZYCARD";
    public static final String REPAY_TRANSACTION_SOURCE="REPAY_PARTNER";
    public static final String REFUND_TRANSACTION_NAME="REFUND";
    public static final String REPAYMENT_TRANSACTION_NAME="REPAY";
    public static final String REFUND_AMOUNT="50.0";
    public static final String CREDIT_AMOUNT_SUCCESS_DESCRIPTION="Amount credited.";
    public static final String INACTIVE_ERROR_CODE="05";
    public static final String INACTIVE_ERROR_MSG="Card is locked!";
    public static final String TRANSACTION_DB_SUCCESS_STATUS="APPROVED";
    public static final String TRANSACTION_DB_RRN="1404204555";
    public static final String ERROR_CREATING_TRANSACTION="Error creating transaction!";
    public static final String USER_MOBILE="6100000169";
    public static final String SURCHARGE_USER_MOBILE="6100000308";
    public static final String CARD_ACTION_USER="6100000307";
    public static final String TEST_TRANSACTION_MERCHNAT_NAME="Kuru";
    public static final String REFUND_TRANSACTION_NAME_WITHOUT_PARENT="CREDIT";
    public static final String SURCHARGE_SUCCESS_DESCRIPTION="Surcharge processed";
    public static final String SURCHARE_PROCESSED_TRANSACTION_TYPE="SURCHARGE";
    public static final String SURCHARE_WAIVED_TRANSACTION_TYPE="SURCHARGE_WAIVER";
    public static final String LAZYCARD_LOW_LIMIT_USER="6100000142";
    public static final String EXCEEDS_LIMIT_ERRORMSG="Exceeds withdrawal amount limit";
    public static final String EXCEEDS_LIMIT_ERRORCODE="61";
    public static final String EXCEEDS_LIMIT_TRANSACTION_STATUS="LP_EXCEEDS_USER_MAX_TXN_LIMIT";
    public static final String OFLINE_TRANSACTION_LIMIT_EXCEED_ERRORMSG="Offline transaction limit set by user exceeded!";
    public static final String ONLINE_TRANSACTION_LIMIT_EXCEED_ERRORMSG="Online transaction limit set by user exceeded!";
    public static final String SET_LIMIT_TRANSACTION_ERROE_STATUS="WITHDRAW_LIMIT_EXCEEDS";
    public static final String REVERSAL_BEFORE_AUTH_STATUS_CODE="14";
    public static final String REVERSAL_BEFORE_AUTH_MSG="Invalid transaction reference!";


    /***
     *
     * @param uuid
     * @return
     */
    @SneakyThrows
    @Step
    public  static String getKitNumber(String uuid)
    {

        String s=null;
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_KIT_NUMBER.replace("$",uuid));
        while (rs.next())
            s=rs.getString("kitNumber");
        return s;
    }

    /***
     *
     * @param uuid
     * @return
     */
    @SneakyThrows
    @Step
    public  static String getGetUserEntityId(String uuid)
    {

        String s=null;
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_USER_ENTITY_ID.replace("$",uuid));
        while (rs.next())
            s=rs.getString("entityId");
        return s;
    }

    /***
     *
     * @param Query
     * @return
     */
    @SneakyThrows
    @Step
    public  static String[] getDefaultValueFromGlobalDefault(String Query)
    {

        String s=null;
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(Query);
        while (rs.next()){
            s=rs.getString("defaultValue");}
        System.out.println(s+"sdfghjk1");

        return s.split(",");
    }

    /**
     *
     * @param transactionRef
     * @param transactionType
     * @return
     */
    @SneakyThrows
    @Step
    public  static HashMap<String, String> getDebitTransactionData(String transactionRef ,String transactionType,String lpUuid)
    {
        HashMap <String ,String>transactionDetails=new HashMap();
        String s=null;
        String query=new String();
        if (transactionType.equalsIgnoreCase("debit"))
            query=GET_DEBIT_TRANSACTION_DATA.replace("$1",lpUuid).replace("$2",transactionRef);
        else if(transactionType.equalsIgnoreCase("credit"))
            query=GET_CREDIT_TRANSACTION_DATA.replace("$1",lpUuid).replace("$2",transactionRef);
        else if (transactionType.equalsIgnoreCase("repay"))
            query=GET_REPAY_TRANSACTION_DATA.replace("$1",lpUuid);
        else if (transactionType.equalsIgnoreCase("surcharge_processed"))
            query=GET_LP_SURCHARGE_PROCESSSED_TRANSACTION_DATA.replace("$1",transactionRef);
        else if (transactionType.equalsIgnoreCase("surcharge_waived"))
            query=GET_LP_SURCHARGE_WAIVED_TRANSACTION_DATA.replace("$1",transactionRef);


        log.info("Query for getDebitTransactionData --->"+query);
        ResultSet rs= Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(query);
        while (rs.next()) {
            s = rs.getString("amount");
            transactionDetails.put("amount", s);
            System.out.println("amount:"+s);
            s = rs.getString("merchantTxnId");
            transactionDetails.put("merchantTxnId", s);
            s = rs.getString("status");
            transactionDetails.put("status", s);
            s = rs.getString("txnSource");
            transactionDetails.put("txnSource", s);
            s = rs.getString("type");
            transactionDetails.put("type", s);
            s = rs.getString("reason");
            transactionDetails.put("reason", s);
            s = rs.getString("parentTxnId");
            transactionDetails.put("parentTxnId", s);
            s = rs.getString("repayType");
            transactionDetails.put("repayType", s);
            s = rs.getString("id");
            transactionDetails.put("id", s);

        }
        transactionDetails.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return transactionDetails;
    }

    @DataProvider(name="refund_fallback")
    public Object[][] refund_fallback(){
        return new Object[][]
                {
                        { "ValidParent_orginalRrn","DIGI-01"},
                        { "ValidParent_ApprovalCode","DIGI-02"},
                        {"NoValidParent_WithoutMerchant","DIGI-03"},
                        {"NoValidParent_WithMerchant","DIGI-04"}

                };}

    @DataProvider(name="cardLockedOrUserBlocked")
    public Object[][] cardLockedOrUserBlocked(){
        return new Object[][]
                {
                        { "locked"},
                        { "LazyPayUserBlocked"},

                };}

    /***
     *
     * @param txnRefNo
     * @return
     */
    @SneakyThrows
    @Step
    public  static HashMap<String, String> getLazyCardTransactionData(String txnRefNo)
    {
        HashMap <String ,String>transactionDetails=new HashMap();
        String s=null;
        String query=GET_LAZYCARD_TRANSACTION_DATA.replace("$",txnRefNo);

        log.info("Query for get transaction data from lazycard transaction table --->"+query);
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(query);
        while (rs.next()) {
            s = rs.getString("kitNumber");
            transactionDetails.put("kitNumber", s);
            s = rs.getString("rrn");
            transactionDetails.put("rrn", s);
            s = rs.getString("status");
            transactionDetails.put("LazycardStatus", s);
            s = rs.getString("reason");
            transactionDetails.put("LazycardReason", s);
            s = rs.getString("lazypayTransactionId");
            transactionDetails.put("lazypayTransactionId", s);
            s = rs.getString("amount");
            transactionDetails.put("LazycardAmount", s);
            s = rs.getString("mcc");
            transactionDetails.put("mcc", s);
            s = rs.getString("lazyCardParentTxnId");
            transactionDetails.put("lazyCardParentTxnId", s);
            s = rs.getString("posEntryMode");
            transactionDetails.put("posEntryMode", s);
            s = rs.getString("id");
            transactionDetails.put("lazycardTransactionId", s);
            s = rs.getString("posEntryMode");
            transactionDetails.put("posEntryMode", s);
            s = rs.getString("tapNpay");
            transactionDetails.put("tapNpay", s);
            s = rs.getString("visaSIData");
            transactionDetails.put("visaSIData", s);
            s = rs.getString("vsc");
            transactionDetails.put("vsc", s);
            s = rs.getString("terminalID");
            transactionDetails.put("terminalID", s);

        }
        transactionDetails.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return transactionDetails;
    }

    /**
     *
     * @return
     */
    @DataProvider(name="surcharge_cases")
    public Object[][] surcharge(){
        return new Object[][]
                {
                        { "Surcharge_waived_MCC","DIGI-TC-369"},
                        //{ "Surcharge_non_waived_MCC","DIGI-TC-370"},
//                        {"DIGI-TC-373","DIGI-TC-373"},
//                        {"DIGI-TC-374","DIGI-TC-374"},
//                        {"DIGI-TC-376","DIGI-TC-376"},
//                        {"DIGI-TC-377","DIGI-TC-377"}

                };}

    /**
     *
     * @param transactionRef
     * @param transactionType
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public  static HashMap<String, String> getLazycardTransactionData(String transactionRef ,String transactionType) throws SQLException, ClassNotFoundException {
        HashMap <String ,String>transactionDetails=new HashMap();
        String s=null;
        String query=new String();
        query=GET_LAZYCARD_DEBIT_TRANSACTION_DATA.replace("$1",transactionRef).replace("$2",transactionType);
        log.info("Query for getDebitTransactionData --->"+query);
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(query);
        while (rs.next()) {
            s = rs.getString("lazypayTransactionId");
            transactionDetails.put("lazypayTransactionId", s);
            s = rs.getString("kitNumber");
            transactionDetails.put("kitNumber", s);
            s = rs.getString("isSurcharge");
            transactionDetails.put("isSurcharge", s);
            s = rs.getString("merchantName");
            transactionDetails.put("merchantName", s);
            s = rs.getString("amount");
            transactionDetails.put("LazyCardAmount", s);
            s = rs.getString("mcc");
            transactionDetails.put("mcc", s);
            s = rs.getString("lazyCardParentTxnId");
            transactionDetails.put("lazyCardParentTxnId", s);
            s = rs.getString("id");
            transactionDetails.put("lazycardTransactionId", s);
            s = rs.getString("posEntryMode");
            transactionDetails.put("posEntryMode", s);
            s = rs.getString("tapNpay");
            transactionDetails.put("tapNpay", s);
            s = rs.getString("visaSIData");
            transactionDetails.put("visaSIData", s);
            s = rs.getString("vsc");
            transactionDetails.put("vsc", s);
            s = rs.getString("terminalID");
            transactionDetails.put("terminalID", s);

        }
        transactionDetails.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return transactionDetails;
    }

    /***
     *
     * @param uuid
     * @return
     */
    public static float getSurcharegeWaivedOfAmount(String uuid)
    {
        String query=GET_SURCHARGE_WAIVEDOFF_AMOUNT.replace("$",uuid);
        log.info("Query for Get Surcharge --->"+query);
        String surcharge_Amount=null;
        try {
            ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(query);
            while (rs.next()) {
                surcharge_Amount = rs.getString("waivedOffAmt");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(surcharge_Amount);
    }

    /***
     *
     * @param lpUuid
     * @return
     */
    public static HashMap<String,String> getLazycardData(String lpUuid)
    {
        HashMap <String ,String>lazycardUserData=new HashMap();
        String s=null;
        String query=new String();
        query=GET_LAZYCARD_DETAILS.replace("$1",lpUuid);
        log.info("Query for getDebitTransactionData --->"+query);
        try{
            ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(query);
            while (rs.next()) {
                s = rs.getString("kitNumber");
                lazycardUserData.put("kitNumber", s);
                s = rs.getString("entityId");
                lazycardUserData.put("entityId", s);
                s = rs.getString("lazypayUuid");
                lazycardUserData.put("lazypayUuid", s);
                s = rs.getString("offlineTxnLimit");
                lazycardUserData.put("offlineTxnLimit", s);
                s = rs.getString("onlineTransactionEnabled");
                lazycardUserData.put("onlineTransactionEnabled", s);
                s = rs.getString("onlineTxnLimit");
                lazycardUserData.put("onlineTxnLimit", s);
                s = rs.getString("formFactor");
                lazycardUserData.put("formFactor", s);
                s = rs.getString("contactLessTransactionEnabled");
                lazycardUserData.put("contactLessTransactionEnabled", s);
                s = rs.getString("freezeStatus");
                lazycardUserData.put("freezeStatus", s);
                s = rs.getString("physicalCardRequested");
                lazycardUserData.put("physicalCardRequested", s);
                s = rs.getString("cardType");
                lazycardUserData.put("cardType", s);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        lazycardUserData.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return lazycardUserData;
    }
    @DataProvider(name="test_transactionLimit")
    public Object[][] transactionLimitType(){
        return new Object[][]
                {
                        { "online","DIGI-TC"},
                        { "online","DIGI-TC"}
                };}

    @DataProvider(name="test_authTransactionData")
    public Object[][] authParams(){
        return new Object[][]
                {
                        //  { "regular"},
                        { "WithSIData"}
                };}
    @DataProvider(name="posEntryMode")
    public Object[][] postransactionEntryMode(){
        return new Object[][]
                {
                        { "UNKNOWN","00","unknown"},
                        { "MANUAL_KEY_ENTRY","01","Manual key entry"},
                        { "MAGNETIC_READ_VISANET","02","Magnetic stripe read for VisaNet"},
                        { "OPTICAL_CODE","03","QR transactions"},
                        { "INTEGRATED_CIRCUIT_READ","05","Integrated circuit card read"},
                        { "CONTACTLESS_VSDC","07","Contactless device-read-originated using VSDC chip"},
                        { "CREDENTIAL_FILE","10","Credential stored file"},
                        { "MAGNETIC_READ","90","Magnetic stripe read"},
                        { "CONTACTLESS_MAGNETIC","91","Contactless device-read-originated using magnetic stripe"},
                        { "INTEGRATED_CIRCUIT_READ_VISANET","95","Integrated circuit card read for VisaNet"},
                        { "STORED_VALUE","96","Stored value from pre-registered checkout service"}

                };}

    /***
     *
     * @param ExtTransactionId
     * @return
     */
    public static boolean getreversalPendingTransactionData(String ExtTransactionId)
    {

        String query=GET_PENDING_REVERSAL_DATA.replace("$",ExtTransactionId);
        log.info("Query for Get REVERSAL --->"+query);
        String processed=null;
        Boolean isprocessed=false;
        try {
            ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(query);
            while (rs.next()) {
                processed = rs.getString("processed");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (processed=="1")
            isprocessed=true;

        return isprocessed ;

    }

    /***
     *
     * @param wavierId
     * @return
     */
    public static HashMap<String,String> getWavierAdjustments(String wavierId)
    {
        HashMap <String ,String>WavierAdjustments=new HashMap();
        String s=null;
        String query=new String();
        query=GET_WAVIER_ADJUSTMENT_DATA.replace("$",wavierId);
        log.info("Query for WavierAdjustments --->"+query);
        try{
            ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(query);
            while (rs.next()) {
                s = rs.getString("amount");
                WavierAdjustments.put("amount", s);
                s = rs.getString("merchant_txn_id");
                WavierAdjustments.put("merchant_txn_id", s);
                s = rs.getString("settled");
                WavierAdjustments.put("settled", s);
                s = rs.getString("waiver_txn_id");
                WavierAdjustments.put("waiver_txn_id", s);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        WavierAdjustments.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return WavierAdjustments;
    }

    /**
     *
     * @param surchargeProcessedId
     * @return
     */
    public static HashMap<String,String> getRefundedSurcharge(String surchargeProcessedId)
    {
        HashMap <String ,String>surchargeRefund=new HashMap();
        String s=null;
        String query=new String();
        query=GET_SURCHARGE_REFUND_DATA.replace("$",surchargeProcessedId);
        log.info("Query for GET_SURCHARGE_REFUND_DATA --->"+query);
        try{
            ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(query);
            while (rs.next()) {
                s = rs.getString("amount");
                surchargeRefund.put("amount", s);
                s = rs.getString("merchant_txn_id");
                surchargeRefund.put("merchant_txn_id", s);
                s = rs.getString("processed");
                surchargeRefund.put("processed", s);
                s = rs.getString("refund_method");
                surchargeRefund.put("refund_method", s);
                s = rs.getString("status");
                surchargeRefund.put("status", s);
                s = rs.getString("refund_txn_id");
                surchargeRefund.put("refund_txn_id", s);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        surchargeRefund.forEach((k,v) -> log.info("Key = "
                + k + ", Value = " + v));
        return surchargeRefund;
    }
    public static String getRefundAmountByParentId(String parentTransactionId) {
        String s = null;
        String query = new String();
        query = GET_LAZYCARD_REFUND_BY_ID.replace("$", parentTransactionId);
        log.info("Query for get refund amount by ParentTransactionId --->" + query);
        try {
            ResultSet rs = Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(query);
            while (rs.next()) {
                s = rs.getString("amount");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }

        @DataProvider(name="refundType")
    public Object[][] surcharge_refund(){
        return new Object[][]
                {
                        {"withWaiver" },
                        {"WithoutWaiver" }


                };}
}
