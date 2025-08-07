package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.DisbursalAPIPojo;
import pojos.Xpress.PaymentApiPojo;

import java.util.HashMap;

public class PaymentAPI extends BaseAPI<PaymentApiPojo> {
    public PaymentAPI() throws Exception {
        super(Uri.XPRESS_PAYMENT_API, PaymentApiPojo.class);
    }

    @Step
    public Response postWithPathParamsWithReponse(HashMap<String, String> headerDetails, String jsonRequestBody, String PathParams) {
        return super.postWithPathParamsWithReponse(headerDetails, jsonRequestBody,PathParams);
    }
}
