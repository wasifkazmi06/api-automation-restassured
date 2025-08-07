package neo.fd;

import api.neobank.fd.FetchNomineeDetails;
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
public class FdNomineeDetailsTest {

    public FdNomineeDetailsTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    FetchNomineeDetails fetchNomineeDetails = new FetchNomineeDetails();

    @SneakyThrows
    @Test(priority = 1)
    @Description("Verifying the Fd Details of existing FD Id")
    @Owner("Kareeshma")
    public void getFDDetails() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String TestCustomer_UUID =  configuration.getVerifyUpi_testData().lpUuid;
        FdDetails fdNomineeDetails = fetchNomineeDetails.get(queryParamDetails, headerDetails,TestCustomer_UUID);
        Assert.assertEquals(fdNomineeDetails.status,"success");
        if(fdNomineeDetails.getPayload() != null){
        log.info("Fd nominee name is : " +fdNomineeDetails.getPayload().name);
        log.info("Fd nominee DOB is : " +fdNomineeDetails.getPayload().dateOfBirth);
        log.info("Fd nominee relationship is : " +fdNomineeDetails.getPayload().relationship);}
        else{
            log.info("No Nominee added to the fd account for user " +TestCustomer_UUID);
        }
    }
}
