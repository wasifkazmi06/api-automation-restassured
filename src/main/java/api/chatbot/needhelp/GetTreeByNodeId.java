package api.chatbot.needhelp;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.chatbot.needhelp.GetTreeByNodeIdPojo;

import java.util.HashMap;
import java.util.Map;

public class GetTreeByNodeId extends BaseAPI<GetTreeByNodeIdPojo> {
    public GetTreeByNodeId() throws Exception {
        super(Uri.GET_TREE_BY_NODEID, GetTreeByNodeIdPojo.class);
    }

    @Step
    public GetTreeByNodeIdPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails,String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.getWithPathPram(queryParamDetails, headerDetails, pathParam);
    }

}

