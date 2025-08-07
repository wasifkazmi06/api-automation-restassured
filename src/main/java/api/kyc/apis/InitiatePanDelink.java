package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.InitiatePanDelinkPojo;
import pojos.kyc.apis.UploadDocumentPojo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class InitiatePanDelink extends BaseAPI<InitiatePanDelinkPojo> {
    public InitiatePanDelink() throws Exception {
        super(Uri.INITIATE_PAN_DELINK, InitiatePanDelinkPojo.class);
    }

    @Step
    public InitiatePanDelinkPojo postWithJsonHeader(HashMap<String, Object> headerDetails, HashMap<String, Object> queryParamDetails) {
        return  super.postWithQueryParam(headerDetails,queryParamDetails);
    }

}
