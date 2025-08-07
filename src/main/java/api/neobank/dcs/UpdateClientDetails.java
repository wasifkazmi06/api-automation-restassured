package api.neobank.dcs;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.dcs.DCS_Pojo;

import java.util.HashMap;
import java.util.Map;

public class UpdateClientDetails extends BaseAPI<DCS_Pojo> {
    public UpdateClientDetails() throws Exception {
        super(Uri.DCS_UPDATE_SPECIFIED_CLIENT_DETAILS, DCS_Pojo.class);
    }

    @Step
    public DCS_Pojo put(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.put(queryParamDetails, headerDetails, request);
    }
}
