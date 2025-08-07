package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.KycGenericResponse;
import pojos.kyc.apis.KycStatusResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class LatestSuccessKyc extends BaseAPI<KycGenericResponse<KycStatusResponsePojo>> {
    public LatestSuccessKyc() throws Exception {
        super(Uri.V1_LATEST_SUCCESS_KYC,
                (Class<KycGenericResponse<KycStatusResponsePojo>>) new KycGenericResponse<KycStatusResponsePojo>().getClass());
    }

    @Step
    public KycGenericResponse<KycStatusResponsePojo> get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);

    }
}
