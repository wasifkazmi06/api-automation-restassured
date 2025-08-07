package billpay;

import api.billpay.UserBills;
import api.billpay.UserLinkedBillId;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.UserbillsPojo;
import pojos.billpayment.fetchBillAndLastBillDetails.UserLinkedBillIdPojo;

import java.util.HashMap;
import java.util.Map;

public class UserLinkedBillidTest extends BillpayTestData{
    public static UserLinkedBillIdPojo userLinkedBillIdPojo;
    public static UserLinkedBillId userLinkedBillId;

    static {
        try {
            userLinkedBillIdPojo = new UserLinkedBillIdPojo();
            userLinkedBillId = new UserLinkedBillId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("To verify the fetchBillAndLastBillDetailsForUserLinkedBillId for DTH")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyGetfetchBillAndLastBillDetailsForUserLinkedBillIdDth() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("userLinkedBillId", BillpayTestData.dthBillid);
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        userLinkedBillIdPojo = userLinkedBillId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(userLinkedBillIdPojo.billerDetails,"Verify the billerdetails should be there");
        Assert.assertNull(userLinkedBillIdPojo.fetchBillResponse);
    }
    @Description("To verify the fetchBillAndLastBillDetailsForUserLinkedBillId for Electricity")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyGetfetchBillAndLastBillDetailsForUserLinkedBillIdElectricity() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("userLinkedBillId", BillpayTestData.electricityBillid);
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        userLinkedBillIdPojo = userLinkedBillId.get(queryParamDetails, headerDetails);
        if(userLinkedBillIdPojo.billerDetails.status.equalsIgnoreCase("200")) {
            Assert.assertNotNull(userLinkedBillIdPojo.billerDetails, "Verify the billerdetails should be there");
            Assert.assertNotNull(userLinkedBillIdPojo.fetchBillResponse);
        } else if (userLinkedBillIdPojo.billerDetails.status.equalsIgnoreCase("400")) {
            Assert.assertEquals(userLinkedBillIdPojo.message,"User Linked Bill Id 4150337 is already deleted","User Linked Bill Id 4150337 is already deleted");
        }
    }
    @Description("To verify the fetchBillAndLastBillDetailsForUserLinkedBillId for MobilePrepaid")
    @Feature("Billpay")
    @Test(priority = 3,groups = {"regression", "sanity"})
    public void verifyGetfetchBillAndLastBillDetailsForUserLinkedBillIdMobilePrepaid() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("userLinkedBillId", BillpayTestData.mPrepaidBillid);
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        userLinkedBillIdPojo = userLinkedBillId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(userLinkedBillIdPojo.billerDetails,"Verify the billerdetails should be there");
        Assert.assertNull(userLinkedBillIdPojo.fetchBillResponse);
        Assert.assertNull(userLinkedBillIdPojo.billerDetails.primaryCustomerParam,"primaryValue");
    }
    @Description("To verify the fetchBillAndLastBillDetailsForUserLinkedBillId for MobilePostpaid")
    @Feature("Billpay")
    @Test(priority = 4,groups = {"regression", "sanity"})
    public void verifyGetfetchBillAndLastBillDetailsForUserLinkedBillIdMobilePostpaid() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("userLinkedBillId", BillpayTestData.mPostpaidBillid);
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        userLinkedBillIdPojo = userLinkedBillId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(userLinkedBillIdPojo.billerDetails,"Verify the billerdetails should be there");
        Assert.assertNotNull(userLinkedBillIdPojo.fetchBillResponse);
        Assert.assertNull(userLinkedBillIdPojo.billerDetails.primaryCustomerParam,"primaryValue");
    }
}

