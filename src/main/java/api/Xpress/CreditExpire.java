package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.CreditExpireResponsePojo;

import java.util.HashMap;

public class CreditExpire extends BaseAPI<CreditExpireResponsePojo> {

    public CreditExpire()throws Exception{
        super(Uri.CREDIT_EXPIRE_POST_APPROVED, CreditExpireResponsePojo.class);
    }

    @Step
    public Response getWithPathParam(HashMap<String, String> headerDetails, String pathParam) {
        return super.get(headerDetails, pathParam);
    }
}
