package api.devicebinding;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.userUuid;

import java.util.HashMap;
import java.util.Map;

public class DBGetUserBindingStatus extends BaseAPI<userUuid> {
    public DBGetUserBindingStatus() throws Exception {
        super(Uri.DB_GET_USER_BINDING_STATUS, userUuid.class);
    }

    @Step
    public userUuid get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}
