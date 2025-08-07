package heimdall.apvChanges;


import heimdall.common.APVHeimdallTestData;
import heimdall.common.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.*;


@Slf4j
public class FetchFeildsTests extends APVHeimdallTestData {

    public FetchFeildsTests() throws Exception {
        super();
    }

    BaseTest tests = new BaseTest();
    Map<String, Object> queryParamDetails = new HashMap<>();
    HashMap<String, String> headerDetails = new HashMap<>();


    @Description("check the service providers for bill types")
    @Feature("APV_HeimdallChanges")
    @Test(priority = 1, groups = {"APVHeimdallTestData"}, dataProvider = "getFieldsForBilltypes")
    public void fetchFieldsValidations(String testcase,String billtype) throws FileNotFoundException {
         log.info("Getting started with the Fetch fields validations");
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);

        tests.fetchFeilds(queryParamDetails, headerDetails,testcase,billtype);
        log.info("Stopped with the Fetch fields validations");


    }

}
