package billpay;

import api.billpay.Categories;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.CategoryPojo;
import java.util.HashMap;
import java.util.Map;

public class CategoriesTest extends BillpayTestData {
    public static CategoryPojo categoryPojo;
    public static Categories categories;

    static {
        try {
            categoryPojo = new CategoryPojo();
            categories = new Categories();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("To verify that Get Categories")
    @Feature("GetCategories")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyGetCategory() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);

        Response category = categories.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(category.getStatusCode(), 200);

    }
    @Description("To verify that Get Category API without headers")
    @Feature("GetCategories")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyGetCategorywithoutheaders() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        Response category = categories.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(category.getStatusCode(), 200);
        Assert.assertNull(categoryPojo.getCategory());
    }

}

