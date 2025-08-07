package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.ShylockCampaignUserResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class ShylockApi extends BaseAPI<ShylockCampaignUserResponsePojo> {

    public ShylockApi() throws Exception {
        super(Uri.REGISTER_USER_SHYLOCK, ShylockCampaignUserResponsePojo.class);
    }

    @Step
    public Response postWithFileUpload(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String filename) {
        return super.postWithFileUpload(queryParamDetails, headerDetails, filename);
    }
}
