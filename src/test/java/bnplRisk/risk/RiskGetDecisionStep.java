package bnplRisk.risk;

import api.bnplRisk.RiskGetDecisionV1;
import api.bnplRisk.RiskGetDecisionV2;
import io.qameta.allure.Step;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import pojos.bnplRisk.RiskGetDecisionPojo;
import bnplRisk.Constants;
import bnplRisk.TestData;
import util.StringTemplate;
import java.util.*;

@Slf4j
public class RiskGetDecisionStep {

    public static RiskGetDecisionPojo riskGetDecisionPojo = new RiskGetDecisionPojo();
    public static RiskGetDecisionV1 riskGetDecisionV1;
    public static RiskGetDecisionV2 riskGetDecisionV2;

    static {
        try {
            riskGetDecisionV1 = new RiskGetDecisionV1();
            riskGetDecisionV2 = new RiskGetDecisionV2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void RiskGetDecisionMethod(HashMap<String, Object> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        headerDetails.put("x-tenant", finalRequest.get("tenant"));

        String riskGetDesicionV1Request = new StringTemplate(Constants.RISK_GET_DECISION_V1)
                .replace("mobile", finalRequest.get("mobile"))
                .replace("mobileHash", finalRequest.get("mobileHash"))
                .replace("email", finalRequest.get("email"))
                .replace("emailHash", finalRequest.get("emailHash"))
                .replace("firstName", finalRequest.get("firstName"))
                .replace("middleName", finalRequest.get("middleName"))
                .replace("lastName", finalRequest.get("lastName"))
                .replace("street1", finalRequest.get("street1"))
                .replace("street2", finalRequest.get("street2"))
                .replace("city", finalRequest.get("city"))
                .replace("state", finalRequest.get("state"))
                .replace("country", finalRequest.get("country"))
                .replace("zip", finalRequest.get("zip"))
                .replace("amount", finalRequest.get("amount"))
                .replace("currency", finalRequest.get("currency"))
                .replace("merchantAccessKey", finalRequest.get("merchantAccessKey"))
                .replace("requestId", finalRequest.get("requestId"))
                .replace("integrationMethod", finalRequest.get("integrationMethod"))
                .replace("timestamp", finalRequest.get("timestamp"))
                .replace("isTransacting", finalRequest.get("isTransacting"))
                .replace("uuid", finalRequest.get("uuid"))
                .replace("creditLimit", finalRequest.get("creditLimit"))
                .replace("isBlocked", finalRequest.get("isBlocked"))
                .replace("optedOut", finalRequest.get("optedOut"))
                .replace("requestApi", finalRequest.get("requestApi"))
                .replace("deviceInfo", finalRequest.get("deviceInfo"))
                .replace("deviceRuleEnabled", finalRequest.get("deviceRuleEnabled"))
                .replace("products", finalRequest.get("products"))
                .replace("merchantId", finalRequest.get("merchantId"))
                .replace("subMerchantIdentifier", finalRequest.get("subMerchantIdentifier"))
                .replace("subMerchantId", finalRequest.get("subMerchantId"))
                .replace("doMlFraudCheck", finalRequest.get("doMlFraudCheck"))
                .replace("version", finalRequest.get("version"))
                .toString();

        switch (finalRequest.get("version")) {
            case TestData.RISK_GET_DECISION_V1:
                riskGetDecisionPojo = riskGetDecisionV1.post(queryParamDetails, headerDetails, riskGetDesicionV1Request);
                break;
            case TestData.RISK_GET_DECISION_V2:
                riskGetDecisionPojo = riskGetDecisionV2.post(queryParamDetails, headerDetails, riskGetDesicionV1Request);
                break;
            default:
                log.error("Invalid version: {}", finalRequest.get("version"));
                break;
        }
    }

    private static HashMap requestBuilder(HashMap<String, Object> request) throws Exception {

        request.put("mobile", request.get("mobile"));
        request.put("mobileHash", request.get("mobileHash"));
        request.put("email", request.get("email"));
        request.put("emailHash", request.get("emailHash"));
        request.put("firstName", request.get("firstName"));
        request.put("middleName", request.get("middleName"));
        request.put("lastName", request.get("lastName"));
        request.put("street1", request.get("street1"));
        request.put("street2", request.get("street2"));
        request.put("city", request.get("city"));
        request.put("state", request.get("state"));
        request.put("country", request.get("country"));
        request.put("zip", request.get("zip"));
        request.put("amount", request.get("amount"));
        request.put("currency", request.get("currency"));
        request.put("merchantAccessKey", request.get("merchantAccessKey"));
        request.put("requestId", request.get("requestId"));
        request.put("integrationMethod", request.get("integrationMethod"));
        request.put("timestamp", request.get("timestamp"));
        request.put("isTransacting", request.get("isTransacting"));
        request.put("uuid", request.get("uuid"));
        request.put("creditLimit", request.get("creditLimit"));
        request.put("isBlocked", request.get("isBlocked"));
        request.put("optedOut", request.get("optedOut"));
        request.put("requestApi", request.get("requestApi"));
        request.put("deviceInfo", request.get("deviceInfo"));
        request.put("deviceRuleEnabled", request.get("deviceRuleEnabled"));
        request.put("products", request.get("products"));
        request.put("merchantId", request.get("merchantId"));
        request.put("subMerchantIdentifier", request.get("subMerchantIdentifier"));
        request.put("subMerchantId", request.get("subMerchantId"));
        request.put("doMlFraudCheck", request.get("doMlFraudCheck"));
        request.put("version", request.get("version"));
        request.put("tenant", request.get("tenant"));

        List keys = new ArrayList(request.keySet());

        for(int i=0; i<keys.size(); i++) {
            if(request.get(keys.get(i)) == null && !keys.get(i).toString().equals("deviceInfo")){
                    request.put(keys.get(i).toString(), "");
            }
        }

        if(request.get("currency").equals("")) {
            request.put("currency", TransactionData.CURRENCY);
        }

        if(request.get("isTransacting").equals("")){
            request.put("isTransacting", "0");
        }

        if(request.get("isBlocked").equals("")){
            request.put("isBlocked", "0");
        }

        if(request.get("optedOut").equals("")){
            request.put("optedOut", "0");
        }

        if(request.get("requestId").equals("")){
            request.put("requestId", UUID.randomUUID().toString());
        }

        if(request.get("timestamp").equals("")){
            Date date = new Date();
            long timeMilli = date.getTime();
            request.put("timestamp", String.valueOf(timeMilli));
        }

        if(request.get("deviceRuleEnabled").equals("")){
            request.put("deviceRuleEnabled", "false");
        }

        if(request.get("doMlFraudCheck").equals("")){
            request.put("doMlFraudCheck", "false");
        }

        return request;
    }
}
