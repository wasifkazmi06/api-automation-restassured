package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;

import io.restassured.response.Response;
import pojos.billpayment.SearchBillerPojo;
import java.util.HashMap;
import java.util.Map;

public class SearchBiller extends BaseAPI<SearchBillerPojo>

    {

    public SearchBiller() throws Exception {
        super(Uri.GET_SEARCHBILLER, SearchBillerPojo.class);
    }
        @Step
        public SearchBillerPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

    }

