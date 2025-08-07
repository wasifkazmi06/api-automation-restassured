package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.GetPanDetailsResponsePojo;

import java.util.HashMap;

public class GetPanDetails extends BaseAPI<GetPanDetailsResponsePojo> {

    public GetPanDetails() throws Exception {
        super(Uri.PAN_FORM_DETAILS_SHYLOCK, GetPanDetailsResponsePojo.class);
    }

    @Step
    public Response getWithResponse(HashMap<String, String> headerDetails, String pathParam) {
        return super.getWithResponse(headerDetails, pathParam);
    }
}
