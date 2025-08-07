package api.neobank.supportapis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.support.CardControlPojo;

import java.util.HashMap;
import java.util.Map;

public class CardControl extends BaseAPI<CardControlPojo> {
    public CardControl() throws Exception {
        super(Uri.NEO_CARD_CONTROL, CardControlPojo.class);
    }
    @Step
    public CardControlPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
