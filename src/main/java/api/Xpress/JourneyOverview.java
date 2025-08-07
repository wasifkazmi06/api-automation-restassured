package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.JourneyOverviewResponsePojo;

import java.util.HashMap;

public class JourneyOverview extends BaseAPI<JourneyOverviewResponsePojo> {

    public JourneyOverview() throws Exception {
        super(Uri.JOURNEY_OVERVIEW_SHYLOCK, JourneyOverviewResponsePojo.class);
    }

    @Step
    public Response getWithResponse(String pathParam, HashMap<String, String> headerDetails) {
        return super.getWithResponse(headerDetails, pathParam);
    }
}
