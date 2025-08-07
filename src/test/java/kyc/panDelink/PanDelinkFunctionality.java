package kyc.panDelink;

import api.kyc.apis.InitiatePanDelink;
import api.lazypay.transaction.PayV0;
import kyc.ckyc.ApiRequests;
import kyc.ckyc.CommonMethodsImpl;
import kyc.enums.DocumentTypeEnum;
import lazypay.LazypayConstants;
import lazypay.transactionFlow.GenerateSignature;
import lazypay.transactionFlow.GenerateToken;
import lazypay.transactionFlow.TransactionData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import pojos.kyc.apis.InitiatePanDelinkPojo;
import pojos.kyc.apis.UploadDocumentPojo;
import pojos.lazypay.transactionFlow.PayPojo;
import util.ReturnRandomTxnId;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PanDelinkFunctionality extends CommonMethodsImpl {

    public PanDelinkFunctionality() throws Exception {
    }

    InitiatePanDelink initiatepandelink = new InitiatePanDelink();
    JSONObject reqObject = ApiRequests.getPanDocRequest(DELINKED_PAN, "Kareeshma Sharif", "true");


    public static String signature;
    public static String authToken;
    static public String MTX;
    GenerateToken generateToken = new GenerateToken();
    PayV0 payV0 = new PayV0();
    public void uploadDocForFirstUser(String uuid) throws Exception {

        // Initiate KYC for first user
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(uuid, VALID_PRODUCT);
        Assert.assertNotNull(initiateKYCV9pojo.kycCaseId, "check the error message");
        log.info("Fetch the KycCaseId ::" + initiateKYCV9pojo.kycCaseId);

        //upload pan doc for the user
        UploadDocumentPojo uploadPanDocpojo = uploadDoc(uuid,initiateKYCV9pojo.kycCaseId, DocumentTypeEnum.PAN_DOC.toString(), reqObject.toString());
        Assert.assertEquals(uploadPanDocpojo.uploadStatus, "SUCCESS", "check the data");
        Assert.assertEquals(uploadPanDocpojo.message, "Document uploaded successfully", "check the data");

    }
    public String initiateKycANDUploadDoc(String uuid) throws Exception {

        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(uuid, VALID_PRODUCT);
        Assert.assertNotNull(initiateKYCV9pojo.kycCaseId, "check the error message");
        log.info("Fetch the KycCaseId ::" + initiateKYCV9pojo.kycCaseId);

        //upload pan doc for the user
        UploadDocumentPojo uploadPanDocpojo = uploadDoc(uuid,initiateKYCV9pojo.kycCaseId, DocumentTypeEnum.PAN_DOC.toString(), reqObject.toString());
        Assert.assertEquals(uploadPanDocpojo.errorCode, "PAN_ALREADY_REGISTERED", "Failed to check Pan already linked to other mobile");
        return initiateKYCV9pojo.kycCaseId;
    }
    public InitiatePanDelinkPojo initiatePanDelink(String uuid, String kyccaseId, String panNumber) throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, Object> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        log.info("headers print " + headerDetails);
        queryParamDetails.put("uuid", uuid);
        queryParamDetails.put("kycCaseId", kyccaseId);
        queryParamDetails.put("panNumber", panNumber);
        InitiatePanDelinkPojo pandDelinkResponse = initiatepandelink.postWithJsonHeader(headerDetails,queryParamDetails);
          return pandDelinkResponse;
    }

    public void PayWithTokenV0LP(String Mobile) throws Exception {

//               DeleteUser.deleteUserByMobile(TransactionData.USER_MOBILE_VALID10, "lp");

        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        authToken = "Bearer "+ generateToken.generateToken(TransactionData.ACCESS_KEY, Mobile, "","","");
        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", Mobile)
                .replace("email", "")
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertNotNull(payPojo.transactionId, "verify that user is valid and transaction data is correct");
        Assert.assertNotNull(payPojo.amount);
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }
}
