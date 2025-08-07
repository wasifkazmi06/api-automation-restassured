package bnplRisk.risk;

import bnplRisk.riskPL.RiskPLSetWhitelistLimitV1Step;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.bnplRisk.Product;
import bnplRisk.TestData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RiskAPITests {

    @Description("Verify that risk gets correct product limit for a valid user for BNPL and COF product using the risk get product limit API")
    @Feature("Risk")
    @Test(priority = 1, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskGetProductLimitValidUserBNPLCOFProductRiskLevel2() throws Exception {

        List<String> products = new ArrayList<>();
        products.add(TestData.PRODUCT_COF);
        products.add(TestData.PRODUCT_BNPL);

        HashMap<String, Object> request = new HashMap<>();
            request.put("mobile", TestData.USER_MOBILE_VALID1);
            request.put("products", products);
            request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));

        RiskGetProductLimitStep.RiskGetProductLimitMethod(request);

        Assert.assertTrue(RiskGetProductLimitStep.riskGetProductLimitPojo.productLimitDetails.get(Product.COF).decision);
        Assert.assertEquals(String.valueOf(RiskGetProductLimitStep.riskGetProductLimitPojo.productLimitDetails.get(Product.COF).limit),
                TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_2);
        Assert.assertTrue(RiskGetProductLimitStep.riskGetProductLimitPojo.productLimitDetails.get(Product.BNPL).decision);
        Assert.assertEquals(String.valueOf(RiskGetProductLimitStep.riskGetProductLimitPojo.productLimitDetails.get(Product.BNPL).limit),
                TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
    }

    @Description("Verify that risk give decision true for a valid user for COF product using the risk get decision V1 API")
    @Feature("Risk")
    @Test(priority = 2, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskGetDecisionV1ValidUserCOFProduct() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
            request.put("mobile", TestData.USER_MOBILE_VALID1);
            request.put("amount", TestData.TRANSACTION_AMOUNT);
            request.put("merchantAccessKey", TestData.ACCESS_KEY_COF);
            request.put("requestApi", "ELIGIBILITY_" + TransactionData.ELIGIBILITY_V7);
            request.put("products", TestData.PRODUCT_COF);
            request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
            request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
            request.put("subMerchantId", TestData.SUBMERCHANT_ID_COF);
            request.put("version", TestData.RISK_GET_DECISION_V1);


        RiskGetDecisionStep.RiskGetDecisionMethod(request);

        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertTrue(RiskGetDecisionStep.riskGetDecisionPojo.productDetails.get(Product.COF).decision);
    }

    @Description("Verify that risk give decision true for a valid user for COF product using the risk get decision V2 API")
    @Feature("Risk")
    @Test(priority = 3, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskGetDecisionV2ValidUserCOFProduct() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
            request.put("mobile", TestData.USER_MOBILE_VALID1);
            request.put("amount", TestData.TRANSACTION_AMOUNT);
            request.put("merchantAccessKey", TestData.ACCESS_KEY_COF);
            request.put("requestApi", "ELIGIBILITY_" + TransactionData.ELIGIBILITY_V7);
            request.put("products", TestData.PRODUCT_COF);
            request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
            request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
            request.put("subMerchantId", TestData.SUBMERCHANT_ID_COF);
            request.put("version", TestData.RISK_GET_DECISION_V2);

        RiskGetDecisionStep.RiskGetDecisionMethod(request);

        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertTrue(RiskGetDecisionStep.riskGetDecisionPojo.productDetails.get(Product.COF).decision);
    }

    @Description("Verify that risk give decision true for a valid user for BNPL product using the risk get decision V1 API")
    @Feature("Risk")
    @Test(priority = 4, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskGetDecisionV1ValidUserBNPLProduct() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
            request.put("mobile", TestData.USER_MOBILE_VALID1);
            request.put("amount", TestData.TRANSACTION_AMOUNT);
            request.put("merchantAccessKey", TestData.ACCESS_KEY_COF);
            request.put("requestApi", "ELIGIBILITY_" + TransactionData.ELIGIBILITY_V7);
            request.put("products", TestData.PRODUCT_BNPL);
            request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
            request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
            request.put("subMerchantId", TestData.SUBMERCHANT_ID_COF);
            request.put("version", TestData.RISK_GET_DECISION_V1);

        RiskGetDecisionStep.RiskGetDecisionMethod(request);

        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertNotNull(RiskGetDecisionStep.riskGetDecisionPojo.limit);
    }

    @Description("Verify that risk give decision true for a valid user for BNPL product using the risk get decision V2 API")
    @Feature("Risk")
    @Test(priority = 5, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskGetDecisionV2ValidUserBNPLProduct() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
            request.put("mobile", TestData.USER_MOBILE_VALID1);
            request.put("amount", TestData.TRANSACTION_AMOUNT);
            request.put("merchantAccessKey", TestData.ACCESS_KEY_COF);
            request.put("requestApi", "ELIGIBILITY_" + TransactionData.ELIGIBILITY_V7);
            request.put("products", TestData.PRODUCT_BNPL);
            request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
            request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
            request.put("subMerchantId", TestData.SUBMERCHANT_ID_COF);
            request.put("version", TestData.RISK_GET_DECISION_V2);

        RiskGetDecisionStep.RiskGetDecisionMethod(request);

        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertNotNull(RiskGetDecisionStep.riskGetDecisionPojo.limit);
    }

    @Description("Verify that risk give decision true for a valid user for Lazypay Tenant and BNPL product using the risk get decision V1 API")
    @Feature("Risk")
    @Test(priority = 6, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskGetDecisionV1ValidUserTenantLazypay() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
            request.put("mobile", TestData.USER_MOBILE_VALID1);
            request.put("amount", TestData.TRANSACTION_AMOUNT);
            request.put("merchantAccessKey", TestData.ACCESS_KEY_COF);
            request.put("requestApi", "ELIGIBILITY_" + TransactionData.ELIGIBILITY_V7);
            request.put("products", TestData.PRODUCT_BNPL);
            request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
            request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
            request.put("subMerchantId", TestData.SUBMERCHANT_ID_COF);
            request.put("version", TestData.RISK_GET_DECISION_V1);
            request.put("tenant", mpl.TestData.TENANT_LAZYPAY);

        RiskGetDecisionStep.RiskGetDecisionMethod(request);

        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertNotNull(RiskGetDecisionStep.riskGetDecisionPojo.limit);
    }

    @Description("Verify that risk give decision true for a valid user for Lazypay Tenant and BNPL product using the risk get decision V2 API")
    @Feature("Risk")
    @Test(priority = 7, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskGetDecisionV2ValidUserTenantLazypay() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
            request.put("mobile", TestData.USER_MOBILE_VALID1);
            request.put("amount", TestData.TRANSACTION_AMOUNT);
            request.put("merchantAccessKey", TestData.ACCESS_KEY_COF);
            request.put("requestApi", "ELIGIBILITY_" + TransactionData.ELIGIBILITY_V7);
            request.put("products", TestData.PRODUCT_BNPL);
            request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
            request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
            request.put("subMerchantId", TestData.SUBMERCHANT_ID_COF);
            request.put("version", TestData.RISK_GET_DECISION_V2);
            request.put("tenant", mpl.TestData.TENANT_LAZYPAY);

        RiskGetDecisionStep.RiskGetDecisionMethod(request);

        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertNotNull(RiskGetDecisionStep.riskGetDecisionPojo.limit);
    }

    @Description("Verify that risk give decision true for a valid user for BNPL and COF product using the risk get decision V2 API")
    @Feature("Risk")
    @Test(priority = 8, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskUserLimitTransactingUser() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("isTransacting", "1");
        request.put("accessKey", TestData.ACCESS_KEY_COF);

        RiskUserLimitStep.RiskUserLimitMethod(request);

        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponse.getStatusCode(), 200);
        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).mobile , TestData.USER_MOBILE_VALID1);
    }

    @Description("Verify that approved limit is updated for a valid user using the risk update approved limit API")
    @Feature("Risk")
    @Test(priority = 9, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskUpdateApprovedLimitValidUser() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("reason", "Automation Test 123 @#$");
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("cof_limitRisk1", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
        request.put("cof_limitRisk2", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_2);
        request.put("cof_limitRisk3", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_3);
        request.put("cof_reason", "Automation Test 123 @#$");
        request.put("cof_decision", "true");
        request.put("interestRate3", TestData.DEFAULT_INTEREST_3M);
        request.put("interestRate6", TestData.DEFAULT_INTEREST_6M);
        request.put("interestRate9", TestData.DEFAULT_INTEREST_9M);
        request.put("interestRate12", TestData.DEFAULT_INTEREST_12M);
        request.put("bnpl_limitRisk1", TestData.PRE_APPROVE_LIMIT_RISK_LEVEL_1);
        request.put("bnpl_reason", "Automation Test 123 @#$");
        request.put("bnpl_decision", "true");

        RiskUpdateApprovedLimitStep.RiskUpdateApprovedLimitMethod(request);

        Assert.assertTrue(RiskUpdateApprovedLimitStep.riskUpdateApprovedLimitPOJO.productLimits.get(Product.COF).isUpdated);
        Assert.assertTrue(RiskUpdateApprovedLimitStep.riskUpdateApprovedLimitPOJO.productLimits.get(Product.BNPL).isUpdated);
    }


    @Description("Verify that the tentative limit is returned as expected for BNPL product for a valid user")
    @Feature("Risk")
    @Test(priority = 10, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskTentativeLimitPostMITCBNPLProductValidUser() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("products", TestData.PRODUCT_BNPL);
        request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
        request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
        request.put("riskCategory", TestData.RISK_CATEGORY_1);

        RiskTentativeLimitPostMITCStep.RiskTentativeLimitPostMITCMethod(request);

        Assert.assertEquals(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.mobile, TestData.USER_MOBILE_VALID1);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.limit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.cycleLimit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.termLimit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.riskCategory);
    }

    @Description("Verify that the tentative limit is returned as expected for COF product for a valid user")
    @Feature("Risk")
    @Test(priority = 11, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskTentativeLimitPostMITCCOFProductValidUser() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("products", TestData.PRODUCT_COF);
        request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
        request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
        request.put("riskCategory", TestData.RISK_CATEGORY_1);

        RiskTentativeLimitPostMITCStep.RiskTentativeLimitPostMITCMethod(request);

        Assert.assertEquals(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.mobile, TestData.USER_MOBILE_VALID1);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.limit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.cycleLimit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.termLimit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.riskCategory);
    }

    @Description("Verify that the tentative limit is returned as expected for BNPL and COF product for a valid user")
    @Feature("Risk")
    @Test(priority = 12, groups = {"risk", "getDecision", "regression", "sanity"})
    public void riskTentativeLimitPostMITCBNPLCOFProductValidUser() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("products", TestData.PRODUCT_COF + "," + TestData.PRODUCT_BNPL);
        request.put("merchantId", TestData.getMerchant(TestData.ACCESS_KEY_COF, "merchantId"));
        request.put("subMerchantIdentifier", TestData.getSubMerchant(TestData.ACCESS_KEY_COF, TestData.SUBMERCHANT_ID_COF, "id"));
        request.put("riskCategory", TestData.RISK_CATEGORY_1);

        RiskTentativeLimitPostMITCStep.RiskTentativeLimitPostMITCMethod(request);

        Assert.assertEquals(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.mobile, TestData.USER_MOBILE_VALID1);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.limit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.cycleLimit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.termLimit);
        Assert.assertNotNull(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.riskCategory);
    }

    /**
     * MPL Risk cases
     * @throws Exception
     */

    @Description("Verify that BNPL Meesho limits are set correctly for a valid user using the risk PL set whitelist limit API")
    @Feature("Risk_MPL")
    @Test(priority = 4, groups = {"risk", "regression", "sanity", "mpl_regression"})
    public void riskSetWhitelistLimitV1BNPLMeeshoProduct() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", mpl.TestData.USER_MOBILE_VALID_3);
        request.put("panHash", TestData.TEST_PAN_HASH);
        request.put("bnpl_limitRisk1", mpl.TestData.LIMIT_DEFAULT);
        request.put("decision_bnpl", "true");
        request.put("product", mpl.TestData.PRODUCT_BNPL);
        request.put("tenant", mpl.TestData.TENANT_MEESHO);

        RiskSetWhitelistLimitV1Step.riskSetWhitelistLimitV1Method(request);

        Assert.assertEquals(RiskSetWhitelistLimitV1Step.riskSetWhitelistLimitV1Pojo.productLimits.get(0).product, TestData.PRODUCT_BNPL);
        Assert.assertTrue(RiskSetWhitelistLimitV1Step.riskSetWhitelistLimitV1Pojo.productLimits.get(0).isUpdated);
    }

    @Description("Verify that BNPL Meesho limits are set correctly for a valid user using the risk PL set whitelist limit API")
    @Feature("Risk_MPL")
    @Test(priority = 4, groups = {"risk", "regression", "sanity", "mpl_regression"})
    public void riskSetWhitelistLimitV1BNPLMeeshoProductCopy() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", mpl.TestData.USER_MOBILE_VALID_3);
        request.put("panHash", TestData.TEST_PAN_HASH);
        request.put("bnpl_limitRisk1", mpl.TestData.LIMIT_DEFAULT);
        request.put("decision_bnpl", "true");
        request.put("product", mpl.TestData.PRODUCT_BNPL);
        request.put("tenant", mpl.TestData.TENANT_MEESHO);

        RiskSetWhitelistLimitV1StepCopy.riskSetWhitelistLimitV1Method(request);

        Assert.assertEquals(RiskSetWhitelistLimitV1Step.riskSetWhitelistLimitV1Pojo.productLimits.get(0).product, TestData.PRODUCT_BNPL);
        Assert.assertTrue(RiskSetWhitelistLimitV1Step.riskSetWhitelistLimitV1Pojo.productLimits.get(0).isUpdated);
    }

    //@Test whitelist limit has been added to the DB

    @Description("Verify that risk give decision true for a valid user for Meesho BNPL product using the risk get decision V1 API")
    @Feature("Risk_MPL")
    @Test(priority = 13, groups = {"risk", "getDecision", "regression", "sanity", "mpl_regression"})
    public void riskGetDecisionV1ValidUserMeeshoBNPLProduct() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", mpl.TestData.USER_MOBILE_VALID_4);
        request.put("amount", mpl.TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("merchantAccessKey", mpl.TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("requestApi", "ELIGIBILITY");
        request.put("products", TestData.PRODUCT_BNPL);
        request.put("merchantId", TestData.getMerchant(mpl.TestData.ACCESS_KEY_MPL_DEFAULT, "merchantId"));
        request.put("tenant", mpl.TestData.TENANT_MEESHO);
        request.put("version", TestData.RISK_GET_DECISION_V1);

        RiskGetDecisionStep.RiskGetDecisionMethod(request);

        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertTrue(RiskGetDecisionStep.riskGetDecisionPojo.limit > 0);
        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.cycleLimit.toString(),
                String.valueOf(Double.valueOf(RiskGetDecisionStep.riskGetDecisionPojo.limit)*5));
        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.termLimit.toString(),
                String.valueOf(Double.valueOf(RiskGetDecisionStep.riskGetDecisionPojo.limit)*5*12*5));
    }

    //@Test user mitc not done

    //@Test platform user not found

    @Description("Verify that risk give decision true for a valid user for Meesho BNPL product using the risk get decision V2 API")
    @Feature("Risk_MPL")
    @Test(priority = 14, groups = {"risk", "getDecision", "regression", "sanity", "mpl_regression"})
    public void riskGetDecisionV2ValidUserMeeshoBNPLProduct() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", mpl.TestData.USER_MOBILE_VALID_4);
        request.put("amount", mpl.TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("merchantAccessKey", mpl.TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("requestApi", "ELIGIBILITY");
        request.put("products", TestData.PRODUCT_BNPL);
        request.put("merchantId", TestData.getMerchant(mpl.TestData.ACCESS_KEY_MPL_DEFAULT, "merchantId"));
        request.put("tenant", mpl.TestData.TENANT_MEESHO);
        request.put("version", TestData.RISK_GET_DECISION_V2);

        RiskGetDecisionStep.RiskGetDecisionMethod(request);

        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertTrue(RiskGetDecisionStep.riskGetDecisionPojo.limit > 0);
        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.cycleLimit.toString(),
                String.valueOf(Double.valueOf(RiskGetDecisionStep.riskGetDecisionPojo.limit)*5));
        Assert.assertEquals(RiskGetDecisionStep.riskGetDecisionPojo.termLimit.toString(),
                String.valueOf(Double.valueOf(RiskGetDecisionStep.riskGetDecisionPojo.limit)*5*12*5));
    }

    @Description("")
    @Feature("Risk_MPL")
    @Test(priority = 15, groups = {"risk", "getDecision", "regression", "sanity", "mpl_regression"})
    public void riskUserLimitNonTransactingValidUserMeesho() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", mpl.TestData.USER_MOBILE_VALID_4);
        request.put("isTransacting", "0");
        request.put("accessKey", mpl.TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("merchantId", TestData.getMerchant(mpl.TestData.ACCESS_KEY_MPL_DEFAULT, "merchantId"));
        request.put("tenant", mpl.TestData.TENANT_MEESHO);

        RiskUserLimitStep.RiskUserLimitMethod(request);

        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponse.getStatusCode(), 200);
        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).mobile , mpl.TestData.USER_MOBILE_VALID_4);
        Assert.assertTrue(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).limit > 0);
        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).cycleLimit.toString(),
                String.valueOf(Double.valueOf(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).limit)*5));
        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).termLimit.toString(),
                String.valueOf(Double.valueOf(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).limit)*5*12*5));
    }

    @Description("")
    @Feature("Risk_MPL")
    @Test(priority = 16, groups = {"risk", "getDecision", "regression", "sanity", "mpl_regression"})
    public void riskUserLimitTransactingValidUserMeesho() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", mpl.TestData.USER_MOBILE_VALID_4);
        request.put("isTransacting", "1");
        request.put("accessKey", mpl.TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("merchantId", TestData.getMerchant(mpl.TestData.ACCESS_KEY_MPL_DEFAULT, "merchantId"));
        request.put("tenant", mpl.TestData.TENANT_MEESHO);

        RiskUserLimitStep.RiskUserLimitMethod(request);

        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponse.getStatusCode(), 200);
        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).mobile , mpl.TestData.USER_MOBILE_VALID_4);
        Assert.assertTrue(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).limit > 0);
        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).cycleLimit.toString(),
                String.valueOf(Double.valueOf(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).limit)*5));
        Assert.assertEquals(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).termLimit.toString(),
                String.valueOf(Double.valueOf(RiskUserLimitStep.riskGetDecisionResponseAsList.get(0).limit)*5*12*5));
    }

    @Description("Verify that the tentative limit is returned as expected for BNPL Meesho product for a valid user")
    @Feature("Risk_MPL")
    @Test(priority = 17, groups = {"risk", "getDecision", "regression", "sanity", "mpl_regression"})
    public void riskTentativeLimitPostMITCBNPLMeeshoProductValidUser() throws Exception {

        HashMap<String, Object> request = new HashMap<>();
        request.put("mobile", mpl.TestData.USER_MOBILE_VALID_4);
        request.put("products", TestData.PRODUCT_BNPL);
        request.put("merchantId", TestData.getMerchant(mpl.TestData.ACCESS_KEY_MPL_DEFAULT, "merchantId"));
        request.put("mitcYearValidity", TestData.MITC_YEAR_VALIDITY_DEFAULT);
        request.put("tenant", mpl.TestData.TENANT_MEESHO);

        RiskTentativeLimitPostMITCStep.RiskTentativeLimitPostMITCMethod(request);

        Assert.assertEquals(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.mobile, mpl.TestData.USER_MOBILE_VALID_4);
        Assert.assertTrue(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.limit > 0);
        Assert.assertEquals(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.cycleLimit.toString(),
                String.valueOf(Double.valueOf(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.limit)*5));
        Assert.assertEquals(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.termLimit.toString(),
                String.valueOf(Double.valueOf(RiskTentativeLimitPostMITCStep.riskTentativeLimitPostMITCPOJO.limit)*5*12*5));
    }
}
