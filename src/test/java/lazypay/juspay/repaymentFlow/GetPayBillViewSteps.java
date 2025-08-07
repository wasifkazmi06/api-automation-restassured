package lazypay.juspay.repaymentFlow;

import api.lazypay.juspay.repayment.GetPayBillView;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.Step;
import lazypay.MakeTransaction;
import lazypay.transactionFlow.TransactionData;
import mbe.authentication.APPLoginUser;
import mbe.authentication.AuthTestData;
import org.testng.Assert;
import pojos.lazypay.juspay.repaymentFlow.GetPayBillViewPojo;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


public class GetPayBillViewSteps {

    public static GetPayBillViewPojo getPayBillViewPojo = new GetPayBillViewPojo();
    public static GetPayBillView getPayBillView;
    public static ResultSet rs;
    public static String totaloutStandingAmount;
    public static Double lpoutstanding;
    lazypay.repaymentFlow.RepayDetailsSteps repayDetailsSteps= new lazypay.repaymentFlow.RepayDetailsSteps();

    static {
        try {
            getPayBillView = new GetPayBillView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GetPayBillViewSteps() throws Exception {
    }

    @Step
    public void verifyBillViewFortheUser (String userMobile) throws Exception {

        String OauthToken= APPLoginUser.LoginIntoApp(userMobile);
         lpoutstanding=repayDetailsSteps.fetchTotalOustanding (userMobile);

        if(lpoutstanding<=0){
            HashMap<String, String> request = new HashMap<>();
            request.put("mobile", userMobile);
            request.put("amount", TransactionData.TRANSACTION_AMOUNT);
            request.put("accessKey", TransactionData.ACCESS_KEY);
            request.put("source", TransactionData.SOURCE_CITRUS_SDK);
            request.put("initiateVersion", TransactionData.INITIATE_PAY_V0);
            request.put("payVersion", TransactionData.PAY_V0);

            MakeTransaction.makeLPOTPTransactionHash(request, 1);
            Thread.sleep(500);
            lpoutstanding=repayDetailsSteps.fetchTotalOustanding (userMobile);
        }

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization",OauthToken);
        headerDetails.put("PF-App-Lock-Token", "");
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", "");
        headerDetails.put("PF-Location", AuthTestData.location);
        queryParamDetails.put("minAmountDue","false");
        queryParamDetails.put("product",AuthTestData.productBNPL);
        getPayBillViewPojo = getPayBillView.get(queryParamDetails, headerDetails);
        JsonNode outstandingAmount=getPayBillViewPojo.getTotalOutstandingRadioButton().get("amountValue");
        totaloutStandingAmount=outstandingAmount.asText();
        Assert.assertEquals(totaloutStandingAmount,String.valueOf(lpoutstanding),"Doesnt fetch the outstanding amount");
        Assert.assertEquals(getPayBillViewPojo.screenName,"CHECKOUT_PAY_BILL","Invalid Screen Name ");

    }

    @Step
    public void verifyBillViewFortheInValidUser ( ) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("authorization","116765a7-4e23-4190-962c-9bfc535a82d");
        headerDetails.put("PF-App-Lock-Token", "");
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", "");
        headerDetails.put("PF-Location", AuthTestData.location);
        queryParamDetails.put("minAmountDue","false");
        queryParamDetails.put("product",AuthTestData.productBNPL);
        getPayBillViewPojo = getPayBillView.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getPayBillViewPojo.message,"Invalid auth token","Doesnt fetch the outstanding amount for invalid user");

    }

}
