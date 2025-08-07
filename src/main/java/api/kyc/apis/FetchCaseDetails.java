package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.V2CaseDetailsResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class FetchCaseDetails extends BaseAPI<V2CaseDetailsResponsePojo> {


    public FetchCaseDetails() throws Exception {
        super(Uri.V2_CASE_DETAILS, V2CaseDetailsResponsePojo.class);
    }
    @Step
    public V2CaseDetailsResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
