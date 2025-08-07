package heimdall.apvChanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import heimdall.common.APVHeimdallTestData;
import heimdall.common.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import pojos.heimdall.latLngData;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FetchProximityDistanceTests extends APVHeimdallTestData
{
    public FetchProximityDistanceTests()throws Exception {
        super();
    }

    BaseTest tests = new BaseTest();
    Map<String, Object> queryParamDetails = new HashMap<>();
    HashMap<String, String> headerDetails = new HashMap<>();


    @Description("To check the proximity distance b/w source and target lat long")
    @Feature("APV_HeimdallChanges")
    @Test(priority = 1, groups = {"APVHeimdallTestData"}, dataProvider = "getProximityData")
    public void fetchProximityTest(String testcase, String requestId, String source, ArrayList sourceLng, latLngData targetLng, String AuthKey) throws JsonProcessingException, FileNotFoundException {

        log.info("Getting started with the GeoLocation validations");
        String payload = tests.proximityPayload(requestId,source,sourceLng,targetLng);
        log.info(payload);
        headerDetails.put("X-API-Key", AuthKey);
        tests.fetchProximityDistance(queryParamDetails, headerDetails, payload,testcase);

    }


}
