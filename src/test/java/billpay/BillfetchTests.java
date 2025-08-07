package billpay;

import api.billpay.BillFetch;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.LazypayConstants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pojos.billpayment.BillersPojo;
import pojos.billpayment.billFetch.BillFetchPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class BillfetchTests extends BillpayTestData {

    public static String refId;
    public static String subString= "lpbbps";
    public static BillFetchPojo billFetchPojo;
    static BillFetch billFetch;


    static {
        try {
            billFetch = new BillFetch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BillfetchTests() {

    }
    @Description("Verifying fetch details for Water")
    @Feature("Billpay")
    @Test(priority = 1, groups = {"regression", "sanity"})
    public static void verifyBillFetchforWater() throws Exception {
        BillFetch billfetch = new BillFetch();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String fetchRequest = new StringTemplate(Billpayconstants.FETCH_REQUEST)
                .replace("billerId", BillpayTestData.waterBillerID).toString();
        JSONObject fetchRequestJson = new JSONObject(fetchRequest);
        fetchRequestJson.getJSONObject("customerParams").put("Customer_ID", BillpayTestData.Water_Customer_ID);
        billFetchPojo = billfetch.post(queryParamDetails, headerDetails, fetchRequestJson.toString());
        log.info("Logging refId ::" + billFetchPojo.refId);
        Assert.assertEquals(billFetchPojo.billerDetails.billerId, "MCC000000KAR01","Biller is Active");
    }

    @Description("Verifying fetch details for ICICI fastag")
    @Feature("Billpay")
    @Test(priority = 2, groups = {"regression", "sanity"})
    public static void verifyBillFetchforFASTAG() throws Exception {
        BillFetch billfetch = new BillFetch();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String fetchRequest = new StringTemplate(Billpayconstants.FETCH_REQUEST)
                .replace("billerId", BillpayTestData.fastagBillerID).toString();
        JSONObject fetchRequestJson = new JSONObject(fetchRequest);
        fetchRequestJson.getJSONObject("customerParams").put("Vehicle Number", BillpayTestData.Vehicle_Registration_Number);
        billFetchPojo = billfetch.post(queryParamDetails, headerDetails, fetchRequestJson.toString());
        log.info("Logging refId ::" + billFetchPojo.refId);
       Assert.assertEquals(billFetchPojo.billerDetails.billerId, "TOLL00000NAT72","Biller is Active");
    }
    @Description("Verifying fetch details for LPG GAS")
    @Feature("Billpay")
    @Test(priority = 3, groups = {"regression", "sanity"})
    public static void verifyBillFetchforLPG() throws Exception {
        BillFetch billfetch = new BillFetch();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String fetchRequest = new StringTemplate(Billpayconstants.FETCH_REQUEST)
                .replace("billerId", BillpayTestData.gasBillerID).toString();
        JSONObject fetchRequestJson = new JSONObject(fetchRequest);
        fetchRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.Mobile_Number);
        billFetchPojo = billfetch.post(queryParamDetails, headerDetails, fetchRequestJson.toString());
        log.info("Logging refId ::" + billFetchPojo.refId);
        Assert.assertEquals(billFetchPojo.billerDetails.billerId, "HPCL00000NAT01","Biller is Active");
    }

    @Description("Verifying fetch details for Rajdhani Electricity")
    @Feature("Billpay")
    @Test(priority = 4, groups = {"regression", "sanity"})
    public static void verifyBillFetchforElectricity() throws Exception {
        BillFetch billfetch = new BillFetch();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String fetchRequestforelectricity = new StringTemplate(Billpayconstants.FETCH_REQUEST)
                .replace("billerId", BillpayTestData.electricityBillerId).toString();
        JSONObject electricityfetchRequestJson = new JSONObject(fetchRequestforelectricity);
        electricityfetchRequestJson.getJSONObject("customerParams").put("CA Number", BillpayTestData.CA_Number);
        billFetchPojo = billfetch.post(queryParamDetails, headerDetails, electricityfetchRequestJson.toString());
        log.info("Logging refId ::" + billFetchPojo.refId);
        refId = billFetchPojo.refId;
        Assert.assertEquals(billFetchPojo.billerDetails.billerId, "BSESRAJPLDEL01", "fetching Electricity details");
        Assert.assertNotNull(billFetchPojo.refId, "Verify the refId and should satrt with lpbbps");
        Assert.assertNotNull(billFetchPojo.primaryCustomerParam);
        Assert.assertTrue(refId.contains(subString));
    }

    @Description("Verifying fetch details for Rajdhani Electricity")
    @Feature("Billpay")
    @Test(priority = 5, groups = {"regression", "sanity"})
    public static void verifyBillFetchforElectricity1() throws Exception {
        BillFetch billfetch = new BillFetch();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String fetchRequestforelectricity1 = new StringTemplate(Billpayconstants.FETCH_REQUEST)
                .replace("billerId", BillpayTestData.billerId1).toString();
        JSONObject electricity1fetchRequestJson = new JSONObject(fetchRequestforelectricity1);
        electricity1fetchRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.Mobile_Number);
        electricity1fetchRequestJson.getJSONObject("customerParams").put("Account Number", BillpayTestData.Account_Number);
        billFetchPojo = billfetch.post(queryParamDetails, headerDetails, electricity1fetchRequestJson.toString());
        //log.info("Logging refId ::" + billFetchPojo.refId);
        Reporter.log("\n Fetch Response:" + billFetchPojo.refId, true);
        refId = billFetchPojo.refId;
        Assert.assertEquals(billFetchPojo.billerDetails.billerId, "DHBVN0000HAR01", "fetching Electricity details");
        Assert.assertNotNull(billFetchPojo.refId, "Verify the refId and should satrt with lpbbps");
        Assert.assertNotNull(billFetchPojo.primaryCustomerParam);
    }

    @Description("Verifying fetch details for Jio Postpaid")
    @Feature("Billpay")
    @Test(priority = 6, groups = {"regression", "sanity"})
    public static void verifyBillFetchforJioPostPaid() throws Exception {
        BillFetch billfetch = new BillFetch();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String fetchRequestforJioPostpaid = new StringTemplate(Billpayconstants.FETCH_REQUEST)
                .replace("billerId", BillpayTestData.mobilePostpaidBillerId).toString();
        JSONObject jioPostPaidfetchRequestJson = new JSONObject(fetchRequestforJioPostpaid);
        jioPostPaidfetchRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.postpaidMobile_Number);
        billFetchPojo = billfetch.post(queryParamDetails, headerDetails, jioPostPaidfetchRequestJson.toString());
        Reporter.log("\n Fetch Response:" + billFetchPojo.refId, true);
        refId = billFetchPojo.refId;
        Assert.assertEquals(billFetchPojo.billerDetails.billerId, "JIO000000NAT02", "fetching Electricity details");
        Assert.assertNotNull(billFetchPojo.refId, "Verify the refId and should satrt with lpbbps");
        Assert.assertNotNull(billFetchPojo.primaryCustomerParam);
        Assert.assertTrue(refId.contains(subString));
    }
    @Description("Verifying fetch details for Permanent deactivate user")
    @Feature("Billpay")
    @Test(priority = 7, groups = {"regression", "sanity"})
    public static void verifyBillFetchforPermanentDeactivateUser() throws Exception {
        BillFetch billfetch = new BillFetch();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.permanentDeactivated_UserId);
        String fetchRequestforJioPostpaid = new StringTemplate(Billpayconstants.FETCH_REQUEST)
                .replace("billerId", BillpayTestData.mobilePostpaidBillerId).toString();
        JSONObject jioPostPaidfetchRequestJson = new JSONObject(fetchRequestforJioPostpaid);
        jioPostPaidfetchRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.postpaidMobile_Number);
        billFetchPojo = billfetch.post(queryParamDetails, headerDetails, jioPostPaidfetchRequestJson.toString());
        Assert.assertEquals(billFetchPojo.errorCode,"USER_NOT_FOUND", "User is permanentDeactivated on lazypay");

    }


    @Description("Verifying fetch details for non whitelisted user")
    @Feature("Billpay")
    @Test(priority = 8, groups = {"regression", "sanity"})
    public static void verifyBillFetchforNonWhitelistedUser() throws Exception {
        BillFetch billfetch = new BillFetch();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.BBPS_PRODUCT_NOT_ELIGIBLE);
        String fetchRequestforJioPostpaid = new StringTemplate(Billpayconstants.FETCH_REQUEST)
                .replace("billerId", BillpayTestData.mobilePostpaidBillerId).toString();
        JSONObject jioPostPaidfetchRequestJson = new JSONObject(fetchRequestforJioPostpaid);
        jioPostPaidfetchRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.postpaidMobile_Number);
        billFetchPojo = billfetch.post(queryParamDetails, headerDetails, jioPostPaidfetchRequestJson.toString());
        Assert.assertEquals(billFetchPojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");

    }
}