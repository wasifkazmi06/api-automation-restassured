package kyc.ckyc;

import api.kyc.apis.FetchCaseDetails;
import api.kyc.apis.FetchV3CaseDetails;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.DBValidations;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.V2CaseDetailsResponsePojo;
import pojos.kyc.apis.V3CaseDetailsResponsePojo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CaseDetailsTests extends CommonMethodsImpl {
    public String kycCaseId;
    CkycHappyFlow ckycHappyFlow = new CkycHappyFlow();

    public CaseDetailsTests() throws Exception {
    }

    FetchCaseDetails caseDetails = new FetchCaseDetails();
    FetchV3CaseDetails v3_casedetails = new FetchV3CaseDetails();
    DBValidations dbData = new DBValidations();

    @Description("Fetch the case details of KYC'd user")
    @Feature("CASE_DETAILS")
    @Test(priority = 1,dataProvider = "v2CaseDetailsTestdata")
    public void v2CaseDetailsValidations(String testcase, String uuid, String kycTypeId, Boolean full, String product) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        queryParamDetails.put("uuid",uuid);
        queryParamDetails.put("kycTypeId",kycTypeId);
        queryParamDetails.put("full", full);
        queryParamDetails.put("product",product);
        V2CaseDetailsResponsePojo v2caseDetailsResponse = caseDetails.get(queryParamDetails,headerDetails);
        int db_kycCaseId = dbData.validateKYCCaseDetailId(uuid,"id");
        switch(testcase){
            case "ValidParams":
            case "WithKYCType_CKYC":
            case "WithKYCType_AADHHAR":
            case "WithFullValue_False":
                log.info("response status is : " +v2caseDetailsResponse.id+ " for test : "+testcase);
                Assert.assertEquals(v2caseDetailsResponse.id,db_kycCaseId,"Received incorrect id / response");
                break;
            case "IncorrectUuid":
            case "InvalidProduct":
            case "WithNullProduct":
                Assert.assertEquals(v2caseDetailsResponse.status,404,"Received incorrect status");
                Assert.assertEquals(v2caseDetailsResponse.errorCode,"KYC_CASE_NOT_FOUND","Received incorrect status");
                Assert.assertEquals(v2caseDetailsResponse.message,"Kyc with given kyc-Id does not exist.","Received incorrect status");
                break;
            case "WithNullUuid":
                Assert.assertEquals(v2caseDetailsResponse.status,400,"Received incorrect status");
                Assert.assertEquals(v2caseDetailsResponse.message,"Empty/Invalid uuid in the request","Received incorrect status");
                break;

        }

    }
    @Description("Fetch the case details of KYC'd user")
    @Feature("CASE_DETAILS")
    @Test(priority = 2,dataProvider = "v3CaseDetailsTestdata")
    public void v3CaseDetailsValidations(String testcase, String uuid, String source) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        queryParamDetails.put("uuid",uuid);
        queryParamDetails.put("source",source);
       // V3CaseDetailsResponsePojo v3caseDetailsResponse = v3_casedetails.get(queryParamDetails, headerDetails);
        switch(testcase){
            case "ValidUuid&Source_BNPL":
                //Purge any existing user
                purgeUser(USER_MOBILE);
                // creating payufin_kyc for test user
                kycCaseId = ckycHappyFlow.uploadRequiredDocuments(TEST_USER_UUID, VALID_PRODUCT);
                V3CaseDetailsResponsePojo v3caseDetailsResponse = v3_casedetails.get(queryParamDetails, headerDetails);
                log.info("response status is : " +v3caseDetailsResponse.status+ " for test : "+testcase);
                Assert.assertEquals(v3caseDetailsResponse.status,"SUCCESS","Received incorrect status");
            break;
            case "IncorrectUuid":
            case "WithNullUuid":
                V3CaseDetailsResponsePojo v3caseDetailsResponse1 = v3_casedetails.get(queryParamDetails, headerDetails);
                Assert.assertEquals(v3caseDetailsResponse1.status,"FAILED","Received incorrect status");
                Assert.assertEquals(v3caseDetailsResponse1.errorCode,"USER_NOT_FOUND","Received incorrect errorCode");
                Assert.assertEquals(v3caseDetailsResponse1.errorMsg,"User not found","Received incorrect errorMessage");
                break;
            case "InvalidSource":
            case "WithNullSource":
                V3CaseDetailsResponsePojo v3caseDetailsResponse2 = v3_casedetails.get(queryParamDetails, headerDetails);
                Assert.assertEquals(v3caseDetailsResponse2.status,"FAILED","Received incorrect status");
                Assert.assertEquals(v3caseDetailsResponse2.errorCode,"INVALID_SOURCE","Received incorrect errorCode");
                Assert.assertEquals(v3caseDetailsResponse2.errorMsg,"Source can only be BNPL/BBPS/XPRESS","Received incorrect errorMessage");
                break;

        }

    }
}
