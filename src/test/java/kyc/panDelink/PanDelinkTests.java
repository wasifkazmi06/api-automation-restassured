package kyc.panDelink;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.DBValidations;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import pojos.kyc.apis.InitiatePanDelinkPojo;
import util.DeleteUser;

import java.util.Properties;


@Slf4j
public class PanDelinkTests extends PanDelinkFunctionality {

    public PanDelinkTests() throws Exception {

    }

    String uuid1 = "5788085793037870401";
    String uuid2 = "5189699679495393484";
     String kycCaseId;
    DBValidations dbData = new DBValidations();

    @Description("Initiate the pan delink")
    @Feature("PAN_DELINK")
    @Test(priority = 1)
    public void initiatePanDelinkWithValidInputs() throws Exception {
        //Purge any existing user
        purgeUser(PAN_DELINK_MOBILE1);
        purgeUser(PAN_DELINK_MOBILE2);
        // commenting old purge code as its not functioning as expected
       /* Properties properties = System.getProperties();
        String env = properties.getProperty("env");
        DeleteUser.deleteUserMethod(BaseTestData.PAN_DELINK_MOBILE1, env, "");
        DeleteUser.deleteUserMethod(BaseTestData.PAN_DELINK_MOBILE2, env, ""); */
        //Initiate KYC and upload pan doc for first user
        uploadDocForFirstUser(uuid1);

        //Initiate KYC and upload pan doc for second user
        kycCaseId = initiateKycANDUploadDoc(uuid2);
        log.info("kycCaseId is : " +kycCaseId);

        Assert.assertTrue(dbData.validatePanDelinkEntry(uuid1,uuid2),"Failed to create entry in pandelink table");

        // Inititate the pan delink
        InitiatePanDelinkPojo pandDelinkResponse = initiatePanDelink(uuid2, kycCaseId, "EDHPS2950H");
        Assert.assertEquals(pandDelinkResponse.status, "SUCCESS", "Failed to get the response status");
        Assert.assertEquals(pandDelinkResponse.statusCode, "PAN_DELINK_INITIATED", "Failed to Initiate the delinking");
        Assert.assertTrue(dbData.fetchPanDilinkEntryStatus(uuid1,"IN_PROGRESS"),"Failed to initiate the pan delinking");

    }
 /*   @Description("Initiate the pan delink")
    @Feature("PAN_DELINK")
    @Test(priority = 2)
    public void initiatePanDelinkWithPendingOutstanding() throws Exception {
        //Purge any existing user
        Properties properties = System.getProperties();
        String env = properties.getProperty("env");
        DeleteUser.deleteUserMethod(BaseTestData.PAN_DELINK_MOBILE1, env, "");
        DeleteUser.deleteUserMethod(BaseTestData.PAN_DELINK_MOBILE2, env, "");
        //Initiate KYC and upload pan doc for first user
        uploadDocForFirstUser(uuid1);

        // do txn for first user
        PayWithTokenV0LP(PAN_DELINK_MOBILE1);

        //Initiate KYC and upload pan doc for second user
        kycCaseId = initiateKycANDUploadDoc(uuid2);

        // Inititate the pan delink
        InitiatePanDelinkPojo pandDelinkResponse = initiatePanDelink(uuid2, kycCaseId, "EDHPS2950H");
        Assert.assertEquals(pandDelinkResponse.status, "FAILED", "Failed to get the response status");
  //      Assert.assertEquals(pandDelinkResponse.statusCode, "PAN_DELINK_INITIATED", "Failed to Initiate the delinking");

    } */
    @Description("Invalid cases for Initaite pan delink")
    @Feature("PAN_DELINK")
    @Test(priority = 2, dataProvider = "getPanDelinkTestdata")
    public void panDelinkValidations(String testcase , String uuid, String panNumber) throws Exception {
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(uuid2, VALID_PRODUCT);
        kycCaseId = initiateKYCV9pojo.kycCaseId;
        log.info("kycCaseId is : " +initiateKYCV9pojo.kycCaseId);
        InitiatePanDelinkPojo pandDelinkResponse = initiatePanDelink(uuid, kycCaseId, panNumber);
        switch (testcase){
            case "Already_Delinked_user":
                Assert.assertEquals(pandDelinkResponse.status, "SUCCESS", "Failed to get the response status");
                Assert.assertEquals(pandDelinkResponse.statusCode, "PAN_DELINK_INITIATED", "Failed to Initiate the delinking");
                Assert.assertTrue(dbData.fetchPanDilinkEntryStatus(uuid1,"IN_PROGRESS"),"Failed to initiate the pan delinking");
                break;
            case "Invalid_UUID":
            case "Invalid_PANNUMBER":
                Assert.assertEquals(pandDelinkResponse.status, "FAILED", "Failed to get the response status");
                break;
            case "WithNo_PANNUMBER":
            case "WithNo_UUID":
                Assert.assertEquals(pandDelinkResponse.status, "400", "Failed to get the response status");

        }

    }

    }
