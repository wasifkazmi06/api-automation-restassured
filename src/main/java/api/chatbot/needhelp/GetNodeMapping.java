package api.chatbot.needhelp;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.chatbot.needhelp.NeedHelpNodeMapping;

import java.util.HashMap;
import java.util.Map;

public class GetNodeMapping extends BaseAPI<NeedHelpNodeMapping[]> {
    public GetNodeMapping() throws Exception {
        super(Uri.GET_NODE_MAPPING, NeedHelpNodeMapping[].class);
    }
    @Step
    public NeedHelpNodeMapping[] get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }


}

