package billpay.mobilePrepaid;

import api.billpay.mobilePrepaid.GenerateRefId;
import billpay.BillpayTestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.mobilePrepaid.RefidPojo;

import java.util.HashMap;
import java.util.Map;

public class GenerateRefIdTest extends BillpayTestData {

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
    @Description("Generate refId for jio prepaid payments")
    @Feature("Billpay")
    @Test(priority = 0,dataProvider = "PrepaidBillerIds", dataProviderClass = BillpayTestData.class,groups = {"regression", "sanity"})

    public static void generateRefIdTestforMobilePrepaidBillers(String prepaidBillerIds) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("billerId",prepaidBillerIds);
        refidPojo=generateRefId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(refidPojo.refId,"Refid should be generate for jio prepaid biller id");
    }

    @Description("Generate refId for DTH prepaid payments")
    @Feature("Billpay")
    @Test(priority = 4,groups = {"regression", "sanity"})

    public static void generateDTHRefIdTest() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("billerId", BillpayTestData.dthBillerid);
        refidPojo=generateRefId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(refidPojo.refId,"Refid should be generated for DTH billerid");
    }

    @Description("Verifying the generate refId api with BBPS_PRODUCT_NOT_ELIGIBLE")
    @Feature("Billpay")
    @Test(priority = 3,groups = {"regression", "sanity"})

    public static void BBPS_PRODUCT_NOT_ELIGIBLE() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        queryParamDetails.put("billerId", BillpayTestData.prepaidJioBillerid);
        refidPojo=generateRefId.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(refidPojo.errorCode);
        Assert.assertEquals(refidPojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE");

    }



}
