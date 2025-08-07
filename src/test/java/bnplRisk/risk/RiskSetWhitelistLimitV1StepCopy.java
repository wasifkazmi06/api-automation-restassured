package bnplRisk.risk;

import api.bnplRisk.RiskSetWhitelistLimitV1;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskSetWhitelistLimitV1Pojo;
import pojos.bnplRisk.request.RiskPLSetWhitelistLimitV1RequestPojo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiskSetWhitelistLimitV1StepCopy {

    public static RiskSetWhitelistLimitV1Pojo riskSetWhitelistLimitV1Pojo = new RiskSetWhitelistLimitV1Pojo();
    public static RiskSetWhitelistLimitV1 riskSetWhitelistLimitV1;
    static RiskPLSetWhitelistLimitV1RequestPojo riskPLSetWhitelistLimitV1RequestPojo = new RiskPLSetWhitelistLimitV1RequestPojo();
    static RiskPLSetWhitelistLimitV1RequestPojo.ProductLimits productLimitsBNPL = new RiskPLSetWhitelistLimitV1RequestPojo.ProductLimits();
    static RiskPLSetWhitelistLimitV1RequestPojo.ProductLimits productLimitsCOF = new RiskPLSetWhitelistLimitV1RequestPojo.ProductLimits();
    static RiskPLSetWhitelistLimitV1RequestPojo.Limit limitBNPL = new RiskPLSetWhitelistLimitV1RequestPojo.Limit();
    static RiskPLSetWhitelistLimitV1RequestPojo.Limit limitCOF = new RiskPLSetWhitelistLimitV1RequestPojo.Limit();
    static RiskPLSetWhitelistLimitV1RequestPojo.InterestRate interestRateBNPL = new RiskPLSetWhitelistLimitV1RequestPojo.InterestRate();
    static RiskPLSetWhitelistLimitV1RequestPojo.InterestRate interestRateCOF_3 = new RiskPLSetWhitelistLimitV1RequestPojo.InterestRate();
    static RiskPLSetWhitelistLimitV1RequestPojo.InterestRate interestRateCOF_6 = new RiskPLSetWhitelistLimitV1RequestPojo.InterestRate();
    static RiskPLSetWhitelistLimitV1RequestPojo.InterestRate interestRateCOF_9 = new RiskPLSetWhitelistLimitV1RequestPojo.InterestRate();
    static RiskPLSetWhitelistLimitV1RequestPojo.InterestRate interestRateCOF_12 = new RiskPLSetWhitelistLimitV1RequestPojo.InterestRate();

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
        //HashMap<String, String> finalRequest = requestBuilder(request);

        requestBuilder(request);
        headerDetails.put("x-tenant", request.get("tenant"));
        System.out.println(riskPLSetWhitelistLimitV1RequestPojo.toString());
        riskSetWhitelistLimitV1Pojo = riskSetWhitelistLimitV1.post(queryParamDetails, headerDetails, riskPLSetWhitelistLimitV1RequestPojo.toString());
    }

    private static void requestBuilder(HashMap<String, String> request) {

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

        try{
            for (int i = 0; i < keys.size(); i++) {
                if (request.get(keys.get(i)) != null) {
                    riskPLSetWhitelistLimitV1RequestPojo.setMobile(request.get("mobile"));
                    riskPLSetWhitelistLimitV1RequestPojo.setReason(request.get("reason"));
                    riskPLSetWhitelistLimitV1RequestPojo.setPanHash(request.get("panHash"));

                    limitBNPL.setLimit(Double.valueOf(request.get("bnpl_limitRisk1")));
                    productLimitsBNPL.setLimit(limitBNPL);
                    productLimitsBNPL.setReason(request.get("reason"));
                    productLimitsBNPL.setDecision(Boolean.valueOf(request.get("decision_bnpl")));

                    riskPLSetWhitelistLimitV1RequestPojo.setProductLimits(productLimitsBNPL);

                    limitCOF.setLimitRisk1(Double.valueOf(request.get("cof_limitRisk1")));
                    limitCOF.setLimitRisk2(Double.valueOf(request.get("cof_limitRisk2")));
                    limitCOF.setLimitRisk3(Double.valueOf(request.get("cof_limitRisk3")));
                    productLimitsCOF.setLimit(limitCOF);
                    productLimitsCOF.setReason(request.get("reason"));
                    productLimitsCOF.setDecision(Boolean.valueOf(request.get("decision_cof")));
                    interestRateCOF_3.setTenure(3);
                    interestRateCOF_3.setInterestRate(Double.valueOf(request.get("cof_limitRisk3")));
                    interestRateCOF_6.setTenure(6);
                    interestRateCOF_6.setInterestRate(Double.valueOf(request.get("cof_limitRisk6")));
                    interestRateCOF_9.setTenure(9);
                    interestRateCOF_9.setInterestRate(Double.valueOf(request.get("cof_limitRisk9")));
                    interestRateCOF_12.setTenure(12);
                    interestRateCOF_12.setInterestRate(Double.valueOf(request.get("cof_limitRisk12")));
                    productLimitsCOF.setInterestRates(interestRateCOF_3);
                    productLimitsCOF.setInterestRates(interestRateCOF_6);
                    productLimitsCOF.setInterestRates(interestRateCOF_9);
                    productLimitsCOF.setInterestRates(interestRateCOF_12);

                    riskPLSetWhitelistLimitV1RequestPojo.setProductLimits(productLimitsCOF);
                }
            }
        }catch (NullPointerException e){

        }

       /* if(request.get("reason").equals("")) {
            request.put("reason", "Testing");
        }

       *//* if(request.get("interestRate12").equals("")) {
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
        }*//*

        if(request.get("decision_cof").equals("")) {
            request.put("decision_cof", "false");
        }

        if(request.get("decision_bnpl").equals("")) {
            request.put("decision_bnpl", "false");
        }

        return request;
    }*/
    }
}
