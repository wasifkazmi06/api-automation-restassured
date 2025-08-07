package neo.fd;

import api.neobank.fd.GetQueueCount;
import api.neobank.fd.QueueCreation;
import api.neobank.fd.RemoveFromRedis;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.fd.Configuration;
import pojos.neobank.fd.FdDetails;
import pojos.neobank.fd.FdGenericPayload;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

import static util.readYml.readYamlFile;

@Slf4j
public class FdRemoveFromRedis
{
    public FdRemoveFromRedis() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    RemoveFromRedis fetchQueueCount = new RemoveFromRedis();

    @SneakyThrows
    @Test(priority = 2)
    @Description("fetch the verified UPIId present for an user")
    @Owner("Kareeshma")
    public void getPenaltyInterestDetails() {
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathparam = new HashMap<>();
        pathparam.put("uuid",configuration.getVerifyUpi_testData().lpUuid);
        headerDetails.put("Tenanat","1");
        FdGenericPayload removeFromRedis = fetchQueueCount.post(pathparam, headerDetails);
        Assert.assertEquals(removeFromRedis.status,"success");
       if(removeFromRedis.payload.equalsIgnoreCase("true")) {
           log.info("removed from redis successfully");
       }else
            log.info("there is no payload to remove from redis");

        }
}
