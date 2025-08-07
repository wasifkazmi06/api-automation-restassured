package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.GetKfsResponsePojo;

import java.util.HashMap;

public class GetKfsDetails extends BaseAPI<GetKfsResponsePojo> {

    public GetKfsDetails() throws Exception {
        super(Uri.GET_KFS_DETAILS_SHYLOCK, GetKfsResponsePojo.class);
    }

    @Step
    public Response getWithResponse(String pathParam, HashMap<String, String> headerDetails) {
        return super.getWithResponse(headerDetails, pathParam);
    }
}
