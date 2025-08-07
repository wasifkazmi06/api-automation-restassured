package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.DeactivateKycCaseResponse;
import pojos.kyc.apis.KycGenericResponse;
import pojos.kyc.apis.KycStatusResponsePojo;
import pojos.kyc.apis.UserLocationData;
import java.util.HashMap;
import java.util.Map;

public class UserLocation extends BaseAPI<KycGenericResponse<UserLocationData>> {


    protected UserLocation() throws Exception {
        super(Uri.USER_LAT_LONG, (Class<KycGenericResponse<UserLocationData>>) new KycGenericResponse<UserLocationData>().getClass());
    }

    @Step
    public KycGenericResponse<UserLocationData> post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}

