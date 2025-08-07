package api.neobank.dcs;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.dcs.DCS_Pojo;

import java.util.HashMap;
import java.util.Map;

public class RegisterVertical extends BaseAPI<DCS_Pojo> {

    public RegisterVertical() throws Exception {
        super(Uri.DCS_REGISTER_VERTICAL, DCS_Pojo.class);
    }

    @Step
    public DCS_Pojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }

}
