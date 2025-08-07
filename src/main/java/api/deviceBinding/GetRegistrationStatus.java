package api.deviceBinding;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.deviceBinding.GetRegistrationStatusPojo;
import java.util.HashMap;
import java.util.Map;

public class GetRegistrationStatus extends BaseAPI<GetRegistrationStatusPojo> {

    public GetRegistrationStatus() throws Exception {
        super(Uri.GET_REGISTRATION_STATUS, GetRegistrationStatusPojo.class);
    }

    @Step
    public GetRegistrationStatusPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}