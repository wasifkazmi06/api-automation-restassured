package billpay.mobilePrepaid;

import api.billpay.mobilePrepaid.PrepaidOperators;
import billpay.BillpayTestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.mobilePrepaid.PrepaidOperatorPojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidOperatorTest extends BillpayTestData {
    static PrepaidOperatorPojo prepaidOperatorPojo;
    public static PrepaidOperators prepaidOperators;

    static {
        try {
            prepaidOperatorPojo = new PrepaidOperatorPojo();
            prepaidOperators= new PrepaidOperators();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("Verify the prepaidoperator")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyPrepaidOperators() throws Exception {
        PrepaidOperators prepaidOperators = new PrepaidOperators();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        Response prepaidOperatorsWithResponse  = prepaidOperators.getWithResponse(queryParamDetails,headerDetails);
        Assert.assertEquals(prepaidOperatorsWithResponse.getStatusCode(), 200, "prepaidoperator are coming");
    }
    @Description("Verify the prepaidoperator for BBPS_PRODUCT_NOT_ELIGIBLE user")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyPrepaidOperatorsforBBPS_PRODUCT_NOT_ELIGIBLE() throws Exception {
        PrepaidOperators prepaidOperators = new PrepaidOperators();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        Response prepaidOperatorsWithResponse  = prepaidOperators.getWithResponse(queryParamDetails,headerDetails);
        Assert.assertEquals(prepaidOperatorsWithResponse.getStatusCode(), 403, "BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");
    }
}
