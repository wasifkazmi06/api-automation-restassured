package neo.fd;

import api.neobank.fd.GetQueueCount;
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
import java.util.concurrent.ThreadLocalRandom;

import static util.readYml.readYamlFile;

@Slf4j
public class GetQueueCountTest {
    public GetQueueCountTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    GetQueueCount fetchQueueCount = new GetQueueCount();

    @SneakyThrows
    @Test(priority = 1)
    @Description("get queue count for fdId which is in queue for secured card and verify the same in db")
    @Owner("Kareeshma")
    public void getQueueCount() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathparam = new HashMap<>();
        pathparam.put("uuid",configuration.getVerifyUpi_testData().lpUuid);
        pathparam.put("mobile",configuration.getVerifyUpi_testData().mobile);
        headerDetails.put("Tenanat","1");
        FdDetails UserQueueData = fetchQueueCount.post(pathparam, headerDetails);
        log.info("response status : "+UserQueueData.getStatus());
        Assert.assertEquals(UserQueueData.status,"success");
        int queueCount = UserQueueData.getPayload().queueValue;
        log.info("user fd queue is  : "+queueCount);

    // Verify the queue value has been replicating in db as well
        int queueCountInDb = FdDBData.getSecuredCardQueueCount("queue_count",configuration.getVerifyUpi_testData().lpUuid);
        Assert.assertEquals(queueCountInDb,queueCount);

    }

    @SneakyThrows
    @Test(priority = 2)
    @Description("get queue count for lpuuid which is not present")
    @Owner("Kareeshma")
    public void getQueueCountWithInvalidlpUuid() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathparam = new HashMap<>();
        int int_random = ThreadLocalRandom.current().nextInt();
        pathparam.put("uuid", Integer.toString(int_random));
        pathparam.put("mobile",configuration.getVerifyUpi_testData().mobile);
        headerDetails.put("Tenanat", "1");
        FdDetails UserQueueData = fetchQueueCount.post(pathparam, headerDetails);
        log.info("response status for user " +int_random+ " : "+ UserQueueData.getStatus());
        Assert.assertEquals(UserQueueData.status, "success");
        Assert.assertEquals(UserQueueData.getPayload().queueValue,0);
        Assert.assertEquals(UserQueueData.getPayload().userStatus,"NOT_PRESENT");

    }
}
