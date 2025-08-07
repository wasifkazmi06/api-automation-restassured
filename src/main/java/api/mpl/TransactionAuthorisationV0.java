package api.mpl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.mpl.TransactionAuthorisationPOJO;
import java.util.HashMap;
import java.util.Map;

public class TransactionAuthorisationV0 extends BaseAPI<TransactionAuthorisationPOJO> {
    public TransactionAuthorisationV0() throws Exception {
            super(Uri.MPL_TRANSACTION_AUTHORISATION_V0, TransactionAuthorisationPOJO.class);
    }
    @Step
    public TransactionAuthorisationPOJO post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
