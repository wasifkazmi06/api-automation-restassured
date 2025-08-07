package heimdall.apvChanges;

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
public class FetchCityListTests extends APVHeimdallTestData {

    public FetchCityListTests() throws Exception {
        super();
    }

    BaseTest tests = new BaseTest();
    Map<String, Object> queryParamDetails = new HashMap<>();
    HashMap<String, String> headerDetails = new HashMap<>();


    @Description("Fetch city list with given service providers for landline billtype")
    @Feature("APV_HeimdallChanges")
    @Test(priority = 1, groups = {"APVHeimdallTestData"}, dataProvider = "getCityListWithProvider")
    public void fetchCityListValidations(String testcase, String serviceProvider) throws FileNotFoundException {
        log.info("Getting started with the Fetch City List validations");

        System.out.println("serviceProvider is : " +serviceProvider);
        tests.fetchCityList(queryParamDetails, headerDetails,serviceProvider, testcase);


    }
}
