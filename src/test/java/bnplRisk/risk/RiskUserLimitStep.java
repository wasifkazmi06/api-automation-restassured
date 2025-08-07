package bnplRisk.risk;

import api.bnplRisk.RiskUserLimit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import pojos.bnplRisk.RiskUserLimitPOJO;
import bnplRisk.Constants;
import util.StringTemplate;

import java.lang.reflect.Type;
import java.util.*;

@Slf4j
public class RiskUserLimitStep {

    public static Response riskGetDecisionResponse;
    public static RiskUserLimit riskUserLimit;
    public static List<RiskUserLimitPOJO> riskGetDecisionResponseAsList;

    static {
        try {
            riskUserLimit = new RiskUserLimit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static void RiskUserLimitMethod(HashMap<String, Object> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        headerDetails.put("x-tenant", finalRequest.get("tenant"));

        String riskGetDecisionV1Request = new StringTemplate(Constants.RISK_USER_LIMIT)
                .replace("mobile", finalRequest.get("mobile"))
                .replace("isTransacting", finalRequest.get("isTransacting"))
                .replace("creditLimit", finalRequest.get("creditLimit"))
                .replace("accessKey", finalRequest.get("accessKey"))
                .replace("merchantId", finalRequest.get("merchantId"))
                .toString();

        riskGetDecisionResponse = riskUserLimit.postWithResponse(queryParamDetails, headerDetails, riskGetDecisionV1Request);

        Gson gson = new Gson();
        Type userListType = new TypeToken<List<RiskUserLimitPOJO>>() {}.getType();
        riskGetDecisionResponseAsList = gson.fromJson(RiskUserLimitStep.riskGetDecisionResponse.asString(), userListType);
    }

    private static HashMap requestBuilder(HashMap<String, Object> request) throws Exception {

        request.put("mobile", request.get("mobile"));
        request.put("isTransacting", request.get("isTransacting"));
        request.put("creditLimit", request.get("creditLimit"));
        request.put("accessKey", request.get("accessKey"));
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
