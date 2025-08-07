package kyc.ckyc;

import api.kyc.apis.FetchV4kycStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import pojos.kyc.apis.KycGenericResponse;
import pojos.kyc.apis.KycStatusResponsePojo;
import util.DeleteUser;
import util.JacksonJsonUtil;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class V4KycStatusTests extends CommonMethodsImpl {
    public V4KycStatusTests() throws Exception {
    }

    @Feature("v4/kycStatus")
    @Description("To verify the v4/kycStatus API with valid & invalid data of uuid & source")
    @Test(priority = 1, dataProvider = "V4/kycstatus Testdata", dataProviderClass = BaseTestData.class)
    public void getV4KycStatus(String testCase, String uuid, String source) throws Exception {

        //Purge any existing user
        purgeUser(USER_MOBILE);

        if (testCase != "NOT_INITIATED" && testCase != "INVALID_UUID" && testCase != "EMPTY_UUID") {
            //calling InitiateAPI
            InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(uuid, VALID_PRODUCT);
            Assert.assertNotNull(initiateKYCV9pojo.kycCaseId, "check the error message");

        }

        FetchV4kycStatus v4kycStatus = new FetchV4kycStatus();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("uuid", uuid);
        queryParamDetails.put("source", source);
          // calling v4/kycStatus API
        KycGenericResponse<KycStatusResponsePojo> v4KycStatusResponsePojo = v4kycStatus.get(queryParamDetails, headerDetails);
        // converting the response pojo to string & then object to handle the linkedhashmap ClassCastException.
        String response = JacksonJsonUtil.getJsonString(v4KycStatusResponsePojo);
        v4KycStatusResponsePojo = JacksonJsonUtil.getGenericJsonObject(response, new TypeReference<KycGenericResponse<KycStatusResponsePojo>>() {
        });
        // Validations based on test-case.
        switch (testCase) {
            case "VALID_UUID":
                Assert.assertEquals(v4KycStatusResponsePojo.getStatus(), "SUCCESS");
                break;
            case "INVALID_UUID":
                Assert.assertEquals(v4KycStatusResponsePojo.getStatus(), "FAILED");
                Assert.assertEquals(v4KycStatusResponsePojo.getErrorCode(), "USER_NOT_FOUND");
                break;
            case "EMPTY_UUID":
                Assert.assertEquals(v4KycStatusResponsePojo.getStatus(), "FAILED");
                Assert.assertEquals(v4KycStatusResponsePojo.getErrorCode(), "INVALID_UUID");
                break;
            case "NOT_INITIATED":
                Assert.assertEquals(v4KycStatusResponsePojo.getStatus(), "FAILED");
                Assert.assertEquals(v4KycStatusResponsePojo.getData().kycStatus, "NOT_INITIATED");
                break;
            case "INVALID_SOURCE":
            case "EMPTY_SOURCE":
                Assert.assertEquals(v4KycStatusResponsePojo.getStatus(), "FAILED");
                Assert.assertEquals(v4KycStatusResponsePojo.getErrorCode(), "INVALID_SOURCE");
                break;
        }


    }

}

