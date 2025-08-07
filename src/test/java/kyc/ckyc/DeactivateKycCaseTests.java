package kyc.ckyc;

import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.DeactivateKycCaseResponse;
import pojos.kyc.apis.KycGenericResponse;
import util.DeleteUser;
import util.JacksonJsonUtil;

import java.io.FileNotFoundException;
import java.util.Properties;

@Slf4j
public class DeactivateKycCaseTests extends CommonMethodsImpl {

    public String kycCaseId;
    CkycHappyFlow ckycHappyFlow = new CkycHappyFlow();

    public DeactivateKycCaseTests() throws Exception {
    }

    @Feature("v1/deactivate-kyc-case")
    @Description("To verify the v1/deactivate-kyc-case API happy path flow")
    @Test(priority = 1)
    public void deactivateKycCaseId() throws Exception {
        //Purge any existing user
        purgeUser(USER_MOBILE);

        // creating payufin_kyc for test user
        kycCaseId = ckycHappyFlow.uploadRequiredDocuments(TEST_USER_UUID, VALID_PRODUCT);
        log.info("logging the kyc caseId ::" + kycCaseId);
        // calling deactivate-kyc-case API
        KycGenericResponse<DeactivateKycCaseResponse> deactivateKycCaseResponse = deactivateKycCase(kycCaseId);

        // converting the response pojo to string & then object to handle the linkedhashmap ClassCastException.
        String response = JacksonJsonUtil.getJsonString(deactivateKycCaseResponse);
        deactivateKycCaseResponse = JacksonJsonUtil.getGenericJsonObject(response, new TypeReference<KycGenericResponse<DeactivateKycCaseResponse>>() {
        });
        Assert.assertEquals(deactivateKycCaseResponse.getStatus(), "SUCCESS");

    }

    @Feature("v1/deactivate-kyc-case")
    @Description("To verify the v1/deactivate-kyc-case API with valid kycCaseId & invalid userId")
    @Test(priority = 2)
    public void deactivateKycCaseWithInvalidUuid() throws Exception {

        // calling deactivate-kyc-case API
        KycGenericResponse<DeactivateKycCaseResponse> deactivateKycCaseResponse = deactivateKycCase(kycCaseId, VALID_SOURCE_BNPL, INVALID_USER_UUID);

        // converting the response pojo to string & then object to handle the linkedhashmap ClassCastException.
        String response = JacksonJsonUtil.getJsonString(deactivateKycCaseResponse);
        deactivateKycCaseResponse = JacksonJsonUtil.getGenericJsonObject(response, new TypeReference<KycGenericResponse<DeactivateKycCaseResponse>>() {
        });
        Assert.assertEquals(deactivateKycCaseResponse.getStatus(), "FAILED");
        Assert.assertEquals(deactivateKycCaseResponse.getErrorCode(), "INVALID_UUID_FOR_KYC_CASE");
    }

    @Feature("v1/deactivate-kyc-case")
    @Description("To verify the v1/deactivate-kyc-case API with valid kycCaseId & invalid userId & source")
    @Test(priority = 3, dataProvider = "deactivateKycCasetestdata", dataProviderClass = BaseTestData.class)
    public void deactivateKycCaseNegativeTests(String testcase, String source, String uuid) throws Exception {

        // calling deactivate-kyc-case API
        KycGenericResponse<DeactivateKycCaseResponse> deactivateKycCaseResponse = deactivateKycCase(kycCaseId, source, uuid);

        // converting the response pojo to string & then object to handle the linkedhashmap ClassCastException.
        String response = JacksonJsonUtil.getJsonString(deactivateKycCaseResponse);
        deactivateKycCaseResponse = JacksonJsonUtil.getGenericJsonObject(response, new TypeReference<KycGenericResponse<DeactivateKycCaseResponse>>() {
        });

        switch (testcase) {
            case "INVALID_SOURCE":
                Assert.assertEquals(deactivateKycCaseResponse.getStatus(), "FAILED");
                Assert.assertEquals(deactivateKycCaseResponse.getErrorCode(), "INVALID_SOURCE");
                break;
            case "EMPTY_SOURCE":
            case "EMPTY_UUID":
            case "NO_SOURCE_FEILD":
            case "NO_UUID_FEILD":
                Assert.assertEquals(deactivateKycCaseResponse.getStatus(), "SUCCESS");
                break;
        }

    }

    @Feature("v1/deactivate-kyc-case")
    @Description("To verify the v1/deactivate-kyc-case API with Invalidvalid kycCaseId")
    @Test(priority = 3, dataProvider = "kycCaseId testdata", dataProviderClass = BaseTestData.class)
    public void deactivateKycCaseWithInvalidKycCaseIdTests(String kycCaseId) throws Exception{
        // calling deactivate-kyc-case API
        KycGenericResponse<DeactivateKycCaseResponse> deactivateKycCaseResponse = deactivateKycCase(kycCaseId);

        // converting the response pojo to string & then object to handle the linkedhashmap ClassCastException.
        String response = JacksonJsonUtil.getJsonString(deactivateKycCaseResponse);
        deactivateKycCaseResponse = JacksonJsonUtil.getGenericJsonObject(response, new TypeReference<KycGenericResponse<DeactivateKycCaseResponse>>() {
        });
        switch (kycCaseId){
            case INVALID_KYC_CASE_ID:
                Assert.assertEquals(deactivateKycCaseResponse.getStatus(),"FAILED");
                Assert.assertEquals(deactivateKycCaseResponse.getErrorCode(),"KYC_CASE_NOT_FOUND");
                break;
            case EMPTY_KYC_CASE_ID:
                Assert.assertEquals(deactivateKycCaseResponse.getErrorCode(),"INVALID_KYC_CASE_ID");
                Assert.assertEquals(deactivateKycCaseResponse.getStatus(),"FAILED");
                break;

        }


    }


}

