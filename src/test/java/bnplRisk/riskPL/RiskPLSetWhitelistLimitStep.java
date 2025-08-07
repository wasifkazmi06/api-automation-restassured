package bnplRisk.riskPL;

import api.bnplRisk.RiskPLSetWhitelistLimit;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskPLSetWhitelistLimitPojo;
import bnplRisk.Constants;
import util.StringTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiskPLSetWhitelistLimitStep {

    public static RiskPLSetWhitelistLimitPojo riskPLSetWhitelistLimitPojo = new RiskPLSetWhitelistLimitPojo();
    public static RiskPLSetWhitelistLimit riskPLSetWhitelistLimit;

    static {
        try {
            riskPLSetWhitelistLimit = new RiskPLSetWhitelistLimit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void riskPLSetWhitelistLimitMethod(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        String riskPLSetWhitelistLimitRequest = new StringTemplate(Constants.RISK_PL_SET_WHITELIST_LIMIT)
                .replace("mobile", finalRequest.get("mobile"))
                .replace("limit", finalRequest.get("limit"))
                .replace("reason", finalRequest.get("reason"))
                .replace("decision", finalRequest.get("decision"))
                .replace("bnpl_line", finalRequest.get("bnpl_line"))
                .replace("pan_hash", finalRequest.get("pan_hash"))
                .replace("bnpl_decision", finalRequest.get("bnpl_decision"))
                .replace("lazycard_decision", finalRequest.get("lazycard_decision"))
                .replace("lazycard_line", finalRequest.get("lazycard_line"))
                .replace("tenant", finalRequest.get("tenant"))
                .toString();

        riskPLSetWhitelistLimitPojo = riskPLSetWhitelistLimit.post(queryParamDetails, headerDetails, riskPLSetWhitelistLimitRequest);
    }

    private static HashMap requestBuilder(HashMap<String, String> request) throws Exception {

        request.put("mobile", request.get("mobile"));
        request.put("limit", request.get("limit"));
        request.put("reason", request.get("reason"));
        request.put("decision", request.get("decision"));
        request.put("bnpl_line", request.get("bnpl_line"));
        request.put("pan_hash", request.get("pan_hash"));
        request.put("bnpl_decision", request.get("bnpl_decision"));
        request.put("lazycard_decision", request.get("lazycard_decision"));
        request.put("lazycard_line", request.get("lazycard_line"));
        request.put("tenant", request.get("tenant"));

        List keys = new ArrayList(request.keySet());

        for(int i=0; i<keys.size(); i++) {
            if(request.get(keys.get(i)) == null){
                request.put(keys.get(i).toString(), "");
            }
        }

        if(request.get("reason").equals("")) {
            request.put("reason", "Testing");
        }

        return request;
    }
}
