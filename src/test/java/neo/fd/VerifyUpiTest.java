package neo.fd;

import api.neobank.fd.GetVerifiedUpiList;
import api.neobank.fd.VerifyUpi;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.fd.*;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;
import static util.readYml.readYamlFile;

@Slf4j
public class VerifyUpiTest {

    public VerifyUpiTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    VerifyUpi upiVerification = new VerifyUpi();
    GetVerifiedUpiList verifiedUpiList = new GetVerifiedUpiList();

    @SneakyThrows
    @Test(priority = 1)
    @Description("Verifying the valid UpiId")
    @Owner("Kareeshma")
    public void VerifyUpiId() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate().replace("upiId",configuration.getVerifyUpi_testData().upiId).replace("umUuid",configuration.getVerifyUpi_testData().umUuid).replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).toString();

        FdVerifyUpi upiStatus = upiVerification.post(queryParamDetails, headerDetails, request);
        log.info("response status : "+upiStatus.getStatus());
        Assert.assertEquals(upiStatus.status,"success");
        log.info("response message : " +upiStatus.message);

        // Verify the UPI Id has been replicating in db as well along with name_match_score which confirms UPIId verified
        int verifiedUPIData = FdDBData.getVerifiedUPIId("name_match_score",configuration.getVerifyUpi_testData().upiId);
        Assert.assertEquals(verifiedUPIData,1);

    }

    @SneakyThrows
    @Test(priority = 2)
    @Description("Verifying the UPIId with invalid UpiId and verify on namescore for the upi id in db")
    @Owner("Kareeshma")
    public void VerifyWithInvalidUpiId() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate().replace("upiId",configuration.getVerifyUpi_testData().upiId+1).replace("umUuid",configuration.getVerifyUpi_testData().umUuid).replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).toString();

        FdVerifyUpi upiStatus = upiVerification.post(queryParamDetails, headerDetails, request);
        log.info("response status : "+upiStatus.getStatus());
        Assert.assertEquals(upiStatus.status,"UpiVerificationException");
        log.info("response error message : " +upiStatus.message);

    }

    @SneakyThrows
    @Test(priority = 3)
    @Description("Verifying other user UpiId which doesn't match with loggedin user's KYC name")
    @Owner("Kareeshma")
    public void VerifyWithOtherUserUpiId() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate().replace("upiId",configuration.getVerifyUpi_testData().upiId2).replace("umUuid",configuration.getVerifyUpi_testData().umUuid).replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).toString();

        FdVerifyUpi upiStatus = upiVerification.post(queryParamDetails, headerDetails, request);
        log.info("response status : "+upiStatus.getStatus());
        Assert.assertEquals(upiStatus.status,"KycNameMatchException");
        log.info("response error message : " +upiStatus.message);

    }

    @SneakyThrows
    @Test
    @Description("fetch the verified UPIId present for an user")
    @Owner("Kareeshma")
    public void fetchVerifiedUpi() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat", "1");

        String request= new StringTemplate().replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).replace("amount",configuration.getCreateOrder_nomineedetails().amount).toString();
        String TestCustomer_UUID =  configuration.getVerifyUpi_testData().lpUuid;
        FdDetails fetchFDCount = verifiedUpiList.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(fetchFDCount.status,"success");
        log.info("Verified UPI ID for user " +TestCustomer_UUID + " is : " +fetchFDCount.getPayload().getUpiList());
    }
}
