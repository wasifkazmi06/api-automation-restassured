package lazypay.repaymentFlow;

import api.lazypay.repayment.CheckBinHealth;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.lazypay.repaymentFlow.CheckBinHealthPojo;

import java.util.HashMap;
import java.util.Map;

public class BinHealthCheckSteps {
    CheckBinHealthPojo checkBinHealthPojo = new CheckBinHealthPojo();
    CheckBinHealth checkBinHealth = new CheckBinHealth();
    SavedOptionsSteps savedOptionsTests = new SavedOptionsSteps();

    public BinHealthCheckSteps() throws Exception {
    }


    @Step
    public void verifyBinHealthCreditCard() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("isCreditLine", "0");

        checkBinHealthPojo = checkBinHealth.get(queryParamDetails, headerDetails, savedOptionsTests.savedOptionPojo.cards.get(0).cardNo.substring(0,6));

        Assert.assertEquals(checkBinHealthPojo.bankShortName, RepaymentData.BANK_SHORT_NAME,"Bank Short Name did not match");
        Assert.assertEquals(checkBinHealthPojo.status,1,"Bin Health check status is not true");

    }

    @Step
    public void verifyBinHealthDebitCard() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("isCreditLine", "1");

        checkBinHealthPojo = checkBinHealth.get(queryParamDetails, headerDetails, savedOptionsTests.savedOptionPojo.cards.get(0).cardNo.substring(0,6));

        Assert.assertEquals(checkBinHealthPojo.bankShortName, RepaymentData.BANK_SHORT_NAME,"Bank Short Name did not match");
        Assert.assertEquals(checkBinHealthPojo.status,1,"Bin Health check status is not true");

    }

    @Step
    public void verifyBinHealthInvalidBINCL0() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("isCreditLine", "0");

        checkBinHealthPojo = checkBinHealth.get(queryParamDetails, headerDetails, "1$@dSA");

        Assert.assertEquals(checkBinHealthPojo.bankShortName,null,"bank Short name is not Null");
        Assert.assertEquals(checkBinHealthPojo.status,0,"Bin Health check status is not false");

    }

    @Step
    public void verifyBinHealthInvalidBINCL1() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("isCreditLine", "1");

        checkBinHealthPojo = checkBinHealth.get(queryParamDetails, headerDetails, "1$@dSA");

        Assert.assertEquals(checkBinHealthPojo.bankShortName,null,"bank Short name is not Null");
        Assert.assertEquals(checkBinHealthPojo.status,0,"Bin Health check status is not true");

    }

}
