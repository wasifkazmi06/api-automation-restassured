package bnplRisk.riskPL;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import bnplRisk.TestData;

import java.sql.ResultSet;
import java.util.HashMap;

@Slf4j
public class RiskPLAPITests {


    //@BeforeClass()
    public void baseDataSetup(){

        TestData.deletePreApproveHistory(TestData.USER_MOBILE_VALID1);
    }

    @Description("Verify that pre-approved limit and interest rates are setup correctly for a valid user using the risk PL update pre-approved limit API")
    @Feature("RiskPL")
    @Test(priority = 1, groups = {"riskpl", "regression", "sanity"})
    public void riskPLUpdatePreApproveLimitCOFProduct() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("limit", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
        request.put("decision", "true");
        request.put("product", TestData.PRODUCT_COF);

        RiskPLUpdatePreApproveLimitStep.RiskPLUpdatePreApproveLimitMethod(request);

        Assert.assertEquals(RiskPLUpdatePreApproveLimitStep.riskPLUpdatePreApproveLimitPojo.productLimits.get(0).product, TestData.PRODUCT_COF);
        Assert.assertTrue(RiskPLUpdatePreApproveLimitStep.riskPLUpdatePreApproveLimitPojo.productLimits.get(0).isUpdated);
    }

    @Description("Verify that the pre-approved limit and interest rates set via the risk PL update pre-approved limit API are stored correctly in the DB")
    @Feature("RiskPL")
    @Test(priority = 2, groups = {"riskpl", "regression", "sanity"})
    public void validateDBUpdateOnRiskPLUpdatePreApproveLimitCOFProduct() throws Exception {

        ResultSet rs = TestData.getPreApproveHistory(TestData.USER_MOBILE_VALID1);

        while (rs.next()) {
            Assert.assertEquals(rs.getString("mobile"), TestData.USER_MOBILE_VALID1);
            log.info("Actual mobile: " + rs.getString("mobile"));
            Allure.addDescription("Actual mobile: " + rs.getString("mobile"));
            Assert.assertEquals(rs.getString("product_limit"), TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
            log.info("Actual product_limit: " + rs.getString("product_limit"));
            Allure.addDescription("Actual product_limit: " + rs.getString("product_limit"));
            Assert.assertEquals(rs.getString("product"), TestData.PRODUCT_COF);
            log.info("Actual product: " + rs.getString("product"));
            Allure.addDescription("Actual product: " + rs.getString("product"));
            JSONArray interest_rate= new JSONArray(rs.getString("interest_rate"));
            int i;
            log.info("Actual interest_rate: " + rs.getString("interest_rate"));
            for (i=0; i<interest_rate.length(); i++)
                {
                    switch(interest_rate.getJSONObject(i).get("tenure").toString()) {
                        case TestData.TENURE_3M:
                            Assert.assertEquals(interest_rate.getJSONObject(i).get("interestRate").toString(), TestData.DEFAULT_INTEREST_3M);
                            break;
                        case TestData.TENURE_6M:
                            Assert.assertEquals(interest_rate.getJSONObject(i).get("interestRate").toString(), TestData.DEFAULT_INTEREST_6M);
                            break;
                        case TestData.TENURE_9M:
                            Assert.assertEquals(interest_rate.getJSONObject(i).get("interestRate").toString(), TestData.DEFAULT_INTEREST_9M);
                            break;
                        case TestData.TENURE_12M:
                            Assert.assertEquals(interest_rate.getJSONObject(i).get("interestRate").toString(), TestData.DEFAULT_INTEREST_12M);
                            break;
                        default :
                            log.error("Invalid Tenure!");
                    }
                }
            }
        }

    @Description("Verify that BNPL limit is set correctly for a valid user using the risk PL get whitelist limit API")
    @Feature("RiskPL")
    @Test(priority = 3, groups = {"riskpl", "regression", "sanity"})
    public void riskPLSetWhitelistLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("limit", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
        request.put("decision", "true");
        request.put("bnpl_line", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
        request.put("pan_hash", TestData.TEST_PAN_HASH);
        request.put("bnpl_decision", "true");

        RiskPLSetWhitelistLimitStep.riskPLSetWhitelistLimitMethod(request);

        Assert.assertTrue(RiskPLSetWhitelistLimitStep.riskPLSetWhitelistLimitPojo.bnplUpdated);
        //Assert.assertNull(RiskPLSetWhitelistLimitStep.riskPLSetWhitelistLimitPojo.updated);
        //Assert.assertNull(RiskPLSetWhitelistLimitStep.riskPLSetWhitelistLimitPojo.lazycardUpdated);
    }

    @Description("Verify that COF and BNPL limits are set correctly for a valid user using the risk PL set whitelist limit API")
    @Feature("RiskPL")
    @Test(priority = 4, groups = {"riskpl", "regression", "sanity"})
    public void riskPLSetWhitelistLimitV1() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("panHash", TestData.TEST_PAN_HASH);
        request.put("decision", "true");
        request.put("cof_limitRisk1", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
        request.put("cof_limitRisk2", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_2);
        request.put("cof_limitRisk3", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_3);
        request.put("decision_cof", "true");
        request.put("bnpl_limitRisk1", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
        request.put("decision_bnpl", "true");

        RiskPLSetWhitelistLimitV1Step.riskPLSetWhitelistLimitV1Method(request);

        Assert.assertEquals(RiskPLSetWhitelistLimitV1Step.riskPLSetWhitelistLimitV1Pojo.productLimits.get(0).product, TestData.PRODUCT_COF);
        Assert.assertTrue(RiskPLSetWhitelistLimitV1Step.riskPLSetWhitelistLimitV1Pojo.productLimits.get(0).isUpdated);
        Assert.assertEquals(RiskPLSetWhitelistLimitV1Step.riskPLSetWhitelistLimitV1Pojo.productLimits.get(1).product, TestData.PRODUCT_BNPL);
        Assert.assertTrue(RiskPLSetWhitelistLimitV1Step.riskPLSetWhitelistLimitV1Pojo.productLimits.get(1).isUpdated);
    }

    @Description("To")
    @Feature("RiskPL")
    //@Test(priority = 4, groups = {"riskpl", "preapprovelimit", "regression", "sanity"})
    public void riskPLGetDecisionV1() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("inputEmail", TransactionData.USER_EMAIL);
        request.put("inputMobile", TestData.USER_MOBILE_VALID1);
        request.put("inputName", TransactionData.FIRST_NAME + TransactionData.LAST_NAME);
        request.put("panVerifiedName", TransactionData.FIRST_NAME + TransactionData.LAST_NAME);

        RiskPLGetDecisionV1Step.riskPLGetDecisionV1Method(request);

        Assert.assertEquals(RiskPLGetDecisionV1Step.riskPLGetDecisionPojo, TestData.PRODUCT_COF);
    }
}