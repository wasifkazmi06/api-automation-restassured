package heimdall;

import api.heimdall.TxnMonitorCreateCustomerRequestData;
import api.heimdall.TxnMonitorCustomerRequestCron;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TransactionMonitoringTests extends HeimdallSupportData {

    TxnMonitorCreateCustomerRequestData txnMonitorCreateCustomerRequestData = new TxnMonitorCreateCustomerRequestData();
    TxnMonitorCustomerRequestCron txnMonitorCustomerRequestCron = new TxnMonitorCustomerRequestCron();

    public TransactionMonitoringTests() throws Exception {

    }

    String ValidUUID_Lazypay = "6030672778706942461",
            ValidUUID_PS = "10167464",
            InvalidUUID_Lazypay = "ABCD6030672778706942461",
            InvalidUUID_PS = "ABCD10167464",
            WithValidCustomerEffectiveStatusDate = "2021-05-23",
            WithInValidCustomerEffectiveStatusDate = "02-05-23",
            panNumberRefKey = "24142829-a9c3-4f3e-8939-6f6d895f9c70",
            panNumberMaskId = "BHNPP3939A",
            validDOB = "1922-05-23",
            invalidDOB = "22-05-23",
            futureDOB = "2222-05-23",
            validPincode = "560102",
            invalidPincode = "900000",
            charLimitPincode = "900",
            validAddress = "testHouseno53ApoorvaNagarHsrLayout%20Sector",
            validAddressGT55 = "testHouseno53ApoorvaNagarHsrLayout%20Sector7%20Hsr%20Layout%20Bangalore",
            validAddressGT100 = "testHouseno53ApoorvaNagarHsrLayout%20Sector7%20Hsr%20Layout%20Bangalore%20testHouseno53ApoorvaNagarHsrLayout%20Sector7%20Hsr%20Layout%20Bangalore",
            invalidAddress = "212312341242132131313123124321",
            charLimitAddress = "1",
            validName="TestAutomation",
            invalidName="12",
            charLimitName="1",
            userID;
    ;

    Map<String, Object> queryParamDetailsProcessCustomerCron = new HashMap<>();

    private void processCustomerRequestAndValidate(String userID, Map<String, Object> queryParamDetailsProcessCustomerCron, HashMap<String, String> headerDetails) {
        String customerCronResponse = txnMonitorCustomerRequestCron.postWithNoResponseBody(queryParamDetailsProcessCustomerCron, headerDetails);
        log.info("response status code : " + customerCronResponse);
        Assert.assertTrue(validateCustomerRequest(userID, "active", "0"), "Failed to update the active field in customer request table");
    }

    /**
     * TestCases for customer UUID
     * ValidUUID > Lazypay
     * ValidUUID > PS
     * InvalidUUID > Lazypay
     * InvalidUUID > PS
     * WithEmptyUUID
     * WithNullUUID
     *
     * @param testForUUIDData
     */
    @Description("To check the customer data is getting processed correctly or not for the UUID field")
    @Feature("Txn_Monitoring")
    @Test(priority = 1, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "testForUUIDData")
    public void testForUUID(String testForUUIDData) {
        log.info("Getting started with the UUID tests for " + testForUUIDData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + customerRequestParamData());
        switch (testForUUIDData) {
            case "ValidUUID_Lazypay":
                queryParamDetails.replace("userId", ValidUUID_Lazypay);
                userID = ValidUUID_Lazypay;
                break;
            case "ValidUUID_PS":
                queryParamDetails.replace("userId", ValidUUID_PS);
                userID = ValidUUID_PS;
                break;
            case "InvalidUUID_Lazypay":
                queryParamDetails.replace("userId", InvalidUUID_Lazypay);
                userID = InvalidUUID_Lazypay;
                break;
            case "InvalidUUID_PS":
                queryParamDetails.replace("userId", InvalidUUID_PS);
                userID = InvalidUUID_PS;
                break;
            case "WithEmptyUUID":
                queryParamDetails.replace("userId", "");
                userID = "";
                break;
            case "WithNullUUID":
                queryParamDetails.remove("userId");
                userID = "is null";
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (testForUUIDData) {
            case "ValidUUID_Lazypay":

                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "ValidUUID_PS":

                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "InvalidUUID_Lazypay":

                Assert.assertTrue(validateCustomerFailureValidation(userID, "errorcode", "errordesc"), "Failed to create the entry in the validation Table");
                break;
            case "InvalidUUID_PS":

                Assert.assertTrue(validateCustomerFailureValidation(userID, "errorcode", "errordesc"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyUUID":

                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "userId must not be blank, "), "Failed to create the entry in the validation Table");
                break;
            case "WithNullUUID":
                Assert.assertTrue(validateCustomerFailureValidation("is null", "ERR_MISSING_REQUIRED_FIELDS", "userId must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the CustomerEffectiveStatusDate
     * WithValidCustomerEffectiveStatusDate
     * WithInValidCustomerEffectiveStatusDate
     * WithNullCustomerEffectiveStatusDate
     * WithEmptyCustomerEffectiveStatusDate
     *
     * @param CustomerEffectiveStatusDateData
     */
    @Description("To check the customer data is getting processed correctly or not for the CustomerEffectiveStatusDate field")
    @Feature("Txn_Monitoring")
    @Test(priority = 2, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "CustomerEffectiveStatusDateData")
    public void testForCustomerEffectiveStatusDate(String CustomerEffectiveStatusDateData) {
        log.info("Getting started with the CustomerEffectiveStatusDate test for " + CustomerEffectiveStatusDateData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + customerRequestParamData());
        switch (CustomerEffectiveStatusDateData) {
            case "WithValidCustomerEffectiveStatusDate":
                queryParamDetails.replace("customerStatusEffectiveDate", WithValidCustomerEffectiveStatusDate);
                break;
            case "WithInValidCustomerEffectiveStatusDate":
                queryParamDetails.replace("customerStatusEffectiveDate", WithInValidCustomerEffectiveStatusDate);
                break;
            case "WithNullCustomerEffectiveStatusDate":
                queryParamDetails.remove("customerStatusEffectiveDate");
                break;
            case "WithEmptyCustomerEffectiveStatusDate":
                queryParamDetails.replace("customerStatusEffectiveDate", "");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (CustomerEffectiveStatusDateData) {
            case "WithValidCustomerEffectiveStatusDate":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithInValidCustomerEffectiveStatusDate":
            case "WithNullCustomerEffectiveStatusDate":
            case "WithEmptyCustomerEffectiveStatusDate":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "customerStatusEffectiveDate must not be null, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the Pan Number
     * WithValidCustomerEffectiveStatusDate
     * WithInValidCustomerEffectiveStatusDate
     * WithNullCustomerEffectiveStatusDate
     * WithEmptyCustomerEffectiveStatusDate
     *
     * @param testForPanNumberData
     */
    @Description("To check the customer data is getting processed correctly or not for the Pan Number  field")
    @Feature("Txn_Monitoring")
    @Test(priority = 3, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "testForPanNumberData")
    public void testForPanNumber(String testForPanNumberData) {
        log.info("Getting started with the PanNumber test for " + testForPanNumberData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + customerRequestParamData());
        switch (testForPanNumberData) {
            case "WithValidPanIDRefKey":
                queryParamDetails.replace("panNumber", panNumberRefKey);
                break;
            case "WithValidPanIDMaskedKey":
                queryParamDetails.replace("panNumber", panNumberMaskId);
                break;
            case "WithInValidPanIDRefKey":
                queryParamDetails.replace("panNumber", "sdfghj3456sdfgh3456sdfg");
                break;
            case "WithInValidPanIDMaskedKey":
                queryParamDetails.replace("panNumber", "sdfghj3456sdfgh3456sdfgsaf");
                break;
            case "WithEmptyPanId":
                queryParamDetails.replace("panNumber", "");
                break;
            case "WithNullPanId":
                queryParamDetails.remove("panNumber");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (testForPanNumberData) {
            case "WithValidPanIDRefKey":
            case "WithValidPanIDMaskedKey":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithInValidPanIDRefKey":
            case "WithInValidPanIDMaskedKey":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_PAN_NUMBER", "Incorrect Pan Syntax:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyPanId":
            case "WithNullPanId":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "panNumber must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the DOB
     * <p>
     * WithValidDOB
     * WithFutureDOB
     * WithInvalidDOB
     * WithEmptyDOB
     * WithNullDOB
     *
     * @param DOBData
     */
    @Description("To check the customer data is getting processed correctly or not for the DOB field")
    @Feature("Txn_Monitoring")
    @Test(priority = 4, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "DOBData")
    public void testForDOB(String DOBData) {
        log.info("Getting started with the DOB test for " + DOBData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + customerRequestParamData());
        switch (DOBData) {
            case "WithValidDOB":
                queryParamDetails.replace("dob", validDOB);
                break;
            case "WithFutureDOB":
                queryParamDetails.replace("dob", futureDOB);
                break;
            case "WithInvalidDOB":
                queryParamDetails.replace("dob", invalidDOB);
                break;
            case "WithEmptyDOB":
                queryParamDetails.replace("dob", "");
                break;
            case "WithNullDOB":
                queryParamDetails.remove("dob");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (DOBData) {
            case "WithValidDOB":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithFutureDOB":
            case "WithInvalidDOB":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_DOB_FUTURE", "Dob is in future"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyDOB":
            case "WithNullDOB":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "dob must not be null, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the PermanentPincode
     * withValidPermanentPincode
     * withInValidPermanentPincode
     * withCharLimitPermanentPincode
     * withEmptyPermanentPincode
     * withNullPermanentPincode
     *
     * @param pincodeData
     */
    @Description("To check the customer data is getting processed correctly or not for the PermanentPincode field")
    @Feature("Txn_Monitoring")
    @Test(priority = 5, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "pincodeData")
    public void testForPermanentPincode(String pincodeData) {
        log.info("Getting started with the pincode test for " + pincodeData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + customerRequestParamData());
        switch (pincodeData) {
            case "withValidPermanentPincode":
                queryParamDetails.replace("permanentPincode", validPincode);
                break;
            case "withInValidPermanentPincode":
                queryParamDetails.replace("permanentPincode", invalidPincode);
                break;
            case "withCharLimitPermanentPincode":
                queryParamDetails.replace("permanentPincode", charLimitPincode);
                break;
            case "withEmptyPermanentPincode":
                queryParamDetails.replace("permanentPincode", "");
                break;
            case "withNullPermanentPincode":
                queryParamDetails.remove("permanentPincode");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (pincodeData) {
            case "withValidPermanentPincode":
            case "withInValidPermanentPincode":
            case "withCharLimitPermanentPincode":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            //TODO :: Issue Needs discussion with Dev, Should have validation for invalidPincode
            case "withEmptyPermanentPincode":
            case "withNullPermanentPincode":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "permanentPincode must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the PermanentAddress
     * withValidPermanentPincode
     * withInValidPermanentPincode
     * withCharLimitPermanentPincode
     * withEmptyPermanentPincode
     * withNullPermanentPincode
     *
     * @param addressData
     */
    @Description("To check the customer data is getting processed correctly or not for the PermanentAddress field")
    @Feature("Txn_Monitoring")
    @Test(priority = 6, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "addressData")
    public void testForPermanentAddress(String addressData) {
        log.info("Getting started with the PermanentAddress test for " + addressData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (addressData) {
            case "withValidPermanentAddress01":
                queryParamDetails.replace("permanentAddress", validAddress);
                break;
            case "withValidPermanentAddress02":
                queryParamDetails.replace("permanentAddress", validAddressGT55);
                break;
            case "withValidPermanentAddress03":
                queryParamDetails.replace("permanentAddress", validAddressGT100);
                break;
            case "withInValidPermanentAddress":
                queryParamDetails.replace("permanentAddress", invalidAddress);
                break;
            case "withCharLimitPermanentAddress":
                queryParamDetails.replace("permanentAddress", charLimitAddress);
                break;
            case "withEmptyPermanentAddress":
                queryParamDetails.replace("permanentAddress", "");
                break;
            case "withNullPermanentAddress":
                queryParamDetails.remove("permanentAddress");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (addressData) {
            case "withValidPermanentAddress":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            //TODO :: Issue Needs discussion with Dev, Should have validation for withInValidPermanentAddress and withCharLimitPermanentAddress
            case "withInValidPermanentAddress":
            case "withCharLimitPermanentAddress":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_PERMANENT_ADDRESS", "Dob did not match with existing record01"), "Failed to create the entry in the validation Table");
                break;
            case "withEmptyPermanentAddress":
            case "withNullPermanentAddress":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "permanentAddress must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the CorrespondencePincode
     * withValidCorrespondencePincode
     * withInValidCorrespondencePincode
     * withCharLimitCorrespondencePincode
     * withEmptyCorrespondencePincode
     * withNullCorrespondencePincode
     *
     * @param pincodeData
     */
    @Description("To check the customer data is getting processed correctly or not for the CorrespondencePincode field")
    @Feature("Txn_Monitoring")
    @Test(priority = 7, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "pincodeData")
    public void testForCorrespondencePincode(String pincodeData) {
        log.info("Getting started with the pincode test for " + pincodeData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + customerRequestParamData());
        switch (pincodeData) {
            case "withValidPermanentPincode":
                queryParamDetails.replace("permanentPincode", validPincode);
                break;
            case "withInValidPermanentPincode":
                queryParamDetails.replace("permanentPincode", invalidPincode);
                break;
            case "withCharLimitPermanentPincode":
                queryParamDetails.replace("permanentPincode", charLimitPincode);
                break;
            case "withEmptyPermanentPincode":
                queryParamDetails.replace("permanentPincode", "");
                break;
            case "withNullPermanentPincode":
                queryParamDetails.remove("permanentPincode");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (pincodeData) {
            case "withValidPermanentPincode":
            case "withInValidPermanentPincode":
            case "withCharLimitPermanentPincode":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "withEmptyPermanentPincode":
            case "withNullPermanentPincode":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "permanentPincode must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the Correspondence Address
     * withValidPermanentPincode
     * withInValidPermanentPincode
     * withCharLimitPermanentPincode
     * withEmptyPermanentPincode
     * withNullPermanentPincode
     *
     * @param addressData
     */
    @Description("To check the customer data is getting processed correctly or not for the CorrespondenceAddress field")
    @Feature("Txn_Monitoring")
    @Test(priority = 8, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "addressData")
    public void testForCorrespondenceAddress(String addressData) {
        log.info("Getting started with the pincode test for " + addressData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (addressData) {
            case "withValidPermanentAddress01":
                queryParamDetails.replace("permanentAddress", validAddress);
                break;
            case "withValidPermanentAddress02":
                queryParamDetails.replace("permanentAddress", validAddressGT55);
                break;
            case "withValidPermanentAddress03":
                queryParamDetails.replace("permanentAddress", validAddressGT100);
                break;
            case "withInValidPermanentAddress":
                queryParamDetails.replace("correspondenceAddress", invalidAddress);
                break;
            case "withCharLimitPermanentAddress":
                queryParamDetails.replace("correspondenceAddress", charLimitAddress);
                break;
            case "withEmptyPermanentAddress":
                queryParamDetails.replace("correspondenceAddress", "");
                break;
            case "withNullPermanentAddress":
                queryParamDetails.remove("correspondenceAddress");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (addressData) {
            case "withValidPermanentAddress":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            //TODO :: Issue Needs discussion with Dev, Should have validation for withInValidPermanentAddress and withCharLimitPermanentAddress
            case "withInValidPermanentAddress":
            case "withCharLimitPermanentAddress":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_PERMANENT_ADDRESS", "Dob did not match with existing record01"), "Failed to create the entry in the validation Table");
                break;
            case "withEmptyPermanentAddress":
            case "withNullPermanentAddress":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "correspondenceAddress must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }


    /**
     * Test cases for the PanName
     * WithValidPanName
     * WithInvalidPanName
     * WithSpecialCharactersPanName
     * WithNullPanName
     * WithEmptyPanName
     *
     * @param panNameData
     */
    @Description("To check the customer data is getting processed correctly or not for the name field")
    @Feature("Txn_Monitoring")
    @Test(priority = 9, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "panNameData")
    public void testForPanName(String panNameData) {
        log.info("Getting started with the pincode test for " + panNameData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (panNameData) {
            case "WithValidPanName":
                queryParamDetails.replace("name", validName);
                break;
            case "WithInvalidPanName":
                queryParamDetails.replace("name", invalidName);
                break;
            case "WithSpecialCharactersPanName":
                queryParamDetails.replace("name", charLimitName);
                break;
            case "WithEmptyPanName":
                queryParamDetails.replace("name", "");
                break;
            case "WithNullPanName":
                queryParamDetails.remove("name");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (panNameData) {
            case "WithValidPanName":
            case "WithInvalidPanName":
            case "WithSpecialCharactersPanName":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithEmptyPanName":
            case "WithNullPanName":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "name must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the Segment
     * WithValidSegment
     * WithInvalidSegment
     * WithEmptySegment
     * WithNullSegment
     * @param segmentData
     */
    @Description("To check the customer data is getting processed correctly or not for the Segments field")
    @Feature("Txn_Monitoring")
    @Test(priority = 10, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "segmentData")
    public void testForSegment(String segmentData) {
        log.info("Getting started with the segment for " + segmentData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (segmentData) {
            case "WithInvalidSegment":
                queryParamDetails.replace("segments", "00");
                break;
            case "WithEmptySegment":
                queryParamDetails.replace("segments", "");
                break;
            case "WithNullSegment":
                queryParamDetails.remove("segments");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (segmentData) {
            case "WithValidSegment":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            //TODO :: Issue Needs discussion with Dev, Should have validation for WithInvalidPanName and WithSpecialCharactersPanName
            case "WithInvalidSegment":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_SEGMENT", "Incorrect Segment:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptySegment":
            case "WithNullSegment":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "segments must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the ProductSegments
     * WithValidProductSegments
     * WithInvalidProductSegments
     * WithEmptyProductSegments
     * WithNullProductSegments
     * @param productSegmentData
     */
    @Description("To check the customer data is getting processed correctly or not for the ProductSegment field")
    @Feature("Txn_Monitoring")
    @Test(priority = 11, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "productSegmentData")
    public void testForProductSegment(String productSegmentData) {
        log.info("Getting started with the ProductSegment test for " + productSegmentData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (productSegmentData) {
            case "WithValidProductSegments_BNPL":
                queryParamDetails.replace("productSegments", "BNPL");
                break;
            case "WithInvalidProductSegmentsWithValidProductSegments_PersonalLoan":
                queryParamDetails.replace("productSegments", "Personal%20Loan");
                break;
            case "WithInvalidProductSegments":
                queryParamDetails.replace("productSegments", "ABC");
                break;
            case "WithEmptyProductSegments":
                queryParamDetails.replace("productSegments", "");
                break;
            case "WithNullProductSegments":
                queryParamDetails.remove("productSegments");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (productSegmentData) {
            case "WithValidProductSegments":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            //TODO :: Issue Needs discussion with Dev, Should have validation for WithInvalidProductSegments and WithSpecialCharactersPanName
            case "WithInvalidProductSegments":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_PRODUCT_SEGMENT", "Incorrect Product Segment:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyProductSegments":
            case "WithNullProductSegments":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "productSegments must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }


    /**
     * Test cases for the Customer Type
     * WithValidProductSegments
     * WithInvalidProductSegments
     * WithEmptyProductSegments
     * WithNullProductSegments
     * @param customerTypeData
     */
    @Description("To check the customer data is getting processed correctly or not for the CustomerType field")
    @Feature("Txn_Monitoring")
    @Test(priority = 12, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "customerTypeData")
    public void testForCustomerType(String customerTypeData) {
        log.info("Getting started with the customerType test for " + customerTypeData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (customerTypeData) {
            case "WithInvalidCustomerType":
                queryParamDetails.replace("customerType", "ABC");
                break;
            case "WithEmptyCustomerType":
                queryParamDetails.replace("customerType", "");
                break;
            case "WithNullCustomerType":
                queryParamDetails.remove("customerType");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (customerTypeData) {
            case "WithValidCustomerType":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            //TODO :: Issue Needs discussion with Dev, Should have validation for WithInvalidProductSegments and WithSpecialCharactersPanName
            case "WithInvalidCustomerType":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_CUSTOMER_TYPE", "Incorrect Customer Type:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyCustomerType":
            case "WithNullCustomerType":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "customerType must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the Gender Type
     * WithValidGender - M
     * WithValidGender - F
     * WithValidGender - T
     * WithInvalidGender
     * WithEmptyGender
     * WithNullGender
     *
     * @param genderTypeData
     */
    @Description("To check the customer data is getting processed correctly or not for the CustomerType field")
    @Feature("Txn_Monitoring")
    @Test(priority = 13, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "genderTypeData")
    public void testForGenderType(String genderTypeData) {
        log.info("Getting started with the customerType test for " + genderTypeData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);

        switch (genderTypeData) {
            case "WithValidGender_M":
                queryParamDetails.replace("gender", "M");
                break;
            case "WithValidGender_T":
                queryParamDetails.replace("gender", "T");
                break;
            case "WithValidGender_F":
                queryParamDetails.replace("gender", "F");
                break;
            case "WithInvalidGender":
                queryParamDetails.replace("gender", "G");
                break;
            case "WithEmptyGender":
                queryParamDetails.replace("gender", "");
                break;
            case "WithNullGender":
                queryParamDetails.remove("gender");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (genderTypeData) {
            case "WithValidGender_M":
            case "WithValidGender_T":
            case "WithValidGender_F":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            //TODO :: Issue Needs discussion with Dev, Should have validation for WithEmptyGender and WithNullGender
            case "WithInvalidGender":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_GENDER", "Incorrect Gender:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyGender":
            case "WithNullGender":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "gender must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the Occupation Type
     * WithValidOccupationType
     * WithInvalidOccupationType
     * WithEmptyOccupationType
     * WithNullOccupationType
     *
     * @param occupationTypeData
     */
    @Description("To check the customer data is getting processed correctly or not for the OccupationType field")
    @Feature("Txn_Monitoring")
    @Test(priority = 14, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "occupationTypeData")
    public void testForOccupationType(String occupationTypeData) {
        log.info("Getting started with the customerType test for " + occupationTypeData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (occupationTypeData) {
            case "WithValidOccupationType_01":
                queryParamDetails.replace("occupationType", "01");
                break;
            case "WithValidOccupationType_02":
                queryParamDetails.replace("occupationType", "02");
                break;
            case "WithValidOccupationType_03":
                queryParamDetails.replace("occupationType", "03");
                break;
            case "WithValidOccupationType_04":
                queryParamDetails.replace("occupationType", "04");
                break;
            case "WithValidOccupationType_05":
                queryParamDetails.replace("occupationType", "05");
                break;
            case "WithValidOccupationType_06":
                queryParamDetails.replace("occupationType", "06");
                break;
            case "WithValidOccupationType_07":
                queryParamDetails.replace("occupationType", "07");
                break;
            case "WithValidOccupationType_08":
                queryParamDetails.replace("occupationType", "08");
                break;
            case "WithValidOccupationType_09":
                queryParamDetails.replace("occupationType", "PV");
                break;
            case "WithValidOccupationType_10":
                queryParamDetails.replace("occupationType", "PS");
                break;
            case "WithValidOccupationType_11":
                queryParamDetails.replace("occupationType", "GS");
                break;
            case "WithValidOccupationType_12":
                queryParamDetails.replace("occupationType", "4");
                break;
            case "WithValidOccupationType_13":
                queryParamDetails.replace("occupationType", "ND");
                break;
            case "WithValidOccupationType_14":
                queryParamDetails.replace("occupationType", "FD");
                break;
            case "WithValidOccupationType_15":
                queryParamDetails.replace("occupationType", "SE");
                break;
            case "WithInvalidOccupationType":
                queryParamDetails.replace("occupationType", "09");
                break;
            case "WithEmptyOccupationType":
                queryParamDetails.replace("occupationType", "");
                break;
            case "WithNullOccupationType":
                queryParamDetails.remove("occupationType");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (occupationTypeData) {
            case "WithValidOccupationType":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithInvalidOccupationType":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_OCCUPATION_TYPE", "Incorrect OccupationType:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyOccupationType":
            case "WithNullOccupationType":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "occupationType must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }



    /**
     * Test cases for the Module Applicable
     * WithValidModuleApplicable TM%2CScreening, TM,Screening
     * WithInvalidModuleApplicable
     * WithEmptyModuleApplicable
     * WithNullModuleApplicable
     *
     * @param ModuleApplicableData
     */
    @Description("To check the customer data is getting processed correctly or not for the ModuleApplicable field")
    @Feature("Txn_Monitoring")
    @Test(priority = 15, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "ModuleApplicableData")
    public void testForModuleApplicable(String ModuleApplicableData) {
        log.info("Getting started with the customerType test for " + ModuleApplicableData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (ModuleApplicableData) {
            case "WithValidModuleApplicable_1":
                queryParamDetails.replace("moduleApplicable", "TM%2CScreening");
                break;
            case "WithValidModuleApplicable_2":
                queryParamDetails.replace("moduleApplicable", "TM");
                break;
                //TODO :: need to remove the screening testcase
            case "WithValidModuleApplicable_3":
                queryParamDetails.replace("moduleApplicable", "Screening");
                break;
            case "WithInvalidModuleApplicable":
                queryParamDetails.replace("moduleApplicable", "MT");
                break;
            case "WithEmptyModuleApplicable":
                queryParamDetails.replace("moduleApplicable", "");
                break;
            case "WithNullModuleApplicable":
                queryParamDetails.remove("moduleApplicable");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (ModuleApplicableData) {
            case "WithValidModuleApplicable_1":
            case "WithValidModuleApplicable_2":
            case "WithValidModuleApplicable_3":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithInvalidModuleApplicable":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_MODULE_APPLICABLE", "Incorrect ModuleApplicable:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyModuleApplicable":
            case "WithNullModuleApplicable":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "moduleApplicable must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the Module Applicable
     * WithValidRegulatoryAMLRisk - Very High
     * WithValidRegulatoryAMLRisk - High
     * WithValidRegulatoryAMLRisk - Medium
     * WithValidRegulatoryAMLRisk - Low
     * WithInvalidRegulatoryAMLRisk
     * WithEmptyRegulatoryAMLRisk
     * WithNullRegulatoryAMLRisk
     *
     * @param RegulatoryAMLRiskData
     */
    @Description("To check the customer data is getting processed correctly or not for the RegulatoryAMLRisk field")
    @Feature("Txn_Monitoring")
    @Test(priority = 16, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "RegulatoryAMLRiskData")
    public void testForRegulatoryAMLRisk(String RegulatoryAMLRiskData) {
        log.info("Getting started with the customerType test for " + RegulatoryAMLRiskData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (RegulatoryAMLRiskData) {
            case "WithValidRegulatoryAMLRisk_VeryHigh":
                queryParamDetails.replace("regulatoryAMLRisk", "VERY%20HIGH");
                break;
            case "WithValidRegulatoryAMLRisk_High":
                queryParamDetails.replace("regulatoryAMLRisk", "HIGH");
                break;
            case "WithValidRegulatoryAMLRisk_Medium":
                queryParamDetails.replace("regulatoryAMLRisk", "MEDIUM");
                break;
            case "WithValidRegulatoryAMLRisk_Low":
                queryParamDetails.replace("regulatoryAMLRisk", "LOW");
                break;
            case "WithInvalidRegulatoryAMLRisk":
                queryParamDetails.replace("regulatoryAMLRisk", "MT");
                break;
            case "WithEmptyRegulatoryAMLRisk":
                queryParamDetails.replace("regulatoryAMLRisk", "");
                break;
            case "WithNullRegulatoryAMLRisk":
                queryParamDetails.remove("regulatoryAMLRisk");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (RegulatoryAMLRiskData) {
            case "WithValidRegulatoryAMLRisk_VeryHigh":
            case "WithValidRegulatoryAMLRisk_High":
            case "WithValidRegulatoryAMLRisk_Medium":
            case "WithValidRegulatoryAMLRisk_Low":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithInvalidRegulatoryAMLRisk":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_AML_RISK", "Incorrect Aml Risk:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyRegulatoryAMLRisk":
            case "WithNullRegulatoryAMLRisk":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "regulatoryAMLRisk must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the LastRiskReviewDate
     *
     * WithValidLastRiskReviewDate
     * WithInValidLastRiskReviewDate
     * WithNullLastRiskReviewDate
     * WithEmptyLastRiskReviewDate
     *
     * @param lastRiskReviewDateData
     */
    @Description("To check the customer data is getting processed correctly or not for the LastRiskReviewDate field")
    @Feature("Txn_Monitoring")
    @Test(priority = 17, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "lastRiskReviewDateData")
    public void testForLastRiskReviewDate(String lastRiskReviewDateData) {
        log.info("Getting started with the customerType test for " + lastRiskReviewDateData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (lastRiskReviewDateData) {
            case "WithValidLastRiskReviewDate":
                queryParamDetails.replace("lastRiskReviewDate", "2023-05-23");
                break;
            case "WithInValidLastRiskReviewDate":
                queryParamDetails.replace("lastRiskReviewDate", "2200-22-22");
                break;
            case "WithEmptyLastRiskReviewDate":
                queryParamDetails.replace("lastRiskReviewDate", "");
                break;
            case "WithNullLastRiskReviewDate":
                queryParamDetails.remove("lastRiskReviewDate");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (lastRiskReviewDateData) {
            case "WithValidLastRiskReviewDate":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithInValidLastRiskReviewDate":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_RISK_DATE", "Incorrect OccupationType:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyLastRiskReviewDate":
            case "WithNullLastRiskReviewDate":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "lastRiskReviewDate must not be null, "), "Failed to create the entry in the validation Table");
                break;
        }
    }


    /**
     * Test cases for the LastRiskReviewDate
     *
     * WithValidLastRiskReviewDate
     * WithInValidLastRiskReviewDate
     * WithNullLastRiskReviewDate
     * WithEmptyLastRiskReviewDate
     *
     * @param nextRiskReviewDateData
     */
    @Description("To check the customer data is getting processed correctly or not for the NextRiskReviewDate field")
    @Feature("Txn_Monitoring")
    @Test(priority = 18, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "nextRiskReviewDateData")
    public void testForNextRiskReviewDate(String nextRiskReviewDateData) {
        log.info("Getting started with the customerType test for " + nextRiskReviewDateData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (nextRiskReviewDateData) {
            case "WithValidLastRiskReviewDate":
                queryParamDetails.replace("nextRiskReviewDate", "2025-05-23");
                break;
            case "WithInValidLastRiskReviewDate":
                queryParamDetails.replace("nextRiskReviewDate", "2200-22-22");
                break;
            case "WithEmptyLastRiskReviewDate":
                queryParamDetails.replace("nextRiskReviewDate", "");
                break;
            case "WithNullLastRiskReviewDate":
                queryParamDetails.remove("nextRiskReviewDate");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (nextRiskReviewDateData) {
            case "WithValidLastRiskReviewDate":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithInValidLastRiskReviewDate":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_RISK_DATE", "Incorrect OccupationType:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptyLastRiskReviewDate":
            case "WithNullLastRiskReviewDate":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "nextRiskReviewDate must not be null, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

    /**
     * Test cases for the Source
     * WithValidSource
     * WithInValidSource
     * WithNullSource
     * WithEmptySource
     *
     * @param sourceData
     */
    @Description("To check the customer data is getting processed correctly or not for the source field")
    @Feature("Txn_Monitoring")
    @Test(priority = 19, groups = {"Txn_Monitoring_Sanity"}, dataProvider = "sourceData")
    public void testForSource(String sourceData) {
        log.info("Getting started with the customerType test for " + sourceData);
        deleteUserData(customerRequestParamData().get("panNumber").toString());
        Map<String, Object> queryParamDetails = customerRequestParamData();
        HashMap<String, String> headerDetails = headerDetails();
        queryParamDetailsProcessCustomerCron.put("batchSize", "2");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (sourceData) {
            case "WithValidSource":
                queryParamDetails.replace("source", "LazyPay");
                break;
            case "WithValidSource_PS":
                queryParamDetails.replace("source", "PSCORE");
                break;
            case "WithInValidSource":
                queryParamDetails.replace("source", "LazyPatest");
                break;
            case "WithEmptySource":
                queryParamDetails.replace("source", "");
                break;
            case "WithNullSource":
                queryParamDetails.remove("source");
                break;
        }
        String customerRequestResponse = txnMonitorCreateCustomerRequestData.postWithNoResponseBody(queryParamDetails, headerDetails);
        log.info("response status code : " + customerRequestResponse);
        String userID = customerRequestParamData().get("userId").toString();
        processCustomerRequestAndValidate(userID, queryParamDetailsProcessCustomerCron, headerDetails);
        switch (sourceData) {
            case "WithValidSource":
                Assert.assertTrue(validateCustomerEntry(userID, "status", "CREATED"), "Failed to create the entry in the customer Table");
                break;
            case "WithInValidSource":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "INVALID_SOURCE", "Incorrect Source:"), "Failed to create the entry in the validation Table");
                break;
            case "WithEmptySource":
            case "WithNullSource":
                Assert.assertTrue(validateCustomerFailureValidation(userID, "ERR_MISSING_REQUIRED_FIELDS", "source must not be blank, "), "Failed to create the entry in the validation Table");
                break;
        }
    }

}
