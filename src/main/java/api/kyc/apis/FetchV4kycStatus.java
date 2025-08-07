package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import pojos.kyc.apis.KycGenericResponse;
import pojos.kyc.apis.KycStatusResponsePojo;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class FetchV4kycStatus extends BaseAPI<KycGenericResponse<KycStatusResponsePojo>> {

    public FetchV4kycStatus() throws Exception {

        super(Uri.V4_KYC_STATUS,
                (Class<KycGenericResponse<KycStatusResponsePojo>>) new KycGenericResponse<KycStatusResponsePojo>().getClass());

    }

    @Step
    public KycGenericResponse<KycStatusResponsePojo> get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails){
        return super.get(queryParamDetails, headerDetails);


    }
}


