package neo.fd;

import api.neobank.fd.WebhookCloseFD;
import api.neobank.fd.WebhookModifyLien;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.fd.Configuration;
import pojos.neobank.fd.FdDetails;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

import static util.readYml.readYamlFile;

@Slf4j
public class WebhookModifyLienAndClose {

    public WebhookModifyLienAndClose() throws Exception {
        super();
    }

    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    WebhookModifyLien webhook_modifyLien = new WebhookModifyLien();
    WebhookCloseFD webhook_fdClosure = new WebhookCloseFD();

    int fdStatusInDb = (Integer) FdDBData.getFdStatus(configuration.getFdDetails_testData().fdId).get("fd_status");
    HashMap<String, Object> lienModifiedFd = FdDBData.getLienModifiedFdDetails(configuration.getFdDetails_testData().fdId);
    String accountNumber = FdDBData.getFdStatus(configuration.getFdDetails_testData().fdId).get("account_number").toString();

    String request= new StringTemplate(NeoConstants.WEBHOOK_MODIFYLIEN_AND_CLOSEFD).replace("status","SUCCESS").replace("accountNumber",accountNumber).
            replace("entityId",configuration.getFdDetails_testData().entityId).toString();


    @SneakyThrows
    @Test
    @Description("closing the fds which are in closureInProgress state and lien modify failed while closing them")
    @Owner("Kareeshma")
    public void modifyLienAndCloseFd() {
        log.info("webhook request body : "+request);

        if(fdStatusInDb == 4 && (Integer) lienModifiedFd.get("leinModifiedCount") < 1) {
            String modifyLien = webhookModifyLien();
            Assert.assertEquals(modifyLien, "success");

            //verify the fd status changed to closed in user_fd_details table and make an entry in lien_modified_fd table
            fdStatusInDb = (Integer) FdDBData.getFdStatus(configuration.getFdDetails_testData().fdId).get("fd_status");
            try {
                Assert.assertEquals(fdStatusInDb, 5);
                int lienModified_FdCount = (Integer) FdDBData.getLienModifiedFdDetails(configuration.getFdDetails_testData().fdId).get("count");
                Assert.assertEquals(lienModified_FdCount, 1);
            }catch(AssertionError e){
                int lienModified_FdCount = (Integer) FdDBData.getLienModifiedFdDetails(configuration.getFdDetails_testData().fdId).get("count");
                Assert.assertEquals(lienModified_FdCount, 1);
                log.info("lien modified but Fd closure failed as sbm server is down : "+"fd_status - "+fdStatusInDb+" & lien_modified_count - "+ lienModified_FdCount);
                String webhook_closeFD = webhookFdClose();
                Assert.assertEquals(webhook_closeFD, "success");
                //verify the fd status changed to closed in user_fd_details table and make an entry in lien_modified_fd table
                fdStatusInDb = (Integer) FdDBData.getFdStatus(configuration.getFdDetails_testData().fdId).get("fd_status");
                Assert.assertEquals(fdStatusInDb, 5);
                String lienModifiedFdStatus = FdDBData.getLienModifiedFdDetails(configuration.getFdDetails_testData().fdId).get("status").toString();
                Assert.assertEquals(lienModifiedFdStatus, "success");
            }
        }
        else {
            String webhook_closeFD = webhookFdClose();
            Assert.assertEquals(webhook_closeFD, "success");
            //verify the fd status changed to closed in user_fd_details table and make an entry in lien_modified_fd table
            fdStatusInDb = (Integer) FdDBData.getFdStatus(configuration.getFdDetails_testData().fdId).get("fd_status");
            Assert.assertEquals(fdStatusInDb, 5);
            String lienModifiedFdStatus = FdDBData.getLienModifiedFdDetails(configuration.getFdDetails_testData().fdId).get("status").toString();
            Assert.assertEquals(lienModifiedFdStatus, "success");
        }
    }

    @SneakyThrows
    @Description("Closing Fd which are failed to get closed from m2p")
    @Owner("Kareeshma")
    public String webhookModifyLien() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        FdDetails webhook_lienModified = webhook_modifyLien.post(queryParamDetails, headerDetails, request);
        return webhook_lienModified.status;
    }
    @SneakyThrows
    @Description("Closing Fd which are failed to get closed from m2p")
    @Owner("Kareeshma")
    public String webhookFdClose() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        FdDetails webhook_fdClose = webhook_fdClosure.post(queryParamDetails, headerDetails, request);
        return webhook_fdClose.status;
    }
}
