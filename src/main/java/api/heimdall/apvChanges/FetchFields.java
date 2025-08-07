package api.heimdall.apvChanges;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.LocationProximity;

import java.util.HashMap;
import java.util.Map;

public class FetchFields extends BaseAPI<LocationProximity> {

    public FetchFields() throws Exception {
        super(Uri.FETCH_FIELDS, LocationProximity.class);
    }
    @Step
    public LocationProximity get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails){
        return super.get(queryParamDetails, headerDetails);
    }
}
