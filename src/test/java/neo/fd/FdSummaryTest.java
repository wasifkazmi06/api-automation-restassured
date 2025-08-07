package neo.fd;

import api.neobank.fd.GetFdSummary;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.fd.Configuration;
import pojos.neobank.fd.FdDetails;


import java.util.HashMap;
import java.util.Map;

import static util.readYml.readYamlFile;

@Slf4j
public class FdSummaryTest {

    public FdSummaryTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    GetFdSummary fetchFdSummary = new GetFdSummary();

    @SneakyThrows
    @Test(priority = 1)
    @Description("Verifying the Fd summary details")
    @Owner("Kareeshma")
    public void getFDSummary() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String TestCustomer_UUID =  configuration.getVerifyUpi_testData().lpUuid;
        FdDetails fdSummaryDetails = fetchFdSummary.get(queryParamDetails, headerDetails,TestCustomer_UUID);
        Assert.assertEquals(fdSummaryDetails.status,"success");
        log.info("Fd matured amount is : " +fdSummaryDetails.getPayload().maturedAmount);
        log.info("Fd invested amount is : " +fdSummaryDetails.getPayload().investedAmount);
        log.info("interest earned on fd is : " +fdSummaryDetails.getPayload().interestEarned);
    }

}
