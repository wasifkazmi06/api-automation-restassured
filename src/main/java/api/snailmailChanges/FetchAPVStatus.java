package api.snailmailChanges;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.snailMail.APVStatusResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class FetchAPVStatus extends BaseAPI<APVStatusResponsePojo> {

    public FetchAPVStatus() throws Exception {
        super(Uri.FETCH_APV_STATUS, APVStatusResponsePojo.class);
    }
    @Step
    public APVStatusResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
