package neo.fd;

import api.neobank.fd.GetInterestDetails;
import api.neobank.fd.GetPenaltyInterestDetails;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.fd.Configuration;
import pojos.neobank.fd.FdDetails;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

import static util.readYml.readYamlFile;

@Slf4j
public class FdInterestDetailsTest {

    public FdInterestDetailsTest() throws Exception {
        super();
    }
        Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
        GetInterestDetails interestDetails = new GetInterestDetails();
        GetPenaltyInterestDetails penaltyInterestDetails = new GetPenaltyInterestDetails();

        String TestCustomer_UUID =  configuration.getVerifyUpi_testData().lpUuid;

        @SneakyThrows
        @Test(priority = 1)
        @Description("Verifying the Fd Details of existing FD Id")
        @Owner("Kareeshma")
        public void getFDInterestDetails() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        FdDetails fdInterestDetails = interestDetails.get(queryParamDetails, headerDetails,TestCustomer_UUID);
        Assert.assertEquals(fdInterestDetails.status,"success");
        log.info("Fd interestRate is : " +fdInterestDetails.getPayload().interestRate);
        log.info("Fd compoundInterestFormulae is : " +fdInterestDetails.getPayload().compoundInterestFormulae);
        log.info("Fd creditLimitRatio is : " +fdInterestDetails.getPayload().creditLimitRatio);
        log.info("Fd minFdAmount is : " +fdInterestDetails.getPayload().minFdAmount);
        log.info("Fd popUpEnabled is : " +fdInterestDetails.getPayload().popUpEnabled);
        log.info("Fd unsecuredCardRestricted is : " +fdInterestDetails.getPayload().unsecuredCardRestricted);
    }

    @SneakyThrows
    @Test(priority = 2)
    @Description("fetch the verified UPIId present for an user")
    @Owner("Kareeshma")
    public void getPenaltyInterestDetails() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat", "1");
        String request = new StringTemplate().replace("lpUuid", TestCustomer_UUID).replace("fdId", configuration.getFdDetails_testData().fdId).toString();
        FdDetails fetchFDCount = penaltyInterestDetails.post(queryParamDetails, headerDetails, request);

        if(fetchFDCount.status.equalsIgnoreCase("success")){
        log.info("penalty interest rate is " + fetchFDCount.getPayload().penaltyInterestRate);
        log.info("matured amount is " + fetchFDCount.getPayload().maturedAmount);
        log.info("fd maturity date is " + fetchFDCount.getPayload().maturityDate);
        }
        else{
            Assert.assertEquals(fetchFDCount.status,"Exception");
            log.info("FD either not initiated or in Failed state");
        }


    }
}
