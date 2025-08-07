package lazypay.repaymentFlow;

import api.lazypay.repayment.RepayDetails;
import io.qameta.allure.Step;
import lazypay.juspay.repaymentFlow.RepaymentData_JP;
import org.testng.Assert;
import pojos.lazypay.repaymentFlow.RepayDetailsPojo;
import java.util.HashMap;
import java.util.Map;

public class RepayDetailsSteps {
    public static RepayDetailsPojo repayDetailsPojo = new RepayDetailsPojo();
    public static RepayDetails repayDetails;
    public static double totalOutStanding;


    static {
        try {
            repayDetails = new RepayDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public RepayDetailsSteps() throws Exception {
    }

    @Step
    public void verifyRepayDetailsUPI () throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, RepaymentData_JP.USER_MOBILE_UPI_JP);

        //Temp solution, need to handle properly once pay API is refactored


        Assert.assertNotNull(repayDetailsPojo.identifier);
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding);
        Assert.assertEquals(repayDetailsPojo.mobile, RepaymentData.USER_MOBILE_UPI,"Repay details for UPI failed as Mobile number mis-matched");

    }
    @Step
    public void verifyRepayDetailsCreditCard() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, RepaymentData_JP.USER_MOBILE_CC_JP);

        //Temp solution, need to handle properly once pay API is refactored



        Assert.assertNotNull(repayDetailsPojo.identifier);
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding);
        Assert.assertEquals(repayDetailsPojo.mobile, RepaymentData.USER_MOBILE_CC,"Repay details for Credit Card failed as Mobile number mis-matched");

    }
    @Step
    public void verifyRepayDetailsDebitCard() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, RepaymentData_JP.USER_MOBILE_DC_JP);

        //Temp solution, need to handle properly once pay API is refactored



        Assert.assertNotNull(repayDetailsPojo.identifier);
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding);
        Assert.assertEquals(repayDetailsPojo.mobile, RepaymentData.USER_MOBILE_DC,"Repay details for Debit Card failed as Mobile number mis-matched");

    }

    @Step
    public void verifyRepayDetailsNetBanking() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, RepaymentData_JP.USER_MOBILE_NB_JP);

        //Temp solution, need to handle properly once pay API is refactored



        Assert.assertNotNull(repayDetailsPojo.identifier);
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding);
        Assert.assertEquals(repayDetailsPojo.mobile, RepaymentData.USER_MOBILE_NB,"Repay details for Net Banking failed as Mobile number mis-matched");

    }
    @Step
    public void verifyRepayDetailsInvalidUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, "123Ab!@#45");
        Assert.assertEquals(repayDetailsPojo.errorCode,"USER_NOT_FOUND","Repay details for Invalid User failed as error code was different");
        Assert.assertEquals(repayDetailsPojo.message,"Oops!! Something went wrong","Repay details for Invalid User failed as message was different");

    }


    public String fetchIdentifier (String mobile) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails,mobile );

       String identifier=repayDetailsPojo.identifier;
       return identifier;
    }

    public Double fetchTotalOustanding(String mobile) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        //identifier=repayDetailsSteps.fetchIdentifier(mobile);

        //repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails,mobile );
        totalOutStanding=repayDetailsPojo.totalOutstanding;
        return totalOutStanding;

    }

}
