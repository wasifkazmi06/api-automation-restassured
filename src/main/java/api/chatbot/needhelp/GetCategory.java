package api.chatbot.needhelp;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.chatbot.needhelp.GetCategoryPojo;
import java.util.HashMap;
import java.util.Map;

public class GetCategory extends BaseAPI<GetCategoryPojo> {
    public GetCategory() throws Exception {
            super(Uri.GET_CATEGORY_SEARCH, GetCategoryPojo.class);
    }
    @Step
    public GetCategoryPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
            return super.get(queryParamDetails, headerDetails);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }


}