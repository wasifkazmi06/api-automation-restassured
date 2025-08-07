package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.DeactivateKycCaseResponse;
import pojos.kyc.apis.KycGenericResponse;

import java.util.HashMap;
import java.util.Map;

public class DeactivateKycCase extends BaseAPI<KycGenericResponse<DeactivateKycCaseResponse>> {

    public DeactivateKycCase() throws Exception {
        super(Uri.DEACTIVATE_KYC_CASE_V1,
                (Class<KycGenericResponse<DeactivateKycCaseResponse>>) new KycGenericResponse<DeactivateKycCaseResponse>().getClass());
    }

    @Step
    public KycGenericResponse<DeactivateKycCaseResponse> post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody){
        return super.post(queryParamDetails, headerDetails,jsonRequestBody);

    }
}


