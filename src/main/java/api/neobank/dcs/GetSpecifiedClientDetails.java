package api.neobank.dcs;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.dcs.DCS_Pojo;

import java.util.HashMap;
import java.util.Map;

public class GetSpecifiedClientDetails extends BaseAPI<DCS_Pojo> {
    public GetSpecifiedClientDetails() throws Exception {
        super(Uri.DCS_GET_SPECIFIED_CLIENT_DETAILS, DCS_Pojo.class);
    }

    @Step
    public DCS_Pojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path) {
        return super.get(queryParamDetails, headerDetails, path);
    }
}
