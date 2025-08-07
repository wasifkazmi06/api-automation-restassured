package api.heimdall.apvChanges;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.LocationProximity;

import java.util.HashMap;
import java.util.Map;

public class FetchProximityDistance extends BaseAPI<LocationProximity> {

    public FetchProximityDistance() throws Exception {
        super(Uri.FETCH_PROXIMITY_DISTANCE, LocationProximity.class);
    }
    @Step
    public LocationProximity post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}
