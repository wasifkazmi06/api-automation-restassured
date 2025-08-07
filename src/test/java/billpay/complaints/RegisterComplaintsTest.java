package billpay.complaints;

import api.billpay.Billpay;
import api.billpay.complaints.RegisterComplaint;
import billpay.BillfetchTests;
import billpay.BillpayTestData;
import billpay.Billpayconstants;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.billpay.BillpayPojo;
import pojos.billpayment.complaints.RegisterComplaintsPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

public class RegisterComplaintsTest extends BillpayTestData {
    static RegisterComplaintsPojo registerComplaintsPojo;
    static RegisterComplaint registerComplaint;
    static {
        try {
            registerComplaint = new RegisterComplaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("Verifying Registercomplaints for billpayments")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public static void verifyRegisterComplaint() throws Exception {
        RegisterComplaint registerComplaint = new RegisterComplaint();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("app", App);
        headerDetails.put("imei", Imei);
        headerDetails.put("initiatingChannel", InitiatingChannel);
        headerDetails.put("ip", Ip);
        headerDetails.put("os", Os);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", TestUser1);
        String registercomplainrequest = new StringTemplate(Billpayconstants.RegusterComplaints_Request).toString();
        JSONObject complaintRequestJson = new JSONObject(registercomplainrequest);

        registerComplaintsPojo = registerComplaint.post(queryParamDetails, headerDetails,complaintRequestJson.toString());


    }

}
