package api.neobank.dcs;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.dcs.DCS_Pojo;

import java.util.HashMap;
import java.util.Map;

public class AddConfigDetails extends BaseAPI<DCS_Pojo> {


    public AddConfigDetails() throws Exception {
        super(Uri.DCS_ADD_CONFIG_DETAILS, DCS_Pojo.class);
    }

    @Step
    public DCS_Pojo put(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.put(queryParamDetails, headerDetails, request);
    }
}
