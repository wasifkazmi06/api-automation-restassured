package api.neobank.supportapis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.support.preference.lazyCardActions;

import java.util.HashMap;
import java.util.Map;

public class cardActions extends BaseAPI<lazyCardActions> {

    public cardActions() throws Exception {
        super(Uri.NEO_CARD_ACTIONS, lazyCardActions.class);
    }
    @Step
    public lazyCardActions post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
