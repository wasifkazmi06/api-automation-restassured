package docstore;

import api.docStore.DeleteSensitiveInfo;
import io.qameta.allure.Step;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.docStore.DeleteSensitiveInfoPojo;
import util.StringTemplate;

import java.util.HashMap;

public class DeleteSensitiveInfoSteps {

    public DeleteSensitiveInfoSteps() throws Exception {

    }

    DeleteSensitiveInfo deleteSensitiveInfoAPI = new DeleteSensitiveInfo();

    @BeforeClass
    public void deleteSensitiveInfoStepsPrerequisites() {

    }

    @Step
    public void deleteSensitiveInfoForValidUUID(String type, String refkey) throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authToken", SensitiveInfoData.AUTH_TOKEN);

        String deleteSensitiveInfoRequest = new StringTemplate(DocStoreConstants.DELETE_SENSITIVE_INFO_REQUEST)
                .replace("umuuid", SensitiveInfoData.UUID)
                .replace("type", type)
                .replace("sensitiveInfo", null)
                .replace("refKey", refkey)
                .toString();

        JSONObject deleteSensitiveRequestJson = new JSONObject(deleteSensitiveInfoRequest);

        DeleteSensitiveInfoPojo deleteSensitiveInfoPojo = deleteSensitiveInfoAPI.post(headerDetails, deleteSensitiveRequestJson.toString());
        Assert.assertEquals(deleteSensitiveInfoPojo.message, "SUCCESS");
    }

    @Step
    public void deleteSensitiveInfoForInvalidRefKey() throws Exception {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authToken", SensitiveInfoData.AUTH_TOKEN);

        String deleteSensitiveInfoRequest = new StringTemplate(DocStoreConstants.DELETE_SENSITIVE_INFO_REQUEST)
                .replace("status", SensitiveInfoData.VERIFED)
                .replace("refKey", SensitiveInfoData.INVALID_REFKEY)
                .toString();

        JSONObject deleteSensitiveRequestJson = new JSONObject(deleteSensitiveInfoRequest);

        DeleteSensitiveInfoPojo deleteSensitiveInfoPojo = deleteSensitiveInfoAPI.post(headerDetails, deleteSensitiveRequestJson.toString());
        Assert.assertEquals(deleteSensitiveInfoPojo.responseStatus, "FAILED");

    }
}
