package bnplRisk.fraud;

import api.bnplRisk.FraudGetDecision;
import io.qameta.allure.Step;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import pojos.bnplRisk.FraudGetDecisionPojo;
import bnplRisk.Constants;
import util.StringTemplate;
import java.util.*;

@Slf4j
public class FraudGetDecisionStep {

    public static FraudGetDecisionPojo fraudGetDecisionPojo = new FraudGetDecisionPojo();
    public static api.bnplRisk.FraudGetDecision fraudGetDecision;

    static {
        try {
            fraudGetDecision = new FraudGetDecision();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void fraudGetDecisionMethod(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        String riskGetDecisionV1Request = new StringTemplate(Constants.FRAUD_GET_DECISION)
                .replace("email", finalRequest.get("email"))
                .replace("mobile", finalRequest.get("mobile"))
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
                .replace("subMerchantId", finalRequest.get("subMerchantId"))
                .replace("isTransacting", finalRequest.get("isTransacting"))
                .replace("uuid", finalRequest.get("uuid"))
                .replace("creditLimit", finalRequest.get("creditLimit"))
                .replace("isBlocked", finalRequest.get("isBlocked"))
                .replace("optedOut", finalRequest.get("optedOut"))
                .replace("requestApi", finalRequest.get("requestApi"))
                .replace("sourceDevicePlatform", finalRequest.get("sourceDevicePlatform"))
                .replace("userId", finalRequest.get("userId"))
                .replace("deviceId", finalRequest.get("deviceId"))
                .replace("policyId", finalRequest.get("policyId"))
                .replace("deviceRuleEnabled", finalRequest.get("deviceRuleEnabled"))
                .replace("products", finalRequest.get("products"))
                .toString();

        fraudGetDecisionPojo = fraudGetDecision.post(queryParamDetails, headerDetails, riskGetDecisionV1Request);
    }

    private static HashMap requestBuilder(HashMap<String, String> request) throws Exception {

        request.put("email", request.get("email"));
        request.put("mobile", request.get("mobile"));
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
        request.put("subMerchantId", request.get("subMerchantId"));
        request.put("isTransacting", request.get("isTransacting"));
        request.put("uuid", request.get("uuid"));
        request.put("creditLimit", request.get("creditLimit"));
        request.put("isBlocked", request.get("isBlocked"));
        request.put("optedOut", request.get("optedOut"));
        request.put("requestApi", request.get("requestApi"));
        request.put("sourceDevicePlatform", request.get("sourceDevicePlatform"));
        request.put("userId", request.get("userId"));
        request.put("deviceId", request.get("deviceId"));
        request.put("policyId", request.get("policyId"));
        request.put("deviceRuleEnabled", request.get("deviceRuleEnabled"));
        request.put("products", request.get("products"));

        List keys = new ArrayList(request.keySet());

        for(int i=0; i<keys.size(); i++) {
            if (request.get(keys.get(i)) == null) {
                request.put(keys.get(i).toString(), "");
            }
        }

        if(request.get("currency").equals("")) {
            request.put("currency", TransactionData.CURRENCY);
        }
        if(request.get("requestId").equals("")){
            request.put("requestId", UUID.randomUUID().toString());
        }
        if(request.get("street1").equals("")){
            request.put("street1", TransactionData.STREET1);
        }
        if(request.get("street2").equals("")){
            request.put("street2", TransactionData.STREET2);
        }
        if(request.get("city").equals("")){
            request.put("city", TransactionData.CITY);
        }
        if(request.get("state").equals("")){
            request.put("state", TransactionData.STATE);
        }
        if(request.get("country").equals("")){
            request.put("country", TransactionData.COUNTRY);
        }
        if(request.get("zip").equals("")){
            request.put("zip", TransactionData.ZIP);
        }
        if(request.get("isTransacting").equals("")){
            request.put("isTransacting", "1");
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
        if(request.get("sourceDevicePlatform").equals("")){
            request.put("sourceDevicePlatform", TransactionData.PLATFORM_NAME_IOS);
        }
        if(request.get("userId").equals("")){
            request.put("userId", TransactionData.USER_MOBILE_VALID38_LPUSERID);
        }
        if(request.get("deviceId").equals("")){
            request.put("deviceId", TransactionData.TRUSTED_DEVICE1);
        }
        if(request.get("policyId").equals("")){
            request.put("policyId", TransactionData.POLICY_ID);
        }
        if(request.get("deviceRuleEnabled").equals("")){
            request.put("deviceRuleEnabled", "false");
        }



        return request;
    }
}
