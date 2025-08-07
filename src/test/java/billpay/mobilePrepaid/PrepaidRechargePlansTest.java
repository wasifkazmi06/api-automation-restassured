package billpay.mobilePrepaid;

import api.billpay.mobilePrepaid.PrepaidRechargePlans;
import billpay.BillpayTestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.billpayment.mobilePrepaid.PrepaidRechargePlansPojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidRechargePlansTest extends BillpayTestData {
    static PrepaidRechargePlansPojo prepaidRechargePlansPojo;
    public static PrepaidRechargePlans prepaidRechargePlans;

    static {
        try {
            prepaidRechargePlansPojo = new PrepaidRechargePlansPojo();
            prepaidRechargePlans= new PrepaidRechargePlans();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Description("Verify the PrepaidRechargePlans for Airtel")
    @Feature("Billpay")
    @Test(priority = 1,dataProvider = "PrepaidOperators", dataProviderClass = BillpayTestData.class,groups = {"regression", "sanity"})
    public void verifyPrepaidRechargePlans(String PrepaidOperators) throws Exception {
        PrepaidRechargePlans prepaidRechargePlans = new PrepaidRechargePlans();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("operatorCode",PrepaidOperators);
        queryParamDetails.put("circleRefId",CircleRefID);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        prepaidRechargePlansPojo = prepaidRechargePlans.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(prepaidRechargePlansPojo.plans);
        //Assert.assertEquals(prepaidRechargePlansPojo.biller.billerId, "MTNLPREPAID","MTNL Plans are displaying");
    }
    @Description("Verify the PrepaidRechargePlans for BSNL")
    @Feature("Billpay")
    @Test(priority = 1,groups = {"regression", "sanity"})
    public void verifyPrepaidRechargePlansforBSNL() throws Exception {
        PrepaidRechargePlans prepaidRechargePlans = new PrepaidRechargePlans();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("operatorCode",bsnloperatorCode);
        queryParamDetails.put("circleRefId",bsnlCircleRefID);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", UserId11);
        prepaidRechargePlansPojo = prepaidRechargePlans.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(prepaidRechargePlansPojo.plans);
        Assert.assertEquals(prepaidRechargePlansPojo.biller.category, "MOBILE PREPAID","BSNL Plans are displaying");
    }


    @Description("Verify the PrepaidRechargePlans for Vodafone with BBPS_PRODUCT_NOT_ELIGIBLE user")
    @Feature("Billpay")
    @Test(priority = 2,groups = {"regression", "sanity"})
    public void verifyPrepaidRechargePlansforVodafonenonWhitelistedUser() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("operatorCode",ideaoperatorCode);
        queryParamDetails.put("circleRefId",circleRefId);
        headerDetails.put("sourceSystem", SourceSystem);
        headerDetails.put("userId", BBPS_PRODUCT_NOT_ELIGIBLE);
        prepaidRechargePlansPojo = prepaidRechargePlans.get(queryParamDetails, headerDetails);
        Assert.assertEquals(prepaidRechargePlansPojo.errorCode,"BBPS_PRODUCT_NOT_ELIGIBLE","BBPS_PRODUCT_NOT_ELIGIBLE for non kyc users");
    }
}
