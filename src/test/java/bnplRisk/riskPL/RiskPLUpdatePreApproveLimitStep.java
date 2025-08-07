package bnplRisk.riskPL;

import api.bnplRisk.RiskPLUpdatePreApproveLimit;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskPLUpdatePreApproveLimitPojo;
import bnplRisk.Constants;
import bnplRisk.TestData;
import util.StringTemplate;
import java.util.*;

public class RiskPLUpdatePreApproveLimitStep {

    public static RiskPLUpdatePreApproveLimitPojo riskPLUpdatePreApproveLimitPojo = new RiskPLUpdatePreApproveLimitPojo();
    public static RiskPLUpdatePreApproveLimit riskPLUpdatePreApproveLimit;

    static {
        try {
            riskPLUpdatePreApproveLimit = new RiskPLUpdatePreApproveLimit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void RiskPLUpdatePreApproveLimitMethod(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        String riskPLUpdatePreApproveLimitRequest = new StringTemplate(Constants.RISK_PL_UPDATE_PRE_APPROVE_LIMIT)
                .replace("mobile", finalRequest.get("mobile"))
                .replace("limit", finalRequest.get("limit"))
                .replace("reason", finalRequest.get("reason"))
                .replace("product", finalRequest.get("product"))
                .replace("decision", finalRequest.get("decision"))
                .replace("interestRate12", finalRequest.get("interestRate12"))
                .replace("interestRate9", finalRequest.get("interestRate9"))
                .replace("interestRate6", finalRequest.get("interestRate6"))
                .replace("interestRate3", finalRequest.get("interestRate3"))
                .toString();

        riskPLUpdatePreApproveLimitPojo = riskPLUpdatePreApproveLimit.post(queryParamDetails, headerDetails, riskPLUpdatePreApproveLimitRequest);
    }

    private static HashMap requestBuilder(HashMap<String, String> request) throws Exception {

        request.put("mobile", request.get("mobile"));
        request.put("limit", request.get("limit"));
        request.put("reason", request.get("reason"));
        request.put("product", request.get("product"));
        request.put("decision", request.get("decision"));
        request.put("interestRate12", request.get("interestRate12"));
        request.put("interestRate9", request.get("interestRate9"));
        request.put("interestRate6", request.get("interestRate6"));
        request.put("interestRate3", request.get("interestRate3"));

        List keys = new ArrayList(request.keySet());

        for(int i=0; i<keys.size(); i++) {
            if(request.get(keys.get(i)) == null){
                request.put(keys.get(i).toString(), "");
            }
        }

        if(request.get("reason").equals("")) {
            request.put("reason", "Testing");
        }

        if(request.get("interestRate12").equals("")) {
            request.put("interestRate12", TestData.DEFAULT_INTEREST_12M);
        }

        if(request.get("interestRate9").equals("")) {
            request.put("interestRate9", TestData.DEFAULT_INTEREST_9M);
        }

        if(request.get("interestRate6").equals("")) {
            request.put("interestRate6", TestData.DEFAULT_INTEREST_6M);
        }

        if(request.get("interestRate3").equals("")) {
            request.put("interestRate3", TestData.DEFAULT_INTEREST_3M);
        }

        return request;
    }
}
