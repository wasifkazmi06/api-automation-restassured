package api.kyc.digilocker;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.KycGenericResponse;
import pojos.kyc.apis.KycStatusResponsePojo;
import pojos.kyc.digilocker.ResultsResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class GetResults extends BaseAPI<ResultsResponsePojo> {

    public GetResults() throws Exception {
        super(Uri.GET_DIGILOCKER_RESULTS, ResultsResponsePojo.class);
    }
    @Step
    public ResultsResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
