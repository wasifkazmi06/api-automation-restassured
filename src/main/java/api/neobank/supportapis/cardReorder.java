package api.neobank.supportapis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.support.CardControlPojo;
import java.util.HashMap;


public class cardReorder extends BaseAPI<CardControlPojo> {

    public cardReorder() throws Exception {
        super(Uri.REQ_PHYSICAL_CARD, CardControlPojo.class);
    }
    @Step
    public CardControlPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails,request);
    }
}
