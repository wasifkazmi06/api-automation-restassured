package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.FdDetails;

import java.util.HashMap;

public class GetQueueCount extends BaseAPI<FdDetails> {
    public GetQueueCount() throws Exception {
        super(Uri.SECUREDCARD_FD_QUEUECOUNT, FdDetails.class);
    }
    @Step
    public FdDetails post(HashMap<String, String> pathparam, HashMap<String, String> headerDetails) {
        return super.post(pathparam,headerDetails);
    }
}
