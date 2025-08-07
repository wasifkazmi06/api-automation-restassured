package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.FdVerifyUpi;

import java.util.HashMap;
import java.util.Map;

public class VerifyUpi extends BaseAPI<FdVerifyUpi> {

    public VerifyUpi() throws Exception {
        super(Uri.SECUREDCARD_VERIFYUPI, FdVerifyUpi.class);
    }
    @Step
    public FdVerifyUpi post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
