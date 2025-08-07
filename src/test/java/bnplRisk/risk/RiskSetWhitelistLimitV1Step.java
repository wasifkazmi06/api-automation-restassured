package bnplRisk.risk;

import api.bnplRisk.RiskSetWhitelistLimitV1;
import bnplRisk.Constants;
import io.qameta.allure.Step;
import mpl.TestData;
import pojos.bnplRisk.RiskSetWhitelistLimitV1Pojo;
import util.StringTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiskSetWhitelistLimitV1Step {

    public static RiskSetWhitelistLimitV1Pojo riskSetWhitelistLimitV1Pojo = new RiskSetWhitelistLimitV1Pojo();
    public static RiskSetWhitelistLimitV1 riskSetWhitelistLimitV1;

    static {
        try {
            riskSetWhitelistLimitV1 = new RiskSetWhitelistLimitV1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void riskSetWhitelistLimitV1Method(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);
        String riskSetWhitelistLimitV1Request;

        headerDetails.put("x-tenant", finalRequest.get("tenant"));

        if(finalRequest.get("product").equals(TestData.PRODUCT_BNPL)){
             riskSetWhitelistLimitV1Request = new StringTemplate(Constants.RISK_PL_SET_WHITELIST_BNPL_LIMIT_V1)
                    .replace("mobile", finalRequest.get("mobile"))
                    .replace("reason", finalRequest.get("reason"))
                    .replace("panHash", finalRequest.get("panHash"))
                    .replace("bnpl_limitRisk1", finalRequest.get("bnpl_limitRisk1"))
                    .replace("decision_bnpl", finalRequest.get("decision_bnpl"))
                    .toString();
        }else if(finalRequest.get("product").equals(TestData.PRODUCT_COF)){
            riskSetWhitelistLimitV1Request = new StringTemplate(Constants.RISK_PL_SET_WHITELIST_COF_LIMIT_V1)
                    .replace("mobile", finalRequest.get("mobile"))
                    .replace("reason", finalRequest.get("reason"))
                    .replace("panHash", finalRequest.get("panHash"))
                    .replace("cof_limitRisk1", finalRequest.get("cof_limitRisk1"))
                    .replace("cof_limitRisk2", finalRequest.get("cof_limitRisk2"))
                    .replace("cof_limitRisk3", finalRequest.get("cof_limitRisk3"))
                    .replace("decision_cof", finalRequest.get("decision_cof"))
                    .replace("interestRate12", finalRequest.get("interestRate12"))
                    .replace("interestRate9", finalRequest.get("interestRate9"))
                    .replace("interestRate6", finalRequest.get("interestRate6"))
                    .replace("interestRate3", finalRequest.get("interestRate3"))
                    .toString();
        }else{
             riskSetWhitelistLimitV1Request = new StringTemplate(Constants.RISK_SET_WHITELIST_LIMIT_V1)
                    .replace("mobile", finalRequest.get("mobile"))
                    .replace("reason", finalRequest.get("reason"))
                    .replace("panHash", finalRequest.get("panHash"))
                    .replace("cof_limitRisk1", finalRequest.get("cof_limitRisk1"))
                    .replace("cof_limitRisk2", finalRequest.get("cof_limitRisk2"))
                    .replace("cof_limitRisk3", finalRequest.get("cof_limitRisk3"))
                    .replace("decision_cof", finalRequest.get("decision_cof"))
                    .replace("interestRate12", finalRequest.get("interestRate12"))
                    .replace("interestRate9", finalRequest.get("interestRate9"))
                    .replace("interestRate6", finalRequest.get("interestRate6"))
                    .replace("interestRate3", finalRequest.get("interestRate3"))
                    .replace("bnpl_limitRisk1", finalRequest.get("bnpl_limitRisk1"))
                    .replace("decision_bnpl", finalRequest.get("decision_bnpl"))
                    .toString();
        }
        riskSetWhitelistLimitV1Pojo = riskSetWhitelistLimitV1.post(queryParamDetails, headerDetails, riskSetWhitelistLimitV1Request);

    }

    private static HashMap requestBuilder(HashMap<String, String> request) {

        request.put("mobile", request.get("mobile"));
        request.put("reason", request.get("reason"));
        request.put("panHash", request.get("panHash"));
        request.put("cof_limitRisk1", request.get("cof_limitRisk1"));
        request.put("cof_limitRisk2", request.get("cof_limitRisk2"));
        request.put("cof_limitRisk3", request.get("cof_limitRisk3"));
        request.put("decision_cof", request.get("decision_cof"));
        request.put("interestRate12", request.get("interestRate12"));
        request.put("interestRate9", request.get("interestRate9"));
        request.put("interestRate6", request.get("interestRate6"));
        request.put("interestRate3", request.get("interestRate3"));
        request.put("bnpl_limitRisk1", request.get("bnpl_limitRisk1"));
        request.put("decision_bnpl", request.get("decision_bnpl"));
        request.put("product", request.get("product"));
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

       /* if(request.get("interestRate12").equals("")) {
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
        }*/

        if(request.get("decision_cof").equals("")) {
            request.put("decision_cof", "false");
        }

        if(request.get("decision_bnpl").equals("")) {
            request.put("decision_bnpl", "false");
        }

        return request;
    }
}
