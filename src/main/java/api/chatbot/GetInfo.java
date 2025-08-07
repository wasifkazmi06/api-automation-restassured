package api.chatbot;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.chatbot.GetInfoPojo;
import java.util.HashMap;
import java.util.Map;

public class GetInfo extends BaseAPI<GetInfoPojo> {
    public GetInfo() throws Exception {
        super(Uri.GET_INFO, GetInfoPojo.class);
    }
    @Step
    public GetInfoPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}

