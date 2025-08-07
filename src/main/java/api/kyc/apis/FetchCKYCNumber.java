package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.CKYCPojo;
import pojos.kyc.apis.V2CaseDetailsResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class FetchCKYCNumber extends BaseAPI<CKYCPojo> {


    public FetchCKYCNumber() throws Exception {
        super(Uri.FETCH_CKYC_NUMBER, CKYCPojo.class);
    }
    @Step
    public CKYCPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }
}
