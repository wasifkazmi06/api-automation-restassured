package constants;


public class UtilConstants {
    /**
     * Components
     */
    public static final String LAZYCARD_COMPONENT = "lazycard";
    public static final String WEBAPP_COMPONENT = "webapp";
    public static final String SECUREAPP_COMPONENT = "secureApp";


    /*
        MySQL DB Details
     */
    public static final String LAZYPAY_HOST = "lazypay.mysql.host";
    public static final String LAZYPAY_PORT = "lazypay.mysql.port";
    public static final String LAZYPAY_USER = "lazypay.mysql.username";
    public static final String LAZYPAY_PASSWORD = "lazypay.mysql.passwd";
    public static final String LAZYPAY_DBNAME = "lazypay.mysql.dbName";
    public static final String SSH_USER = "ssh.user";
    public static final String SSH_PASSWORD = "ssh.passowrd";
    public static final String SSH_HOST = "ssh.host";
    public static final String SSH_PORT = "ssh.port";
    public static final String SSH_LOCAL_PORT = "ssh.localport";
    public static final String FD_SSH_USER = "fd.ssh.user";
    public static final String FD_SSH_PASSWORD = "fd.ssh.passowrd";
    public static final String FD_SSH_HOST = "fd.ssh.host";

    /**
     * lazy card
     */
    public static final String LAZYCARD_HOST = "lazycard.mysql.host";
    public static final String LAZYCARD_PORT = "lazycard.mysql.port";
    public static final String LAZYCARD_USER = "lazycard.mysql.username";
    public static final String LAZYCARD_HPASSWORD = "lazycard.mysql.passwd";
    public static final String LAZYCARD_DBNAME = "lazycard.mysql.dbName";

    public static final String LAZYCARD_MONGO_HOST = "lazycard.mysql.dbName";
    public static final String FD_HOST = "fd.mysql.host";
    public static final String FD_PORT = "fd.mysql.port";
    public static final String FD_USER = "fd.mysql.username";
    public static final String FD_HPASSWORD = "fd.mysql.passwd";
    public static final String FD_DBNAME = "fd.mysql.dbName";

    public static final String USERPLATFORM_MONGO_HOST = "userplatform.mongo.host";
    public static final String USERPALTFORM_MONGO_PORT = "userplatform.mongo.port";
    public static final String USERPLATFORM_MONGO_USERNAME = "userplatform.mongo.username";
    public static final String USERPLATFORM_MONGO_PASSWORD = "userplatform.mongo.password";
    public static final String USERPLATFORM_MONGO_DATABASE = "userplatform.mongo.db";

    public static final String USERPLATFORM_SSH_HOST = "userplatform.ssh.host";
    public static final String USERPLATFORM_SSH_PORT = "userplatform.ssh.port";
    public static final String USERPLATFORM_SSH_USER = "userplatform.ssh.user";
    public static final String USERPLATFORM_SSH_PASSWORD = "userplatform.ssh.password";


    /**
     * User Segmentation
     */
    public static final String USERSEGMENTATION_HOST = "usersegmentation.mysql.host";
    public static final String USERSEGMENTATION_PORT = "usersegmentation.mysql.port";
    public static final String USERSEGMENTATION_USER = "usersegmentation.mysql.username";
    public static final String USERSEGMENTATION_HPASSWORD = "usersegmentation.mysql.passwd";
    public static final String USERSEGMENTATION_DBNAME = "usersegmentation.mysql.dbName";


    /**
     * UPI
     */
    public static final String UPI_HOST = "upi.mysql.host";
    public static final String UPI_PORT = "upi.mysql.port";
    public static final String UPI_USER = "upi.mysql.username";
    public static final String UPI_PASSWORD = "upi.mysql.password";
    public static final String UPI_DBNAME = "upi.mysql.dbName";


    /**
     * Api Base URI
     */
    // public static final String LAZYPAY_SANDBOX = "lazypay.sandbox.server";
    public static final String LAZYCARD_SANDBOX = "lazycard.sandbox.server";
    public static final String WEBAPP = "webAppURI";
    public static final String SECUREAPP = "secureAppURI";
    public static final String LAZYPAY_BASEURL_PLATFORM = "baseURIforPlatform";
    public static final String OTP_BASEURL = "baseURIforOTP";
    public static final String LAZYPAY_BASEURL_KYC = "baseURIforKYC";
    public static final String BIFROST_SBOX = "bifrost.sandbox.uri";

    public static final String CREDIT_LINE = "creditLineURI";
    public static final String UPI = "upiURI";
    public static final String STG_WEBAPP = "stagingWebAppURI";
    public static final String COLLECTION = "collectionURI";
    public static final String KYCPlatform_URI = "kycPlatformURI";

    /**
     * lazypay client Id, secret key
     */

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";

    public static final String LAZYPAY_BASEURL_CRON = "bnplCronURI";
    public static final String FD_SERVICE = "securedCard.sandbox.fdserver";
    public static final String PLATFORM_SERVICE = "platformBaseURI";
    public static final String USER_REGISTRATION_SERVICE = "userRegistrationBaseURI";
    public static final String HEIMDALL_SERVICE = "heimdallBaseURI";

    public static final String MBE = "mbeAppURI";

    public static final String DCS_SERVICE = "dcsBaseURI";

    public static final String USER_SEGMENTATION_SERVICE = "usersegmentationBaseURI";

    /**
     * JustPay -Collections DB -WebAPP -LazyPay Repayment
     */

    public static final String JUSTPAY_HOST = "justpay.mysql.host";
    public static final String JUSTPAY_PORT = "justpay.mysql.port";
    public static final String JUSTPAY_USER = "justpay.mysql.username";
    public static final String JUSTPAY_PASSWORD = "justpay.mysql.passwd";
    public static final String JUSTPAY_DBNAME = "justpay.mysql.dbName";

    public static final String DOC_STORE_SERVICE = "docStoreURI";
    public static final String API_KEY = "secureapp.juspay.apiKey";


    /**
     * VAULT
     */

    public static final String VAULT="vault.URI";
    public static final String VAULT_AUTH_TOKEN="vault.authToken";
    public static final String GRINGOTTS="gringotts.URI";
    public static final String GRINGOTTS_XAPI_Key="gringotts.XAPIKey";

    /**
     * AMS
     */
    public static final String AMS_HOST = "ams.mysql.host";
    public static final String AMS_PORT = "ams.mysql.port";
    public static final String AMS_USER = "ams.mysql.username";
    public static final String AMS_HPASSWORD = "ams.mysql.passwd";
    public static final String AMS_DBNAME = "ams.mysql.dbName";
    public static final String AMS_SSH_HOST = "ams.ssh.host";
    public static final String AMS_SSH_USERNAME = "ams.ssh.user";
    public static final String AMS_SSH_PASSWORD = "ams.ssh.password";
    public static final String AMS_SSH_PORT = "ams.ssh.port";


    public static final String URS_HOST = "urs.mysql.host";
    public static final String URS_PORT = "urs.mysql.port";
    public static final String URS_USER = "urs.mysql.username";
    public static final String URS_HPASSWORD = "urs.mysql.passwd";
    public static final String URS_DBNAME = "urs.mysql.dbName";
    public static final String URS_SSH_HOST = "urs.ssh.host";
    public static final String URS_SSH_USERNAME = "urs.ssh.user";
    public static final String URS_SSH_PASSWORD = "urs.ssh.password";
    public static final String URS_SSH_PORT = "urs.ssh.port";


    public static final String HEIMDALL_HOST = "heimdall.mysql.host";
    public static final String HEIMDALL_PORT = "heimdall.mysql.port";
    public static final String HEIMDALL_USER = "heimdall.mysql.username";
    public static final String HEIMDALL_HPASSWORD = "heimdall.mysql.passwd";
    public static final String HEIMDALL_DBNAME = "heimdall.mysql.dbName";

    /**
     * User Registration Service
     */

    public static final String USER_REGISTRATION = "userRegistration.URI";
    public static final String TENANT_ID = "userRegistration.tenantID";

    /**
     * OTP
     */
    public static final String OTP_SERVICE_BASE_URL = "baseURIforOtpService";
    public static final String OTP_SERVICE_HOST = "otpservice.mysql.host";
    public static final String OTP_SERVICE_PORT = "otpservice.mysql.port";
    public static final String OTP_SERVICE_USER = "otpservice.mysql.user";
    public static final String OTP_SERVICE_PASSWORD = "otpservice.mysql.password";
    public static final String OTP_SERVICE_DBNAME = "otpservice.mysql.db";

    /**
     * OTP Decryption
     */
    public static final String OTP_DECRYPT_ALGORITHIM = "algorithm";
    public static final String OTP_DECRYPT_ALGORITHIM_MODE = "algorithmMode";
    public static final String OTP_INITIALIZATION_VECTOR = "initializationVector";


    /**
     * KYC DB
     */

    public static final String KYC_DBNAME = "kyc.mysql.dbName";
    public static final String KYC_SSH_USER="kyc.ssh.user";
    public static final String KYC_SSH_PASSWORD="kyc.ssh.passowrd";
    public static final String KYC_SSH_HOST="kyc.ssh.host";
    public static final String KYC_SSH_PORT="kyc.ssh.port";
    public static final String KYC_LOCAL_PORT="kyc.ssh.localport";
    public static final String KYC_HOST="kyc.mysql.host";
    public static final String KYC_PASSWORD="kyc.mysql.passwd";
    public static final String KYC_USER="kyc.mysql.username";
    public static final String KYC_PORT="kyc.mysql.port";
    public static final String KYC_LOCAL_HOST="kyc.mysql.local.host";
    public static final String KYC_PORT_LOCAL="kyc.mysql.local.port";


    /**
     * BIFROST DB
     */
    public static final String BIFROST_HOST = "bifrost.mysql.host";
    public static final String BIFROST_PORT = "bifrost.mysql.port";
    public static final String BIFROST_USER = "bifrost.mysql.username";
    public static final String BIFROST_PASSWORD = "bifrost.mysql.passwd";
    public static final String BIFROST_DBNAME = "bifrost.mysql.dbName";

    /**
     * Chat Bot
     */

    public static final String CHAT_BOT = "chatbot.url";

    /**
     * Xpress Api
     */
    public static final String CIBIL_CBP_MOCKING = "bureau.mock.url";
    public static final String HARD_PULL_MOCKING_URL = "bureau.mock.url";
    public static final String AMS_BASE_URL = "ams.mock.url";
    public static final String CREDIT_EXPIRE_BASE_URL = "creditexpire.url";
    public static final String PAYSENSE_BASEURL = "paysense.base.url";
    public static final String REGISTER_USER_SHYLOCK = "shylock.base.url";
    public static final String RISK_UPDATE_CREDIT_LIMIT ="risk.base.url";
    public static final String PAYMENTS_BASE_URL ="payments.base.url";
    public static final String ARTHAMATICS_BASE_URL ="arthamatics.base.url";
    public static final String ASSESSMENT_ENGINE_BASE_URL ="assessment.engine.base.url";
    public static final String PORTFOLIO_SCRUB_MOCKING = "sboxbureau.lazypay.net";

    /**
     * Xpress Databases
     */

    // OCR DB
    public static final String OCRDB_HOST = "ocr.postgresql.host";
    public static final String OCRDB_PORT = "ocr.postgresql.port";
    public static final String OCRDB_LOCAL_HOST = "ocr.postgresql.local.host";
    public static final String OCRDB_LOCAL_PORT = "ocr.postgresql.local.port";
    public static final String OCRDB_USER = "ocr.postgresql.username";
    public static final String OCRDB_PASSWORD = "ocr.postgresql.passwd";
    public static final String OCRDB_DBNAME = "ocr.postgresql.dbName";

    // Pscore DB
    public static final String PSCORE_HOST = "pscore.postgresql.host";
    public static final String PSCORE_PORT = "pscore.postgresql.port";
    public static final String PSCORE_LOCAL_HOST = "pscore.postgresql.local.host";
    public static final String PSCORE_LOCAL_PORT = "pscore.postgresql.local.port";
    public static final String PSCORE_USER = "pscore.postgresql.username";
    public static final String PSCORE_PASSWORD = "pscore.postgresql.passwd";
    public static final String PSCORE_DBNAME = "pscore.postgresql.dbName";

    //Shylock-CreditLine DB
    public static final String SHYLOCK_HOST="shylock.mysql.host";
    public static final String SHYLOCK_PORT="shylock.mysql.port";
    public static final String SHYLOCK_LOCAL_HOST="shylock.mysql.local.host";
    public static final String SHYLOCK_LOCAL_PORT="shylock.mysql.local.port";
    public static final String SHYLOCK_USER="shylock.mysql.username";
    public static final String SHYLOCK_PASSWORD="shylock.mysql.passwd";
    public static final String SHYLOCK_DBNAME="shylock.mysql.dbname";

    //Paymemts DB
    public static final String PAYMENT_HOST="payments.mysql.host";
    public static final String PAYMENT_PORT="payments.mysql.port";
    public static final String PAYMENT_LOCAL_HOST="payments.mysql.local.host";
    public static final String PAYMENT_LOCAL_PORT="payments.mysql.local.port";
    public static final String PAYMENT_USER="payments.mysql.username";
    public static final String PAYMENT_PASSWORD="payments.mysql.passwd";
    public static final String PAYMENT_DBNAME="payments.mysql.dbname";

    // AMS DB
    public static final String AMS_HOST_XPRESS = "ams.postgresql.host";
    public static final String AMS_PORT_XPRESS = "ams.postgresql.port";
    public static final String AMS_LOCAL_HOST = "ams.postgresql.local.host";
    public static final String AMS_LOCAL_PORT = "ams.postgresql.local.port";
    public static final String AMS_USER_XPRESS = "ams.postgresql.username";
    public static final String AMS_PASSWORD = "ams.postgresql.passwd";
    public static final String AMS_DBNAME_XPRESS = "ams.postgresql.dbname";

    /**
     * Device Binding
     * DEVICE_BINDING
     */

    public static final String DEVICE_BINDING_V2 = "deviceBindingBaseURI";
    public static final String DEVICE_BINDING_HOST="devicebinding.mysql.host";
    public static final String DEVICE_BINDING_PORT="devicebinding.mysql.port";
    public static final String DEVICE_BINDING_USER="devicebinding.mysql.username";
    public static final String DEVICE_BINDING_HPASSWORD="devicebinding.mysql.passwd";
    public static final String DEVICE_BINDING_DBNAME="devicebinding.mysql.dbName";
    public static final String DEVICE_BINDING_SSH_HOST="devicebinding.ssh.host";
    public static final String DEVICE_BINDING_SSH_USERNAME="devicebinding.ssh.user";
    public static final String DEVICE_BINDING_SSH_PASSWORD="devicebinding.ssh.password";
    public static final String DEVICE_BINDING_SSH_PORT="devicebinding.ssh.port";

    /**
     * Device Binding phase 1
     */
    public static final String DEVICE_BINDING = "deviceBinding.url";

    /**
     * SFTP SERVER DETAILS
     *
     */
    public static final String SFTP_HOST ="sftp.host";


    /**
     * Billpay
     *
     */

    public static final String BILLPAY_URI="billpay.uri";
    public static final String UM_URI="um.uri";



    public static final String SFTP_USER ="sftp.user";
    public static final String SFTP_PASSWORD ="sftp.password";

    /**
     * local ssh details
     *
     */
    public static final String LOCAL_SSH_USER="local.ssh.user";
    public static final String LOCAL_SSH_PWD="local.ssh.password";
    public static final String LOCAL_SSH_HOST = "local.ssh.host";

    /*
     * KYC platform details
     */
    public static final String KYCPlatform_DBName="KycPlatform.mysql.dbName";

    /*
     * device Info service details
     */
    public static final String PFTenantToken = "pftenantoken";
    public static final String PFTenantId = "userRegistration.tenantID";

    /**
     * BNPL RISK AND FRAUD
     *
     */
    public static final String BNPL_RISK="bnpl.risk.URI";
    public static final String BNPL_RISK_PL="bnpl.riskpl.URI";
    public static final String BNPL_FRAUD="bnpl.fraud.URI";




}
