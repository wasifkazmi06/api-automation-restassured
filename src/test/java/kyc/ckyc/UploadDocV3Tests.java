package kyc.ckyc;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.enums.DocumentTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import pojos.kyc.apis.UploadDocumentPojo;
import util.DeleteUser;

import java.io.FileNotFoundException;
import java.util.Properties;

@Slf4j
public class UploadDocV3Tests extends CommonMethodsImpl {
    public static String kycCaseId;
    public static String PAN_NAME = "TestName";

    public UploadDocV3Tests() throws Exception {
    }


    @Feature("v3/uploadDocuments for PAN-Doc")
    @Description("To verify the PAN-DOC upload using v3/uploadDocuments API with multiple scenarios ")
    @Test(priority = 1, dataProvider = "PAN-DOC Testdata", dataProviderClass = BaseTestData.class)
    public void uploadDocumentsV3PanDoc(String panCase, String panData) throws Exception {
        // constructing pan data request
        JSONObject reqObject = ApiRequests.getPanDocRequest(panData, PAN_NAME, "true");
        log.info("logging reqObject :: " + reqObject);
        // do not purge the user if same panNumber is same to test the can't upload scenario.
        if (!panCase.equals("DUPLICATE_PAN_NUMBER")) {
            purgeUser(USER_MOBILE);
        }
        //calling InitiateAPI
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(TEST_USER_UUID, VALID_PRODUCT);
        kycCaseId = initiateKYCV9pojo.kycCaseId;

        // Calling upload V3 API
        UploadDocumentPojo uploadPanDocpojo = uploadDoc(TEST_USER_UUID, kycCaseId, DocumentTypeEnum.PAN_DOC.toString(), reqObject.toString());
        switch (panCase) {
            case "VALID_PAN_NUMBER":
                Assert.assertEquals(uploadPanDocpojo.uploadStatus, "SUCCESS", "check the data");
                Assert.assertEquals(uploadPanDocpojo.message, "Document uploaded successfully", "check the data");
                break;
            case "INVALID_PAN_NUMBER":
                Assert.assertEquals(uploadPanDocpojo.errorCode, "INVALID_PAN", "check the PAN number in request");
                break;
            case "DUPLICATE_PAN_NUMBER":
                log.info("check if this is coming");
                Assert.assertEquals(uploadPanDocpojo.message, "This document is already VERIFIED", "check the pan data");
                Assert.assertEquals(uploadPanDocpojo.errorCode, "DOCUMENT_CANNOT_UPDATE", "check the error");
                break;
        }
    }
    /*
    Verify the v3/uploadDocuments API with invalid request params
    * Invalid kycCaseId
    * Without uploadRequest
    * Empty kycCaseId

     */

    @Feature("v3/uploadDocuments for PAN-Doc")
    @Description("To verify v3/uploadDocuments API with invalid request params ")
    @Test(priority = 2, dataProvider = "kycCaseId testdata", dataProviderClass = BaseTestData.class)
    public void uploadDocumentsV3(String kycCaseId) throws Exception {
        // constructing pan data request
        JSONObject reqObject = ApiRequests.getPanDocRequest(VALID_PAN_NUMBER, PAN_NAME, "true");
        UploadDocumentPojo uploadPanDocpojo = uploadDoc(TEST_USER_UUID, kycCaseId, DocumentTypeEnum.PAN_DOC.toString(), reqObject.toString());
        // use-cases for kycCaseId
        switch (kycCaseId) {
            case INVALID_KYC_CASE_ID:
                Assert.assertEquals(uploadPanDocpojo.errorCode, "KYC_CASE_NOT_FOUND", "check the kycCaseId in request");
                break;
            case EMPTY_KYC_CASE_ID:
                Assert.assertEquals(uploadPanDocpojo.errorCode, "INVALID_KYC_CASE_ID", "check the error code");
                Assert.assertEquals(uploadPanDocpojo.message, "KycCaseID cannot be null or less than 0 or non-numeric",
                        "check the error");
                break;
        }
    }
}
