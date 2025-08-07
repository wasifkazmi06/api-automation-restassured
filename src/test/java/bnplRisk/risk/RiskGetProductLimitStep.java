package bnplRisk.risk;

import api.bnplRisk.RiskGetProductLimit;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskGetProductLimitPojo;
import java.util.*;

public class RiskGetProductLimitStep {

    public static RiskGetProductLimitPojo riskGetProductLimitPojo = new RiskGetProductLimitPojo();
    public static RiskGetProductLimit riskGetProductLimit;

    static {
        try {
            riskGetProductLimit = new RiskGetProductLimit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void RiskGetProductLimitMethod(HashMap<String, Object> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        queryParamDetails.put("mobile", finalRequest.get("mobile"));
        queryParamDetails.put("products", finalRequest.get("products"));
        queryParamDetails.put("merchantId", finalRequest.get("merchantId"));
        queryParamDetails.put("tenant", finalRequest.get("tenant"));

        riskGetProductLimitPojo = riskGetProductLimit.get(queryParamDetails, headerDetails);
    }

    private static HashMap requestBuilder(HashMap<String, Object> request) throws Exception {

        request.put("mobile", request.get("mobile"));
        request.put("products", request.get("products"));
        request.put("merchantId", request.get("merchantId"));
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
