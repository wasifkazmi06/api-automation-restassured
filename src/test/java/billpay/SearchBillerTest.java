package billpay;

import api.billpay.Billers;
import api.billpay.SearchBiller;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.BillersPojo;
import pojos.billpayment.SearchBillerPojo;

import java.util.HashMap;
import java.util.Map;

public class SearchBillerTest extends BillpayTestData {
    public static SearchBillerPojo searchBillerPojo;
    public static SearchBiller searchBiller;
    static {
        try {
            searchBillerPojo = new SearchBillerPojo();
            searchBiller = new SearchBiller();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("To verify that Searchbillers ELECTRICITY category")
    @Feature("GetSearchBillers")
    @Test(priority = 1,dataProvider = "SearchBillers", dataProviderClass = BillpayTestData.class, groups = {"regression", "sanity"})
    public void verifyGetSearchBillersForAllCategories(String SearchBillers) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("category", electricity);
        //queryParamDetails.put("pageNum", "1");
        //queryParamDetails.put("pageSize", "1");
        queryParamDetails.put("billerSearchString", SearchBillers);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        searchBillerPojo = searchBiller.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(searchBillerPojo.elements, "verify the elements should not null");

    }


    @Description("To verify that Search biller details for Insurance category with non whitelisted user")
    @Feature("GetSearchBillers")
    @Test(priority = 2, groups = {"regression", "sanity"})
    public void verifyGetSearchBillersForInsuranceWithNonwhitelistedUser() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("category", insurance);
        queryParamDetails.put("pageNum", "1");
        queryParamDetails.put("pageSize", "1");
        queryParamDetails.put("billerSearchString", insuranceSearchString);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        searchBillerPojo = searchBiller.get(queryParamDetails, headerDetails);
        Assert.assertEquals(searchBillerPojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");

    }

}
