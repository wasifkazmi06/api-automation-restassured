package docstore;

import api.docStore.UpdateSensitiveInfo;
import io.qameta.allure.Step;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.docStore.UpdateSensitiveInfoPojo;
import util.StringTemplate;

import java.util.HashMap;

public class UpdateSensitiveInfoSteps {

    public UpdateSensitiveInfoSteps() throws Exception {

    }

    UpdateSensitiveInfo updateSensitiveInfoAPI = new UpdateSensitiveInfo();

    @BeforeClass
    public void updateSensitiveInfoStepsPrerequisites() {

    }

    @Step
    public void updateSensitiveInfoForValidUUID(String refkey) throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authToken", SensitiveInfoData.AUTH_TOKEN);

        String updateSensitiveInfoRequest = new StringTemplate(DocStoreConstants.UPDATE_SENSITIVE_INFO_REQUEST)
                .replace("status", SensitiveInfoData.VERIFED)
                .replace("refKey", refkey)
                .toString();

        JSONObject updateSensitiveRequestJson = new JSONObject(updateSensitiveInfoRequest);

        UpdateSensitiveInfoPojo updateSensitiveInfoPojo = updateSensitiveInfoAPI.post(headerDetails, updateSensitiveRequestJson.toString());
        Assert.assertEquals(updateSensitiveInfoPojo.responseStatus, "SUCCESS");
    }

    @Step
    public void updateSensitiveInfoForInvalidRefKey() throws Exception {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authToken", SensitiveInfoData.AUTH_TOKEN);

        String updateSensitiveInfoRequest = new StringTemplate(DocStoreConstants.UPDATE_SENSITIVE_INFO_REQUEST)
                .replace("status", SensitiveInfoData.VERIFED)
                .replace("refKey", SensitiveInfoData.INVALID_REFKEY)
                .toString();

        JSONObject updateSensitiveRequestJson = new JSONObject(updateSensitiveInfoRequest);

        UpdateSensitiveInfoPojo updateSensitiveInfoPojo = updateSensitiveInfoAPI.post(headerDetails, updateSensitiveRequestJson.toString());
        Assert.assertEquals(updateSensitiveInfoPojo.responseStatus, "FAILED");


    }
}
