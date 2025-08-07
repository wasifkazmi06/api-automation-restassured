package chatbot;

import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;

public class ChatBotData {


    /**
     *  User Data
     */

    static String inCorrectUserToken = "abcd1234";
    static String inCorrectUserID = "abcd1234";
    static String emailID = "chatbotuser@gmail.com";
    static String invalidTxnId = "abcd";

    /**
     * BNPL Active No limit user
     * Kyc not initated
     */
    static String chatBotUserMobile1 = "6000000481";

    /**
     * BNPL Active limit 9000 user
     * kyc initated
     */
    static String chatBotUserMobile2 = "6000000482";

    /**
     * BNPL Active limit + outstanding user
     * Kyc completed
     * Mitc completed
     * Billpay whitelisted
     */
    static String chatBotUserMobile3 = "6000000483";
    static String successTxnId = "TXN1353873";
    static String successRepayTxnId = "TXN1353877";
    static String failedRepayTxnId = "TXN1354093";
    static String inprogressRepayTxnId = "TXN1354092";
    static String failedTxnId = "TXN1349948";
    static String inprogressTxnId = "TXN1353876";
    static String repaymentTxnId = "TXN1353877";
    static String cashbackTxnId = "TXN1353878";
    static String lazycashTxnId = "TXN1353879";
    static String billpayTxnId = "TXN1353953";
    static String limit20k = "20000.0";

    /**
     * Fresh user not whitelisted for any product
     */
    static String chatBotUserMobile4 = "6000000484";

    /**
     * BNPL DPD Blocked limit + outstanding user
     * Kyc completed
     * Mitc completed
     */
    static String chatBotUserMobile5 = "6000000485";
    static String waiverTxnId = "TXN1354588";

    /**
     * BNPL Active limit 0 outstanding user
     * Kyc completed
     * Mitc completed
     */
    static String chatBotUserMobile6 = "6000000486";

    /**
     * BNPL Active limit 2500 outstanding user
     * Kyc completed
     * Mitc completed
     * Billpay Whitelisted
     */
    static String chatBotUserMobile7 = "6000000487";

    /**
     * BNPL Blacklisted limit negative outstanding user
     * Kyc in progress
     */
    static String chatBotUserMobile8 = "6000000488";

    /**
     * BNPL Blocked limit 2500 outstanding user
     * Kyc Document pending
     */
    static String chatBotUserMobile9 = "6000000489";

    /**
     * BNPL Active limit 2500 outstanding user
     * Kyc completed
     * Mitc completed
     */
    static String chatBotUserMobile10 = "6000000490";
    static String pendingStmtId = "LpStmt31050";
    static String clearedStmtId ="LpStmt31049";




    static String chatBotExpiredToken = "af011028-503f-4911-9059-a32c4b01ed6a";
    static String chatBotExpiredUserID = "6110fe1c-d2ef-4737-bc2b-24bb8961c8fb";

    static String chatBotInvalidToken = "Invalid-token";
    static String chatBotInvalidUserID = "Invalid-userId";


    /**
     *  Txn Data
     */

    static String merchantAccessKey = "CCWHRSH9UULECPLQCFD2";
    static String txnAmt = "1.45";
    static String pageNumber ="2";
    static String startPageNumber = "0";
    static String notAvailablePageNumber ="2000";
    static String invalidPageNumber ="invalid-pageno";

    /**
     *  CTA
     */

    static String productCTA = "PRODUCTS";
    static String userStatusCTA = "USER_STATUS";
    static String outStandingCTA = "OUTSTANDING";
    static String phoneNumberCTA = "PHONE_NUMBER";
    static String allTxnCTA = "ALL_TXN";
    static String txnStatusCTA = "TXN_STATUS";
    static String checkLimitCTA = "CHECK_LIMIT";
    static String kycMitcStatusCTA = "KYC_MITC_STATUS";
    static String onboardingStatusCTA = "ONBOARDING_STATUS";
    static String kycStatusCTA = "KYC_STATUS";
    static String changeDOBCTA = "CHANGE_DOB";
    static String outStandingLoanCTA = "OUTSTANDING_AND_LOAN_INFO";
    static String deactivatedLoanCTA = "DEACTIVATED_LOAN";
    static String allDeactivatedLoanCTA = "ALL_DEACTIVATED_LOANS";
    static String sendNDCCTA = "SEND_NDC";
    static String checkDelinqCTA = "CHECK_DELINQ";
    static String userEligibilityRBLCTA = "USER_ELIGIBILITY_RBL";
    static String billPayCTA = "BILL_PAY_STATUS";
    static String downloadStatementCTA = "DOWNLOAD_STATEMENT";
    static String allRepaymentCTA = "ALL_REPAYMENTS";
    static String repaymentStatusCTA = "REPAYMENT_STATUS";
    static String invalidCTA = "ABC_XYZ";

    /**
     *  Variable Name
     */

    static String kycStatusVariableName = "kycStatus";
    static String mitcStatusVariableName = "mitcStatus";
    static String productsVariableName = "products";
    static String userStatusVariableName = "userStatus";
    static String userOutStandingVariableName = "outstanding";
    static String phoneNumberVariableName = "phoneNumberEnding";
    static String billPayStatusVariableName = "billPayStatus";
    static String outstandingVariableName = "outstanding";
    static String activeLoanVariableName = "activeLoan";
    static String delenqVariableName = "delenqStatus";
    static String limitVariableName = "limit";
    static String downloadStatementVariableName = "downloadStatementStatus";


    /**
     *  Variable Value
     */

    static String notCompletedVariableValue = "Not Completed";
    static String completedVariableValue = "Completed";
    static String initiatedVariableValue = "Initiated";
    static String notInitiatedVariableValue = "Not initiated";
    static String bnplProductVariableValue = "Buy now pay later";
    static String xpressLoanProductVariableValue = "XpressLoan";
    static String bbpsProductVariableValue = "BillPay";
    static String userStatusBlockedVariableValue = "BLOCKED";
    static String userStatusBlackListedVariableValue = "BLACKLISTED";
    static String userStatusActiveVariableValue = "ACTIVE";
    static String notAvailableVariableValue = "Not available";
    static String availableVariableValue = "Available";
    static String userStatusActiveEligibleVariableValue = "ACTIVE_AND_ELIGIBLE";
    static String txnVariableValue = "txn";
    static String repaytxnVariableValue ="rpymnt";
    static String notdelenqVariableValue = "Non delinquent";
    static String delenqVariableValue = "Delinquent";

    /**
     *  Response Variable Value
     */

    static String unauthorizedStatusCode = "UNAUTHORIZED";
    static String successValue = "SUCCESS";
    static String failValue ="FAIL";
    static String failedValue ="Failed";
    static String inprogressValue = "IN_PROGRESS";
    static String status_200_ok ="200 OK";
    static String status_CB_100 ="CB_100";
    static String status_CB_104 ="CB_104";
    static String status_CB_114 ="CB_114";
    static String status_CB_211 ="CB_211";
    static String status_CB_212 ="CB_212";


    @SneakyThrows
    @DataProvider(name = "user-mobile")
    public final static Object[][] getUserMobileForTransactions()
    {
        return new Object[][] {{chatBotUserMobile1}, {chatBotUserMobile2}, {chatBotUserMobile3}, {chatBotUserMobile4},{chatBotUserMobile5},
                {chatBotUserMobile8}, {chatBotUserMobile9}, {chatBotUserMobile10}};
    }


}
