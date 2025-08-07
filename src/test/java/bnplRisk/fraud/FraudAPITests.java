package bnplRisk.fraud;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.bnplRisk.Product;
import bnplRisk.TestData;

import java.util.HashMap;

public class FraudAPITests {

    @Description("Verify that fraud give decision true for a valid user with non-blacklisted pincode using the fraud get decision API")
    @Feature("Fraud")
    @Test(priority = 1, groups = {"fraud", "getDecision", "regression", "sanity"})
    public void fraudGetDecisionValidUserCOFProduct() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID1);
        request.put("amount", TestData.TRANSACTION_AMOUNT);
        request.put("merchantAccessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("integrationMethod", TransactionData.SOURCE_CITRUS_SDK);
        request.put("requestApi", "ELIGIBILITY_"+TransactionData.ELIGIBILITY_V7);
        request.put("products", TestData.PRODUCT_COF);

        FraudGetDecisionStep.fraudGetDecisionMethod(request);

        Assert.assertEquals(FraudGetDecisionStep.fraudGetDecisionPojo.decision, Integer.valueOf("1"));
        Assert.assertTrue(FraudGetDecisionStep.fraudGetDecisionPojo.productResponseMap.get(Product.COF).decision);
    }

}
