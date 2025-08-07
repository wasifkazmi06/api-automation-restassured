package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.DisbursalAPIPojo;
import pojos.Xpress.PscoreRegistrationResponsePojo;

import java.util.HashMap;

public class DisbursalAPi extends BaseAPI<DisbursalAPIPojo> {
    public DisbursalAPi() throws Exception {
        super(Uri.PSCORE_DISBURSAL_API, DisbursalAPIPojo.class);
    }

    @Step
    public Response postWithPathParamsWithReponse(HashMap<String, String> headerDetails, String jsonRequestBody, String pathParam) {
        return super.postWithPathParamsWithReponse(headerDetails, jsonRequestBody, pathParam);
    }
}
