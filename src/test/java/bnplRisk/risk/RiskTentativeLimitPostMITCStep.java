package bnplRisk.risk;

import api.bnplRisk.RiskTentativeLimitPostMITC;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import pojos.bnplRisk.RiskTentativeLimitPostMITCPOJO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RiskTentativeLimitPostMITCStep {

    public static RiskTentativeLimitPostMITCPOJO riskTentativeLimitPostMITCPOJO = new RiskTentativeLimitPostMITCPOJO();
    public static RiskTentativeLimitPostMITC riskTentativeLimitPostMITC;

    static {
        try {
            riskTentativeLimitPostMITC = new RiskTentativeLimitPostMITC();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void RiskTentativeLimitPostMITCMethod(HashMap<String, Object> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        headerDetails.put("x-tenant", finalRequest.get("tenant"));

        queryParamDetails.put("mobile", finalRequest.get("mobile"));
        queryParamDetails.put("products", finalRequest.get("products"));
        queryParamDetails.put("merchantId", finalRequest.get("merchantId"));
        queryParamDetails.put("subMerchantIdentifier", finalRequest.get("subMerchantIdentifier"));
        queryParamDetails.put("riskCategory", finalRequest.get("riskCategory"));
        queryParamDetails.put("mitcYearValidity", finalRequest.get("mitcYearValidity"));

        riskTentativeLimitPostMITCPOJO = riskTentativeLimitPostMITC.get(queryParamDetails, headerDetails);
    }

    private static HashMap requestBuilder(HashMap<String, Object> request) throws Exception {

        request.put("mobile", request.get("mobile"));
        request.put("products", request.get("products"));
        request.put("merchantId", request.get("merchantId"));
        request.put("subMerchantIdentifier", request.get("subMerchantIdentifier"));
        request.put("riskCategory", request.get("riskCategory"));
        request.put("mitcYearValidity", request.get("mitcYearValidity"));
        request.put("tenant", request.get("tenant"));

        List keys = new ArrayList(request.keySet());

        for(int i=0; i<keys.size(); i++) {
            if(request.get(keys.get(i)) == null){
                    request.put(keys.get(i).toString(), "");
            }
        }

        return request;
    }
}
