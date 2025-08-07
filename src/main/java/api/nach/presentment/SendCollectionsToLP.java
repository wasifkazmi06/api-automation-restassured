package api.nach.presentment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class SendCollectionsToLP extends BaseAPI<Object> {
    public SendCollectionsToLP() throws Exception {
            super(Uri.SEND_COLLECTIONS_TO_LP, Object.class);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }
}
