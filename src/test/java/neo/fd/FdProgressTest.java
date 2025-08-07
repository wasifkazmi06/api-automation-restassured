package neo.fd;


import api.neobank.fd.GetFdProgress;
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
public class FdProgressTest {

    public FdProgressTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    GetFdProgress fetchFdProgress = new GetFdProgress();

    @SneakyThrows
    @Test(priority = 1)
    @Description("Verifying the Fd Progress details of existing FD Id")
    @Owner("Kareeshma")
    public void getFDProgress() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat", "1");
        String TestCustomer_UUID =  configuration.getVerifyUpi_testData().lpUuid;
        FdDetails fdProgressDetails = fetchFdProgress.get(queryParamDetails, headerDetails,TestCustomer_UUID);
        Assert.assertEquals(fdProgressDetails.status,"success");
        log.info("Fd Status is : " +fdProgressDetails.getPayload().fdStatus);
        log.info("SBM KYC Status is : " +fdProgressDetails.getPayload().sbmKycStatus);
    }
    }
