package lazypay;

import api.lazypay.transaction.*;
import lazypay.transactionFlow.GenerateSignature;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.EligibilityPojo;
import util.StringTemplate;
import java.util.*;

@Slf4j
public class CheckEligibility {

    public static String signature;
    public static String eligibilityVersion = System.getProperty("eligibilityVersion");
    public static String subMerchantID = System.getProperty("subMerchantID");
    public static EligibilityPojo eligibilityPojo = new EligibilityPojo();

    public static EligibilityV0 eligibilityV0;
    public static EligibilityV1 eligibilityV1;
    public static EligibilityV2 eligibilityV2;
    public static EligibilityV3 eligibilityV3;
    public static EligibilityV4 eligibilityV4;
    public static EligibilityV5 eligibilityV5;
    public static EligibilityV6 eligibilityV6;

    static {
        try {
            eligibilityV0 = new EligibilityV0();
            eligibilityV1 = new EligibilityV1();
            eligibilityV2 = new EligibilityV2();
            eligibilityV3 = new EligibilityV3();
            eligibilityV4 = new EligibilityV4();
            eligibilityV5 = new EligibilityV5();
            eligibilityV6 = new EligibilityV6();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CheckEligibility() throws Exception {
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

        Assert.assertTrue(eligibilityPojo.userEligibility);
        Assert.assertTrue(eligibilityPojo.txnEligibility);
        Assert.assertEquals(eligibilityPojo.code,"LP_ELIGIBLE");
    }

    public static void checkEligibilityMethod(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        headerDetails.put("signature", finalRequest.get("signature"));
        headerDetails.put("accessKey", finalRequest.get("accessKey"));
        headerDetails.put("deviceId", finalRequest.get("deviceId"));
        headerDetails.put("platform", finalRequest.get("platform"));

        String eligibilityRequest = new StringTemplate(LazypayConstants.ELIGIBILITY_REQUEST)
                .replace("amount", finalRequest.get("amount"))
                .replace("currency", finalRequest.get("currency"))
                .replace("mobile", finalRequest.get("mobile"))
                .replace("email", finalRequest.get("email"))
                .replace("firstName", finalRequest.get("firstName"))
                .replace("lastName", finalRequest.get("lastName"))
                .replace("source", finalRequest.get("source"))
                .replace("zip", finalRequest.get("zip"))
                .replace("country", finalRequest.get("country"))
                .replace("street1", finalRequest.get("street1"))
                .replace("street2", finalRequest.get("street2"))
                .replace("state", finalRequest.get("state"))
                .replace("city", finalRequest.get("city"))
                .toString();

        JSONObject eligibilityRequestJson = new JSONObject(eligibilityRequest);

        if (finalRequest.get("customParamMaxLimit").equals(String.valueOf(true))) {
            for (int i = 0; i < 11; i++) {
                eligibilityRequestJson.getJSONObject("customParams").put("custom_prop" + i, "prop" + i);
            }
        } else if (finalRequest.get("customParamMaxLength").equals(String.valueOf(true))) {
            eligibilityRequestJson.getJSONObject("customParams").put("custompropfortestingcharacterlimit", "prop5");
        } else if (finalRequest.get("noCustomParam").equals(String.valueOf(true))) {

        } else {
            for (int i = 0; i < 8; i++) {
                eligibilityRequestJson.getJSONObject("customParams").put("custom_prop" + i, "prop" + i);
            }
        }

        int runCount = 0;
        try {
            do {
                switch (finalRequest.get("version")) {
                    case TransactionData.ELIGIBILITY_V0:
                        eligibilityPojo = eligibilityV0.post(queryParamDetails, headerDetails, eligibilityRequestJson.toString());
                        runCount++;
                        break;
                    case TransactionData.ELIGIBILITY_V1:
                        eligibilityPojo = eligibilityV1.post(queryParamDetails, headerDetails, eligibilityRequestJson.toString());
                        runCount++;
                        break;
                    case TransactionData.ELIGIBILITY_V2:
                        eligibilityPojo = eligibilityV2.post(queryParamDetails, headerDetails, eligibilityRequestJson.toString());
                        runCount++;
                        break;
                    case TransactionData.ELIGIBILITY_V3:
                        eligibilityPojo = eligibilityV3.post(queryParamDetails, headerDetails, eligibilityRequestJson.toString());
                        runCount++;
                        break;
                    case TransactionData.ELIGIBILITY_V4:
                        eligibilityPojo = eligibilityV4.post(queryParamDetails, headerDetails, eligibilityRequestJson.toString());
                        runCount++;
                        break;
                    case TransactionData.ELIGIBILITY_V5:
                        eligibilityPojo = eligibilityV5.post(queryParamDetails, headerDetails, eligibilityRequestJson.toString());
                        runCount++;
                        break;
                    case TransactionData.ELIGIBILITY_V6:
                        if (!finalRequest.get("subMerchant").equals("")) {
                            eligibilityRequestJson.getJSONObject("customParams").put("subMerchantId", finalRequest.get("subMerchant"));
                        }
                        eligibilityPojo = eligibilityV6.post(queryParamDetails, headerDetails, eligibilityRequestJson.toString());
                        runCount++;
                        break;
                    default:
                        log.error("Invalid eligibility version: {}", finalRequest.get("version"));
                        break;
                }
            } while (eligibilityPojo.errorCode.equals("LP_GENERIC_ERROR") && runCount < 3);
        }catch  (NullPointerException e) {
        }
    }

    private static HashMap requestBuilder(HashMap<String, String> request) throws Exception {

        request.put("mobile",request.get("mobile"));
        request.put("email", request.get("email"));
        request.put("firstName", request.get("firstName"));
        request.put("lastName", request.get("lastName"));
        request.put("amount", request.get("amount"));
        request.put("currency", request.get("currency"));
        request.put("accessKey", request.get("accessKey"));
        request.put("subMerchant", request.get("subMerchant"));
        request.put("source", request.get("source"));
        request.put("version", request.get("version"));
        request.put("zip", request.get("zip"));
        request.put("country", request.get("country"));
        request.put("city", request.get("city"));
        request.put("street1", request.get("street1"));
        request.put("street2", request.get("street2"));
        request.put("deviceId", request.get("deviceId"));
        request.put("platform", request.get("platform"));
        request.put("signature", request.get("signature"));
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
            request.put("currency", TransactionData.CURRENCY);
        }

        if(request.get("signature").equals("")){
            request.put("signature", GenerateSignature.generateSignature(request.get("mobile") + request.get("email") + request.get("amount")
                    + request.get("currency"), request.get("accessKey")));
        }
        return request;
    }
}
