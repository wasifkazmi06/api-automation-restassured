package billpay;
import api.billpay.BillFetch;
import api.billpay.Billpay;
import billpay.mobilePrepaid.GenerateRefIdTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeRepayment;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojos.billpayment.billpay.BillpayPojo;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;
import static billpay.BillfetchTests.billFetchPojo;
@Slf4j
public class BillpayTest extends BillpayTestData {
    static BillpayPojo billpayPojo;
    static Billpay billpay;
    static {
        try {
            billpay = new Billpay();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    MakeRepayment makeRepayment = new MakeRepayment("", 0, "");
@BeforeClass
    public void billpayRepayment() throws Exception {

        makeRepayment.verifyRepayDetails(BillpayTestData.mobile);

        if (makeRepayment.repayDetailsPojo.totalOutstanding > 5000) {
            makeRepayment.makeRepaymentMethod(mobile);
        }
    }
    @Description("Verifying Payments of Water")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public static void verifyGetPaymentforWater() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforWater();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        String electricitybillpayRequest = new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.waterBillerID).
                replace("refId", BillfetchTests.billFetchPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", String.valueOf(billFetchPojo.amount)).toString();
        JSONObject waterpayRequestJson = new JSONObject(electricitybillpayRequest);
        waterpayRequestJson.getJSONObject("customerParams").put("Customer_ID", BillpayTestData.Water_Customer_ID);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, waterpayRequestJson.toString());
        Assert.assertNotNull(billpayPojo.secureAppTxnId, "verify the transaction id should be correct");
        Assert.assertNotNull(billpayPojo.status);
        Assert.assertEquals(billpayPojo.status,"SUCCESS","Payment is successful");
    }

    @Description("Verifying Payments of LPG GAS")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public static void verifyGetPaymentforLPG() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforLPG();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId",UserId11);
        String electricitybillpayRequest= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.gasBillerID).
                replace("refId", BillfetchTests.billFetchPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", String.valueOf(billFetchPojo.amount)).toString();
        JSONObject gaspayRequestJson = new JSONObject(electricitybillpayRequest);
        gaspayRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.Mobile_Number);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, gaspayRequestJson.toString());
        Assert.assertNotNull(billpayPojo.secureAppTxnId, "verify the transaction id should be correct");
        Assert.assertNotNull(billpayPojo.status);
        Assert.assertEquals(billpayPojo.status,"SUCCESS","Payment is successful");
    }
    @Description("Verifying Payments of fastag")
    @Feature("Billpay")
    @Test(priority = 3,groups = {"regression", "sanity"})
    public static void verifyGetPaymentforFastag() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforFASTAG();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId",UserId11);
        String electricitybillpayRequest= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.fastagBillerID).
                replace("refId", BillfetchTests.billFetchPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", String.valueOf(billFetchPojo.amount)).toString();
        JSONObject fastagpayRequestJson = new JSONObject(electricitybillpayRequest);
        fastagpayRequestJson.getJSONObject("customerParams").put("Vehicle Number", BillpayTestData.Vehicle_Registration_Number);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, fastagpayRequestJson.toString());
        Assert.assertNotNull(billpayPojo.secureAppTxnId, "verify the transaction id should be correct");
        Assert.assertNotNull(billpayPojo.status);
        Assert.assertEquals(billpayPojo.status,"SUCCESS","Payment is successful");
    }
    @Description("Verifying Payments details for Rajdhani Electricity")
    @Feature("Billpay")
    @Test(priority = 4,groups = {"regression", "sanity"})
    public void verifyGetPaymentforElectricity() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforElectricity();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId",UserId11);
        String electricitybillpayRequest= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.electricityBillerId).
                replace("refId", BillfetchTests.billFetchPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", String.valueOf(billFetchPojo.amount)).toString();
        JSONObject electricitypayRequestJson = new JSONObject(electricitybillpayRequest);
        electricitypayRequestJson.getJSONObject("customerParams").put("CA Number", BillpayTestData.CA_Number);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, electricitypayRequestJson.toString());
        Assert.assertNotNull(billpayPojo.secureAppTxnId, "verify the transaction id should be correct");
        Assert.assertNotNull(billpayPojo.status);
        Assert.assertEquals(billpayPojo.status,"SUCCESS","Payment is successful");
    }
    @Description("Verifying Payments details for Rajdhani Electricity")
    @Feature("Billpay")
    @Test(priority = 5,groups = {"regression", "sanity"})
    public static void verifyGetPaymentforElectricity1() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforElectricity1();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String electricity1billpayRequest= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.billerId1).
                replace("refId", BillfetchTests.billFetchPojo.refId).
                replace("payAgainConsent",BillpayTestData.payAgainConsent).
                replace("paidAmount", String.valueOf(billFetchPojo.amount)).toString();
        JSONObject electricity1payRequestJson = new JSONObject(electricity1billpayRequest);
        electricity1payRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.Mobile_Number);
        electricity1payRequestJson.getJSONObject("customerParams").put("Account Number", BillpayTestData.Account_Number);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, electricity1payRequestJson.toString());
        Assert.assertNotNull(billpayPojo.secureAppTxnId, "verify the transaction id should be correct");
        Assert.assertNotNull(billpayPojo.status);
        Assert.assertEquals(billpayPojo.status,"SUCCESS","Payment is successful");

    }
    @Description("Verifying Payments details for Mobile Jio Prepaid")
    @Feature("Billpay")
    @Test(priority = 6,groups = {"regression", "sanity"})
    public static void verifyJioPrepaidPayment() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
       IntiatePaymentTests.generateJioRefIdTest();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        String jioPrepaidRequest= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.prepaidJioBillerid).
                replace("refId", IntiatePaymentTests.refidPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", BillpayTestData.planamount).toString();
        JSONObject jiopPrepaidpayRequestJson = new JSONObject(jioPrepaidRequest);
        jiopPrepaidpayRequestJson.getJSONObject("customerParams").put("MobileNumber", BillpayTestData.PMobile_Number);
        jiopPrepaidpayRequestJson.getJSONObject("customerParams").put("CircleRefID", BillpayTestData.CircleRefID);
        jiopPrepaidpayRequestJson.getJSONObject("customerParams").put("OperatorCode", BillpayTestData.OperatorCode);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, jiopPrepaidpayRequestJson.toString());
        Assert.assertNotNull(billpayPojo.secureAppTxnId, "verify the transaction id should be correct");
        Assert.assertNotNull(billpayPojo.status);
        Assert.assertEquals(billpayPojo.status,"SUCCESS","Payment is successful");
    }
    @Description("Verifying Payments details for Mobile Airtel Prepaid")
    @Feature("Billpay")
    @Test(priority = 7,groups = {"regression", "sanity"})
    public static void verifyAiretelPrepaidPayment() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        IntiatePaymentTests.generateAirtelRefIdTest();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        String jioPrepaidRequest= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.prepaidAiretelBillerid).
                replace("refId", IntiatePaymentTests.refidPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", BillpayTestData.planamount).toString();
        JSONObject jiopPrepaidpayRequestJson = new JSONObject(jioPrepaidRequest);
        jiopPrepaidpayRequestJson.getJSONObject("customerParams").put("MobileNumber", BillpayTestData.AMobile_Number);
        jiopPrepaidpayRequestJson.getJSONObject("customerParams").put("CircleRefID", BillpayTestData.CircleRefID);
        jiopPrepaidpayRequestJson.getJSONObject("customerParams").put("OperatorCode", BillpayTestData.AOperatorCode);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, jiopPrepaidpayRequestJson.toString());
        Assert.assertNotNull(billpayPojo.secureAppTxnId, "verify the transaction id should be correct");
        Assert.assertNotNull(billpayPojo.status);
        Assert.assertEquals(billpayPojo.status,"SUCCESS","Payment is successful");
    }
    @Description("Verifying Payments details for Mobile Airtel Prepaid")
    @Feature("Billpay")
    @Test(priority = 8,groups = {"regression", "sanity"})
    public static void verifyDTHPrepaidPayment() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        IntiatePaymentTests.generateDTHRefIdTest();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", Anitha_UserId);
        String jioPrepaidRequest= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.dthBillerid).
                replace("refId", IntiatePaymentTests.refidPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", BillpayTestData.planamount).toString();
        JSONObject jiopPrepaidpayRequestJson = new JSONObject(jioPrepaidRequest);
        jiopPrepaidpayRequestJson.getJSONObject("customerParams").put("Subscriber Number", BillpayTestData.AMobile_Number);

        billpayPojo = billpay.post(queryParamDetails, headerDetails, jiopPrepaidpayRequestJson.toString());
        Assert.assertNotNull(billpayPojo.secureAppTxnId, "verify the transaction id should be correct");
        Assert.assertNotNull(billpayPojo.billerName);
        Assert.assertEquals(billpayPojo.status,"SUCCESS","Payment is successful");
    }

    @Description("Verifying Payment details for Permanent deactivate user or blocked")
    @Feature("Billpay")
    @Test(priority = 9,groups = {"regression", "sanity"})
    public static void verifyBillFetchforPermanentDeactivateUser() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforPermanentDeactivateUser();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.permanentDeactivated_UserId);
        String payRequestforJioPostpaid= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.mobilePostpaidBillerId).
                replace("refId", BillfetchTests.billFetchPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", String.valueOf(billFetchPojo.amount)).toString();
        JSONObject jioPostPaidpayRequestJson = new JSONObject(payRequestforJioPostpaid);
        jioPostPaidpayRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.postpaidMobile_Number);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, jioPostPaidpayRequestJson.toString());
        Assert.assertEquals(billpayPojo.errorCode,"USER_NOT_FOUND","User is in permanent deactivated state or blocked");
    }

    @Description("Verifying Payment details for non whitelisted user")
    @Feature("Billpay")
    @Test(priority = 10,groups = {"regression", "sanity"})
    public static void verifyBillFetchforNonWhitelistedUser() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforPermanentDeactivateUser();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.BBPS_PRODUCT_NOT_ELIGIBLE);
        String payRequestforJioPostpaid= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.mobilePostpaidBillerId).
                replace("refId", BillfetchTests.billFetchPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent).
                replace("paidAmount", String.valueOf(billFetchPojo.amount)).toString();
        JSONObject jioPostPaidpayRequestJson = new JSONObject(payRequestforJioPostpaid);
        jioPostPaidpayRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.postpaidMobile_Number);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, jioPostPaidpayRequestJson.toString());
        Assert.assertEquals(billpayPojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");
    }
    @Description("Verifying Payments of fastag")
    @Feature("Billpay")
    @Test(priority = 11,groups = {"regression", "sanity"})
    public void verifyGetPaymentforFastagDuplicateTnx() throws Exception {
        Billpay billpay = new Billpay();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforElectricity();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId",UserId11);
        String electricitybillpayRequest= new StringTemplate(Billpayconstants.Billpay_Request)
                .replace("billerId", BillpayTestData.electricityBillerId).
                replace("refId", BillfetchTests.billFetchPojo.refId).
                replace("payAgainConsent", BillpayTestData.payAgainConsent1).
                replace("paidAmount", String.valueOf(billFetchPojo.amount)).toString();
        JSONObject fastagpayRequestJson = new JSONObject(electricitybillpayRequest);
        fastagpayRequestJson.getJSONObject("customerParams").put("CA Number", BillpayTestData.CA_Number);
        billpayPojo = billpay.post(queryParamDetails, headerDetails, fastagpayRequestJson.toString());
        Assert.assertNotNull(billpayPojo.message);
       Assert.assertEquals(billpayPojo.errorCode, "POTENTIAL_DUPLICATE_TRANSACTION","Multiple txn is happening in 24hrs for same biller");
    }

}

