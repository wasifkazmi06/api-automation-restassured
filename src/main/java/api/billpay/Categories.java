package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.log4j.Category;
import pojos.billpayment.BillersPojo;
import pojos.billpayment.CategoryPojo;

import java.util.HashMap;
import java.util.Map;

public class Categories extends BaseAPI<CategoryPojo> {

    public Categories() throws Exception {
        super(Uri.GET_CATEGORY, CategoryPojo.class);
    }
    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }

}
