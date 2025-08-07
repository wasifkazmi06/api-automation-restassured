package neo.fd;

import api.neobank.fd.GetQueueCount;
import api.neobank.fd.QueueCreation;
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
public class QueueCreationTest {
    public QueueCreationTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    QueueCreation securedCardQueue = new QueueCreation();
    GetQueueCount fetchQueueCount = new GetQueueCount();


     int int_random = ThreadLocalRandom.current().nextInt();
    int userQueueStatusInDb;


    @SneakyThrows
    @Test(priority = 1)
    @Description("add user to queue for securedcard and verify the user queue status in db")
    @Owner("Kareeshma")
    public void queueCreation() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathparam = new HashMap<>();
        pathparam.put("uuid",String.valueOf(int_random));
        pathparam.put("mobile",configuration.getVerifyUpi_testData().mobile);
        headerDetails.put("Tenanat","1");

        //check if queue exists
        userQueueStatusInDb = FdDBData.getUserQueueStatus("queue_count",Integer.toString(int_random) );
        log.info("queue count of " + int_random + " is : " + userQueueStatusInDb);

        if(userQueueStatusInDb == 0)
        {
            //Verify the whether queue is already present in db before adding queue
            FdDetails UserQueueData = securedCardQueue.post(pathparam, headerDetails);
            log.info("response status : " + UserQueueData.getStatus());
            Assert.assertEquals(UserQueueData.status, "success");
            log.info("response message on queue creation : " + UserQueueData.message);

            // Verify the queue added has been replicating in db as well
            userQueueStatusInDb = FdDBData.getUserQueueStatus("queue_count",Integer.toString(int_random));
            log.info("Queue added successfully with value : " +userQueueStatusInDb);
        }else{ log.info("User is already in queue : " +userQueueStatusInDb); }

    }
    @SneakyThrows
    @Test(priority = 2)
    @Description("create queue for user for which already queue added")
    @Owner("Kareeshma")
    public void queueCreationWithExistingQueueUser() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathparam = new HashMap<>();
        pathparam.put("uuid",configuration.getVerifyUpi_testData().lpUuid);
        pathparam.put("mobile",configuration.getVerifyUpi_testData().mobile);
        headerDetails.put("Tenanat","1");

        //Verify if queue already added for the user with get queue count api response
        FdDetails queueCount = fetchQueueCount.post(pathparam, headerDetails);
        if(queueCount.getPayload().queueValue != 0)
        {
            //Verify the whether queue is already present in db before adding queue
            FdDetails UserQueueData = securedCardQueue.post(pathparam, headerDetails);
            log.info("response status : " + UserQueueData.getStatus());
            Assert.assertEquals(UserQueueData.status, "ConflictException");
            log.info("response message on queue creation : " + UserQueueData.message);

        }

    }
}
