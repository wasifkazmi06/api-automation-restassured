package api.snailmailChanges;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.snailMail.SnailMailGenericResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class PushDataToKlassicRemote extends BaseAPI<SnailMailGenericResponsePojo> {

    public PushDataToKlassicRemote() throws Exception {
        super(Uri.PUSH_TO_REMOTE, SnailMailGenericResponsePojo.class);
    }
    @Step
    public SnailMailGenericResponsePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}
