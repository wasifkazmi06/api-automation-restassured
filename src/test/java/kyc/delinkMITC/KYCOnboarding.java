package kyc.delinkMITC;

import api.platform.GetUserData;
import kyc.DBValidations;
import kyc.ckyc.ApiRequests;
import kyc.ckyc.CkycHappyFlow;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.testng.Assert;
import pojos.platform.getEligibleProducts.GetProductsResponsePojo;
import pojos.platform.getUserData.UserData;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KYCOnboarding extends  OnboardingStepsFunctionalities {


    public KYCOnboarding() throws Exception {
    }

    String pan = System.getProperty("pan");
    String product = System.getProperty("product");
    String mobile = System.getProperty("mobile");
    String panName = System.getProperty("PANName");
    JSONObject reqObject = ApiRequests.getPanDocRequest(pan, panName, "true");
    //   OnboardingStepsFunctionalities onboardingSteps = new OnboardingStepsFunctionalities();
    CkycHappyFlow uploadDocs = new CkycHappyFlow();
    GetUserData userData = new GetUserData();
    DBValidations dbData = new DBValidations();


    @Test()
    public void kycOnboardingSteps() throws Exception {

        Map<String, Object> queryParamDetailsInitiate = new HashMap<>();
        HashMap<String, String> headerDetailsInitiate = new HashMap<>();
        queryParamDetailsInitiate.put("mobile", mobile);
        UserData platformUserData= userData.get(queryParamDetailsInitiate,headerDetailsInitiate);
        log.info("userD30details : "+platformUserData.getUserD30Details());
        //complete ckyc
        //  String kycCaseId = uploadDocs.uploadRequiredDocuments(PAN_UPLOADED_USER,VALID_PRODUCT);
        String kycCaseId = uploadRequiredDocuments(platformUserData.getUmUuid(),VALID_PRODUCT,reqObject.toString());
        System.out.println("All KYC documents uploaded for " +kycCaseId);

        //Call bifrost API for inline step
        GetProductsResponsePojo eligibliProducts = resolveStepsForInline(mobile,platformUserData.getId(),product);
        //Verify DB - user_elgibility table for bbps entry
        Assert.assertTrue(dbData.validateUserEligibilityEntry(eligibliProducts.userId,product), "Failed to create bbps product entry in userEligibility");

        //sign KFS and MITC
        signKFS(platformUserData.getId(),platformUserData.getUmUuid());
        signMITC(platformUserData.getId(),platformUserData.getUmUuid());
    }

}