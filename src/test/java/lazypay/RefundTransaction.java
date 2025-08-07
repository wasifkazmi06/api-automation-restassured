package lazypay;

import api.lazypay.transaction.MerchantRefund;
import lazypay.transactionFlow.GenerateSignature;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.MerchantRefundPojo;
import util.StringTemplate;
import java.util.HashMap;

public class RefundTransaction {

    static String signature = null;
    static MerchantRefundPojo merchantRefundPojo = new MerchantRefundPojo();
    static MerchantRefund merchantRefund;
    static String merchantRefundRequest=null;
    public static double refundAmount = Double.valueOf(System.getProperty("refundAmount"));
    public static String merchantTxnId = System.getProperty("merchantTxnId");


    static {
        try {
            merchantRefund = new MerchantRefund();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RefundTransaction()  {
    }

    @Test
    public static void refundTransaction() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        MakeTransaction.makeLPOTPTransaction(MakeTransaction.mobile, "", "v0", "v0",
                MakeTransaction.merchantAccessKey, MakeTransaction.amount, "", false);

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + MakeTransaction.merchantAccessKey + "&merchantTxnId="
                +  MakeTransaction.payPojo.merchantOrderId + "&amount=" + refundAmount, MakeTransaction.merchantAccessKey);

        headerDetails.put("signature", signature);
headerDetails.put("accessKey", MakeTransaction.merchantAccessKey);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId",MakeTransaction.payPojo.merchantOrderId)
                .replace("value", String.valueOf(refundAmount))
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status,  "REFUND_SUCCESS", "Check that refund amount is equal to the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful",  "Check that refund amount is equal to the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,  "Check that refund amount is equal to the transaction amount");
        Assert.assertEquals(String.valueOf(merchantRefundPojo.amount), String.valueOf(refundAmount),  "Check that refund amount is equal to the transaction amount");

    }

    @Test
    public static void refundTransactionOnly() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + MakeTransaction.merchantAccessKey + "&merchantTxnId="
                +  merchantTxnId + "&amount=" + refundAmount, MakeTransaction.merchantAccessKey);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", MakeTransaction.merchantAccessKey);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId", merchantTxnId)
                .replace("value", String.valueOf(refundAmount))
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status,  "REFUND_SUCCESS", "Check that refund amount is equal to the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful",  "Check that refund amount is equal to the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,  "Check that refund amount is equal to the transaction amount");
        Assert.assertEquals(String.valueOf(merchantRefundPojo.amount), String.valueOf(refundAmount),  "Check that refund amount is equal to the transaction amount");
    }

    public static void refundTransactionMethod(String merchantAccessKey, String merchantTxnId, String refundAmount) throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + merchantAccessKey + "&merchantTxnId="
                +  merchantTxnId + "&amount=" + refundAmount, merchantAccessKey);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", merchantAccessKey);

        merchantRefundRequest= new StringTemplate(LazypayConstants.MERCHANT_OTP)
                .replace("merchantTxnId", merchantTxnId)
                .replace("value", refundAmount)
                .replace("currency", TransactionData.CURRENCY)
                .replace("refundTxnId", "")
                .replace("isSkipVelocityCheck", "")
                .toString();

        merchantRefundPojo = merchantRefund.post(queryParamDetails, headerDetails, merchantRefundRequest);
        Assert.assertEquals(merchantRefundPojo.status,  "REFUND_SUCCESS", "Check that refund amount is equal to the transaction amount");
        Assert.assertEquals(merchantRefundPojo.respMessage, "Refund is successful",  "Check that refund amount is equal to the transaction amount");
        Assert.assertNotNull(merchantRefundPojo.lpTxnId,  "Check that refund amount is equal to the transaction amount");
        Assert.assertEquals(String.valueOf(merchantRefundPojo.amount), String.valueOf(refundAmount),  "Check that refund amount is equal to the transaction amount");
    }
}