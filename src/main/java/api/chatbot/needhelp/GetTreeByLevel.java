package api.chatbot.needhelp;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.chatbot.needhelp.GetTreeByLevelPojo;

import java.util.HashMap;
import java.util.Map;

public class GetTreeByLevel extends BaseAPI<GetTreeByLevelPojo[]> {
    public GetTreeByLevel() throws Exception {
        super(Uri.GET_TREE_BY_LEVEL, GetTreeByLevelPojo[].class);
    }
    @Step
    public GetTreeByLevelPojo[] get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }

}

