package billpay;

import api.billpay.BillPaymentStatus;
import api.billpay.mobilePrepaid.GenerateRefId;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.PaymentStatusPojo;

import java.util.HashMap;
import java.util.Map;

public class BillPaymentStatusTest extends BillpayTestData {
    static PaymentStatusPojo paymentStatusPojo;
    static BillPaymentStatus billPaymentStatus;
    static {
        try {
            billPaymentStatus = new BillPaymentStatus();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("Verifying Payments status for Rajdhani Electricity")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyPaymentStatusElectricity1() throws Exception {
        String refId;
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforElectricity1();
        BillpayTest.verifyGetPaymentforElectricity1();
        refId=BillpayTest.billpayPojo.refId;
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("refId",refId);
        paymentStatusPojo = billPaymentStatus.get(queryParamDetails,headerDetails);
        Assert.assertEquals(paymentStatusPojo.status,"SUCCESS","Paymnet is successfull");
        Assert.assertNotNull(paymentStatusPojo.secureAppTxnId);
    }
    @Description("Verifying Payments status for Water")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyPaymentStatusWater() throws Exception {
        String refId;
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforWater();
        BillpayTest.verifyGetPaymentforWater();
        refId=BillpayTest.billpayPojo.refId;
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("refId",refId);
        paymentStatusPojo = billPaymentStatus.get(queryParamDetails,headerDetails);
        Assert.assertEquals(paymentStatusPojo.status,"SUCCESS","Paymnet is successfull");
        Assert.assertNotNull(paymentStatusPojo.secureAppTxnId);
    }
    @Description("Verifying Payments status for Fastag")
    @Feature("Billpay")
    @Test(priority = 3,groups = {"regression", "sanity"})
    public void verifyPaymentStatusFastag() throws Exception {
        String refId;
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillfetchTests.verifyBillFetchforFASTAG();
        BillpayTest.verifyGetPaymentforFastag();
        refId=BillpayTest.billpayPojo.refId;
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("refId",refId);
        paymentStatusPojo = billPaymentStatus.get(queryParamDetails,headerDetails);
        Assert.assertEquals(paymentStatusPojo.status,"SUCCESS","Paymnet is successfull");
        Assert.assertNotNull(paymentStatusPojo.secureAppTxnId);
    }

    @Description("Verifying Payments Status for Mobile Prepaid")
    @Feature("Billpay")
    @Test(priority = 4,groups = {"regression", "sanity"})
    public void verifyJioPrepaidPaymentStatus() throws Exception {
        String refId;
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillpayTest.verifyJioPrepaidPayment();
        refId=BillpayTest.billpayPojo.refId;
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("refId",refId);
        paymentStatusPojo = billPaymentStatus.get(queryParamDetails,headerDetails);
        Assert.assertNotNull(paymentStatusPojo.status);
        Assert.assertEquals(paymentStatusPojo.billerId,"JioPREPAID","Payment is successful for jio");
    }
    @Description("Verifying Payments Status for DTH")
    @Feature("Billpay")
    @Test(priority = 5,groups = {"regression", "sanity"})
    public void verifyDthPaymentStatus() throws Exception {
        String refId;
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        BillpayTest.verifyDTHPrepaidPayment();
        refId=BillpayTest.billpayPojo.refId;
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("refId",refId);
        paymentStatusPojo = billPaymentStatus.get(queryParamDetails,headerDetails);
        Assert.assertNotNull(paymentStatusPojo.status);
        Assert.assertEquals(paymentStatusPojo.billerId,  "TATASKY00NAT01", "payment is scucessfull for DTH billerid");
    }

}
