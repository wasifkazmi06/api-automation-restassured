package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.FdGenericPayload;

import java.util.HashMap;

public class RemoveFromRedis extends BaseAPI<FdGenericPayload> {

    public RemoveFromRedis() throws Exception {
        super(Uri.SECUREDCARD_REMOVE_FROM_REDIS, FdGenericPayload.class);
    }
    @Step
    public FdGenericPayload post(HashMap<String, String> pathparam, HashMap<String, String> headerDetails) {
        return super.post(pathparam,headerDetails);
    }
}
