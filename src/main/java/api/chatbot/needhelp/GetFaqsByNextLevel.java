package api.chatbot.needhelp;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.chatbot.needhelp.GetFaqsByNextLevelPojo;

import java.util.HashMap;
import java.util.Map;

public class GetFaqsByNextLevel extends BaseAPI<GetFaqsByNextLevelPojo> {
    public GetFaqsByNextLevel() throws Exception {
        super(Uri.GET_FAQ_BY_LEVEL, GetFaqsByNextLevelPojo.class);
    }
    @Step
    public GetFaqsByNextLevelPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }


}
