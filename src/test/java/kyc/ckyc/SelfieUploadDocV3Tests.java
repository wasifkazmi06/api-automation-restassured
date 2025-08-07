package kyc.ckyc;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.enums.DocumentTypeEnum;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import pojos.kyc.apis.UploadDocumentPojo;
import util.DeleteUser;

import java.io.FileNotFoundException;
import java.util.Properties;

public class SelfieUploadDocV3Tests extends CommonMethodsImpl {
    public static String kycCaseId;

    public SelfieUploadDocV3Tests() throws Exception {
    }

    @Feature("v3/uploadDocuments for SELFIE")
    @Description("To verify the IPV_SELFIE upload using v3/uploadDocuments API with multiple scenarios ")
    @Test(priority = 1, dataProvider = "SELFIE Testdata", dataProviderClass = BaseTestData.class)
    public void uploadDocumentsV3SelfieDoc(String selfieCase, String selfieData) throws Exception {
        // do not purge the user if same panNumber is same to test the can't upload scenario.
        if (!selfieCase.equals("RE_UPLOAD_SELFIE")) {
            purgeUser(USER_MOBILE);
        }
        //calling InitiateAPI
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(TEST_USER_UUID, VALID_PRODUCT);
        kycCaseId = initiateKYCV9pojo.kycCaseId;
        // calling V3 upload API
        UploadDocumentPojo uploadPanDocpojo = uploadDoc(TEST_USER_UUID, kycCaseId, DocumentTypeEnum.IPV_SELFIE.toString(), selfieData);
        switch (selfieCase) {
            case "VALID_SELFIE":
                Assert.assertEquals(uploadPanDocpojo.uploadStatus, "SUCCESS", "check the data");
                break;
            case "RE_UPLOAD_SELFIE":
                Assert.assertEquals(uploadPanDocpojo.message, "This document is already UPLOADED", "check the error code");
                Assert.assertEquals(uploadPanDocpojo.errorCode, "DOCUMENT_CANNOT_UPDATE", "check the error code");
                break;
            case "INVALID_SELFIE":
                Assert.assertEquals(uploadPanDocpojo.message, "Invalid document encoding", "check the error code");
                Assert.assertEquals(uploadPanDocpojo.errorCode, "INVALID_ENCODING", "check the error code");
                break;
        }
    }
}
