package heimdall.apvChanges;

import heimdall.common.APVHeimdallTestData;
import heimdall.common.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import util.StringTemplate;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FetchGeoLocationTests extends APVHeimdallTestData
{
    public  FetchGeoLocationTests()throws Exception {
        super();
    }

    BaseTest tests = new BaseTest();
    String request ;
    Map<String, Object> queryParamDetails = new HashMap<>();
    HashMap<String, String> headerDetails = new HashMap<>();

    @Description("To check the lat & lng details for provided address / pincode")
    @Feature("APV_HeimdallChanges")
    @Test(priority = 1, groups = {"APVHeimdallTestData"}, dataProvider = "validDataForGeoLocation")
    public void fetchGeoLocationValidCases(String testcase, String address, String pincode, String requestid, String source, String AuthKey) {
        log.info("Getting started with the GeoLocation validations");
        request = new StringTemplate().replace("address", address).replace("pincode", pincode).
                replace("requestId", requestid).replace("source", source).toString();
        headerDetails.put("X-API-Key", AuthKey);
        tests.geoLocationForValidCases(queryParamDetails, headerDetails, request);

    }


    @Description("To check the errorCode & errorMsg with invalid requests")
    @Feature("APV_HeimdallChanges")
    @Test(priority = 2, groups = {"APVHeimdallTestData"}, dataProvider = "invalidDataForGeoLocation")
    public void fetchGeoLocationInvalidCases(String testcase, String address, String pincode, String requestid, String source, String AuthKey) throws FileNotFoundException {
        log.info("Getting started with the GeoLocation validations");

        request = new StringTemplate().replace("address", address).replace("pincode", pincode).
                replace("requestId", requestid).replace("source", source).toString();
        headerDetails.put("X-API-Key", AuthKey);
        tests.geoLocationForInvalidCases(testcase,queryParamDetails, headerDetails, request);

    }


}
