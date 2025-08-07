package billpay.mobilePrepaid;

import api.billpay.mobilePrepaid.PrepaidOperatorCircleForMobile;
import billpay.BillpayTestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.mobilePrepaid.PrepaidOperatorCircleForMobilePojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidOperatorCircleForMobileTest extends BillpayTestData {
    static PrepaidOperatorCircleForMobilePojo prepaidOperatorCircleForMobilePojo;
    public static PrepaidOperatorCircleForMobile prepaidOperatorCircleForMobile;

    static {
        try {
            prepaidOperatorCircleForMobilePojo = new PrepaidOperatorCircleForMobilePojo();
            prepaidOperatorCircleForMobile = new PrepaidOperatorCircleForMobile();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("To verify prepaidOperatorCircleForMobile")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyPrepaidOperatorCircleForMobile() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("customerPhoneNumber", customerPhoneNumber);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        prepaidOperatorCircleForMobilePojo = prepaidOperatorCircleForMobile.get(queryParamDetails, headerDetails);
        Assert.assertEquals(prepaidOperatorCircleForMobilePojo.billerDetail.displayParamName,"MobileNumber","display param is coming for mobileNumber");
    }
    @Description("To verify prepaidOperatorCircleForMobile")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyPrepaidOperatorCircleForinvalidMobile() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("customerPhoneNumber", customerinvalidPhoneNumber);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        prepaidOperatorCircleForMobilePojo = prepaidOperatorCircleForMobile.get(queryParamDetails, headerDetails);
        Assert.assertEquals(prepaidOperatorCircleForMobilePojo.errorCode,"INVALID_RECHARGE_PHONE_NUMBER","INVALID_RECHARGE_PHONE_NUMBER is entered");
    }
    @Description("To verify prepaidOperatorCircleForMobile for BBPS_PRODUCT_NOT_ELIGIBLE")
    @Feature("Billpay")
    @Test(priority = 3,groups = {"regression", "sanity"})
    public void verifyPrepaidOperatorCircleForMobileBBPS_PRODUCT_NOT_ELIGIBLE() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("customerPhoneNumber", customerPhoneNumber);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        prepaidOperatorCircleForMobilePojo = prepaidOperatorCircleForMobile.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(prepaidOperatorCircleForMobilePojo.errorCode);
        Assert.assertEquals(prepaidOperatorCircleForMobilePojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");

    }

}