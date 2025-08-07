package billpay;

import api.billpay.BillerDetails;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pojos.billpayment.BillerDetailsPojo;

import java.util.HashMap;
import java.util.Map;

public class BillerDetailsTests extends BillpayTestData{
    static BillerDetailsPojo billerDetailsPojo;
    public static BillerDetails billerDetails;

    static {
        try {
            billerDetailsPojo = new BillerDetailsPojo();
            billerDetails= new BillerDetails();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("Verifying the billerdetails for electricity")
    @Feature("Billpay")
    @Test(priority = 1,dataProvider = "BillerDetails", dataProviderClass = BillpayTestData.class,groups = {"regression", "sanity"})
    public void verifyBillerDeatilsforallCategories(String BillerDetails) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
       queryParamDetails.put("billerId", BillerDetails);
       Response billerDetailsWithResponse  = billerDetails.getWithResponse(queryParamDetails,headerDetails);
       Assert.assertEquals(billerDetailsWithResponse.getStatusCode(),200);

      }


    @Description("Verifying the billerdetails for BBPS_PRODUCT_NOT_ELIGIBLE user")
    @Feature("Billpay")
    @Test(priority = 7,groups = {"regression", "sanity"})
    public void verifyBillerDeatilsforBBPS_PRODUCT_NOT_ELIGIBLE() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        queryParamDetails.put("billerId", BillpayTestData.waterBillerID);
        Response billerDetailsWithResponse  = billerDetails.getWithResponse(queryParamDetails,headerDetails);
        Assert.assertEquals(billerDetailsWithResponse.getStatusCode(),403);
    }
    }
