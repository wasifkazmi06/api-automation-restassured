package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.AddReferenceResponsePojo;

import java.util.HashMap;

public class CollectReference extends BaseAPI<AddReferenceResponsePojo> {

    public CollectReference() throws Exception {
        super(Uri.COLLECT_REFERENCE_SHYLOCK, AddReferenceResponsePojo.class);
    }

    @Step
    public Response postWithResponseBody(String pathParam, String jsonRequestBody, HashMap<String, String> headerDetails) {
        return super.postWithResponseBody(pathParam,headerDetails, jsonRequestBody);
    }
}
