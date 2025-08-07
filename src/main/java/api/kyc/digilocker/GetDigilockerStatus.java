package api.kyc.digilocker;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.digilocker.ResultsResponsePojo;
import pojos.kyc.digilocker.StatusResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class GetDigilockerStatus extends BaseAPI<StatusResponsePojo> {

    public GetDigilockerStatus() throws Exception {
        super(Uri.GET_DIGILOCKER_STATUS, StatusResponsePojo.class);
    }
    @Step
    public StatusResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
