package api.neobank.dcs;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.dcs.DCS_Pojo;

import java.util.HashMap;
import java.util.Map;

public class GetConfigDetails extends BaseAPI<DCS_Pojo>  {
    public GetConfigDetails() throws Exception {
        super(Uri.DCS_GET_CONFIG_DETAILS, DCS_Pojo.class);
    }

    @Step
    public DCS_Pojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
