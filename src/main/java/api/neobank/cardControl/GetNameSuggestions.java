package api.neobank.cardControl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.cardControl.GetNameSuggestionsPojo;
import pojos.neobank.cardControl.GetUserTransactionLimitPojo;

import java.util.HashMap;
import java.util.Map;

public class GetNameSuggestions extends BaseAPI<GetNameSuggestionsPojo> {
    public GetNameSuggestions() throws Exception {
        super(Uri.NEO_CARD_CONTROL_GETNAMESUGGESTIONS, GetNameSuggestionsPojo.class);
    }
    @Step
    public GetNameSuggestionsPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request){
        return super.post(queryParamDetails, headerDetails,request);
    }
}
