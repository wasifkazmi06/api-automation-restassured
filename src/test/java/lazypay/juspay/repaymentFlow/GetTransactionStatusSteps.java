package lazypay.juspay.repaymentFlow;

import api.lazypay.juspay.repayment.GetTransactionStatus;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.Step;
import lazypay.LazypayConstants;
import mbe.authentication.AuthTestData;
import pojos.lazypay.juspay.repaymentFlow.GetTransactionStatusPojo;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;
import static mbe.authentication.APPLoginUser.OauthToken;


public class GetTransactionStatusSteps {

    public static GetTransactionStatusPojo getTransactionStatusPojo = new GetTransactionStatusPojo();
    public static GetTransactionStatus getTransactionStatus;
    public static String repaymentActionText = "";

    static {
        try {
            getTransactionStatus = new GetTransactionStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GetTransactionStatusSteps() throws Exception {
    }

    @Step
    public static void getTransactionStatusMBE () throws Exception {
        int count = 0;
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("isLastAttempt","false");
        queryParamDetails.put("mock", "true");


        headerDetails.put("authorization", OauthToken);
        headerDetails.put("PF-App-Lock-Token", AuthTestData.appLockToken);
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", AuthTestData.deviceBindingId);
        headerDetails.put("PF-Location", AuthTestData.location);
        headerDetails.put("PF-Product-Type", AuthTestData.productBNPL);

        String getTransactionStatusRequest = new StringTemplate(LazypayConstants.GET_TRANSACTION_STATUS_REQUEST)
                .replace("orderId", InitiateRepayProcessSteps.orderId)
                .replace("optInState", "DESELECT")
                .replace("userPolicyDetailsId","8828")
                .replace("txnId",InitiateRepayProcessSteps.transactionId)
                .replace("isCancelledTxn","false")
                .toString();

        while((repaymentActionText.equalsIgnoreCase("TRANSACTION_POLL_STATUS") || repaymentActionText.equals(""))
        && count<2) {
            getTransactionStatusPojo = getTransactionStatus.post(queryParamDetails, headerDetails, getTransactionStatusRequest);
            JsonNode repaymentAction = getTransactionStatusPojo.getActions().get("default").get("action");
            repaymentActionText = repaymentAction.asText();
            Thread.sleep(4000);
            count ++;
        }
    }

}
