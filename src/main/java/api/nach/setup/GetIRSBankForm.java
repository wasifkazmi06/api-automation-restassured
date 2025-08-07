package api.nach.setup;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.nach.GetIRSBankFormPojo;
import java.util.HashMap;
import java.util.Map;

public class GetIRSBankForm extends BaseAPI<GetIRSBankFormPojo> {
    public GetIRSBankForm() throws Exception {
            super(Uri.GET_IRS_BANK_FORM, GetIRSBankFormPojo.class);
    }
    @Step
    public GetIRSBankFormPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
