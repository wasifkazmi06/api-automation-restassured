package chatbot;

import api.chatbot.GetInfo;
import api.lazypay.juspay.repayment.RepayDetails;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeTransaction;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.chatbot.GetInfoPojo;
import pojos.lazypay.juspay.repaymentFlow.RepayDetailsPojo;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ChatBotTest {

    static GetInfo getInfo;
    static GetInfoPojo getInfoPojo;
    static RepayDetails repayDetails;
    static RepayDetailsPojo repayDetailsPojo;
    static String expectedUserOutStanding;

    static HashMap<String, String> chatAuthData = new HashMap<>();

    static {
        try {
            getInfo = new GetInfo();
            repayDetails = new RepayDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("To set up data requirement for Chatbot sanity suite")
    @Feature("UserDataSetUP")
    @Test(priority = 1, dataProvider = "user-mobile", dataProviderClass = ChatBotData.class, groups = {"sanity"})
    public void sanityDataSetUp(String mobile) throws Exception {

        ChatbotToken.setChatbotStartSessionData(mobile);

        chatAuthData.put("auth-token-"+mobile, ChatbotToken.startSessionPojo.getData().getOauthTokenResponse().getAuthToken());
        chatAuthData.put("user-id-"+mobile, ChatbotToken.startSessionPojo.getData().getChatbotUserId());

    }

    @Description("CTA=PRODUCTS: To validate the response for a valid CTA for correct ChatbotToken")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "CTA"})
    public static void validateUserWithValidCTA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.productCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

    }



    @Description("CTA=Invalid CTA: To validate the response for a Invalid CTA for correct ChatbotToken")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "CTA"})
    public static void validateUserWithInValidCTA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.invalidCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_100, "Status not 400, check logs!");
        Assert.assertEquals(getInfoPojo.message, "Bad Request!! CTA is invalid", "Status not 400, check logs!");


    }


    @Description("CTA=PRODUCTS: To validate the response for a valid CTA for expired ChatbotToken")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "CTA"})
    public static void validateExpiredChatbotTokenUserWithValidCTA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", ChatBotData.chatBotExpiredToken);
        headerDetails.put("user-id", ChatBotData.chatBotExpiredUserID);
        queryParamDetails.put("cta", ChatBotData.productCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.unauthorizedStatusCode, "Status not 400, check logs!");

    }

    @Description("CTA=PRODUCTS: To validate the response for a valid CTA for expired ChatbotToken")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "CTA"})
    public static void validateInvalidChatbotTokenUserWithValidCTA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", ChatBotData.chatBotInvalidToken);
        headerDetails.put("user-id", ChatBotData.chatBotInvalidUserID);
        queryParamDetails.put("cta", ChatBotData.productCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.unauthorizedStatusCode, "Status not 400, check logs!");

    }




    @Description("CTA=PRODUCTS: To validate the response for a user eligible for BNPL products only.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "PRODUCTS"})
    public static void validateProductsXpressBNPL() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.productCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.productsVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.bnplProductVariableValue)),
                "Assertion for variable name and value failed!");
    }

    @Description("CTA=PRODUCTS: To validate the response for a user eligible for BNPL and XpressLoan products only.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "PRODUCTS"})
    public static void validateProductsBNPL() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.productCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.productsVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.bnplProductVariableValue) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(1).equals(ChatBotData.xpressLoanProductVariableValue)),
                "Assertion for variable name and value failed!");
    }

    @Description("CTA=PRODUCTS: To validate the response for a user eligible for BNPL and BBPS products only.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "PRODUCTS"})
    public static void validateProductsBBPSAndBNPL() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.productCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.productsVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.bnplProductVariableValue)||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(1).equals(ChatBotData.bbpsProductVariableValue)),
                "Assertion for variable name and value failed!");

    }

    @Description("CTA=PRODUCTS: To validate the response for a user not eligible for any products with correct chatbot token.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "PRODUCTS"})
    public static void validateNoProductUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile4));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile4));
        queryParamDetails.put("cta", ChatBotData.productCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_212, "Status not 200, check logs!");

    }

    @Description("CTA=USER_STATUS: To validate the response for a Bnpl Active user status")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "USER_STATUS"})
    public static void validateUserStatusActive() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.userStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.userStatusVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.userStatusActiveEligibleVariableValue)),
                "Assertion for variable name and value failed!");
    }

    @Description("CTA=USER_STATUS: To validate the response for a Bnpl Blocked user status")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "USER_STATUS"})
    public static void validateUserStatusBlocked() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile5));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile5));
        queryParamDetails.put("cta", ChatBotData.userStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.userStatusVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.userStatusBlockedVariableValue)),
                "Assertion for variable name and value failed!");
    }

    @Description("CTA=USER_STATUS: To validate the response for a Bnpl Blacklisted user status")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "USER_STATUS"})
    public static void validateUserStatusBlacklisted() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile8));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile8));
        queryParamDetails.put("cta", ChatBotData.userStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.userStatusVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.userStatusBlackListedVariableValue)),
                "Assertion for variable name and value failed!");
    }

    @Description("CTA=OUTSTANDING: To validate the response for a user Total Outstanding")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "OUTSTANDING"})
    public static void validateUserOutStanding() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, ChatBotData.chatBotUserMobile3);
        expectedUserOutStanding = String.valueOf(repayDetailsPojo.totalOutstanding);

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));

        queryParamDetails.put("cta", ChatBotData.outStandingCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.userOutStandingVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(expectedUserOutStanding)),
                "Assertion for variable name and value failed!");
    }

    @Description("CTA=OUTSTANDING: To validate the response for a user Zero Outstanding")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "OUTSTANDING"})
    public static void validateUserWithZeroOutStanding() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        expectedUserOutStanding = "0.0";

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));

        queryParamDetails.put("cta", ChatBotData.outStandingCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.userOutStandingVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(expectedUserOutStanding)),
                "Assertion for variable name and value failed!");
    }


    @Description("CTA=BILL_PAY_STATUS: To validate the response for billpay user user")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "BILL_PAY_STATUS"})
    public static void validateUserBillPayStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));

        queryParamDetails.put("cta", ChatBotData.billPayCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.billPayStatusVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.availableVariableValue)),
                "Assertion for variable name and value failed!");
    }

    @Description("CTA=BILL_PAY_STATUS: To validate the response for not billpay user user")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "BILL_PAY_STATUS"})
    public static void validateUserBillPayStatusForNotBillpayUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));

        queryParamDetails.put("cta", ChatBotData.billPayCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.billPayStatusVariableName) ||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.notAvailableVariableValue)),
                "Assertion for variable name and value failed!");
    }


    @Description("CTA=PHONE_NUMBER: To validate phone number for a user")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "PHONE_NUMBER"})
    public static void validateUserPhoneNumber() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));

        queryParamDetails.put("cta", ChatBotData.phoneNumberCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.phoneNumberVariableName)||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.chatBotUserMobile1.substring(6))),
                "Assertion for variable name and value failed!");

    }


    @Description("CTA=ALL_TXN: To validate all txn for a user")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_TXN"})
    public static void validateALL_TXN() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));

        queryParamDetails.put("cta", ChatBotData.allTxnCTA);
        queryParamDetails.put("inputParams", ChatBotData.pageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue(getInfoPojo.data.getVariables().get(0).variableName.contains(ChatBotData.txnVariableValue),
                "Incorrect Txn page number!");

    }

    @Description("CTA=ALL_TXN: To validate all txn for a fresh user to show empty txn")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_TXN"})
    public static void validateALL_TXNShowEmptyTxnforFreshUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));

        queryParamDetails.put("cta", ChatBotData.allTxnCTA);
        queryParamDetails.put("inputParams", "0");


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue(getInfoPojo.data.getVariables().isEmpty(),
                "Incorrect Txn page number!");

    }
    @Description("CTA=ALL_TXN: To validate all txn should show latest txn first")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_TXN"})
    public static void validateALL_TXNCTATxnOrder() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        MakeTransaction.makeLPOTPTransaction(ChatBotData.chatBotUserMobile3, ChatBotData.emailID, "V0", "V0",
                ChatBotData.merchantAccessKey, ChatBotData.txnAmt, "", false);

        String txnId = MakeTransaction.payPojo.transactionId;

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));

        queryParamDetails.put("cta", ChatBotData.allTxnCTA);
        queryParamDetails.put("inputParams", "0");

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

        Assert.assertEquals(getInfoPojo.data.getVariables().get(0).getMetaData().get("identifier"), txnId,
                "Assertion for variable name and value failed!");



    }

    @Description("CTA=ALL_TXN: To validate all txn should show txn as per Page number and pageSize")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_TXN"})
    public static void validateALL_TXNCTAWithPageNumber() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));

        queryParamDetails.put("cta", ChatBotData.allTxnCTA);
        queryParamDetails.put("inputParams", ChatBotData.pageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

        JSONObject jsonObj = new JSONObject(getInfoPojo.getData());
        Assert.assertTrue(jsonObj.getJSONObject("otherInfo").getString("Current Page Number").equals(ChatBotData.pageNumber),
                "Assertion for variable name and value failed!");

    }

    @Description("CTA=ALL_TXN: To validate all txn should show no txn as for not available Page number")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_TXN"})
    public static void validateALL_TXNCTAWithNotAvailablePageNumber() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.allTxnCTA);
        queryParamDetails.put("inputParams", ChatBotData.notAvailablePageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

        JSONObject jsonObj = new JSONObject(getInfoPojo.getData());
        Assert.assertTrue((jsonObj.getJSONObject("otherInfo").getString("Current Page Number").equals(ChatBotData.notAvailablePageNumber) && getInfoPojo.data.getVariables().isEmpty()),
                "Assertion for variable name and value failed!");

    }

    @Description("CTA=ALL_TXN: To validate all txn should show no txn as for invalid Page number")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_TXN"})
    public static void validateALL_TXNCTAWithInvalidPageNumber() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.allTxnCTA);
        queryParamDetails.put("inputParams", ChatBotData.invalidPageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_104, "Status not 400, check logs!");
        Assert.assertEquals(getInfoPojo.message, "Page number provided in input params is invalid");

    }

    @Description("CTA=ALL_TXN: To validate Check Limit for valid User")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "CHECK_LIMIT"})
    public static void validateCheckLimitWithValidChatbotToken() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.checkLimitCTA);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.limit20k)) &&
                        (getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.limitVariableName)),
                "Assertion for variable name and values failed!");


    }


    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid Success Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidSuccessTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.successTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.successValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid Failed Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidFailedTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.failedTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.failValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid in progress Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidInProgressTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.inprogressTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.inprogressValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid Repayement Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidRepayementTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.repaymentTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.successValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid Cashback Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidCashbackTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.cashbackTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.successValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid Lazycash Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidLazycashTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.lazycashTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.successValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid Waiver Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidWaiverTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile5));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile5));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.waiverTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.successValue)),
                "Assertion for variable name and values failed!");


    }


    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid Billpay Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidBillpayTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.billpayTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.inprogressValue)),
                "Assertion for variable name and values failed!");


    }


    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for valid Txn id and another User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForValidTxnIdforAnotherTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile4));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile4));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.successTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_114, "Status not 400, check logs!");
        Assert.assertEquals(getInfoPojo.message, "TxnId is invalid for this user");

    }

    @Description("CTA=TXN_STATUS: To validate TXN_STATUS for invalid Txn id")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "TXN_STATUS"})
    public static void validateTxnStatusForInValidTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.txnStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.invalidTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_211, "Status not 400, check logs!");

    }


    @Description("CTA=CHECK_LIMIT: To validate Check Limit for invalid User")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "CHECK_LIMIT"})
    public static void validateCheckLimitWithInValidChatbotToken() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", ChatBotData.inCorrectUserToken);
        headerDetails.put("user-id", ChatBotData.inCorrectUserID);
        queryParamDetails.put("cta", ChatBotData.checkLimitCTA);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.unauthorizedStatusCode, "Status not 400, check logs!");

    }

    @Description("CTA=KYC_MITC_STATUS: To validate status for a kyc  not initated user should show KYC not completed.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_MITC_STATUS"})
    public static void validateKYCMITCStatusforKYCNotIntiatedUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.kycMitcStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.kycStatusVariableName)||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.initiatedVariableValue)),
                "Incorrect KYC status!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(1).variableName.equals(ChatBotData.mitcStatusVariableName)||
                        getInfoPojo.data.getVariables().get(1).variableValues.get(0).equals(ChatBotData.notCompletedVariableValue)),
                "Incorrect MITC status!");

    }

    @Description("CTA=KYC_MITC_STATUS: To validate status for a kyc initated user should show KYC not completed.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_MITC_STATUS"})
    public static void validateKYCMITCStatusNotComplete() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile2));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile2));
        queryParamDetails.put("cta", ChatBotData.kycMitcStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.kycStatusVariableName)||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.initiatedVariableValue)),
                "Incorrect KYC status!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(1).variableName.equals(ChatBotData.mitcStatusVariableName)||
                        getInfoPojo.data.getVariables().get(1).variableValues.get(0).equals(ChatBotData.notCompletedVariableValue)),
                "Incorrect MITC status!");

    }

    @Description("CTA=KYC_MITC_STATUS: To validate status for a user who has completed KYC and MITC.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_MITC_STATUS"})
    public static void validateKYCMITCStatusCompleted() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.kycMitcStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.kycStatusVariableName)||
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.completedVariableValue)),
                "Incorrect KYC status!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(1).variableName.equals(ChatBotData.mitcStatusVariableName)||
                        getInfoPojo.data.getVariables().get(1).variableValues.get(0).equals(ChatBotData.completedVariableValue)),
                "Incorrect MITC status!");

    }

    @Description("CTA=KYC_MITC_STATUS: To validate KYC and MITC status for a Invalid user token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_MITC_STATUS"})
    public static void validateKYCMITCStatusforInvalidUserToken() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", ChatBotData.inCorrectUserToken);
        headerDetails.put("user-id", ChatBotData.inCorrectUserID);
        queryParamDetails.put("cta", ChatBotData.kycMitcStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.unauthorizedStatusCode, "Status not 400, check logs!");

    }

    @Description("CTA=KYC_STATUS: To validate KYC_STATUS status for a valid token ")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_STATUS"})
    public static void validateKYCStatusCTAForValidUserToken() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.kycStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

    }

    @Description("CTA=KYC_STATUS: To validate KYC_STATUS status for a invalid token ")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_STATUS"})
    public static void validateKYCStatusCTAforInvalidUserToken() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", ChatBotData.chatBotInvalidToken);
        headerDetails.put("user-id", ChatBotData.chatBotInvalidUserID);
        queryParamDetails.put("cta", ChatBotData.kycStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.unauthorizedStatusCode, "Status not 400, check logs!");

    }


    @Description("CTA=KYC_STATUS: To validate status for a kyc not initated user should show KYC Not Initiated.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_STATUS"})
    public static void validateKYCStatusforKYCNotIntiatedUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.kycStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.kycStatusVariableName) &&
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.notInitiatedVariableValue)),
                "Incorrect KYC status!");

    }


    @Description("CTA=KYC_STATUS: To validate status for a kyc initated user should show KYC initiated.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_STATUS"})
    public static void validateKYCStatusforKYCIntiatedUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile2));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile2));
        queryParamDetails.put("cta", ChatBotData.kycStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.kycStatusVariableName) &&
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.initiatedVariableValue)),
                "Incorrect KYC status!");

    }

    @Description("CTA=KYC_STATUS: To validate status for a kyc completed user should show KYC completed.")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "KYC_STATUS"})
    public static void validateKYCStatusforKYCCompletedUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.kycStatusCTA);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).variableName.equals(ChatBotData.kycStatusVariableName) &&
                        getInfoPojo.data.getVariables().get(0).variableValues.get(0).equals(ChatBotData.completedVariableValue)),
                "Incorrect KYC status!");

    }

    @Description("CTA=ALL_REPAYMENTS: To validate response for Valid user token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_REPAYMENTS"})
    public static void validateALL_REPAYMENTSforValidToken() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.allRepaymentCTA);
        queryParamDetails.put("inputParams", ChatBotData.startPageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

    }

    @Description("CTA=ALL_REPAYMENTS: To validate response for invalid user token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_REPAYMENTS"})
    public static void validateALL_REPAYMENTSforInvalidToken() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", ChatBotData.inCorrectUserToken);
        headerDetails.put("user-id", ChatBotData.inCorrectUserID);
        queryParamDetails.put("cta", ChatBotData.allRepaymentCTA);
        queryParamDetails.put("inputParams", ChatBotData.startPageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.unauthorizedStatusCode, "Status not 400, check logs!");

    }

    @Description("CTA=ALL_REPAYMENTS: To validate all txn for a user")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_REPAYMENTS"})
    public static void validateALL_REPAYMENTS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.allRepaymentCTA);
        queryParamDetails.put("inputParams", ChatBotData.startPageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue(getInfoPojo.data.getVariables().get(0).variableName.contains(ChatBotData.repaytxnVariableValue),
                "Incorrect Txn page number!");

    }

    @Description("CTA=ALL_REPAYMENTS: To validate all txn for a fresh user to show empty txn")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_REPAYMENTS"})
    public static void validateALL_REPAYMENTSShowEmptyTxnforFreshUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.allRepaymentCTA);
        queryParamDetails.put("inputParams", ChatBotData.startPageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue(getInfoPojo.data.getVariables().isEmpty(),
                "Incorrect Txn page number!");

    }



    @Description("CTA=ALL_REPAYMENTS: To validate all txn should show txn as per Page number and pageSize")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_REPAYMENTS"})
    public static void validateALL_REPAYMENTSCTAWithPageNumber() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.allRepaymentCTA);
        queryParamDetails.put("inputParams", ChatBotData.pageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

        JSONObject jsonObj = new JSONObject(getInfoPojo.getData());
        Assert.assertTrue(jsonObj.getJSONObject("otherInfo").getString("Current Page Number").equals(ChatBotData.pageNumber),
                "Assertion for variable name and value failed!");

    }

    @Description("CTA=ALL_REPAYMENTS: To validate all txn should show no txn as for not available Page number")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_REPAYMENTS"})
    public static void validateALL_REPAYMENTSCTAWithNotAvailablePageNumber() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.allRepaymentCTA);
        queryParamDetails.put("inputParams", ChatBotData.notAvailablePageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

        JSONObject jsonObj = new JSONObject(getInfoPojo.getData());
        Assert.assertTrue((jsonObj.getJSONObject("otherInfo").getString("Current Page Number").equals(ChatBotData.notAvailablePageNumber) && getInfoPojo.data.getVariables().isEmpty()),
                "Assertion for variable name and value failed!");

    }

    @Description("CTA=ALL_REPAYMENTS: To validate all txn should show no txn as for invalid Page number")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_REPAYMENTS"})
    public static void validateALL_REPAYMENTSCTAWithInvalidPageNumber() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile1));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile1));
        queryParamDetails.put("cta", ChatBotData.allRepaymentCTA);
        queryParamDetails.put("inputParams", ChatBotData.invalidPageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_104, "Status not 400, check logs!");
        Assert.assertEquals(getInfoPojo.message, "Page number provided in input params is invalid");

    }


    @Description("CTA=ALL_REPAYMENTS: To validate all repayemnt txn should show no txn as for fresh user ")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "ALL_REPAYMENTS"})
    public static void validateALL_REPAYMENTSCTAForFreshUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile2));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile2));
        queryParamDetails.put("cta", ChatBotData.allRepaymentCTA);
        queryParamDetails.put("inputParams", ChatBotData.startPageNumber);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");

        JSONObject jsonObj = new JSONObject(getInfoPojo.getData());
        Assert.assertTrue((jsonObj.getJSONObject("otherInfo").getString("Current Page Number").equals(ChatBotData.startPageNumber) && getInfoPojo.data.getVariables().isEmpty()),
                "Assertion for variable name and value failed!");

    }



    @Description("CTA=REPAYMENT_STATUS: To validate REPAYMENT_STATUS for valid Success Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "REPAYMENT_STATUS"})
    public static void validateRepayTxnStatusForValidSuccessTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.repaymentStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.successRepayTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.successValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=REPAYMENT_STATUS: To validate REPAYMENT_STATUS for valid Failed Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "REPAYMENT_STATUS"})
    public static void validateRepayTxnStatusForValidFailedTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.repaymentStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.failedRepayTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.failValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=REPAYMENT_STATUS: To validate REPAYMENT_STATUS for valid in progress Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "REPAYMENT_STATUS"})
    public static void validateRepayTxnStatusForValidInProgressTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.repaymentStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.inprogressRepayTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.inprogressValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=REPAYMENT_STATUS: To validate REPAYMENT_STATUS for valid Repayement Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "REPAYMENT_STATUS"})
    public static void validateRepayTxnStatusForValidRepayementTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.repaymentStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.repaymentTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).contains(ChatBotData.successValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=REPAYMENT_STATUS: To validate REPAYMENT_STATUS for other than repayment Txn id and valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "REPAYMENT_STATUS"})
    public static void validateRepayTxnStatusForValidSaleTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.repaymentStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.successTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_114, "Status not CB_114, check logs!");
        Assert.assertEquals(getInfoPojo.message, "TxnId is invalid for this user");


    }


    @Description("CTA=REPAYMENT_STATUS: To validate REPAYMENT_STATUS for valid Txn id and another User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "REPAYMENT_STATUS"})
    public static void validateRepayTxnStatusForValidTxnIdforAnotherUserToken() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile4));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile4));
        queryParamDetails.put("cta", ChatBotData.repaymentStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.repaymentTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_114, "Status not 400, check logs!");
        Assert.assertEquals(getInfoPojo.message, "TxnId is invalid for this user");

    }

    @Description("CTA=REPAYMENT_STATUS: To validate REPAYMENT_STATUS for invalid Txn id")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "REPAYMENT_STATUS"})
    public static void validateRepayTxnStatusForInValidTxnId() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.repaymentStatusCTA);
        queryParamDetails.put("inputParams", ChatBotData.invalidTxnId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_CB_211, "Status not 400, check logs!");

    }

    @Description("CTA=OUTSTANDING_AND_LOAN_INFO: To validate OUTSTANDING_AND_LOAN_INFO for Invalid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "OUTSTANDING_AND_LOAN_INFO"})
    public static void validateOUTSTANDING_AND_LOAN_INFOForInValidUserToken() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", ChatBotData.inCorrectUserToken);
        headerDetails.put("user_id", ChatBotData.inCorrectUserID);
        queryParamDetails.put("cta", ChatBotData.outStandingLoanCTA);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.unauthorizedStatusCode, "Status not UNAUTHORIZED, check logs!");


    }


    @Description("CTA=OUTSTANDING_AND_LOAN_INFO: To validate OUTSTANDING_AND_LOAN_INFO for valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "OUTSTANDING_AND_LOAN_INFO"})
    public static void validateOUTSTANDING_AND_LOAN_INFOForValidUserToken() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.outStandingLoanCTA);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableName().equalsIgnoreCase(ChatBotData.outstandingVariableName)
                        && getInfoPojo.data.getVariables().get(1).getVariableName().equalsIgnoreCase(ChatBotData.activeLoanVariableName)),
                "Assertion for variable name and values failed!");


    }



    @Description("CTA=CHECK_DELINQ: To validate CHECK_DELINQ for valid Non Deliquent User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "CHECK_DELINQ"})
    public static void validateCHECK_DELINQForValidNonDeliquentUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.checkDelinqCTA);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableName().equalsIgnoreCase(ChatBotData.delenqVariableName)
                        && getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).equalsIgnoreCase(ChatBotData.notdelenqVariableValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=CHECK_DELINQ: To validate CHECK_DELINQ for valid Deliquent User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "CHECK_DELINQ"})
    public static void validateCHECK_DELINQForValidDeliquentUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile5));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile5));
        queryParamDetails.put("cta", ChatBotData.checkDelinqCTA);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableName().equalsIgnoreCase(ChatBotData.delenqVariableName)
                        && getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).equalsIgnoreCase(ChatBotData.delenqVariableValue)),
                "Assertion for variable name and values failed!");


    }


    @Description("CTA=DOWNLOAD_STATEMENT: To validate DOWNLOAD_STATEMENT for valid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "DOWNLOAD_STATEMENT"})
    public static void validateDOWNLOAD_STATEMENTForValidUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile10));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile10));
        queryParamDetails.put("cta", ChatBotData.downloadStatementCTA);
        queryParamDetails.put("inputParams", ChatBotData.clearedStmtId);


        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableName().equalsIgnoreCase(ChatBotData.downloadStatementVariableName)
                        && getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).equalsIgnoreCase(ChatBotData.successValue)),
                "Assertion for variable name and values failed!");


    }

    @Description("CTA=DOWNLOAD_STATEMENT: To validate DOWNLOAD_STATEMENT for another User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "DOWNLOAD_STATEMENT"})
    public static void validateDOWNLOAD_STATEMENTForAnotherUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", chatAuthData.get("auth-token-"+ChatBotData.chatBotUserMobile3));
        headerDetails.put("user-id", chatAuthData.get("user-id-"+ChatBotData.chatBotUserMobile3));
        queryParamDetails.put("cta", ChatBotData.downloadStatementCTA);
        queryParamDetails.put("inputParams", ChatBotData.pendingStmtId);

        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.status_200_ok, "Status not 200, check logs!");
        Assert.assertTrue((getInfoPojo.data.getVariables().get(0).getVariableName().equalsIgnoreCase(ChatBotData.downloadStatementVariableName)
                        && getInfoPojo.data.getVariables().get(0).getVariableValues().get(0).equalsIgnoreCase(ChatBotData.failedValue)),
                "Assertion for variable name and values failed!");

    }

    @Description("CTA=DOWNLOAD_STATEMENT: To validate DOWNLOAD_STATEMENT for invalid User Token")
    @Feature("ChatBotAPI")
    @Test(priority =2, groups = {"chatbot", "sanity", "DOWNLOAD_STATEMENT"})
    public static void validateDOWNLOAD_STATEMENTForInvalidUser() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("auth-token", ChatBotData.inCorrectUserToken);
        headerDetails.put("user-id", ChatBotData.inCorrectUserID);
        queryParamDetails.put("cta", ChatBotData.downloadStatementCTA);
        queryParamDetails.put("inputParams", ChatBotData.pendingStmtId);



        getInfoPojo = getInfo.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getInfoPojo.statusCode, ChatBotData.unauthorizedStatusCode, "Status not 400, check logs!");

    }





}