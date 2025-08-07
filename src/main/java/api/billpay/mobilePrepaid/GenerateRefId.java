package api.billpay.mobilePrepaid;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;

import pojos.billpayment.mobilePrepaid.RefidPojo;

import java.util.HashMap;
import java.util.Map;

public class GenerateRefId extends BaseAPI<RefidPojo> {

    public GenerateRefId() throws Exception {
        super(Uri.GET_RefId, RefidPojo.class);

    }
    @Step
    public RefidPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);

    }
}