package billpay;

import api.billpay.BillFetch;
import api.billpay.UserLinkedBillVerified;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.UserLinkedBillVerifiedPojo;
import pojos.billpayment.billFetch.BillFetchPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

public class UserLinkedBillverifiedTest extends BillpayTestData {
    public static UserLinkedBillVerifiedPojo userLinkedBillVerifiedPojo;
    static UserLinkedBillVerified userLinkedBillVerified;

    static {
        try {
            userLinkedBillVerified = new UserLinkedBillVerified();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public UserLinkedBillverifiedTest() {

    }
    @Description("Verifying the UserlinkedBill verification")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public static void verifyUserLinkedBillVerified() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String billRequest = new StringTemplate(Billpayconstants.UserLinkedBillVerified_Request)
                .replace("billerId", BillpayTestData.electricityBillerId).toString();
        JSONObject billRequestJson = new JSONObject(billRequest);
        billRequestJson.getJSONObject("customerParams").put("CA Number", BillpayTestData.CA_Number);
        userLinkedBillVerifiedPojo = userLinkedBillVerified.post(queryParamDetails, headerDetails, billRequestJson.toString());
        Assert.assertEquals(userLinkedBillVerifiedPojo.message,"EXISTING BILL","User linkedbill is verified one");
    }
    @Description("Verifying the non existing UserlinkedBillverifaction")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public static void verifyUserLinkedBillVerified1() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BillpayTestData.UserId11);
        String billRequest = new StringTemplate(Billpayconstants.UserLinkedBillVerified_Request)
                .replace("billerId", BillpayTestData.prepaidJioBillerid).toString();
        JSONObject billRequestJson = new JSONObject(billRequest);
        billRequestJson.getJSONObject("customerParams").put("Mobile Number", BillpayTestData.Mobile_Number);
        userLinkedBillVerifiedPojo = userLinkedBillVerified.post(queryParamDetails, headerDetails, billRequestJson.toString());
        Assert.assertEquals(userLinkedBillVerifiedPojo.errorCode,"BILL_VERIFICATION_REQUIRED","User linkedbill is not verified");
        Assert.assertEquals(userLinkedBillVerifiedPojo.status,403,"Bill verification is required");
    }
}
