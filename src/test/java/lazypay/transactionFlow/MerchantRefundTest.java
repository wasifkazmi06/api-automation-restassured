package lazypay.transactionFlow;

import api.lazypay.transaction.MerchantRefund;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.LazypayConstants;
import lazypay.MakeTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.MerchantRefundPojo;
import pojos.lazypay.transactionFlow.getTransactionHistory.Transaction;
import util.ReturnRandomTxnId;
import util.StringTemplate;
import java.text.DecimalFormat;
import java.util.HashMap;

public class MerchantRefundTest {

    String signature = null;
    MerchantRefundPojo merchantRefundPojo = new MerchantRefundPojo();
    MerchantRefund merchantRefund = new MerchantRefund();
    Double refundAmount=null;
    String merchantOrderId=null;
    static final DecimalFormat df = new DecimalFormat("0.00");
    String merchantRefundRequest=null;

    public MerchantRefundTest() throws Exception {
    }

    @Description("To verify that a merchant can completely refund a sale transaction")
    @Feature(value = "Merchant Refund")
    @Test(priority = 1, groups = {"transaction", "regression", "sanity"})
    public void merchantRefundFullAmount() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status,  "REFUND_SUCCESS", "Check that refund amount is equal to the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful",  "Check that refund amount is equal to the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,  "Check that refund amount is equal to the transaction amount");
        Assert.assertEquals(merchantRefundPojo.amount, Double.valueOf(TransactionData.TRANSACTION_AMOUNT),  "Check that refund amount is equal to the transaction amount");

    }

    @Description("To verify that a merchant can partially refund a sale transaction")
    @Feature(value = "Merchant Refund")
    @Test(priority = 2, groups = {"transaction", "regression", "sanity"})
    public void merchantRefundPartialAmount() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();

        refundAmount = Double.valueOf(df.format(Double.valueOf(TransactionData.TRANSACTION_AMOUNT)/2));

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + refundAmount, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", refundAmount.toString())
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "REFUND_SUCCESS", "Check that refund amount is half of the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful", "Check that refund amount is half of the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,"Check that refund amount is half of the transaction amount");
        Assert.assertEquals(merchantRefundPojo.amount, refundAmount,  "Check that refund amount is half of the transaction amount");

    }

    @Description("To verify that a merchant should not be able to refund an amount greater than the sale transaction's amount")
    @Feature(value = "Merchant Refund")
    @Test(priority = 3, groups = {"transaction", "regression", "sanity"})
    public void merchantRefundFullGreaterThanSaleAmount() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();

        refundAmount = Double.sum(Double.valueOf(TransactionData.TRANSACTION_AMOUNT),1);

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + refundAmount, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", refundAmount.toString())
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "400", "Check that the amount should be greater than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Refund amount seems to be greater than the sale transaction amount", "Check that the amount should be greater than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_REFUND_GT_SALE", "Check that the amount should be greater than the transaction amount");

    }

    @Description("To verify that a merchant should not be able to refund an amount greater than the sale transaction's amount when partially refunded multiple times")
    @Feature(value = "Merchant Refund")
    @Test(priority = 4, groups = {"transaction", "regression", "sanity"})
    public void merchantRefundPartialGreaterThanSaleAmount() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();

        refundAmount = Double.valueOf(df.format(Double.valueOf(Double.valueOf(TransactionData.TRANSACTION_AMOUNT)/2)));

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + refundAmount, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", refundAmount.toString())
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "true")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);

        Assert.assertEquals(merchantRefundPojo.status, "REFUND_SUCCESS", "Check that refund amount is half of the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful", "Check that refund amount is half of the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,"Check that refund amount is half of the transaction amount");
        Assert.assertEquals(merchantRefundPojo.amount, refundAmount,  "Check that refund amount is half of the transaction amount");

        refundAmount = Double.valueOf(df.format(refundAmount-0.1));

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + refundAmount, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", refundAmount.toString())
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "true")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);

        Assert.assertEquals(merchantRefundPojo.status, "REFUND_SUCCESS", "Check that refund amount is less than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful", "Check that refund amount is less than the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,"Check that refund amount is less than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.amount, refundAmount,  "Check that refund amount is less than the transaction amount");

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + refundAmount, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", refundAmount.toString())
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "true")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);

        Assert.assertEquals(merchantRefundPojo.status, "400", "Check that the amount should be greater than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Refund amount seems to be greater than the sale transaction amount", "Check that the amount should be greater than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_REFUND_GT_SALE", "Check that the amount should be greater than the transaction amount");

    }


    @Description("To verify that a merchant can completely refund a sale transaction by passing the RefundTxnId")
    @Feature(value = "Merchant Refund")
    @Test(priority = 5, groups = {"transaction", "regression", "sanity"})
    public void merchantRefundFullWithRefundTxnId() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "REFUND_SUCCESS", "Check that refund amount is less than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful", "Check that refund amount is less than the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,"Check that refund amount is less than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.amount, Double.valueOf(TransactionData.TRANSACTION_AMOUNT),  "Check that refund amount is less than the transaction amount");

    }


    @Description("To verify that a merchant can partially refund a sale transaction by passing the RefundTxnId")
    @Feature(value = "Merchant Refund")
    @Test(priority = 6, groups = {"transaction", "regression"})
    public void merchantRefundPartialWithRefundTxnId() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();
        refundAmount = Double.valueOf(df.format(Double.valueOf(TransactionData.TRANSACTION_AMOUNT)/2));

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + refundAmount, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", refundAmount.toString())
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", ReturnRandomTxnId.returnTxnIDMethod("RTX"))
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "REFUND_SUCCESS", "Check that refund amount is less than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful", "Check that refund amount is less than the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,"Check that refund amount is less than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.amount, refundAmount,  "Check that refund amount is less than the transaction amount");

    }

    @Description("To verify that a merchant cannot use the same RefundTxnId for multiple refunds")
    @Feature(value = "Merchant Refund")
    @Test(priority = 7, groups = {"transaction", "regression", "sanity"})
    public void merchantRefundDuplicateRefundTxnId() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();

        refundAmount = Double.valueOf(df.format(Double.valueOf(Double.valueOf(TransactionData.TRANSACTION_AMOUNT)/2)));

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + refundAmount, TransactionData.ACCESS_KEY);

        String RTX = ReturnRandomTxnId.returnTxnIDMethod("RTX");
        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", refundAmount.toString())
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", RTX)
                .replace("isSkipVelocityCheck", "true")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);

        Assert.assertEquals(merchantRefundPojo.status, "REFUND_SUCCESS", "Check that refund amount is less than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful", "Check that refund amount is less than the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,"Check that refund amount is less than the transaction amount");
        Assert.assertEquals(merchantRefundPojo.amount, refundAmount,  "Check that refund amount is less than the transaction amount");

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);

        Assert.assertEquals(merchantRefundPojo.status, "400", "Check that the same refundTxnId is being used");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_DUPLICATE_REFUND", "Check that the same refundTxnId is being used");
        Assert.assertEquals(merchantRefundPojo.message, "Duplicate Refund request", "Check that the same refundTxnId is being used");

    }

    @Description("To verify that a merchant should not be allowed to hit 2 consecutive refund request for the same sale transaction")
    @Feature(value = "Merchant Refund")
    @Test(priority = 8, groups = {"transaction", "regression"})
    public void merchantRefundVelocityCheck() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();

        refundAmount = Double.valueOf(df.format(Double.valueOf(Double.valueOf(TransactionData.TRANSACTION_AMOUNT)/2)));

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + refundAmount, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", refundAmount.toString())
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);


        Assert.assertEquals(merchantRefundPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! We see something unusual. Hence we are not allowing this transaction via LazyPay", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_VELOCITY_CHECK_FAILED", "Failure reason could be different else check if the validation still exist in code");

    }

    @Description("To verify that a merchant should not be able to refund if an incorrect merchantOrderId is passed in the request")
    @Feature(value = "Merchant Refund")
    @Test(priority = 9, groups = {"transaction", "regression"})
    public void merchantRefundIncorrectMTX() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        merchantOrderId = "MTXIncorrect1234567890";

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                +  merchantOrderId + "&amount=" + TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId", merchantOrderId)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! No corresponding transaction could be found", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_TXN_NOT_FOUND", "Failure reason could be different else check if the validation still exist in code");

    }

    @Description("To verify that a merchant should not be able to refund if negative amount is passed in the request")
    @Feature(value = "Merchant Refund")
    @Test(priority = 10, groups = {"transaction", "regression"})
    public void merchantRefundNegativeAmount() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + "-12", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", "-12")
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_INVALID_REQUEST", "Failure reason could be different else check if the validation still exist in code");

    }

    @Description("To verify that a merchant should not be able to refund if zero amount is passed in the request")
    @Feature(value = "Merchant Refund")
    @Test(priority = 11, groups = {"transaction", "regression"})
    public void merchantRefundZeroAmount() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + "0.00", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", "0.00")
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_INVALID_REQUEST", "Failure reason could be different else check if the validation still exist in code");

    }


    @Description("To verify that a merchant should not be able to refund an incorrect signature is passed in the request header")
    @Feature(value = "Merchant Refund")
    @Test(priority = 12, groups = {"transaction", "regression"})
    public void merchantRefundInvalidSignature() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = "InvalidSignature";

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId", "MTXTestSample1234567890")
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_SIGNATURE_MISMATCH", "Failure reason could be different else check if the validation still exist in code");

    }

    @Description("To verify that a merchant should not be able to refund if merchantTxnId is not passed in the request")
    @Feature(value = "Merchant Refund")
    @Test(priority = 13, groups = {"transaction", "regression"})
    public void merchantRefundMandatoryValidationOnMTX() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                 + "" + "&amount=" + TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId", "")
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.fieldErrors.get(0).field, "merchantTxnId", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.fieldErrors.get(0).message, "may not be empty", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify that a merchant should not be able to refund if amount is not passed in the request")
    @Feature(value = "Merchant Refund")
    @Test(priority = 14, groups = {"transaction", "regression"})
    public void merchantRefundMandatoryValidationOnAmount() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + "", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", "")
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "500", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_GENERIC_ERROR", "Failure reason could be different else check if the validation still exist in code");

    }

    @Description("To verify that a merchant should not be able to refund if an incorrect amount is passed in the request")
    @Feature(value = "Merchant Refund")
    @Test(priority = 15, groups = {"transaction", "regression"})
    public void merchantRefundInvalidAmount() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + "abc@#", TransactionData.ACCESS_KEY);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", "abc@#")
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "500", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_GENERIC_ERROR", "Failure reason could be different else check if the validation still exist in code");

    }

    @Description("To verify that a merchant should not be able to refund if merchant access key is not passed in the request")
    @Feature(value = "Merchant Refund")
    @Test(priority = 16, groups = {"transaction", "regression"})
    public void merchantRefundMandatoryValidationOnAccessKey() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + TransactionData.TRANSACTION_AMOUNT, "");

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", "");

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_INVALID_ACCESS_KEY", "Failure reason could be different else check if the validation still exist in code");

    }

    @Description("To verify that a merchant should not be able to refund if signature is not passed in the request header")
    @Feature(value = "Merchant Refund")
    @Test(priority = 17, groups = {"transaction", "regression"})
    public void merchantRefundMandatoryValidationOnSignature() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("signature", null);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_SIGNATURE_MISMATCH", "Failure reason could be different else check if the validation still exist in code");

    }

    @Description("To verify that a merchant should not be able to refund if an incorrect access key passed in the request header")
    @Feature(value = "Merchant Refund")
    @Test(priority = 18, groups = {"transaction", "regression"})
    public void merchantRefundIncorrectAccessKey() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        PayV0WithOTPTests.payV0V0LPSourceCitrusSDK();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&merchantTxnId="
                + MakeTransaction.MTX + "&amount=" + TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY_INVALID);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", TransactionData.ACCESS_KEY_INVALID);

        String merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.MTX)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status, "401", "Failure reason could be different else check if the validation still exist in code or else check if the a merchant has been created with the same access key");
        Assert.assertEquals(merchantRefundPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code or else check if the a merchant has been created with the same access key");
        Assert.assertEquals(merchantRefundPojo.errorCode, "LP_INVALID_ACCESS_KEY", "Failure reason could be different else check if the validation still exist in code or else check if the a merchant has been created with the same access key");

    }

}
