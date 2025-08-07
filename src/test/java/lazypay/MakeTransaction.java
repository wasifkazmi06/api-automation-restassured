package lazypay;

import api.lazypay.transaction.*;
import constants.UtilConstants;
import lazypay.transactionFlow.GenerateSignature;
import lazypay.transactionFlow.GenerateToken;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.AutoDebitPayMIPojo;
import pojos.lazypay.transactionFlow.InitiatePojo;
import pojos.lazypay.transactionFlow.PayPojo;
import util.FindOrCreateUserOTP;
import util.ReadProperties;
import util.ReturnRandomTxnId;
import util.StringTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static lazypay.transactionFlow.GetOTPID.getAppOTPMethod;
import static lazypay.transactionFlow.GetOTPUsingID.getOTPByIDMethod;

@Slf4j
public class MakeTransaction {

    static public String MTX;
    static public String CTX;
    static public String txnRefNo;
    public static String signaturePay;
    public static String signatureInitiate;
    public static String signaturePayOTP;
    public static String authToken = null;
    public static InitiatePojo initiatePojo = new InitiatePojo();
    public static PayPojo payPojo = new PayPojo();
    public static AutoDebitPayMIPojo autoDebitPayMIPojo = new AutoDebitPayMIPojo();
    public static String mobile = System.getProperty("mobile");
    public static String email = System.getProperty("email");
    public static String merchantAccessKey = System.getProperty("merchantAccessKey");
    public static String amount = System.getProperty("amount");
    public static String paymentMode = System.getProperty("paymentMode");
    public static GenerateToken generateToken;
    private static String otp;
    public static String initiateVersion = System.getProperty("initiateVersion");
    public static String payVersion = System.getProperty("payVersion");
    public static String signaturePayAutoDebit;

    static PayV0 payV0;
    static PayV4 payV4;
    static PayV5 payV5;
    static InitiateV0 initiateV0;
    static InitiateV1 initiateV1;
    static InitiateV2 initiateV2;
    static InitiateV4 initiateV4;
    static InitiateV5 initiateV5;
    static AutoDebitMIV1 autoDebitMIV1;

    static {
        try {
            initiateV0 = new InitiateV0();
            initiateV1 = new InitiateV1();
            initiateV2 = new InitiateV2();
            initiateV4 = new InitiateV4();
            initiateV5 = new InitiateV5();
            autoDebitMIV1 = new AutoDebitMIV1();
            generateToken = new GenerateToken();
            payV0 = new PayV0();
            payV4 = new PayV4();
            payV5 = new PayV5();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MakeTransaction() {
    }

    @Test
    public static void makeLPTransaction() throws Exception {

        if (paymentMode.equals("OTP")) {
            makeLPOTPTransaction(mobile, email, initiateVersion, payVersion, merchantAccessKey, amount, CheckEligibility.subMerchantID, false);
        } else if (paymentMode.equals("AUTO_DEBIT")) {
            makeLPAutoDebitTransaction(mobile, email, initiateVersion, payVersion, merchantAccessKey, amount, CheckEligibility.subMerchantID, authToken);
        } else {
            log.error("Incorrect payment mode.");
        }
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    public static void makeLPAutoDebitTransaction(String mobile, String email, String initiateVersion, String payVersion, String merchant, String amount, String subMerchant, String authToken) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        signaturePayAutoDebit = GenerateSignature.generateSignature("merchantAccessKey=" + merchant + "&transactionId=" + MTX
                + "&amount=" + amount, merchant);

        String payRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", amount)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", mobile)
                .replace("email", "")
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        switch (payVersion) {
            case TransactionData.PAY_V0:

                if (authToken == null) {
                    authToken = "Bearer " + generateToken.generateToken(merchant, mobile, "", "", "");
                }

                headerDetails.put("Authorization", authToken);
                headerDetails.put("signature", signaturePayAutoDebit);

                payPojo = payV0.post(queryParamDetails, headerDetails, payRequest);

                break;
            case TransactionData.PAY_V4:

                if (authToken == null) {
                    authToken = "Bearer " + generateToken.generateToken(merchant, mobile, "", "", "");
                }

                headerDetails.put("Authorization", authToken);
                headerDetails.put("signature", signaturePayAutoDebit);

                payPojo = payV4.post(queryParamDetails, headerDetails, payRequest);

                break;
            case TransactionData.PAY_V5:

                if (authToken == null) {
                    makeLPOTPTransaction(mobile, email, initiateVersion, payVersion, merchant, amount, CheckEligibility.subMerchantID, false);
                    authToken = "Bearer " + payPojo.token.get("access_token");
                    RefundTransaction.refundTransactionMethod(merchant, payPojo.merchantOrderId, amount);
                }

                headerDetails.put("Authorization", authToken);
                headerDetails.put("signature", signaturePayAutoDebit);
                headerDetails.put("accessKey", merchant);

                JSONObject payRequestJson = new JSONObject(payRequest);
                payRequestJson.getJSONObject("customParams").put("subMerchantId", subMerchant);
                payRequestJson.getJSONObject("customParams").put("subMerchantTxnId", TransactionData.SUB_MERCHANT_TXN_ID);

                payPojo = payV5.post(queryParamDetails, headerDetails, payRequestJson.toString());

                break;
            default:
                log.error("Incorrect pay version!");
        }
    }

    public static void makeLPOTPTransaction(String mobile, String email, String initiateVersion, String payVersion, String merchant, String amount, String subMerchant, boolean is2FA) throws Exception {

        int retryCount = 0;

        initiateLPOTPTransaction(mobile, email, initiateVersion, merchant, amount, subMerchant);

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signaturePayOTP = GenerateSignature.generateSignature("merchantAccessKey=" + merchant + "&txnRefNo=" + initiatePojo.txnRefNo, merchant);

        headerDetails.put("signature", signaturePayOTP);

        try {
            do {
                if(System.getProperty("env").equals("QA")){
                    otp = "1234";
                }
                else if (is2FA) {
                    Thread.sleep(2000);
                    String OTPID = getAppOTPMethod(null, initiatePojo.lpTxnId);
                    otp = getOTPByIDMethod(ReadProperties.testConfigMap.get(UtilConstants.TENANT_ID).toString(), OTPID);
                } else {
                    otp = FindOrCreateUserOTP.getLatestOTP(mobile);
                }
                String payRequest = new StringTemplate(LazypayConstants.PAY_REQUEST)
                        .replace("txnRefNo", initiatePojo.txnRefNo)
                        .replace("otp", otp)
                        .toString();
                switch (payVersion) {
                    case TransactionData.PAY_V0:

                        payPojo = payV0.post(queryParamDetails, headerDetails, payRequest);

                        break;
                    case TransactionData.PAY_V4:

                        payPojo = payV4.post(queryParamDetails, headerDetails, payRequest);

                        break;
                    case TransactionData.PAY_V5:

                        payPojo = payV5.post(queryParamDetails, headerDetails, payRequest);

                        break;
                    default:
                        log.error("Incorrect pay version!");
                }
                retryCount++;
            } while (payPojo.status.equals("401") && payPojo.errorCode.equals("LP_INCORRECT_OTP") && retryCount <= 1);
        } catch (NullPointerException e) {
        }
    }

    public static void initiateLPOTPTransaction(String mobile, String email, String initiateVersion, String merchant, String amount, String subMerchant) throws Exception {

        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        CTX = ReturnRandomTxnId.returnTxnIDMethod("CTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signatureInitiate = GenerateSignature.generateSignature("merchantAccessKey=" + merchant + "&transactionId=" + MTX + "&amount=" + amount, merchant);

        headerDetails.put("signature", signatureInitiate);
        headerDetails.put("accessKey", merchant);

        String initiateRequest = new StringTemplate(LazypayConstants.INITIATE_PAY_REQUEST)
                .replace("amount", amount)
                .replace("mobile", mobile)
                .replace("email", email)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mtx", MTX)
                .replace("ctx", CTX)
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("returnUrl", TransactionData.RETURN_URL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("isRedirectFlow", "false")
                .toString();

        switch (initiateVersion) {
            case TransactionData.INITIATE_PAY_V0:

                initiatePojo = initiateV0.post(queryParamDetails, headerDetails, initiateRequest);

                break;
            case TransactionData.INITIATE_PAY_V1:

                initiatePojo = initiateV1.post(queryParamDetails, headerDetails, initiateRequest);

                break;
            case TransactionData.INITIATE_PAY_V2:

                initiatePojo = initiateV2.post(queryParamDetails, headerDetails, initiateRequest);

                break;
            case TransactionData.INITIATE_PAY_V4:

                initiatePojo = initiateV4.post(queryParamDetails, headerDetails, initiateRequest);

                break;
            case TransactionData.INITIATE_PAY_V5:

                JSONObject initiateRequestJson = new JSONObject(initiateRequest);
                initiateRequestJson.getJSONObject("customParams").put("subMerchantId", subMerchant);
                initiateRequestJson.getJSONObject("customParams").put("subMerchantTxnId", TransactionData.SUB_MERCHANT_TXN_ID);

                initiatePojo = initiateV5.post(queryParamDetails, headerDetails, initiateRequestJson.toString());

                break;
            default:
                log.error("Incorrect initiate version!");
                break;
        }
    }

    public static void initiateLPOTPTransactionHash(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest =  requestBuilder(request);

        if(finalRequest.get("MTX").equals("")) {
            MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        }
        else {
            MTX = finalRequest.get("MTX");
        }

        if(finalRequest.get("sendCTX").equals("0")) {
           CTX = "";
        }
        else {
            CTX = ReturnRandomTxnId.returnTxnIDMethod("CTX");
        }

        if(finalRequest.get("signature").equals("")) {
            signatureInitiate = GenerateSignature.generateSignature("merchantAccessKey=" + finalRequest.get("accessKey") + "&transactionId=" + MTX +
                    "&amount=" + finalRequest.get("amount"), finalRequest.get("accessKey"));
            headerDetails.put("signature", signatureInitiate);
        }else {
            headerDetails.put("signature", finalRequest.get("signature"));
        }

        headerDetails.put("accessKey", finalRequest.get("accessKey"));
        headerDetails.put("deviceId", finalRequest.get("deviceId"));
        headerDetails.put("platform", finalRequest.get("platform"));


        String initiateRequest = new StringTemplate(LazypayConstants.INITIATE_PAY_REQUEST)
                .replace("amount", finalRequest.get("amount"))
                .replace("mobile", finalRequest.get("mobile"))
                .replace("email", finalRequest.get("email"))
                .replace("currency", finalRequest.get("currency"))
                .replace("mtx", MTX)
                .replace("ctx", CTX)
                .replace("source", finalRequest.get("source"))
                .replace("returnUrl", finalRequest.get("returnUrl"))
                .replace("firstName", finalRequest.get("firstName"))
                .replace("lastName", finalRequest.get("lastName"))
                .replace("isRedirectFlow", finalRequest.get("isRedirectFlow"))
                .toString();

        int runCount = 0;
        try {
            do {
                switch (finalRequest.get("initiateVersion")) {
                    case TransactionData.INITIATE_PAY_V0:

                        initiatePojo = initiateV0.post(queryParamDetails, headerDetails, initiateRequest);
                        runCount++;

                        break;
                    case TransactionData.INITIATE_PAY_V1:

                        initiatePojo = initiateV1.post(queryParamDetails, headerDetails, initiateRequest);
                        runCount++;

                        break;
                    case TransactionData.INITIATE_PAY_V2:

                        initiatePojo = initiateV2.post(queryParamDetails, headerDetails, initiateRequest);
                        runCount++;

                        break;
                    case TransactionData.INITIATE_PAY_V4:

                        initiatePojo = initiateV4.post(queryParamDetails, headerDetails, initiateRequest);
                        runCount++;

                        break;
                    case TransactionData.INITIATE_PAY_V5:

                        JSONObject initiateRequestJson = new JSONObject(initiateRequest);

                        if (!finalRequest.get("sendSubMerchantId").equals("false")) {
                            initiateRequestJson.getJSONObject("customParams").put("subMerchantId", finalRequest.get("subMerchantId"));
                        }

                        if (!finalRequest.get("sendSubMerchantTxnId").equals("false")) {
                            initiateRequestJson.getJSONObject("customParams").put("subMerchantTxnId", finalRequest.get("subMerchantTxnId"));
                        }

                        initiatePojo = initiateV5.post(queryParamDetails, headerDetails, initiateRequestJson.toString());
                        runCount++;

                        break;
                    default:
                        log.error("Incorrect initiate version!");
                }
            } while (initiatePojo.errorCode.equals("LP_GENERIC_ERROR") && runCount < 3);
        }catch  (NullPointerException e) {
            }
    }


    public static void makeLPOTPTransactionHash(HashMap<String, String> request, int maxRetriesAllowed) throws Exception {

        int retryCount = 0;
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest = requestBuilder(request);

        initiateLPOTPTransactionHash(finalRequest);
        headerDetails.put("deviceId", finalRequest.get("deviceId"));
        headerDetails.put("platform", finalRequest.get("platform"));

        if(finalRequest.get("signature").equals("")){
            signaturePay = GenerateSignature.generateSignature("merchantAccessKey=" + finalRequest.get("accessKey") + "&txnRefNo=" +
                    initiatePojo.txnRefNo, finalRequest.get("accessKey"));
            headerDetails.put("signature", signaturePay);
        }else {
            headerDetails.put("signature", finalRequest.get("signature"));
        }

        if(!finalRequest.get("txnRefNo").equals("")){
            if(finalRequest.get("txnRefNo").equals(" ")){
                txnRefNo="";
            }else {
                txnRefNo = finalRequest.get("txnRefNo");
            }
        }else{
            txnRefNo=initiatePojo.txnRefNo;
        }

        try {
            do {
                if (System.getProperty("env").equals("QA")) {
                    otp = "1234";
                } else if (!finalRequest.get("otp").equals("")) {
                    if (finalRequest.get("otp").equals(" ")) {
                        otp = "";
                    } else
                        otp = finalRequest.get("otp");
                } else {
                    if (finalRequest.get("is2FA").equals("true")) {
                        Thread.sleep(500);
                        String OTPID = getAppOTPMethod(null, initiatePojo.lpTxnId);
                        otp = getOTPByIDMethod(ReadProperties.testConfigMap.get(UtilConstants.TENANT_ID).toString(), OTPID);
                    } else {
                        otp = FindOrCreateUserOTP.getLatestOTP(finalRequest.get("mobile"));
                    }
                }

                String payRequest = new StringTemplate(LazypayConstants.PAY_REQUEST)
                        .replace("txnRefNo", txnRefNo)
                        .replace("otp", otp)
                        .toString();

                int runCount = 0;

                do {
                    switch (finalRequest.get("payVersion")) {
                        case TransactionData.PAY_V0:

                            payPojo = payV0.post(queryParamDetails, headerDetails, payRequest);
                            runCount++;

                            break;
                        case TransactionData.PAY_V4:

                            payPojo = payV4.post(queryParamDetails, headerDetails, payRequest);
                            runCount++;

                            break;
                        case TransactionData.PAY_V5:

                            payPojo = payV5.post(queryParamDetails, headerDetails, payRequest);
                            runCount++;

                            break;
                        default:
                            log.error("Incorrect pay version!");
                    }
                    retryCount++;
                }while(payPojo.errorCode.equals("LP_GENERIC_ERROR") && runCount>3);
            }while (payPojo.status.equals("401") && payPojo.errorCode.equals("LP_INCORRECT_OTP") && retryCount <= maxRetriesAllowed);
        } catch (NullPointerException e) {
        }
    }

    public static void makeLPAutoDebitTransactionHash(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        HashMap<String, String> finalRequest =  requestBuilder(request);
        headerDetails.put("deviceId", finalRequest.get("deviceId"));
        headerDetails.put("platform", finalRequest.get("platform"));

        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        signaturePayAutoDebit = GenerateSignature.generateSignature("merchantAccessKey=" + finalRequest.get("accessKey") + "&transactionId=" + MTX
                + "&amount=" + finalRequest.get("amount"), finalRequest.get("accessKey"));

        String payRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", finalRequest.get("amount"))
                .replace("currency", finalRequest.get("currency"))
                .replace("mobile", finalRequest.get("mobile"))
                .replace("email", finalRequest.get("email"))
                .replace("firstName",finalRequest.get("firstName"))
                .replace("lastName", finalRequest.get("lastName"))
                .replace("merchantTxnId", MTX)
                .toString();

        switch (finalRequest.get("payVersion")) {
            case TransactionData.PAY_V0:

                if (finalRequest.get("authToken").equals("")){
                    authToken = "Bearer " + generateToken.generateToken(finalRequest.get("accessKey"), finalRequest.get("mobile"),
                            finalRequest.get("email"), finalRequest.get("firstName"), finalRequest.get("lastName"));
                }
                else {
                    authToken=finalRequest.get("authToken");
                }

                headerDetails.put("Authorization", authToken);
                headerDetails.put("signature", signaturePayAutoDebit);


                payPojo = payV0.post(queryParamDetails, headerDetails, payRequest);

                break;
            case TransactionData.PAY_V4:

                if (finalRequest.get("authToken").equals("")){
                    authToken = "Bearer " + generateToken.generateToken(finalRequest.get("accessKey"), finalRequest.get("mobile"),
                            finalRequest.get("email"), finalRequest.get("firstName"), finalRequest.get("lastName"));
                }
                else {
                    authToken=finalRequest.get("authToken");
                }

                headerDetails.put("Authorization", authToken);
                headerDetails.put("signature", signaturePayAutoDebit);

                payPojo = payV4.post(queryParamDetails, headerDetails, payRequest);

                break;
            case TransactionData.PAY_V5:

                if (finalRequest.get("authToken").equals("")){
                    makeLPOTPTransactionHash(finalRequest, 1);
                    authToken = "Bearer " + payPojo.token.get("access_token");
                }else{
                        authToken=finalRequest.get("authToken");
                }

                headerDetails.put("Authorization", authToken);
                headerDetails.put("signature", signaturePayAutoDebit);
                headerDetails.put("accessKey", finalRequest.get("accessKey"));


                JSONObject payRequestJson = new JSONObject(payRequest);
                payRequestJson.getJSONObject("customParams").put("subMerchantId", finalRequest.get("subMerchantId"));
                payRequestJson.getJSONObject("customParams").put("subMerchantTxnId", TransactionData.SUB_MERCHANT_TXN_ID);

                payPojo = payV5.post(queryParamDetails, headerDetails, payRequestJson.toString());

                break;
            default:
                log.error("Incorrect pay version!");
        }
    }

    public static void makeAutoDebitMITransactionHash(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> finalRequest =  requestBuilder(request);

        if(finalRequest.get("MTX").equals("")) {
            MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        }
        else {
            MTX = finalRequest.get("MTX");
        }
        headerDetails.put("accessKey", finalRequest.get("accessKey"));
        String autoDebitPayMIRequest = new StringTemplate(LazypayConstants.autoDebitV1Pay)
                .replace("amount", finalRequest.get("amount"))
                .replace("mobile", finalRequest.get("mobile"))
                .replace("currency", finalRequest.get("currency"))
                .replace("mtx", MTX)
                .replace("source", finalRequest.get("source"))
                .toString();

        if(!finalRequest.get("subMerchantId").isEmpty())
        {

            JSONObject autoDebitPayV1RequestJson = new JSONObject(autoDebitPayMIRequest);
            autoDebitPayV1RequestJson.getJSONObject("customParams").put("subMerchantId", finalRequest.get("subMerchantId"));
            autoDebitPayV1RequestJson.getJSONObject("customParams").put("subMerchantTxnId", finalRequest.get("subMerchantTxnId"));
            autoDebitPayMIPojo = autoDebitMIV1.post(queryParamDetails,headerDetails,autoDebitPayV1RequestJson.toString());
        }
        else
        {
            autoDebitPayMIPojo = autoDebitMIV1.post(queryParamDetails,headerDetails, autoDebitPayMIRequest);
        }
    }

    private static HashMap requestBuilder(HashMap<String, String> request) {

        request.put("mobile", request.get("mobile"));
        request.put("email", request.get("email"));
        request.put("firstName", request.get("firstName"));
        request.put("lastName", request.get("lastName"));
        request.put("amount", request.get("amount"));
        request.put("currency", request.get("currency"));
        request.put("accessKey", request.get("accessKey"));
        request.put("subMerchantId", request.get("subMerchantId"));
        request.put("sendSubMerchantId", request.get("sendSubMerchantId"));
        request.put("subMerchantTxnId", request.get("subMerchantTxnId"));
        request.put("sendSubMerchantTxnId", request.get("sendSubMerchantTxnId"));
        request.put("source", request.get("source"));
        request.put("version", request.get("version"));
        request.put("zip", request.get("zip"));
        request.put("country", request.get("country"));
        request.put("city", request.get("city"));
        request.put("street1", request.get("street1"));
        request.put("street2", request.get("street2"));
        request.put("deviceId", request.get("deviceId"));
        request.put("platform", request.get("platform"));
        request.put("signature", request.get("signature"));
        request.put("customParamMaxLimit", request.get("customParamMaxLimit"));
        request.put("customParamMaxLength", request.get("customParamMaxLength"));
        request.put("noCustomParam", request.get("noCustomParam"));
        request.put("returnUrl", request.get("returnUrl"));
        request.put("is2FA", request.get("is2FA"));
        request.put("txnRefNo", request.get("txnRefNo"));
        request.put("otp", request.get("otp"));
        request.put("authToken", request.get("authToken"));
        request.put("MTX", request.get("MTX"));
        request.put("sendCTX", request.get("sendCTX"));
        request.put("isRedirectFlow", request.get("isRedirectFlow"));

        List keys = new ArrayList(request.keySet());

        for (int i = 0; i < keys.size(); i++) {
            if (request.get(keys.get(i)) == null) {
                request.put(keys.get(i).toString(), "");
            }
        }

        if(request.get("currency").equals("")) {
            request.put("currency", TransactionData.CURRENCY);
        }

        if(request.get("isRedirectFlow").equals("")) {
            request.put("isRedirectFlow", "false");
        }

        if(request.get("subMerchantTxnId").equals("")) {
            request.put("subMerchantTxnId", TransactionData.SUB_MERCHANT_TXN_ID);
        }

        return request;
    }
}