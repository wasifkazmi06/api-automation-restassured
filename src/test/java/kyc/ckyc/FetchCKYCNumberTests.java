package kyc.ckyc;

import api.kyc.apis.FetchCKYCNumber;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.DBValidations;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.CKYCPojo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FetchCKYCNumberTests extends BaseTestData {

    public FetchCKYCNumberTests() throws Exception {
    }

    FetchCKYCNumber getCkycNumber = new FetchCKYCNumber();
    DBValidations dbData = new DBValidations();

    @Description("Fetch the ckyc number of KYC'd user")
    @Feature("CKYC_NUMBER")
    @Test(priority = 1,dataProvider = "getCkycNumberTestdata")
    public void getCKYCNumberValidations(String testcase, String uuid, String product) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        queryParamDetails.put("product",product);
        CKYCPojo ckycNumberResponse = getCkycNumber.get(queryParamDetails,headerDetails,uuid);
        switch(testcase){
            case "ValidUuid&Product":
                String db_ckycNumber = dbData.validateCKYCNumber(uuid,"ckyc_no");
                log.info("response status is : " +ckycNumberResponse.kycStatus+ " for test : "+testcase);
                Assert.assertEquals(ckycNumberResponse.kycStatus,"SUCCESS","Received incorrect status");
                Assert.assertEquals(ckycNumberResponse.ckycNumber,db_ckycNumber,"Received incorrect response");
                break;
            case "CKYC_FailedUser":
            case "InvalidProduct":
            case "WithNullProduct":
                Assert.assertEquals(ckycNumberResponse.status,400,"Received incorrect status");
                Assert.assertEquals(ckycNumberResponse.errorCode,"CASE_NOT_CKYC","Received incorrect errorCode");
                Assert.assertEquals(ckycNumberResponse.message,"The Case does not belong to CKYC","Received incorrect errorMessage");
                break;
            case "NonKYC'dUuid":
                Assert.assertEquals(ckycNumberResponse.status,400,"Received incorrect status");
                Assert.assertEquals(ckycNumberResponse.errorCode,"DOCUMENT_NOT_FOUND","Received incorrect errorCode");
                Assert.assertEquals(ckycNumberResponse.message,"Document not found","Received incorrect errorMessage");
                break;

        }

    }

}
