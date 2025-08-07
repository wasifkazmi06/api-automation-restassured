package billpay;

import api.billpay.Billers;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.BillersPojo;
import java.util.HashMap;
import java.util.Map;

public class GetBillerTest extends BillpayTestData {

    public static BillersPojo billersPojo;
    public static Billers billers;

    static {
        try {
            billersPojo = new BillersPojo();
            billers = new Billers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("To verify that billers for ELECTRICITY category")
    @Feature("GetBillers")
    @Test(priority = 1,dataProvider = "Categories", dataProviderClass = BillpayTestData.class,groups = {"regression", "sanity"})
    public void verifyGetBillersForAllCategories(String Categories) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("categoryName",Categories);
        //queryParamDetails.put("pageNum", "1");
        //queryParamDetails.put("pageSize", "10");
        headerDetails.put("sourceSystem",SourceSystem);
        headerDetails.put("userId", UserId11);
        billersPojo = billers.get(queryParamDetails, headerDetails);
        //Assert.assertEquals(billersPojo.billers.pageNum, "1", "You are on the wrong page");
        Assert.assertNotNull(billersPojo.billers,"verify that billerIds should be Present");
    }

    @Description("To verify that billers for Mobile Postpaid category for permanent Deactivated user")
    @Feature("GetBillers")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyGetBillersForMobilePostpaidwithPermanentDeactivatedUser() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("categoryName",mobile_Postpaid);
        queryParamDetails.put("pageNum", "1");
        queryParamDetails.put("pageSize", "10");
        headerDetails.put("sourceSystem",SourceSystem);
        headerDetails.put("userId", permanentDeactivated_UserId);
        billersPojo = billers.get(queryParamDetails, headerDetails);
        Assert.assertEquals(billersPojo.errorCode,"USER_NOT_FOUND","User is permanantDeactivated");


    }
    @Description("To verify that billers for Mobile Postpaid category for non whitelisted user")
    @Feature("GetBillers")
    @Test(priority = 3,groups = {"regression", "sanity"})
    public void verifyGetBillersForMobilePostpaidwithNonWhitelistedUser() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("categoryName",mobile_Postpaid);
        queryParamDetails.put("pageNum", "1");
        queryParamDetails.put("pageSize", "10");
        headerDetails.put("sourceSystem",SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        billersPojo = billers.get(queryParamDetails, headerDetails);
        Assert.assertEquals(billersPojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");

    }
}
