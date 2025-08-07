package nach.presentment;

import api.platform.GetUserData;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import nach.NACHPresentmentData;
import org.testng.Assert;
import org.testng.annotations.*;
import pojos.platform.getUserData.UserData;
import org.testng.asserts.SoftAssert;
import util.DateTimeConverter;
import static nach.presentment.GetCollectionDetailsStep.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class NACHpresentmentTestBNPL {

    public String um_uuid;
    static public GetUserData getUserData;
    static public UserData userData;
    public static String amount = NACHPresentmentData.amount;

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 1,groups = { "NACHPresentment", "sanity", "E2E"})
    public void BNPLCollectionInitiate() throws Exception {

        Map<String, Object> queryParamDetailsGetUserPlatform = new HashMap<>();
        HashMap<String, String> headerDetailsGetUserPlatform = new HashMap<>();

        queryParamDetailsGetUserPlatform.put("mobile", NACHPresentmentData.razorpayNBUser_BNPL_SUCCESS);

        userData = getUserData.get(queryParamDetailsGetUserPlatform, headerDetailsGetUserPlatform);
        um_uuid = userData.getUmUuid();
        InitiateCollectionRequestStep.initiateCollection(NACHPresentmentData.amount, NACHPresentmentData.bnplProduct, um_uuid,"");

        getCollectionDetail(um_uuid);
        getRazorpayNBNachCollection(collectionDetails.get("methodVendorId"));
        getRazorpayOrder(razorpayNBNachSetupDetails.get("orderId"));

    }


    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 2,groups = { "NACHPresentment", "sanity", "E2E"})
    public void verifyCollectionCreated() throws Exception {
        Allure.addAttachment("Actual merchantTxnId : " + collectionDetails.get("merchantTxnId") , " Expected merchantTxnId : " + InitiateCollectionRequestStep.MTX);
        Allure.addAttachment("Actual state : " + collectionDetails.get("state") , " Expected state : " + "SENT_TO_BANK");
        Allure.addAttachment("Actual status : " + collectionDetails.get("status") , " Expected status : " + "IN_PROGRESS");

        Assert.assertTrue(collectionDetails.get("merchantTxnId").equals(InitiateCollectionRequestStep.MTX), "Check that new collection is created successfully");
        Assert.assertTrue(collectionDetails.get("state").equals("SENT_TO_BANK") ||
                collectionDetails.get("state").equals("CREATED"), "Check that new collection is created successfully");
        Assert.assertTrue(collectionDetails.get("status").equals("IN_PROGRESS") , "Check that new collection is created successfully");

            }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 3,groups = { "NACHPresentment", "sanity", "E2E"})
    public void verifyRazorpayNBNachCollectionCreated() throws Exception {
        Allure.addAttachment("Actual razorpayPaymentStatus : " + razorpayNBNachSetupDetails.get("razorpayPaymentStatus") , " Expected razorpayPaymentStatus : " + "created");
        Thread.sleep(300);
        Assert.assertTrue(razorpayNBNachSetupDetails.get("razorpayPaymentStatus").equals("created"), "Check that payment is successful");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 4,groups = { "NACHPresentment", "sanity", "E2E"})
    public void verifyRazorpayOrderCreated() throws Exception {
        Allure.addAttachment("Actual amountRazorpayOrderId : " + razorpayOrderDetails.get("amountRazorpayOrderId") , " Expected amountRazorpayOrderId : " + amount);
        Allure.addAttachment("Actual sourceRazorpayOrderId : " + razorpayOrderDetails.get("sourceRazorpayOrderId") , " Expected sourceRazorpayOrderId : " + "COLLECTION");
        Allure.addAttachment("Actual statusRazorpayOrderId : " + razorpayOrderDetails.get("statusRazorpayOrderId") , " Expected statusRazorpayOrderId : " + "created");

        Assert.assertTrue(Double.valueOf(razorpayOrderDetails.get("amountRazorpayOrderId")).equals(Double.valueOf(amount)), "Check that payment is successful");
        Assert.assertTrue(razorpayOrderDetails.get("sourceRazorpayOrderId").equals("COLLECTION"), "Check that payment is successful");
        Assert.assertTrue(razorpayOrderDetails.get("statusRazorpayOrderId").equals("created"), "Check that payment is successful");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 5,groups = { "NACHPresentment", "sanity", "E2E"})
    public void BNPLCollectionSuccessWebhook() throws Exception {
        Response response;
        response = InitiateRazorpayWebhookStep.initiateRazorpayWebhook(
                NACHPresentmentData.eventRazorpaySuccess,
                razorpayNBNachSetupDetails.get("razorpayPaymentId"),
                amount,
                razorpayOrderDetails.get("razorpayOrderId"),
                "","","","","",
                NACHPresentmentData.status_captured
        );

        Assert.assertTrue(response.getBody().asString().equals("Success") , "Check that new collection is created successfully");
    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 6,groups = { "NACHPresentment", "sanity", "E2E"})
    public void verifySendCollectionsToLpCron() throws Exception {
        int count = 0;
        while(!collectionDetails.get("state").equals("RECEIVED_FROM_BANK") && count <5) {
            Thread.sleep(1000);
            getCollectionDetail(um_uuid);
            count++;
        }
        Allure.addAttachment("Actual state : " + collectionDetails.get("state") , " Expected state : " + "RECEIVED_FROM_BANK");
        Assert.assertTrue(collectionDetails.get("state").equals("RECEIVED_FROM_BANK"), "Check that webhook processed successfully");

        Response response = SendCollectionsToLpCron.initiateSendCollectionsToLpCron();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200, "Check that new collection is created successfully");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 7,groups = { "NACHPresentment", "sanity", "E2E"})
    public void verifyCollectionSuccess() throws Exception {
        Thread.sleep(3000);
        getCollectionDetail(um_uuid);
        Allure.addAttachment("Actual amount : " + collectionDetails.get("amount") , " Expected amount : " + amount);
        Allure.addAttachment("Actual state : " + collectionDetails.get("state") , " Expected state : " + "SENT_TO_LP");
        Allure.addAttachment("Actual status : " + collectionDetails.get("status") , " Expected status : " + "SUCCESS");

        Assert.assertTrue(Double.valueOf(collectionDetails.get("amount")).equals(Double.valueOf(amount)), "Check that payment is successful");
        Assert.assertTrue(collectionDetails.get("state").equals("SENT_TO_LP"), "Check that sent to LP cron ran successfully");
        Assert.assertTrue(collectionDetails.get("status").equals("SUCCESS") , "Check that sent to LP cron ran successfully");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 8,groups = { "NACHPresentment", "sanity", "E2E"})
    public void verifyRazorpayNBNachCollectionSuccess() throws Exception {

        getRazorpayNBNachCollection(collectionDetails.get("methodVendorId"));
        Allure.addAttachment("Actual razorpayPaymentStatus : " + razorpayNBNachSetupDetails.get("razorpayPaymentStatus") , " Expected razorpayPaymentStatus : " + "captured");
        Allure.addAttachment("Actual repayDate : " + razorpayNBNachSetupDetails.get("repayDate") , " Expected repayDate : " + "Not null");

        Assert.assertTrue(razorpayNBNachSetupDetails.get("razorpayPaymentStatus").equals("captured"), "Check that payment is successful");
            Assert.assertNotNull(razorpayNBNachSetupDetails.get("repayDate"), "Check that payment is successful");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 9,groups = { "NACHPresentment", "sanity", "E2E"})
    public void verifyRazorpayOrderSuccess() throws Exception {

        getRazorpayOrder(razorpayNBNachSetupDetails.get("orderId"));
        Allure.addAttachment("Actual amountRazorpayOrderId : " + razorpayOrderDetails.get("amountRazorpayOrderId") , " Expected amountRazorpayOrderId : " + amount);
        Allure.addAttachment("Actual statusRazorpayOrderId : " + razorpayOrderDetails.get("statusRazorpayOrderId") , " Expected statusRazorpayOrderId : " + "paid");

        Assert.assertTrue(Double.valueOf(razorpayOrderDetails.get("amountRazorpayOrderId")).equals(Double.valueOf(amount)), "Check that payment is successful");
        Assert.assertTrue(razorpayOrderDetails.get("statusRazorpayOrderId").equals("paid"), "Check that payment is successful");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 10,groups = { "NACHPresentment", "sanity","failed"})
    public void BNPLCollectionFailedInitiate() throws Exception {

        Map<String, Object> queryParamDetailsGetUserPlatform = new HashMap<>();
        HashMap<String, String> headerDetailsGetUserPlatform = new HashMap<>();

        queryParamDetailsGetUserPlatform.put("mobile", NACHPresentmentData.razorpayNBUser_BNPL_SUCCESS);

        userData = getUserData.get(queryParamDetailsGetUserPlatform, headerDetailsGetUserPlatform);
        um_uuid = userData.getUmUuid();

        InitiateCollectionRequestStep.initiateCollection(NACHPresentmentData.amount, NACHPresentmentData.bnplProduct,um_uuid,"");

        getCollectionDetail(um_uuid);
        getRazorpayNBNachCollection(collectionDetails.get("methodVendorId"));
        getRazorpayOrder(razorpayNBNachSetupDetails.get("orderId"));

    }


    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 11,groups = { "NACHPresentment", "sanity","failed"})
    public void BNPLCollectionFailedWebhook() throws Exception {
        Response response;
        response = InitiateRazorpayWebhookStep.initiateRazorpayWebhook(
                NACHPresentmentData.eventRazorpayFailure,
                razorpayNBNachSetupDetails.get("razorpayPaymentId"),
                amount,
                razorpayOrderDetails.get("razorpayOrderId"),
                NACHPresentmentData.error_code, NACHPresentmentData.error_description,
                NACHPresentmentData.error_reason, NACHPresentmentData.error_source,
                NACHPresentmentData.error_step, NACHPresentmentData.status_failed
        );

        Assert.assertTrue(response.getBody().asString().equals("Success") , "Check that new collection is created successfully");
        verifySendCollectionsToLpCron();
    }


    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 12,groups = { "NACHPresentment", "sanity","failed"})
    public void verifyCollectionFailed() throws Exception {
        Thread.sleep(3000);
        getCollectionDetail(um_uuid);
        Allure.addAttachment("Actual amount : " + collectionDetails.get("amount") , " Expected amount : " + amount);
        Allure.addAttachment("Actual state : " + collectionDetails.get("state") , " Expected state : " + "SENT_TO_LP");
        Allure.addAttachment("Actual status : " + collectionDetails.get("status") , " Expected status : " + "FAILED");

        Assert.assertTrue(Double.valueOf(collectionDetails.get("amount")).equals(Double.valueOf(amount)), "Check that payment is successful");
        Assert.assertTrue(collectionDetails.get("state").equals("SENT_TO_LP") , "Check that sent to LP cron ran successfully");
        Assert.assertTrue(collectionDetails.get("status").equals("FAILED") , "Check that payment is failed");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 13,groups = { "NACHPresentment", "sanity","failed"})
    public void verifyRazorpayNBNachCollectionFailed() throws Exception {

        getRazorpayNBNachCollection(collectionDetails.get("methodVendorId"));
        Allure.addAttachment("Actual razorpayPaymentStatus : " + razorpayNBNachSetupDetails.get("razorpayPaymentStatus") , " Expected razorpayPaymentStatus : " + "failed");
        Allure.addAttachment("Actual repayDate : " + razorpayNBNachSetupDetails.get("repayDate") , " Expected repayDate : " + "Not null");
        Allure.addAttachment("Actual razorpayRejectCode : " + razorpayNBNachSetupDetails.get("razorpayRejectCode") , " Expected razorpayRejectCode : " + "BAD_REQUEST_ERROR");
        Allure.addAttachment("Actual razorpayRejectMessage : " + razorpayNBNachSetupDetails.get("razorpayRejectMessage") , " Expected razorpayRejectMessage : " + "Not null");

        Assert.assertTrue(razorpayNBNachSetupDetails.get("razorpayPaymentStatus").equals("failed"), "Check that payment is successful");
        Assert.assertNotNull(razorpayNBNachSetupDetails.get("repayDate"), "Check that payment is failed");
        Assert.assertTrue(razorpayNBNachSetupDetails.get("razorpayRejectCode").equals("BAD_REQUEST_ERROR"), "Check that payment is failed");
        Assert.assertNotNull(razorpayNBNachSetupDetails.get("razorpayRejectMessage"), "Check that payment is failed");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @Test(priority = 14,groups = { "NACHPresentment", "sanity","failed"})
    public void verifyRazorpayOrderFailed() throws Exception {

        getRazorpayOrder(razorpayNBNachSetupDetails.get("orderId"));
        Allure.addAttachment("Actual amountRazorpayOrderId : " + razorpayOrderDetails.get("amountRazorpayOrderId") , " Expected amountRazorpayOrderId : " + amount);
        Allure.addAttachment("Actual statusRazorpayOrderId : " + razorpayOrderDetails.get("statusRazorpayOrderId") , " Expected statusRazorpayOrderId : " + "created");

        Assert.assertTrue(Double.valueOf(razorpayOrderDetails.get("amountRazorpayOrderId")).equals(Double.valueOf(amount)), "Check that payment is successful");
        Assert.assertTrue(razorpayOrderDetails.get("statusRazorpayOrderId").equals("created"), "Check that payment is successful");

    }

    @Description("NACH presentment for BNPL product")
    @Feature("BNPLRazorPayPresentment")
    @AfterClass(alwaysRun=true)
    public void resetCoolOffAndMonthlyLimit() throws Exception {
         LocalDate currentDate = DateTimeConverter.getCurrentDate();
         String dateCreated = currentDate.minusDays(5).toString().concat(" 12:35:37");

         NACHPresentmentData.updateRazorpayNBnachCollection(dateCreated, dateCreated, collectionDetails.get("methodVendorId"));
         NACHPresentmentData.deleteCollectionFailureStats(collectionDetails.get("collectionSetupId"));
         GetCollectionDetailsStep.razorpayPaymentId = null;
         GetCollectionDetailsStep.orderId = null;
         collectionDetails.clear();
         razorpayNBNachSetupDetails.clear();
         razorpayOrderDetails.clear();
     }
}
