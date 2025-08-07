package billpay.mobilePrepaid;
import api.billpay.mobilePrepaid.PrepaidOperatorsAndCircles;
import billpay.BillpayTestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.mobilePrepaid.PrepaidOperatorandCirclePojo;

import java.util.HashMap;
import java.util.Map;
public class PrepaidOperatorAndCircleTest extends BillpayTestData {
    public static PrepaidOperatorandCirclePojo prepaidOperatorandCirclePojo;
    public static PrepaidOperatorsAndCircles prepaidOperatorsAndCircles;
    static {
        try {
            prepaidOperatorandCirclePojo = new PrepaidOperatorandCirclePojo();
            prepaidOperatorsAndCircles = new PrepaidOperatorsAndCircles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("To verify prepaidOperatorsAndCircles")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyPrepaidOperatorsAndCircles() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        Response prepaidOperator  = prepaidOperatorsAndCircles.getWithResponse(queryParamDetails,headerDetails);
        Assert.assertEquals(prepaidOperator.getStatusCode(), 200, "CircleName and circleRefId is coming");
    }
    @Description("To verify prepaidOperatorsAndCircles")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyPrepaidOperatorsAndCirclesBBPS_PRODUCT_NOT_ELIGIBLE() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        Response prepaidOperator  = prepaidOperatorsAndCircles.getWithResponse(queryParamDetails,headerDetails);
        Assert.assertEquals(prepaidOperator.getStatusCode(), 403, "BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");
    }

}
