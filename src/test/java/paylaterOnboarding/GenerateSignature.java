package paylaterOnboarding;

import org.apache.http.client.utils.URIBuilder;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class GenerateSignature {
    static String signature;

    // This method will return the signature based on the data
    public static String generateSignature(Map<String, String> data, String accessKey) throws Exception {
        api.paylaterOnboarding.authentication.GenerateSignature generateSignature = new api.paylaterOnboarding.authentication.GenerateSignature();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String headers = getHeaderParams(data);
        System.out.println("headers are "+ headers);
        headerDetails.put("data", headers);
        headerDetails.put("accessKey", accessKey);
        signature = generateSignature.postReturnResponse(queryParamDetails, headerDetails).getBody().asString();
        return signature;

    }

    // This method will construct the headers params without encoding
    public static String getHeaderParams(Map<String, String> map) throws Exception {

        URIBuilder ub = new URIBuilder("");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            ub.addParameter(entry.getKey(), entry.getValue());
        }
        return URLDecoder.decode(ub.toString().replace("/", "").replace("?", ""), "UTF-8");
    }
}
