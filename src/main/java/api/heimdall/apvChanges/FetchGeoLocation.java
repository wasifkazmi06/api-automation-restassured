package api.heimdall.apvChanges;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.GeoLocationPojo;

import java.util.HashMap;
import java.util.Map;

public class FetchGeoLocation extends BaseAPI<GeoLocationPojo> {

    public FetchGeoLocation() throws Exception {
        super(Uri.FETCH_GEO_LOCATION, GeoLocationPojo.class);
    }
    @Step
    public GeoLocationPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}
