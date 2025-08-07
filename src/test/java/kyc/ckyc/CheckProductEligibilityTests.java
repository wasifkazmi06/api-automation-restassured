package kyc.ckyc;

import api.kyc.apis.CheckProductEligibility;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.KycConstants;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.CheckEligibilityPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CheckProductEligibilityTests extends BaseTestData {

    public CheckProductEligibilityTests() throws Exception {
    }

    CheckProductEligibility checkEligibility = new CheckProductEligibility();

    @Description("Check product eligibility")
    @Feature("CHECK_ELIGIBILITY")
    @Test(priority = 1,dataProvider = "eligibilityTestdata")
    public void checkEligibilityValidations(String testcase, String uuid, String product) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        String request= new StringTemplate(KycConstants.CHECK_ELIGIBILITY_REQUEST).replace("product",product).replace("uuid", uuid).toString();
        CheckEligibilityPojo eligibilityResponse = checkEligibility.post(queryParamDetails,headerDetails,request);
        log.info("response status is : " +eligibilityResponse.getResponseList().get(0).status+ " for test : "+testcase);
        Assert.assertEquals(eligibilityResponse.getResponseList().get(0).status,"FAILED","Received incorrect status");

    }
}
