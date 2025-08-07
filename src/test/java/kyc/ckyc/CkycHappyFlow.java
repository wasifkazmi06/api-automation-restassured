package kyc.ckyc;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.enums.DocumentTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.DocumentsPendingPojo;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import pojos.kyc.apis.UploadDocumentPojo;
import util.DeleteUser;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;
/**
 * @author Jyothi
 */
@Slf4j


public class CkycHappyFlow extends CommonMethodsImpl {


    public CkycHappyFlow() throws Exception {
    }

    @Description("Basic CKYC Happy Flow")
    @Feature("KYC")

    @Test(priority = 1)
    public void ckycHappyFlow() throws Exception {

        //Purge any existing user
       /* Properties properties = System.getProperties();
        String env = properties.getProperty("env");
        DeleteUser.deleteUserMethod(BaseTestData.USER_MOBILE, env, ""); */
        purgeUser(USER_MOBILE);

        uploadRequiredDocuments(TEST_USER_UUID,VALID_PRODUCT);

    }

    // This method will keep check the next doc to be uploaded until there is no pending doc to upload
    public String uploadRequiredDocuments(String uuid,String product) throws Exception {
        List<DocumentsPendingPojo> docsPending = null;
        String selfieJson = null;
        String nextDoc = null;
        DocumentTypeEnum nextDoctoUpload = null;
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(uuid,product);
        Assert.assertNotNull(initiateKYCV9pojo.kycCaseId);
        String kycCaseId = initiateKYCV9pojo.kycCaseId;
        log.info("This is kyc id " + initiateKYCV9pojo.kycCaseId);
        docsPending = initiateKYCV9pojo.documentsPending;
        if (docsPending.size() > 0) {
            nextDoc = docsPending.get(0).getDocTypeId();
            log.info("print: " + docsPending.get(0).docTypeId);
            log.info("print next doc: " + nextDoc);
            nextDoctoUpload = DocumentTypeEnum.valueOf(nextDoc);
        } else {
            log.info("No Pending Doc's to upload");
        }

        if (nextDoctoUpload != null) {
            switch (nextDoctoUpload) {
                case PAN_DOC:
                    UploadDocumentPojo uploadPanDocpojo = uploadDoc(uuid, kycCaseId, nextDoc, BaseTestData.PAN_dataJson);
                    Assert.assertNotNull(uploadPanDocpojo.docId);
                    Assert.assertEquals(uploadPanDocpojo.uploadStatus, "SUCCESS", "check the data");
                    Assert.assertEquals(uploadPanDocpojo.message, "Document uploaded successfully", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT);
                    break;
                case IPV_SELFIE:
                    selfieJson = BaseTestData.getSelfieJsonData();
                    log.info("selfie data: " + selfieJson);
                    UploadDocumentPojo uploadSelfieDocpojo = uploadDoc(uuid, kycCaseId, nextDoc, selfieJson);
                    Assert.assertEquals(uploadSelfieDocpojo.uploadStatus, "SUCCESS", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT);
                    break;
                case PERSONAL_DETAILS_INCOME_FORM:
                    UploadDocumentPojo uploadIncomeDocpojo = uploadDoc(uuid, kycCaseId, nextDoc, BaseTestData.INCOMEFORM_JSON);
                    Assert.assertEquals(uploadIncomeDocpojo.uploadStatus, "SUCCESS", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT);
                    break;
                case ADDRESS_PROOF_VERIFICATION:
                    UploadDocumentPojo uploadApvDocpojo = uploadDoc(uuid, kycCaseId, nextDoc, BaseTestData.APV_dataJson);
                    Assert.assertEquals(uploadApvDocpojo.uploadStatus, "SUCCESS", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT);
                    break;
                case USER_REVIEW_DETAILS:
                    UploadDocumentPojo uploadReviewDetailspojo = uploadDoc(uuid, kycCaseId, nextDoc, "true");
                    Assert.assertEquals(uploadReviewDetailspojo.uploadStatus, "SUCCESS", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT);
                    break;
                case LOCATION_DETAILS:
                    UploadDocumentPojo uploadLocationDetailspojo = uploadDoc(uuid, kycCaseId, nextDoc, BaseTestData.LOCATION_JSON);
                    Assert.assertEquals(uploadLocationDetailspojo.uploadStatus, "SUCCESS", "check the data");
                    //uploadRequiredDocuments();
                    break;
                default:
                    log.info("no docs to fetch");
            }
        }
        // Verify the KYC status in initiate call
        InitiateKYCV9Pojo initiateKYCV9 = initiateKYCV9(uuid, VALID_PRODUCT);
        Assert.assertEquals(initiateKYCV9.kycStatus, "SUCCESS");
        String successKycCaseId = initiateKYCV9.kycCaseId;
        log.info("KYC is successful for the user: " + uuid);
        return successKycCaseId;

    }
}