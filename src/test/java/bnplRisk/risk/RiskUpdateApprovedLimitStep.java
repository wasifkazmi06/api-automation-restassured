package bnplRisk.risk;

import api.bnplRisk.RiskUpdateApprovedLimit;
import bnplRisk.Constants;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import pojos.bnplRisk.RiskUpdateApprovedLimitPOJO;
import util.StringTemplate;
import java.util.*;

@Slf4j
public class RiskUpdateApprovedLimitStep {

    public static RiskUpdateApprovedLimitPOJO riskUpdateApprovedLimitPOJO = new RiskUpdateApprovedLimitPOJO();
    public static RiskUpdateApprovedLimit riskUpdateApprovedLimit;

    static {
        try {
            riskUpdateApprovedLimit = new RiskUpdateApprovedLimit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void RiskUpdateApprovedLimitMethod(HashMap<String, Object> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        String riskUpdateApprovedLimitRequest = new StringTemplate(Constants.UPDATE_APPROVED_LIMIT)
                .replace("reason", finalRequest.get("reason"))
                .replace("mobile", finalRequest.get("mobile"))
                .replace("cof_limitRisk1", finalRequest.get("cof_limitRisk1"))
                .replace("cof_limitRisk2", finalRequest.get("cof_limitRisk2"))
                .replace("cof_limitRisk3", finalRequest.get("cof_limitRisk3"))
                .replace("cof_reason", finalRequest.get("cof_reason"))
                .replace("cof_decision", finalRequest.get("cof_decision"))
                .replace("interestRate3", finalRequest.get("interestRate3"))
                .replace("interestRate6", finalRequest.get("interestRate6"))
                .replace("interestRate9", finalRequest.get("interestRate9"))
                .replace("interestRate12", finalRequest.get("interestRate12"))
                .replace("bnpl_limitRisk1", finalRequest.get("bnpl_limitRisk1"))
                .replace("bnpl_reason", finalRequest.get("bnpl_reason"))
                .replace("bnpl_decision", finalRequest.get("bnpl_decision"))
                .toString();

        riskUpdateApprovedLimitPOJO = riskUpdateApprovedLimit.post(queryParamDetails, headerDetails, riskUpdateApprovedLimitRequest);
    }

    private static HashMap requestBuilder(HashMap<String, Object> request) throws Exception {

        request.put("reason", request.get("reason"));
        request.put("mobile", request.get("mobile"));
        request.put("cof_limit", request.get("cof_limit"));
        request.put("cof_reason", request.get("cof_reason"));
        request.put("cof_decision", request.get("cof_decision"));
        request.put("interestRate3", request.get("interestRate3"));
        request.put("interestRate6", request.get("interestRate6"));
        request.put("interestRate9", request.get("interestRate9"));
        request.put("interestRate12", request.get("interestRate12"));
        request.put("bnpl_limit", request.get("bnpl_limit"));
        request.put("bnpl_reason", request.get("bnpl_reason"));
        request.put("bnpl_decision", request.get("bnpl_decision"));

        List keys = new ArrayList(request.keySet());

        for(int i=0; i<keys.size(); i++) {
            if(request.get(keys.get(i)) == null && !keys.get(i).toString().equals("deviceInfo")){
                    request.put(keys.get(i).toString(), "");
            }
        }

        return request;
    }
}
