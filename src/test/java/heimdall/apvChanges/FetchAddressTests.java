package heimdall.apvChanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import heimdall.common.APVHeimdallTestData;
import heimdall.common.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;


import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class FetchAddressTests extends APVHeimdallTestData
{
    public FetchAddressTests()throws Exception {
        super();
    }

    BaseTest tests = new BaseTest();
    Map<String, Object> queryParamDetails = new HashMap<>();
    HashMap<String, String> headerDetails = new HashMap<>();

    @Description("To fetch the address details for given billtype and service provider")
    @Feature("APV_HeimdallChanges")
    @Test(priority = 1, groups = {"APVHeimdallTestData"}, dataProvider = "getAddressData")
    public void fetchAddressValidations(String testcase,String requestId,String source,String type,String serviceProvider,String key,String value) throws JsonProcessingException, FileNotFoundException {

        log.info("Getting started with the GeoLocation validations");
        String payload = tests.addressPayload(requestId, source, type, serviceProvider, key, value);
        log.info(payload);
        tests.fetchaAddress(queryParamDetails, headerDetails, payload, testcase);
        log.info("Stopped with the Fetch address validations");
    }
}
