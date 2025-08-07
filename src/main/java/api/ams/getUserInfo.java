package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.userUuid;

import java.util.HashMap;
import java.util.Map;

public class getUserInfo extends BaseAPI<userUuid> {
    public getUserInfo() throws Exception {
        super(Uri.GET_USER_INFO, userUuid.class);
    }

    @Step
    public userUuid get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}
