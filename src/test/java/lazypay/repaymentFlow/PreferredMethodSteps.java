package lazypay.repaymentFlow;

import api.lazypay.repayment.PreferredMethod;
import io.qameta.allure.Step;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.lazypay.repaymentFlow.PreferredMethodPojo;
import java.util.HashMap;
import java.util.Map;

public class PreferredMethodSteps {

    public static PreferredMethod preferredMethod;

    static {
        try {
            preferredMethod = new PreferredMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PreferredMethodPojo preferredMethodPojo = new PreferredMethodPojo();

    @BeforeClass
    public void repaymentTestsPrerequisites() {

    }

    @Step
    public void verifyPreferredMethodUPI() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        preferredMethodPojo = preferredMethod.get(queryParamDetails, headerDetails, RepaymentData.USER_MOBILE_UPI);
        Assert.assertEquals(preferredMethodPojo.paymentMethod, "UPI","preferred repayment methods for a UPI user failed as payment method is not UPI");
        Assert.assertEquals(preferredMethodPojo.bankCode, "UPI","preferred repayment methods for a UPI user failed as bank Code is not UPI");
        Assert.assertEquals(preferredMethodPojo.vpa, RepaymentData.USER_TEST_VPA,"preferred repayment methods for a UPI user failed as VPA mismatched");
        Assert.assertEquals(preferredMethodPojo.userIdentifier, RepayDetailsSteps.repayDetailsPojo.identifier,"preferred repayment methods for a UPI user failed as UserIdentifier mismatched for UPI");
    }

    @Step
    public void verifyPreferredMethodCreditCard() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        preferredMethodPojo = preferredMethod.get(queryParamDetails, headerDetails, RepaymentData.USER_MOBILE_CC);
        Assert.assertEquals(preferredMethodPojo.paymentMethod,"CC","preferred repayment methods for a Debit Card user failed as payment method is not CC");
        Assert.assertEquals(preferredMethodPojo.bankCode, "CC","preferred repayment methods for a Credit Card user failed as bank Code is not CC");
        Assert.assertEquals(preferredMethodPojo.userIdentifier, RepayDetailsSteps.repayDetailsPojo.identifier,"preferred repayment methods for a Credit Card user failed as UserIdentifier mismatched for CC");


    }

    @Step
    public void verifyPreferredMethodNetBanking() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        preferredMethodPojo = preferredMethod.get(queryParamDetails, headerDetails, RepaymentData.USER_MOBILE_NB);
        Assert.assertEquals(preferredMethodPojo.bankCode, "HDFB","preferred repayment methods for a Net banking user failed as bank Code is not HDFB");
        Assert.assertEquals(preferredMethodPojo.userIdentifier, RepayDetailsSteps.repayDetailsPojo.identifier,"Preferred repayment methods for a Net Banking user failed as UserIdentifier mismatched for NB");

    }

    @Step
    public void verifyPreferredMethodDebitCard() {

            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();

            preferredMethodPojo = preferredMethod.get(queryParamDetails, headerDetails, RepaymentData.USER_MOBILE_DC);
            Assert.assertEquals(preferredMethodPojo.bankCode, "VISA","preferred repayment methods for a Debit Card user failed as bank Code is not VISA");
            Assert.assertEquals(preferredMethodPojo.userIdentifier, RepayDetailsSteps.repayDetailsPojo.identifier,"Preferred repayment methods for a Debit Card user failed as UserIdentifier mismatched for DC");
        }


    @Step
    public void verifyPreferredMethodInvalidUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        preferredMethodPojo = preferredMethod.get(queryParamDetails, headerDetails, "123Ab!@#45");
        Assert.assertEquals(preferredMethodPojo.errorCode,"USER_NOT_FOUND","Preferred Method for Invalid User failed as error code was different from user not found");
        Assert.assertEquals(preferredMethodPojo.message,"Oops!! Something went wrong","Preferred Method for Invalid User failed as Message Oops!! Something went wrong was not matched");
    }

}
