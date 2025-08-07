package mpl;

import io.qameta.allure.Step;
import lazypay.transactionFlow.GenerateSignature;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import pojos.mpl.TransactionAuthorisationPOJO;
import util.StringTemplate;
import java.util.*;

@Slf4j
public class TransactionAuthorisationStep {

    public static TransactionAuthorisationPOJO transactionAuthorisationPOJO = new TransactionAuthorisationPOJO();
    public static api.mpl.TransactionAuthorisationV0 transactionAuthorisationV0;
    //public static String merchantTxnId;

    static {
        try {
            transactionAuthorisationV0 = new api.mpl.TransactionAuthorisationV0();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void transactionAuthorisationMethod(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest =  requestBuilder(request);

        headerDetails.put("signature", finalRequest.get("signature"));
        headerDetails.put("accessKey", finalRequest.get("accessKey"));
        headerDetails.put("deviceId", finalRequest.get("deviceId"));
        headerDetails.put("platform", finalRequest.get("platform"));
        headerDetails.put("userIpAddress", finalRequest.get("userIpAddress"));

        String transactionAuthorisationRequest= new StringTemplate(Constants.TRANSACTION_AUTHORISATION_REQUEST)
                .replace("mobile", finalRequest.get("mobile"))
                .replace("email", finalRequest.get("email"))
                .replace("amount", finalRequest.get("amount"))
                .replace("currency", finalRequest.get("currency"))
                .replace("merchantTxnId", finalRequest.get("merchantTxnId"))
                .replace("street1", finalRequest.get("street1"))
                .replace("street2",finalRequest.get("street2"))
                .replace("city",finalRequest.get("city"))
                .replace("state", finalRequest.get("state"))
                .replace("country", finalRequest.get("country"))
                .replace("zip", finalRequest.get("zip"))
                .toString();

        JSONObject transactionAuthorisationRequestJson = new JSONObject(transactionAuthorisationRequest);

        if(finalRequest.get("customParamMaxLimit").equals(String.valueOf(true))){
            for(int i=0; i<11; i++)
            {
                transactionAuthorisationRequestJson.getJSONObject("customParams").put("custom_prop"+i, "prop"+i);
            }
        }else if(finalRequest.get("customParamMaxLength").equals(String.valueOf(true))){
            transactionAuthorisationRequestJson.getJSONObject("customParams").put("custompropfortestingcharacterlimit", "prop5");
        }else if(finalRequest.get("noCustomParam").equals(String.valueOf(true))){
        }else{
            for(int i=0; i<8; i++)
            {
                transactionAuthorisationRequestJson.getJSONObject("customParams").put("custom_prop"+i, "prop"+i);
            }
        }

        switch (finalRequest.get("version")) {
            case TransactionData.INITIATE_PAY_V0:
                transactionAuthorisationPOJO = transactionAuthorisationV0.post(queryParamDetails, headerDetails, transactionAuthorisationRequestJson.toString());
                break;
            default:
                log.error("Invalid version: {}", finalRequest.get("version"));
                break;
        }
    }

    private static HashMap requestBuilder(HashMap<String, String> request) throws Exception {

        request.put("mobile", request.get("mobile"));
        request.put("email", request.get("email"));
        request.put("amount", request.get("amount"));
        request.put("currency", request.get("currency"));
        request.put("merchantTxnId", request.get("merchantTxnId"));
        request.put("street1", request.get("street1"));
        request.put("street2",request.get("street2"));
        request.put("city",request.get("city"));
        request.put("state", request.get("state"));
        request.put("country", request.get("country"));
        request.put("zip", request.get("zip"));
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

        if(request.get("merchantTxnId").equals("")) {
            request.put("merchantTxnId", UUID.randomUUID().toString());
        }

        if(request.get("signature").equals("")){
            request.put("signature", GenerateSignature.generateSignature("merchantAccessKey=" + request.get("accessKey") + "&transactionId="
                    + request.get("merchantTxnId") + "&amount=" + request.get("amount"), request.get("accessKey")));
        }

        return request;
    }
}