package lazypay.transactionFlow;

import java.util.HashMap;
import java.util.Map;

public class GenerateSignature {

    static String signature;

    public static String generateSignature(String data, String accessKey) throws  Exception{
        api.lazypay.transaction.GenerateSignature generateSignature = new api.lazypay.transaction.GenerateSignature();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("data", data);
        headerDetails.put("accessKey", accessKey);
        signature = generateSignature.postReturnResponse(queryParamDetails, headerDetails).getBody().asString();
        return signature;

    }
}
