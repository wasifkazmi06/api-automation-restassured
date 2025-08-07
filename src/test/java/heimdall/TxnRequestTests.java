package heimdall;

import api.heimdall.TxnMonitorCreateTxnRequestData;
import api.heimdall.TxnMonitorFinancialTxnRequestCron;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;


@Slf4j
public class TxnRequestTests extends HeimdallSupportData{


    TxnMonitorCreateTxnRequestData txnMonitorCreateTxnRequestData = new TxnMonitorCreateTxnRequestData();
    TxnMonitorFinancialTxnRequestCron txnMonitorFinancialTxnRequestCron = new TxnMonitorFinancialTxnRequestCron();
      HeimdallTestData testData = new HeimdallTestData();

    public TxnRequestTests() throws Exception {

      }

    Map<String, Object> queryParamDetailsProcessFinancialTxnCron = new HashMap<>();
    String userId;
    String lpUuid;
    String txnId;
    String loanId ;
    String source ;




    private void processFinancialTxnRequestAndValidate(String txnId, Map<String, Object> queryParamDetailsProcessCustomerCron, HashMap<String, String> headerDetails){
        String financialTxnCronResponse = txnMonitorFinancialTxnRequestCron.postWithNoResponseBody(queryParamDetailsProcessCustomerCron,headerDetails);
        log.info("response status code : " + financialTxnCronResponse);
        if(!validateFinancialTxnRequest(txnId,"active","0")){
        Assert.assertTrue(validateFinancialTxnRequest(txnId,"retry_count","1"),"Failed to update the retry_count field in financialTxn request table");
            financialTxnCronResponse = txnMonitorFinancialTxnRequestCron.postWithNoResponseBody(queryParamDetailsProcessCustomerCron,headerDetails);
            financialTxnCronResponse = txnMonitorFinancialTxnRequestCron.postWithNoResponseBody(queryParamDetailsProcessCustomerCron,headerDetails);
            Assert.assertTrue(validateFinancialTxnRequest(txnId,"retry_count","3"),"Failed to update the retry_count field in financialTxn request table");
            financialTxnCronResponse = txnMonitorFinancialTxnRequestCron.postWithNoResponseBody(queryParamDetailsProcessCustomerCron,headerDetails);
            Assert.assertTrue(validateFinancialTxnRequest(txnId,"active","0"),"Failed to update the active field in financialTxn request table");
        }else{
            Assert.assertTrue(validateFinancialTxnRequest(txnId,"active","0"),"Failed to update the active field in financialTxn request table");

        }
    }


    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 1, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "userIdDataForTxnRequest")
    public void useridValidationCases(String userIdDataForTxnRequest) {
        log.info("Getting started with the UUID tests for " + userIdDataForTxnRequest);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (userIdDataForTxnRequest) {
            case "ValidUserId_Lazypay":
                queryParamDetails.replace("userId", testData.ValidUserId_Lazypay);
                userId = testData.ValidUserId_Lazypay;
                source = testData.ValidSource_Lazypay;
                break;
            case "ValidUserId_PS":
                queryParamDetails.replace("userId", testData.ValidUserId_PS);
                queryParamDetails.replace("source", testData.ValidSource_PS);
                queryParamDetails.replace("loanId", "STPLSC01000777740017");
                queryParamDetails.remove("lpUuid");
                loanId = "STPLSC01000777740017";
                source = testData.ValidSource_PS;
                break;
            case "UserId_With_NOCustomerEntry":
                queryParamDetails.replace("userId", "135426576871");
                break;
            case "InvalidUserId_Lazypay":
                queryParamDetails.replace("userId", testData.InvalidUserId_Lazypay);
                userId = testData.InvalidUserId_Lazypay;
                break;
            case "WithEmptyUserId":
                queryParamDetails.replace("userId", "");
                userId = "";
                break;
            case "WithNullUserId":
                queryParamDetails.remove("userId");
                userId = "is null";
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);

        switch (userIdDataForTxnRequest) {
            case "ValidUserId_Lazypay":
                Assert.assertTrue(validateProductEntry(source,lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "ValidUserId_PS":
                Assert.assertTrue(validateProductEntry(source, loanId, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "InvalidUserId_Lazypay":
            case "UserId_With_NOCustomerEntry":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "CUSTOMER_REQUEST_NOT_LINKED", "Empty Customer Request"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyUserId":
            case "WithNullUserId":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "userId must not be blank, "), "Failed to create the entry in the validation Table");
                break;

        }
    }
    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 2, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "lpUuidData")
    public void lpUuidValidationCases(String lpUuidData) {
        log.info("Getting started with the UUID tests for " + lpUuidData);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (lpUuidData) {
            case "ValidlpUuid_Lazypay":
                queryParamDetails.replace("lpUuid", testData.ValidlpUuid_Lazypay);
                lpUuid = testData.ValidlpUuid_Lazypay;
                txnId = txnRequestParamData().get("transactionId").toString();
                break;
            case "DuplicatelpUuid_ForDifferentTxnId":
                queryParamDetails.replace("lpUuid", testData.ValidlpUuid_Lazypay);
                queryParamDetails.replace("transactionId","TXN1686299503608");
                txnId = "TXN1686299503608";
                break;
            case "WithEmptylpUuid":
                queryParamDetails.replace("lpUuid", "");
                txnId = txnRequestParamData().get("transactionId").toString();
                break;
            case "WithNulllpUuid":
                queryParamDetails.remove("lpUuid");
                txnId = txnRequestParamData().get("transactionId").toString();
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        source = txnRequestParamData().get("source").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);

        switch (lpUuidData) {
            case "ValidlpUuid_Lazypay":
            case "DuplicatelpUuid_ForDifferentTxnId":
                Assert.assertTrue(validateProductEntry(source,lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "WithEmptylpUuid":
            case "WithNulllpUuid":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_PRODUCT_IDENTITY", "Both LpUuid & LoanId are either present or absent"), "Failed to create the entry in the validation Table");
                break;
        }
    }

    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 3, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "loanIdData")
    public void loanIdValidationCases(String loanIdData) {
        log.info("Getting started with the UUID tests for " + loanIdData);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (loanIdData) {
            case "ValidloanId_Lazypay":
                queryParamDetails.replace("loanId", testData.ValidloanId_Lazypay);
                queryParamDetails.replace("userId", "8179250614667824836");
                queryParamDetails.replace("source", testData.ValidSource_PS);
                queryParamDetails.remove("lpUuid");
                loanId = testData.ValidloanId_Lazypay;
                txnId = txnRequestParamData().get("transactionId").toString();
                break;
            case "DuplicateloanId__ForDifferentTxnId":
                queryParamDetails.replace("loanId", testData.ValidloanId_Lazypay);
                queryParamDetails.replace("userId", "8179250614667824836");
                queryParamDetails.replace("source", testData.ValidSource_PS);
                queryParamDetails.replace("transactionId","TXN1686277009999");
                queryParamDetails.remove("lpUuid");
                txnId = "TXN1686277009999";
                break;
            case "WithEmptyloanId":
                queryParamDetails.replace("loanId", "");
                queryParamDetails.replace("userId", "8179250614667824836");
                queryParamDetails.replace("source", testData.ValidSource_PS);
                queryParamDetails.remove("lpUuid");
                txnId = txnRequestParamData().get("transactionId").toString();
                break;
            case "WithNullloanId":
                queryParamDetails.remove("loanId");
                queryParamDetails.replace("userId", "8179250614667824836");
                queryParamDetails.replace("source", testData.ValidSource_PS);
                queryParamDetails.remove("lpUuid");
                txnId = txnRequestParamData().get("transactionId").toString();
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        source = testData.ValidSource_PS;
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);

        switch (loanIdData) {
            case "ValidloanId_Lazypay":
            case "DuplicateloanId__ForDifferentTxnId":
                Assert.assertTrue(validateProductEntry(source,loanId, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "WithEmptylpUuid":
            case "WithNulllpUuid":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_PRODUCT_IDENTITY", "Both LpUuid & LoanId are either present or absent"), "Failed to create the entry in the validation Table");
                break;
        }
    }

    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 4, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "transactionId")
    public void transactionIdValidationCases(String transactionId) {
        log.info("Getting started with the UUID tests for " + transactionId);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (transactionId) {
            case "ValidTransactionId_Lazypay":
                queryParamDetails.replace("transactionId", testData.ValidtransactionId_Lazypay);
                txnId=testData.ValidtransactionId_Lazypay;
                break;
            case "DuplicateTransactionId_Lazypay":
                queryParamDetails.replace("transactionId", testData.DuplicatetransactionId_Lazypay);
                break;
            case "WithEmptyTransactionId":
                queryParamDetails.replace("transactionId", "");
                txnId="";
                break;
            case "WithNullTransactionId":
                queryParamDetails.remove("transactionId");
                txnId="is null";
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        source = txnRequestParamData().get("source").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (transactionId) {
            case "ValidTransactionId_Lazypay":

                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "DuplicateTransactionId_Lazypay":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "DUPLICATE_ENTRY", "Record already exists"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyTransactionId":
            case "WithNullTransactionId":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "transactionId must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }
    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 5, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "sanctionedAmount")
    public void sanctionedAmountValidationCases(String sanctionedAmount) {
        log.info("Getting started with the UUID tests for " + sanctionedAmount);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (sanctionedAmount) {
            case "ValidsanctionedAmount_Lazypay":
                queryParamDetails.replace("sanctionedAmount", testData.ValidsanctionedAmount_Lazypay);
                break;
            case "InvalidsanctionedAmount_Lazypay":
                queryParamDetails.replace("sanctionedAmount", testData.InvalidsanctionedAmount_Lazypay);
                break;
            case "lessThanTxnAmount":
                queryParamDetails.replace("sanctionedAmount", testData.lessThanTxnAmount);
                break;
            case "WithEmptySanctionedAmount":
                queryParamDetails.replace("sanctionedAmount", "");
                break;
            case "WithNullSanctionedAmount":
                queryParamDetails.remove("sanctionedAmount");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        source = txnRequestParamData().get("source").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (sanctionedAmount) {
            case "ValidsanctionedAmount_Lazypay":
                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "InvalidsanctionedAmount_Lazypay":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_AMOUNTS, INVALID_SANCTIONED_AMOUNT", "Amounts are either less than or equal to zero, Sanctioned Amount is less than Transaction Amount"), "Failed to create the entry in the validation Table");
                break;
           case "lessThanTxnAmount":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_SANCTIONED_AMOUNT", "Sanctioned Amount is less than Transaction Amount"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptySanctionedAmount":
            case "WithNullSanctionedAmount":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "sanctionedAmount must not be null, "), "Failed to create the entry in the validation Table");
                break;

        }
    }

    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 6, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "txnAmount")
    public void txnAmountValidationCases(String txnAmount) {
        log.info("Getting started with the UUID tests for " + txnAmount);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (txnAmount) {
            case "ValidtxnAmount_Lazypay":
                queryParamDetails.replace("txnAmount", testData.ValidtxnAmount_Lazypay);
                break;
            case "InvalidtxnAmount_Lazypay":
                queryParamDetails.replace("txnAmount", testData.InvalidtxnAmount_Lazypay);
                break;
            case "GreaterThanSecuredAmount":
                queryParamDetails.replace("txnAmount", testData.GreaterThanSecuredAmount);
                break;
            case "WithEmptytxnAmount":
                queryParamDetails.replace("txnAmount", "");
                break;
            case "WithNulltxnAmount":
                queryParamDetails.remove("txnAmount");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        source = txnRequestParamData().get("source").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (txnAmount) {
            case "ValidtxnAmount_Lazypay":
                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "InvalidtxnAmount_Lazypay":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_AMOUNTS", "Amounts are either less than or equal to zero"), "Failed to create the entry in the validation Table");
                break;
            case "GreaterThanSecuredAmount":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_SANCTIONED_AMOUNT", "Sanctioned Amount is less than Transaction Amount"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptytxnAmount":
            case "WithNulltxnAmount":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "txnAmount must not be null, "), "Failed to create the entry in the validation Table");
                break;

        }
    }
    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 7, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "productSegments")
    public void productSegmentsValidationCases(String productSegments) {
        log.info("Getting started with the UUID tests for " + productSegments);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (productSegments) {
            case "ValidProductSegments_LP_Value1":
                queryParamDetails.replace("productSegments", testData.ValidProductSegments_LP_Value1);
                source = testData.ValidSource_Lazypay;
                break;
            case "ValidProductSegments_LP_Value2":
                queryParamDetails.replace("productSegments", testData.ValidProductSegments_LP_Value2);
                source = testData.ValidSource_Lazypay;
                break;
            case "ValidProductSegments_PS":
                queryParamDetails.replace("productSegments", testData.ValidProductSegments_PS);
                queryParamDetails.replace("userId","8179250614667824836");
                queryParamDetails.replace("source", testData.ValidSource_PS);
                queryParamDetails.replace("loanId", "STPLSC01008896740017");
                queryParamDetails.remove("lpUuid");
                source = testData.ValidSource_PS;
                loanId = "STPLSC01008896740017";
                break;
            case "InvalidProductSegments_Lazypay":
                queryParamDetails.replace("productSegments", testData.InvalidProductSegments_Lazypay);
                break;
            case "WithEmptyProductSegments":
                queryParamDetails.replace("productSegments", "");
                break;
            case "WithNullProductSegments":
                queryParamDetails.remove("productSegments");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (productSegments) {
            case "ValidProductSegments_LP_Value1":
            case "ValidProductSegments_LP_Value2":

                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "ValidProductSegments_PS":

                Assert.assertTrue(validateProductEntry(source, loanId, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "InvalidProductSegments_Lazypay":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_PRODUCT_SEGMENT", "Incorrect Product Segment: Loan"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyProductSegments":
            case "WithNullProductSegments":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "productSegments must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 8, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "voucherType")
    public void voucherTypeValidationCases(String voucherType) {
        log.info("Getting started with the UUID tests for " + voucherType);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (voucherType) {
            case "ValidVoucherType_type1":
                queryParamDetails.replace("voucherType", testData.ValidVoucherType_type1);
                break;
            case "ValidVoucherType_type2":
                queryParamDetails.replace("voucherType", testData.ValidVoucherType_type2);
                break;
            case "InvalidVoucherType":
                queryParamDetails.replace("voucherType", testData.InvalidVoucherType);
                break;
            case "WithEmptyVoucherType":
                queryParamDetails.replace("voucherType", "");
                break;
            case "WithNullVoucherType":
                queryParamDetails.remove("voucherType");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        source = txnRequestParamData().get("source").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (voucherType) {
            case "ValidVoucherType_type1":
            case "ValidVoucherType_type2":

                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "InvalidVoucherType":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_VOUCHER_TYPE", "Incorrect Voucher Type: Payments"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyVoucherType":
            case "WithNullVoucherType":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "voucherType must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority =9, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "txnType")
    public void txnTypeValidationCases(String txnType) {
        log.info("Getting started with the UUID tests for " + txnType);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (txnType) {
            case "ValidTxnType_type1":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type1);
                break;
            case "ValidTxnType_type2":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type2);
                break;
            case "ValidTxnType_type3":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type3);
                break;
            case "ValidTxnType_type4":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type4);
                break;
            case "ValidTxnType_type5":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type5);
                break;
            case "ValidTxnType_type6":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type6);
                break;
            case "ValidTxnType_type7":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type7);
                break;
            case "ValidTxnType_type8":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type8);
                break;
            case "ValidTxnType_type9":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type9);
                break;
            case "ValidTxnType_type10":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type10);
                break;
            case "ValidTxnType_type11":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type11);
                break;
            case "ValidTxnType_type12":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type12);
                break;
            case "ValidTxnType_type13":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type13);
                break;
            case "ValidTxnType_type14":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type14);
                break;
            case "ValidTxnType_type15":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type15);
                break;
            case "ValidTxnType_type16":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type16);
                break;
            case "ValidTxnType_type17":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type17);
                break;
            case "ValidTxnType_type18":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type18);
                break;
            case "ValidTxnType_type19":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type19);
                break;
            case "ValidTxnType_type20":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type20);
                break;
            case "ValidTxnType_type21":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type21);
                break;
            case "ValidTxnType_type22":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type22);
                break;
            case "ValidTxnType_type23":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type23);
                break;
            case "ValidTxnType_type24":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type24);
                break;
            case "ValidTxnType_type25":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type25);
                break;
            case "ValidTxnType_type26":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type26);
                break;
            case "ValidTxnType_type27":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type27);
                break;
            case "ValidTxnType_type28":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type28);
                break;
            case "ValidTxnType_type29":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type29);
                break;
            case "ValidTxnType_type30":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type30);
                break;
            case "ValidTxnType_type31":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type31);
                break;
            case "ValidTxnType_type32":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type32);
                break;
            case "ValidTxnType_type33":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type33);
                break;
            case "ValidTxnType_type34":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type34);
                break;
            case "ValidTxnType_type35":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type35);
                break;
            case "ValidTxnType_type36":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type36);
                break;
            case "ValidTxnType_type37":
                queryParamDetails.replace("txnType", testData.ValidTxnType_type37);
                break;
            case "InvalidTxnType_Lazypay":
                queryParamDetails.replace("txnType", testData.InvalidTxnType_Lazypay);
                break;
            case "WithEmptyTxnType":
                queryParamDetails.replace("txnType", "");
                break;
            case "WithNullTxnType":
                queryParamDetails.remove("txnType");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        source = txnRequestParamData().get("source").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (txnType) {
            case "ValidTxnType_type1":
            case "ValidTxnType_type2":
            case "ValidTxnType_type3":
            case "ValidTxnType_type4":
            case "ValidTxnType_type5":
            case "ValidTxnType_type6":
            case "ValidTxnType_type7":
            case "ValidTxnType_type8":
            case "ValidTxnType_type9":
            case "ValidTxnType_type10":
            case "ValidTxnType_type11":
            case "ValidTxnType_type12":
            case "ValidTxnType_type13":
            case "ValidTxnType_type14":
            case "ValidTxnType_type15":
            case "ValidTxnType_type16":
            case "ValidTxnType_type17":
            case "ValidTxnType_type18":
            case "ValidTxnType_type19":
            case "ValidTxnType_type20":
            case "ValidTxnType_type21":
            case "ValidTxnType_type22":
            case "ValidTxnType_type23":
            case "ValidTxnType_type24":
            case "ValidTxnType_type25":
            case "ValidTxnType_type26":
            case "ValidTxnType_type27":
            case "ValidTxnType_type28":
            case "ValidTxnType_type29":
            case "ValidTxnType_type30":
            case "ValidTxnType_type31":
            case "ValidTxnType_type32":
            case "ValidTxnType_type33":
            case "ValidTxnType_type34":
            case "ValidTxnType_type35":
            case "ValidTxnType_type36":
            case "ValidTxnType_type37":

                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "InvalidTxnType_Lazypay":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_TYPE2", "Incorrect Type2: EMI"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyTxnType":
            case "WithNullTxnType":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "txnType must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 10, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "instrumentType")
    public void instrumentTypeValidationCases(String instrumentType) {
        log.info("Getting started with the UUID tests for " + instrumentType);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (instrumentType) {
            case "ValidInstrumentType_type1":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type1);
                break;
            case "ValidInstrumentType_type2":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type2);
                break;
            case "ValidInstrumentType_type3":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type3);
                break;
            case "ValidInstrumentType_type4":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type4);
                break;
            case "ValidInstrumentType_type5":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type5);
                break;
            case "ValidInstrumentType_type6":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type6);
                break;
            case "ValidInstrumentType_type7":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type7);
                break;
            case "ValidInstrumentType_type8":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type8);
                break;
            case "ValidInstrumentType_type9":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type9);
                break;
            case "ValidInstrumentType_type10":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type10);
                break;
            case "ValidInstrumentType_type11":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type11);
                break;
            case "ValidInstrumentType_type12":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type12);
                break;
            case "ValidInstrumentType_type13":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type13);
                break;
            case "ValidInstrumentType_type14":
                queryParamDetails.replace("instrumentType", testData.ValidInstrumentType_type14);
                break;
            case "InvalidInstrumentType_Lazypay":
                queryParamDetails.replace("instrumentType", testData.InvalidInstrumentType_Lazypay);
                break;
            case "WithEmptyInstrumentType":
                queryParamDetails.replace("instrumentType", "");
                break;
            case "WithNullInstrumentType":
                queryParamDetails.remove("instrumentType");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        source = txnRequestParamData().get("source").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (instrumentType) {
            case "ValidInstrumentType_type1":
            case "ValidInstrumentType_type2":
            case "ValidInstrumentType_type3":
            case "ValidInstrumentType_type4":
            case "ValidInstrumentType_type5":
            case "ValidInstrumentType_type6":
            case "ValidInstrumentType_type7":
            case "ValidInstrumentType_type8":
            case "ValidInstrumentType_type9":
            case "ValidInstrumentType_type10":
            case "ValidInstrumentType_type11":
            case "ValidInstrumentType_type12":
            case "ValidInstrumentType_type13":
            case "ValidInstrumentType_type14":

                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "InvalidInstrumentType_Lazypay":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_INSTRUMENT_TYPE", "Incorrect Instrument Type: Netbanking"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyInstrumentType":
            case "WithNullInstrumentType":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "instrumentType must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }
    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 11, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "Source")
    public void sourceValidationCases(String Source) {
        log.info("Getting started with the UUID tests for " + Source);
        deleteTxnRequestData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + txnRequestParamData());
        switch (Source) {
            case "ValidSource_Lazypay":
                queryParamDetails.replace("userId","8503172645936983444");
                queryParamDetails.replace("source", testData.ValidSource_Lazypay);
                queryParamDetails.replace("lpUuid", "12778159");
                source = testData.ValidSource_Lazypay;
                lpUuid = "12778159";
                break;
            case "ValidSource_PS":
                queryParamDetails.replace("userId","8179250614667824836");
                queryParamDetails.replace("source", testData.ValidSource_PS);
                queryParamDetails.replace("loanId", "STPLSC01000996740017");
                queryParamDetails.remove("lpUuid");
                source = testData.ValidSource_PS;
                loanId = "STPLSC01000996740017";
                break;
            case "CustomerToTxn_SourceMismatch":
                queryParamDetails.replace("source", testData.ValidSource_PS);
                queryParamDetails.replace("loanId", "STPLSC01007706945099");
                queryParamDetails.remove("lpUuid");
                break;
            case "PSSource_WithLpUuuid":
                queryParamDetails.replace("source", testData.ValidSource_PS);
                break;
            case "LPSource_WithLoanId":
                queryParamDetails.replace("source", testData.ValidSource_Lazypay);
                queryParamDetails.replace("loanId", "STPLSC01000006945017");
                queryParamDetails.remove("lpUuid");
                break;
            case "InvalidSource":
                queryParamDetails.replace("source", testData.InvalidSource);
                break;
            case "WithEmptySource":
                queryParamDetails.replace("source", "");
                break;
            case "WithNullSource":
                queryParamDetails.remove("source");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (Source) {
            case "ValidSource_Lazypay":

                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "ValidSource_PS":

                Assert.assertTrue(validateProductEntry(source, loanId, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "CustomerToTxn_SourceMismatch":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "CUSTOMER_REQUEST_NOT_LINKED", "Empty Customer Request"), "Failed to create the entry in the validation Table");
                break;
            case "PSSource_WithLpUuuid":
            case "LPSource_WithLoanId":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_PRODUCT_IDENTITY", "Both LpUuid & LoanId are either present or absent"), "Failed to create the entry in the validation Table");
                break;
            case "InvalidSource":

                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_SOURCE", "Incorrect Source: LP"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptySource":
            case "WithNullSource":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "source must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 12, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "sanctionDate")
    public void sanctinedDateValidationCases(String sanctionDate) {
        log.info("Getting started with the customerType test for " + sanctionDate);
        deleteUserData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (sanctionDate) {
            case "WithValidsanctionDate":
                queryParamDetails.replace("sanctionDate", testData.ValidSanctionDate);
                break;
            case "WithInValidsanctionDate":
                queryParamDetails.replace("sanctionDate", testData.InvalidSanctionDate);
                break;
            case "WithNullsanctionDate":
                queryParamDetails.remove("sanctionDate");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        source = txnRequestParamData().get("source").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (sanctionDate) {
            case "WithValidsanctionDate":
                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "WithInValidsanctionDate":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_SANCTIONED_DATE_FUTURE", "Sanctioned Date is in future"), "Failed to create the entry in the validation Table");
                break;
            case "WithNullsanctionDate":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "sanctionDate must not be null, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    @Description("To check the txn request data is getting processed correctly or not")
    @Feature("Txn_Monitoring")
    @Test(priority = 13, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "txnCreateDate")
    public void txnCreateDateValidationCases(String txnCreateDate) {
        log.info("Getting started with the customerType test for " + txnCreateDate);
        deleteUserData(txnRequestParamData().get("userId").toString());
        Map<String, Object> queryParamDetails = txnRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (txnCreateDate) {
            case "WithValidtxnCreateDate":
                queryParamDetails.replace("txnCreateDate", testData.ValidTxnDate);
                break;
            case "WithInValidtxnCreateDate":
                queryParamDetails.replace("txnCreateDate", testData.InvalidTxnDate);
                break;
            case "WithNulltxnCreateDate":
                queryParamDetails.remove("txnCreateDate");
                break;
        }
        String financialTxnRequestResponse = txnMonitorCreateTxnRequestData.getWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + financialTxnRequestResponse);
        queryParamDetailsProcessFinancialTxnCron.put("batchSize", "2");
        txnId = txnRequestParamData().get("transactionId").toString();
        lpUuid = txnRequestParamData().get("lpUuid").toString();
        source = txnRequestParamData().get("source").toString();
        processFinancialTxnRequestAndValidate(txnId, queryParamDetailsProcessFinancialTxnCron, headerDetails);
        switch (txnCreateDate) {
            case "WithValidtxnCreateDate":
                Assert.assertTrue(validateProductEntry(source, lpUuid, "product_acc_number", "1"), "Failed to create the entry in the product Table");
                Assert.assertTrue(validateTransactionEntry(txnId, "status", "CREATED"), "Failed to create the entry in the financial_transaction Table");
                break;
            case "WithInValidtxnCreateDate":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "INVALID_TXN_DATE_FUTURE", "Txn Date is in future"), "Failed to create the entry in the validation Table");
                break;
            case "WithNulltxnCreateDate":
                Assert.assertTrue(validateFinancialTxnFailureValidation(txnId, "ERR_MISSING_REQUIRED_FIELDS", "txnCreateDate must not be null, "), "Failed to create the entry in the validation Table");
                break;
        }
    }
}
