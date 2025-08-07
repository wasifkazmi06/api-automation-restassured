package kyc.ckyc;

import api.kyc.apis.LatestSuccessKyc;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.KycGenericResponse;
import pojos.kyc.apis.KycStatusResponsePojo;
import util.DeleteUser;
import util.JacksonJsonUtil;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class V1LatestSuccessKycTests extends CommonMethodsImpl {
    public V1LatestSuccessKycTests() throws Exception {
    }

    @Feature("v1/latest-success-kyc")
    @Description("To verify the v1/latest-success-kyc API with valid & invalid data of uuid & source")
    @Test(priority = 1, dataProvider = "V1/latestSuccessKyc Testdata", dataProviderClass = BaseTestData.class)
    public void getV1LatestSuccessKyc(String testCase, String uuid, String source) throws Exception {

        //Purge any existing user
        purgeUser(USER_MOBILE);

        LatestSuccessKyc latestSuccessKyc = new LatestSuccessKyc();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("uuid", uuid);
        queryParamDetails.put("source", source);

        // calling v1/latest-success-kyc API
        KycGenericResponse<KycStatusResponsePojo> v1LatestSuccessKycPojo = latestSuccessKyc.get(queryParamDetails, headerDetails);
        // converting the response pojo to string & then object to handle the linkedhashmap ClassCastException.
        String response = JacksonJsonUtil.getJsonString(v1LatestSuccessKycPojo);
        v1LatestSuccessKycPojo = JacksonJsonUtil.getGenericJsonObject(response, new TypeReference<KycGenericResponse<KycStatusResponsePojo>>() {
        });

        // Validations based on test-case.
        switch (testCase) {
            case "INVALID_UUID":
                Assert.assertEquals(v1LatestSuccessKycPojo.getErrorCode(), "USER_NOT_FOUND", "check the error code");
                Assert.assertEquals(v1LatestSuccessKycPojo.getStatus(), "FAILED", "check api status");
                break;
            case "EMPTY_UUID":
                Assert.assertEquals(v1LatestSuccessKycPojo.getErrorCode(), "INVALID_UUID", "check the error code");
                Assert.assertEquals(v1LatestSuccessKycPojo.getStatus(), "FAILED", "check api status");
                break;
            case "NOT_INITIATED":
                Assert.assertEquals(v1LatestSuccessKycPojo.getErrorCode(), "KYC_IN_SUCCESS_STATE_NOT_FOUND", "check the error code");
                Assert.assertEquals(v1LatestSuccessKycPojo.getStatus(), "FAILED", "check the api status");
                break;
            case "INVALID_SOURCE":
            case "EMPTY_SOURCE":
                Assert.assertEquals(v1LatestSuccessKycPojo.getErrorCode(), "INVALID_SOURCE", "check the error code");
                Assert.assertEquals(v1LatestSuccessKycPojo.getStatus(), "FAILED", "check the api status");
                break;


        }


    }
}
