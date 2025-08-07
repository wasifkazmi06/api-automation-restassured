package api.chatbot;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.chatbot.StartSessionPojo;

import java.util.HashMap;
import java.util.Map;

public class StartSession extends BaseAPI<StartSessionPojo> {
    public StartSession() throws Exception {
        super(Uri.START_SESSION, StartSessionPojo.class);
    }
    @Step
    public StartSessionPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails,  String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}
