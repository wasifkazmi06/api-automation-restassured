package billpay;

import api.billpay.mobilePrepaid.GenerateRefId;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.mobilePrepaid.RefidPojo;

import java.util.HashMap;
import java.util.Map;

public class IntiatePaymentTests extends BillpayTestData {

    public static RefidPojo refidPojo;
    public static GenerateRefId generateRefId;

    static {
        try {
            refidPojo = new RefidPojo();
            generateRefId= new GenerateRefId();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("Generate refId for Airtel prepaid payments")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})

    public static void generateAirtelRefIdTest() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("billerId", BillpayTestData.prepaidAiretelBillerid);
        refidPojo=generateRefId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(refidPojo.refId,"Refid should generate");
    }
    @Description("Generate refId for Airtel prepaid payments")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})

    public static void generateJioRefIdTest() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("billerId", BillpayTestData.prepaidJioBillerid);
        refidPojo=generateRefId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(refidPojo.refId,"Refid should generate");
    }

    @Description("Generate refId for DTH prepaid payments")
    @Feature("Billpay")
    @Test(priority = 3,groups = {"regression", "sanity"})

    public static void generateDTHRefIdTest() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("billerId", BillpayTestData.dthBillerid);
        refidPojo=generateRefId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(refidPojo.refId,"Refid should generate");
    }

}