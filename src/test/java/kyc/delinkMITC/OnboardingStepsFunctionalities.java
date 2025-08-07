package kyc.delinkMITC;

import api.kyc.apis.BifrostResolveSteps;
import api.kyc.apis.SignKFS;
import api.kyc.apis.SignMITC;
import api.platform.GetEligibleProducts;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import kyc.BaseTestData;
import kyc.ckyc.CommonMethodsImpl;
import kyc.enums.DocumentTypeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import pojos.kyc.apis.BifrostResolveStepsResponsePojo;
import pojos.kyc.apis.DocumentsPendingPojo;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import pojos.kyc.apis.UploadDocumentPojo;
import pojos.platform.getEligibleProducts.GetProductsResponsePojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class OnboardingStepsFunctionalities extends CommonMethodsImpl {
    public OnboardingStepsFunctionalities() throws Exception {
    }

    BifrostResolveSteps resolveSteps = new BifrostResolveSteps();
    GetEligibleProducts eligibleProducts = new GetEligibleProducts();
    SignKFS submitkfs = new SignKFS();
    SignMITC submit_mitc = new SignMITC();

    @Step
    @SneakyThrows
    public String uploadRequiredDocuments(String uuid,String product,String panJson) throws Exception {
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
                    UploadDocumentPojo uploadPanDocpojo = uploadDoc(uuid, kycCaseId, nextDoc, panJson);
                    Assert.assertNotNull(uploadPanDocpojo.docId);
                    Assert.assertEquals(uploadPanDocpojo.uploadStatus, "SUCCESS", "check the data");
                    Assert.assertEquals(uploadPanDocpojo.message, "Document uploaded successfully", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT,panJson);
                    break;
                case IPV_SELFIE:
                    selfieJson = BaseTestData.getSelfieJsonData();
                    log.info("selfie data: " + selfieJson);
                    UploadDocumentPojo uploadSelfieDocpojo = uploadDoc(uuid, kycCaseId, nextDoc, selfieJson);
                    Assert.assertEquals(uploadSelfieDocpojo.uploadStatus, "SUCCESS", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT,panJson);
                    break;
                case PERSONAL_DETAILS_INCOME_FORM:
                    UploadDocumentPojo uploadIncomeDocpojo = uploadDoc(uuid, kycCaseId, nextDoc, BaseTestData.INCOMEFORM_JSON);
                    Assert.assertEquals(uploadIncomeDocpojo.uploadStatus, "SUCCESS", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT,panJson);
                    break;
                case ADDRESS_PROOF_VERIFICATION:
                    UploadDocumentPojo uploadApvDocpojo = uploadDoc(uuid, kycCaseId, nextDoc, BaseTestData.APV_dataJson);
                    Assert.assertEquals(uploadApvDocpojo.uploadStatus, "SUCCESS", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT,panJson);
                    break;
                case USER_REVIEW_DETAILS:
                    UploadDocumentPojo uploadReviewDetailspojo = uploadDoc(uuid, kycCaseId, nextDoc, "true");
                    Assert.assertEquals(uploadReviewDetailspojo.uploadStatus, "SUCCESS", "check the data");
                    uploadRequiredDocuments(uuid,VALID_PRODUCT,panJson);
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
    @Step
    @SneakyThrows
    public GetProductsResponsePojo resolveStepsForInline(String mobile, String uuid, String product) {

        String stepRequest;
        int inlineRetryCount = 1;
        Map<String, Object> queryParamDetailsInitiate = new HashMap<>();
        HashMap<String, String> headerDetailsInitiate = new HashMap<>();
        headerDetailsInitiate.put("cache-control", "no-cache");

        stepRequest = new StringTemplate().replace("identifier", uuid)
                .replace("product", product).toString();
        BifrostResolveStepsResponsePojo stepresponse = resolveSteps.post(queryParamDetailsInitiate, headerDetailsInitiate, stepRequest);
        inlineRetryCount++;
        log.info("Bifrost : Onboarding Status is : " + stepresponse.status);
        log.info("Bifrost : Next Step in onboarding is : " + stepresponse.nextStep);

        while (stepresponse.nextStep.equalsIgnoreCase("INLINE")) {
            if(inlineRetryCount<=3)
            {stepresponse = resolveSteps.post(queryParamDetailsInitiate, headerDetailsInitiate, stepRequest);inlineRetryCount++;}
            else{log.error("Issue with AMS request/response for inline step");}
            break;}

        GetProductsResponsePojo eligibleProductsList = null;
        if (stepresponse.nextStep.equalsIgnoreCase("KFS")) {
            log.info("Bifrost : Next Step in onboarding is : " + stepresponse.nextStep);
            queryParamDetailsInitiate.put("mobile", mobile);
            try {
                eligibleProductsList = eligibleProducts.get(queryParamDetailsInitiate, headerDetailsInitiate);
                log.info("eligible products with decision are : " + eligibleProductsList.getEligibleProducts().iterator());
                Assert.assertNotNull(eligibleProductsList.getEligibleProducts());
                Assert.assertEquals(eligibleProductsList.getEligibleProducts().get(0).type, "bbpsDecision", "Failed to update the eligibleProducts in platform");
                log.info("Inline Processed Successfully and eligibleProducts are updated in platform");
            }catch(Exception e){
                log.error("Error while updating eligibleProducts in platform db");
            }
        }
        return eligibleProductsList;
    }
    @Step
    @SneakyThrows
    public void signKFS(String userId,String uuid){
        String kfsRequest;
        Map<String, Object> queryParamDetailsInitiate = new HashMap<>();
        HashMap<String, String> headerDetailsInitiate = new HashMap<>();
        headerDetailsInitiate.put("cache-control", "no-cache");

        kfsRequest = new StringTemplate().replace("userId", userId).replace("umUuid",uuid)
                .replace("source", "LP").replace("deviceIp","12.11.112.3").replace("userJourneySource","")
                .replace("type","V7").toString();
        try {
            Response sign_kfs_response = submitkfs.postWithResponse(queryParamDetailsInitiate, headerDetailsInitiate, kfsRequest);
            log.info("KFS signed successfully and API response is : " + sign_kfs_response.statusCode());
            Assert.assertEquals(sign_kfs_response.statusCode(), 200, "failed to sign kfs");
        }catch(Exception e){
            log.error("KFS sign request failed " +e.getMessage());
        }


    }
    @Step
    @SneakyThrows
    public void signMITC(String userId,String uuid){
        Map<String, Object> queryParamDetailsInitiate = new HashMap<>();
        HashMap<String, String> headerDetailsInitiate = new HashMap<>();
        headerDetailsInitiate.put("cache-control", "no-cache");

        String mitcRequest = signMITCRequestPayload(uuid, userId);
        try{
            Response mitcresponse = submit_mitc.postWithResponse(queryParamDetailsInitiate, headerDetailsInitiate, mitcRequest);
            log.info("MITC signed successfully and API response is : " + mitcresponse.statusCode());
            Assert.assertEquals(mitcresponse.statusCode(),200,"failed to sign mitc");
        }catch(Exception e){
            log.error("MITC sign request failed " +e.getMessage());
        }
    }
}