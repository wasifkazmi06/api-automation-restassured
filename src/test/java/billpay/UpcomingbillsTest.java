package billpay;

import api.billpay.SearchBiller;
import api.billpay.Upcomingbills;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.SearchBillerPojo;
import pojos.billpayment.UpcomingbillPojo;

import java.util.HashMap;
import java.util.Map;

public class UpcomingbillsTest extends BillpayTestData{
    public static UpcomingbillPojo upcomingbillPojo;
    public static Upcomingbills upcomingbills;

    static {
        try {
            upcomingbillPojo = new UpcomingbillPojo();
            upcomingbills = new Upcomingbills();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("To verify the Upcomingbills")
    @Feature("Upcomingbills")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyUpcomingbills() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("pageNum", "0");
        queryParamDetails.put("pageSize", "1");
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        upcomingbillPojo = upcomingbills.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(upcomingbillPojo.pageNum);
    }
    @Description("To verify the Upcomingbills with another user")
    @Feature("Upcomingbills")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyUpcomingbills1() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("pageNum", "0");
        queryParamDetails.put("pageSize", "1");
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId1);
        upcomingbillPojo = upcomingbills.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(upcomingbillPojo.pageNum);
    }
    @Description("To verify the Upcomingbills with non whitelisted user")
    @Feature("Upcomingbills")
    @Test(priority = 3,groups = {"regression", "sanity"})
    public void verifyUpcomingbillswithNonwhitelsitedUser() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("pageNum", "0");
        queryParamDetails.put("pageSize", "1");
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId",BBPS_PRODUCT_NOT_ELIGIBLE);
        upcomingbillPojo = upcomingbills.get(queryParamDetails, headerDetails);
        Assert.assertEquals(upcomingbillPojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");
    }

}