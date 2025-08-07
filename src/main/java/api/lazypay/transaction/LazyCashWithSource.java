package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.lazypay.transactionFlow.LazyCashWithSourcePojo;

import java.util.HashMap;
import java.util.Map;

public class LazyCashWithSource  extends BaseAPI<LazyCashWithSourcePojo> {
    public LazyCashWithSource() throws Exception {
        super(Uri.LAZYCASHWITHSOURCE, LazyCashWithSourcePojo.class);
    }

    @Step
    public Response postReturnResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.postReturnResponse(queryParamDetails, headerDetails, request);
    }


}
