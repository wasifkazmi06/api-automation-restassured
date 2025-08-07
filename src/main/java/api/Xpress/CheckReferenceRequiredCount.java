package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.AddReferenceResponsePojo;

import java.util.HashMap;

public class CheckReferenceRequiredCount extends BaseAPI<AddReferenceResponsePojo> {

    public CheckReferenceRequiredCount() throws Exception {
        super(Uri.COLLECT_REFERENCE_SHYLOCK, AddReferenceResponsePojo.class);
    }

    @Step
    public Response getWithResponse(String pathParam,HashMap<String, String> headerDetails) {
        return super.getWithResponse(headerDetails, pathParam);
    }
}
