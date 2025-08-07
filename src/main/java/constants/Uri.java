package constants;

public class Uri {
    /**
     * Define URI HERE
     */

    public static final String TEST = "LAZYPAY_SANDBOX/api/v1/fetchTransaction/$";
    public static final String CUSTOMER_PREFERENCE = "LAZYCARD_SANDBOX/api/v1/custPreference";
    public static final String AUTHORIZE_TRANSACTION = "LAZYCARD_SANDBOX/api/v1/authorize";
    public static final String REFUND_TRANSACTION = "LAZYCARD_SANDBOX/api/v1/reversal";
    public static final String CREDIT_TRANSACTION = "LAZYCARD_SANDBOX/api/v1/credit";
    public static final String CARD_CONTROL = "LAZYCARD_SANDBOX/api/v1/cardAction";
    public static final String CARD_REPLACEMENT = "LAZYCARD_SANDBOX/api/v1/cardReplacement/{uuid}";
    public static final String REQ_PHYSICAL_CARD = "LAZYCARD_SANDBOX/api/v1/reqPhysicalCard";
    public static final String CARD_FREEZE = "LAZYCARD_SANDBOX/android/cardControls/freezeCard";
    public static final String NEO_CUSTOMER_PREFERENCE = "LAZYCARD_SANDBOX/api/v1/custPreference";
    public static final String NEO_AUTHORIZE_TRANSACTION = "LAZYCARD_SANDBOX/api/v1/authorize";
    public static final String NEO_REFUND_TRANSACTION = "LAZYCARD_SANDBOX/api/v1/reversal";
    public static final String NEO_CREDIT_TRANSACTION = "LAZYCARD_SANDBOX/api/v1/credit";
    public static final String NEO_CARD_CONTROL = "LAZYCARD_SANDBOX/api/v1/cardAction";
    public static final String NEO_INITIATE_ONBOARDING = "LAZYCARD_SANDBOX/api/v1/initiateOnboarding";
    public static final String NEO_ONBOARD_CUSTOMER = "LAZYCARD_SANDBOX/api/v1/onboardCustomer";
    public static final String NEO_CARD_ACTIONS = "LAZYCARD_SANDBOX/api/v1/cardAction";
    public static final String NEO_CARD_ACTION_STATUS = "LAZYCARD_SANDBOX/api/v1/cardActionStatus/$";
    public static final String SIGNATURE = "WEBAPP/temp/getSignature";
    public static final String ELIGIBILITYV0 = "WEBAPP/api/lazypay/v0/payment/eligibility";
    public static final String ELIGIBILITYV1 = "WEBAPP/api/lazypay/v1/payment/eligibility";
    public static final String ELIGIBILITYV2 = "WEBAPP/api/lazypay/v2/payment/eligibility";
    public static final String ELIGIBILITYV3 = "WEBAPP/api/lazypay/v3/payment/eligibility";
    public static final String ELIGIBILITYV4 = "WEBAPP/api/lazypay/v4/payment/eligibility";
    public static final String ELIGIBILITYV5 = "WEBAPP/api/lazypay/v5/payment/eligibility";
    public static final String ELIGIBILITYV6 = "WEBAPP/api/lazypay/v6/payment/eligibility";

    public static final String INITIATE_PAYV0 = "WEBAPP/api/lazypay/v0/payment/initiate";
    public static final String INITIATE_PAYV1 = "WEBAPP/api/lazypay/v1/payment/initiate";
    public static final String INITIATE_PAYV2 = "WEBAPP/api/lazypay/v2/payment/initiate";
    public static final String INITIATE_PAYV4 = "WEBAPP/api/lazypay/v4/payment/initiate";
    public static final String INITIATE_PAYV5 = "WEBAPP/api/lazypay/v5/payment/initiate";


    public static final String PAYV0 = "WEBAPP/api/lazypay/v0/payment/pay";
    public static final String PAYV1 = "WEBAPP/api/lazypay/v1/payment/pay";
    public static final String PAYV2 = "WEBAPP/api/lazypay/v2/payment/pay";
    public static final String PAYV4 = "WEBAPP/api/lazypay/v4/payment/pay";
    public static final String PAYV5 = "WEBAPP/api/lazypay/v5/payment/pay";

    public static final String AUTODEBITMIV1 = "SECUREAPP/api/pay/v1/autodebit";


    public static final String ISSIALLOWED = "WEBAPP//api/lazypay/v0/payment/isSIAllowed/$/#";
    public static final String CHECKBINHEALTH = "WEBAPP/api/lazypay/v0/payment/checkBinHealth/$";
    public static final String GETPAYUBIZREPAYTRANSACTION = "SECUREAPP/api/lazypay/v0/payubiz/$";
    public static final String REPAYDETAILS = "SECUREAPP/api/repay/repayDetails/$";

    public static final String PREFERREDMETHOD = "SECUREAPP/api/repay/preferred/method/$";
    public static final String SAVEDOPTION = "SECUREAPP/api/repay/savedOptions/$";
    public static final String PAYMENTMETHOD = "SECUREAPP/api/repay/methods/$";
    public static final String SUCCESSREDIRECT = "SECUREAPP/api/repay/successRedirect/$";
    public static final String VALIDATEVPA = "SECUREAPP/api/repay/vpa/validate";
    public static final String PROCESSREPAY = "SECUREAPP/api/repay/v0/processRepay/$";
    public static final String INITIATEREPAY = "SECUREAPP/api/repay/initiateRepay/$";
    public static final String LAZYPAY_GETUUID = "LAZYPAY_BASEURL_PLATFORM/api/lazypay/platform/users/";
    public static final String COF_ELIGIBILTY = "SECUREAPP/api/lazypay/cof/v0/eligibility";
    public static final String COF_INITIATE = "SECUREAPP/api/lazypay/cof/v0/initiate";
    public static final String GET_APP_TRANSACTION_HISTORY = "SECUREAPP/api/lazypay/v0/users/{module}/history/app/{uuid}";
    public static final String NEO_CARD_CONTROL_GETLIMIT = "LAZYCARD_SANDBOX/api/v1/getTxnLimit/$";
    public static final String NEO_CARD_CONTROL_SETLIMIT = "LAZYCARD_SANDBOX/api/v1/setTxnLimit";
    public static final String NEO_CARD_CONTROL_GETNAMESUGGESTIONS = "LAZYCARD_SANDBOX/api/v1/getNameSuggestions";
    public static final String NEO_CARD_FETCH_SHIPMENT_UPDATES = "LAZYCARD_SANDBOX/api/v1/fetchShipmentUpdates/$";


    public static final String OTP = "OTP_BASEURL/service/um/find_or_create/user";
    public static final String OAUTH_TOKEN = "OTP_BASEURL/oauth/token";
    public static final String LAZYPAY_LOGIN = "PLATFORM_SERVICE/api/lazypay/platform/login";

    /**
     * KYC service
     */
    public static final String INITIATE_KYCV9 = "LAZYPAY_BASEURL_KYC/api/kycEngine/v9/initiateKyc";
    public static final String KYC_UPLOAD_DOCUMENT = "LAZYPAY_BASEURL_KYC/api/kycEngine/v3/uploadDocuments";
    public static final String KYC_STATUS = "LAZYPAY_BASEURL_KYC/api/kycEngine/v2/kycStatus";
    public static final String KYC_EXPIRY_Status = "LAZYPAY_BASEURL_KYC/api/kycEngine/rekyc/kyc-expiry-status/$";
    public static final String V4_KYC_STATUS = "LAZYPAY_BASEURL_KYC/api/kycEngine/v4/kycStatus?uuid={uuid}&source={source}";
    public static final String V1_LATEST_SUCCESS_KYC = "LAZYPAY_BASEURL_KYC/api/kycEngine/v1/latest-success-kyc?uuid={uuid}&source={source}";
    public static final String FETCH_PAN_DETAILS = "LAZYPAY_BASEURL_KYC/api/kycEngine/panDetails";
    public static final String CHECK_ELIGIBILITY = "LAZYPAY_BASEURL_KYC/api/kyc/util/check-eligibility";
    public static final String V2_CASE_DETAILS = "LAZYPAY_BASEURL_KYC/api/kycEngine/v2/caseDetail";
    public static final String V3_CASE_DETAILS = "LAZYPAY_BASEURL_KYC/api/kycEngine/v3/caseDetail";
    public static final String FETCH_CKYC_NUMBER = "LAZYPAY_BASEURL_KYC/api/kycEngine/ckyc-number/$";
    public static final String DEACTIVATE_KYC_CASE_V1 =
            "LAZYPAY_BASEURL_KYC/api/kycEngine/v1/deactivate-kyc-case";
    public static final String FETCH_APV_STATUS = "LAZYPAY_BASEURL_KYC/api/kycEngine/v1/kyc-apv-status";
    public static final String RESOLVE_STEPS = "BIFROST_SERVICE_BASE_URL/api/onboarding/resolve-steps";

    public static final String USER_LAT_LONG = "LAZYPAY_BASEURL_KYC/api/kycEngine/user-lat-long";

    public static final String LOAN_CANCELLATION = "CREDIT_LINE/api/creditline/loanMerchantSale/cancel/emi";

    //UPI
    public static final String FOCMERCHANTUPI = "SECUREAPP/api/idfc/upi/findOrCreateMerchant";
    public static final String LP_UPI_TXN_ELIGIBILITY = "SECUREAPP/api/idfc/upi/lpTxnEligibility";
    public static final String CL_UPI_TXN_ELIGIBILITY = "SECUREAPP/api/idfc/upi/clTxnEligibility";
    public static final String POST_INTENT = "UPI/api/upi/txn/intent";
    public static final String INITIATE_TXN_UPI = "SECUREAPP/api/upi/payment/initiate";
    public static final String VALIDATE_TXN_UPI = "SECUREAPP/api/upi/payment/validate";
    public static final String GENERATE_SMS = "UPI/api/upi/registration/generate-sms";
    public static final String NOTIFY_SMS = "UPI/api/upi/registration/notify-sms-status";
    public static final String REGISTRATION_STATUS = "UPI/api/upi/registration/status";
    public static final String REFUND_UPI = "SECUREAPP/api/lazypay/v0/upi/refund";

    public static final String YAP_CALLBACK_REQ = "LAZYCARD_SANDBOX/api/webhook/yap/notification";

    //BNPL Cron
    public static final String STATEMENT_GEN_CRON = "LAZYPAY_BASEURL_CRON/api/lazypay/cron/generateStatements/$";
    public static final String STATEMENT_SENDER_CRON = "LAZYPAY_BASEURL_CRON/api/lazypay/cron/sendStatementsOptimised";


    //Secured_Card [FD]
    public static final String SECUREDCARD_VERIFYUPI = "FD_SERVICE/fdPayment/verifyUpi";
    public static final String SECUREDCARD_FD_DETAILS = "FD_SERVICE/fd/details";
    public static final String SECUREDCARD_FD_PROGRESS = "FD_SERVICE/fd/progress/$";
    public static final String SECUREDCARD_FD_QUEUECOUNT = "FD_SERVICE/fd/userQueue/{uuid}/{mobile}";
    public static final String SECUREDCARD_FD_ADDQUEUE = "FD_SERVICE/fd/userQueue/add/{uuid}/{mobile}";
    public static final String SECUREDCARD_CREATE_ORDER = "FD_SERVICE/fdPayment/createOrder";
    public static final String SECUREDCARD_CREATE_FD = "FD_SERVICE/fdPayment/createFd";
    public static final String SECUREDCARD_FD_LIST = "FD_SERVICE/fd/fdList/$";
    public static final String SECUREDCARD_FD_COUNT = "FD_SERVICE/fd/fdCount/$";
    public static final String SECUREDCARD_Verified_UPI_LIST = "FD_SERVICE/fd/fetchVerifiedUpi";
    public static final String SECUREDCARD_WEBHOOK_CREATE_FD = "FD_SERVICE/webhook/fd/create";
    public static final String SECUREDCARD_FD_SUMMARY = "FD_SERVICE/fd/fdSummary/$";
    public static final String SECUREDCARD_FD_NOMINEEDETAILS = "FD_SERVICE/fd/fetchNominee/$";
    public static final String SECUREDCARD_FD_INTERESTDETAILS = "FD_SERVICE/fd/interestDetails/$";
    public static final String SECUREDCARD_PENALTY_INTERESTDETAILS = "FD_SERVICE/fd/penaltyInterestDetails";
    public static final String SECUREDCARD_REMOVE_FROM_REDIS = "FD_SERVICE/fd/removeFromRedis/fdAmount{uuid}";
    public static final String SECUREDCARD_WEBHOOK_MODIFY_LIEN = "FD_SERVICE/webhook/modifyLien";
    public static final String SECUREDCARD_WEBHOOK_CLOSE_FD = "FD_SERVICE/webhook/closeFd";

    /**
     * Platform service
     */
    public static final String PLATFORM_GET_USER = "PLATFORM_SERVICE/api/lazypay/platform/users/";
    public static final String FOC_PLATFORM = "PLATFORM_SERVICE/api/lazypay/platform/um/find_or_create_user";
    public static final String UPDATE_USER_STATUS = "PLATFORM_SERVICE/api/lazypay/platform/users/status";
    public static final String PLATFORM_GET_ELIGIBLE_PRODUCTS = "PLATFORM_SERVICE/api/lazypay/platform/users/get-products";
    public static final String PLATFORM_SIGN_KFS = "PLATFORM_SERVICE/api/lazypay/platform/users/sign-kfs";
    public static final String PLATFORM_SIGN_MITC = "PLATFORM_SERVICE/api/lazypay/platform/users/terms-and-conditions?mock=false";


    /**
     * Link APIs
     */
    public static final String SEND_OTP = "WEBAPP/api/lazypay/token/initiate";
    public static final String VALIDATE_OTP = "WEBAPP/api/lazypay/token/validateOTP";

    /**
     * BNPL Refund
     */
    public static final String MERCHANT_REFUND = "WEBAPP/api/lazypay/v0/refund";

    /**
     * Lazycash
     */
    public static final String LAZYCASHWITHSOURCE = "SECUREAPP/api/lazypay/v1/reward/lazyCash?source={source}";

    /**
     * Cashback
     */
    public static final String CASHBACK = "SECUREAPP/api/lazypay/v1/cashback";

    //JusPay
    public static final String REPAYDETAILS_JP = "WEBAPP/api/lazypay/v2/payment/repayDetails/$";
    public static final String PREFERREDMETHOD_JP = "WEBAPP/api/lazypay/v2/payment/saved/method/$";
    public static final String SAVEDOPTION_JP = "WEBAPP/api/lazypay/v2/payment/saved/method/$";
    public static final String CHECKREPAY_JP = "WEBAPP/api/lazypay/v2/order-status?";
    public static final String REFUNDENQUIRY_JP = "SECUREAPP/api/repay/v1/refund-enquiry";
    public static final String VALIDATEVPA_JP = "WEBAPP/api/lazypay/v2/repayment/upi/vpa/validate?";
    public static final String CREATEORDER_JP = "WEBAPP/api/lazypay/v2/create-order";
    public static final String REPAYREFUND_JP = "SECUREAPP/api/assuredpay/v0/repay/refund";

    public static final String SEND_OTP_MBE = "MBE/v1/auth/send-otp";
    public static final String VERIFY_OTP = "MBE/v1/auth/verify-otp";
    public static final String LOGIN="MBE/v0/ANDROID/user/loginOrRegister";
    public static final String DEVICECONFIG="MBE/v0/android/device/config";
    public static final String PAY_BILL="MBE/v2/android/appview/getPayBillView?";

    public static final String PAYMENT_MODES="MBE/v1/android/appview/getPaymentModesView?";
    public static final String MBE_INIIATE_TRANSATION="MBE/api/payments/v0/initiate-transaction";
    public static final String TRANSACTION_STATUS="MBE/api/payments/v0/get-transaction-status?";

    public static final String TRANSACTION_SUCCESS="MBE/api/payments/v0/transaction/success?";
    public static final String UPDATE_REPAY_STATUS="SECUREAPP/api/repay/v1/status-update";


    /**
     * Dynamic Config System Service
     */
    public static final String DCS_REGISTER_VERTICAL = "DCS_SERVICE/dc/api/v1/client/vertical";
    public static final String DCS_REGISTER_CLIENT = "DCS_SERVICE/dc/api/v1/client/register";
    public static final String DCS_GET_SPECIFIED_CLIENT_DETAILS = "DCS_SERVICE/dc/api/v1/client/$";
    public static final String DCS_UPDATE_SPECIFIED_CLIENT_DETAILS = "DCS_SERVICE/dc/api/v1/client";
    public static final String DCS_DELETE_SPECIFIED_CLIENT_DETAILS = "DCS_SERVICE/dc/api/v1/client/$";
    public static final String DCS_ADD_CONFIG_DETAILS = "DCS_SERVICE/dc/api/v1/config";
    public static final String DCS_GET_CONFIG_DETAILS = "DCS_SERVICE/dc/api/v1/config?client_id={client_id}&key={key}";
    public static final String DCS_DELETE_SPECIFIED_CONFIG_DETAILS = "DCS_SERVICE/dc/api/v1/config";


    /**
     * User Segmentation Service
     */
    public static final String US_CREATE_TENANT = "USER_SEGMENTATION_SERVICE/api/v1/tenant/?tenant-name={tenant-name}";
    public static final String US_CREATE_CLIENT = "USER_SEGMENTATION_SERVICE/api/v1/client/?client-name={client-name}";
    public static final String US_CREATE_SEGMENT = "USER_SEGMENTATION_SERVICE/api/v1/user-segment/?user-segment-name={user-segment-name}";
    public static final String US_ADD_USERS = "USER_SEGMENTATION_SERVICE/api/v1/user-mapping/add-users/?user-segment-id={user-segment-id}";
    public static final String US_REMOVE_USERS = "USER_SEGMENTATION_SERVICE/api/v1/user-mapping/remove-users/?user-segment-id={user-segment-id}";
    public static final String US_SUBSCRIBE = "USER_SEGMENTATION_SERVICE/api/v1/subscription/subscribe/?user-segment-id={user-segment-id}";
    public static final String US_UNSUBSCRIBE = "USER_SEGMENTATION_SERVICE/api/v1/subscription/unsubscribe/?user-segment-id={user-segment-id}";
    public static final String US_GET_USER_SEGMENT_ID = "USER_SEGMENTATION_SERVICE/api/v1/user-mapping/?user-id={user-id}";
    public static final String US_GET_UPLOAD_HISTORY_STATUS = "USER_SEGMENTATION_SERVICE/api/v1/upload-history/$";
    public static final String US_GET_ALL_ACTIVE_SEGMENTS = "USER_SEGMENTATION_SERVICE/api/v1/subscription/get-all/";


    /**
     * Docstore
     * DEFINE DOC_STORE_SERVICE
     */

    public static final String STORE_DOCUMENT = "DOC_STORE_SERVICE/docStore/storeDocument";
    public static final String GET_DOCUMENTS = "DOC_STORE_SERVICE/docStore/getDocuments";
    public static final String GET_DOCUMENT_BY_ID = "DOC_STORE_SERVICE/docStore/getDocumentByDocId";
    public static final String STORE_SENSITIVE_INFO = "DOC_STORE_SERVICE/sensitiveInfo/storeSensitiveInfo";
    public static final String DELETE_SENSITIVE_INFO = "DOC_STORE_SERVICE/sensitiveInfo/delete";
    public static final String RETRIEVE_SENSITIVE_INFO = "DOC_STORE_SERVICE/sensitiveInfo/retrieveSensitiveInfo";
    public static final String UPDATE_SENSITIVE_INFO = "DOC_STORE_SERVICE/sensitiveInfo/updateSensitiveInfo";

    /**
     * Paylater Onboarding URL's
     */
    public static final String PL_SIGNATURE = "STG_WEBAPP/temp/getSignature";
    public static final String PL_INITIATE_OTP = "STG_WEBAPP/api/lazypay/spl/v1/otp/initiate";
    public static final String PL_VALIDATE_OTP = "STG_WEBAPP/api/lazypay/spl/v1/otp/validate";
    public static final String PL_USER_STATUS = "STG_WEBAPP/api/lazypay/spl/v1/user-status";

    /**
     * NACH
     */

    public static final String GET_LANDING_PAGE = "MBE/v0/android/appview/getLandingScreen";
    public static final String UPDATE_REPAYMENT_AMOUNT_TYPE = "MBE/v0/android/appview/updateRepaymentAmountType";
    public static final String GET_IRS_BANK_FORM = "MBE/v0/android/appview/getIRSBankForm";
    public static final String GET_SUPPORTED_BANK_LIST = "MBE/v0/android/appview/getSupportedBankList";
    public static final String INITIATE_IRS = "MBE/v0/android/appview/initiateIRS";
    public static final String AUTHENTICATE_SETUP_NB = "MBE/v0/android/appview/authenticateSetupNB";
    public static final String GET_IRS_STATUS = "MBE/v0/android/appview/getIRSStatus";

    /**
     * User onboarding
     */
    public static final String USER_ONBOARDINGV1 = "SECUREAPP/api/lazypay/v1/onboard";


    public static final String VAULT_INTERNAL_ACCOUNT = "VAULT/v1/internal-accounts/$";
    public static final String BALANCE_LEDGER = "SECUREAPP/api/account/balances/ledger/$";
    public static final String BALANCE_CACHE = "SECUREAPP/api/account/balances/cache/$";
    public static final String GET_ACCOUNT = "GRINGOTTS//api/v0/accounts/$";
    public static final String PLAN_UPDATE = "VAULT/v1/plan-updates";
    public static final String ACCOUNT_UPDATE = "VAULT/v1/account-updates";


    /**
     * AMSautomation
     * AMS
     * AMS Service
     */
    public static final String FIND_OR_CREATE_USER = "PLATFORM_SERVICE/api/lazypay/platform/um/find_or_create_user";
    public static final String GET_USER_INFO = "PLATFORM_SERVICE/api/lazypay/platform/um/getUserInfo";
    public static final String GET_USER_DETAILS = "PLATFORM_SERVICE/api/lazypay/platform/users/";
    public static final String CLINK_ONBOARD = "SECUREAPP/api/lazypay/v0/clink/onboard";
    public static final String PLATFORM_OTP = "PLATFORM_SERVICE/api/lazypay/platform/otp";
    public static final String PLATFORM_V1_OTP = "PLATFORM_SERVICE/api/lazypay/platform/v1/otp";
    public static final String PLATFORM_LOGIN = "PLATFORM_SERVICE/api/lazypay/platform/login";
    public static final String PLATFORM_V1_LOGIN = "PLATFORM_SERVICE/api/lazypay/platform/v1/login";
    public static final String PLATFORM_LOGIN_REFRESH = "PLATFORM_SERVICE/api/lazypay/platform/login/refresh";
    public static final String PLATFORM_VALIDATE_TOKEN = "PLATFORM_SERVICE/api/lazypay/platform/um/validateToken";
    public static final String PLATFORM_TOKEN_VALIDATE = "PLATFORM_SERVICE/umToken/validate";
    public static final String PLATFORM_TOKEN_INVALIDATE = "PLATFORM_SERVICE/umToken/invalidate-token";
    public static final String PLATFORM_OAUTH_TOKEN = "PLATFORM_SERVICE/api/lazypay/platform/um/oauth/token";
    public static final String PLATFORM_VERIFY_MOBILE_SIGIN_IN = "PLATFORM_SERVICE/api/lazypay/platform/um/verifyMobileAndSignIn";
    public static final String PLATFORM_BBPS_INITIATE_OTP = "PLATFORM_SERVICE/api/lazyPay/verification/otp/initiateAuthentication";
    public static final String PLATFORM_BBPS_VALIDATE_OTP = "PLATFORM_SERVICE/api/lazyPay/verification/otp/validateAuthentication";
    public static final String PLATFORM_BBPS_CALL_BACK = "PLATFORM_SERVICE/api/lazyPay/verification/otp/callBack";

    /**
     * User Registration Service
     */
    public static final String FETCH_OTP = "USER_REGISTRATION/v0/user/fetchOtp";
    public static final String URS_FIND_OR_CREATE_USER = "USER_REGISTRATION_SERVICE/v0/user/findOrCreateUser";
    public static final String URS_RESEND_OTP = "USER_REGISTRATION_SERVICE/v0/user/resendOtp";
    public static final String URS_FIND_USER = "USER_REGISTRATION_SERVICE/v0/user/findUser";
    public static final String URS_VALIDATE_USER = "USER_REGISTRATION_SERVICE/v0/user/validateUser";



    /**
     * Txn Monitoring Crons
     */
    public static final String TM_CREATE_CUSTOMER_REQUEST = "HEIMDALL_SERVICE/test/test-customerRequest";
    public static final String TM_CREATE_FINANCIAL_TXN_REQUEST = "HEIMDALL_SERVICE/test/test-txnRequest";
    public static final String TM_CRON_PROCESS_CUSTOMER_REQUEST = "HEIMDALL_SERVICE/cron/process-customer-request";
    public static final String TM_CRON_PROCESS_FINANCIAL_TXN_REQUEST = "HEIMDALL_SERVICE/cron/process-financialtxn-request";
    public static final String TM_CRON_UPLOAD_CUSTOMER = "HEIMDALL_SERVICE/cron/upload-customer";
    public static final String TM_CRON_UPLOAD_FINANCIAL_TXN = "HEIMDALL_SERVICE/cron/upload-financialtxn";
    public static final String TM_CRON_UPLOAD_PRODUCT = "HEIMDALL_SERVICE/cron/upload-product";

//kyc status


    /**
     * POLICY APIS
     */
    public static final String OTP_SERVICE_CREATE_POLICY = "OTP_SERVICE_BASE_URL/v1/internal/policy/create";
    public static final String OTP_SERVICE_GET_POLICY = "OTP_SERVICE_BASE_URL/v1/internal/policy/get";
    public static final String OTP_SERVICE_DELETE_POLICY = "OTP_SERVICE_BASE_URL/v1/internal/policy/delete";

    /**
     * OTP SERVICE APIS
     * */

    public static final String OTP_SERVICE_GET_OTP = "OTP_SERVICE_BASE_URL/v1/otp/get";
    public static final String OTP_SERVICE_GENERATE_OTP="OTP_SERVICE_BASE_URL/v1/otp/generate";
    public static final String OTP_SERVICE_VALIDATE_OTP="OTP_SERVICE_BASE_URL/v1/otp/validate";
    public static final String OTP_SERVICE_RESEND_OTP="OTP_SERVICE_BASE_URL/v1/otp/resend";

    /**
     * APV_Heimdall Changes
     */
    public static final String FETCH_GEO_LOCATION = "HEIMDALL_SERVICE/external-integration/fetch-geolocation";
    public static final String FETCH_PROXIMITY_DISTANCE = "HEIMDALL_SERVICE/external-integration/fetch-proximity-distance";
    public static final String FETCH_FIELDS = "HEIMDALL_SERVICE/external-integration/utilitybill/fetch-fields";
    public static final String FETCH_ADDRESS = "HEIMDALL_SERVICE/external-integration/utilitybill/fetch-address";
    public static final String FETCH_CITY_LIST = "HEIMDALL_SERVICE/external-integration/utilitybill/fetch-landline-city-list";
    public static final String PULL_FROM_REMOTE = "HEIMDALL_SERVICE/external-integration/sm-pull-from-remote";
    public static final String PUSH_TO_REMOTE = "HEIMDALL_SERVICE/external-integration/sm-push-to-remote";

    /**
     * Delink PAN
     */

    public static final String INITIATE_PAN_DELINK = "LAZYPAY_BASEURL_KYC/api/kycEngine/v1/initiatePanDelink";
    public static final String REPORT_TO_CKYC = "LAZYPAY_BASEURL_KYC/ckyc-reporting/util/reportToCkyc/{uuid}/{product}";


    /**
     * Billpay
     */
    public static final String GET_BILLERS = "BILLPAY_URI/api/lazypay/billpayments/v0/billers";
    public static final String BILL_FETCH = "BILLPAY_URI/api/lazypay/billpayments/v0/billFetch";
    public static final String BILL_PAY = "BILLPAY_URI/api/lazypay/billpayments/v2/billPay";
    public static final String GET_PAYMENTSTATUS = "BILLPAY_URI/api/lazypay/billpayments/v0/billPaymentStatus";
    public static final String GET_BILLERDETAILS = "BILLPAY_URI/api/lazypay/billpayments/v0/billerDetails";
    public static final String GET_CATEGORY = "BILLPAY_URI/api/lazypay/billpayments/v0/categories";
    public static final String GET_BILLERPLANS = "BILLPAY_URI/api/lazypay/billpayments/v0/billerPlans";
    public static final String GET_SEARCHBILLER = "BILLPAY_URI/api/lazypay/billpayments/v0/searchBiller";
    public static final String GET_UPCOMINGBILLS = "BILLPAY_URI/api/lazypay/billpayments/v0/upcomingBills";
    public static final String GET_FETCHBILLDETAILS = "BILLPAY_URI/api/lazypay/billpayments/v0/fetchBillAndLastBillDetailsForUserLinkedBillId";
    public static final String GET_USERSBILLS = "BILLPAY_URI/api/lazypay/billpayments/v0/userBills";
    public static final String GET_PREPAIDCIRCLEINFO="BILLPAY_URI/api/lazypay/billpayments/v0/prepaidCirclesInfo";
    public static final String GET_PREPAIDOPERATOR="BILLPAY_URI/api/lazypay/billpayments/v0/prepaidOperators";
    public static final String GET_prepaidOperatorsAndCircles="BILLPAY_URI/api/lazypay/billpayments/v0/prepaidOperatorsAndCircles";
    public static final String GET_prepaidOperatorCircleForMobile="BILLPAY_URI/api/lazypay/billpayments/v0/prepaidOperatorCircleForMobile";
    public static final String GET_prepaidRechargePlans="BILLPAY_URI/api/lazypay/billpayments/v1/prepaidRechargePlans";
    public static final String GET_RefId="BILLPAY_URI/api/lazypay/billpayments/v0/generateRefId";
    public static final String GET_UserLinkedBillId="BILLPAY_URI/api/lazypay/billpayments/v0/userLinkedBillVerified";
    public static final String GET_registerComplaint="BILLPAY_URI/api/lazypay/billpayments/v0/registerComplaint";


    /**
     * Chat Bot
     */
    public static final String GET_INFO = "CHAT_BOT/api/info/v0/getInfo";
    public static final String START_SESSION = "CHAT_BOT/api/chat/v0/startSession";



    /**
     * Chat Bot NeedHelp
     */
    public static final String GET_CATEGORY_SEARCH = "CHAT_BOT/api/lazypay/chatbot/v0/needHelp/category/search";
    public static final String GET_NODE_MAPPING = "CHAT_BOT/api/lazypay/chatbot/v0/needHelp/node/mapping";
    public static final String GET_TREE_BY_LEVEL = "CHAT_BOT/api/lazypay/chatbot/v0/needHelp/tree";
    public static final String GET_TREE_BY_NODEID = "CHAT_BOT/api/lazypay/chatbot/v0/needHelp/tree/$";
    public static final String GET_FAQ_BY_LEVEL = "CHAT_BOT/api/lazypay/chatbot/v0/needHelp/faqs";
    public static final String GET_TREE_1 =       "CHAT_BOT/api/lazypay/chatbot/v0/needHelp/tree1";

    /**
     * NACH presentment
     */


    public static final String INITIATE_COLLECTION = "COLLECTION/api/collections";
    public static final String INITIATE_WEBHOOK = "COLLECTION/api/collections/web-hook";
    public static final String SEND_COLLECTIONS_TO_LP = "COLLECTION/api/collections/cron/sendCollectionsToLp";


    /**
     * Xpress API Endpoints
     */
    public static final String BUREAU_CIBIL_CBP_MOCK = "CIBIL_CBP_MOCKING/v1/customerAssets/mock";
    public static final String BUREAU_HARD_PULL_MOCK = "HARD_PULL_MOCKING_URL/cibil/create-report";
    public static final String LAZYPAY_FEATURES_MOCK = "AMS_BASE_URL/assessment/.mocks/upsert-generic-mock/ds_lp_data";
    public static final String ONE_REPAYMENT_FEATURES_MOCK = "AMS_BASE_URL/assessment/.mocks/upsert-generic-mock/ds_tc_bnpl_features";
    public static final String DARWIN_FEATURES_MOCK = "AMS_BASE_URL/assessment/.mocks/upsert-generic-mock/darwin_v6_features";
    public static final String DARWINV5_FEATURES_MOCK = "AMS_BASE_URL/assessment/.mocks/upsert-generic-mock/darwin_features";
    public static final String CREDIT_EXPIRE = "CREDIT_EXPIRE_BASE_URL/.automation/credit-expire/{muid}?force=true";
    public static final String CREDIT_EXPIRE_POST_APPROVED = "PAYSENSE_BASE_URL/.automation/credit-expire-post-approved/$";
    public static final String BUREAU_PORTFOLIO_SCRUB_MOCK = "PORTFOLIO_SCRUB_MOCKING/cibil/mock-cibil-portfolio-scrub";
    public static final String BSCORE_MOCK = "AMS_BASE_URL/assessment/.mocks/upsert-generic-mock/bscore_data";
    public static final String REGISTER_USER_SHYLOCK = "SHYLOCK/api/shylock/campaign-user";
    public static final String SECURE_V0_STATUS_FETCH = "SECUREAPP/api/lazypay/v0/userStatus?mobile=?&refreshLimit=true";
    public static final String RISK_UPDATE_BNPL_LIMIT = "RISK_UPDATE_CREDIT_LIMIT/RiskAPI/risk/updateUserLimit";
    public static final String PSCORE_DISBURSAL_API = "PAYSENSE_BASE_URL/.automation/disburse-on-sbox/$";
    public static final String CHECK_MAX_DPD = "PAYMENTS_BASE_URL/core/api/loans/installments?master_user_id=?";
    public static final String XPRESS_PAYMENT_API = "PAYMENTS_BASE_URL/core/.automation/pay/$";
    public static final String PSCORE_REGISTRATION_ENDPOINT = "PAYSENSE_BASE_URL/users/v1/external/users";
    public static final String PAN_FORM_DETAILS_SHYLOCK = "SHYLOCK/api/shylock/pan-form-details/$";
    public static final String SUBMIT_PAN_FORM_SHYLOCK = "SHYLOCK/api/shylock/initiate-loan-application";
    public static final String ONBOARDING_STATUS_SHYLOCK = "SHYLOCK/api/shylock/onBoardingCaseStatus?phoneNumber=?";
    public static final String GET_LOAN_PLANS_SHYLOCK = "SHYLOCK/api/shylock/v2/loan-plan/$";
    public static final String SELECT_LOAN_PLANS_SHYLOCK = "SHYLOCK/api/shylock/select-loan-plan/$";
    public static final String JOURNEY_OVERVIEW_SHYLOCK = "SHYLOCK/api/shylock/journey-overview/$";
    public static final String VERIFY_BANK_ACCOUNT_SHYLOCK = "SHYLOCK/api/shylock/bank/bankVerification";
    public static final String SKIP_REPAYMENT_SHYLOCK = "SHYLOCK/api/shylock/skip-repayment-setup/$";
    public static final String COMPLETE_APPLICATION_REVIEW_SHYLOCK = "SHYLOCK/api/shylock/complete-application-review/$";
    public static final String SELECTED_LOAN_PLANS_SHYLOCK = "SHYLOCK/api/shylock/selected-loan-plan-details/$";
    public static final String GET_KFS_DETAILS_SHYLOCK = "SHYLOCK/api/shylock/kfs-details/$";
    public static final String SIGN_KFS_SHYLOCK = "SHYLOCK/api/shylock/sign-kfs/$";
    public static final String FETCH_LOAN_AGREEMENT_SHYLOCK = "SHYLOCK/api/shylock/fetch-loan-agreement";
    public static final String AGREEMENT_SENT_OTP_SHYLOCK = "SHYLOCK/api/shylock/send-otp";
    public static final String SIGN_LOAN_AGREEMENT_SHYLOCK = "SHYLOCK/api/shylock/sign-loan-agreement";
    public static final String SUBMIT_CA_FORM_SHYLOCK = "SHYLOCK/api/shylock/communication-address-form/$";
    public static final String COLLECT_REFERENCE_SHYLOCK = "SHYLOCK/api/shylock/contact-references/$";
    public static final String SUBMIT_REFERENCE_SHYLOCK = "SHYLOCK/api/shylock/submit-references/$";
    public static final String VERIFY_REFERENCE_SHYLOCK = "SHYLOCK/api/shylock/reference/verify-code/$";
    public static final String CASH_LOAN_OFFERING_SHYLOCK ="SHYLOCK/api/shylock/campaign-user/cash-loan-offering";
    public static final String AGREEMENT_GET_OTP_SHYLOCK ="PAYSENSE_BASE_URL/.automation/get-otp";
    public static final String PSCORE_GENERIC_ASSESSMENT = "PAYSENSE_BASE_URL/.internal/users/generic-assessment?master_user_id=$";
    public static final String GENERATE_XPRESS_SCRUB_REPORT = "PAYSENSE_BASE_URL/.external/generate-xpress-scrub-report";
    public static final String SCRUB_ASSESSMENT_ENGINE = "ASSESSMENT_ENGINE_BASE_URL/assessment/assess/xpress/scrub";
    public static final String REPEAT_ASSESSMENT_ASSESSMENT_ENGINE = "ARTHAMATICS_BASE_URL/assessment/generic/v1/xpress-repeat";


    /**
     * Device Binding
     * Device Binding API endpoints
     */
    public static final String DB_CANCEL = "DEVICE_BINDING/v1/deviceBinding/cancel";
    public static final String DB_CHECK_BINDING_STATUS_WITH_TOKEN = "DEVICE_BINDING/v1/deviceBinding/checkBindingStatusWithToken";
    public static final String DB_GET_USER_BINDING_STATUS = "DEVICE_BINDING/v1/deviceBinding/getUserBindingStatus";
    public static final String DB_INITIATE = "DEVICE_BINDING/v1/deviceBinding/initiate";
    public static final String DB_INVALIDATE = "DEVICE_BINDING/v1/deviceBinding/invalidateDeviceBinding";
    public static final String DB_NOTIFY_SMS_DETAILS = "DEVICE_BINDING/v0/webhook/notifySmsDetails";
    public static final String DB_REFRESH_BINDING = "DEVICE_BINDING/v1/deviceBinding/refreshBinding";

    /**
     * Device Binding phase 1
     */
    public static final String GET_REGISTRATION_STATUS = "DEVICE_BINDING/api/device/bind/v0/status";
    public static final String GENERATE_SMS_DEVICE_BINDING = "DEVICE_BINDING/api/device/bind/v0/generate-sms";
    public static final String NOTIFY_SMS_DEVICE_BINDING = "DEVICE_BINDING/api/device/bind/v0/notify-sms-status";
    public static final String DEVICE_BINDING_WEBHOOK = "DEVICE_BINDING/api/device/v0/webhook/karix/device-binding";

    /**
     * 2FA
     */
    public static final String GET_APP_OTP = "SECUREAPP/api/transactions/v0/app-otp";
    public static final String GET_OTP_BY_ID = "OTP_SERVICE_BASE_URL/v1/otp/get";
    public static final String CHECK_EXISTENCE= "DEVICE_BINDING/v1/deviceInfo/checkExistence";

    /**
     * KYC_PLATFORM SERVICE REQUESTS
     */
    public static final String START_DIGILOCKER = "KYC_PLATFORM_SERVICE_BASE_URL/digilocker/v0/start-link";
    public static final String GET_DIGILOCKER_STATUS = "KYC_PLATFORM_SERVICE_BASE_URL/digilocker/v0/status";
    public static final String GET_DIGILOCKER_RESULTS = "KYC_PLATFORM_SERVICE_BASE_URL/digilocker/v0/results";

    /**
     * BNPL RISK
     */
    public static final String RISK_GET_DECISION_V1 = "BNPL_RISK/RiskAPI/risk/v1/getDecision";
    public static final String RISK_GET_DECISION_V2 = "BNPL_RISK/RiskAPI/risk/v2/getDecision";
    public static final String RISK_GET_PRODUCT_LIMIT = "BNPL_RISK/RiskAPI/risk/v0/product-limit";
    public static final String RISK_USER_LIMIT = "BNPL_RISK/RiskAPI/risk/v1/getUserLimit";
    public static final String RISK_UPDATE_APPROVED_LIMIT = "BNPL_RISK/RiskAPI/risk/update-approved-limit";
    public static final String RISK_TENTATIVE_PRODUCT_LIMIT_POST_MITC = "BNPL_RISK/RiskAPI/risk/v0/tentative-product-limit-post-mitc";
    public static final String RISK_SET_WHITELIST_LIMIT_V1 = "BNPL_RISK/RiskAPI/risk/v1/setWhitelistLimit";

    public static final String RISK_PL_UPDATE_PRE_APPROVED_LIMIT = "BNPL_RISK_PL/riskpl/tentative-limit/update-pre-approve-limit";
    public static final String RISK_PL_SET_WHITELIST_LIMIT = "BNPL_RISK_PL/riskpl/tentative-limit/setWhitelistLimit";
    public static final String RISK_PL_SET_WHITELIST_LIMIT_V1 = "BNPL_RISK_PL/riskpl/tentative-limit/v1/setWhitelistLimit";
    public static final String RISK_PL_GET_DECISION_V1 = "BNPL_RISK_PL/riskpl/v1/getdecision";

    public static final String FRAUD_GET_DECISION = "BNPL_FRAUD/FraudEngine/getDecision";

    /**
     * MPL BNPL CORE
     */
    public static final String MPL_ELIGIBILITY_V0 = "WEBAPP/api/mpl/v0/transaction/eligibility";
    public static final String MPL_TRANSACTION_AUTHORISATION_V0 = "WEBAPP/api/mpl/v0/transaction/authorisation";
    public static final String MPL_TRANSACTION_SETTLEMENT_V0 = "WEBAPP/api/mpl/v0/transaction/settlement";
    public static final String MPL_TRANSACTION_REFUND_V0 = "WEBAPP/api/mpl/v0/transaction/refund";
    public static final String MPL_TRANSACTION_ENQUIRY_V0 = "WEBAPP/api/mpl/v0/transaction/enquiry";
    public static final String MPL_TRANSACTION_DETAILS_V0 = "WEBAPP/api/mpl/v0/user/transaction-details";
    public static final String MPL_ACCOUNT_DETAILS_V0 = "WEBAPP/api/mpl/v0/user/account-details";
    public static final String MPL_INITIATE_REPAY_V0 = "WEBAPP/api/mpl/v0/repayment/initiate";
    public static final String MPL_UPDATE_REPAY_STATUS_V0 = "WEBAPP/api/mpl/v0/repayment/status-update";

}
