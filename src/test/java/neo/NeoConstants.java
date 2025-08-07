package neo;

public class NeoConstants {


    public static final String Lazy_card_server="lazypay.sandbox.server";
    public static final String CUSTOMER_PREFERENCE_REQUEST="src/test/resources/neo/support/customerprefrence.txt";
    public static final String AUTHORIZE_TRANSACTION_REQUEST="src/test/resources/neo/Transaction/authoriseTransaction.txt";
    public static final String REFUND_TRANSACTION_REQUEST ="src/test/resources/neo/Transaction/reversalapi.txt";
    public static final String CREDIT="src/test/resources/neo/Transaction/credit.txt";
    public static final String CREDIT_WITH_TXNID="src/test/resources/neo/Transaction/creditapiwithTraxid.txt";
    public static final String CREDIT_API_WITHOUT_TRANXID="src/test/resources/neo/Transaction/creditapiwithouttransactionid.txt";
    public static final String CARD_CONTROL="src/test/resources/neo/support/cardControl.txt";
    public static final String YAP_NOTIFICATION="src/test/resources/neo/Transaction/webhook.txt";

    public static final String PHYSICAL_CARD_REQUEST_KYC="src/test/resources/neo/support/reqPhysicalCardKYC.txt";
    public static final String PHYSICAl_CARD_REQUEST_NONKYC="src/test/resources/neo/support/reqPhysicalCardNonKYC.txt";


    public static final String OOBOARDING_CUSTOMER="src/test/resources/neo/onboarding/onboardingcustomer.txt";
    public static final String OOBOARDING_CUSTOMER_OTP="src/test/resources/neo/onboarding/onboardingcustomerotp.txt";
    public static final String CARD_ACTIONS_REQUEST = "src/test/resources/neo/support/cardActions.txt";

    public static final String FD_TESTDATA="src/test/resources/neo/fd/fd-testdata.yml";
    public static final String SET_TRANSACTION_LIMIT="src/test/resources/neo/cardControl/setTransactionLimit.txt";
    public static final String DEBIT_TRANSACTION="src/test/resources/neo/Transaction/debiTransaction.txt";
    public static final String VISASIDATA="src/test/resources/neo/Transaction/visaSiData.txt";
    public static final String AUTH_POS_TRANSACTION="src/test/resources/neo/Transaction/authTransactionPos.txt";
    public static final String CREATE_ORDER_WITH_NOMINEE="src/test/resources/neo/fd/createOrderWithNominee.txt";
    public static final String CREATE_ORDER_WITHOUT_NOMINEE="src/test/resources/neo/fd/createOrderWithoutNominee.txt";
    public static final String CREATE_FD="src/test/resources/neo/fd/createFdPayload.txt";
    public static final String WEBHOOK_FD_CREATE="src/test/resources/neo/fd/webhookFDCreate.txt";
    public static final String USER_NAME_SUGGESTIONS="src/test/resources/neo/cardControl/getNameSuggestions.txt";
    public static final String WEBHOOK_MODIFYLIEN_AND_CLOSEFD="src/test/resources/neo/fd/webhookModifyLien.txt";

    public static final String APV_HIEMDALL_TESTDATA = "src/test/resources/heimdall/APV-heimdall-testData.yml";
    public static final String APV_HEIMDALL_ERROR_JSON = "src/test/resources/heimdall/errorResponse.json";

}