package docstore;

import api.docStore.RetrieveSensitiveInfo;
import io.qameta.allure.Step;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.docStore.RetrieveSensitiveInfoPojo;
import util.StringTemplate;

import java.util.HashMap;

public class RetrieveSensitiveInfoSteps {


    public RetrieveSensitiveInfoSteps() throws Exception {

    }

    RetrieveSensitiveInfo retrieveSensitiveInfoAPI = new RetrieveSensitiveInfo();

    @BeforeClass
    public void retrieveSensitiveInfoStepsPrerequisites() {

    }


    @Step
    public void retrieveSensitiveInfoForValidUUID(String uuid, String senstiveInfoType, String refkey) throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authToken", SensitiveInfoData.AUTH_TOKEN);

        String retrieveSensitiveInfoRequest = new StringTemplate(DocStoreConstants.RETRIEVE_SENSITIVE_INFO_REQUEST)
                .replace("uuid", uuid)
                .replace("type", senstiveInfoType)
                .replace("status", "")
                .replace("refKey", refkey)
                .toString();

        JSONObject retreiveSensitiveRequestJson = new JSONObject(retrieveSensitiveInfoRequest);

        RetrieveSensitiveInfoPojo retrieveSensitiveInfoPojo = retrieveSensitiveInfoAPI.post(headerDetails, retreiveSensitiveRequestJson.toString());
        Assert.assertEquals(retrieveSensitiveInfoPojo.responseStatus, "SUCCESS");
        Assert.assertNotNull(retrieveSensitiveInfoPojo.sensitiveInfoEntryResponses);
        Assert.assertNotNull(retrieveSensitiveInfoPojo.sensitiveInfoEntryResponses);
    }

    @Step
    public void retrieveSensitiveInfoForInvalidUUID() throws Exception {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authToken", SensitiveInfoData.AUTH_TOKEN);

        String retrieveSensitiveInfoRequest = new StringTemplate(DocStoreConstants.RETRIEVE_SENSITIVE_INFO_REQUEST)
                .replace("uuid", SensitiveInfoData.INVALID_UUID)
                .replace("type", SensitiveInfoData.PAN)
                .replace("sensitiveInfo", null)
                .replace("status", SensitiveInfoData.UNVERIFED)
                .replace("refKey", SensitiveInfoData.INVALID_REFKEY)
                .toString();

        JSONObject retreiveSensitiveRequestJson = new JSONObject(retrieveSensitiveInfoRequest);

        RetrieveSensitiveInfoPojo retrieveSensitiveInfoPojo = retrieveSensitiveInfoAPI.post(headerDetails, retreiveSensitiveRequestJson.toString());
        Assert.assertEquals(retrieveSensitiveInfoPojo.responseStatus, "FAILED");


    }
}
