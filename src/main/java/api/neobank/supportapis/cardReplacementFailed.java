package api.neobank.supportapis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.support.cardControlFailedPojo;


import java.util.HashMap;

public class cardReplacementFailed extends BaseAPI<cardControlFailedPojo> {
    public cardReplacementFailed() throws Exception {
        super(Uri.CARD_REPLACEMENT, cardControlFailedPojo.class);
    }
    @Step
    public cardControlFailedPojo post(HashMap<String, String> pathparam, HashMap<String, String> headerDetails) {
        return super.post(pathparam,headerDetails);
    }
}
