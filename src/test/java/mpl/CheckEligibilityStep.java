package mpl;

import io.qameta.allure.Step;
import lazypay.CheckEligibility;
import lazypay.MakeTransaction;
import lazypay.transactionFlow.GenerateSignature;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.mpl.EligibilityPOJO;
import util.StringTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CheckEligibilityStep {

    public static String signature;
    public static String eligibilityVersion = System.getProperty("eligibilityVersion");
    public static String subMerchantID = System.getProperty("subMerchantID");
    public static EligibilityPOJO eligibilityPOJO = new EligibilityPOJO();
    public static api.mpl.EligibilityV0 eligibilityV0;

    static {
        try {
            eligibilityV0 = new api.mpl.EligibilityV0();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkEligibilityTest() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", MakeTransaction.mobile);
        request.put("email", MakeTransaction.email);
        request.put("amount", MakeTransaction.amount);
        request.put("accessKey", MakeTransaction.merchantAccessKey);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("subMerchant", subMerchantID);
        request.put("version", eligibilityVersion);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(eligibilityPOJO.txnEligibility);
        Assert.assertEquals(eligibilityPOJO.code,"LP_ELIGIBLE");
    }

    @Step
    public static void checkEligibilityMethod(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest =  requestBuilder(request);

        headerDetails.put("signature", finalRequest.get("signature"));
        headerDetails.put("accessKey", finalRequest.get("accessKey"));
        headerDetails.put("deviceId", finalRequest.get("deviceId"));
        headerDetails.put("platform", finalRequest.get("platform"));
        headerDetails.put("userIpAddress", finalRequest.get("userIpAddress"));

        String eligibilityRequest= new StringTemplate(Constants.ELIGIBILITY_REQUEST)
                .replace("mobile", finalRequest.get("mobile"))
                .replace("email", finalRequest.get("email"))
                .replace("amount", finalRequest.get("amount"))
                .replace("currency", finalRequest.get("currency"))
                .replace("street1", finalRequest.get("street1"))
                .replace("street2",finalRequest.get("street2"))
                .replace("city",finalRequest.get("city"))
                .replace("state", finalRequest.get("state"))
                .replace("country", finalRequest.get("country"))
                .replace("zip", finalRequest.get("zip"))
                .replace("landmark", finalRequest.get("landmark"))
                .replace("residenceType", finalRequest.get("residenceType"))
                .toString();

        JSONObject eligibilityRequestJson = new JSONObject(eligibilityRequest);

        if(finalRequest.get("customParamMaxLimit").equals(String.valueOf(true))){
            for(int i=0; i<11; i++)
            {
                eligibilityRequestJson.getJSONObject("customParams").put("custom_prop"+i, "prop"+i);
            }
        }else if(finalRequest.get("customParamMaxLength").equals(String.valueOf(true))){
            eligibilityRequestJson.getJSONObject("customParams").put("custompropfortestingcharacterlimit", "prop5");
        }else if(finalRequest.get("noCustomParam").equals(String.valueOf(true))){
        }else{
            for(int i=0; i<8; i++)
            {
                eligibilityRequestJson.getJSONObject("customParams").put("custom_prop"+i, "prop"+i);
            }
        }

        switch (finalRequest.get("version")) {
            case TransactionData.ELIGIBILITY_V0:
                eligibilityPOJO = eligibilityV0.post(queryParamDetails, headerDetails, eligibilityRequestJson.toString());
                break;
            default:
                log.error("Invalid eligibility version: {}", finalRequest.get("version"));
                break;
        }
    }

    private static HashMap requestBuilder(HashMap<String, String> request) throws Exception {

        request.put("mobile", request.get("mobile"));
        request.put("email", request.get("email"));
        request.put("amount", request.get("amount"));
        request.put("currency", request.get("currency"));
        request.put("street1", request.get("street1"));
        request.put("street2",request.get("street2"));
        request.put("city",request.get("city"));
        request.put("state", request.get("state"));
        request.put("country", request.get("country"));
        request.put("zip", request.get("zip"));
        request.put("landmark", request.get("landmark"));
        request.put("residenceType", request.get("residenceType"));
        request.put("signature", request.get("signature"));
        request.put("accessKey", request.get("accessKey"));
        request.put("deviceId", request.get("deviceId"));
        request.put("platform", request.get("platform"));
        request.put("userIpAddress", request.get("userIpAddress"));
        request.put("customParamMaxLimit", request.get("customParamMaxLimit"));
        request.put("customParamMaxLength", request.get("customParamMaxLength"));
        request.put("noCustomParam", request.get("noCustomParam"));

        List keys = new ArrayList(request.keySet());

        for(int i=0; i<keys.size(); i++) {
            if(request.get(keys.get(i)) == null){
                request.put(keys.get(i).toString(), "");
            }
        }

        if(request.get("currency").equals("")) {
            request.put("currency", TestData.CURRENCY);
        }

        if(request.get("signature").equals("")){
            request.put("signature", GenerateSignature.generateSignature("merchantAccessKey=" + request.get("accessKey") + "&mobile=" + request.get("mobile")
                    + "&email" + request.get("email") + "&amount=" + request.get("amount") + "&currency" + request.get("currency"), request.get("accessKey")));
        }
        return request;
    }
}