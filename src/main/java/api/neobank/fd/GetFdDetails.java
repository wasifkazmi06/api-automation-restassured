package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.FdDetails;


import java.util.HashMap;
import java.util.Map;

public class GetFdDetails extends BaseAPI<FdDetails> {
    public GetFdDetails() throws Exception {
        super(Uri.SECUREDCARD_FD_DETAILS, FdDetails.class);
    }
    @Step
    public FdDetails post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
