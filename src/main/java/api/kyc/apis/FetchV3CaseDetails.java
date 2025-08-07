package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.V3CaseDetailsResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class FetchV3CaseDetails extends BaseAPI<V3CaseDetailsResponsePojo> {


    public FetchV3CaseDetails() throws Exception {
        super(Uri.V3_CASE_DETAILS, V3CaseDetailsResponsePojo.class);
    }
    @Step
    public V3CaseDetailsResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}
