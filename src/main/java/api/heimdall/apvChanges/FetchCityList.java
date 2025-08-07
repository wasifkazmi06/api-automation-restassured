package api.heimdall.apvChanges;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.CityList;

import java.util.HashMap;
import java.util.Map;

public class FetchCityList extends BaseAPI<CityList> {

    public FetchCityList() throws Exception {
        super(Uri.FETCH_CITY_LIST, CityList.class);
    }
    @Step
    public CityList get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails){
        return super.get(queryParamDetails, headerDetails);
    }
}
