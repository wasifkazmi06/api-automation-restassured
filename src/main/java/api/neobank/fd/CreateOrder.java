package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.NomineeDetailsPayload;

import java.util.HashMap;
import java.util.Map;

public class CreateOrder extends BaseAPI<NomineeDetailsPayload> {

    public CreateOrder() throws Exception {
        super(Uri.SECUREDCARD_CREATE_ORDER, NomineeDetailsPayload.class);
    }
    @Step
    public NomineeDetailsPayload post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
