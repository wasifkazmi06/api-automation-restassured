package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.lazypay.repaymentFlow.IsSIAllowedPojo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsSIAllowed extends BaseAPI<IsSIAllowedPojo> {
    public IsSIAllowed() throws Exception {
            super(Uri.ISSIALLOWED, IsSIAllowedPojo.class);
    }
    @Step
    public Response getWithPathPrams(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String param1, String param2) {
        return super.getWithPathPrams(queryParamDetails, headerDetails, param1, param2);
    }
    @Step
    public IsSIAllowedPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, HashMap<String, String> pathPrams) {
        return super.get(queryParamDetails, headerDetails, pathPrams);
    }
}
