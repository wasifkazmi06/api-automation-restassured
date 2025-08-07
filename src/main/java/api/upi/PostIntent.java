package api.upi;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.PostIntentPojo;
import java.util.HashMap;
import java.util.Map;

public class PostIntent extends BaseAPI<PostIntentPojo> {
    public PostIntent() throws Exception {
        super(Uri.POST_INTENT, PostIntentPojo.class);
    }
    @Step
    public PostIntentPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
