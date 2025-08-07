package lazypay.juspay.repaymentFlow;



import api.lazypay.juspay.repayment.ValidateVPA;
import io.qameta.allure.Step;


import org.testng.Assert;
import pojos.lazypay.juspay.repaymentFlow.ValidateVPAPojo;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ValidateVPASteps {

    public static ValidateVPAPojo validateVPAPojo= new ValidateVPAPojo();
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
    public void verifyValidateVPAUPI() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("vpa", RepaymentData_JP.USER_TEST_VPA_JP);
        //queryParamDetails.put("mobile", lazypay.repaymentFlow.RepaymentData.USER_VPA.substring(0,10));
        validateVPAPojo = validateVPA.get(queryParamDetails, headerDetails);

        try {
            String status = validateVPAPojo.status;
            System.out.println (status);
        }
          catch (NullPointerException e) {
            System.out.println (e.getMessage ());
        }
        Assert.assertEquals(validateVPAPojo.vpa, RepaymentData_JP.USER_TEST_VPA_JP, "UPI is Invalid");
        //removed Assertion of customer name validation as API retirves null value for test numbers
    }

    @Step
    public void verifyValidateVPAInvalidVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("vpa", "1234566#!2d");
        //queryParamDetails.put("mobile", TransactionData.USER_MOBILE);

        validateVPAPojo = validateVPA.get(queryParamDetails, headerDetails);
        Optional<String> str2 = Optional.ofNullable(validateVPAPojo.status);
        if (str2.isPresent()) {
            System.out.println("Getting Status value as " + str2);
        } else {
            System.out.println("Getting Status value as Invalid");
        }


    }
}