package api.neobank.dcs;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.dcs.DCS_Pojo;

import java.util.HashMap;
import java.util.Map;

public class DeleteClientDetails extends BaseAPI<DCS_Pojo> {
    public DeleteClientDetails() throws Exception {
        super(Uri.DCS_DELETE_SPECIFIED_CLIENT_DETAILS, DCS_Pojo.class);
    }

    @Step
    public DCS_Pojo delete(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path) {
        return super.deleteWithParam(queryParamDetails, headerDetails, path);
    }
}
