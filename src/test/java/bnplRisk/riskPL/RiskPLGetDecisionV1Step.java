package bnplRisk.riskPL;

import api.bnplRisk.RiskPLGetDecisionV1;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskPLGetDecisionPojo;
import bnplRisk.Constants;
import util.StringTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiskPLGetDecisionV1Step {

    public static RiskPLGetDecisionPojo riskPLGetDecisionPojo = new RiskPLGetDecisionPojo();
    public static RiskPLGetDecisionV1 riskPLGetDecisionV1;

    static {
        try {
            riskPLGetDecisionV1 = new RiskPLGetDecisionV1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void riskPLGetDecisionV1Method(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        String riskPLUpdatePreApproveLimitRequest = new StringTemplate(Constants.RISK_PL_GET_DECISION_V1)
                .replace("inputEmail", finalRequest.get("inputEmail"))
                .replace("inputMobile", finalRequest.get("inputMobile"))
                .replace("inputName", finalRequest.get("inputName"))
                .replace("panVerifiedName", finalRequest.get("panVerifiedName"))
                .toString();

        riskPLGetDecisionPojo = riskPLGetDecisionV1.post(queryParamDetails, headerDetails, riskPLUpdatePreApproveLimitRequest);
    }

    private static HashMap requestBuilder(HashMap<String, String> request) {

        request.put("inputEmail", request.get("inputEmail"));
        request.put("inputMobile", request.get("inputMobile"));
        request.put("inputName", request.get("inputName"));
        request.put("panVerifiedName", request.get("panVerifiedName"));

        List keys = new ArrayList(request.keySet());

        for(int i=0; i<keys.size(); i++) {
            if(request.get(keys.get(i)) == null){
                request.put(keys.get(i).toString(), "");
            }
        }

        return request;
    }
}
