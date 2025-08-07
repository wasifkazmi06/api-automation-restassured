package api.neobank.supportapis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.support.preference.cardStatus;

import java.util.HashMap;
import java.util.Map;

public class cardActionStatus extends BaseAPI<cardStatus> {

    public cardActionStatus() throws Exception {
        super(Uri.NEO_CARD_ACTION_STATUS, cardStatus.class);
    }
    @Step
    public cardStatus get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path){
        return super.get(queryParamDetails, headerDetails,path);
    }

}
