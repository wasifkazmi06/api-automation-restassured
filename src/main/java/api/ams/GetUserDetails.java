package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.PlatformLoginResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class GetUserDetails extends BaseAPI<PlatformLoginResponsePojo> {

    public GetUserDetails() throws Exception {
        super(Uri.GET_USER_DETAILS, PlatformLoginResponsePojo.class);
    }

    @Step
    public PlatformLoginResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}