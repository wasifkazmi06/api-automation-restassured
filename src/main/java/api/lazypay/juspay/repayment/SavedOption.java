package api.lazypay.juspay.repayment;
import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.SavedOptionPojo;

import java.util.HashMap;
import java.util.Map;

public class SavedOption  extends BaseAPI<SavedOptionPojo> {

    public SavedOption() throws Exception {
        super(Uri.SAVEDOPTION_JP, SavedOptionPojo.class);
    }
    @Step
    public SavedOptionPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }
}