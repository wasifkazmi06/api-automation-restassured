package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.FdDetails;

import java.util.HashMap;
import java.util.Map;

public class FetchNomineeDetails extends BaseAPI<FdDetails> {

    public FetchNomineeDetails() throws Exception {
        super(Uri.SECUREDCARD_FD_NOMINEEDETAILS, FdDetails.class);
    }

    @Step
    public FdDetails get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path) {
        return super.get(queryParamDetails, headerDetails,path);
    }
}
