package docstore;

import api.docStore.StoreSensitiveInfo;

import io.qameta.allure.Step;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.docStore.StoreSensitiveInfoPojo;
import util.StringTemplate;

import java.util.HashMap;


public class StoreSensitiveInfoSteps {

    public String refkey= null;

    public StoreSensitiveInfoSteps() throws Exception {

    }

    StoreSensitiveInfo storeSensitiveInfoAPI = new StoreSensitiveInfo();

    @BeforeClass
    public void storeSensitiveInfoStepsPrerequisites() {

    }


    @Step
    public void storeSensitiveInfoForValidUUID(String uuid, String senstiveInfoType, String senstiveInfoValue) throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", SensitiveInfoData.AUTH_TOKEN);

        String storeDocumentRequest = new StringTemplate(DocStoreConstants.STORE_SENSITIVE_INFO_REQUEST)
                .replace("uuid", uuid)
                .replace("sensitiveInfoType", senstiveInfoType)
                .replace("sensitiveInfoValue", senstiveInfoValue)
                .replace("status", SensitiveInfoData.UNVERIFED)
                .replace("allowDuplitcate", SensitiveInfoData.DUPLICATE_FLAG.toString())
                .toString();

        JSONObject storeDocumentRequestJson = new JSONObject(storeDocumentRequest);

        StoreSensitiveInfoPojo storeSensitiveInfoPojo = storeSensitiveInfoAPI.post(headerDetails, storeDocumentRequestJson.toString());
        this.refkey = storeSensitiveInfoPojo.refKey;
        Assert.assertEquals(storeSensitiveInfoPojo.responseStatus, "SUCCESS");
        Assert.assertNotNull(storeSensitiveInfoPojo.refKey);
    }

    @Step
    public void storeDocumentForInvalidUUID() throws Exception {

        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", SensitiveInfoData.AUTH_TOKEN);

        String storeDocumentRequest = new StringTemplate(DocStoreConstants.STORE_SENSITIVE_INFO_REQUEST)
                .replace("uuid", SensitiveInfoData.INVALID_UUID)
                .replace("sensitiveInfoType", SensitiveInfoData.PAN)
                .replace("sensitiveInfoValue", SensitiveInfoData.PAN_NUMBER)
                .replace("status", SensitiveInfoData.UNVERIFED)
                .replace("allowDuplitcate", SensitiveInfoData.DUPLICATE_FLAG.toString())
                .toString();

        JSONObject storeDocumentRequestJson = new JSONObject(storeDocumentRequest);

        StoreSensitiveInfoPojo storeSensitiveInfoPojo = storeSensitiveInfoAPI.post(headerDetails, storeDocumentRequestJson.toString());
        Assert.assertEquals(storeSensitiveInfoPojo.responseStatus, "FAILED");


    }
}
