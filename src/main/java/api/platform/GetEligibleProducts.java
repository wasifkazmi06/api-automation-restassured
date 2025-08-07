package api.platform;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.platform.getEligibleProducts.GetProductsResponsePojo;
import pojos.platform.getUserData.UserData;

import java.util.HashMap;
import java.util.Map;

public class GetEligibleProducts extends BaseAPI<GetProductsResponsePojo> {

    public GetEligibleProducts() throws Exception {
        super(Uri.PLATFORM_GET_ELIGIBLE_PRODUCTS, GetProductsResponsePojo.class);
    }
    @Step
    public GetProductsResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
