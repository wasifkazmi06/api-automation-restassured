package lazypay.juspay.repaymentFlow;

import api.lazypay.juspay.repayment.SavedOption;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.qameta.allure.Step;

import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.juspay.repaymentFlow.SavedOptionPojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.Map;

public class SavedOptionSteps {
    public static ResultSet rs;
    public static SavedOption savedOption;
    public static SavedOptionPojo savedOptionPojo = new SavedOptionPojo();
    static {
        try {
            savedOption = new SavedOption();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public SavedOptionSteps() throws Exception {
    }
    @Step
    @Test
    public void verifySavedOptionUPI() throws SQLException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        savedOptionPojo = savedOption.get(queryParamDetails, headerDetails, RepayDetailsSteps.identifier);
        System.out.println(savedOptionPojo.toString());
        JsonNode paymentMethod=savedOptionPojo.getUserPreferredPaymentMethod().get("paymentMethod");
        JsonNode prferredMethod=savedOptionPojo.getUserPreferredPaymentMethod().get("preferredMethodDetails");
        JsonNode prferredMethodVPA=prferredMethod.get("vpa");
        JsonNode savedmethod=savedOptionPojo.getUserSavedPaymentMethod();
        ArrayNode arrayNode= (ArrayNode) savedmethod.get("vpas");
        String vpaValue = arrayNode.get(0).asText();

        String ValueForPaymentmethod=paymentMethod.asText();
        String ValueForprferredMethod=prferredMethodVPA.asText();
        Assert.assertEquals(ValueForPaymentmethod, "UPI_COLLECT","saved repayment options for a UPI user failed as VPA mismatched");
        Assert.assertEquals(ValueForprferredMethod, RepaymentData_JP.USER_TEST_VPA_JP,"saved repayment options for a VPA user failed as VPA mismatched");
        Assert.assertEquals(vpaValue, RepaymentData_JP.USER_TEST_VPA_JP,"saved repayment options for a VPA user failed as VPA mismatched");
    }



    @Step
    public void verifySavedOptionCreditCard() throws SQLException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("mock","true");
        savedOptionPojo = savedOption.get(queryParamDetails, headerDetails, RepayDetailsSteps.identifier);
        Assert.assertEquals(savedOptionPojo.id,RepaymentData_JP.USER_MOBILE_CC_JP,"getting invalid response");
    }

    @Step
    public void verifySavedOptionDebitCard() throws SQLException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("mock","true");
        savedOptionPojo = savedOption.get(queryParamDetails, headerDetails, RepayDetailsSteps.identifier);
        Assert.assertEquals(savedOptionPojo.id,RepaymentData_JP.USER_MOBILE_DC_JP,"getting invalid response");

    }


    @Step
    public void verifySavedOptionInvalidUser() {


        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        savedOptionPojo = savedOption.get(queryParamDetails, headerDetails, "123Ab!@#45");
        Assert.assertEquals(savedOptionPojo.errorCode,"LP_INVALID_USER_DETAIL","invalid validation on mobile number for savedOptions API failed as error code did not match");
        Assert.assertEquals(savedOptionPojo.message,"Oops!! User associated with corresponding transaction does not seem to exist in LazyPay","invalid validation on mobile number for savedOptions API failed as error message did not match");


    }

}
