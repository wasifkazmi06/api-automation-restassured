package lazypay.repaymentFlow;

import api.lazypay.repayment.ValidateVPA;
import io.qameta.allure.Step;

import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import pojos.lazypay.repaymentFlow.ValidateVPAPojo;
import java.util.HashMap;
import java.util.Map;

public class ValidateVPASteps {

    public static ValidateVPAPojo validateVPAPojo = new ValidateVPAPojo();
    public static ValidateVPA validateVPA;

    static {
        try {
            validateVPA = new ValidateVPA();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ValidateVPASteps() throws Exception {
    }

    @Step
    public void verifyValidateVPAUPI() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("vpa", RepaymentData.USER_VPA);
        queryParamDetails.put("mobile", RepaymentData.USER_VPA.substring(0,10));
        ValidateVPAPojo validateVPAPojo = validateVPA.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(validateVPAPojo.vpa, RepaymentData.USER_VPA);
        Assert.assertEquals(validateVPAPojo.name, "Nitesh null","validation of user's VPA failed as Name did not match");
    }

    @Step
    public void verifyValidateVPAInvalidVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("vpa","1234566#!2d");
        queryParamDetails.put("mobile", TransactionData.USER_MOBILE_VALID1);

        validateVPAPojo = validateVPA.get(queryParamDetails, headerDetails);

        Assert.assertEquals(validateVPAPojo.errorCode, "INVALID_VPA","validation of invalid user VPA failed as error code did not match");
        Assert.assertEquals(validateVPAPojo.message, "Virtual Address not exist","validation of invalid user VPA failed as error message did not match");


    }
    
}
