package lazypay.repaymentFlow;


import io.qameta.allure.Step;

import api.lazypay.repayment.SavedOption;
import org.testng.Assert;
import pojos.lazypay.repaymentFlow.SavedOptionPojo;
import lazypay.repaymentFlow.RepaymentData;
import java.util.HashMap;
import java.util.Map;

public class SavedOptionsSteps {

    public static SavedOption savedOption;

    static {
        try {
            savedOption = new SavedOption();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SavedOptionPojo savedOptionPojo = new SavedOptionPojo();

    public SavedOptionsSteps() throws Exception {
    }

    @Step
    public void verifySavedOptionUPI() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        savedOptionPojo = savedOption.get(queryParamDetails, headerDetails, RepaymentData.USER_MOBILE_UPI);
        Assert.assertEquals(savedOptionPojo.vpaList.get(0), RepaymentData.USER_TEST_VPA,"saved repayment options for a VPA user failed as VPA mismatched");
    }

    @Step
    public void verifySavedOptionDebitCard() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        savedOptionPojo = savedOption.get(queryParamDetails, headerDetails, RepaymentData.USER_MOBILE_DC);
        Assert.assertEquals(savedOptionPojo.cards.get(0).cardToken, PreferredMethodSteps.preferredMethodPojo.card.cardToken,"saved repayment options for a DebitCard user failed as card token did not match");
        Assert.assertEquals(savedOptionPojo.cards.get(0).cardNo.substring(0,6), PreferredMethodSteps.preferredMethodPojo.card.cardNo.substring(0,6),"saved repayment options for a DebitCard user failed as card number did not match");
    }

    @Step
    public void verifySavedOptionCreditCard() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

       savedOptionPojo = savedOption.get(queryParamDetails, headerDetails, RepaymentData.USER_MOBILE_CC);
        Assert.assertEquals(savedOptionPojo.cards.get(0).cardToken, PreferredMethodSteps.preferredMethodPojo.card.cardToken,"saved repayment options for a CreditCard user failed as card token did not match");
        Assert.assertEquals(savedOptionPojo.cards.get(0).cardNo.substring(0,6), PreferredMethodSteps.preferredMethodPojo.card.cardNo.substring(0,6),"saved repayment options for a CreditCard user failed as card number did not match");

    }

    @Step
    public void verifySavedOptionInvalidUser() {


            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();
        savedOptionPojo = savedOption.get(queryParamDetails, headerDetails, "123Ab!@#45");
        Assert.assertEquals(savedOptionPojo.errorCode,"USER_NOT_FOUND","invalid validation on mobile number for savedOptions API failed as error code did not match");
        Assert.assertEquals(savedOptionPojo.message,"Oops!! Something went wrong","invalid validation on mobile number for savedOptions API failed as error message did not match");


    }
}
