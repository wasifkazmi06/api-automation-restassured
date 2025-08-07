package billpay;

import api.billpay.Upcomingbills;
import api.billpay.UserBills;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.UpcomingbillPojo;
import pojos.billpayment.UserbillsPojo;

import java.util.HashMap;
import java.util.Map;

public class UserbillsTest extends BillpayTestData{
    public static UserbillsPojo userbillsPojo;
    public static UserBills userBills;

    static {
        try {
            userbillsPojo = new UserbillsPojo();
            userBills = new UserBills();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("Verify the UserBills")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyGetUserbills() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("pageNum", "0");
        queryParamDetails.put("pageSize", "1");
        headerDetails.put("sourceSystem",SourceSystem);
        headerDetails.put("userId", UserId11);
        userbillsPojo = userBills.get(queryParamDetails, headerDetails);
       Assert.assertNotNull(userbillsPojo.elements, "userelements should present");
    }
    @Description("Verify the UserBills with another user ")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyGetUserbills1() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("pageNum", "0");
        queryParamDetails.put("pageSize", "1");
        headerDetails.put("sourceSystem",SourceSystem);
        headerDetails.put("userId", UserId11);
        userbillsPojo = userBills.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(userbillsPojo.elements, "userelements should present");
    }
    @Description("Verify the UserBills with non whitelisted user ")
    @Feature("Billpay")
    @Test(priority = 3,groups = {"regression", "sanity"})
    public void verifyGetUserbillsfornonWhitelistedUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("pageNum", "0");
        queryParamDetails.put("pageSize", "1");
        headerDetails.put("sourceSystem",SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        userbillsPojo = userBills.get(queryParamDetails, headerDetails);
        Assert.assertEquals(userbillsPojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");
    }
}
