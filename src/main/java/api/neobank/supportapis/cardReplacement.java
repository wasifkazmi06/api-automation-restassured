package api.neobank.supportapis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.support.CardControlPojo;

import java.util.HashMap;

public class cardReplacement extends BaseAPI<CardControlPojo> {
    public cardReplacement() throws Exception {
        super(Uri.CARD_REPLACEMENT, CardControlPojo.class);
    }
    @Step
    public CardControlPojo post(HashMap<String, String> pathparam,HashMap<String, String> headerDetails) {
        return super.post(pathparam,headerDetails);
    }

}
