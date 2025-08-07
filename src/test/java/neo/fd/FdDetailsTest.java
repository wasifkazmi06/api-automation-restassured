package neo.fd;

import api.neobank.fd.GetFdDetails;
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
public class FdDetailsTest {


    public FdDetailsTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    GetFdDetails fetchFdDetails = new GetFdDetails();

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
        String request= new StringTemplate().replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).replace("fdId",configuration.getFdDetails_testData().fdId).toString();

        FdDetails fdData = fetchFdDetails.post(queryParamDetails, headerDetails, request);
        log.info("response status : "+fdData.getStatus());
        Assert.assertEquals(fdData.status,"success");
        log.info("FD STATUS : " +fdData.getPayload().fdStatus);
        if(fdData.getPayload().getNomineeDetailsResponse()!=null){
            System.out.println("Nominee added for this FD is : " +fdData.getPayload().getNomineeDetailsResponse().name);
        }

    }

    @SneakyThrows
    @Test(priority = 2)
    @Description("Verifying the Fd Details for the FD Id which is not present or created")
    @Owner("Kareeshma")
    public void getFDDetailsWithNonCreatedFdId() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate().replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).replace("fdId","1000002009").toString();

        FdDetails fdData = fetchFdDetails.post(queryParamDetails, headerDetails, request);
        log.info("response status : "+fdData.getStatus());
        Assert.assertEquals(fdData.status,"EntityNotFoundException");
        log.info("response status : "+fdData.getStatus());
        Assert.assertEquals(fdData.message,"Invalid Fd Id");

    }
}
