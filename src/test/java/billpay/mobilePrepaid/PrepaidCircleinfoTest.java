package billpay.mobilePrepaid;

import api.billpay.mobilePrepaid.PrepaidCircleInfo;
import billpay.BillpayTestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.mobilePrepaid.PrepaidcircleInfoPojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidCircleinfoTest extends BillpayTestData {
    static PrepaidcircleInfoPojo prepaidcircleInfoPojo;
    public static PrepaidCircleInfo prepaidCircleInfo;

    static {
        try {
            prepaidcircleInfoPojo = new PrepaidcircleInfoPojo();
            prepaidCircleInfo= new PrepaidCircleInfo();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("Generate CircleName and CircleRefId ")
    @Feature("Billpay")
    @Test(priority = 1,dataProvider = "PrepaidOperators", dataProviderClass = BillpayTestData.class, groups = {"regression", "sanity"})
    public void verifyPrepaidCircleInfoforallOperators(String PrepaidOperators) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        queryParamDetails.put("operatorCode",PrepaidOperators);
        Response prepaidcircle  = prepaidCircleInfo.getWithResponse(queryParamDetails,headerDetails);
        Assert.assertEquals(prepaidcircle.getStatusCode(), 200, "fetching circle name n circle refid");
    }
    @Description("Generate CircleName and CircleRefId for BBPS_PRODUCT_NOT_ELIGIBLE")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyPrepaidCircleInforBBPS_PRODUCT_NOT_ELIGIBLE() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        Response prepaidcircle  = prepaidCircleInfo.getWithResponse(queryParamDetails,headerDetails);
        Assert.assertEquals(prepaidcircle.getStatusCode(), 403, "BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");
    }

}
